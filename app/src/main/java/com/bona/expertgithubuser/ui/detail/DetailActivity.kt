package com.bona.expertgithubuser.ui.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import coil.load
import com.bona.core.data.remote.ItemState
import com.bona.core.utils.ItemUser
import com.bona.expertgithubuser.R
import com.bona.expertgithubuser.databinding.ActivityDetailBinding
import com.bona.expertgithubuser.ui.AppViewModel
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: AppViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("User Selected")
        username?.let {
            supportActionBar?.title = it
            showDetailContent(it)
            initPager(it)
        }
    }

    private fun initPager(username: String) {
        val sectionPagerAdapter = SectionPagerAdapter(this@DetailActivity, username)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionPagerAdapter

        val tabs = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = getString(TAB_TITLES[position])
        }.attach()
    }

    private fun showDetailContent(username: String) {
        detailViewModel.getUserDetail(username).observe(this) { users ->
            when (users) {
                is ItemState.Loading -> stateLoading(true)
                is ItemState.Success -> {
                    stateLoading(false)
                    users.data?.let { displayUserDetails(it) } ?: stateLoading(true)
                }
                is ItemState.Error -> stateLoading(true)
            }
        }
    }

    private fun displayUserDetails(user: ItemUser) {
        with(binding) {
            profilePict.load(user.avatarUrl)
            tvUserName.text = user.login
            tvFullName.text = user.name
            tabs.getTabAt(0)?.text = getString(R.string.followers_count, user.followers)
            tabs.getTabAt(1)?.text = getString(R.string.following_count, user.following)
            setStatusFavorite(user)
        }
    }

    private fun setStatusFavorite(user: ItemUser) {
        detailViewModel.getFavoriteIsExists(user.login).observe(this) { isFavorite ->
            val drawableRes = if (isFavorite) R.drawable.favorite else R.drawable.not_favorite
            binding.favorite.setImageDrawable(ContextCompat.getDrawable(this, drawableRes))

            binding.favorite.setOnClickListener {
                if (isFavorite) detailViewModel.deleteUserFavorite(user)
                else detailViewModel.insertUserFavorite(user)
            }
        }
    }

    private fun stateLoading(isLoading: Boolean) {
        binding.loading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        private val TAB_TITLES = intArrayOf(
            R.string.followers_count, R.string.following_count
        )
    }
}
