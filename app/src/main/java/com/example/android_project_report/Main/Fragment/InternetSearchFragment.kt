package com.example.android_project_report.Main.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.android_project_report.Main.MainActivity
import com.example.android_project_report.Util.setOnSingleClickListener
import com.example.android_project_report.databinding.FragmentInternetSearchBinding

class InternetSearchFragment: Fragment() {

    private var mBinding: FragmentInternetSearchBinding?= null
    private val binding get() = mBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentInternetSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sActivity = requireActivity() as MainActivity
        sActivity.setTitle("인터넷 검색")

        binding.urlWebview.settings.javaScriptEnabled = true
        binding.urlWebview.settings.domStorageEnabled = true
        binding.urlWebview.settings.useWideViewPort = true

        binding.urlMoveBtn.setOnSingleClickListener {
            val url = binding.urlEdittext.text.toString().trim()

            if (url.isNotEmpty()) {
                val formattedUrl = formatUrl(url)
                binding.urlWebview.webViewClient = WebViewClient()
                binding.urlWebview.loadUrl(formattedUrl)
            } else {
                Toast.makeText(requireContext(), "URL을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun formatUrl(url: String): String {
        return if (url.startsWith("http://") || url.startsWith("https://")) {
            url
        } else {
            "https://$url"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }
}