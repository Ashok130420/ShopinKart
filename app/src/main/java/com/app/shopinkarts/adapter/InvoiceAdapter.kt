package com.app.shopinkarts.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.shopinkarts.R
import com.app.shopinkarts.databinding.ItemsInvoiceDetailsBinding
import com.app.shopinkarts.model.CreateProduct
import java.text.DecimalFormat

class InvoiceAdapter(
    val context: Context, val arrayList: List<CreateProduct>
) : RecyclerView.Adapter<InvoiceAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemsInvoiceDetailsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.items_invoice_details, parent, false
        )

        return ViewHolder(binding)

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        val itemDetails = arrayList[position]
        holder.binding.apply {

            itemsNameTV.text = "${itemDetails.productName} \nPid : (${itemDetails.pId}), HsnCode : (${itemDetails.hsnCode})"
            qtyValueTV.text = itemDetails.qty.toString()
            totalAmountValueTV.text = "RS " + DecimalFormat(".00").format(itemDetails.totalAmount)

        }

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class ViewHolder(itemView: ItemsInvoiceDetailsBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val binding: ItemsInvoiceDetailsBinding = itemView

    }
}