package com.redron.presentation.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.ViewModelInitializer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.redron.R
import com.redron.data.datasource.local.CacheJokesDataSourceImpl
import com.redron.data.datasource.remote.RetrofitInstance
import com.redron.data.datasource.local.LocalJokesDataSourceImpl
import com.redron.data.datasource.remote.RemoteJokesDataSourceImpl
import com.redron.data.datasource.local.JokesDatabase
import com.redron.data.repository.JokesRepositoryImpl
import com.redron.databinding.FragmentListBinding
import com.redron.domain.usecases.AddJokeUseCase
import com.redron.domain.usecases.AddJokesUseCase
import com.redron.domain.usecases.AddToFavoritesUseCase
import com.redron.domain.usecases.ClearLoadedJokesUseCase
import com.redron.domain.usecases.LoadJokesLocalUseCase
import com.redron.domain.usecases.LoadJokesFromCacheUseCase
import com.redron.domain.usecases.LoadJokesFromNetUseCase
import com.redron.domain.usecases.RemoveFromFavoritesUseCase
import com.redron.presentation.main.recycler.JokesListAdapter
import kotlinx.coroutines.launch
import javax.inject.Inject

class ListFragment : Fragment(R.layout.fragment_list) {

    private lateinit var binding: FragmentListBinding

    @delegate:Inject
    private val viewModel: JokesListViewModel by viewModels(
        ownerProducer = { requireActivity() },
        factoryProducer = {
            ViewModelProvider.Factory.from(
                ViewModelInitializer(
                    clazz = JokesListViewModel::class.java,
                    initializer = {
                        val repository = JokesRepositoryImpl(
                            CacheJokesDataSourceImpl(JokesDatabase.INSTANCE!!),
                            LocalJokesDataSourceImpl(JokesDatabase.INSTANCE!!),
                            RemoteJokesDataSourceImpl(RetrofitInstance.retrofitClient)
                        )
                        JokesListViewModel(
                            LoadJokesLocalUseCase(repository),
                            AddJokeUseCase(repository),
                            AddJokesUseCase(repository),
                            LoadJokesFromCacheUseCase(repository),
                            ClearLoadedJokesUseCase(repository),
                            LoadJokesFromNetUseCase(repository),
                            AddToFavoritesUseCase(repository),
                            RemoveFromFavoritesUseCase(repository)
                        )
                    }
                )
            )
        }
    )

    private val adapter = JokesListAdapter(
        clickListener = {
            findNavController().navigate(
                ListFragmentDirections.actionListFragmentToJokeDetailsFragment(it)
            )
        },

        favClickListener = {
            viewModel.onFavClicked(it)
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.clearLoaded()

        savedInstanceState ?: run {
            viewModel.initJokes()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentListBinding.bind(view)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.addFragmentButton.setOnClickListener {
            findNavController().navigate(
                ListFragmentDirections.actionListFragmentToAddJokeFragment()
            )
        }

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                if (layoutManager.findLastVisibleItemPosition() == adapter.itemCount - 1) {
                    viewModel.loadMoreJokes()
                }
            }
        })

        initViewModel()
    }

    private fun initViewModel() {
        lifecycleScope.launch {
            viewModel.jokes.collect {
                adapter.submitList(it)
                binding.textView.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
            }
        }

        lifecycleScope.launch {
            viewModel.error.collect {
                it?.let {
                    Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
                }
            }
        }

        lifecycleScope.launch {
            viewModel.isLoading.collect {
                binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
            }
        }

        lifecycleScope.launch {
            viewModel.isLoadFromCache.collect {
                if (it)
                    Snackbar.make(
                        binding.root,
                        requireActivity().getString(R.string.is_load_from_cache_true),
                        Snackbar.LENGTH_SHORT
                    ).show()
            }
        }
    }
}