package com.graffgaaav.animeshnik.Fragments

import RetrofitApi
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.graffgaaav.animeshnik.Adapters.CharacterAdapter
import com.graffgaaav.animeshnik.Adapters.RecomAdapter
import com.graffgaaav.animeshnik.DataBase.AppDatabase
import com.graffgaaav.animeshnik.MainActivity
import com.graffgaaav.animeshnik.Models.*
import com.graffgaaav.animeshnik.R
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.detail_content.*
import kotlinx.android.synthetic.main.fragment_details.*
import java.util.concurrent.TimeUnit


class DetailsFragment : Fragment(), RecomAdapter.Clicker {

    private lateinit var toolbar: ActionBar
    private var mCompositeDisposable: CompositeDisposable? = CompositeDisposable()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v =  inflater.inflate(R.layout.fragment_details, container, false)

        val name = requireArguments().getString("name")

        toolbar = (activity as AppCompatActivity?)!!.supportActionBar!!
        toolbar.title = name

        var button_fav = v.findViewById<Button>(R.id.button_fav)

        button_fav.setOnClickListener {
            addToFavorite(name, requireArguments().getString("image_url"))
        }

        loadDetails()
        loadRecom()
        loadChar()
        return v
    }

    @SuppressLint("CheckResult")
    private fun addToFavorite(name: String?, image: String?) {

        val fm = FavoriteModel(requireArguments().getString("id")?.toInt()!!, name!!, image!!)

        Single.fromCallable {
            AppDatabase.getDataBase(requireContext()).dao().insert(fm)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(this::showToast, this::showError)
    }

    fun showToast(unit: Unit) {
        Toast.makeText(context, "success", Toast.LENGTH_SHORT).show()
    }

    fun showError(error: Throwable){
        Toast.makeText(context, "Уже добавлено, чо тыкаешь", Toast.LENGTH_SHORT).show()
    }
    
    override fun onResume() {
        super.onResume()
        (activity as MainActivity?)!!.showUpButton()
    }

    private fun loadDetails() {
        mCompositeDisposable!!.add(
            (RetrofitApi.getRetrofit())!!.getAnimeDetail(
                requireArguments().getString("id")?.toInt()!!
            )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::setDetails, this::initError)
        )
    }

    private fun loadRecom() {
        mCompositeDisposable!!.add(
            (RetrofitApi.getRetrofit())!!.getAnimeRecommendations(
                requireArguments().getString("id")?.toInt()!!
            )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::setRecom, this::initError)
        )
    }

    private fun loadChar() {
        mCompositeDisposable!!.add(
            (RetrofitApi.getRetrofit())!!.getAnimeCharacters(
                requireArguments().getString("id")?.toInt()!!
            )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::setChars, this::initError)
        )
    }

    fun setChars(chars: CharactersResponse){
        val adapter = context?.let { CharacterAdapter(chars.characters, it) }
        characterRcv?.layoutManager = LinearLayoutManager(context)
        characterRcv?.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        characterRcv?.adapter = adapter
    }


    fun setRecom(rec: RecommendationsResponse) {
        val adapter = context?.let { RecomAdapter(rec.recommendations, this, it) }
        recomRcv?.layoutManager = LinearLayoutManager(context)
        recomRcv?.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        recomRcv?.adapter = adapter
    }


    @SuppressLint("SetTextI18n", "InflateParams")
    private fun setDetails(details: AnimeDetails){
        loaderDetails.visibility = View.GONE
        content.visibility = View.VISIBLE

        Glide.with(requireContext()).load(requireArguments().getString("image_url")).into(
            image_detail
        )
        title_detail.text = details.title
        type_detail.text = "Type: ${details.type}"
        amount_episodes_detail.text = "Kол-во серий: ${details.episodes.toString()}"
        dlitelnost_detail.text = "Длительность:\n${details.duration}"

        score.text = "${details.score.toString()}/10"
        text_detail.text = details.synopsis
        score_by.text = details.scored_by.toString()
        
        val genres = details.genres
        val inflater = LayoutInflater.from(context)
        for(genre in genres!!){
            val chip = inflater.inflate(R.layout.genre_chip_item, null, false) as Chip
            chip.text = genre.name
            anime_details_genres.addView(chip)
        }
    }

    private fun initError(error: Throwable){
        println(error)
    }

    override fun OnClick(company: AnimeRecommendation) {
        val bundle = Bundle()
        bundle.putString("id", company.mal_id.toString())
        bundle.putString("name", company.title)
        bundle.putString("image_url", company.image_url)

        findNavController().navigate(R.id.detailsFragment, bundle)

    }
}




