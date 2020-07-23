package com.yogendra.socialmediamvvm.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.yogendra.socialmediamvvm.adapter.UsersAdapter
import com.yogendra.socialmediamvvm.data.Result
import com.yogendra.socialmediamvvm.data.Users
import com.yogendra.socialmediamvvm.databinding.FragmentUsersBinding
import com.yogendra.socialmediamvvm.di.Injectable
import com.yogendra.socialmediamvvm.di.injectViewModel
import com.yogendra.socialmediamvvm.utils.ui_components.MultilineSnackbar
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

        binding.progressbar.visibility = View.VISIBLE
        usersViewModel.refresh()

        binding.usersListRecyclerview.adapter = adapter
        subscribeUi(adapter)

        return binding.root
    }


    private fun subscribeUi(adapter: UsersAdapter) {


        usersViewModel.users.observe(viewLifecycleOwner) {
            binding.progressbar.visibility = View.GONE

            adapter.submitList(it)
            binding.hasUsers = it.isNotEmpty()

        }


        usersViewModel.refresh().observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    binding.progressbar.visibility = View.GONE

                    result.data?.let { binding.hasUsers = result.data.isNotEmpty() }
                }
                Result.Status.LOADING -> binding.progressbar.visibility = View.VISIBLE

                Result.Status.ERROR -> {
                    binding.progressbar.visibility=View.GONE


                    MultilineSnackbar(
                        binding.root,
                        result.message!!
                    ).show()

                }
            }
        })
    }
}