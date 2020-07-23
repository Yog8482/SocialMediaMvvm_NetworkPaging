package com.yogendra.socialmediamvvm.ui.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.yogendra.socialmediamvvm.data.Result
import com.yogendra.socialmediamvvm.databinding.FragmentProfileBinding
import com.yogendra.socialmediamvvm.di.Injectable
import com.yogendra.socialmediamvvm.di.injectViewModel
import com.yogendra.socialmediamvvm.utils.ui_components.MultilineSnackbar
import javax.inject.Inject

class ProfileFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var binding: FragmentProfileBinding

    private val args: ProfileFragmentArgs by navArgs()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileViewModel = injectViewModel(viewModelFactory)

        binding = FragmentProfileBinding.inflate(inflater, container, false)
        context ?: return binding.root

        if (args.articleId != null) {
            profileViewModel.getArticle_profile(args.articleId!!)
        }
        if (args.userId != null) {
            profileViewModel.getUserProfile(args.userId!!)
        }

        subscribeUi(binding)

        return binding.root
    }


    private fun subscribeUi(binding: FragmentProfileBinding) {
        profileViewModel.article_profile.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
//                    binding.progressBar.visibility = View.GONE
                    binding.hasData = false


                    result.data?.let {
                        binding.profileModel = it.user[0]
                        binding.hasData = it.user.isNotEmpty()
                    }
                }
//                Result.Status.LOADING -> binding.progressBar.visibility = View.VISIBLE

                Result.Status.ERROR -> {
//                    binding.progressBar.visibility = View.GONE

                    binding.hasData = false
                    MultilineSnackbar(
                        binding.root,
                        result.message!!
                    ).show()
                }
            }
        })

        profileViewModel.user_profile.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
//                    binding.progressBar.visibility = View.GONE
                    binding.hasData = false


                    result.data?.let {
                        binding.profileModel = it
                        binding.hasData = it.name.length > 0
                    }
                }
//                Result.Status.LOADING -> binding.progressBar.visibility = View.VISIBLE

                Result.Status.ERROR -> {
//                    binding.progressBar.visibility = View.GONE

                    binding.hasData = false
                    MultilineSnackbar(
                        binding.root,
                        result.message!!
                    ).show()
                }
            }
        })
    }
}