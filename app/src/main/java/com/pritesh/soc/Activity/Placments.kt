package com.pritesh.soc.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import com.pritesh.soc.Adapter.OpenLinkedin
import com.pritesh.soc.Adapter.PlacementAdapter
import com.pritesh.soc.R
import com.pritesh.soc.modles.Placement
import com.pritesh.soc.modles.User
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader


class Placments : AppCompatActivity(), OpenLinkedin {

    private lateinit var mDbRef : DatabaseReference
    private lateinit var adapter: PlacementAdapter
    val placementList=ArrayList<Placement>()
    private var Username = ""
    private var ImgUrl = ""
    private var year = 2023

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_placments)

        mDbRef = FirebaseDatabase.getInstance().reference
        Username=intent.getStringExtra("UserName").toString()
        year=intent.getStringExtra("year").toString().toInt()


        val rcv=findViewById<RecyclerView>(R.id.rcv)
        val addVideoBtn=findViewById<Button>(R.id.Add_New_Video_Btn)
        if (year!=0) {
            findViewById<TextView>(R.id.title).text = "Placements " + year.toString()
        }

        // ActionBar
        val UserProfil=findViewById<ImageView>(R.id.UserProfile)
        val HomeIcon=findViewById<ImageView>(R.id.HomeIcon)
        val download=findViewById<ImageView>(R.id.Downloadbtn)


        UserProfil.setOnClickListener {
            val intent= Intent(this,UserProfileActivity::class.java)
            intent.putExtra("UserName",Username)
            startActivity(intent)
        }

        HomeIcon.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }


        addVideoBtn.setOnClickListener {
            val intent = Intent(this, AddNewPlacment::class.java)
            intent.putExtra("UserName",Username)
            intent.putExtra("ImgUrl",ImgUrl)
            intent.putExtra("year",year.toString())
            startActivity(intent)
            finish()
        }

        download.setOnClickListener {
            gotoUrl("https://docs.google.com/spreadsheets/d/1cclaB2EBLOzQxKBiPFF3BWW0nFtYHwyRs2aKA4_e1MY/edit?usp=sharing")
        }


        mDbRef.child("Placements").addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                placementList.clear()
                for (postSnapshot in snapshot.children) {
                    val placement = postSnapshot.getValue(Placement::class.java)!!
                    if (year!=0) {
                        if (placement.year == year) {
                            placementList.add(placement)
                        }
                    }else{
                        placementList.add(placement)
                    }
                }
                adapter.notifyDataSetChanged()
            }
            override fun onCancelled(e: DatabaseError){
                Toast.makeText(this@Placments, e.toString(), Toast.LENGTH_SHORT).show()
            }
        })


        adapter = PlacementAdapter(placementList,this)
        rcv.layoutManager = LinearLayoutManager(this)
        rcv.adapter = adapter
    }

    private fun UpdateUi() {
        val ProfileImage=findViewById<ImageView>(R.id.UserProfile)
        ProfileImage.isVisible=true

        mDbRef.child("Users").child(Username.substring(0, Username.indexOf('.')).lowercase()).get()
            .addOnSuccessListener {
                val UserData = it.getValue(User::class.java)!!
                ImgUrl=UserData.imageUrl
                if (ImgUrl != "") {
                    Glide.with(this).load(ImgUrl).circleCrop().into(ProfileImage)
                }
                findViewById<Button>(R.id.Add_New_Video_Btn).isVisible = UserData.admin
                findViewById<ImageView>(R.id.Downloadbtn).isVisible = UserData.admin
            }.addOnFailureListener {
                Toast.makeText(this, "Failed To Fetch Data...", Toast.LENGTH_SHORT).show()
                finish()
            }
    }

    override fun onStart() {
        super.onStart()
        val fis : FileInputStream
        try {
            fis=openFileInput("UserName.txt")
            val isr= InputStreamReader(fis)
            val br= BufferedReader(isr)
            val sb=StringBuilder()
            sb.append(br.readLine())
            Username=sb.toString()
            UpdateUi()
        }catch (e : Exception){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    override fun gotoUrl(url: String) {
        val uri : Uri = Uri.parse(url)
        startActivity(Intent(Intent.ACTION_VIEW,uri))
    }

}