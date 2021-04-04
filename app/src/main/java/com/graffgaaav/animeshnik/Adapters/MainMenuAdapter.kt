package com.graffgaaav.animeshnik.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.graffgaaav.animeshnik.Models.MainMenuModel
import com.graffgaaav.animeshnik.R

class MainMenuAdapter(val companies: Array<MainMenuModel>, var listener: Clicker): RecyclerView.Adapter<MainMenuAdapter.ViewHolder>() {


    interface Clicker{
        fun OnClick(mainMenuModel: MainMenuModel)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.menu_item, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  companies.size
    }

    @SuppressLint("SetTextI18n", "DefaultLocale")
    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {

        p0.insertAddons(companies[p1], listener)
        p0.name?.text = companies[p1].name
        p0.imageLogo?.setImageResource(companies[p1].image)

        p0.cardView?.setOnClickListener { listener.OnClick(companies[p1]) }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var name: TextView? = null
        var cardView: CardView? = null
        var imageLogo: ImageView? = null
        @SuppressLint("NewApi", "WrongViewCast")
        fun insertAddons(companies: MainMenuModel, listener: Clicker){
            name = itemView.findViewById(R.id.menuName) as TextView
            cardView = itemView.findViewById(R.id.cardMenu) as CardView
            imageLogo = itemView.findViewById(R.id.imageMenu) as ImageView
        }
    }
}


