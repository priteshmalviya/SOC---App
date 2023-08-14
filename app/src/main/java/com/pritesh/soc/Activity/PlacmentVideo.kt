package com.pritesh.soc.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import com.pritesh.soc.Adapter.OpenLink
import com.pritesh.soc.Adapter.VideoAdapter
import com.pritesh.soc.R
import com.pritesh.soc.modles.User
import com.pritesh.soc.modles.Video
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

class PlacmentVideo : AppCompatActivity(), OpenLink {

    private lateinit var mDbRef : DatabaseReference
    private lateinit var adapter: VideoAdapter
    val VideoList=ArrayList<Video>()
    private var Username = ""
    private var ImgUrl = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_placment_video)

        mDbRef = FirebaseDatabase.getInstance().reference
        Username=intent.getStringExtra("UserName").toString()

        val rcv=findViewById<RecyclerView>(R.id.rcv)
        val addVideoBtn=findViewById<Button>(R.id.Add_New_Video_Btn)

        findViewById<ImageView>(R.id.LoginIcon).setOnClickListener {
            val intent= Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }

        // ActionBar
        val UserProfil=findViewById<ImageView>(R.id.UserProfile)
        val HomeIcon=findViewById<ImageView>(R.id.HomeIcon)


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
            val intent = Intent(this, AddNewVideo::class.java)
            intent.putExtra("UserName",Username)
            intent.putExtra("ImgUrl",ImgUrl)
            startActivity(intent)
            finish()
        }


        mDbRef.child("Videos").addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                VideoList.clear()
                for (postSnapshot in snapshot.children) {
                    val video = postSnapshot.getValue(Video::class.java)!!
                    VideoList.add(video)
                }
                adapter.notifyDataSetChanged()
            }
            override fun onCancelled(e: DatabaseError){
                Toast.makeText(this@PlacmentVideo, e.toString(), Toast.LENGTH_SHORT).show()
            }
        })


        adapter = VideoAdapter(VideoList,this)
        rcv.layoutManager = LinearLayoutManager(this)
        rcv.adapter = adapter

    }

    private fun UpdateUi() {
        val ProfileImage=findViewById<ImageView>(R.id.UserProfile)
        findViewById<ImageView>(R.id.LoginIcon).isVisible=false
        ProfileImage.isVisible=true

        mDbRef.child("Users").child(Username.substring(0, Username.indexOf('.')).lowercase()).get()
            .addOnSuccessListener {
                val UserData = it.getValue(User::class.java)!!
                ImgUrl=UserData.imageUrl
                if (ImgUrl != "") {
                    Glide.with(this).load(ImgUrl).circleCrop().into(ProfileImage)
                }
                findViewById<Button>(R.id.Add_New_Video_Btn).isVisible = UserData.admin
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