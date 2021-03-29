package com.demo.retrofitwithpost

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recucler_row_list.view.*

class RecyclerViewAdapter(val clickListener: OnItemClickListener): RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    var userList = mutableListOf<User>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewAdapter.MyViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.recucler_row_list, parent, false)
        return MyViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: RecyclerViewAdapter.MyViewHolder, position: Int) {
       holder.bind(userList[position])
        holder.itemView.setOnClickListener {
            clickListener.onItemEditCLick(userList[position])
        }
    }

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val textViewName = view.textViewName
        val textViewEmail = view.textViewEmail
        val textViewStats = view.textViewStats

        fun bind(data : User) {
            textViewName.text = data.name
            textViewEmail.text = data.email
            textViewStats.text = data.status
        }
    }

    interface OnItemClickListener {
        fun onItemEditCLick(user : User)
    }
}