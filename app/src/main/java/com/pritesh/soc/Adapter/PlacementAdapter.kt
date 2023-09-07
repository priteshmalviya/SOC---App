package com.pritesh.soc.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pritesh.soc.R
import com.pritesh.soc.modles.Placement

class PlacementAdapter(private val dataSet: ArrayList<Placement>, private val listener  : OpenLinkedin) :
    RecyclerView.Adapter<PlacementAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView
        val post: TextView
        val image : ImageView
        val packege : TextView
        //val title : TextView
        init {
            name = view.findViewById(R.id.NameOfTheFaculty)
            post = view.findViewById(R.id.PostOfTheFaculty)
            image = view.findViewById(R.id.ImageOfTheFaculty)
            packege=view.findViewById(R.id.packegeForPlacment)
            //title=view.findViewById(R.id.title)
        }
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.faculty_list_segment, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.name.text=dataSet[position].name
        //viewHolder.title.text="Package"
        viewHolder.post.text=dataSet[position].dsignation+" At "+dataSet[position].company
        viewHolder.packege.text="Package\n"+dataSet[position].packege+" Lpa"
        if (dataSet[position].image!="") {
            Glide.with(viewHolder.itemView.context).load(dataSet[position].image).into(viewHolder.image)
        }
        /*viewHolder.itemView.setOnClickListener {
            listener.gotoUrl(dataSet[position].)
            //Toast.makeText(viewHolder.itemView.context,dataSet[position].url, Toast.LENGTH_SHORT).show()
        }*/
    }
    override fun getItemCount() = dataSet.size
}

interface OpenLinkedin{
    fun gotoUrl(url: String)
}