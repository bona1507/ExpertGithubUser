package com.bona.expertgithubuser.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bona.core.data.remote.ItemState
import com.bona.core.ui.ItemUserAdapter
import com.bona.expertgithubuser.R
import com.bona.expertgithubuser.databinding.ActivityMainBinding
import com.bona.expertgithubuser.ui.AppViewModel
import com.bona.expertgithubuser.ui.detail.DetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ItemUserAdapter
    private val mainViewModel: AppViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.apply {
            searchView.setupWithSearchBar(search)
            searchView.editText.setOnEditorActionListener { _, _, _ ->
                search.setText(searchView.text)
                searchView.hide()
                showData(search.text.toString())
                false
            }
            favorite.setOnClickListener {
                startActivity(Intent(this@MainActivity, Class.forName("com.bona.favorite.ui.FavoriteActivity")))
            }
        }

        stateLoading(false)
        setAdapter()
    }

    private fun setAdapter() {
        adapter = ItemUserAdapter()

        binding.rvSearch.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
        }
    }

    private fun showData(username: String) {
        mainViewModel.getUsersByUsername(username).observe(this) { users ->
            when (users) {
                is ItemState.Loading -> stateLoading(true)
                is ItemState.Success -> {
                    stateLoading(false)
                    if (users.data.isNullOrEmpty()) stateLoading(false)
                    else {
                        binding.rvSearch.adapter = ItemUserAdapter().apply {
                            submitList(users.data)
                            onItemClick = { selectedData ->
                                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                                intent.putExtra("User Selected", selectedData.login)
                                startActivity(intent)
                            }
                        }
                    }
                }
                is ItemState.Error -> stateLoading(false)
            }
        }
    }

    private fun stateLoading(isLoading: Boolean) {
        binding.loading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
