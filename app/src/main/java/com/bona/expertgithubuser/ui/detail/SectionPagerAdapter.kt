package com.bona.expertgithubuser.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionPagerAdapter(
    activity: AppCompatActivity,
    private val username: String
) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return FollowFragment().apply {
            arguments = Bundle().apply {
                putInt(FollowFragment.ARG_POSITION, position + 1)
                putString(FollowFragment.ARG_USERNAME, username)
            }
        }
    }
}