package com.bona.favorite.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bona.core.ui.ItemUserAdapter
import com.bona.expertgithubuser.ui.detail.DetailActivity
import com.bona.favorite.R
import com.bona.favorite.databinding.ActivityFavoriteBinding
import com.bona.favorite.di.favoriteModule
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private val adapter: ItemUserAdapter by lazy { ItemUserAdapter() }
    private val favoriteViewModel: FavoriteViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        loadKoinModules(favoriteModule)
        setupRecyclerView()
        observeFavoriteData()
    }

    private fun observeFavoriteData() {
        favoriteViewModel.getAllUserFavorite().observe(this) { users ->
            stateEmpty(false)
            if (!users.isNullOrEmpty()) {
                adapter.submitList(users)
            } else {
                adapter.submitList(null)
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvFavorite.apply {
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            setHasFixedSize(true)
            adapter = this@FavoriteActivity.adapter
            this@FavoriteActivity.adapter.onItemClick = { selectedData ->
                val intent = Intent(this@FavoriteActivity, DetailActivity::class.java)
                intent.putExtra("User Selected", selectedData.login)
                startActivity(intent)
            }
        }
    }

    private fun stateEmpty(isEmpty: Boolean) {
        binding.loading.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }

    override fun onResume() {
        super.onResume()
        observeFavoriteData()
    }
}
