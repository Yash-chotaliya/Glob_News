package com.example.globnews

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.globnews.databinding.RvlayoutBinding
import com.example.globnews.retrofit.Articles

class NewsAdapter(private val context: Context, private val article : List<Articles>):RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RvlayoutBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.newsTitle.text = article[position].title
        holder.newsDesc.text = article[position].description
        Glide.with(context).load(article[position].urlToImage).into(holder.newsImg)
        holder.itemView.setOnClickListener{
            val intent = Intent(context,DetailNews::class.java)
            intent.putExtra("URL",article[position].url)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return article.size
    }

    class ViewHolder(itemBinding: RvlayoutBinding): RecyclerView.ViewHolder(itemBinding.root) {
        val newsImg : ImageView= itemBinding.newsImg
        val newsTitle:TextView = itemBinding.newsTitle
        val newsDesc:TextView = itemBinding.newsDesc
    }
}