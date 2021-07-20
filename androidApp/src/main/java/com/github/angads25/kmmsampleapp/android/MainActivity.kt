package com.github.angads25.kmmsampleapp.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.angads25.kmmsampleapp.android.adapter.ImageListAdapter
import com.github.angads25.kmmsampleapp.android.databinding.ActivityMainBinding
import com.github.angads25.kmmsampleapp.data.State
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.imageList.layoutManager = LinearLayoutManager(this)
        binding.imageList.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        val adapter =  ImageListAdapter()
        binding.imageList.adapter = adapter

        binding.imageList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount: Int = recyclerView.layoutManager?.childCount!!
                val totalItemCount: Int = recyclerView.layoutManager?.itemCount!!
                val firstVisibleItemPosition: Int = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                if (!isLoading) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                        viewModel.loadNextPage("galaxy", false)
                    }
                }
            }
        })

        viewModel.liveData.observe(this, {
            when(it) {
                is State.empty -> {

                }

                is State.error -> {
                    isLoading = false
                    Snackbar.make(binding.root, it.message, Snackbar.LENGTH_LONG).show()
                }

                is State.loading -> {

                }

                is State.result -> {
                    isLoading = false
                    adapter.addImages(it.data)
                }
            }
        })
        viewModel.loadNextPage("galaxy", true)
    }
}
