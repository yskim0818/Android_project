package com.example.android_project_report.Main.Fragment

import android.app.AppOpsManager
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Context.APP_OPS_SERVICE
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.StatFs
import android.os.Process
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.android_project_report.Main.MainActivity
import com.example.android_project_report.databinding.FragmentPhoneBehaviorStatusBinding
import java.lang.reflect.Method

class PhoneBehaviorStatusFragment: Fragment() {

    private var mBinding: FragmentPhoneBehaviorStatusBinding? = null
    private val binding get() = mBinding!!

    private var isPermissionChecked = false
    private var isPermissionDialogShown = false

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

        val wifiEnabled = isWifiEnabled(requireContext())
        binding.wifiStatusText.text = if (wifiEnabled) "ON" else "OFF"

        val mobileDataEnabled = isMobileDataEnabled(requireContext())
        binding.lteStatusText.text = if (mobileDataEnabled) "ON" else "OFF"

        val oneUIVersionInt = getOneUIVersion()?.toIntOrNull() ?: 0

        val storageCapacity = getInternalStorageCapacity()
        if (storageCapacity != null) {
            val totalSize = storageCapacity.first
            val availableSize = storageCapacity.second

            val totalSizeGB: Long
            val availableSizeGB: Long

            if (version >= 14 && oneUIVersionInt >= 60000) {
                totalSizeGB = totalSize / 1000000000
                availableSizeGB = availableSize / 1000000000
            } else {
                totalSizeGB = totalSize / (1024 * 1024 * 1024)
                availableSizeGB = availableSize / (1024 * 1024 * 1024)
            }

            binding.sdcardCapacityText.text = "${totalSizeGB}GB / ${availableSizeGB}GB"
        }

        val runningAppsCount = getRunningAppsCount()
<<<<<<< HEAD
=======


        val totalInstalledAppsCount = getAllInstalledAppsCount()
>>>>>>> a64249115bbf009b34f963c44e687383d92fbbf4
        val userAppsCount = getUserAppsCount()
        binding.appNumberText.text = "$userAppsCount / $runningAppsCount"
    }

    override fun onResume() {
        super.onResume()

        if (!checkPermissionUsage()) {
            return
        }

        if (!isPermissionChecked) {
            val runningAppsCount = getRunningAppsCount()
            val userAppsCount = getUserAppsCount()
            binding.appNumberText.text = "$userAppsCount / $runningAppsCount"
            isPermissionChecked = true
        }
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

<<<<<<< HEAD
=======
    private fun getInstalledAppsCount(): Int {
        val packageManager = requireContext().packageManager
        val apps = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)

        return apps.size
    }

    private fun getAllInstalledAppsCount(): Int {
        val packageManager = requireContext().packageManager
        val apps = packageManager.getInstalledPackages(0)
        return apps.size
    }

>>>>>>> a64249115bbf009b34f963c44e687383d92fbbf4
    private fun getUserAppsCount(): Int {
        val packageManager = requireContext().packageManager
        val apps = packageManager.getInstalledApplications(0)

        val userApps = apps.filter { appInfo ->
            appInfo.flags and ApplicationInfo.FLAG_SYSTEM == 0
        }

        return userApps.size
    }

    private fun checkPermissionUsage(): Boolean {
        val appOps = requireContext().getSystemService(APP_OPS_SERVICE) as AppOpsManager
        val mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, Process.myUid(), requireContext().packageName)

        if (mode != AppOpsManager.MODE_ALLOWED) {
            if (!isPermissionDialogShown) {
                openUsageAccessSettings()
                isPermissionDialogShown = true
            }
            return false
        }
        return true
    }

    private fun openUsageAccessSettings() {
        val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
        startActivity(intent)
    }

    private fun getRunningAppsCount(): Int {
        if (!checkPermissionUsage()) {
            return 0
        }

        val usageStateManager = requireContext().getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        val time = System.currentTimeMillis()
        val stats = usageStateManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 3600, time)

        val userApps = stats?.filter { usageStat ->
            try {
                val appInfo = requireContext().packageManager.getApplicationInfo(usageStat.packageName, 0)
                appInfo.flags and ApplicationInfo.FLAG_SYSTEM == 0
            } catch (e: PackageManager.NameNotFoundException) {
                false
            }
        } ?: emptyList()

        return userApps.size
    }

    private fun isWifiEnabled(context: Context): Boolean {
        val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        return wifiManager.isWifiEnabled
    }

    private fun isMobileDataEnabled(context: Context): Boolean {
        try {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val method: Method = connectivityManager.javaClass.getDeclaredMethod("getMobileDataEnabled")
            method.isAccessible = true
            return method.invoke(connectivityManager) as Boolean
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    private fun getOneUIVersion(): String? {
        return try {
            val systemPropertiesClass = Class.forName("android.os.SystemProperties")
            val getMethod: Method = systemPropertiesClass.getMethod("get",String::class.java,String::class.java)

            getMethod.invoke(null, "ro.build.version.oneui", null) as? String
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}