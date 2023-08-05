package com.tiogroup.safepass.ui.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.tiogroup.safepass.R
import com.tiogroup.safepass.databinding.ActivitySettingsBinding
import com.tiogroup.safepass.preferences.LockPref
import com.tiogroup.safepass.preferences.UserPreferences

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val userPreference = UserPreferences(this)
        val locked = userPreference.getLockStatus()
        binding.lockSwitch.isChecked = locked

        binding.lockSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                showAlertDialog()
            }
            else{
                userPreference.setLock(LockPref("", false))
            }
        }
    }

    private fun showAlertDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Enter password")
        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_TEXT
        alertDialogBuilder.setView(input)

        alertDialogBuilder.setPositiveButton("OK") { _, _ ->
            val enteredText = input.text.toString()
            if (enteredText == ""){
                Toast.makeText(this, "Please input password and try again.", Toast.LENGTH_SHORT).show()
                binding.lockSwitch.isChecked = false
            }
            else{
                saveLockPref(enteredText)
            }
        }

        alertDialogBuilder.setNegativeButton("Cancel") { dialog, _ ->
            binding.lockSwitch.isChecked = false
            dialog.dismiss()
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun saveLockPref(password: String) {
        val userPreference = UserPreferences(this)
        userPreference.setLock(LockPref(password, true))
        Toast.makeText(this, "Lock has been set", Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}