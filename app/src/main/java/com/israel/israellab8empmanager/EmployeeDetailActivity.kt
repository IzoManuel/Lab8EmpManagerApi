package com.israel.israellab8empmanager

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class EmployeeDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_employee_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }// end insets

        //Retrieve employee details
        val idNumber = intent.getStringExtra("idNumber")
        val username = intent.getStringExtra("username")
        val salary = intent.getStringExtra("salary")

        //Display employee details in TextViewws
        findViewById<TextView>(R.id.id_number).text = "ID number: $idNumber"
        findViewById<TextView>(R.id.username).text = "Username: $username"
        findViewById<TextView>(R.id.salary).text = "Salary: $salary"



    } // end onCreate
} // end EmployeeDetailActivity