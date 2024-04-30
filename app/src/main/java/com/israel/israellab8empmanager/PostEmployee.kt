package com.israel.israellab8empmanager

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.loopj.android.http.AsyncHttpClient.log
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.io.File
import android.Manifest
import android.content.pm.PackageManager
import com.google.gson.Gson
import okhttp3.RequestBody.Companion.toRequestBody


class PostEmployee : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private var selectedImageUri: Uri? = null
    private val READ_EXTERNAL_STORAGE_REQUEST = 123
    private val REQUEST_CODE_STORAGE_PERMISSION = 100

//    private val employeeService: EmployeeService by lazy {
//        //val baseApi = "https://manuel09434.pythonanywhere.com/"
//        val baseApi = "http://192.168.43.11:3000/"
//        val retrofit = Retrofit.Builder()
//            .baseUrl(baseApi)
//            .addConverterFactory(JacksonConverterFactory.create())
//            .build()
//
//        retrofit.create(EmployeeService::class.java)
//    }

    val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        // Callback is invoked after the user selects a media item or closes the
        // photo picker.
        if (uri != null) {
            Log.d("PhotoPicker", "Selected URI: $uri")
            selectedImageUri = uri
            Glide.with(this)
                .load(uri)
                .placeholder(R.drawable.employee_place_holder)
                .into(imageView)
        } else {
            Log.d("PhotoPicker", "No media selected")
            Toast.makeText(applicationContext, "No image selected", Toast.LENGTH_SHORT).show()
        }
    }

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

        val imageButton = findViewById<Button>(R.id.image_button)
        imageView = findViewById(R.id.image_view)

        imageButton.setOnClickListener {
            requestStoragePermission()
            pickMedia.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        val retrofit = RetrofitClient.getClient(applicationContext)

        // Initialize EmployeeService
        val employeeService = retrofit.create(EmployeeService::class.java)

        val btnsave = findViewById<Button>(R.id.btnsave)
        btnsave.setOnClickListener {

            if (selectedImageUri != null){
                //convert Uri to file
                val imageFile = File(
                    getRealPathFromURI(selectedImageUri!!)
                )
                // Create requestBody for image file
                val imagePart: MultipartBody.Part =
                    MultipartBody.Part.createFormData(
                        "employee_image",
                        imageFile.name,
                        imageFile.asRequestBody("image/*".toMediaTypeOrNull())
                    )

                val idNumberReqBody = id_number.text.toString()
                    .toRequestBody("text/plain".toMediaTypeOrNull())
                val usernameReqBody = username.text.toString()
                    .toRequestBody("text/plain".toMediaTypeOrNull())
                val othersReqBody = others.text.toString()
                    .toRequestBody("text/plain".toMediaTypeOrNull())
                val salaryReqBody = salary.text.toString()
                    .toRequestBody("text/plain".toMediaTypeOrNull())
                val departmentReqBody = department.text.toString()
                    .toRequestBody("text/plain".toMediaTypeOrNull())

                lifecycleScope.launch {
                    try {
                        employeeService.createEmployee(
                            idNumberReqBody,
                            usernameReqBody,
                            othersReqBody,
                            salaryReqBody,
                            departmentReqBody,
                            imagePart
                        )

                        Toast.makeText(this@PostEmployee,
                            "Employee created successfully",
                            Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        log.d("EMPLOYEE UPLOAD ERROR:", e.toString())
                        Toast.makeText(this@PostEmployee,
                            e.message,
                            Toast.LENGTH_SHORT).show()
                    }
                }

            }else{
                Toast.makeText(application, "No image selected", Toast.LENGTH_SHORT).show()
            }

        }
    } // end onCreate

    private fun getRealPathFromURI(uri: Uri): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(uri, projection, null, null, null)
        cursor?.use {
            val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            it.moveToFirst()
            return it.getString(columnIndex)
        }
        return null
    }

    private fun requestStoragePermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            READ_EXTERNAL_STORAGE_REQUEST
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_STORAGE_PERMISSION -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted, proceed with the task that required the permission
                    // For example, you can call a function to handle image loading here
                    pickMedia.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                } else {
                    // Permission denied, inform the user and handle accordingly
                    Toast.makeText(this, "Storage permission denied", Toast.LENGTH_SHORT).show()
                }
                return
            }
            // Handle other permission requests if needed
        }
    }


} // end PostEmployee