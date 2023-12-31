package com.ianindratama.searchgithubuserapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ianindratama.searchgithubuserapp.databinding.ItemRowUserBinding

class SearchDataAdapter(private val searchData: List<GithubUserData?>): RecyclerView.Adapter<SearchDataAdapter.ViewHolder>() {

    class ViewHolder(var binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(holder.itemView.context)
            .load(searchData[position]?.avatarUrl)
            .circleCrop()
            .into(holder.binding.itemPhoto)
        holder.binding.itemUsername.text = searchData[position]?.username
        holder.binding.itemUrl.text = searchData[position]?.url

    }

    override fun getItemCount(): Int = searchData.size


}