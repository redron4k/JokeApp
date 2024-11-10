package com.redron.ui.main
//
//import android.os.Bundle
//import android.widget.Toast
//import androidx.activity.ComponentActivity
//import androidx.lifecycle.ViewModelProvider
//import com.redron.data.JokesGenerator
//import com.redron.databinding.FragmentMainBinding
//import com.redron.ui.JokeViewModelFactory
//import com.redron.ui.main.recycler.JokesListAdapter
//import com.redron.ui.single_joke.SingleJokeActivity
//
//class MainActivity : ComponentActivity() {
//
//    private lateinit var binding: FragmentMainBinding
//    private lateinit var viewModel: JokesListViewModel
//    private val adapter = JokesListAdapter {
//        startActivity(SingleJokeActivity.getInstance(this, viewModel.getJokeId(it)))
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        binding = FragmentMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        initViewModel()
//
//        binding.recyclerView.adapter = adapter
//        savedInstanceState ?: run {
//            viewModel.generateJokes()
//        }
//    }
//
//    private fun initViewModel() {
//        val factory = JokeViewModelFactory(JokesGenerator)
//        viewModel = ViewModelProvider(this, factory)[JokesListViewModel::class.java]
//        viewModel.jokes.observe(this) {
//            adapter.submitList(it)
//        }
//        viewModel.error.observe(this) {
//            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
//        }
//    }
//}
