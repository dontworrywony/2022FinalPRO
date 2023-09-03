package com.example.aluapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

//타이틀 화면 설정 -> 가장 처음 나오는 화면
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_splash)

        val backExecutor : ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor() //실제 실행시킬 내용
        val mainExecutor: Executor = ContextCompat.getMainExecutor(this) //메인 액티비티 부르기
        backExecutor.schedule({
            mainExecutor.execute{
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
            } //mainActivity 실행 후 종료

        }, 2, TimeUnit.SECONDS) //{실행내용}, 딜레이(얼마간 실행), 숫자에 대한 단위 (2초)
    }
}