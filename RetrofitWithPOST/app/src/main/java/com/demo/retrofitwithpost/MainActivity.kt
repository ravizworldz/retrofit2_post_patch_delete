package com.demo.retrofitwithpost

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_create_new_user.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), RecyclerViewAdapter.OnItemClickListener {

    lateinit var recyclerViewAdapter: RecyclerViewAdapter
    lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()
        initViewModel()
        searchUser()

        createUserButton.setOnClickListener {
            startActivity(Intent(this@MainActivity, CreateNewUserActivity::class.java))
        }
    }

    private fun searchUser() {
        search_button.setOnClickListener {
            if(!TextUtils.isEmpty(searchEditText.text.toString())) {
                viewModel.searchUser(searchEditText.text.toString())
            } else {
                viewModel.getUsersList()
            }
        }
    }

    private fun initRecyclerView() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            val decoration = DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)
            recyclerViewAdapter = RecyclerViewAdapter(this@MainActivity)
            adapter = recyclerViewAdapter

        }
    }

    fun initViewModel() {
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.getUserListObserverable().observe(this, Observer<UserList> {
            if(it == null) {
                Toast.makeText(this@MainActivity, "no result found...", Toast.LENGTH_LONG).show()
            } else {
                recyclerViewAdapter.userList = it.data.toMutableList()
                recyclerViewAdapter.notifyDataSetChanged()
            }
        })
        viewModel.getUsersList()
    }

    override fun onItemEditCLick(user: User) {
        val intent = Intent(this@MainActivity, CreateNewUserActivity::class.java)
        intent.putExtra("user_id", user.id)
        startActivityForResult(intent, 1000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if(requestCode == 1000) {
            viewModel.getUsersList()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}