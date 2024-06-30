package com.example.gitprojectapp.presentation.fragments

import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.gitprojectapp.databinding.FragmentFileReadBinding
import com.example.gitprojectapp.presentation.viewmodel.FileReadViewModel
import com.example.gitprojectapp.presentation.viewmodel.RepositoryInfoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FileReadFragment : Fragment() {
    val viewModel: FileReadViewModel by viewModels()

    lateinit var binding: FragmentFileReadBinding
    private val repositoryName: String
        get() = requireArguments().getString(REPOSITORY).let { requireNotNull(it) }
    private val branchName: String
        get() = requireArguments().getString(BRANCHNAME).let { requireNotNull(it) }
    private val filePath: String
        get() = requireArguments().getString(FILEPATH).let { requireNotNull(it) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFileReadBinding.inflate(inflater, container, false);
        val view = binding.root;
        Log.d("myLog", "repositoryName is $repositoryName")
        Log.d("myLog", "branchName is $branchName")
        Log.d("myLog", "filePath is $filePath")
        viewModel.loadFiles(repositoryName, branchName, filePath)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBack.setOnClickListener {
            Navigation.findNavController(view).popBackStack()
        }
        viewModel.state.observe(viewLifecycleOwner){
            when(it){
                FileReadViewModel.State.Empty -> {
                    Toast.makeText(context, "Пусто", Toast.LENGTH_LONG).show()
                }
                is FileReadViewModel.State.Error -> {
                    Toast.makeText(context, it.error, Toast.LENGTH_LONG).show()
                }
                is FileReadViewModel.State.Loaded -> {
                    binding.codeViewer.setText(String(java.util.Base64.getMimeDecoder().decode(it.repos.content)))
                }
                FileReadViewModel.State.Loading ->{
                }
            }
        }
    }

    companion object {
        private const val REPOSITORY = "repositoryName"
        private const val BRANCHNAME = "branchName"
        private const val FILEPATH = "filePath"
        fun createArgumentFileName(repositoryName: String,fileName: String,path: String): Bundle{
            return bundleOf((BRANCHNAME to fileName),(FILEPATH to path),(REPOSITORY to repositoryName))
        }
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FileReadFragment().apply {

            }
    }
}