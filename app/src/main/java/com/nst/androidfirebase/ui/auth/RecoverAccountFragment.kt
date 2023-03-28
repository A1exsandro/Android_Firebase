package com.nst.androidfirebase.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.nst.androidfirebase.R
import com.nst.androidfirebase.databinding.FragmentRecoverAccountBinding
import com.nst.androidfirebase.databinding.FragmentSignUpBinding
import com.nst.androidfirebase.helper.BaseFragment
import com.nst.androidfirebase.helper.FirebaseHelper

class RecoverAccountFragment : Fragment() {

    private var _binding: FragmentRecoverAccountBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecoverAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        initToolbar(binding.toolbar)

        auth = Firebase.auth
        initClicks()
    }

    private fun initClicks() {
        binding.btnRecover.setOnClickListener { checkData() }
    }

    private fun checkData() {
        val email = binding.editEmail.toString().trim()

        if (email.isNotBlank()) {

//            hideKeyboard()
            binding.progressBar.isVisible = true

            recoverUser(email)
        } else {
        Toast.makeText(requireContext(), "Informe seu e-mail", Toast.LENGTH_SHORT).show()
        }
    }

    private fun recoverUser(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(), "Verifique seu email", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), FirebaseHelper.validError(task.exception?.message ?: ""), Toast.LENGTH_SHORT).show()
                }

                binding.progressBar.isVisible = false

            }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}