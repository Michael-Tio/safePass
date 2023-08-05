package com.tiogroup.safepass.ui.create

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.tiogroup.safepass.R
import com.tiogroup.safepass.data.Password
import com.tiogroup.safepass.databinding.ActivityCreateBinding
import com.tiogroup.safepass.ui.ViewModelFactory

class CreateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateBinding
    private lateinit var viewModel: CreateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Add new Password"

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[CreateViewModel::class.java]

        val sender = intent.getStringExtra("FROM_ACTIVITY")
        var id = 0
        if (sender != null){
            if (sender == "DETAIL"){
                id = intent.getIntExtra("DETAIL_ID", 0)
                viewModel.getDetailPassword(id).observe(this) { passwordData ->
                    passwordData?.let {
                        binding.apply {
                            txtCreateTitle.setText(it.title)
                            txtCreateEmail.setText(it.emailOrUsernameOrPhone)
                            txtCreatePass.setText(it.password)
                        }
                    }
                }

                supportActionBar?.title = "Edit Password"
                binding.btnSave.text = getString(R.string.save_changes)
            }
        }

        binding.txtCreateTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // This method is called before the text changes
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // This method is called when the text changes
                val newText = s.toString().length
                binding.txtHintTitle.text = "Title (${newText}/30)"
            }

            override fun afterTextChanged(s: Editable?) {
                // This method is called after the text changes
            }
        })

        binding.btnSave.setOnClickListener{
            binding.apply{
                if (txtCreateTitle.text.isNotEmpty()){
                    if (txtCreatePass.text.isNotEmpty()) {
                        val title = txtCreateTitle.text.toString()
                        val emailUserPhone = if(txtCreateEmail.text.isNotEmpty()) txtCreateEmail.text.toString() else "-"
                        val password = txtCreatePass.text.toString()

                        viewModel.addPassword(Password(id, title, emailUserPhone, password))
                        finish()
                    }
                    else{
                        Toast.makeText(this@CreateActivity, getString(R.string.please_fill_the_password), Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    Toast.makeText(this@CreateActivity, getString(R.string.please_fill_the_title), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}