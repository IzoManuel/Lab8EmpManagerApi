package com.israel.israellab8empmanager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EmployeeAdapter(
    private val employeeList: List<Employee>,
    private val listener: OnItemClickListener? = null
    ) : RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_employee, parent, false)
        return EmployeeViewHolder(view)
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        val employee = employeeList[position]

        holder.idNumber.text = "ID number: ${employee.idNumber}"
        holder.username.text = "Username: ${employee.username}"
        holder.salary.text = "Salary: ${employee.salary}"

        listener?.let {listener ->
            holder.itemView.setOnClickListener{
                listener.onItemClick(employee)
            }
        }
    }

    class EmployeeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var idNumber: TextView
        var username: TextView
        var salary: TextView

        init{
            idNumber = itemView.findViewById(R.id.id_number)
            username = itemView.findViewById(R.id.username)
            salary = itemView.findViewById(R.id.salary)
        }
    }
    // return the number of the items in the list
    override fun getItemCount(): Int {
        return employeeList.size
    }

    interface  OnItemClickListener {
        fun onItemClick(employee: Employee)
    }
}
