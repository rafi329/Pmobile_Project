package com.rafi.pmobile_project

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val db = DatabaseHelper(this)

        val email = findViewById<EditText>(R.id.etRegEmail)
        val password = findViewById<EditText>(R.id.etRegPassword)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        btnRegister.setOnClickListener {
            if (db.registerUser(email.text.toString(), password.text.toString())) {
                Toast.makeText(this, "Register berhasil", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Email sudah terdaftar", Toast.LENGTH_SHORT).show()
            }
        }
    }
}