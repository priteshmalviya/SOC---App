package com.pritesh.soc.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pritesh.soc.R
import com.pritesh.soc.modles.Company
import com.pritesh.soc.modles.Placement

class CompanyAdapter (private val dataSet: ArrayList<Company>, private val listener  : OpenCompany) :
    RecyclerView.Adapter<CompanyAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView
        val post: TextView
        val image : ImageView
        val title : TextView
        val placedStudent : TextView
        init {
            name = view.findViewById(R.id.NameOfTheFaculty)
            post = view.findViewById(R.id.PostOfTheFaculty)
            image = view.findViewById(R.id.ImageOfTheFaculty)
            placedStudent=view.findViewById(R.id.packegeForPlacment)
            title=view.findViewById(R.id.packegeForPlacment)
        }
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.faculty_list_segment, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.name.text=dataSet[position].name
        viewHolder.post.text=dataSet[position].packege+" Lpa"
        Glide.with(viewHolder.itemView.context).load(dataSet[position].image).into(viewHolder.image)
        viewHolder.placedStudent.text=dataSet[position].placedStudnts.toString()+"\nStudents Placed"
        //viewHolder.title.text="Students Placed"

        viewHolder.itemView.setOnClickListener {
            listener.gotoUrl(dataSet[position].url)
        }
    }
    override fun getItemCount() = dataSet.size
}

interface OpenCompany{
    fun gotoUrl(url: String)
}
