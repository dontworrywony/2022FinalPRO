package com.example.aluapplication

import com.google.gson.annotations.SerializedName

//내가 가져오고 싶은 데이터

data class ItemRetrofitModel(
    var resultCode: Long = 0, //결과코드
    var resultMse: String? = null, //결과메세지
    var facility_nm: String? = null, //시설명
    var addr: String? = null, //소재지
    var num_of_people: String? = null, //정원
    var present_of_people: String? = null, //현원
    var type_of_facility: String? = null, //시설종류
    var tel: String? = null, //전화번호
    var lat: String? = null, //위도
    var lng: String? = null, //경도
    var reference_date: String? = null //데이터기준일자
)

//데이터 클래스 ->items
data class MyItem(val item:ItemRetrofitModel) //아이템 가져오기

//body 안에 있는 데이터를 class로 묶어줌 ->body
data class MyItems(val items:MutableList<MyItem>)


//변수명 반드시 같아야 함!!! -> 최상위 root부터 내려옴
data class Mymodel(val body:MyItems) //MyItem 가져오기
