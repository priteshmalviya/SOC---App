package com.pritesh.soc.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pritesh.soc.R
import com.pritesh.soc.modles.StudentForm

class StudentAdapter (private val dataSet: ArrayList<StudentForm>, private val listener  : StudentDetailes) :
    RecyclerView.Adapter<StudentAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name : TextView
        val post : TextView
        val image : ImageView
        init {
            name = view.findViewById(R.id.NameOfTheFaculty)
            post = view.findViewById(R.id.PostOfTheFaculty)
            image = view.findViewById(R.id.ImageOfTheFaculty)
        }
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.faculty_list_segment, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.name.text=dataSet[position].name
        viewHolder.post.text = dataSet[position].course
        /*if(dataSet[position].imageUrl!="") {
             Glide.with(viewHolder.itemView.context).load(dataSet[position].imageUrl).circleCrop()
                 .into(viewHolder.image)
         }*/
        viewHolder.itemView.setOnClickListener {
            listener.openActivity(dataSet[position].key)
        }
    }

    override fun getItemCount() = dataSet.size
}

interface StudentDetailes{
    fun openActivity(id: String)
}