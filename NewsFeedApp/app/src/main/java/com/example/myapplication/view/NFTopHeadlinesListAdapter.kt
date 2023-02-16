package com.example.myapplication.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.model.http.services.topheadlines.NFArticlesItem
import com.example.myapplication.model.http.services.topheadlines.NFTopHeadlines

class NFTopHeadlinesListAdapter(
    private val topHeadlines: NFTopHeadlines,
    private val onNewsClickListener: NFOnNewsItemClickListener
) :
    RecyclerView.Adapter<NFTopHeadlinesListAdapter.NFHeadlinesViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NFHeadlinesViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.top_headlines_item_view_template, parent, false)

        return NFHeadlinesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NFHeadlinesViewHolder, position: Int) {
        topHeadlines.articles?.get(position).apply {
            holder.itemView.setOnClickListener {
                onNewsClickListener.onClick(this)
            }
            holder.bind(this)
        }

    }

    override fun getItemCount(): Int = topHeadlines.articles?.size ?: 0

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    class NFHeadlinesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var titleTv: TextView = itemView.findViewById(R.id.titleTv)
        private var sourceTv: TextView = itemView.findViewById(R.id.sourceTv)
        private var imageView: ImageView = itemView.findViewById(R.id.image)
        fun bind(article: NFArticlesItem?) {
            article?.apply {
                titleTv.text = title
                sourceTv.apply {
                    text =
                        "${itemView.context.getString(R.string.source)}: ${source.name}".toString()
                }
                //bind image view
                Glide.with(itemView.context)
                    .load(urlToImage)
                    .into(imageView)
            }
        }

    }

    interface NFOnNewsItemClickListener {
        fun onClick(article: NFArticlesItem?)

    }
}