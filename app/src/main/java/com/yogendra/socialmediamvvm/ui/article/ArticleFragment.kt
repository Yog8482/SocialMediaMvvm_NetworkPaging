package com.yogendra.socialmediamvvm.ui.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.yogendra.socialmediamvvm.R
import com.yogendra.socialmediamvvm.ui.users.UsersFragmentDirections

class ArticleFragment : Fragment() {

    private lateinit var articleViewModel: ArticleViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        articleViewModel =
            ViewModelProviders.of(this).get(ArticleViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_article, container, false)
        val textView: TextView = root.findViewById(R.id.text_article_empty)
        articleViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        val directions = ArticleFragmentDirections.actionNavigationArticleToNavigationProfile("")
        textView.setOnClickListener { v ->
            Navigation.findNavController(root).navigate(
                directions
            )
        }
        return root
    }
}