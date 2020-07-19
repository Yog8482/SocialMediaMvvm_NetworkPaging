package com.yogendra.socialmediamvvm.ui.article

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
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.yogendra.socialmediamvvm.R
import com.yogendra.socialmediamvvm.binding.ArticlesAdapter
import com.yogendra.socialmediamvvm.databinding.FragmentArticleBinding
import com.yogendra.socialmediamvvm.di.Injectable
import com.yogendra.socialmediamvvm.di.injectViewModel
import com.yogendra.socialmediamvvm.ui.users.UsersFragmentDirections
import javax.inject.Inject

class ArticleFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var articleViewModel: ArticleViewModel
    private val adapter: ArticlesAdapter by lazy { ArticlesAdapter() }
    private lateinit var binding: FragmentArticleBinding


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
        binding.swipeRefresh.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener { articleViewModel.refresh() })
        binding.articleRecyclerList.adapter = adapter
        subscribeUi(adapter)

        return binding.root
    }

    private fun subscribeUi(adapter: ArticlesAdapter) {
        binding.swipeRefresh.isRefreshing = true

        articleViewModel.articles.observe(viewLifecycleOwner) {
            binding.swipeRefresh.isRefreshing = false
            adapter.submitList(it)
        }
    }

}