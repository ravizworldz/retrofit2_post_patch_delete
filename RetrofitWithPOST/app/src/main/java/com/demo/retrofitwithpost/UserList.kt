package com.demo.retrofitwithpost

data class UserList(val data: List<User>)
data class User(val id: String?,val name: String?,  val email: String?, val status: String?, val gender: String?)
data class UserResponse(val code: Int?, val meta: String?, val data: User?)