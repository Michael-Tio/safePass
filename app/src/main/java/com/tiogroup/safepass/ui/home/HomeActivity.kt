package com.tiogroup.safepass.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tiogroup.safepass.R
import com.tiogroup.safepass.data.Password
import com.tiogroup.safepass.databinding.ActivityHomeBinding
import com.tiogroup.safepass.ui.ViewModelFactory
import com.tiogroup.safepass.ui.create.CreateActivity
import com.tiogroup.safepass.ui.settings.SettingsActivity

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var passwordAdapter: HomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fabCreate.setOnClickListener{
            val intent = Intent(this@HomeActivity, CreateActivity::class.java)
            startActivity(intent)
        }

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]
        binding.rvPass.layoutManager = LinearLayoutManager(this)

        viewModel.passwords.observe(this, Observer(this::showRecyclerView))
    }

    private fun showRecyclerView(password: List<Password>){
        passwordAdapter = HomeAdapter(password)
        binding.rvPass.adapter = passwordAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_settings -> {
                val intent = Intent(this@HomeActivity, SettingsActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}