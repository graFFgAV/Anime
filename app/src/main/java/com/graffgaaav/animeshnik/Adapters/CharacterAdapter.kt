package com.graffgaaav.animeshnik.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.graffgaaav.animeshnik.Models.Character
import com.graffgaaav.animeshnik.R



class CharacterAdapter(val companies: ArrayList<Character>, var context: Context): RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.character_item, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  companies.size
    }

    @SuppressLint("SetTextI18n", "DefaultLocale")
    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {


        p0.insertAddons(companies[p1])
        p0.name?.text = companies[p1].name
        p0.role?.text = companies[p1].role

//        Picasso.get()
//            .load(companies[p1].image_url)
//            .error(R.drawable.ic_launcher_background)
//            .resize(180, 180).centerCrop()
//            .into(p0.imageLogo)
        p0.imageLogo?.let { Glide.with(context).load(companies[p1].image_url).into(it) }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var role: TextView? = null
        var name: TextView? = null
        var imageLogo: ImageView? = null
        @SuppressLint("NewApi", "WrongViewCast")
        fun insertAddons(companies: Character){
            role = itemView.findViewById(R.id.roleCharacter) as TextView
            name = itemView.findViewById(R.id.characterName) as TextView
            imageLogo = itemView.findViewById(R.id.imageViewChar) as ImageView
        }
    }
}
