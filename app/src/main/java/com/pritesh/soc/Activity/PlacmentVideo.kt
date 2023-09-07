package com.pritesh.soc.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.play.core.integrity.p
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
    var MatchedVideoList = ArrayList<Video>()
    private var Username = ""
    private var ImgUrl = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_placment_video)

        mDbRef = FirebaseDatabase.getInstance().reference
        Username=intent.getStringExtra("UserName").toString()

        val rcv=findViewById<RecyclerView>(R.id.rcv)
        val addVideoBtn=findViewById<Button>(R.id.Add_New_Video_Btn)


        // ActionBar
        val UserProfil=findViewById<ImageView>(R.id.UserProfile)
        val HomeIcon=findViewById<ImageView>(R.id.HomeIcon)
        val NoResult=findViewById<ImageView>(R.id.NoResult)


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
                    MatchedVideoList.add(video)
                }
                adapter.notifyDataSetChanged()
            }
            override fun onCancelled(e: DatabaseError){
                Toast.makeText(this@PlacmentVideo, e.toString(), Toast.LENGTH_SHORT).show()
            }
        })



        findViewById<EditText>(R.id.SearchBox).addTextChangedListener(object :
            TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                MatchedVideoList.clear()

                for (video in VideoList) {
                    if (video.title.toLowerCase().contains(p0.toString().toLowerCase()) || video.category.toLowerCase().contains(p0.toString().toLowerCase())){
                        MatchedVideoList.add(video)
                    }
                }
                //Toast.makeText(this@StudentsActivity, switch.isChecked.toString(), Toast.LENGTH_SHORT).show()
                if(MatchedVideoList.size>0){
                    NoResult.isVisible=false
                    rcv.isVisible=true
                }else{
                    NoResult.isVisible=true
                    rcv.isVisible=false
                }
                adapter.notifyDataSetChanged()
            }
        })



        adapter = VideoAdapter(MatchedVideoList,this)
        rcv.layoutManager = LinearLayoutManager(this)
        rcv.adapter = adapter

    }

    private fun UpdateUi() {
        val ProfileImage=findViewById<ImageView>(R.id.UserProfile)

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