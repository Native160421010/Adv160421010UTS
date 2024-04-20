package com.wildfire.adv160421010uts.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.wildfire.adv160421010uts.databinding.NewsListItemBinding
import com.wildfire.adv160421010uts.model.News

class NewsListAdapter(val newsList:ArrayList<News>)
    : RecyclerView.Adapter<NewsListAdapter.NewsViewHolder>()
{
    class NewsViewHolder(var binding: NewsListItemBinding)
        :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = NewsListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)

    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.binding.txtTitle.text = newsList[position].title
        holder.binding.txtDesc.text = newsList[position].desc
        holder.binding.txtCreator.text = newsList[position].creator

        val id: Int = newsList[position].id ?: 0

        holder.binding.btnRead.setOnClickListener {
            val action = MainFragmentDirections.actionDetailFragment(id)
            Navigation.findNavController(it).navigate(action)
        }
    }

    fun updateStudentList(newStudentList: ArrayList<News>) {
        newsList.clear()
        newsList.addAll(newStudentList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

}
