package com.bona.expertgithubuser.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bona.core.data.remote.ItemState
import com.bona.core.ui.ItemUserAdapter
import com.bona.core.utils.ItemUser
import com.bona.expertgithubuser.databinding.FragmentFollowBinding
import com.bona.expertgithubuser.ui.AppViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FollowFragment : Fragment() {
    private lateinit var binding: FragmentFollowBinding
    private lateinit var adapter: ItemUserAdapter
    private val detailViewModel: AppViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentFollowBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()

        val position = arguments?.getInt(ARG_POSITION, 0) ?: 0
        val username = arguments?.getString(ARG_USERNAME).toString()

        val observeData = if (position == 1) detailViewModel.getUserFollowers(username)
        else detailViewModel.getUserFollowing(username)

        observeData.observe(viewLifecycleOwner) { showData(it) }
    }

    override fun onResume() {
        super.onResume()
        setRecyclerViewHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onPause() {
        super.onPause()
        setRecyclerViewHeight(0)
    }

    private fun showData(data: ItemState<List<ItemUser>>?) {
        when (data) {
            is ItemState.Loading -> stateLoading(true)
            is ItemState.Success -> {
                stateLoading(false)
                if (data.data.isNullOrEmpty()) stateLoading(true)
                else {
                    stateLoading(false)
                    adapter.submitList(data.data)
                    adapter.onItemClick ={ selectedData ->
                        val intent = Intent(requireActivity(), DetailActivity::class.java)
                        intent.putExtra("User Selected", selectedData.login)
                        startActivity(intent)
                    }
                }
            }
            is ItemState.Error -> stateLoading(false)
            null -> TODO()
        }
    }

    private fun setAdapter() {
        adapter = ItemUserAdapter()

        binding.rvUser.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(false)
            adapter = this@FollowFragment.adapter
        }
    }

    private fun setRecyclerViewHeight(height: Int) {
        binding.rvUser.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, height
        )
    }

    private fun stateLoading(isLoading: Boolean) {
        binding.loading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }
}
