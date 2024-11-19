package com.example.android_project_report.Main.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.android_project_report.Main.MainActivity
import com.example.android_project_report.Util.setOnSingleClickListener
import com.example.android_project_report.databinding.FragmentContactInfoBinding

class ContactInfoFragment: Fragment() {

    private var mBinding: FragmentContactInfoBinding?= null
    private val binding get() = mBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentContactInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sActivity = requireActivity() as MainActivity
        sActivity.setTitle("연락처 정보")

        binding.infoContactCancelBtn.setOnSingleClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .remove(this)
                .commit()
            sActivity.setTitle("연락처 관리")
            sActivity.supportFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }
}