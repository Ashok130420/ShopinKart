package com.example.shopinkarts.fragments

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.shopinkarts.R
import com.example.shopinkarts.adapter.DeliveredOrderAdapter
import com.example.shopinkarts.databinding.FragmentOrdersBinding


class OrdersFragment : Fragment() {

    lateinit var binding: FragmentOrdersBinding
    lateinit var deliveredOrderAdapter: DeliveredOrderAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_orders, container, false)

        // adapter for delivered order
        deliveredOrderAdapter = DeliveredOrderAdapter(requireContext())
        binding.deliveredOrderRV.adapter = deliveredOrderAdapter
        binding.deliveredOrderRV.isNestedScrollingEnabled = false

        return binding.root
    }

}