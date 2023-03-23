package com.test.todoapp.ui.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.test.todoapp.R
import com.test.todoapp.databinding.FragmentToDoListBinding
import com.test.todoapp.room.ToDo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ToDoListFragment : Fragment() {

    @Inject
    lateinit var toDoAdapter: ToDoAdapter

    private val viewModel: ToDoViewModel by viewModels()
    private lateinit var binding: FragmentToDoListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentToDoListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()
        setupList()
    }

    private fun setupList() {

        binding.rvTodo.adapter = toDoAdapter

        lifecycleScope.launch {
            viewModel.getList().collectLatest {
                binding.tvNoData.isVisible = it.isEmpty()
                toDoAdapter.submitList(it)
            }
        }

        toDoAdapter.setUpdateCompleted {
            val item = toDoAdapter.currentList[it]
            val data = ToDo(
                id = item.id,
                task = item.task,
                isCompleted = !item.isCompleted,
                description = item.description
            )
            viewModel.update(data)
        }

        toDoAdapter.setOnItemClick {
            val item = toDoAdapter.currentList[it]
            navigateToNextScreen(item)
        }

        toDoAdapter.setOnLongPressed {
            val item = toDoAdapter.currentList[it]
            showDialogConfirmRemoveItem(item.id)
        }
    }

    private fun showDialogConfirmRemoveItem(id: Int) {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setTitle("Confirm!")
        dialog.setMessage("Are you sure to delete it?")
        dialog.setNegativeButton("CANCEL") { d, _ ->
            d.dismiss()

        }
        dialog.setPositiveButton("YES") { d, _ ->
            viewModel.delete(id)
            d.dismiss()
        }
        dialog.show()

    }

    private fun setupListener() {
        binding.fab.setOnClickListener {
            navigateToNextScreen()
        }
    }

    private fun navigateToNextScreen(data: ToDo? = null) {
        val bundle = bundleOf("item" to data)
        findNavController().navigate(R.id.action_toDoListFragment_to_createToDoFragment, bundle)
    }

}