package com.example.aluapplication

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView
import androidx.preference.PreferenceManager
import com.example.aluapplication.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import java.io.BufferedReader
import java.io.OutputStreamWriter

class MainActivity : AppCompatActivity(), OnNavigationItemSelectedListener {
    lateinit var binding: ActivityMainBinding
    lateinit var toolbar : ActionBarDrawerToggle // lateinit : '클래스의 값을 이용하는 변수의 경우'에서만!

    var datas: MutableList<String> = mutableListOf()

    lateinit var adapter: FragmentAdapter

    //각각의 frament 연결
    //lateinit var volleyFragment: VolleyFragment
    lateinit var retrofitFragment: RetrofitFragment
    lateinit var boardFragment: BoardFragment
    lateinit var homeFragment: HomeFragment

    lateinit var sharedPreferences: SharedPreferences

    var mode = "home" //현재 상태 -> volley / retrofit
    // 전역변수를 썼을 때 이 변수가 사용될 때까지 최대한 초기화를 늦춘다는 것

    var authMenuItem : MenuItem?= null

    private val authActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            updateLogStatus()
        }
    }


    //처음 앱이 실행될 때
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //배경색 변경
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this) //설정값을 가져오겠다.

        setSupportActionBar(binding.toolbar) //툴바가 액션바의 역할을 하도록 전달

        //액션바에 있는 toggle:on/of  열었다, 닫았다. -> drawer
        toolbar = ActionBarDrawerToggle(this, binding.drawer, R.string.drawer_opened, R.string.drawer_closed) //ativity_main, string
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.syncState() //toolbar 실행

        //drawer-네비게이션바 연결
        binding.mainDrawer.setNavigationItemSelectedListener(this) //Logcat 출력

        updateLogStatus()
        //drawable item 클릭 시//
        setSupportActionBar(binding.toolbar)
        toolbar = ActionBarDrawerToggle(
            this,
            binding.drawer,
            R.string.drawer_opened,
            R.string.drawer_closed
        )

        //초기화
        homeFragment = HomeFragment()
        retrofitFragment= RetrofitFragment()
        boardFragment = BoardFragment()

        supportFragmentManager.beginTransaction() //무엇을 바꾸겠다. (실행 명령어 준비)
            .replace(R.id.activity_content, homeFragment) //activity_content에 volley를 넣어라
            .commit() //실행하기

        //retrofit 실행
        supportFragmentManager.beginTransaction() //무엇을 바꾸겠다. (실행 명령어 준비)
            .add(R.id.activity_content, retrofitFragment) //activity_content에 volley를 넣어라
            .hide(retrofitFragment) //fragment를 가지고 있지만 보여주진 않고 가림
            .commit() //실행하기

        supportFragmentManager.beginTransaction() //무엇을 바꾸겠다. (실행 명령어 준비)
            .add(R.id.activity_content, boardFragment) //activity_content에 volley를 넣어라
            .hide(boardFragment) //fragment를 가지고 있지만 보여주진 않고 가림
            .commit() //실행하기


        supportActionBar?.title="HOME"

    }

    //메뉴 바 설정 시 아래의 두 함수 모두 필요!!
    //-> onCreateOptionMenu, onOtionItemSelected
    //dawer에서 아이템 선택 시 이벤트
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return true
    }

    //drawable 로그인 상태 변경
    private fun updateLogStatus() {
        val navigationView: NavigationView = findViewById(R.id.main_drawer)
        val logTextView = navigationView.getHeaderView(0).findViewById<TextView>(R.id.log)

        if (MyApplication.checkAuth()) {
            val email = MyApplication.email
            logTextView.text = "${MyApplication.email}님"
        } else {
            logTextView.text = "로그인 필요"
        }
    }

    //옵션메뉴 -> 메뉴바 보여지도록 가시화(명시)
    //옵션 멘 만들기
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        authMenuItem=menu!!.findItem(R.id.menu_auth)
        if (MyApplication.checkAuth()){ //인증이 된 경우
            authMenuItem!!.title = "${MyApplication.email}님"
        }
        else{
            authMenuItem!!.title= "인증"
        }
        return super.onCreateOptionsMenu(menu)
    }

    //자동호출 함수
    override fun onStart(){
        //intent에서 finish된 다음 mainActivity로 돌아올 때 실행
        //oncreate -> onStart
        super.onStart()
        if (authMenuItem != null){
            if (MyApplication.checkAuth()){ //인증이 된 경우
                authMenuItem!!.title = "${MyApplication.email}님"
            }
            else{
                authMenuItem!!.title= "인증"
            }
        }

    }

    //클릭리스너
    //메뉴바 함수로 호출 -> 어떤 아이템이 선택되는가 (활동)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //만들어 놓은 toolbar 선택 시 작동
        if (toolbar.onOptionsItemSelected(item)) {
            return true
        }

        if(item.itemId === R.id.menu_home && mode !== "home"){ //현재 발리가 아닐 시 발리 클릭
            supportFragmentManager.beginTransaction()
                //.replace(R.id.activity_content, volleyFragment) //발리로 변경
                .show(homeFragment) //발리만 보이도록
                .commit() //적용
            supportFragmentManager.beginTransaction()
                .hide(retrofitFragment) //레트로핏 숨김
                .commit() //적용 및 실행
            supportFragmentManager.beginTransaction()
                .hide(boardFragment) //보드 숨김
                .commit() //적용 및 실행

            mode="home"
            supportActionBar?.title="Home"
        }

        else if(item.itemId === R.id.menu_retrofit && mode !== "retrofit"){ //레트로핏 아닌데 레트로핏이 눌렸을 경우
            supportFragmentManager.beginTransaction()
                .show(retrofitFragment) //레트로빗 보임
                .commit() //적용 및 실행

            supportFragmentManager.beginTransaction()
                .hide(homeFragment)
                .commit() //적용 및 실행

            supportFragmentManager.beginTransaction()
                .hide(boardFragment) //보드 숨김
                .commit() //적용 및 실행

            mode="retrofit"
            supportActionBar?.title="아동복지시설"

        }
        else if(item.itemId === R.id.menu_board && mode !== "board"){ //레트로핏 아닌데 레트로핏이 눌렸을 경우
            supportFragmentManager.beginTransaction()
                .show(boardFragment) //보드 숨김
                .commit() //적용 및 실행

            supportFragmentManager.beginTransaction()
                .hide(retrofitFragment) //레트로빗 보임
                .commit() //적용 및 실행
            supportFragmentManager.beginTransaction()
                .hide(homeFragment)
                .commit() //적용 및 실행
            mode="board"
            supportActionBar?.title="아동일지 작성"
        }


        else if(item.itemId === R.id.menu_auth){
            val intent = Intent(this, AuthActivity::class.java)
            if (authMenuItem!!.title!!.equals("인증")){//login
                intent.putExtra("data", "logout")
            }
            else{ //이메일 이미 표시
                intent.putExtra("data", "login")
            }
            startActivity(Intent(this, AuthActivity::class.java)) //startActivity를 통해 호출
            //finish일 때 돌아옴.

        }
        when(item.itemId){
            //setting icon 누를 시
            R.id.menu_main_setting ->{
                val intent = Intent(this, SettingActivity::class.java) //settingActivity로 이동
                startActivity(intent)
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }


    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putStringArrayList("datas",ArrayList(datas))
    }


    //액션바와 툴바의 차이점
    // 액션바: 액티비티 창이 자동으로 출력하는 액티비티의 구성요소
    // 툴바: 개발자가 직접 제어하는 뷰 -> 사용자 정의 후 사용가능
}