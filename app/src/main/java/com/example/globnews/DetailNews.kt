package com.example.globnews

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.globnews.databinding.ActivityDetailNewsBinding

class DetailNews : AppCompatActivity() {

    private lateinit var binding:ActivityDetailNewsBinding
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val url = intent.getStringExtra("URL")
        if(url!=null){
            binding.webView.settings.javaScriptEnabled = true
            binding.webView.webViewClient = object : WebViewClient(){
                override fun onPageFinished(view: WebView?, url: String?) {
                    binding.progressBar.visibility = View.GONE
                    binding.webView.visibility = View.VISIBLE
                    super.onPageFinished(view, url)
                }
            }
            binding.webView.loadUrl(url)
        }
    }
}