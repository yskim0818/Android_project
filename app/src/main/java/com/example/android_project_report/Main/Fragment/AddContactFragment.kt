package com.example.android_project_report.Main.Fragment

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.android_project_report.Data.DBHelper
import com.example.android_project_report.Main.MainActivity
import com.example.android_project_report.Util.setOnSingleClickListener
import com.example.android_project_report.databinding.FragmentAddContactBinding

class AddContactFragment: Fragment() {

    private var mBinding: FragmentAddContactBinding?= null
    private val binding get() = mBinding!!
    private lateinit var dbHelper: DBHelper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentAddContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sActivity = requireActivity() as MainActivity
        sActivity.setTitle("연락처 추가")

        dbHelper = DBHelper(requireContext())

        binding.addContactSaveBtn.setOnClickListener {
            saveContact()
            val manageContactsFragment = requireActivity().supportFragmentManager.findFragmentByTag("ManageContactsFragment") as? ManageContactsFragment
            manageContactsFragment?.let {
                it.loadData()
                it.adapter.notifyDataSetChanged()
            }
            requireActivity().supportFragmentManager.beginTransaction()
                .remove(this)
                .commit()
            sActivity.setTitle("연락처 관리")
            sActivity.supportFragmentManager.popBackStack()
        }

        binding.addContactCancelBtn.setOnSingleClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
//                .addToBackStack(null)
                .remove(this)
                .commit()
            sActivity.setTitle("연락처 관리")
//            requireActivity().supportFragmentManager.popBackStack()
            sActivity.supportFragmentManager.popBackStack()
        }

    }

    private fun saveContact() {
        val name = binding.addContactEdittextName.text.toString().trim()
        val phone = binding.addContactEdittextPhoneNumber.text.toString().trim()
        val email = binding.addContactEdittextEmail.text.toString().trim()
        val workPlace = binding.addContactEdittextWorkPlace.text.toString().trim()
        val jobTitle = binding.addContactEdittextJobTitle.text.toString().trim()

        if (name.isNotEmpty() && phone.isNotEmpty()) {
            val db = dbHelper.writableDatabase

            val values = ContentValues().apply {
                put(DBHelper.COLUMN_NAME, name)
                put(DBHelper.COLUMN_PHONE, phone)
                put(DBHelper.COLUMN_EMAIL, email)
                put(DBHelper.COLUMN_WORK_PLACE, workPlace)
                put(DBHelper.COLUMN_JOB_TITLE, jobTitle)
            }

            db.insert(DBHelper.TABLE_NAME, null, values)

//            if (rowId != -1L) {
//                Log.d("AddContactFragment", "Contact saved with ID: $rowId")
//            } else {
//                Log.d("AddContactFragment", "Failed to save contact")
//            }
        }
//        } else {
//            Log.d("AddContactFragment", "Name and phone number are required")
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }
}