package com.graffgaaav.animeshnik.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.graffgaaav.animeshnik.Models.FavoriteModel
import com.graffgaaav.animeshnik.R

/**
 * Created by ThinkSoft on 31/12/2017.
 */
class FavoriteAdapter(cars:ArrayList<FavoriteModel>, var listener: Clicker, var context: Context, var longListener: longClicker): RecyclerView.Adapter<FavoriteAdapter.RecyclerViewHolder>() {

    private var listCars: List<FavoriteModel> = cars

    interface Clicker{
        fun OnClick(company: FavoriteModel)
    }

    interface longClicker{
        fun onItemLongClicked(company: FavoriteModel, position: Int): Boolean
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.top_movie_item,parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val current: FavoriteModel = listCars[position]

        holder.mName.text= current.title

        holder.mImage?.let { Glide.with(context).load(current.image_url).into(it) }

        holder.cardView.setOnClickListener { listener.OnClick(current) }
        holder.cardView.setOnLongClickListener { longListener.onItemLongClicked(current, position) }
    }

    override fun getItemCount(): Int {
        return listCars.size
    }

    fun removeItem(position: Int) {
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, listCars.size)
    }

    fun addFav( listCars : List<FavoriteModel>){
        this.listCars = listCars
        //the most important here
        notifyDataSetChanged()
    }

    class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cardView = itemView.findViewById(R.id.cardViewCategory) as CardView
        var mName = itemView.findViewById<TextView>(R.id.tvName)
        var mImage = itemView.findViewById<ImageView>(R.id.ivIconCategory)

    }

}