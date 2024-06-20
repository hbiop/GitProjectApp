package com.example.gitprojectapp.presentation.recycler_view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gitprojectapp.databinding.ItemBinding
import com.example.gitprojectapp.domain.models.gitRepository
import com.example.gitprojectapp.domain.models.mFile

class FileListAdapter(private val data: List<mFile>, private val listener: OnItemClickListener) : RecyclerView.Adapter<FileListAdapter.ViewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item.name)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }

        fun bind(name: String? = "", opisanie: String? = "", language: String? = "") {
            binding.textView.text = name
        }
    }
}