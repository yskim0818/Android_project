package com.example.android_project_report.Main.MainData

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android_project_report.Data.UserData
import com.example.android_project_report.R
import com.example.android_project_report.databinding.MainDataViewAdapterBinding

class MainDataViewAdapter(private val context: Context): RecyclerView.Adapter<MainDataViewAdapter.ViewHolder>() {

    var userlist = mutableListOf<UserData>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = MainDataViewAdapterBinding.inflate(LayoutInflater.from(viewGroup.context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(userlist[position])
    }

    override fun getItemCount(): Int = userlist.size


    inner class ViewHolder(private val binding: MainDataViewAdapterBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: UserData) {
            binding.manageContactsAdapterText.text = data.name
        }
    }

}