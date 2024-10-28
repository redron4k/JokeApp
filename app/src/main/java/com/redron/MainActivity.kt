package com.redron

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.redron.data.JokesGenerator
import com.redron.databinding.ActivityMainBinding
import com.redron.recycler.JokesAdapter

class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter = JokesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val generator = JokesGenerator()
        val someData = generator.generate()

        adapter.setNewData(someData)
        createRecyclerViewList()
    }

    private fun createRecyclerViewList() {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

    }


//    private fun createList() {
//        binding.linearLayout.apply {
//            for (i in 0 .. 100) {
//                val btn = Button(this@MainActivity).apply {
//                    text = "Button $i"
//                    setOnClickListener {
//                        Toast.makeText(this@MainActivity, "$i pressed", Toast.LENGTH_SHORT).show()
//                    }
//                }
//                addView(btn)
//            }
//        }
//    }
}
