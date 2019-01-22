package it.hoanguyenminh.ktsof.repository.remote

import io.reactivex.Observable
import it.hoanguyenminh.ktsof.repository.data.Users
import retrofit2.http.GET
import retrofit2.http.Query

interface SOFApi {
    @GET("users")
    fun getUsers(
        @Query("site") site: String,
        @Query("pagesize") pagesize: Int,
        @Query("page") page: Int
    ): Observable<Users>
}