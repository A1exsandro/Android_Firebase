package com.nst.androidfirebase.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nst.androidfirebase.R
import com.nst.androidfirebase.databinding.FragmentFormTaskBinding
import com.nst.androidfirebase.helper.BaseFragment
import com.nst.androidfirebase.helper.FirebaseHelper
import com.nst.androidfirebase.model.Task

class FormTaskFragment : BaseFragment() {

    private val args: FormTaskFragmentArgs by navArgs()

    private var _binding: FragmentFormTaskBinding? = null
    private val binding get() = _binding!!

    private lateinit var task: Task
    private var newTask: Boolean = true
    private var statusTask: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFormTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
        getArgs()
    }

    private fun getArgs() {
        args.taskFragment.let {
            if (it != null) {
                task = it

                configTask()
            }
        }
    }

    private fun configTask() {
        newTask = false
        statusTask = task.status
        binding.testToolbar.text = "Editando tarefa..."

        binding.editText.setText(task.description)
        setStatus()
    }

    private fun setStatus() {
        binding.radioGroup.check(
            when (task.status) {
                0 -> {
                    R.id.rbTodo
                }
                1 -> {
                    R.id.rbDoing
                }
                else -> {
                    R.id.rbDone
                }
            }
        )
    }

    private fun initListeners() {
        binding.btnSave.setOnClickListener { validateData() }

        binding.radioGroup.setOnCheckedChangeListener { _, id ->
            statusTask = when (id) {
                R.id.rbTodo -> 0
                R.id.rbDoing -> 1
                else -> 2
            }
        }
    }

    private fun validateData() {
        binding.btnSave.setOnClickListener {
            val description = binding.editText.text.toString().trim()

            if (description.isNotEmpty()) {

                hideKeyboard()
                binding.progressBar.isVisible = true

                if (newTask) task = Task()
                task.description = description
                task.status = statusTask

                saveTask()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Informe uma descrição para a tarefa.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun saveTask() {
        FirebaseHelper
            .getDatabase()
            .child("task")
            .child(FirebaseHelper.getIdUser() ?: "")
            .child(task.id)
            .setValue(task)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (newTask) {
                        // New Task
                        Toast.makeText(requireContext(), "Tarefa salva com sucesso", Toast.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                    } else {
                        // Editing Task
                        binding.progressBar.isVisible = false
                        Toast.makeText(requireContext(), "Tarefa Atualizada com sucesso", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Erro ao salva tarefa", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                binding.progressBar.isVisible = false
                Toast.makeText(requireContext(), "Erro ao salva tarefa", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}