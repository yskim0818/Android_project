package com.example.android_project_report.Main

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.android_project_report.Main.Fragment.ManageContactsFragment
import com.example.android_project_report.R
import com.example.android_project_report.Util.setOnSingleClickListener
import com.example.android_project_report.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var mBinding: ActivityMainBinding?= null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.manageContactsBtnLayout.setOnSingleClickListener {
            showFragment(ManageContactsFragment())
            Log.d("MainActivity", "연락처 관리 버튼 눌림")
        }

    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .add(R.id.main, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }
}