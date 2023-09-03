package com.example.aluapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.aluapplication.databinding.ActivityMainBinding
import com.example.aluapplication.databinding.ActivityTabBinding
import com.google.android.material.tabs.TabLayoutMediator

class TabActivity : AppCompatActivity() {

    private lateinit var toolbar: ActionBarDrawerToggle


    //아답터 클래스 - 프레그먼트를 상속 받아 사용 ->one, two, three
    class MyFragmentAdapter(activity: FragmentActivity)  : FragmentStateAdapter(activity) {
        //기본 변수로는 안됨.
        lateinit var toolbar : ActionBarDrawerToggle // lateinit : '클래스의 값을 이용하는 변수의 경우'에서만!
        // 전역변수를 썼을 때 이 변수가 사용될 때까지 최대한 초기화를 늦춘다는 것

        //프래그먼트 변수 사용-> 리스트로 받음
        val fragments: List<Fragment>

        init {
            fragments = listOf(OneFragment(), TwoFragment()) //순서대로 리스트 작성
        }

        //반드시 선언 (getItemCount)
        override fun getItemCount(): Int {
            return fragments.size //해당 프레그먼트의 size return
        }

        //반드시 선언(createFragment)
        override fun createFragment(position: Int): Fragment {
            return fragments[position] //어떤 프레그먼트를 가져올 것인가 -> list[i]로 받고 있음
        }
    }

    private lateinit var binding: ActivityTabBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTabBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar) //툴바가 액션바의 역할을 하도록 전달

        //viewpager2-> 숨어있던 옆에 창 보여줌 (스와이프)
        binding.viewpager2.adapter = MyFragmentAdapter(this) // 어뎁터 연결 (동작제어-> 위 클래스 사용)

        //Tab버튼 -> viewpager2 연결 (프래그먼트 패이지와 연결)
        TabLayoutMediator(binding.tabs,binding.viewpager2){
                tab, position -> tab.text="TAB ${position+1}" //Tab 이름: 0부터 시작
        }.attach() //붙여넣어 연결

    }

    //옵션메뉴 -> 메뉴바 보여지도록 가시화(명시)
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        return super.onCreateOptionsMenu(menu) //메뉴를 리턴함
    }



    //메뉴바 함수로 호출 -> 어떤 아이템이 선택되는가 (활동)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //만들어 놓은 toolbar 선택 시 작동
        if (toolbar.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}