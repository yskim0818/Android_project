package com.example.android_project_report.Main.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.android_project_report.Main.MainActivity
import com.example.android_project_report.databinding.FragmentPhoneBehaviorStatusBinding

class PhoneBehaviorStatusFragment: Fragment() {

    private var mBinding: FragmentPhoneBehaviorStatusBinding? = null
    private val binding get() = mBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentPhoneBehaviorStatusBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sActivity = requireActivity() as MainActivity
        sActivity.setTitle("폰 동작 상태")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }
}