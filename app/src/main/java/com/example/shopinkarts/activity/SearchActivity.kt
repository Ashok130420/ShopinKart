package com.example.shopinkarts.activity

import android.content.Context
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.shopinkarts.R
import com.example.shopinkarts.adapter.SearchAdapter
import com.example.shopinkarts.api.RetrofitClient
import com.example.shopinkarts.databinding.ActivitySearchBinding
import com.example.shopinkarts.response.Product
import com.example.shopinkarts.response.SearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity() {

    lateinit var binding: ActivitySearchBinding
    lateinit var searchAdapter: SearchAdapter
    var arrayListSearch: ArrayList<Product> = ArrayList()
    var productId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)

        binding.headerSearch.backIV.setOnClickListener {
            onBackPressed()
        }

        binding.headerSearch.searchET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                productId = binding.headerSearch.searchET.text.toString()
                if (productId.isNotEmpty()) {
                    searchList()
                } else {
                    arrayListSearch.clear()
                }
            }

            override fun afterTextChanged(editable: Editable) {
                if (productId.isNotEmpty()){
                    arrayListSearch.clear()
                }
            }
        })
    }

    fun searchList() {

        val call: Call<SearchResponse> = RetrofitClient.instance!!.api.search(searchId = productId)

        call.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>, response: Response<SearchResponse>
            ) {
                val searchResponse = response.body()
                if (response.isSuccessful) {
                    if (searchResponse!!.status) {

                        arrayListSearch.clear()
                        arrayListSearch.addAll(searchResponse.product)
                        searchAdapter = SearchAdapter(this@SearchActivity, arrayListSearch)
                        binding.searchRV.adapter = searchAdapter
                        searchAdapter.notifyDataSetChanged()
                        searchResponse.product
                    }
//                    Toast.makeText(this@SearchActivity, searchResponse.message, Toast.LENGTH_SHORT)
//                        .show()
                    Log.d("TAG", "onResponse: ${searchResponse.message}")

                } else {
//                    Toast.makeText(
//                        this@SearchActivity, "${searchResponse?.message}", Toast.LENGTH_SHORT
//                    ).show()
                    Log.d("TAG", "onResponse_ElseResponse  ${searchResponse?.message} ")
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                Toast.makeText(
                    this@SearchActivity, "${t?.message}", Toast.LENGTH_SHORT
                ).show()
                Log.d("TAG", "onResponse_FailureResponse${t.message} ")
            }
        })
    }

    // To remove EditText focus on touch outside
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }


}