package com.demo.retrofitwithpost

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityViewModel: ViewModel() {


    lateinit var recyclerListData: MutableLiveData<UserList>

    init {
        recyclerListData = MutableLiveData()

    }


    fun getUserListObserverable() : MutableLiveData<UserList> {
        return recyclerListData
    }

    fun getUsersList() {

        val retroInstance = RetroInstance.getRetroInstance().create(RetroService::class.java)
        val call = retroInstance.getUsersList()
        call.enqueue(object : Callback<UserList>{
            override fun onFailure(call: Call<UserList>, t: Throwable) {
                recyclerListData.postValue(null)
            }

            override fun onResponse(call: Call<UserList>, response: Response<UserList>) {
                if(response.isSuccessful) {
                    recyclerListData.postValue(response.body())
                } else {
                    recyclerListData.postValue(null)
                }
            }
        })
    }

    fun searchUser(searchText: String) {

        val retroInstance = RetroInstance.getRetroInstance().create(RetroService::class.java)
        val call = retroInstance.searchUsers(searchText)
        call.enqueue(object : Callback<UserList>{
            override fun onFailure(call: Call<UserList>, t: Throwable) {
                recyclerListData.postValue(null)
            }

            override fun onResponse(call: Call<UserList>, response: Response<UserList>) {
                if(response.isSuccessful) {
                    recyclerListData.postValue(response.body())
                } else {
                    recyclerListData.postValue(null)
                }
            }
        })
    }
}