package com.example.android_project_report.Main.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.android_project_report.Main.MainActivity
import com.example.android_project_report.Util.setOnSingleClickListener
import com.example.android_project_report.databinding.FragmentAddContactBinding

class AddContactFragment: Fragment() {

    private var mBinding: FragmentAddContactBinding?= null
    private val binding get() = mBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentAddContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sActivity = requireActivity() as MainActivity
        sActivity.setTitle("연락처 추가")

        binding.addContactCancelBtn.setOnSingleClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
//                .addToBackStack(null)
                .remove(this)
                .commit()
            sActivity.setTitle("연락처 관리")
//            requireActivity().supportFragmentManager.popBackStack()
            sActivity.supportFragmentManager.popBackStack()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }
}