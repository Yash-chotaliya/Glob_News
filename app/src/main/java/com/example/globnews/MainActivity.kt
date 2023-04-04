package com.example.globnews

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.globnews.databinding.ActivityMainBinding
import com.example.globnews.retrofit.News
import com.example.globnews.retrofit.NewsInterfaceObj
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        getNews()

        setNavigationDrawer()

        binding.navView.setNavigationItemSelectedListener {

            when(it.itemId) {
                R.id.home -> getNews()
                R.id.business -> getNews("business")
                R.id.sports -> getNews("sports")
                R.id.entertainment -> getNews("entertainment")
            }
            if(binding.drawlay.isDrawerOpen(GravityCompat.START))
                binding.drawlay.closeDrawer(GravityCompat.START)
            return@setNavigationItemSelectedListener true
        }
    }
    private fun getNews(){
        val news = NewsInterfaceObj.newsInstance.getHeadlines("in",1)
        news.enqueue(object:retrofit2.Callback<News>{
            override fun onResponse(call: Call<News>, response: Response<News>) {
                val newss = response.body()
                if(newss!=null){
                    Log.d("yash",newss.articles.toString())
                    binding.rv.layoutManager = LinearLayoutManager(this@MainActivity)
                    binding.rv.adapter = NewsAdapter(this@MainActivity,newss.articles)
                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("yash","error in fetching data")
            }
        })
    }

    private fun getNews(category:String){
        val news = NewsInterfaceObj.newsInstance.getHeadlinesCategory("in",1,category)
        news.enqueue(object:retrofit2.Callback<News>{
            override fun onResponse(call: Call<News>, response: Response<News>) {
                val newss = response.body()
                if(newss!=null){
                    Log.d("yash",newss.articles.toString())
                    binding.rv.layoutManager = LinearLayoutManager(this@MainActivity)
                    binding.rv.adapter = NewsAdapter(this@MainActivity,newss.articles)
                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("yash","error in fetching data")
            }
        })
    }

    private fun setNavigationDrawer(){
        toggle = ActionBarDrawerToggle(this, binding.drawlay, R.string.nav_open, R.string.nav_close)

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        binding.drawlay.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (toggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }
}

