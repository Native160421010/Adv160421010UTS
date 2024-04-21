package com.wildfire.adv160421010uts.viewmodel

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.wildfire.adv160421010uts.model.User

class ProfilViewModel(application: Application) : AndroidViewModel(application) {
    val userLD = MutableLiveData<User>()
    private val requestQueue: RequestQueue = Volley.newRequestQueue(application)

    fun fetch(username: String) {
        val url = "http://10.0.2.2/news/getUser.php?nama=$username"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                Log.d(TAG, "JSON Response: $response")

                val gson = Gson()
                val dataArray = response.getJSONArray("data")
                val user = gson.fromJson(dataArray.getJSONObject(0).toString(), User::class.java)
                userLD.value = user
            },
            { error ->
                error.printStackTrace()
            }
        )

        requestQueue.add(jsonObjectRequest)
    }
}
