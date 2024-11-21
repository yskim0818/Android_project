package com.example.android_project_report.Main.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.util.doubleFromBits
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_project_report.Data.DBHelper
import com.example.android_project_report.Data.UserData
import com.example.android_project_report.Main.MainActivity
import com.example.android_project_report.Main.MainData.MainDataViewAdapter
import com.example.android_project_report.R
import com.example.android_project_report.Util.setOnSingleClickListener
import com.example.android_project_report.databinding.FragmentManageContactsBinding

class ManageContactsFragment: Fragment() {

    private var mBinding: FragmentManageContactsBinding?= null
    private val binding get() = mBinding!!

    lateinit var adapter: MainDataViewAdapter
    private val mDatas = mutableListOf<UserData>()
    private lateinit var dbHelper: DBHelper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentManageContactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbHelper = DBHelper(requireContext())

        val sActivity = requireActivity() as MainActivity
        sActivity.setTitle("연락처 관리")

        initRecyclerView()
        loadData()

//        adapter = MainDataViewAdapter(requireContext())
//        binding.recyclerViewManageContactsList.layoutManager = LinearLayoutManager(requireContext())
//        binding.recyclerViewManageContactsList.adapter = adapter

        binding.addContactBtn.setOnSingleClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_manage_contacts, AddContactFragment())
                .addToBackStack(null)
                .commit()
            Log.d("ManageContactsFragment", "addContactBtn 클릭")
        }

//        initRecyclerView()
//        loadData()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

    private fun initRecyclerView() {
        adapter = MainDataViewAdapter(requireContext())
        binding.recyclerViewManageContactsList.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewManageContactsList.adapter = adapter
    }

    fun loadData() {
        val users = dbHelper.getAllUsers()

        mDatas.clear()
        mDatas.addAll(users)

        adapter.userlist = mDatas
        adapter.notifyDataSetChanged()
    }

}