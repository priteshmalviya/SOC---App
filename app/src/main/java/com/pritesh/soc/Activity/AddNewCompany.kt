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
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.pritesh.soc.R
import com.pritesh.soc.modles.Company
import com.pritesh.soc.modles.Video
import java.io.ByteArrayOutputStream
import java.io.IOException

class AddNewCompany : AppCompatActivity() {



    private lateinit var mDbRef : DatabaseReference
    private lateinit var Image : ImageView
    private var PICK_IMAGE = 123
    private lateinit var StorageReference : StorageReference
    private var Imagepath : Uri? = null
    private lateinit var Username : String
    private lateinit var UserImg : String
    private lateinit var CompanyData: Company
    private var year = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_company)


        val addbtn = findViewById<Button>(R.id.AddFacultyBtn)

        Username = intent.getStringExtra("UserName").toString()
        UserImg = intent.getStringExtra("ImgUrl").toString()
        year=intent.getStringExtra("year").toString()

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
            val year=findViewById<EditText>(R.id.year).text.toString()

            if(id=="" || name == "" || year==""){
                Toast.makeText(this, "Please Fill All Required Fields * ", Toast.LENGTH_SHORT).show()
            }else {
                findViewById<View>(R.id.LodingPage).isVisible = true
                window.setFlags(
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                )
                if (Imagepath != null) {
                    CompanyData = Company(name, post,"" ,id,year.toInt(),0)
                    sendImagetoStorage()
                    //AddFacultyData()
                } else {
                    CompanyData = Company(name,post,"",id,year.toInt(),0)
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
        val imageref = StorageReference.child("Images").child("Company").child("logo").child(CompanyData.name.lowercase())

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
                CompanyData.image = uri.toString()
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
        mDbRef.child("Company").child(CompanyData.name.toLowerCase()+CompanyData.year.toString()).get().addOnSuccessListener {
            if (it.value!=null){
                sendDataToExcel("update")
            }else{
                sendDataToExcel("create")
            }
        }


        mDbRef.child("Company").child(CompanyData.name.toLowerCase()+CompanyData.year.toString()).setValue(CompanyData)
        Toast.makeText(this, "company Added", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, CompaniesForPlacments::class.java)
        intent.putExtra("UserName",Username)
        intent.putExtra("ImgUrl",UserImg)
        intent.putExtra("year",year)
        startActivity(intent)
        finish()

    }


    private fun sendDataToExcel(action : String) {
        val data= CompanyData
        val queue : RequestQueue = Volley.newRequestQueue(this)
        var url="https://script.google.com/macros/s/AKfycbwJp9PoaPvx72BzwNmYvO9pZn9_kpEdeK_0kU2Rqsuf1sWLD5oUGCy-e3kT7I0ZL4Dj/exec?"
        url=url+"action="+action+"&name="+data.name+"&packege="+data.packege+" Lpa"+"&year="+data.year+"&placedStudent="+data.placedStudnts
        val stringrequest = StringRequest(
            Request.Method.GET, url , Response.Listener<String> { response ->
                // Display the first 500 characters of the response string.
                Toast.makeText(this, response.substring(0, 500), Toast.LENGTH_SHORT).show()
            },
            Response.ErrorListener {
                //Toast.makeText(this,"failed to update", Toast.LENGTH_SHORT).show()
            })

        queue.add(stringrequest)
    }

}