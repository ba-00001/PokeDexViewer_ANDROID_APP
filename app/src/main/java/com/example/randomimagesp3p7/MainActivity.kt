package com.example.randomimagesp3p7

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.example.randomimagesp3p7.R.id.api_list
import okhttp3.Headers

class MainActivity : AppCompatActivity() {


    private lateinit var apiList: MutableList<String>
    private lateinit var rvAPIs: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        rvAPIs = findViewById(api_list)
        apiList = mutableListOf()

        getApiImageURL()
    }

    private fun getApiImageURL() {
        val client = AsyncHttpClient()

        client["https://pokeapi.co/api/v2/pokemon?limit=151", object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON?) {
                val response = json?.jsonObject
                if (response != null) {
                    val resultsArray = response.getJSONArray("results")
                    for (i in 0 until resultsArray.length()) {
                        val pokemonObject = resultsArray.getJSONObject(i)
                        val name = pokemonObject.getString("name")
                        val url = pokemonObject.getString("url")
                        apiList.add("Name: $name, URL: $url")
                    }

                    val adapter = ApiAdapter(apiList)
                    rvAPIs.adapter = adapter
                    rvAPIs.layoutManager = LinearLayoutManager(this@MainActivity)
                    rvAPIs.addItemDecoration(DividerItemDecoration(this@MainActivity, LinearLayoutManager.VERTICAL))
                } else {
                    Log.e("API Error", "Invalid response format")
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("API Error", errorResponse)
            }
        }]
    }
}
