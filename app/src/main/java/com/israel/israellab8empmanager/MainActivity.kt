package com.israel.israellab8empmanager

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
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
            val x = Intent(applicationContext, PostEmployee::class.java)
            startActivity(x)
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
            val x = Intent(applicationContext, DeleteEmployee::class.java)
            startActivity(x)
        }

    } // end onCreate

}