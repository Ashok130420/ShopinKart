package com.example.shopinkarts.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopinkarts.R
import com.example.shopinkarts.databinding.ItemsDeliveredProductBinding
import com.example.shopinkarts.model.CreateProduct


class ProductAdapter(val context: Context, val products: List<CreateProduct>) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    private lateinit var sizeQtyAdapter: SizeQtyAdapter

    companion object {
        var name = ""
        var qty = ""
        var totalAmount = ""

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemsDeliveredProductBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.items_delivered_product, parent, false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemDetails = products[position]

        holder.binding.apply {

            name = itemDetails.productName
            qty = itemDetails.qty.toString()
            totalAmount = itemDetails.totalAmount.toString()

            productNameTV.text = itemDetails.productName

            priceTV.text = itemDetails.totalAmount.toString()

            Glide.with(context).load(itemDetails.productImage).into(imageIV)

            dateTV.text = DeliveredOrderAdapter.date

//          pidTV.text = itemDetails.productId

            if (DeliveredOrderAdapter.paymentType == 1) {
                codOnlineTV.text = context.resources.getString(R.string.card)
            } else {
                codOnlineTV.text = context.resources.getString(R.string.cod)
            }

        }

        sizeQtyAdapter = SizeQtyAdapter(context, itemDetails.variantsArr)
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