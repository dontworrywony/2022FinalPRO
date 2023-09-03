package com.example.aluapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.multidex.MultiDexApplication
import com.bumptech.glide.Glide.init
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


//전역 변수를 담당하고 있는 클래스

//전역 응용 프로그램 상태를 유지하기 위한 기본 클래스
//첫 번째 액티비티(MainActivity)가 표시되기 전에 전역 상태를 초기화하는 데 사용
class MyApplication : MultiDexApplication() { //firebase를 사용하여 이전에 기록을 보관하고 가져옴
    //class MyApplication : Application() { //상속 -> MainActivity보다 우선 실행

    companion object{ //전역변수 선언(static)

        lateinit var db: FirebaseFirestore
        lateinit var storage : FirebaseStorage

        lateinit var auth : FirebaseAuth //이전 이메일

        var email:String? = null

        fun checkAuth():Boolean{ //사용자가 입력한 유저 값
            var currentuser = auth.currentUser
            return currentuser?.let{ //사용자가 입력한 유저 값에 대한 처리
                email=currentuser.email
                if(currentuser.isEmailVerified) true //인증된 이메일이다.
                else false
            } ?: false //인증되지 못한 유저
        }
        var networkService : NetworkService //초기화 필요

        val retrofit: Retrofit //Retrofit 클래스를 상속 받음 -> url 만들기
            get()= Retrofit.Builder()
                .baseUrl("http://apis.data.go.kr/6260000/ChildWelfareService/") //쿼리 앞 url
                .addConverterFactory(GsonConverterFactory.create()) //전달받은 쿼리로 url 사용
                .build() //만들다

        init{ //초기화
            networkService = retrofit.create(NetworkService::class.java)//초기화
        }

    }

    //초기화
    override fun onCreate() {
        super.onCreate()
        auth = Firebase.auth //초기화

        db=FirebaseFirestore.getInstance()
        storage = Firebase.storage
    }

}