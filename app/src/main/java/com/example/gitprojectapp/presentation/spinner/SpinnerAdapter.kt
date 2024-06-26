package com.example.gitprojectapp.presentation.spinner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.gitprojectapp.domain.models.Branch


class CustomAdapter(context: Context, private val items: List<Branch>) : ArrayAdapter<Branch>(context, 0, items) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_item, parent, false)

        val item = items[position]

        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = item.name

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false)

        val item = items[position]

        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = item.name

        return view
    }
}
