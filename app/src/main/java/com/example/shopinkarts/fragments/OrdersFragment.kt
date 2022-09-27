package com.example.shopinkarts.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.shopinkarts.R
import com.example.shopinkarts.adapter.DeliveredOrderAdapter
import com.example.shopinkarts.api.RetrofitClient
import com.example.shopinkarts.classes.SharedPreference
import com.example.shopinkarts.databinding.FragmentOrdersBinding
import com.example.shopinkarts.response.MyOrdersResponse
import com.example.shopinkarts.response.Order
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class OrdersFragment : Fragment() {

    lateinit var sharedPreference: SharedPreference
    lateinit var binding: FragmentOrdersBinding
    lateinit var deliveredOrderAdapter: DeliveredOrderAdapter
    var arrayListMyOrders: ArrayList<Order> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_orders, container, false)

        sharedPreference = SharedPreference(requireContext())

        myOrdersList()
        return binding.root
    }

    private fun myOrdersList() {

        val call: Call<MyOrdersResponse> =
            RetrofitClient.instance!!.api.myOrdersApi(id = sharedPreference.getUserId())

        call.enqueue(object : Callback<MyOrdersResponse> {
            override fun onResponse(
                call: Call<MyOrdersResponse>,
                response: Response<MyOrdersResponse>
            ) {
                val myOrderResponse = response.body()
                if (response.isSuccessful) {

                    if (myOrderResponse!!.status) {

                        arrayListMyOrders.clear()
                        arrayListMyOrders.addAll(myOrderResponse.orders)
                        if (context!=null) {
                            deliveredOrderAdapter =
                                DeliveredOrderAdapter(requireContext(), arrayListMyOrders)
                            binding.deliveredOrderRV.adapter = deliveredOrderAdapter
                            binding.deliveredOrderRV.isNestedScrollingEnabled = false
                            deliveredOrderAdapter.notifyDataSetChanged()
                        }
                        myOrderResponse.orders
                    }
//                    Toast.makeText(
//                        requireContext(), "${myOrderResponse?.message}", Toast.LENGTH_SHORT
//                    ).show()

                    Log.d("TAG", "onResponse_SuccessResponse  ${myOrderResponse.message} ")

                } else {
                    if (context != null) {
                        Toast.makeText(
                            requireContext(),
                            "${myOrderResponse?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    Log.d("TAG", "onResponse_ElseResponse  ${myOrderResponse?.message} ")

                }
            }

            override fun onFailure(call: Call<MyOrdersResponse>, t: Throwable) {
                if (context != null) {
                    Toast.makeText(
                        requireContext(), "${t?.message}", Toast.LENGTH_SHORT
                    ).show()
                }
                Log.d("TAG", "onResponse_FailureResponse${t.message} ")
            }
        })
    }
}