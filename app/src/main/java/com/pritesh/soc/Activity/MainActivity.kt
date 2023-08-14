package com.pritesh.soc.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.pritesh.soc.R
import com.pritesh.soc.modles.User
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    var Username : String = ""
    private lateinit var mDbRef : DatabaseReference
    var ImgUrl : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mDbRef = FirebaseDatabase.getInstance().reference

        val ProfileImage=findViewById<ImageView>(R.id.UserProfile)

        findViewById<Button>(R.id.facultyBtn).setOnClickListener {
            val intent= Intent(this,FacultyActivity::class.java)
            intent.putExtra("UserName",Username)
            startActivity(intent)
        }

        findViewById<Button>(R.id.PlayVideo).setOnClickListener {
            val intent= Intent(this,PlacmentVideo::class.java)
            intent.putExtra("UserName",Username)
            startActivity(intent)
        }

        ProfileImage.setOnClickListener {
            val intent= Intent(this,UserProfileActivity::class.java)
            intent.putExtra("UserName",Username)
            startActivity(intent)
        }

        findViewById<Button>(R.id.Login).setOnClickListener {
            val intent= Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.studentBtn).setOnClickListener {
            val intent= Intent(this,StudentsActivity::class.java)
            intent.putExtra("UserName",Username)
            startActivity(intent)
        }


        findViewById<Button>(R.id.UpdateBtn).setOnClickListener {
            val intent = Intent(this,UpdateProfileActivity::class.java)
            intent.putExtra("UserName",Username)
            intent.putExtra("ImgUrl",ImgUrl)
            startActivity(intent)
        }

        findViewById<Button>(R.id.ViewProfile).setOnClickListener {
            val intent = Intent(this,ViewStudentProfileActivity::class.java)
            intent.putExtra("UserName",Username)
            intent.putExtra("ImgUrl",ImgUrl)
            intent.putExtra("StudentName",Username)
            startActivity(intent)
        }

    }


    private fun UpdateUi() {
        val ProfileImage=findViewById<ImageView>(R.id.UserProfile)
        findViewById<Button>(R.id.Login).isVisible=false
        findViewById<Button>(R.id.UpdateBtn).isVisible=true
        ProfileImage.isVisible=true

        mDbRef.child("Users").child(Username.substring(0, Username.indexOf('.')).lowercase()).get()
            .addOnSuccessListener {
                val UserData = it.getValue(User::class.java)!!
                if (UserData.imageUrl != "") {
                    Glide.with(this).load(UserData.imageUrl).circleCrop().into(ProfileImage)
                    ImgUrl=UserData.imageUrl
                }
                findViewById<Button>(R.id.studentBtn).isVisible = UserData.admin
            }.addOnFailureListener {
                Toast.makeText(this, "Failed To Fetch Data...", Toast.LENGTH_SHORT).show()
                finish()
            }

        mDbRef.child("StudentData").child(Username.substring(0,Username.indexOf('.')).lowercase()).get().addOnSuccessListener {
            if (it.value!=null){
                findViewById<Button>(R.id.ViewProfile).isVisible=true
            }
        }.addOnFailureListener{
            Toast.makeText(this, "Failed Fetch data...", Toast.LENGTH_SHORT).show()
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
            //Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            finishAffinity() // Close all activites
            System.exit(0)  // closing files, releasing resources
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Press Again To Exit", Toast.LENGTH_SHORT).show()

        Handler(Looper.getMainLooper()).postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)


    }
}