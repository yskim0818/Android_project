package com.example.android_project_report.Main.Fragment

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.android_project_report.R
import com.example.android_project_report.Util.setOnSingleClickListener
import com.example.android_project_report.databinding.FragmentAddContactBinding
import com.example.android_project_report.databinding.FragmentStartMenuBinding

class StartMenuFragment: Fragment() {

    private var mBinding: FragmentStartMenuBinding?= null
    private val binding get() = mBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mBinding = FragmentStartMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        requestUsagePermission()

        binding.manageContactsBtnLayout.setOnSingleClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container, ManageContactsFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.internetSearchBtnLayout.setOnSingleClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container, InternetSearchFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.phoneStatusSearchBtnLayout.setOnSingleClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container, PhoneBehaviorStatusFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.authorIntroduceBtnLayout.setOnSingleClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container, AboutauthorFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

    private fun requestUsagePermission() {
        val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
        startActivity(intent)
    }

}