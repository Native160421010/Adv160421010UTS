package com.wildfire.adv160421010uts.view

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.Navigation
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.wildfire.adv160421010uts.R
import com.wildfire.adv160421010uts.databinding.FragmentLoginBinding
import com.wildfire.adv160421010uts.databinding.FragmentProfilBinding
import com.wildfire.adv160421010uts.view.profilFragmentDirections.Companion.actionLoginFragment
import com.wildfire.adv160421010uts.viewmodel.PrefManager

class profilFragment : Fragment() {
    private lateinit var oldNama: String
    private lateinit var newNama: String
    private lateinit var oldPass: String
    private lateinit var newPass: String
    private lateinit var prefManager: PrefManager
    private lateinit var binding: FragmentProfilBinding
    private val TAG = "volleyTag"
    private lateinit var confirmationDialog: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfilBinding.inflate(
            inflater,
            container, false
        )
        return binding.root
    }

    private fun updateNama(){
        oldNama = prefManager.getUsername().toString()
        val editableOldNama: Editable = Editable.Factory.getInstance().newEditable(oldNama)
        binding.txtOldUsername.text = editableOldNama
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefManager = PrefManager(requireContext())
        updateNama()

        binding.btnChangeProfil.setOnClickListener {
            newNama = binding.txtNewUsername.text.toString().trim()
            newPass = binding.txtNewPass.text.toString().trim()

            if(newNama.isEmpty() || newNama == ""){
                Toast.makeText(requireContext(), "Nama harus diisi!", Toast.LENGTH_SHORT).show()
                binding.txtNewUsername.requestFocus()
            }
            else if(newNama == oldNama && newPass == ""){
                Toast.makeText(requireContext(), "Data harus ada yang diganti!", Toast.LENGTH_SHORT).show()
                binding.txtNewUsername.requestFocus()
            }
            else{
                if(newNama.isEmpty()) newNama = ""
                if(newPass.isEmpty()) newPass = ""

                showConfirmationDialog()
            }
        }

        binding.btnLogout.setOnClickListener {
           prefManager.removeData()
            (activity as MainActivity).setBottomNavVisibility(View.GONE)
           val action = profilFragmentDirections.actionLoginFragment()
           Navigation.findNavController(requireView()).navigate(action)
        }
    }

    private fun showConfirmationDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.confirmation_pop_up, null)
        builder.setView(dialogView)

        val txtOldPass = dialogView.findViewById<EditText>(R.id.txtOldPassCon)
        val btnConfirm = dialogView.findViewById<Button>(R.id.btnConfirm)

        confirmationDialog = builder.create()
        confirmationDialog.show()

        btnConfirm.setOnClickListener {
            oldPass = txtOldPass.text.toString().trim()
            changeProfile()
            confirmationDialog.dismiss()
        }
    }

    private fun changeProfile(){
        val url = "http://10.0.2.2/news/changeProfile.php?oldNama=$oldNama&newNama=$newNama&oldPass=$oldPass&newPass=$newPass"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                val result = Gson().fromJson(response, LoginFragment.Response::class.java)
                if (result.result == "OK") {
                    Toast.makeText(requireContext(), "Data terupdate!", Toast.LENGTH_SHORT).show()
                    prefManager.setUsername(newNama)
                    updateNama()
                } else {
                    Toast.makeText(requireContext(), "Username/Password lama salah!", Toast.LENGTH_SHORT).show()
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