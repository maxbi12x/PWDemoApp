package com.example.pwdemoapp

import android.R.attr
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.pwdemoapp.databinding.ActivityMainBinding
import com.google.android.material.shape.CornerFamily
import android.R.attr.radius
import android.annotation.SuppressLint
import android.util.Log
import android.widget.AbsListView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.google.android.material.shape.MaterialShapeDrawable
import retrofit.*


class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding?=null
    private var list : ArrayList<ProfileModel> = ArrayList()
    private var currentItems = 0
    private var totalItems =0
    private var scrollOutItems = 0
    private var isScrolling = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.backButton?.setOnClickListener {
            finish()

        }
        getProfile(object : ApiCallback {
            override fun onSuccess(result: ArrayList<ProfileModel>?) {
                Log.e("RESPONSE", result!!.toString())
                list = result
                setRV()
            }
        })
    }
    private fun getProfile(callbk: ApiCallback) {
        if (InternetCheck.isConnected(this)) {
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service: ProfileService =
                retrofit.create(ProfileService::class.java)
            val listCall: Call<ArrayList<ProfileModel>> =
                service.getService()
            listCall.enqueue(object : Callback<ArrayList<ProfileModel>> {
                @SuppressLint("SetTextI18n")
                override fun onResponse(
                    response: Response<ArrayList<ProfileModel>>,
                    retrofit: Retrofit
                ) {
                    if (response.isSuccess) {
                        callbk.onSuccess(response.body())
                        Log.v("Paper", response.body().size.toString())
                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            "Unable to fetch data",
                            Toast.LENGTH_SHORT
                        ).show()

                        val sc = response.code()
                        when (sc) {
                            400 -> {
                                Log.e("Error 400", "Bad Request")
                            }
                            404 -> {
                                Log.e("Error 404", "Not Found")
                            }
                            else -> {
                                Log.e("Error", "Generic Error")
                            }
                        }
                        return
                    }
                }

                override fun onFailure(t: Throwable) {
                    Toast.makeText(this@MainActivity, "Unable to fetch data", Toast.LENGTH_SHORT)
                        .show()
                    Log.e("Error", t.message.toString())
                }
            })
        } else {
            Toast.makeText(
                this,
                "No internet connection available.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    private fun setRV() {

        binding?.itemList?.layoutManager = LinearLayoutManager(this)
        binding?.itemList?.setHasFixedSize(true)
        var listAdapter = ListAdapter(this, list!!)
        binding?.itemList?.adapter = listAdapter
//        binding?.itemList?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrollStateChanged(recyclerView : RecyclerView, newState : Int) {
//                super.onScrollStateChanged(recyclerView, newState);
//                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
//                {
//                    isScrolling = true;
//                }
//            }
//            override fun onScrolled(recyclerView : RecyclerView , dx : Int, dy : Int) {
//                super.onScrolled(recyclerView, dx, dy);
//                currentItems = (binding?.itemList?.layoutManager as LinearLayoutManager).childCount
//
//                totalItems = (binding?.itemList?.layoutManager as LinearLayoutManager).itemCount
//
//                scrollOutItems = (binding?.itemList?.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
//
//                if(isScrolling && (currentItems + scrollOutItems == totalItems))
//                {
//                    isScrolling = false
//                }
//            }
//
//        })

    }
    interface ApiCallback {
        fun onSuccess(result: ArrayList<ProfileModel>?)
    }

}