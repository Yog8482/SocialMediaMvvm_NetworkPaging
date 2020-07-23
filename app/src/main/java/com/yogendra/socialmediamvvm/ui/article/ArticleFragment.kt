package com.yogendra.socialmediamvvm.ui.article

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import com.yogendra.socialmediamvvm.adapter.ArticlesAdapter
import com.yogendra.socialmediamvvm.data.Result
import com.yogendra.socialmediamvvm.databinding.FragmentArticleBinding
import com.yogendra.socialmediamvvm.di.Injectable
import com.yogendra.socialmediamvvm.di.injectViewModel
import com.yogendra.socialmediamvvm.utils.NoConnectivityException
import com.yogendra.socialmediamvvm.utils.ProgressStatus
import com.yogendra.socialmediamvvm.utils.ui_components.MultilineSnackbar
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class ArticleFragment : DaggerFragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var articleViewModel: ArticleViewModel

    private val adapter: ArticlesAdapter by lazy { ArticlesAdapter() }
    private lateinit var binding: FragmentArticleBinding
    val handler = Handler()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        articleViewModel = injectViewModel(viewModelFactory)

        binding = FragmentArticleBinding.inflate(inflater, container, false)
        context ?: return binding.root


        // add dividers between RecyclerView's row items
        val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        binding.articleRecyclerList.addItemDecoration(decoration)
        binding.progressbar.visibility = View.VISIBLE
        articleViewModel.refresh()



        binding.articleRecyclerList.adapter = adapter
        subscribeUi(adapter)

        return binding.root
    }

    private fun subscribeUi(adapter: ArticlesAdapter) {

        articleViewModel.articles.observe(viewLifecycleOwner) { result ->
            binding.progressbar.visibility = View.GONE

            adapter.submitList(result)
            binding.hasArticles = result.isNotEmpty()
        }



        articleViewModel.refresh().observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    binding.progressbar.visibility = View.GONE

                    result.data?.let { binding.hasArticles = result.data.isNotEmpty() }
                }
                Result.Status.LOADING -> binding.progressbar.visibility = View.VISIBLE

                Result.Status.ERROR -> {
                    binding.progressbar.visibility = View.GONE


                    MultilineSnackbar(
                        binding.root,
                        result.message!!
                    ).show()

                }
            }
        })

        articleViewModel.progressStatus.observe(viewLifecycleOwner, Observer { status ->

            when (status) {
                ProgressStatus.LOADING.toString() -> binding.progressbar.visibility = View.VISIBLE

                ProgressStatus.COMPLTED.toString() -> binding.progressbar.visibility = View.GONE

                ProgressStatus.ERROR.toString() -> binding.progressbar.visibility = View.GONE

                ProgressStatus.NO_NETWORK.toString() -> {
                    binding.progressbar.visibility = View.GONE

                    MultilineSnackbar(
                        binding.root,
                        NoConnectivityException().message.toString()
                    ).show()
                }

            }

        })

    }


}