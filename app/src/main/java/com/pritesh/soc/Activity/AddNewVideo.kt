package com.pritesh.soc.Activity

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.pritesh.soc.R
import com.pritesh.soc.modles.Video
import java.io.ByteArrayOutputStream
import java.io.IOException

class AddNewVideo : AppCompatActivity() {

    private lateinit var mDbRef : DatabaseReference
    private lateinit var Image : ImageView
    private var PICK_IMAGE = 123
    private lateinit var StorageReference : StorageReference
    private var Imagepath : Uri? = null
    private lateinit var Username : String
    private lateinit var UserImg : String
    private lateinit var VideoData: Video

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_video)

        val addbtn=findViewById<Button>(R.id.AddFacultyBtn)

        Username = intent.getStringExtra("UserName").toString()
        UserImg = intent.getStringExtra("ImgUrl").toString()

        mDbRef = FirebaseDatabase.getInstance().reference
        StorageReference= FirebaseStorage.getInstance().reference

        Image = findViewById(R.id.Image)

        Image.setOnClickListener {
            UploadImage()
        }


        addbtn.setOnClickListener {

            val name=findViewById<EditText>(R.id.Name).text.toString()
            val post=findViewById<EditText>(R.id.Post).text.toString()
            val id=findViewById<EditText>(R.id.facultID).text.toString()
            val category=findViewById<EditText>(R.id.Category).text.toString()
            if(id=="" || name == "" || category==""){
                Toast.makeText(this, "Please Fill All Required Fields * ", Toast.LENGTH_SHORT).show()
            }else {
                findViewById<View>(R.id.LodingPage).isVisible = true
                window.setFlags(
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                )
                if (Imagepath.toString() != "hello") {
                    VideoData = Video(name, post,"" ,id,category)
                    sendImagetoStorage()
                } else {
                    VideoData = Video(name, post, VideoData.image,id,category)
                    AddFacultyData()
                }
            }
        }

    }

    private fun UploadImage() {
        intent= Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(intent,PICK_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            Imagepath = data!!.data
            Image.setImageURI(Imagepath)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun sendImagetoStorage() {
        val imageref = StorageReference.child("Images").child("Video").child("thambnile").child(VideoData.title.lowercase())

        //Image compresesion
        var bitmap: Bitmap? = null
        try {
            bitmap = MediaStore.Images.Media.getBitmap(contentResolver, Imagepath)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap!!.compress(Bitmap.CompressFormat.JPEG,10 , byteArrayOutputStream)
        val data: ByteArray = byteArrayOutputStream.toByteArray()

        ///putting image to storage
        val uploadTask = imageref.putBytes(data)
        uploadTask.addOnSuccessListener {
            imageref.downloadUrl.addOnSuccessListener { uri ->
                VideoData.image = uri.toString()
                AddFacultyData()
            }.addOnFailureListener {
                Toast.makeText(applicationContext,"Faild To Select Image.", Toast.LENGTH_SHORT).show()
            }
            Toast.makeText(applicationContext, "Data Is Uploaded", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(applicationContext,"Failed To Upload Image", Toast.LENGTH_SHORT).show()
        }
    }

    private fun AddFacultyData() {
        val path = VideoData.url.substring(VideoData.url.indexOf('.'))
        mDbRef.child("Videos").child(path).setValue(VideoData)
        Toast.makeText(this, "Video Added", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, PlacmentVideo::class.java)
        intent.putExtra("UserName",Username)
        intent.putExtra("ImgUrl",UserImg)
        startActivity(intent)
        finish()

    }
}