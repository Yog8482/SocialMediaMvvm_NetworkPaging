package com.yogendra.socialmediamvvm.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.yogendra.socialmediamvvm.data.Articles
import com.yogendra.socialmediamvvm.databinding.ArticleRowBinding
import com.yogendra.socialmediamvvm.ui.article.ArticleFragmentDirections
import java.lang.Exception

/**
 * Adapter for the [RecyclerView] in [ArticleFragment].
 */
class ArticlesAdapter :
    PagedListAdapter<Articles, ArticlesAdapter.ViewHolder>(
        ArticlesDiffCallback()
    ) {
    private lateinit var recyclerView: RecyclerView


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ArticleRowBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.apply { bind(it) } }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }


    class ViewHolder(private val binding: ArticleRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Articles) {
            binding.apply {
                binding.articleModel = item

                binding.clickListener = View.OnClickListener { v ->
                    when (v) {
                        binding.articleUrlTv -> openUrl(
                            v.context,
                            item.media!![0].url
                        )
                        else -> navigateToProfile(
                            v,
                            item.id
                        )
                    }
                }
                try {
                    executePendingBindings()
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }
        }
    }


}


fun navigateToProfile(view: View, articleId: String) {
    val directions =
        ArticleFragmentDirections.actionNavigationArticleToNavigationProfile(articleId,null)

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