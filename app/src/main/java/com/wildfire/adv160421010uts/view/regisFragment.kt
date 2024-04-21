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
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.wildfire.adv160421010uts.databinding.FragmentRegisBinding
import com.wildfire.adv160421010uts.viewmodel.PrefManager

class regisFragment : Fragment() {
    private lateinit var prefManager: PrefManager
    private var username: String = ""
    private var namaDep: String = ""
    private var namaBel: String = ""
    private var password: String = ""
    private lateinit var binding: FragmentRegisBinding
    private val TAG = "volleyTag"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisBinding.inflate(
            inflater,
            container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefManager = PrefManager(requireContext())

        binding.btnSignUp.setOnClickListener {
            username = binding.txtUsernameRegis.text.toString().trim()
            password = binding.txtPasswordRegis.text.toString().trim()
            namaDep = binding.txtFrontNameRegis.text.toString().trim()
            namaBel = binding.txtBackNameRegis.text.toString().trim()

            if (username.isEmpty() || username == "") {
                Toast.makeText(requireContext(), "Nama harus diisi!", Toast.LENGTH_SHORT).show()
                binding.txtUsernameRegis.requestFocus()
            } else if (password.isEmpty() || password == "") {
                Toast.makeText(requireContext(), "Password harus diisi!", Toast.LENGTH_SHORT).show()
                binding.txtPasswordRegis.requestFocus()
            } else if (namaDep.isEmpty() || namaDep == "") {
                Toast.makeText(requireContext(), "Nama depan harus diisi!", Toast.LENGTH_SHORT).show()
                binding.txtFrontNameRegis.requestFocus()
            } else if (namaBel.isEmpty() || namaBel == "") {
                Toast.makeText(requireContext(), "Nama belakang harus diisi!", Toast.LENGTH_SHORT).show()
                binding.txtBackNameRegis.requestFocus()
            } else {
                val url =
                    "http://10.0.2.2/news/insertUser.php?nama=$username&password=$password&namaDep=$namaDep&namaBel=$namaBel"
                val stringRequest = StringRequest(
                    Request.Method.GET, url,
                    { response ->
                        try {
                            val result = Gson().fromJson(response, Response::class.java)
                            if (result.result == "OK") {
                                Toast.makeText(requireContext(), "Registrasi user $username berhasil!", Toast.LENGTH_SHORT).show()
                                val action = regisFragmentDirections.actionRegisToLoginFragment()
                                Navigation.findNavController(requireView()).navigate(action)
                                (activity as MainActivity).setBottomNavVisibility(View.VISIBLE)
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    result.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } catch (e: Exception) {
                            Log.e(TAG, "Error parsing JSON response: ${e.message}")
                            Toast.makeText(requireContext(), "Error parsing JSON response", Toast.LENGTH_SHORT).show()
                        }
                    },
                    { error ->
                        Log.d(TAG, error.toString())
                        Toast.makeText(requireContext(), "Database Error: $error", Toast.LENGTH_SHORT)
                            .show()
                    })
                stringRequest.tag = TAG
                Volley.newRequestQueue(requireContext()).add(stringRequest)
            }

            binding.txtSignIn.setOnClickListener {
                val action = regisFragmentDirections.actionRegisToLoginFragment()
                Navigation.findNavController(requireView()).navigate(action)
            }
        }
    }
}
