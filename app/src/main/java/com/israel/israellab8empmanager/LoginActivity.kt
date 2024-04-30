package com.israel.israellab8empmanager

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.loopj.android.http.AsyncHttpClient
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class LoginActivity : AppCompatActivity() {

    private val employeeService: EmployeeService by lazy {
        val baseApi = "https://manuel09434.pythonanywhere.com/"
        //val baseApi = "http://192.168.43.11:3000/"
        val retrofit = Retrofit.Builder()
            .baseUrl(baseApi)
            .addConverterFactory(JacksonConverterFactory.create())
            .build()

        retrofit.create(EmployeeService::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }//end insets

        val visitHomeButton = findViewById<Button>(R.id.visit_home_btn)
        visitHomeButton.setOnClickListener {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        val visitRegisterButton = findViewById<Button>(R.id.visit_register_btn)
        visitRegisterButton.setOnClickListener {
            val intent = Intent(applicationContext, RegisterActivity::class.java)
            startActivity(intent)
        }
        val loginButton = findViewById<Button>(R.id.login_btn)
        loginButton.setOnClickListener {
            val params = mapOf(
                "username" to findViewById<EditText>(R.id.username)
                    .text.toString(),
                "password" to findViewById<EditText>(R.id.password)
                    .text.toString()
            )

            loginUser(params)
        }
    }//End onCreate
    private fun loginUser(params: Map<String, String>) {
        lifecycleScope.launch {
            try {
                val response = employeeService.login(params)
                val responseBody = response.string()

                val jsonResponse = JSONObject(responseBody)
                val accessToken = jsonResponse.getString("access_token")
                val username = jsonResponse.getString("username")

                AuthUtils.setUserDetails(username, accessToken, applicationContext)

                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()

                Toast.makeText(
                    this@LoginActivity,
                    "Login successfully",
                    Toast.LENGTH_SHORT
                ).show()
            } catch (e: Exception) {
                AsyncHttpClient.log.d("Employee login failure:", e.toString())
                Toast.makeText(
                    this@LoginActivity,
                    e.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}// End LoginActivity