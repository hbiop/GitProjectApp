package com.example.gitprojectapp.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gitprojectapp.R
import com.example.gitprojectapp.databinding.FragmentSpisokRepositorievBinding
import com.example.gitprojectapp.presentation.recycler_view.MyAdapter
import com.example.gitprojectapp.presentation.recycler_view.OnItemClickListener
import com.example.gitprojectapp.domain.usecases.GetTokenUseCase
import com.example.gitprojectapp.presentation.viewmodel.SpisokRepositoriewViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SpisokRepositorievFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    val viewModel: SpisokRepositoriewViewModel by viewModels()
    lateinit var binding: FragmentSpisokRepositorievBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSpisokRepositorievBinding.inflate(layoutInflater)
        val view = binding.root;
        recyclerView = binding.recyclerview
        recyclerView.layoutManager = LinearLayoutManager(context)
        viewModel.loadRepos()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is SpisokRepositoriewViewModel.State.Error -> {
                    Toast.makeText(context, it.error, Toast.LENGTH_LONG).show()
                }

                is SpisokRepositoriewViewModel.State.Empty -> {
                    Toast.makeText(context, "Пусто", Toast.LENGTH_LONG).show()
                }

                is SpisokRepositoriewViewModel.State.Loading -> {
                    Toast.makeText(context, "Загрузка", Toast.LENGTH_LONG).show()
                }

                is SpisokRepositoriewViewModel.State.Loaded -> {
                    val adapter = MyAdapter(it.repos, object : OnItemClickListener {
                        override fun onItemClick(position: Int) {
                            Navigation.findNavController(view)
                                .navigate(
                                    R.id.action_spisokRepositorievFragment_to_repositoryInfo,
                                    RepositoryInfo.createArguments(repositoryName = it.repos[position].name)
                                )
                        }
                    })
                    recyclerView.adapter = adapter
                }
            }
        }
    }
}