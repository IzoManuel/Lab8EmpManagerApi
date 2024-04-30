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
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.loopj.android.http.AsyncHttpClient.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import retrofit2.Retrofit
import java.util.ArrayList

class GetEmployee : AppCompatActivity() {
    private lateinit var api: String
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
        //api = "http://192.168.43.11:3000/employees"
        val api = "https://manuel09434.pythonanywhere.com/employees"
        //Access Helper
        log.d("GETEMPLOYEE", "inside getemployee activity")
        val helper = ApiHelper(applicationContext)
        helper.get(api, object : ApiHelper.CallBack{
            override fun onSuccess(result: String?) {
                val employeeJSONArray = JSONArray(result.toString())
                (0 until employeeJSONArray.length()).forEach{
                    val employeeObject = employeeJSONArray.getJSONObject(it)
                    val employee = Employee(
                        employeeObject.getString("id_number"),
                        employeeObject.getString("username"),
                        employeeObject.getString("salary"),
                        employeeObject.getString("image_url"),
                        employeeObject.getString("department")
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


}