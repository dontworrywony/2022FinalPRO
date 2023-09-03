package com.example.aluapplication


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.example.aluapplication.databinding.ItemBoardBinding
import com.example.aluapplication.GlideApp


class MyBoardViewHolder(val binding: ItemBoardBinding) : RecyclerView.ViewHolder(binding.root)
//recycler view가 사용하는 각각의 아이템들에 대한 레이아웃

class MyBoardAdapter(val context: Context, val itemList: MutableList<ItemBoardModel>): RecyclerView.Adapter<MyBoardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyBoardViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MyBoardViewHolder(ItemBoardBinding.inflate(layoutInflater))
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: MyBoardViewHolder, position: Int) {
        val data = itemList.get(position) //itemModel에 있는 각각의 값

        holder.binding.run {
            itemEmailView.text=data.email
            itemDateView.text=data.date
            itemContentView.text=data.content
        }

        //스토리지 이미지 다운로드........................
        val imageRef = MyApplication.storage.reference.child("images/{${data.docId}}.jpg")
        imageRef.downloadUrl.addOnCompleteListener{task ->
            if (task.isSuccessful){
                //전달이 성공적으로 되었을 때
                // 다운로드 이미지를 ImageView에 보여줌
                GlideApp.with(context)
                    .load(task.result) //task가 가지고 있는 결과 로드
                    .into(holder.binding.itemImageView)
            }

        }

    }
}
