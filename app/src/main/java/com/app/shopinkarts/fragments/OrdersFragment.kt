package com.app.shopinkarts.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.app.shopinkarts.R
import com.app.shopinkarts.adapter.DeliveredOrderAdapter
import com.app.shopinkarts.api.RetrofitClient
import com.app.shopinkarts.classes.SharedPreference
import com.app.shopinkarts.databinding.FragmentOrdersBinding
import com.app.shopinkarts.response.MyOrdersResponse
import com.app.shopinkarts.response.Order
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class OrdersFragment : Fragment() {

    lateinit var sharedPreference: SharedPreference
    lateinit var binding: FragmentOrdersBinding
    lateinit var deliveredOrderAdapter: DeliveredOrderAdapter

    //    var arrayListMyOrders: ArrayList<Order> = ArrayList()
    companion object {

        var arrayListMyOrders: ArrayList<Order> = ArrayList()
        val mInstance: OrdersFragment = OrdersFragment()

        fun getInstance(): OrdersFragment {
            return mInstance
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_orders, container, false)

        sharedPreference = SharedPreference(requireContext())


        myOrdersList()
        return binding.root
    }

    fun myOrdersList() {

        val call: Call<MyOrdersResponse> =
            RetrofitClient.instance!!.api.myOrdersApi(id = sharedPreference.getUserId())

        call.enqueue(object : Callback<MyOrdersResponse> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<MyOrdersResponse>, response: Response<MyOrdersResponse>
            ) {
                val myOrderResponse = response.body()
                if (response.isSuccessful) {

                    if (myOrderResponse!!.status) {

                        arrayListMyOrders.clear()
                        arrayListMyOrders.addAll(myOrderResponse.orders)

                        if (context != null) {
                            deliveredOrderAdapter =
                                DeliveredOrderAdapter(requireContext(), arrayListMyOrders)
                            binding.deliveredOrderRV.adapter = deliveredOrderAdapter
                            binding.deliveredOrderRV.isNestedScrollingEnabled = false
                            binding.deliveredOrderRV.hasFixedSize()
                            deliveredOrderAdapter.notifyDataSetChanged()

                            if (arrayListMyOrders.isNotEmpty()) {
                                binding.deliveredOrderRV.visibility = View.VISIBLE
                                binding.lottyAnimationMyOrder.visibility = View.GONE
                            } else {
                                binding.lottyAnimationMyOrder.visibility = View.VISIBLE
                                binding.deliveredOrderRV.visibility = View.GONE
                            }
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
                            requireContext(), "${myOrderResponse?.message}", Toast.LENGTH_SHORT
                        ).show()
                    }
                    Log.d("TAG", "onResponse_ElseResponse  ${myOrderResponse?.message} ")

                }
            }

            override fun onFailure(call: Call<MyOrdersResponse>, t: Throwable) {
                if (context != null) {
                    Toast.makeText(
                        requireContext(), "${t.message}", Toast.LENGTH_SHORT
                    ).show()
                }
                Log.d("TAG", "onResponse_FailureResponse${t.message} ")
            }
        })
    }
}