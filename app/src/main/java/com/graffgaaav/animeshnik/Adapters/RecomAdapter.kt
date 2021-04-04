package com.graffgaaav.animeshnik.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.graffgaaav.animeshnik.Models.AnimeRecommendation
import com.graffgaaav.animeshnik.R



class RecomAdapter(val companies: ArrayList<AnimeRecommendation>, var listener: Clicker, var context: Context): RecyclerView.Adapter<RecomAdapter.ViewHolder>() {


    interface Clicker{
        fun OnClick(company: AnimeRecommendation)
    }



    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.top_movie_item, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  companies.size
    }

    @SuppressLint("SetTextI18n", "DefaultLocale")
    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {


        p0.insertAddons(companies[p1], listener)
        p0.name?.text = companies[p1].title

//        Picasso.get()
//            .load(companies[p1].image_url)
//            .error(R.drawable.ic_launcher_background)
//            .resize(180, 180).centerCrop()
//            .into(p0.imageLogo)

        p0.imageLogo?.let { Glide.with(context).load(companies[p1].image_url).into(it) }

        p0.cardView?.setOnClickListener { listener.OnClick(companies[p1]) }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var name: TextView? = null
        var cardView: CardView? = null
        var imageLogo: ImageView? = null
        @SuppressLint("NewApi", "WrongViewCast")
        fun insertAddons(companies: AnimeRecommendation, listener: Clicker){
            name = itemView.findViewById(R.id.tvName) as TextView
            cardView = itemView.findViewById(R.id.cardViewCategory) as CardView
            imageLogo = itemView.findViewById(R.id.ivIconCategory) as ImageView
        }
    }
}
