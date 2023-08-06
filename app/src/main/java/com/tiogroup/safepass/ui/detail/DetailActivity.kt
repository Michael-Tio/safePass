package com.tiogroup.safepass.ui.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.tiogroup.safepass.R
import com.tiogroup.safepass.data.Password
import com.tiogroup.safepass.databinding.ActivityDetailBinding
import com.tiogroup.safepass.preferences.UserPreferences
import com.tiogroup.safepass.ui.ViewModelFactory
import com.tiogroup.safepass.ui.create.CreateActivity
import com.tiogroup.safepass.ui.create.CreateViewModel

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel
    private lateinit var passwordData: Password
    var shown = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Password Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]

        val id = intent.getIntExtra("PASSWORD_ID", -1)

        var password = ""

        viewModel.getDetailPassword(id).observe(this) { passwordData ->
            passwordData?.let {
                this.passwordData = it
                binding.apply {
                    txtDetailEmail.text = it.emailOrUsernameOrPhone
                    txtDetailTitle.text = it.title
                    password = it.password
                    txtDetailPassword.text = hidePassword(password)
                }
            }
        }

        val userPreference = UserPreferences(this)
        val locked = userPreference.getLockStatus()
        val appPassword = userPreference.getPassword()

        binding.btnExposePassword.setOnClickListener{
            if (shown) {
                binding.txtDetailPassword.text = hidePassword(password)
            } else if (locked) {
                showAlertDialog(appPassword, password)
            } else {
                showPassword(password)
            }
        }
    }

    private fun hidePassword(input: String): String {
        shown = false
        return buildString {
            for (i in input.indices) {
                append('*')
            }
        }
    }

    private fun showPassword(password: String){
        binding.txtDetailPassword.text = password
        shown = true
    }

    private fun showAlertDialog(appPassword:String, password:String) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Enter password")
        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_TEXT
        alertDialogBuilder.setView(input)

        alertDialogBuilder.setPositiveButton("OK") { _, _ ->
            val enteredText = input.text.toString()
            if (enteredText == appPassword){
                showPassword(password)
            }
            else{
                Toast.makeText(this, "Wrong Password!", Toast.LENGTH_SHORT).show()
            }
        }

        alertDialogBuilder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        alertDialogBuilder.setCancelable(false)

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_edit -> {
                val id = intent.getIntExtra("PASSWORD_ID", -1)
                val intent = Intent(this@DetailActivity, CreateActivity::class.java)
                intent.apply {
                    putExtra("FROM_ACTIVITY", "DETAIL")
                    putExtra("DETAIL_ID", id)
                }
                startActivity(intent)
                return true
            }
            R.id.item_delete -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Safe Pass")
                builder.setMessage("Are you sure you want to delete this password?")

                builder.setPositiveButton("Yes") { dialog, _ ->
                    viewModel.deletePassword(passwordData)
                    Toast.makeText(this, "Password deleted successfully", Toast.LENGTH_SHORT).show()
                    finish()
                }

                builder.setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
                builder.show()

                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}