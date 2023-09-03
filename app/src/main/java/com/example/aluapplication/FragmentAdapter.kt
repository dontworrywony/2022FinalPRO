package com.example.aluapplication

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aluapplication.databinding.ItemRecyclerviewBinding

class FragmentAdapter(val datas: MutableList<String>?): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    lateinit var context: Context

    override fun getItemCount(): Int{
        return datas?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder{ //main_activity 연결
        context =parent.context
        return MyViewHolder(ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding=(holder as MyViewHolder).binding
        binding.itemData.text= datas!![position] //배열에 저장된 내용을 가져와서 각각의 아이템에 값을 넣어줌

        //글자색 바꾸기
        var sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context) //설정값을 가져오겠다.
        val txcolor:String?=sharedPreferences.getString("tx_color","") //키값으로 가져올 prefrence가져오기 (null일수도) ->배경색
        binding.itemData.setTextColor(Color.parseColor(txcolor))//배경색 변경

    }
}