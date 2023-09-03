package com.example.aluapplication

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.aluapplication.databinding.ActivityAddBinding
import com.example.aluapplication.databinding.FragmentOneBinding
import java.io.*

class OneFragment : Fragment() {

    private var _binding: FragmentOneBinding? = null
    private val binding get() = _binding!!

    private lateinit var dbHelper: DBHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOneBinding.inflate(inflater, container, false)
        val view = binding.root

        dbHelper = DBHelper(requireContext())

        binding.Bsave.setOnClickListener {
            val name = binding.Ename.text.toString()
            val age = binding.Eage.text.toString()
            val tmi = binding.Etmi.text.toString()

            saveData(name, age, tmi)
        }

        loadData()

        return view
    }

    private fun saveData(name: String, age: String, tmi: String) {
        val data = "$name\n$age\n$tmi"

        try {
            val fileOutputStream = requireContext().openFileOutput("data.txt", Context.MODE_APPEND)
            val outputStreamWriter = OutputStreamWriter(fileOutputStream)
            val bufferedWriter = BufferedWriter(outputStreamWriter)
            bufferedWriter.write(data)
            bufferedWriter.newLine()
            bufferedWriter.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun loadData() {
        try {
            val fileInputStream = requireContext().openFileInput("data.txt")
            val inputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader = BufferedReader(inputStreamReader)

            val stringBuilder = StringBuilder()
            var line: String? = bufferedReader.readLine()
            while (line != null) {
                stringBuilder.append(line).append("\n")
                line = bufferedReader.readLine()
            }

            bufferedReader.close()

            val data = stringBuilder.toString()
            val lines = data.split("\n")

            if (lines.size >= 3) {
                val name = lines[0]
                val age = lines[1]
                val tmi = lines[2]

                binding.Ename.setText(name)
                binding.Eage.setText(age)
                binding.Etmi.setText(tmi)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}



