package com.graffgaaav.animeshnik.Fragments

import RetrofitApi
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.graffgaaav.animeshnik.Adapters.MainMenuAdapter
import com.graffgaaav.animeshnik.Adapters.TopMovieAdapter
import com.graffgaaav.animeshnik.MainActivity
import com.graffgaaav.animeshnik.Models.TopMovie
import com.graffgaaav.animeshnik.Models.TopMovieResponse
import com.graffgaaav.animeshnik.Models.mainMenu
import com.graffgaaav.animeshnik.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_top_movie.*
import retrofit2.http.Path


class TopMovieFragment : Fragment(), TopMovieAdapter.Clicker{

    private lateinit var toolbar: ActionBar
    var mCompositeDisposable: CompositeDisposable? = CompositeDisposable()
    private var layoutManager: GridLayoutManager? = null

    var jopa = ""
    var subtype = "anime"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_top_movie, container, false)

        val position = requireArguments().getString("position")
        jopa = position!!

        if (position == "manga") subtype = "manga"

        loadCompany()

        toolbar = (activity as AppCompatActivity?)!!.supportActionBar!!
        toolbar.title = position

        return view
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity?)!!.showUpButton()
    }

    private fun loadCompany() {
        mCompositeDisposable?.add(
            RetrofitApi.getRetrofit()!!.getTopMovie(jopa, subtype)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::loadRecyclerView, this::initError)
        )
    }

    private fun loadRecyclerView(companies: TopMovieResponse){
        loader.visibility = View.GONE
        val adapter = context?.let { TopMovieAdapter(companies.top, this,  it) }
        layoutManager = GridLayoutManager(context, 3)
        rcv?.layoutManager = layoutManager
        rcv?.adapter = adapter
    }

    private fun initError(error: Throwable){
        println(error)
    }


    @SuppressLint("ShowToast")
    override fun OnClick(company: TopMovie) {

        if (subtype == "manga") Toast.makeText(context, "кто знает что с мангой придумать?${company.title}", Toast.LENGTH_LONG ).show()

        else{
        val bundle = Bundle()
        bundle.putString("id", company.mal_id.toString())
        bundle.putString("name", company.title)
        bundle.putString("image_url", company.image_url)

        findNavController().navigate(R.id.detailsFragment, bundle)
        }
    }
}



