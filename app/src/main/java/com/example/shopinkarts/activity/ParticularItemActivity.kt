package com.example.shopinkarts.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.shopinkarts.R
import com.example.shopinkarts.adapter.ParticularItemAdapter
import com.example.shopinkarts.api.RetrofitClient
import com.example.shopinkarts.databinding.ActivityParticularItemBinding
import com.example.shopinkarts.response.ParticularItemResponse
import com.example.shopinkarts.response.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ParticularItemActivity : AppCompatActivity() {

    lateinit var binding: ActivityParticularItemBinding
    lateinit var particularItemAdapter: ParticularItemAdapter
    var arrayListParticularItem: ArrayList<Product> = ArrayList()
    var subCategoryName = ""
    var imageURL = ""
    var particularItemId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_particular_item)

        subCategoryName = intent.extras!!.getString("subCategoryName", "")
        imageURL = intent.extras!!.getString("imageURL", "")
        particularItemId = intent.extras!!.getString("particularItemId", "")
        Log.d("particularItemId", particularItemId)

        binding.headerParticularItem.titleTV.text = subCategoryName
        Glide.with(this).load(imageURL).into(binding.headerParticularItem.iconIV)

        particularItemList()

        binding.headerParticularItem.backIV.setOnClickListener {
            onBackPressed()
        }

        binding.headerParticularItem.cartIV.setOnClickListener {
            val intent = Intent(this, ProductCartActivity::class.java)
            startActivity(intent)
        }
    }

    private fun particularItemList() {
        val call: Call<ParticularItemResponse> =
            RetrofitClient.instance!!.api.particularItem(particularItemId)
        call.enqueue(object : Callback<ParticularItemResponse> {
            override fun onResponse(
                call: Call<ParticularItemResponse>,
                response: Response<ParticularItemResponse>
            ) {
                val particularItemResponse = response.body()
                if (response.isSuccessful) {
                    if (particularItemResponse!!.status) {
                        arrayListParticularItem.clear()
                        arrayListParticularItem.addAll(particularItemResponse.products)
                        particularItemAdapter = ParticularItemAdapter(
                            this@ParticularItemActivity, arrayListParticularItem
                        )
                        binding.particularItemRV.adapter = particularItemAdapter
                        particularItemAdapter.notifyDataSetChanged()
                    }
                    Log.d("TAG", "onResponse_SuccessResponse${particularItemResponse.message} ")
                } else {
                    Toast.makeText(
                        this@ParticularItemActivity,
                        "${particularItemResponse?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<ParticularItemResponse>, t: Throwable) {
                Log.d("TAG", "onFailureResponse: ${t.message}")
                Toast.makeText(this@ParticularItemActivity, "${t.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

}