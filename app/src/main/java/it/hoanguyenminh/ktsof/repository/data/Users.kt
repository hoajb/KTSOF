package it.hoanguyenminh.ktsof.repository.data

import com.google.gson.annotations.SerializedName

/**
 * Created by Hoa Nguyen on 2018 November 20.
 * hoa.nguyenminh.it@gmail.com
 */

data class Users(
    @SerializedName("items")
    var items: List<User>?

)