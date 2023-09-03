package com.example.aluapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aluapplication.databinding.FragmentRetrofitBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RetrofitFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class RetrofitFragment : Fragment() {
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
        // Inflate the layout for this fragment
        val binding = FragmentRetrofitBinding.inflate(inflater, container, false)
        var mutableList: MutableList<MyItem>

        binding.btnSearch.setOnClickListener { //검색버튼을 눌렀을 때
            var keyword = binding.edtProduct.text.toString()
            //Call객체로 구성된 json->body부터 전달받음(Mymodel)
            val call: Call<Mymodel> = MyApplication.networkService.getList(
                keyword,
                "ZgHVMVYkpEhVVTQ9B79CqvABLw769mECbRPkR0sg7+iJtoq+oPC6yMS+V1G7qygAudMFk7Zu7LcjQUK03Q==",
                1,
                5,
                "json"
            )
            Log.d("mobileApp","${call.request()}")

            call?.enqueue(object : Callback<Mymodel> {
                override fun onResponse(call: Call<Mymodel>, response: Response<Mymodel>) { //올바르게 전달 되었을 때
                    if (response.isSuccessful){ //정말 잘 전달 되었는지
                        Log.d("mobileApp","${response.body()}") //가져와야할 json 객체
                        binding.retrofitRecyclerView.layoutManager = LinearLayoutManager(context)
                        binding.retrofitRecyclerView.adapter = MyRetrofitAdapter(requireContext(), response.body()!!.body.items) //mutable list -> != null
                    }
                }

                override fun onFailure(call: Call<Mymodel>, t: Throwable) { //전달 실패
                    Log.d("mobileApp","${t.toString()}")
                }
            })
        }

        mutableList = mutableListOf<MyItem>()
        binding.retrofitRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.retrofitRecyclerView.adapter = MyRetrofitAdapter(requireContext(), mutableList)

        return binding.root
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RetrofitFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}