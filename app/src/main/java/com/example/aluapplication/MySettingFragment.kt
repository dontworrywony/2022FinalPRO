package com.example.aluapplication

import android.os.Bundle
import android.text.TextUtils
import android.preference.PreferenceFragment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.EditTextPreference
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

class MySettingFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        //setPreferencesFromResource(R.xml.settings,rootKey) //xml형태로 설정

        val idPreference : EditTextPreference? = findPreference("id")
        //idPreference?.summaryProvider = EditTextPreference.SimpleSummaryProvider.getInstance()   //summaryProvider사용자가 입력한 내용을 요약해서 보여줌
        //사용자가 사전에 아이디를 입력했을 수도, 안 했을 수도

        //배경색 변경
        val bgColorPreference: ListPreference?=findPreference("bg_color") //사용자가 입력한 키값 가져오기
        bgColorPreference?.summaryProvider = ListPreference.SimpleSummaryProvider.getInstance() //
        //글자색 변경
        val txColorPreference: ListPreference?=findPreference("tx_color") //사용자가 입력한 키값 가져오기
        txColorPreference?.summaryProvider = ListPreference.SimpleSummaryProvider.getInstance() //사용자가 선택한 값을 SimpleSummaryProvider에 출력
    }
}