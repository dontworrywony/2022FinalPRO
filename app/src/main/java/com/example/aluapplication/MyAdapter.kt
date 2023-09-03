package com.example.aluapplication
//new -> kotlin class 생성
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aluapplication.databinding.ItemRecyclerviewBinding

//myAdapter와 Myviewholder 연결 역할
class MyViewHolder(val binding: ItemRecyclerviewBinding): RecyclerView.ViewHolder(binding.root) { //상속 받음

    // 리사이클러뷰
// 아이템 관리 -> 아래 세 함수 반드시 있어야 함.
    class MyAdapter(val datas: MutableList<String>?) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        lateinit var context: Context

        override fun getItemCount(): Int {
            return datas?.size ?: 0
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): RecyclerView.ViewHolder { //main_activity 연결
            context = parent.context
            return MyViewHolder(
                ItemRecyclerviewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val binding = (holder as MyViewHolder).binding
            binding.itemData.text = datas!![position] //배열에 저장된 내용을 가져와서 각각의 아이템에 값을 넣어줌

            //글자색 바꾸기
            var sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context) //설정값을 가져오겠다.
            val txcolor: String? =
                sharedPreferences.getString("tx_color", "") //키값으로 가져올 prefrence가져오기 (null일수도) ->배경색
            binding.itemData.setTextColor(Color.parseColor(txcolor))//배경색 변경

        }

        //아이템 꾸미기
        class MyDecoration(val context: Context) : RecyclerView.ItemDecoration() {
            //배경이미지에 해당되는 함수
            override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
                super.onDrawOver(c, parent, state)
                //(@drawable/kbo 호출) //이미지를 읽어들임. ->null값 가능
                val dr: Drawable? = ResourcesCompat.getDrawable(
                    context.resources,
                    R.drawable.kbo,
                    null
                ) //drawable에 있는 것을 가져와서 사용 가능

                //이미지 크기 저장     //좌측 상단을 기준으로 크기 저장
                val drW = dr?.intrinsicWidth
                val drH = dr?.intrinsicHeight
                val left =
                    parent.width / 2 - drW?.div(2) as Int //좌측 (화면의 절반-이미지의 절반) -> parent.width - drw/2 을 나누기 함수로 사용
                val top = parent.width / 2 - drH?.div(2) as Int //상단
                //c: Canvas에 그림을 그리겠다.
                c.drawBitmap( //어떤 이미지를 어디에 그릴지
                    BitmapFactory.decodeResource(context.resources, R.drawable.kbo), //이미지 출력 함수
                    left.toFloat(), top.toFloat(), null
                ) //top, left의 타입을 float 타입으로 변경 필요
            }
        }
    }
}