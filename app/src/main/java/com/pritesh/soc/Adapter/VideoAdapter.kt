package com.pritesh.soc.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pritesh.soc.R
import com.pritesh.soc.modles.Video

class VideoAdapter(private val dataSet: ArrayList<Video>, private val listener  : OpenLink) :
    RecyclerView.Adapter<VideoAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView
        val post: TextView
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
        viewHolder.name.text=dataSet[position].title
        viewHolder.post.text=dataSet[position].duration
        Glide.with(viewHolder.itemView.context).load(dataSet[position].image).into(viewHolder.image)

        viewHolder.itemView.setOnClickListener {
            listener.gotoUrl(dataSet[position].url)
        }
    }
    override fun getItemCount() = dataSet.size
}

interface OpenLink{
    fun gotoUrl(url: String)
}