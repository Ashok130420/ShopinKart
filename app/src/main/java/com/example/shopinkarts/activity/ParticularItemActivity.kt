package com.example.shopinkarts.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.PopupMenu
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

    var sorting = String()
    var filter = String()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_particular_item)

        subCategoryName = intent.extras!!.getString("subCategoryName", "")
        imageURL = intent.extras!!.getString("imageURL", "")
        particularItemId = intent.extras!!.getString("particularItemId", "")
        Log.d("particularItemId", particularItemId)

        binding.headerParticularItem.titleTV.text = subCategoryName
        Glide.with(this).load(imageURL).into(binding.headerParticularItem.iconIV)

        particularItemList("sort", "price", "0")

        binding.headerParticularItem.backIV.setOnClickListener {
            onBackPressed()
        }

        binding.sortCL.setOnClickListener {
            popupMenuSorting(it)
        }

        binding.filterCL.setOnClickListener {
            popupMenuFilter(it)
        }


        binding.headerParticularItem.cartIV.setOnClickListener {
            val intent = Intent(this, ProductCartActivity::class.java)
            startActivity(intent)
        }

        /*binding.filterTV.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    filterDetails: Int,
                    id: Long
                ) {
                    val selectedItem = parent?.getItemAtPosition(filterDetails).toString()
                    if (filterDetails == 0) {
                        filter = ""
                    } else {
                        filter = selectedItem
                        if (filterDetails == 1) {
                            particularItemList("filter", "price", "0")
                        } else if (filterDetails == 2) {
                            particularItemList("filter", "price", "1")
                        } else if (filterDetails == 3) {
                            particularItemList("filter", "price", "2")
                        } else if (filterDetails == 4) {
                            particularItemList("filter", "price", "3")
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }*/
        /*  binding.sortTV.onItemSelectedListener =
              object : AdapterView.OnItemSelectedListener {
                  override fun onItemSelected(
                      parent: AdapterView<*>?,
                      view: View?,
                      positionDetails: Int,
                      id: Long
                  ) {
                      val selectedItem = parent?.getItemAtPosition(positionDetails).toString()
                      if (positionDetails == 0) {
                          sorting = ""

                      } else {
                          sorting = selectedItem

                          if (positionDetails == 1) {
                              particularItemList("sort", "price", "0")
                          } else if (positionDetails == 2) {
                              particularItemList("sort", "price", "1")
                          } else {
                              particularItemList("sort", "price", "newest")
                          }
                          *//* if ()
                             particularItemList("sort", "price", "1")
                         else
                             particularItemList("sort", "newest", "2")*//*

                    }
                    Log.d("Position", selectedItem)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
*/
    }

    private fun particularItemList(type: String, subType: String, value: String) {


        val call: Call<ParticularItemResponse> =
            RetrofitClient.instance!!.api.particularItem(
                particularItemId,
                type = type,
                subType = subType,
                value = value
            )
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
                        binding.allProductsTV.text =
                            "Showcasing ${arrayListParticularItem.size} ${subCategoryName}"
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

    private fun popupMenuSorting(view: View) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.inflate(R.menu.sorting_option_menu)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.optionPriceHigh -> {
                    particularItemList("sort", "price", "0")
                    true
                }
                R.id.optionPriceLow -> {
                    particularItemList("sort", "price", "1")
                    true
                }
                R.id.optionNewest -> {
                    particularItemList("sort", "newest", "")
                    true
                }
                else -> true
            }
        }
        val popup = PopupMenu::class.java.getDeclaredField("mPopup")
        popup.isAccessible = true
        val menu = popup.get(popupMenu)
        menu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
            .invoke(menu, true)
        popupMenu.show()

    }

    private fun popupMenuFilter(view: View) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.inflate(R.menu.filter_option_menu)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.optionFilter1 -> {
                    particularItemList("filter", "price", "0")

                    true
                }
                R.id.optionFilter2 -> {
                    particularItemList("filter", "price", "1")

                    true
                }
                R.id.optionFilter3 -> {
                    particularItemList("filter", "price", "2")

                    true
                }
                R.id.optionFilter4 -> {

                    particularItemList("filter", "price", "3")
                    true
                }
                else -> true
            }
        }
        val popup = PopupMenu::class.java.getDeclaredField("mPopup")
        popup.isAccessible = true
        val menu = popup.get(popupMenu)
        menu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
            .invoke(menu, true)
        popupMenu.show()

    }


}