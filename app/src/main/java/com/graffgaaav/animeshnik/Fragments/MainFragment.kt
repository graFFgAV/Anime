package com.graffgaaav.animeshnik.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.graffgaaav.animeshnik.Adapters.MainMenuAdapter
import com.graffgaaav.animeshnik.Adapters.ScheduleAdapter
import com.graffgaaav.animeshnik.MainActivity
import com.graffgaaav.animeshnik.Models.*
import com.graffgaaav.animeshnik.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_main.*



class MainFragment : Fragment(), MainMenuAdapter.Clicker{

    private lateinit var toolbar: ActionBar
    var mCompositeDisposable: CompositeDisposable? = CompositeDisposable()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_main, container, false)

        val rcvMain = v.findViewById<RecyclerView>(R.id.rcvMain)


        toolbar = (activity as AppCompatActivity?)!!.supportActionBar!!
        toolbar.title = "Anime menu"
        toolbar.displayOptions

        val adapter = MainMenuAdapter(mainMenu, this)
        rcvMain.layoutManager = LinearLayoutManager(context)
        rcvMain.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        rcvMain.adapter = adapter

        loadSchedule()
        return v
    }

    private fun loadSchedule() {
        mCompositeDisposable?.add(
            RetrofitApi.getRetrofit()!!.getSchedule()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::loadRecyclerView, this::initError)
        )
    }

    private fun loadRecyclerView(companies: ScheduleResponse){
        loader.visibility = View.GONE
        val adapter = context?.let { ScheduleAdapter(companies) }
        rcvSchedule.layoutManager =  LinearLayoutManager(context,
            LinearLayoutManager.VERTICAL, false)

        rcvSchedule.adapter = adapter
    }

    private fun initError(error: Throwable){
        println(error)
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity?)!!.hideUpButton()
    }

    override fun OnClick(mainMenuModel: MainMenuModel) {
        val bundle = Bundle()
        bundle.putString("position", mainMenuModel.name)
        findNavController().navigate(R.id.topMovieFragment, bundle)
    }
}



