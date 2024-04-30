package com.israel.israellab8empmanager

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface EmployeeService {
    @GET("employees")
    suspend fun getEmployees(): List<Employee>

    @Multipart
    @POST("employees")
    suspend fun createEmployee(
        @Part("id_number") idNumberReqBody: RequestBody,
        @Part("username") usernameReqBody: RequestBody,
        @Part("others") othersReqBody: RequestBody,
        @Part("salary") salaryReqBody: RequestBody,
        @Part("department") departmentReqBody: RequestBody,
        @Part image: MultipartBody.Part
    )

    @POST("login")
    suspend fun login(
        @Body params: Map<String, String>
    ): ResponseBody

    @POST("register")
    suspend fun register(
        @Body params: Map<String, String>
    ): ResponseBody
}