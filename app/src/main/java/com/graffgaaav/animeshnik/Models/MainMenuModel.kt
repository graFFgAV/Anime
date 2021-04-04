package com.graffgaaav.animeshnik.Models

import com.graffgaaav.animeshnik.R

data class MainMenuModel(
    val name: String,
    val image: Int
)

val mainMenu: Array<MainMenuModel> = arrayOf(
    MainMenuModel("movie", R.drawable.movie),
    MainMenuModel("tv", R.drawable.tv),
    MainMenuModel("airing", R.drawable.airing),
    MainMenuModel("upcoming", R.drawable.upcom),
    MainMenuModel("manga", R.drawable.zaebok)
)


