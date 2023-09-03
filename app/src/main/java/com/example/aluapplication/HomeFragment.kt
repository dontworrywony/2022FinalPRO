package com.example.aluapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.aluapplication.databinding.ActivityAddBinding
import com.example.aluapplication.databinding.FragmentHomeBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    lateinit var binding: ActivityAddBinding
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)

        //mykid 버튼 클릭 리스너 추가
        binding.mykid.setOnClickListener {
            // OneFragment로 이동하기 위해 TabActivity 실행
            val intent = Intent(requireActivity(), TabActivity::class.java)
            intent.putExtra("fragmentIndex", 0) // OneFragment의 인덱스(0)를 전달
            startActivity(intent)
        }

        //mykid 버튼 클릭 리스너 추가
        binding.welfare.setOnClickListener {
            val intent = Intent(requireActivity(), RetrofitFragment::class.java)
            startActivity(intent)
        }

        //전화타입
        binding.BtnCall.setOnClickListener{ //view: 전화번호가 뜸. call: 바로 전화
            var intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:/02-1234-5678")) //this. Activity::class.java
            startActivity(intent) //intent 전달
        }

        //웹 연결
        binding.BtnLink.setOnClickListener{
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.amc.seoul.kr/asan/healthinfo/disease/diseaseDetail.do?contentId=32651"))
            startActivity(intent) //intent 전달
        }

        //웹 연결
        binding.devInfor.setOnClickListener{
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.sangdam.kr/encyclopedia/cd/develop/develop.html"))
            startActivity(intent) //intent 전달
        }


        //지도
        binding.BtnCenter.setOnClickListener{
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:37.5662952, 126.9779451")) //갤러리 저장위치
            startActivity(intent) //intent 전달
        }
            // Inflate the layout for this fragment
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}