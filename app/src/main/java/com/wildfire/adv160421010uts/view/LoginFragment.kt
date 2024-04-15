package com.wildfire.adv160421010uts.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.wildfire.adv160421010uts.R
import com.wildfire.adv160421010uts.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView( inflater: LayoutInflater, container:
    ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLoginBinding.inflate(
            inflater,
            container, false
        )
        return binding.root
    }

    // Action pindah fragments
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Cek benar login, jika benar -> home
        binding.btnSIgnIn.setOnClickListener {
            val action = LoginFragmentDirections.actionMainFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }

}