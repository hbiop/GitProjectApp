package com.example.gitprojectapp.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.gitprojectapp.R
import com.example.gitprojectapp.databinding.FragmentRepositoryInfoBinding
import com.example.gitprojectapp.domain.usecases.GetTokenUseCase
import com.example.gitprojectapp.presentation.viewmodel.RepositoryInfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.noties.markwon.Markwon
import java.util.Base64
import javax.inject.Inject


@AndroidEntryPoint
class RepositoryInfo : Fragment() {
    lateinit var binding: FragmentRepositoryInfoBinding
    lateinit var markwon: Markwon
    val viewModel: RepositoryInfoViewModel by viewModels()
    private val repositoryName: String
        get() = requireArguments().getString(USER_ID_KEY).let { requireNotNull(it) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        markwon = Markwon.create(requireContext())
        binding = FragmentRepositoryInfoBinding.inflate(layoutInflater)
        val view = binding.root;
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBack.setOnClickListener {
            Navigation.findNavController(view).popBackStack()
        }
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is RepositoryInfoViewModel.State.Error -> {
                    Toast.makeText(context, it.error, Toast.LENGTH_LONG).show()
                }

                is RepositoryInfoViewModel.State.Loaded -> {
                    binding.btnCode.isEnabled = true
                    binding.btnBack.isEnabled = true
                    binding.RepositoryName.text = it.githubRepo.name
                    binding.link.text = it.githubRepo.url
                    binding.LicenseName.text = it.githubRepo.license
                    binding.StarCount.text = "stargazers ${it.githubRepo.stargazersCount.toString()}"
                    binding.BranchesCount.text = "forks ${it.githubRepo.forksCount.toString()}"
                    binding.ViewersCount.text = "watchers ${it.githubRepo.watchersCount.toString()}"
                    binding.btnCode.setOnClickListener{a ->
                        Navigation.findNavController(view)
                            .navigate(
                                R.id.action_repositoryInfo_to_spisokFailov,
                                SpisokFailov.createArguments(repositoryName = it.githubRepo.name)
                            )
                    }
                    markwon.setMarkdown(
                        binding.Readme,
                        String(Base64.getMimeDecoder().decode(it.markdown))
                    )
                }

                RepositoryInfoViewModel.State.Loading -> {
                    binding.btnCode.isEnabled = false
                }
            }
        }
        viewModel.loadRepositoryInfo(repositoryName)
    }

    companion object {
        private const val USER_ID_KEY = "0"
        fun createArguments(repositoryName: String): Bundle {
            return bundleOf(USER_ID_KEY to repositoryName)
        }

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RepositoryInfo().apply {
                arguments = Bundle().apply {

                }
            }
    }
}