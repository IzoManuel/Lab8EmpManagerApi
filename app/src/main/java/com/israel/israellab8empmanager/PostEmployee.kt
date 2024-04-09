package com.israel.israellab8empmanager

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.json.JSONObject

class PostEmployee : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_post_employee)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        } // end inset

        // Find 5 EditTexts and 1 Button
        val id_number = findViewById<EditText>(R.id.id_number)
        val username = findViewById<EditText>(R.id.username)
        val others = findViewById<EditText>(R.id.others)
        val salary = findViewById<EditText>(R.id.salary)
        val department = findViewById<EditText>(R.id.department)

        val btnsave = findViewById<Button>(R.id.btnsave)
        btnsave.setOnClickListener {
            //POST logic goes here
            //specify endpoint
            //val api = "https://modcom.pythonanywhere.com/employees"
            //val api = "http://192.168.43.11:5000/employees"
            val api = "https://manuel09434.pythonanywhere.com/employees"
            // specify the data/body to POST as a JSON object
            var body = JSONObject()
            body.put("id_number", id_number.text.toString()) // value from edittext
            body.put("username", username.text.toString()) // value from edittext
            body.put("others", others.text.toString()) // value from edittext
            body.put("salary", salary.text.toString()) // value from edittext
            body.put("department", department.text.toString()) // value from edittext

            // Access helper
            val helper = ApiHelper(applicationContext)
            // Post the body to API
            helper.post(api, body)
        }




    } // end onCreate
} // end PostEmployee