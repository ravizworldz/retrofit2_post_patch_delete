package com.demo.retrofitwithpost

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_create_new_user.*

class CreateNewUserActivity : AppCompatActivity() {

    lateinit var viewModel: CreateNewUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_new_user)

        val user_id = intent.getStringExtra("user_id")
        initViewModel()
        createUserObservable()

        if(user_id != null) {
            loadUserData(user_id)
        }
        createButton.setOnClickListener {
            createUser(user_id)
        }
        deleteButton.setOnClickListener {
            deleteUser(user_id)
        }
    }

    private fun deleteUser(user_id: String?) {
        viewModel.getDeleteUserObservable().observe(this, Observer <UserResponse?>{
            if(it == null) {
                Toast.makeText(this@CreateNewUserActivity, "Failed to delete user...", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this@CreateNewUserActivity, "Successfully deleted user...", Toast.LENGTH_LONG).show()
                finish()
            }
        })
        viewModel.deleteUser(user_id)
    }
    private fun loadUserData(user_id: String?) {
        viewModel.getLoadUserObservable().observe(this, Observer <UserResponse?>{
            if(it != null) {
                editTextName.setText(it.data?.name)
                editTextEmail.setText(it.data?.email)
                createButton.setText("Update")
                deleteButton.visibility =  View.VISIBLE
            }
        })
        viewModel.getUserData(user_id)
    }
    private fun createUser(user_id: String?){
        val user = User("", editTextName.text.toString(), editTextEmail.text.toString(), "Active", "Male")

        if(user_id == null)
            viewModel.createUser(user)
        else
            viewModel.updateUser(user_id, user)

    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(CreateNewUserViewModel::class.java)

    }

    private fun createUserObservable() {
        viewModel.getCreateNewUserObservable().observe(this, Observer <UserResponse?>{
            if(it == null) {
                Toast.makeText(this@CreateNewUserActivity, "Failed to create/update new user...", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this@CreateNewUserActivity, "Successfully created/updated user...", Toast.LENGTH_LONG).show()
                finish()
            }
        })
    }
}