package com.yogendra.socialmediamvvm.binding

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.yogendra.socialmediamvvm.data.Articles
import com.yogendra.socialmediamvvm.databinding.ArticleItemBinding
import com.yogendra.socialmediamvvm.ui.article.ArticleFragmentDirections

/**
 * Adapter for the [RecyclerView] in [ArticleFragment].
 */
class ArticlesAdapter :
    PagedListAdapter<Articles, ArticlesAdapter.ViewHolder>(ArticlesDiffCallback()) {
    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: ArticleItemBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ArticleItemBinding.inflate(
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


    class ViewHolder(private val binding: ArticleItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Articles) {
            binding.apply {
                binding.articleModel = item

                binding.clickListener = View.OnClickListener { v ->
                    when (v) {
                        binding.articleUrlTv -> openUrl(v.context, item.media.get(0).url)
                        else -> navigateToProfile(v, item.id)
                    }
                }
                executePendingBindings()
            }
        }
    }


}


fun navigateToProfile(view: View, articleId: Int) {
    val directions =
        ArticleFragmentDirections.actionNavigationArticleToNavigationProfile("$articleId")

    view.findNavController().navigate(
        directions
    )
}

fun openUrl(context: Context, url: String) {

    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(intent)

}

private class ArticlesDiffCallback : DiffUtil.ItemCallback<Articles>() {

    override fun areItemsTheSame(oldItem: Articles, newItem: Articles): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Articles, newItem: Articles): Boolean {
        return oldItem == newItem
    }
}