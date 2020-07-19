package com.yogendra.socialmediamvvm.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.yogendra.socialmediamvvm.R
import com.yogendra.socialmediamvvm.binding.ArticlesAdapter
import com.yogendra.socialmediamvvm.binding.UsersAdapter
import com.yogendra.socialmediamvvm.data.Result
import com.yogendra.socialmediamvvm.databinding.FragmentArticleBinding
import com.yogendra.socialmediamvvm.databinding.FragmentUsersBinding
import com.yogendra.socialmediamvvm.di.Injectable
import com.yogendra.socialmediamvvm.di.injectViewModel
import com.yogendra.socialmediamvvm.ui.article.ArticleViewModel
import javax.inject.Inject

class UsersFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val adapter: UsersAdapter by lazy { UsersAdapter() }
    private lateinit var binding: FragmentUsersBinding
    private lateinit var usersViewModel: UsersViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        usersViewModel = injectViewModel(viewModelFactory)

        binding = FragmentUsersBinding.inflate(inflater, container, false)
        context ?: return binding.root

        binding.swipeRefresh.setOnRefreshListener({ usersViewModel.refresh() })
        binding.usersListRecyclerview.adapter = adapter
        subscribeUi(adapter)

        return binding.root
    }


    private fun subscribeUi(adapter: UsersAdapter) {
        binding.swipeRefresh.isRefreshing = true

        usersViewModel.users.observe(viewLifecycleOwner) {
            binding.swipeRefresh.isRefreshing = false
            adapter.submitList(it)
        }


        usersViewModel.refreshUsers.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    binding.swipeRefresh.isRefreshing = false
                    result.data?.let { binding.hasUsers = true }
                }
                Result.Status.LOADING -> binding.swipeRefresh.isRefreshing = true
                Result.Status.ERROR -> {
                    binding.swipeRefresh.isRefreshing = false

                    Snackbar.make(binding.root, result.message!!, Snackbar.LENGTH_LONG)
                        .show()
                }
            }
        })
    }
}