package com.example.gitprojectapp.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.gitprojectapp.R
import com.example.gitprojectapp.data.api.ApiInterface
import com.example.gitprojectapp.data.api.ApiUtilities
import com.example.gitprojectapp.databinding.FragmentRepositoryInfoBinding
import com.example.gitprojectapp.presentation.recycler_view.MyAdapter
import com.example.gitprojectapp.presentation.recycler_view.OnItemClickListener
import com.example.gitprojectapp.other.repository.GitRepository
import com.example.gitprojectapp.presentation.viewmodel.GitViewModel
import com.example.gitprojectapp.presentation.viewmodel.GitViewModelFactory


class RepositoryInfo : Fragment() {
    lateinit var binding: FragmentRepositoryInfoBinding
    private lateinit var gitViewModel: GitViewModel
    private val repositoryId: Int
        get() = requireArguments().getInt(USER_ID_KEY).let { requireNotNull(it) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val sharedPref = context?.getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
        val savedString = sharedPref?.getString("token", "default")
        binding = FragmentRepositoryInfoBinding.inflate(layoutInflater)
        val view = binding.root;
        val apiInterface: ApiInterface = ApiUtilities.getInstance().create(ApiInterface::class.java)
        val gitRepository: GitRepository = GitRepository(apiInterface, if(savedString == null){""}else{savedString})
        gitViewModel = ViewModelProvider(this, GitViewModelFactory(gitRepository)).get(GitViewModel::class.java)
        gitViewModel.gits.observe(viewLifecycleOwner) {
            binding.RepositoryName.text = it[repositoryId].name
            binding.StarCount.text = it[repositoryId].stargazers_count.toString()
            binding.BranchesCount.text = it[repositoryId].forks_count.toString()
            binding.ViewersCount.text = it[repositoryId].watchers_count.toString()
            //binding.ViewersCount.text = it[repositoryId].req
            binding.link.text = it[repositoryId].url
            binding.LicenseName.text = if(it[repositoryId].license == null){"Отсутствует"}else{it[repositoryId].license.toString()}
        }

        //binding.textViewId.text = userId.toString()
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