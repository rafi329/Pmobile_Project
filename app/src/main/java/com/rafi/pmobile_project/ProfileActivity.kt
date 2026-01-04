package com.rafi.pmobile_project

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // ============ INISIALISASI VIEW ============
        // Cari semua view dengan ID yang sesuai di XML
        val btnBack: LinearLayout = findViewById(R.id.btnBack)

        val tvUserName: TextView = findViewById(R.id.tvUserName)
        val tvUserEmail: TextView = findViewById(R.id.tvUserEmail)
        val btnEditName: Button = findViewById(R.id.btnEditName)
        val btnLogout: Button = findViewById(R.id.btnLogout)

        // ============ INISIALISASI MENU ============
        // Untuk LinearLayout, gunakan findViewById dengan LinearLayout
        val menuOrders: android.widget.LinearLayout = findViewById(R.id.menuOrders)
        val menuFavorites: android.widget.LinearLayout = findViewById(R.id.menuFavorites)
        val menuSettings: android.widget.LinearLayout = findViewById(R.id.menuSettings)
        val menuAccountInfo: android.widget.LinearLayout = findViewById(R.id.menuAccountInfo)
        val menuShare: android.widget.LinearLayout = findViewById(R.id.menuShare)

        // ============ LOAD DATA USER ============
        val pref = getSharedPreferences("USER_PREF", MODE_PRIVATE)

        // Set data ke TextView
        tvUserName.text = pref.getString("USERNAME", "Guest User")
        tvUserEmail.text = pref.getString("EMAIL", "guest@email.com")

        // ============ SET CLICK LISTENER ============

        // 1. Tombol Back
        btnBack.setOnClickListener {
            finish() // Kembali ke activity sebelumnya
        }

        // 2. Tombol Edit Name
        btnEditName.setOnClickListener {
            showEditNameDialog(tvUserName, pref)
        }

        // 3. Menu Orders
        menuOrders.setOnClickListener {
            Toast.makeText(this, "Orders Clicked", Toast.LENGTH_SHORT).show()
        }

        // 4. Menu Favorites
        menuFavorites.setOnClickListener {
            Toast.makeText(this, "Favorites Clicked", Toast.LENGTH_SHORT).show()
        }

        // 5. Menu Settings
        menuSettings.setOnClickListener {
            Toast.makeText(this, "Settings Clicked", Toast.LENGTH_SHORT).show()
        }

        // 6. Menu Account Info
        menuAccountInfo.setOnClickListener {
            showAccountInfoDialog(pref)
        }

        // 7. Menu Share
        menuShare.setOnClickListener {
            shareApp()
        }

        // 8. Tombol Logout
        btnLogout.setOnClickListener {
            showLogoutDialog(pref)
        }
    }

    // ============ FUNGSI-FUNGSI BANTU ============

    private fun showEditNameDialog(
        tvUserName: TextView,
        pref: android.content.SharedPreferences
    ) {
        val input = android.widget.EditText(this)
        input.setText(tvUserName.text.toString())

        AlertDialog.Builder(this)
            .setTitle("Edit Name")
            .setMessage("Enter your new name:")
            .setView(input)
            .setPositiveButton("Save") { _, _ ->
                val newName = input.text.toString().trim()
                if (newName.isNotEmpty()) {
                    // Simpan ke SharedPreferences
                    pref.edit().putString("USERNAME", newName).apply()
                    // Update tampilan
                    tvUserName.text = newName
                    Toast.makeText(this, "Name updated!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Name cannot be empty", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showAccountInfoDialog(pref: android.content.SharedPreferences) {
        val username = pref.getString("USERNAME", "Guest User")
        val email = pref.getString("EMAIL", "guest@email.com")

        val message = """
            Username: $username
            Email: $email
            Member Since: January 2024
        """.trimIndent()

        AlertDialog.Builder(this)
            .setTitle("Account Information")
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }

    private fun shareApp() {
        try {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(
                Intent.EXTRA_TEXT,
                "Check out this awesome app!"
            )
            startActivity(Intent.createChooser(shareIntent, "Share via"))
        } catch (e: Exception) {
            Toast.makeText(this, "No app available to share", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showLogoutDialog(pref: android.content.SharedPreferences) {
        AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Yes") { _, _ ->
                // Hapus data user
                pref.edit().clear().apply()

                // Pindah ke LoginActivity
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)

                Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

}