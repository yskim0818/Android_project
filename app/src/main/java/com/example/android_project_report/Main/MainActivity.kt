package com.example.android_project_report.Main

import android.app.AppOpsManager
import android.app.AppOpsManager.MODE_ALLOWED
import android.app.AppOpsManager.OPSTR_GET_USAGE_STATS
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.text.font.FontVariation
import com.example.android_project_report.Main.Fragment.AboutauthorFragment
import com.example.android_project_report.Main.Fragment.AddContactFragment
import com.example.android_project_report.Main.Fragment.ContactInfoFragment
import com.example.android_project_report.Main.Fragment.InternetSearchFragment
import com.example.android_project_report.Main.Fragment.ManageContactsFragment
import com.example.android_project_report.Main.Fragment.PhoneBehaviorStatusFragment
import com.example.android_project_report.Main.Fragment.StartMenuFragment
import com.example.android_project_report.R
import com.example.android_project_report.Util.setOnSingleClickListener
import com.example.android_project_report.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val menuFragment: StartMenuFragment by lazy { StartMenuFragment() }


    private var mBinding: ActivityMainBinding?= null
    private val binding get() = mBinding!!

    private var mainTitle: TextView? = null
    private var menuTitle: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainTitle = binding.startTitle
        menuTitle = binding.menuTitle
        menuTitle?.visibility = View.GONE
        binding.backBtn.visibility = View.INVISIBLE

        supportFragmentManager.beginTransaction().replace(R.id.container, menuFragment).commit()

//        if (!checkForPermission()) {
//            Log.d("MainActivity", "Usage stats permission not granted")
//            startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
//            // 권한 요청
//        } else {
//            Log.d("MainActivity", "Usage stats permission granted")
//        }

    }

    override fun onResume() {
        super.onResume()
        binding.backBtn.setOnSingleClickListener {
            this.onBackPressedDispatcher.onBackPressed()
        }

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

//    private fun showFragment(fragment: Fragment) {
//        supportFragmentManager.beginTransaction()
//            .add(R.id.main, fragment)
//            .addToBackStack(null)
//            .commit()
//    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }

    fun setBackButton(isHide: Boolean) {
        if (isHide) {
            binding.backBtn.visibility = View.INVISIBLE
            binding.startTitle.visibility = View.VISIBLE
            binding.menuTitle.visibility = View.INVISIBLE
        } else {
            binding.backBtn.visibility = View.VISIBLE
            binding.startTitle.visibility = View.INVISIBLE
            binding.menuTitle.visibility = View.VISIBLE
        }
    }

    fun setTitle(titleName: String) {
        binding.startTitle.visibility = View.INVISIBLE
        binding.menuTitle.visibility = View.VISIBLE
        binding.menuTitle.text = titleName
        binding.backBtn.visibility = View.VISIBLE
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            val lastFragment = supportFragmentManager.fragments.last()
            when (lastFragment) {
                is ManageContactsFragment -> {
                    setBackButton(true)
//                    setTitle("연락처 관리")
                    this@MainActivity.supportFragmentManager.popBackStack()
                }
                is AddContactFragment -> {
                    setBackButton(false)
                    setTitle("연락처 관리")
                    this@MainActivity.supportFragmentManager.popBackStack()
                }
                is ContactInfoFragment -> {
                    setBackButton(false)
                    setTitle("연락처 관리")
                    this@MainActivity.supportFragmentManager.popBackStack()
                }
                is InternetSearchFragment -> {
                    setBackButton(true)
                    this@MainActivity.supportFragmentManager.popBackStack()
                }
                is PhoneBehaviorStatusFragment -> {
                    setBackButton(true)
                    this@MainActivity.supportFragmentManager.popBackStack()
                }
                is AboutauthorFragment -> {
                    setBackButton(true)
                    this@MainActivity.supportFragmentManager.popBackStack()
                }
            }
        }
    }

    private fun checkForPermission(): Boolean {
        val appOps = getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode = appOps.checkOpNoThrow(OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), packageName)
        return mode == MODE_ALLOWED
    }
}