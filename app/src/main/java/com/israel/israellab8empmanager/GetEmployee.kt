package com.israel.israellab8empmanager

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import java.util.ArrayList

// Define a data class to represent an employee
data class Employee(val idNumber: String, val username: String, val salary: String)

class GetEmployee : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_get_employee)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        } // end insets

        val progress = findViewById<ProgressBar>(R.id.progress)
        val empRecyclerView = findViewById<RecyclerView>(R.id.empRecyclerView)
        empRecyclerView.layoutManager = LinearLayoutManager(applicationContext)

        val employees = ArrayList<Employee>()



        //specify the endpoint
        //val api = "http://192.168.43.11:5000/employees"
        val api = "https://manuel09434.pythonanywhere.com/employees"
        //Access Helper
        val helper = ApiHelper(applicationContext)
        helper.get(api, object : ApiHelper.CallBack{
            override fun onSuccess(result: String?) {
                val employeeJSONArray = JSONArray(result.toString())
                (0 until employeeJSONArray.length()).forEach{
                    val employeeObject = employeeJSONArray.getJSONObject(it)
                    val employee = Employee(
                        employeeObject.getString("id_number"),
                        employeeObject.getString("username"),
                        employeeObject.getString("salary")
                    )
                    employees.add(employee)
                }// end loop
                progress.visibility = View.GONE // Dismiss progress bar
            }// end success
        })// end get

        val itemClickListener = object: EmployeeAdapter.OnItemClickListener {
            override fun onItemClick(employee: Employee) {
                val intent = Intent(applicationContext, EmployeeDetailActivity::class.java).apply {
                    putExtra("idNumber", employee.idNumber)
                    putExtra("username", employee.username)
                    putExtra("salary", employee.salary)
                }
                startActivity(intent)
            }
        }

        val employeeAdapter = EmployeeAdapter(employees, itemClickListener)
        empRecyclerView.adapter = employeeAdapter
    }// end oncreate
}// end GetEmployee