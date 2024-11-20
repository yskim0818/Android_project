package com.example.android_project_report.Main.Fragment

import android.app.ActivityManager
import android.app.Application
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.StatFs
import android.util.Log
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

        val versionString = Build.VERSION.RELEASE
        val version = versionString.split(".")[0].toIntOrNull() ?: 0

        val versionName = "Android: $versionString"
        binding.androidVersionText.text = versionName

        val storageCapacity = getInternalStorageCapacity()
        if (storageCapacity != null) {
            val totalSize = storageCapacity.first
            val availableSize = storageCapacity.second

            val totalSizeGB: Long
            val availableSizeGB: Long

            if (version >= 14) {
                totalSizeGB = totalSize / 1000000000
                availableSizeGB = availableSize / 1000000000
            } else {
                totalSizeGB = totalSize / (1024 * 1024 * 1024)
                availableSizeGB = availableSize / (1024 * 1024 * 1024)
            }

            binding.sdcardCapacityText.text = "${totalSizeGB}GB / ${availableSizeGB}GB"
        }

        val installedAppsCount = getInstalledAppsCount()
        val runningAppsCount = getRunningAppsCount()

//        binding.appNumberText.text = "$installedAppsCount/$runningAppsCount"

        //test

        val totalInstalledAppsCount = getAllInstalledAppsCount()
        val userAppsCount = getUserAppsCount()
        binding.appNumberText.text = "$userAppsCount / $runningAppsCount"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

    private fun getInternalStorageCapacity(): Pair<Long, Long> {
        val path = Environment.getDataDirectory().path
        val stat = StatFs(path)

        val blockSize: Long = stat.blockSizeLong
        val totalBlocks: Long = stat.blockCountLong
        val availableBlocks: Long = stat.availableBlocksLong

        val totalSize = blockSize * totalBlocks
        val availableSize = blockSize * availableBlocks

        return Pair(totalSize, availableSize)
    }

    private fun getInstalledAppsCount(): Int {
        val packageManager = requireContext().packageManager
        val apps = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)

        return apps.size
    }

    private fun getRunningAppsCount() : Int {
        val activityManager = requireContext().getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningAppProcess = activityManager.runningAppProcesses
        return runningAppProcess?.size ?: 0

//        val usageStatsManager =  requireContext().getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
//        val time = System.currentTimeMillis()
//        val stats = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 3600, time)
//
//        if (stats != null && stats.isNotEmpty()) {
//            return stats.size
//        }
//        return 0
    }
    //전체 앱 가져오기 test
    private fun getAllInstalledAppsCount(): Int {
        val packageManager = requireContext().packageManager
        val apps = packageManager.getInstalledPackages(0)
        return apps.size
    }

    private fun getUserAppsCount(): Int {
        val packageManager = requireContext().packageManager
        val apps = packageManager.getInstalledApplications(0)

        val userApps = apps.filter { appInfo ->
            appInfo.flags and ApplicationInfo.FLAG_SYSTEM == 0
        }

        return userApps.size
    }
}