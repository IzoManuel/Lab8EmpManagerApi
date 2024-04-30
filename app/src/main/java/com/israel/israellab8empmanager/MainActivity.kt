package com.israel.israellab8empmanager

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpClient.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class MainActivity : AppCompatActivity() {
    private val employeeService: EmployeeService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.43.11:3000/")
            .addConverterFactory(JacksonConverterFactory.create())
            .build()

        retrofit.create(EmployeeService::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        } // end insets

        val postButton = findViewById<Button>(R.id.btnpost)
        postButton.setOnClickListener {
            if (AuthUtils.isAuthenticated(applicationContext)) {
                val x = Intent(applicationContext, PostEmployee::class.java)
                startActivity(x)
            }else{
                Toast.makeText(applicationContext,
                    "You must be logged in to post",
                    Toast.LENGTH_SHORT).show()
            }

        }

        val getButton = findViewById<Button>(R.id.btnget)
        getButton.setOnClickListener {
            val x = Intent(applicationContext, GetEmployee::class.java)
            startActivity(x)
        }

        val putButton = findViewById<Button>(R.id.btnput)
        putButton.setOnClickListener {
            val x = Intent(applicationContext, UpdateEmployee::class.java)
            startActivity(x)
        }

        val deleteButton = findViewById<Button>(R.id.btndelete)
        deleteButton.setOnClickListener {
//            val x = Intent(applicationContext, DeleteEmployee::class.java)
//            startActivity(x)
            }

        val visitLoginBtn = findViewById<Button>(R.id.visit_login_btn)
        visitLoginBtn.setOnClickListener {
            intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        val visitRegisterBtn = findViewById<Button>(R.id.visit_register_btn)
        visitRegisterBtn.setOnClickListener {
            intent = Intent(applicationContext, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        val logoutButton = findViewById<Button>(R.id.logout_btn)
        logoutButton.setOnClickListener {
            AuthUtils.logout(applicationContext)
            updateUI()
        }

        updateUI()


    } // end onCreate

    private fun updateUI() {
        val usernameGreeting = findViewById<TextView>(R.id.username_greeting)
        val loginButton = findViewById<Button>(R.id.visit_login_btn)
        val registerButton = findViewById<Button>(R.id.visit_register_btn)
        val logoutButton = findViewById<Button>(R.id.logout_btn)

        if (AuthUtils.isAuthenticated(applicationContext)) {
            // User is authenticated
            val sharedPreferences = getSharedPreferences("auth_preferences", Context.MODE_PRIVATE)
            val username = sharedPreferences.getString("username", "")
            usernameGreeting.text = "Hello, $username"
            logoutButton.visibility = View.VISIBLE
            usernameGreeting.visibility = View.VISIBLE
            loginButton.visibility = View.GONE
            registerButton.visibility = View.GONE
        } else {
            // User is not authenticated
            usernameGreeting.visibility = View.GONE
            logoutButton.visibility = View.GONE
            loginButton.visibility = View.VISIBLE
            registerButton.visibility = View.VISIBLE
        }
    }

    fun getEmployees() {
        lifecycleScope.launch {
            try {
                val employees = employeeService.getEmployees()
                log.d("EMPLOYEE LIST", "$employees")
            } catch (e: Exception) {
                log.d("ERROR EMPLOYEE LIST", e.message)
                Toast.makeText(
                    this@MainActivity,
                    e.message,
                    Toast.LENGTH_LONG).show()
            }
        }
    }
}// End mainActivity