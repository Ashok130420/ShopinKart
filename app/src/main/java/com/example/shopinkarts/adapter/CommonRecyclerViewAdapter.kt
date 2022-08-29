package com.example.shopinkarts.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shopinkarts.R
import com.example.shopinkarts.databinding.RecyclerViewItemBinding
import com.example.shopinkarts.model.CommonModel

class CommonRecyclerViewAdapter(val context: Context, val arrayList: ArrayList<CommonModel>) : RecyclerView.Adapter<CommonRecyclerViewAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: RecyclerViewItemBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.recycler_view_item,
                parent,
                false
            )


        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.titleTv.text = arrayList[position].name
        holder.binding.viewALl.text = arrayList[position].viewAll
       // holder.shopForAdapter = ShopForAdapter(context)



        if (arrayList[position].layoutManager==0){
            holder.binding.shopForRV.layoutManager = LinearLayoutManager(context,
                if (arrayList[position].orientation==0)
                    LinearLayoutManager.HORIZONTAL else LinearLayoutManager.VERTICAL
                ,false)
        }else if (arrayList[position].layoutManager==1){
            holder.binding.shopForRV.layoutManager = GridLayoutManager(context,2,
            )
        }

        holder.binding.shopForRV.adapter = holder.shopForAdapter

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class ViewHolder(itemView: RecyclerViewItemBinding) : RecyclerView.ViewHolder(itemView.root) {

        val binding : RecyclerViewItemBinding = itemView
        lateinit var shopForAdapter : ShopForAdapter
        init {


        }
    }
}