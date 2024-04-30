package com.israel.israellab8empmanager



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

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

        employee.employeeImageUrl?.let { imageUrl ->
            Glide.with(holder.itemView.context)
                .load(imageUrl)
                .placeholder(R.drawable.employee_place_holder) // Optional placeholder
                //.error(R.drawable.error_image) // Optional error placeholder
                .into(holder.employeeImage)
        }

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
        var employeeImage: ImageView

        init{
            idNumber = itemView.findViewById(R.id.id_number)
            username = itemView.findViewById(R.id.username)
            salary = itemView.findViewById(R.id.salary)
            employeeImage = itemView.findViewById(R.id.employee_image)
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