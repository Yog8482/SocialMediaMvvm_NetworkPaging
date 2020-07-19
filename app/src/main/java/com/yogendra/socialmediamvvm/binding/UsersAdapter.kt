package com.yogendra.socialmediamvvm.binding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.yogendra.socialmediamvvm.data.Users
import com.yogendra.socialmediamvvm.databinding.UserItemBinding
import com.yogendra.socialmediamvvm.ui.users.UsersFragmentDirections

class UsersAdapter : PagedListAdapter<Users, UsersAdapter.ViewHolder>(UsersDiffCallback()) {
    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: UserItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = UserItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(it) }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    class ViewHolder(private val binding: UserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Users) {
            binding.apply {
                binding.userModel = item

                binding.clickListener = View.OnClickListener { v ->
                    navigateUsersToProfile(v, item.id)
                }
                executePendingBindings()
            }
        }
    }

}

fun navigateUsersToProfile(view: View, userId: Int) {
    val directions =
        UsersFragmentDirections.actionNavigationUsersToNavigationProfile("$userId")

    view.findNavController().navigate(
        directions
    )
}

private class UsersDiffCallback : DiffUtil.ItemCallback<Users>() {

    override fun areItemsTheSame(oldItem: Users, newItem: Users): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Users, newItem: Users): Boolean {
        return oldItem == newItem
    }
}