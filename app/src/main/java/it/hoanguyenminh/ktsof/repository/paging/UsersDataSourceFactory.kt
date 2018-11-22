package it.hoanguyenminh.ktsof.repository.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import it.hoanguyenminh.ktsof.repository.data.User
import it.hoanguyenminh.ktsof.repository.remote.SOFApiCall


/**
 * Created by Hoa Nguyen on 2018 November 22.
 * hoa.nguyenminh.it@gmail.com
 */

class UsersDataSourceFactory(private val sofApiCall: SOFApiCall) : DataSource.Factory<Int, User>() {
    val sourceLiveData = MutableLiveData<UsersDataSource>()
    override fun create(): DataSource<Int, User> {
        val source = UsersDataSource(sofApiCall)

        sourceLiveData.postValue(source)
        return source
    }
}