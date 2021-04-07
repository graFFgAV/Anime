package com.graffgaaav.animeshnik.Fragments

import android.annotation.SuppressLint
import android.icu.text.Transliterator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.graffgaaav.animeshnik.Adapters.FavoriteAdapter
import com.graffgaaav.animeshnik.DataBase.AppDatabase
import com.graffgaaav.animeshnik.Models.FavoriteModel
import com.graffgaaav.animeshnik.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class FavoriteFragment : Fragment(), FavoriteAdapter.Clicker, FavoriteAdapter.longClicker {

    private var favAdapter: FavoriteAdapter?= null

    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_favorite, container, false)

        val recyclerView = v.findViewById<RecyclerView>(R.id.rcvFavorite)
        favAdapter = FavoriteAdapter(arrayListOf(), this, requireContext(), this)
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = favAdapter

        AppDatabase.getDataBase(requireContext()).dao().all
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { listCars->
                favAdapter!!.addFav(listCars)
            }

        return v
    }

    override fun OnClick(company: FavoriteModel) {
        val bundle = Bundle()
        bundle.putString("id", company.mal_id.toString())
        bundle.putString("name", company.title)
        bundle.putString("image_url", company.image_url)
        findNavController().navigate(R.id.detailsFragment, bundle)
    }

    override fun onItemLongClicked(company: FavoriteModel, position: Int): Boolean {
        AppDatabase.getDataBase(requireContext()).dao().delete(company)
        favAdapter?.removeItem(position)
        Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
        return true
    }
}