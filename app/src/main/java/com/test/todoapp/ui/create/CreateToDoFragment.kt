package com.test.todoapp.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.test.todoapp.data.ToDo
import com.test.todoapp.databinding.FragmentCreateToDoBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class CreateToDoFragment : Fragment() {

    private val viewModel: CreateViewModel by viewModels()
    private val args: CreateToDoFragmentArgs by navArgs()

    private lateinit var binding: FragmentCreateToDoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateToDoBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()
        ifEdit()
        observe()
    }

    private fun ifEdit() {
        args.item?.let { item ->
            binding.edtTask.setText(item.task)
            binding.checkbox.isChecked = item.isCompleted
        }
    }

    private fun setupListener() {
        binding.btnSubmit.setOnClickListener {
            submit()
        }
    }

    private fun submit() {
        val task = binding.edtTask.text.toString()
        val check = binding.checkbox.isChecked
        if (task.isEmpty()) {
            Toast.makeText(requireContext(), "Please input task", Toast.LENGTH_SHORT).show()
            return
        }

        if (args.item != null) {
            viewModel.update(
                ToDo(
                    id = args.item?.id ?: 0,
                    task = task.trim(),
                    isCompleted = check
                )
            )
            return
        }

        val toDo = ToDo(
            task = task.trim(), isCompleted = check
        )
        viewModel.createToDo(toDo)
    }

    private fun observe() {
        lifecycleScope.launchWhenCreated {
            viewModel.create.collectLatest {
                if (it) findNavController().popBackStack()
                else {
                    Toast.makeText(requireContext(), "Task already exists", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.update.collectLatest {
                if (it) {
                    viewModel.cacheToDo = null
                    findNavController().popBackStack()
                }
                else {
                    showComplainDialog()
                }
            }
        }
    }

    private fun showComplainDialog() {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setTitle("Duplicate!")
        dialog.setMessage("Task already exist!")
        dialog.setNegativeButton("CLOSE") { d, _ ->
            d.dismiss()

        }
        dialog.setPositiveButton("ADD THIS TASK") { d, _ ->
            if (viewModel.cacheToDo != null) {
                val k = viewModel.cacheToDo!!
                viewModel.createToDo(ToDo(task = k.task), true)
            }
            d.dismiss()
        }
        dialog.show()

    }

}