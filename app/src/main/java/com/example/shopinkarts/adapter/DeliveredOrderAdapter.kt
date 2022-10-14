package com.example.shopinkarts.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shopinkarts.R
import com.example.shopinkarts.activity.InvoiceActivity
import com.example.shopinkarts.activity.TrackOrderActivity
import com.example.shopinkarts.classes.Utils
import com.example.shopinkarts.databinding.ItemsDeliveredOrderBinding
import com.example.shopinkarts.response.Order

class DeliveredOrderAdapter(val context: Context, val arrayList: ArrayList<Order>) :
    RecyclerView.Adapter<DeliveredOrderAdapter.ViewHolder>() {

    lateinit var productAdapter: ProductAdapter

    companion object {
        var date = ""
        var paymentType = 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding: ItemsDeliveredOrderBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.items_delivered_order, parent, false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemDetails = arrayList[position]
        holder.binding.apply {

            productAdapter = ProductAdapter(context, itemDetails.products)
            productRV.adapter = productAdapter
            productRV.isNestedScrollingEnabled = false

            if (itemDetails.orderStatus == 0) {
                viewStepper2.setBackgroundColor(context.resources.getColor(R.color.hint))
                viewStepper3.setBackgroundColor(context.resources.getColor(R.color.hint))
                viewStepper1.setBackgroundColor(context.resources.getColor(R.color.hint))
                processingTV.setBackgroundResource(R.drawable.button_grey_radius5)
                outForDeliveryTV.setBackgroundResource(R.drawable.button_grey_radius5)
                deliveredTV.setBackgroundResource(R.drawable.button_grey_radius5)
                outForDeliveryIV.setImageResource(R.drawable.deactivate_stepper)
                deliveredIV.setImageResource(R.drawable.deactivate_stepper)
                processingIV.setImageResource(R.drawable.deactivate_stepper)

            } else if (itemDetails.orderStatus == 1) {
                viewStepper2.setBackgroundColor(context.resources.getColor(R.color.hint))
                viewStepper3.setBackgroundColor(context.resources.getColor(R.color.hint))
                viewStepper1.setBackgroundColor(context.resources.getColor(R.color.primary_Blue))
                processingIV.setImageResource(R.drawable.active_stepper)
                processingTV.setBackgroundResource(R.drawable.button_blue_radius5)
                outForDeliveryTV.setBackgroundResource(R.drawable.button_grey_radius5)
                deliveredTV.setBackgroundResource(R.drawable.button_grey_radius5)
                outForDeliveryIV.setImageResource(R.drawable.deactivate_stepper)
                deliveredIV.setImageResource(R.drawable.deactivate_stepper)

            } else if (itemDetails.orderStatus == 2) {
                viewStepper1.setBackgroundColor(context.resources.getColor(R.color.primary_Blue))
                viewStepper2.setBackgroundColor(context.resources.getColor(R.color.primary_Blue))
                viewStepper3.setBackgroundColor(context.resources.getColor(R.color.hint))
                processingIV.setImageResource(R.drawable.active_stepper)
                outForDeliveryIV.setImageResource(R.drawable.active_stepper)
                processingTV.setBackgroundResource(R.drawable.button_blue_radius5)
                outForDeliveryTV.setBackgroundResource(R.drawable.button_blue_radius5)
                deliveredTV.setBackgroundResource(R.drawable.button_grey_radius5)
                deliveredIV.setImageResource(R.drawable.deactivate_stepper)

            } else if (itemDetails.orderStatus == 3) {
                viewStepper1.setBackgroundColor(context.resources.getColor(R.color.primary_Blue))
                viewStepper2.setBackgroundColor(context.resources.getColor(R.color.primary_Blue))
                viewStepper3.setBackgroundColor(context.resources.getColor(R.color.primary_Blue))
                processingIV.setImageResource(R.drawable.active_stepper)
                outForDeliveryIV.setImageResource(R.drawable.active_stepper)
                deliveredIV.setImageResource(R.drawable.active_stepper)
                deliveredTV.setBackgroundResource(R.drawable.button_blue_radius5)
                processingTV.setBackgroundResource(R.drawable.button_blue_radius5)
                outForDeliveryTV.setBackgroundResource(R.drawable.button_blue_radius5)

            }

//          time stamp date convert
            date = Utils.formatDateFromDateString(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "dd-MMM-yyyy", arrayList[position].creationTimeStamp
            ).toString()

            paymentType = itemDetails.paymentType

            downloadInvoiceCL.setOnClickListener {
                val intent = Intent(context, InvoiceActivity::class.java)
                context.startActivity(intent)
            }

            trackOrderStatusTV.setOnClickListener {
                val intent = Intent(context, TrackOrderActivity::class.java)
                intent.putExtra("orderId", itemDetails.orderId)
                context.startActivity(intent)
            }
        }


    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class ViewHolder(itemView: ItemsDeliveredOrderBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val binding: ItemsDeliveredOrderBinding = itemView
    }
}