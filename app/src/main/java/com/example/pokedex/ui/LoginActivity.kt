package com.example.pokedex.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pokedex.database.UserDatabase
import com.example.pokedex.database.UserRepository
import com.example.pokedex.databinding.ActivityLoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userDao = UserDatabase.getInstance(applicationContext).userDao()
        userRepository = UserRepository(userDao)

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                CoroutineScope(Dispatchers.IO).launch {
                    val isLoggedIn = userRepository.login(email, password)
                    if (isLoggedIn) {
                        runOnUiThread {
                            Toast.makeText(applicationContext, "Login successful!", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@LoginActivity, PokemonList::class.java)
                            startActivity(intent)
                            finish()
                        }
                    } else {
                        runOnUiThread {
                            Toast.makeText(applicationContext, "Invalid email or password", Toast.LENGTH_SHORT).show()
                            startActivity(intent)
                        }
                    }
                }
            } else {
                Toast.makeText(applicationContext, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}

