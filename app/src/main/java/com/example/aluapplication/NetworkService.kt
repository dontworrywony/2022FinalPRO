package com.example.aluapplication

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//요청 메세지 명세

interface NetworkService {
    @GET("ChildWelfareService")
    fun getList( //데이터 요청 명세서에서 사용하라는 이름과 동일핟게 진행
        @Query("gugun") addr:String,
        @Query("serviceKey") apikey:String,
        @Query("pageNo") page:Long,
        @Query("numOfRows") pageSize:Int,
        @Query("returnType") returnType:String
    ): Call<Mymodel>
}

/*
        @Query("serviceKey") apikey:String, //인증키
        @Query("pageNo") page:Long, //페이지번호
        @Query("numOfRows") pageSize:Int, //한페이지 결과 수
        @Query("gugun") addr:Int, //군구명
        @Query("returnType") returnType:String //리턴타입
* */