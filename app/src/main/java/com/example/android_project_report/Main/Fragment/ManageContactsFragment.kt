package com.example.android_project_report.Main.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_project_report.Data.UserData
import com.example.android_project_report.Main.MainActivity
import com.example.android_project_report.Main.MainData.MainDataViewAdapter
import com.example.android_project_report.R
import com.example.android_project_report.Util.setOnSingleClickListener
import com.example.android_project_report.databinding.FragmentManageContactsBinding

class ManageContactsFragment: Fragment() {

    private var mBinding: FragmentManageContactsBinding?= null
    private val binding get() = mBinding!!

    private lateinit var adapter: MainDataViewAdapter
    private val mDatas = mutableListOf<UserData>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentManageContactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sActivity = requireActivity() as MainActivity
        sActivity.setTitle("연락처 관리")

        binding.addContactBtn.setOnSingleClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_manage_contacts, AddContactFragment())
                .addToBackStack(null)
                .commit()
            Log.d("ManageContactsFragment", "addContactBtn 클릭")
        }

        initRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

    private fun initRecyclerView() {
        initializelist()

        adapter = MainDataViewAdapter(requireContext())
        adapter.userlist = mDatas

        binding.recyclerViewManageContactsList.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewManageContactsList.adapter = adapter

    }

    private fun initializelist() {
        mDatas.add(UserData("홍길동"))
        mDatas.add(UserData("김철수"))
        mDatas.add(UserData("이수민"))
        mDatas.add(UserData("김예성"))
    }

}