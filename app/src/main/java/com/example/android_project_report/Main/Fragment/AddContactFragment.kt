package com.example.android_project_report.Main.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.android_project_report.Util.setOnSingleClickListener
import com.example.android_project_report.databinding.FragmentAddContactBinding

class AddContactFragment: Fragment() {

    private var mBinding: FragmentAddContactBinding?= null
    private val binding get() = mBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentAddContactBinding.inflate(inflater, container, false)

        binding.addContactBackBtn.setOnSingleClickListener {
            requireActivity().supportFragmentManager.popBackStack()
            Log.d("AddContactFragment","addContactBackBtn 눌림")
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }
}