package com.wildfire.adv160421010uts.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.wildfire.adv160421010uts.databinding.FragmentLoginBinding
import com.wildfire.adv160421010uts.model.News
import com.wildfire.adv160421010uts.viewmodel.PrefManager

class LoginFragment : Fragment() {
    private lateinit var prefManager: PrefManager
    private lateinit var username: String
    private lateinit var password: String
    private lateinit var binding: FragmentLoginBinding
    private val TAG = "volleyTag"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(
            inflater,
            container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefManager = PrefManager(requireContext())

        binding.btnSIgnIn.setOnClickListener {
            username = binding.txtUsername.text.toString().trim()
            password = binding.txtPassword.text.toString().trim()

            if(username.isEmpty() || username == ""){
                Toast.makeText(requireContext(), "Nama harus diisi!", Toast.LENGTH_SHORT).show()
                binding.txtUsername.requestFocus()
            }
            else if (password.isEmpty() || password == ""){
                Toast.makeText(requireContext(), "Password harus diisi!", Toast.LENGTH_SHORT).show()
                binding.txtPassword.requestFocus()
            }
            else{
                val url = "http://10.0.2.2/news/checkUser.php?nama=$username&password=$password"
                val stringRequest = StringRequest(
                    Request.Method.GET, url,
                    { response ->
                        val result = Gson().fromJson(response, Response::class.java)
                        if (result.result == "OK") {
                            prefManager.setLoggin(true)
                            prefManager.setUsername(username)
                            Toast.makeText(requireContext(), "Welcome, $username!", Toast.LENGTH_SHORT).show()
                            val action = LoginFragmentDirections.actionMainFragment()
                            Navigation.findNavController(requireView()).navigate(action)
                            (activity as MainActivity).setBottomNavVisibility(View.VISIBLE)
                        } else {
                            Toast.makeText(requireContext(), "Username/Password salah!", Toast.LENGTH_SHORT).show()
                        }
                    },
                    { error ->
                        Log.d(TAG, error.toString())
                        Toast.makeText(requireContext(), "Database Error: $error", Toast.LENGTH_SHORT).show()
                    })
                stringRequest.tag = TAG
                Volley.newRequestQueue(requireContext()).add(stringRequest)
            }
        }
    }

    data class Response(val result: String, val message: String)
}