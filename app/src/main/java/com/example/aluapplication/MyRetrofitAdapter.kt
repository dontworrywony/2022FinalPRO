package com.example.aluapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aluapplication.databinding.ItemRetrofitBinding

class MyRetrofitViewHolder(val binding: ItemRetrofitBinding): RecyclerView.ViewHolder(binding.root)

class MyRetrofitAdapter(val context: Context, val datas: MutableList<MyItem>?): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun getItemCount(): Int{
        return datas?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
            = MyRetrofitViewHolder(ItemRetrofitBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding=(holder as MyRetrofitViewHolder).binding

        //add......................................
        val model = datas!![position]
        binding.itemName.text = model.item.facility_nm //시설명
        binding.itemCapacity.text =model.item.num_of_people //정원
        binding.itemAddr.text = model.item.type_of_facility //주소
        binding.itemCall.text = model.item.tel //전화번호
        binding.itemLat.text = model.item.lat //위도
        binding.itemLng.text = model.item.lng //경도
        binding.itemDate.text = model.item.reference_date //데이터

        /*
        Glide.with(context)
            .load(model.item.imgurl1)
            .into(binding.itemImage2)

         */
    }
}