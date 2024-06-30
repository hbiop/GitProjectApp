package com.example.gitprojectapp.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gitprojectapp.R
import com.example.gitprojectapp.databinding.FragmentSpisokFailovBinding
import com.example.gitprojectapp.domain.models.Branch
import com.example.gitprojectapp.presentation.recycler_view.FileListAdapter
import com.example.gitprojectapp.presentation.recycler_view.OnItemClickListener
import com.example.gitprojectapp.presentation.spinner.CustomAdapter
import com.example.gitprojectapp.presentation.viewmodel.SpisokFailovViewModel
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
        viewModel.loadBranches(repositoryName)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.branchState.observe(viewLifecycleOwner){
            when(it){
                SpisokFailovViewModel.BranchState.Empty -> {
                    Toast.makeText(context, "Пусто",Toast.LENGTH_LONG).show()
                }
                is SpisokFailovViewModel.BranchState.Error -> {
                    Toast.makeText(context, it.error,Toast.LENGTH_LONG).show()
                }
                is SpisokFailovViewModel.BranchState.Loaded -> {
                    val adapter = CustomAdapter(requireContext(), it.branches)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.spinner.adapter = adapter
                    binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                            val selectedItem = binding.spinner.selectedItem as Branch
                            Log.d("myLog", selectedItem.name)
                            viewModel.goDefault()
                            viewModel.loadFiles(repositoryName, selectedItem.name)
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {
                            // Действие при отсутствии выбора
                        }
                    }
                    val selectedItem = binding.spinner.selectedItem as Branch
                    viewModel.loadFiles(repositoryName, selectedItem.name)

                }
                SpisokFailovViewModel.BranchState.Loading -> {
                    binding.btnBack.isEnabled = false
                }
            }
        }
        viewModel.state.observe(viewLifecycleOwner){
            when(it){
                SpisokFailovViewModel.State.Empty -> {
                    Toast.makeText(context, "Пусто",Toast.LENGTH_LONG).show()
                }
                is SpisokFailovViewModel.State.Error -> {
                    Toast.makeText(context, it.error,Toast.LENGTH_LONG).show()
                }
                is SpisokFailovViewModel.State.Loaded -> {
                    binding.btnBack.isEnabled = true
                    val adapter = FileListAdapter(it.repos, object : OnItemClickListener {
                        override fun onItemClick(position: Int) {
                            try {
                                if(it.repos[position].type == "dir"){
                                    viewModel.goStraight(it.repos[position].path)
                                    val selectedItem = binding.spinner.selectedItem as Branch
                                    viewModel.loadFiles(repositoryName, selectedItem.name)
                                }
                                else{
                                    val selectedItem = binding.spinner.selectedItem as Branch
                                    Navigation.findNavController(view)
                                        .navigate(
                                            R.id.action_spisokFailov_to_fileReadFragment,
                                            FileReadFragment.createArgumentFileName(repositoryName,selectedItem.name,it.repos[position].path)
                                        )
                                }
                            }
                            catch (e: Exception){
                                Toast.makeText(context, "Данный формат файла не поддерживается", Toast.LENGTH_LONG).show()
                            }

                        }
                    })
                    recyclerView.adapter = adapter
                }
                SpisokFailovViewModel.State.Loading -> {
                    binding.btnBack.isEnabled = false
                }

                SpisokFailovViewModel.State.NavigateBack -> {
                    Navigation.findNavController(view).popBackStack()
                }
            }
        }
        binding.btnBack.setOnClickListener{
            viewModel.goBack()
            val selectedItem = binding.spinner.selectedItem as Branch
            viewModel.loadFiles(repositoryName, selectedItem.name)
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

