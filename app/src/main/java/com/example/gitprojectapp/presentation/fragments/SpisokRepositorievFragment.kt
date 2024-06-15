package com.example.gitprojectapp.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitprojectapp.R
import com.example.gitprojectapp.data.api.ApiInterface
import com.example.gitprojectapp.data.api.ApiUtilities
import com.example.gitprojectapp.databinding.FragmentSpisokRepositorievBinding
import com.example.gitprojectapp.presentation.recycler_view.MyAdapter
import com.example.gitprojectapp.presentation.recycler_view.OnItemClickListener
import com.example.gitprojectapp.other.repository.GitRepository
import com.example.gitprojectapp.presentation.viewmodel.GitViewModel
import com.example.gitprojectapp.presentation.viewmodel.GitViewModelFactory


class SpisokRepositorievFragment : Fragment() {
    private lateinit var gitViewModel: GitViewModel
    lateinit var binding: FragmentSpisokRepositorievBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //
        val sharedPref = context?.getSharedPreferences("shared_prefs", Context.MODE_PRIVATE)
        val savedString = sharedPref?.getString("token", "default")
        //
        binding = FragmentSpisokRepositorievBinding.inflate(layoutInflater)
        val view = binding.root;
        val apiInterface: ApiInterface = ApiUtilities.getInstance().create(ApiInterface::class.java)
        val gitRepository: GitRepository = GitRepository(apiInterface, if(savedString == null){""}else{savedString})
        val recyclerView = binding.recyclerview
        recyclerView.layoutManager = LinearLayoutManager(context)
        gitViewModel = ViewModelProvider(this, GitViewModelFactory(gitRepository)).get(GitViewModel::class.java)
        gitViewModel.gits.observe(viewLifecycleOwner) {
            val adapter = MyAdapter(it, object : OnItemClickListener {
                override fun onItemClick(position: Int) {
                    Navigation.findNavController(view)
                        .navigate(R.id.action_spisokRepositorievFragment_to_repositoryInfo,
                            RepositoryInfo.createArguments(userIdKey = position)
                        )
                }
            })
            recyclerView.adapter = adapter
        }
        return view
    }

    /*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gitViewModel.gits.observe(viewLifecycleOwner,{
            val adapter = MyAdapter(it)
            binding.recyclerView.adapter = adapter
        })
    }*/

}