package com.test.todoapp.ui

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.test.todoapp.R
import com.test.todoapp.ui.list.ToDoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: ToDoViewModel by viewModels()
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var searchMenu: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbar()
        handleSearch()
    }

    private fun initToolbar() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf(R.id.toDoListFragment),
            fallbackOnNavigateUpListener = ::onSupportNavigateUp
        )
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setupWithNavController(navController, appBarConfiguration)
        searchMenu = toolbar.menu.findItem(R.id.action_search)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            searchMenu.isVisible = destination.id == R.id.toDoListFragment
        }
    }

    private fun handleSearch() {
        val searchView = searchMenu.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                lifecycleScope.launchWhenCreated {
                    viewModel.search(newText)
                }
                return true
            }
        })
    }

}