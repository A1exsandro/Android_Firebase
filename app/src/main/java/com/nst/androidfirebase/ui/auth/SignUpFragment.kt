package com.nst.androidfirebase.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import com.nst.androidfirebase.R
import com.nst.androidfirebase.databinding.FragmentLoginBinding
import com.nst.androidfirebase.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun checkData() {
        val email = binding.editEmail.toString().trim()
        val password = binding.editPassword.toString().trim()

        if (email.isNotBlank()) {
            if (password.isNotBlank()) {
                binding.progressBar.isVisible = true

                registerUser(email, password)
            } else {
                Toast.makeText(requireContext(), "Informe seu password", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(requireContext(), "Informe seu e-mail", Toast.LENGTH_SHORT).show()
        }
    }

    private fun registerUser(email: String, password: String) {

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}