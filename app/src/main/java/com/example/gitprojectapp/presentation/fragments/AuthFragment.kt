package com.example.gitprojectapp.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.gitprojectapp.R
import com.example.gitprojectapp.data.api.ApiInterface
import com.example.gitprojectapp.data.api.ApiUtilities
import com.example.gitprojectapp.databinding.FragmentAuthBinding
import com.example.gitprojectapp.other.repository.GitRepository
import com.example.gitprojectapp.presentation.usecases.GetTokenUseCase
import com.example.gitprojectapp.presentation.usecases.SaveTokenUseCase
import com.example.gitprojectapp.presentation.viewmodel.AuthViewModel
import com.example.gitprojectapp.presentation.viewmodel.GitViewModel
import com.example.gitprojectapp.presentation.viewmodel.GitViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class AuthFragment : Fragment() {
    @Inject
    lateinit var saveTokenUseCase: SaveTokenUseCase
    val viewModel: AuthViewModel by viewModels()
    lateinit var binding: FragmentAuthBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAuthBinding.inflate(inflater,container,false);
        val view = binding.root;
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.action.observe(viewLifecycleOwner){
            when (it) {
                is AuthViewModel.Action.ShowError -> {
                    Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                }
                is AuthViewModel.Action.RouteToMain -> {
                    saveTokenUseCase.execute(viewModel.userList.value!!, binding.edToken.text.toString())
                    Navigation.findNavController(view)
                        .navigate(R.id.action_authFragment_to_spisokRepositorievFragment)
                }
            }
        }
        viewModel.state.observe(viewLifecycleOwner){
            when (it) {
                is AuthViewModel.State.Idle -> {
                    binding.Vhod.text = "Sign up"
                    binding.Vhod.isEnabled = true
                }
                is AuthViewModel.State.Loading -> {
                    binding.Vhod.text = "Loading"
                    binding.Vhod.isEnabled = false
                }
                is AuthViewModel.State.InvalidInput -> {
                    binding.Vhod.text = "Invalid"
                    binding.Vhod.isEnabled = true
                }
            }
        }
        binding.Vhod.setOnClickListener{
            Toast.makeText(getActivity(),"Message", Toast.LENGTH_SHORT).show();
            viewModel.loadUsers(binding.edToken.text.toString())
        }
    }
}


//Navigation.findNavController(view)
//.navigate(R.id.action_authFragment_to_spisokRepositorievFragment)