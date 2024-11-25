package com.example.android_project_report.Main.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.android_project_report.Data.DBHelper
import com.example.android_project_report.Data.UserData
import com.example.android_project_report.Main.MainActivity
import com.example.android_project_report.Util.setOnSingleClickListener
import com.example.android_project_report.databinding.FragmentContactInfoBinding

class ContactInfoFragment: Fragment() {

    private var mBinding: FragmentContactInfoBinding?= null
    private val binding get() = mBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentContactInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sActivity = requireActivity() as MainActivity
        sActivity.setTitle("연락처 정보")

        arguments.let {
            binding.infoContactEdittextName.setText(it?.getString("name"))
            binding.infoContactEdittextPhoneNumber.setText(it?.getString("phone"))
            binding.infoContactEdittextEmail.setText(it?.getString("email"))
            binding.infoContactEdittextWorkPlace.setText(it?.getString("workPlace"))
            binding.infoContactEdittextJobTitle.setText(it?.getString("jobTitle"))
        }

        binding.infoContactSaveBtn.setOnSingleClickListener {
            val name = binding.infoContactEdittextName.text.toString().trim()
            val phone = binding.infoContactEdittextPhoneNumber.text.toString().trim()
            val email = binding.infoContactEdittextEmail.text.toString().trim()
            val workPlace = binding.infoContactEdittextWorkPlace.text.toString().trim()
            val jobTitle = binding.infoContactEdittextJobTitle.text.toString().trim()

            if (name.isEmpty()) {
                Toast.makeText(requireContext(),"이름을 입력해주세요", Toast.LENGTH_SHORT).show()
                binding.infoContactEdittextName.requestFocus()
                return@setOnSingleClickListener
            }
            if (phone.isEmpty()) {
                Toast.makeText(requireContext(),"전화번호를 입력해주세요", Toast.LENGTH_SHORT).show()
                binding.infoContactEdittextPhoneNumber.requestFocus()
                return@setOnSingleClickListener
            }

            val updateUser = UserData(
                arguments?.getLong("id") ?: 0,
                name,
                phone,
                email,
                workPlace,
                jobTitle
            )

            val dbHelper = DBHelper(requireContext())
            val isUpdated = dbHelper.updateUser(updateUser)

            if (isUpdated) {
                Toast.makeText(requireContext(),"수정 완료", Toast.LENGTH_SHORT).show()
                requireActivity().supportFragmentManager.popBackStack()
            } else {
                Toast.makeText(requireContext(),"수정 실패", Toast.LENGTH_SHORT).show()
            }
        }

        binding.infoContactShareBtn.setOnClickListener {
            val name = binding.infoContactEdittextName.text.toString().trim()
            val phone = binding.infoContactEdittextPhoneNumber.text.toString().trim()
            val email = binding.infoContactEdittextEmail.text.toString().trim()
            val workPlace = binding.infoContactEdittextWorkPlace.text.toString().trim()
            val jobTitle = binding.infoContactEdittextJobTitle.text.toString().trim()

            val contactInfo = """
                이름: $name
                전화번호: $phone
                이메일: $email
                직장명: $workPlace
                직함: $jobTitle
            """.trimIndent()

            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, contactInfo)
            }
            startActivity(Intent.createChooser(shareIntent, "연락처 공유"))
        }

        binding.infoContactDeleteBtn.setOnSingleClickListener {
            val userId = arguments?.getLong("id") ?: 0

            if (userId != 0L) {
                val dbHelper = DBHelper(requireContext())
                val isDeleted = dbHelper.deleteUser(userId)

                if (isDeleted) {
                    Toast.makeText(requireContext(),"삭제 완료", Toast.LENGTH_SHORT).show()
                    requireActivity().supportFragmentManager.popBackStack()
                } else {
                    Toast.makeText(requireContext(),"삭제 실패", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(),"잘못된 사용자 ID", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }
}