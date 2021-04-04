package com.graffgaaav.animeshnik


import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.material.navigation.NavigationView
import com.mxn.soul.flowingdrawer_core.ElasticDrawer
import com.mxn.soul.flowingdrawer_core.FlowingDrawer


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var  searchview : androidx.appcompat.widget.SearchView
    private lateinit var mDrawer: FlowingDrawer
    private lateinit var keyboard : InputMethodManager

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mDrawer = findViewById<FlowingDrawer>(R.id.drawerlayout)
        mDrawer.setTouchMode(ElasticDrawer.TOUCH_MODE_BEZEL)

        supportActionBar!!.setBackgroundDrawable(getDrawable(R.drawable.gradient_red))
        supportActionBar!!.elevation = 0f

        keyboard = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        val vNavigation = findViewById<View>(R.id.vNavigation) as NavigationView
        vNavigation.setNavigationItemSelectedListener { menuItem ->

            val bundle = Bundle()

            when (menuItem.itemId){
                R.id.menu_top_movie ->  bundle.putString("position", "movie")
                R.id.menu_to_TV->  bundle.putString("position", "tv")
                R.id.menu_airing ->  bundle.putString("position", "airing")
                R.id.menu_upcoming ->  bundle.putString("position", "upcoming")
                R.id.menu_manga ->  bundle.putString("position", "manga")
            }
            navController = findNavController(R.id.my_nav_host_fragment)
            navController.navigate(R.id.topMovieFragment, bundle)

            true
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val menuItem = menu.findItem(R.id.action_search)

        searchview = menuItem.actionView as androidx.appcompat.widget.SearchView
        searchview.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val bundle = Bundle()
                bundle.putString("query", query)
                navController = findNavController(R.id.my_nav_host_fragment)
                navController.navigate(R.id.searchFragment, bundle)

//                keyboard.hideSoftInputFromWindow(
//                    this.getWindowToken(),
//                    InputMethodManager.HIDE_NOT_ALWAYS);

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true
    }


    fun showUpButton() {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    fun hideUpButton() {
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
    }

    override fun onBackPressed() {
        val backStackCount = supportFragmentManager.backStackEntryCount
        if (backStackCount >= 1) {
            supportFragmentManager.popBackStack()
            if (backStackCount == 1) {
                hideUpButton()
            }
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }
}