package com.app.shopinkarts.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.app.shopinkarts.R
import com.app.shopinkarts.databinding.ItemsDeliveredProductBinding
import com.app.shopinkarts.model.CreateProduct


class ProductAdapter(val context: Context, val products: List<CreateProduct>) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    private lateinit var sizeQtyAdapter: SizeQtyAdapter


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemsDeliveredProductBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.items_delivered_product, parent, false
        )

        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemDetails = products[position]

        holder.binding.apply {

            productNameTV.text = itemDetails.productName

            priceTV.text = "Rs ${itemDetails.totalAmount}"

            Glide.with(context).load(itemDetails.productImage).into(imageIV)

            dateTV.text = DeliveredOrderAdapter.date

            pidTV.text = "PID # ${itemDetails.productId}"

            if (DeliveredOrderAdapter.paymentType == 1) {
                codOnlineTV.text = context.resources.getString(R.string.card)
            } else {
                codOnlineTV.text = context.resources.getString(R.string.cod)
            }
        }

//      sizeQtyAdapter = SizeQtyAdapter(context, itemDetails.variantsArr)
        sizeQtyAdapter = SizeQtyAdapter(context, itemDetails.variants)
        holder.binding.sizeQtyRV.adapter = sizeQtyAdapter
        holder.binding.sizeQtyRV.isNestedScrollingEnabled = false

        holder.itemView.setOnClickListener {
//            val intent = Intent(context, OrderDetailsActivity::class.java)
//            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }

    inner class ViewHolder(itemView: ItemsDeliveredProductBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val binding: ItemsDeliveredProductBinding = itemView

    }
}