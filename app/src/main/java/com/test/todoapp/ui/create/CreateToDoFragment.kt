package com.test.todoapp.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.test.todoapp.databinding.FragmentCreateToDoBinding
import com.test.todoapp.room.ToDo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlin.concurrent.timerTask

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

        lifecycleScope.launchWhenCreated {
            viewModel.create.collectLatest {
                if (it) findNavController().popBackStack()
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.update.collectLatest {
                if (it) findNavController().popBackStack()
            }
        }

    }

    private fun ifEdit() {
        args.item?.let { item ->
            binding.edtTask.setText(item.task)
            binding.checkbox.isChecked = item.isCompleted
        }
    }

    private fun setupListener() {
        binding.btnSubmit.setOnClickListener {
            val task = binding.edtTask.text.toString()
            val check = binding.checkbox.isChecked
            if (task.isEmpty()) {
                Toast.makeText(requireContext(), "Please input task", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (args.item != null) {
                viewModel.update(
                    ToDo(
                        id = args.item?.id ?: 0,
                        task = task,
                        isCompleted = check
                    )
                )
                return@setOnClickListener
            }

            val toDo = ToDo(
                task = task, isCompleted = check
            )
            viewModel.createToDo(toDo)
        }
    }

}