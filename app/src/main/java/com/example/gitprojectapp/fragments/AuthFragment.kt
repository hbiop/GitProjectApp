package com.example.gitprojectapp.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitprojectapp.MainActivity
import com.example.gitprojectapp.R
import com.example.gitprojectapp.api.ApiInterface
import com.example.gitprojectapp.api.ApiUtilities
import com.example.gitprojectapp.databinding.FragmentAuthBinding
import com.example.gitprojectapp.repository.GitRepository
import com.example.gitprojectapp.viewmodel.GitViewModel
import com.example.gitprojectapp.viewmodel.GitViewModelFactory

class AuthFragment : Fragment() {
    private lateinit var gitViewModel: GitViewModel
    lateinit var binding: FragmentAuthBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //
        val sharedPref = context?.getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
        val editor = sharedPref?.edit()
        //
        binding = FragmentAuthBinding.inflate(inflater,container,false);
        lateinit var apiInterface: ApiInterface
        lateinit var gitRepository: GitRepository
        val view = binding.root;
        binding.Vhod.setOnClickListener{
            apiInterface = ApiUtilities.getInstance().create(ApiInterface::class.java)
            gitRepository = GitRepository(apiInterface, binding.edToken.text.toString())
            gitViewModel = ViewModelProvider(this, GitViewModelFactory(gitRepository)).get(GitViewModel::class.java)
            gitViewModel.isSuccessfull.observe(viewLifecycleOwner) {
                if (it) {
                    if (editor != null) {
                        editor.remove("token")
                        editor.apply()
                        editor.putString("token", binding.edToken.text.toString())
                        editor.apply()
                    }

                    Navigation.findNavController(view)
                        .navigate(R.id.action_authFragment_to_spisokRepositorievFragment)
                } else {
                    Toast.makeText(context, "Не удалось совершить вход", Toast.LENGTH_LONG).show()
                }
            }
        }
        return view
    }
}