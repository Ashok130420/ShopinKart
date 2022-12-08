package com.app.shopinkarts.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.shopinkarts.R
import com.app.shopinkarts.activity.InvoiceActivity
import com.app.shopinkarts.activity.TrackOrderActivity
import com.app.shopinkarts.classes.SharedPreference
import com.app.shopinkarts.classes.Utils
import com.app.shopinkarts.databinding.ItemsDeliveredOrderBinding
import com.app.shopinkarts.model.CreateProduct
import com.app.shopinkarts.response.Order
import java.text.DecimalFormat

class DeliveredOrderAdapter(val context: Context, val arrayList: ArrayList<Order>) :
    RecyclerView.Adapter<DeliveredOrderAdapter.ViewHolder>() {

    lateinit var productAdapter: ProductAdapter
    lateinit var sharedPreference: SharedPreference

    companion object {
        var date = ""
        var gst = 0F
        var paymentType = 0
        var arrayListInvoice: ArrayList<CreateProduct> = ArrayList()
        var tAmount = 0F
        var orderTotalAmount = 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding: ItemsDeliveredOrderBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.items_delivered_order, parent, false
        )

        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        sharedPreference = SharedPreference(context)

        val itemDetails = arrayList[position]

        holder.binding.apply {
            cancelOrderCL.visibility = View.GONE

            orderIdTV.text = "# ${itemDetails.orderId}"

            dateValueTV.text = Utils.formatDateFromDateString(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "dd-MMM-yyyy", arrayList[position].creationTimeStamp
            ).toString()

//            totalAmountValueTV.text = " Rs ${itemDetails.finalAmount + itemDetails.gstAmount}"


            totalAmountValueTV.text = "RS " + DecimalFormat(".00").format(itemDetails.finalAmount)

            tAmount = (itemDetails.finalAmount + itemDetails.gstAmount)

            gst = itemDetails.gstAmount

            Log.d("itemDetails.gstAmount", itemDetails.gstAmount.toString())

            arrayListInvoice.clear()
            arrayListInvoice.addAll(itemDetails.products)

            productAdapter = ProductAdapter(context, itemDetails.products)
            productRV.adapter = productAdapter
            productRV.isNestedScrollingEnabled = false
            productRV.hasFixedSize()
            if (itemDetails.orderStatus == 4) {
                stepperLayout.visibility = View.GONE
                trackOrderCL.visibility = View.GONE
                downloadInvoiceCL.visibility = View.GONE
                cancelOrderCL.visibility = View.VISIBLE
            } /*else if (itemDetails.orderStatus == 5) {
                stepperLayout.visibility = View.GONE
                trackOrderCL.visibility = View.VISIBLE
                downloadInvoiceCL.visibility = View.VISIBLE
                orderReplacedCL.visibility = View.VISIBLE
            } */else {
                stepperLayout.visibility = View.VISIBLE
                trackOrderCL.visibility = View.VISIBLE
                downloadInvoiceCL.visibility = View.VISIBLE
                cancelOrderCL.visibility = View.GONE
            }
            when (itemDetails.orderStatus) {
                0 -> {
                    viewStepper2.setBackgroundColor(context.resources.getColor(R.color.hint))
                    viewStepper3.setBackgroundColor(context.resources.getColor(R.color.hint))
                    viewStepper1.setBackgroundColor(context.resources.getColor(R.color.hint))
                    processingTV.setBackgroundResource(R.drawable.button_grey_radius5)
                    outForDeliveryTV.setBackgroundResource(R.drawable.button_grey_radius5)
                    deliveredTV.setBackgroundResource(R.drawable.button_grey_radius5)
                    outForDeliveryIV.setImageResource(R.drawable.deactivate_stepper)
                    deliveredIV.setImageResource(R.drawable.deactivate_stepper)
                    processingIV.setImageResource(R.drawable.deactivate_stepper)

                }
                1 -> {
                    viewStepper2.setBackgroundColor(context.resources.getColor(R.color.hint))
                    viewStepper3.setBackgroundColor(context.resources.getColor(R.color.hint))
                    viewStepper1.setBackgroundColor(context.resources.getColor(R.color.primary_Blue))
                    processingIV.setImageResource(R.drawable.active_stepper)
                    processingTV.setBackgroundResource(R.drawable.button_blue_radius5)
                    outForDeliveryTV.setBackgroundResource(R.drawable.button_grey_radius5)
                    deliveredTV.setBackgroundResource(R.drawable.button_grey_radius5)
                    outForDeliveryIV.setImageResource(R.drawable.deactivate_stepper)
                    deliveredIV.setImageResource(R.drawable.deactivate_stepper)

                }
                2 -> {
                    viewStepper1.setBackgroundColor(context.resources.getColor(R.color.primary_Blue))
                    viewStepper2.setBackgroundColor(context.resources.getColor(R.color.primary_Blue))
                    viewStepper3.setBackgroundColor(context.resources.getColor(R.color.hint))
                    processingIV.setImageResource(R.drawable.active_stepper)
                    outForDeliveryIV.setImageResource(R.drawable.active_stepper)
                    processingTV.setBackgroundResource(R.drawable.button_blue_radius5)
                    outForDeliveryTV.setBackgroundResource(R.drawable.button_blue_radius5)
                    deliveredTV.setBackgroundResource(R.drawable.button_grey_radius5)
                    deliveredIV.setImageResource(R.drawable.deactivate_stepper)

                }
                3 -> {
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
                5 -> {
                    deliveredTV.text="Replaced"
                    viewStepper1.setBackgroundColor(context.resources.getColor(R.color.primary_Blue))
                    viewStepper2.setBackgroundColor(context.resources.getColor(R.color.primary_Blue))
                    viewStepper3.setBackgroundColor(context.resources.getColor(R.color.primary_Blue))
                    processingIV.setImageResource(R.drawable.active_stepper)
                    outForDeliveryIV.setImageResource(R.drawable.active_stepper)
                    deliveredIV.setImageResource(R.drawable.green_circel)
                    deliveredTV.setBackgroundResource(R.drawable.button_green_radius5)
                    processingTV.setBackgroundResource(R.drawable.button_blue_radius5)
                    outForDeliveryTV.setBackgroundResource(R.drawable.button_blue_radius5)

                }
            }

//          time stamp date convert
            date = Utils.formatDateFromDateString(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "dd-MMM-yyyy", arrayList[position].creationTimeStamp
            ).toString()

            paymentType = itemDetails.paymentType

            downloadInvoiceCL.visibility = View.GONE

            if (itemDetails.orderStatus == 3 || itemDetails.orderStatus == 5) {

                downloadInvoiceCL.visibility = View.VISIBLE

                downloadInvoiceCL.setOnClickListener {

                    val intent = Intent(context, InvoiceActivity::class.java)
                    //send position
                    intent.putExtra("position", position)
                    intent.putExtra("orderId", itemDetails.orderId)

                    context.startActivity(intent)
                }
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