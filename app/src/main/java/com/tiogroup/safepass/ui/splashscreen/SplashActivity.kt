package com.tiogroup.safepass.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.InputType
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.tiogroup.safepass.R
import com.tiogroup.safepass.preferences.UserPreferences
import com.tiogroup.safepass.ui.home.HomeActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()
        setFinishOnTouchOutside(false)

        val userPreference = UserPreferences(this)
        val locked = userPreference.getLockStatus()
        val password = userPreference.getPassword()


        Handler(Looper.getMainLooper()).postDelayed({
            if (locked){
                showAlertDialog(password)
            }
            else{
                openHome()
            }
        }, 2000L)
    }

    private fun showAlertDialog(password:String) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Enter password")
        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_TEXT
        alertDialogBuilder.setView(input)

        alertDialogBuilder.setPositiveButton("OK") { _, _ ->
            val enteredText = input.text.toString()
            if (enteredText == password){
                openHome()
            }
            else{
                Toast.makeText(this, "Wrong Password!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        alertDialogBuilder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        alertDialogBuilder.setCancelable(false)

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun openHome(){
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}