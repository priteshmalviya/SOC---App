package com.pritesh.soc.Activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.pritesh.soc.R
import com.pritesh.soc.modles.User
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    var Username : String = ""
    private lateinit var mDbRef : DatabaseReference
    var ImgUrl : String = ""
    var iscompany:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mDbRef = FirebaseDatabase.getInstance().reference

        val ProfileImage=findViewById<ImageView>(R.id.UserProfile)

        val layoutforyear=findViewById<View>(R.id.layoutforyear)
        val layoutforsyllabus=findViewById<View>(R.id.layoutforsyllabus)


        findViewById<ImageView>(R.id.LogoutIcon).setOnClickListener {
            val file = File(filesDir,"UserName.txt")
            if(file.delete()){
                val intent = Intent(this,LoginActivity::class.java)
                finish()
                startActivity(intent)
            }else{
                Toast.makeText(this, "Some Error Accured", Toast.LENGTH_SHORT).show()
            }
        }


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

        findViewById<TextView>(R.id.closetextforsyllabus).setOnClickListener {
            layoutforsyllabus.isVisible=false
        }

        findViewById<Button>(R.id.syllabus).setOnClickListener {
            layoutforsyllabus.isVisible=!layoutforyear.isVisible
            layoutforyear.isVisible=false
        }

        findViewById<TextView>(R.id.closetext).setOnClickListener {
            layoutforyear.isVisible=false
        }

        findViewById<Button>(R.id.Company).setOnClickListener {
            iscompany=true
            layoutforyear.isVisible=!layoutforyear.isVisible
            layoutforsyllabus.isVisible=false
        }

        findViewById<Button>(R.id.Placments).setOnClickListener {
            iscompany=false
            layoutforyear.isVisible=!layoutforyear.isVisible
            layoutforsyllabus.isVisible=false
        }

        findViewById<Button>(R.id.btn2023).setOnClickListener {
            layoutforyear.isVisible=false
            if (iscompany){
                val intent= Intent(this,CompaniesForPlacments::class.java)
                intent.putExtra("UserName",Username)
                intent.putExtra("year","2023")
                startActivity(intent)
            }else{
                val intent= Intent(this,Placments::class.java)
                intent.putExtra("UserName",Username)
                intent.putExtra("year","2023")
                startActivity(intent)
            }
        }

        findViewById<Button>(R.id.btn2022).setOnClickListener {
            layoutforyear.isVisible=false
            if (iscompany){
                val intent= Intent(this,CompaniesForPlacments::class.java)
                intent.putExtra("UserName",Username)
                intent.putExtra("year","2022")
                startActivity(intent)
            }else{
                val intent= Intent(this,Placments::class.java)
                intent.putExtra("UserName",Username)
                intent.putExtra("year","2022")
                startActivity(intent)
            }
        }

        findViewById<Button>(R.id.btnall).setOnClickListener {
            layoutforyear.isVisible=false
            if (iscompany){
                val intent= Intent(this,CompaniesForPlacments::class.java)
                intent.putExtra("UserName",Username)
                intent.putExtra("year","0")
                startActivity(intent)
            }else{
                val intent= Intent(this,Placments::class.java)
                intent.putExtra("UserName",Username)
                intent.putExtra("year","0")
                startActivity(intent)
            }
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


        findViewById<Button>(R.id.btnmca).setOnClickListener {
            mDbRef.child("Syllabus").child("Mca").get()
                .addOnSuccessListener {
                    layoutforsyllabus.isVisible=false
                    gotoUrl(it.value.toString())
                }.addOnFailureListener {
                    Toast.makeText(this, "Failed To Open Document...", Toast.LENGTH_SHORT).show()
                    finish()
                }
        }

        findViewById<Button>(R.id.btnimca).setOnClickListener {
            mDbRef.child("Syllabus").child("Imca").get()
                .addOnSuccessListener {
                    layoutforsyllabus.isVisible=false
                    gotoUrl(it.value.toString())
                }.addOnFailureListener {
                    Toast.makeText(this, "Failed To Open Document...", Toast.LENGTH_SHORT).show()
                    finish()
                }
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
    fun gotoUrl(url: String) {
        val uri : Uri = Uri.parse(url)
        startActivity(Intent(Intent.ACTION_VIEW,uri))
    }
}