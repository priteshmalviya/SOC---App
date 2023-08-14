package com.pritesh.soc.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.pritesh.soc.R
import com.pritesh.soc.modles.User
import java.io.File

class UserProfileActivity : AppCompatActivity() {

    private lateinit var mDbRef : DatabaseReference
    private lateinit var Username : String
    private lateinit var ImgUrl : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        Username=intent.getStringExtra("UserName").toString()
        mDbRef = FirebaseDatabase.getInstance().reference



        val ProfileImage=findViewById<ImageView>(R.id.ProfileImage)
        val Name=findViewById<TextView>(R.id.UserName)
        val Email =findViewById<TextView>(R.id.UserEmail)
        val Phone =findViewById<TextView>(R.id.UserPhone)
        val updateBtn=findViewById<Button>(R.id.UpdateBtn)
        val ShowProfile=findViewById<Button>(R.id.ViewProfile)
        val logoutIcon=findViewById<ImageView>(R.id.LogoutIcon)
        val HomeIcon=findViewById<ImageView>(R.id.HomeIcon)

        updateBtn.setOnClickListener {
            val intent = Intent(this,UpdateProfileActivity::class.java)
            intent.putExtra("UserName",Username)
            intent.putExtra("ImgUrl",ImgUrl)
            startActivity(intent)
        }

        ShowProfile.setOnClickListener {
            val intent = Intent(this,ViewStudentProfileActivity::class.java)
            intent.putExtra("UserName",Username)
            intent.putExtra("ImgUrl",ImgUrl)
            intent.putExtra("StudentName",Username)
            startActivity(intent)
        }


        logoutIcon.setOnClickListener {
            val file = File(filesDir,"UserName.txt")
            if(file.delete()){
                val intent = Intent(this,LoginActivity::class.java)
                finish()
                startActivity(intent)
            }else{
                Toast.makeText(this, "Some Error Accured", Toast.LENGTH_SHORT).show()
            }
        }

        HomeIcon.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        mDbRef.child("Users").child(Username.substring(0,Username.indexOf('.')).lowercase()).get().addOnSuccessListener {
            val UserData =it.getValue(User::class.java)!!
            Name.text=UserData.name
            Email.text=UserData.email
            if(UserData.email.length>25){
                Email.text=UserData.email.substring(0,25)+"..."
            }
            ImgUrl=UserData.imageUrl
            updateBtn.isEnabled=true
            if(ImgUrl!="") {
                Glide.with(this).load(ImgUrl).circleCrop().into(ProfileImage)
            }
            if(UserData.mobileNumber!=""){
                Phone.text="+91 "+UserData.mobileNumber
            }
        }.addOnFailureListener{
            Toast.makeText(this, "Failed To Fetch Data...", Toast.LENGTH_SHORT).show()
            finish()
        }

        /*mDbRef.child("StudentData").child(Username.substring(0,Username.indexOf('.')).lowercase()).get().addOnSuccessListener {
            if (it.value!=null){
                ShowProfile.isVisible=true
            }
        }.addOnFailureListener{
            Toast.makeText(this, "Failed Fetch data...", Toast.LENGTH_SHORT).show()
        }*/

    }
}