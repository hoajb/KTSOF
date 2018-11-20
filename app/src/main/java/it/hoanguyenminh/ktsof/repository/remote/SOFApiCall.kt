package it.hoanguyenminh.ktsof.repository.remote

import it.hoanguyenminh.ktsof.repository.data.Users
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SOFApiCall {
    @GET("users")
    fun getUsers(
        @Query("site") site: String,
        @Query("pagesize") pagesize: Int,
        @Query("page") page: Int
    ): Call<Users>
}