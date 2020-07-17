package com.yogendra.socialmediamvvm.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import com.yogendra.socialmediamvvm.R

class UsersFragment : Fragment() {

    private lateinit var usersViewModel: UsersViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        usersViewModel =
            ViewModelProviders.of(this).get(UsersViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_users, container, false)
        val textView: TextView = root.findViewById(R.id.text_users_empty)
        usersViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        val directions = UsersFragmentDirections.actionNavigationUsersToNavigationProfile("${usersViewModel.userID}")
        textView.setOnClickListener { v ->
            findNavController(root).navigate(
                directions
            )
        }

        return root
    }
}