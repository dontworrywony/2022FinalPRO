package com.example.aluapplication

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

//ADD 하였을 때 기록을 저장해주는 클래스
class DBHelper (context: Context) : SQLiteOpenHelper(context, "testdb", null,1){

    //무조건 아래 두 함수를 포함 onCreate,onUpgrade
    //db: sql에서 받은 데이터
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table todo_tbl (_id integer primary key autoincrement, todo not null)") //테이블 생성 (레코드별 구별이름 필요: id, field_name -> null 불가)
        //autoincrement: 데이터가 들어올 때마다 자동으로 id가 들어옴.

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

}