package com.example.gitprojectapp.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gitprojectapp.R
import com.example.gitprojectapp.databinding.FragmentSpisokFailovBinding
import com.example.gitprojectapp.presentation.recycler_view.FileListAdapter
import com.example.gitprojectapp.presentation.recycler_view.MyAdapter
import com.example.gitprojectapp.presentation.recycler_view.OnItemClickListener
import com.example.gitprojectapp.presentation.viewmodel.SpisokFailovViewModel
import com.example.gitprojectapp.presentation.viewmodel.SpisokRepositoriewViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SpisokFailov : Fragment() {
    lateinit var binding: FragmentSpisokFailovBinding
    lateinit var recyclerView: RecyclerView
    val viewModel: SpisokFailovViewModel by viewModels()
    private val repositoryName: String
        get() = requireArguments().getString(USER_ID_KEY).let { requireNotNull(it) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSpisokFailovBinding.inflate(layoutInflater)
        recyclerView = binding.listFiles
        recyclerView.layoutManager = LinearLayoutManager(context)
        viewModel.loadFiles(repositoryName)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.observe(viewLifecycleOwner){
            when(it){
                SpisokFailovViewModel.State.Empty -> {
                    Toast.makeText(context, "Пусто",Toast.LENGTH_LONG).show()
                }
                is SpisokFailovViewModel.State.Error -> {
                    Toast.makeText(context, it.error,Toast.LENGTH_LONG).show()
                }
                is SpisokFailovViewModel.State.Loaded -> {
                    val adapter = FileListAdapter(it.repos, object : OnItemClickListener {
                        override fun onItemClick(position: Int) {
                            if(it.repos[position].type == "dir"){
                                viewModel.goStraight(it.repos[position].path)
                                viewModel.loadFiles(repositoryName)
                            }
                        }
                    })
                    recyclerView.adapter = adapter
                }
                SpisokFailovViewModel.State.Loading -> {
                    Toast.makeText(context, "Загрузка",Toast.LENGTH_LONG).show()
                }
            }
        }
        binding.imageButton.setOnClickListener{
            viewModel.goBack()
            viewModel.loadFiles(repositoryName)
        }
    }
    companion object {
        private const val USER_ID_KEY = "0"
        fun createArguments(repositoryName: String): Bundle {
            return bundleOf(USER_ID_KEY to repositoryName)
        }
        @JvmStatic
        fun newInstance() =
            SpisokFailov().apply {

            }
    }
}