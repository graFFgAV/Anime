package com.graffgaaav.animeshnik.Fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.graffgaaav.animeshnik.Adapters.TopMovieAdapter
import com.graffgaaav.animeshnik.MainActivity
import com.graffgaaav.animeshnik.Models.SearchResponse
import com.graffgaaav.animeshnik.Models.TopMovie
import com.graffgaaav.animeshnik.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.loader



class SearchFragment : Fragment(), TopMovieAdapter.Clicker {

    private lateinit var  searchview : SearchView
    var mCompositeDisposable: CompositeDisposable? = CompositeDisposable()
    private var layoutManager: GridLayoutManager? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_search, container, false)


        val toolbar = (activity as AppCompatActivity?)!!.supportActionBar!!
        toolbar.title = "Search"

        val query = requireArguments().getString("query")

        loadResults(query!!)

        return v
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity?)!!.showUpButton()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
        val menuItem = menu.findItem(R.id.action_search)

        searchview = menuItem.actionView as SearchView

        searchview.queryHint = "search"

        searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {

                loadResults(query.toString())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun loadResults(query: String) {
        mCompositeDisposable?.add(
            RetrofitApi.getRetrofit()!!.getSearchResult(query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::loadRecyclerView, this::initError)
        )
    }

    private fun loadRecyclerView(companies: SearchResponse){
        loader.visibility = View.GONE
        val adapter = context?.let { TopMovieAdapter(companies.results, this, it) }
        layoutManager = GridLayoutManager(context, 3)
        rcvSearch?.layoutManager = layoutManager
        rcvSearch?.adapter = adapter
    }

    private fun initError(error: Throwable){
        println(error)
    }

    override fun OnClick(company: TopMovie) {
        val bundle = Bundle()
        bundle.putString("id", company.mal_id.toString())
        bundle.putString("name", company.title)
        bundle.putString("image_url", company.image_url)

        findNavController().navigate(R.id.detailsFragment, bundle)
    }
}