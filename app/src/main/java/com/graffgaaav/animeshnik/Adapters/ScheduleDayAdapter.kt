package com.graffgaaav.animeshnik.Adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.graffgaaav.animeshnik.Fragments.MainFragment
import com.graffgaaav.animeshnik.Models.TopMovie
import com.graffgaaav.animeshnik.R

class ScheduleDayAdapter (var list: List<TopMovie>)
    : RecyclerView.Adapter<ScheduleDayAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.top_movie_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(list[position])

    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val nameView = itemView.findViewById<TextView>(R.id.tvName)
        private val imageView = itemView.findViewById<ImageView>(R.id.ivIconCategory)

        fun bind(anime: TopMovie) {
            nameView.text = anime.title

            Glide.with(itemView).load(anime.image_url).into(imageView)

            //rating.text = anime.score.toString()

            itemView.setOnClickListener {

                val bundle = Bundle()
                bundle.putString("name", anime.title)
                bundle.putString("image_url", anime.image_url)
                bundle.putString("id", anime.mal_id.toString())
                itemView.findNavController().navigate(R.id.detailsFragment, bundle)

            }
        }
    }
}