package com.example.gitprojectapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.gitprojectapp.R
import com.example.gitprojectapp.databinding.FragmentRepositoryInfoBinding


class RepositoryInfo : Fragment() {
    lateinit var binding: FragmentRepositoryInfoBinding
    private val userId: Int
        get() = requireArguments().getInt(USER_ID_KEY).let { requireNotNull(it) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRepositoryInfoBinding.inflate(layoutInflater)
        val view = binding.root;
        binding.textViewId.text = userId.toString()
        return view
    }

    companion object {
        private const val USER_ID_KEY = "0"
        fun createArguments(userIdKey: Int): Bundle {
            return bundleOf(USER_ID_KEY to userIdKey)
        }
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RepositoryInfo().apply {
                arguments = Bundle().apply {

                }
            }
    }
}