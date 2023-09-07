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
import com.pritesh.soc.modles.Placement
import java.io.ByteArrayOutputStream
import java.io.IOException

class AddNewPlacment : AppCompatActivity() {


    //https://script.google.com/macros/s/AKfycbz4a1Jqsg2ofW6ATPW_tXAlQAw4VDXMOGDTmkh-1JV3RZT6XxN0uqvFYEBCteX8z8Wz/exec

    private lateinit var mDbRef : DatabaseReference
    private lateinit var Image : ImageView
    private var PICK_IMAGE = 123
    private lateinit var StorageReference : StorageReference
    private var Imagepath : Uri? = null
    private lateinit var Username : String
    private lateinit var UserImg : String
    private lateinit var PlacementData : Placement
    private var year = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_placment)

        val addbtn=findViewById<Button>(R.id.AddNewStudent)

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
            val packege=findViewById<EditText>(R.id.packege).text.toString()
            val company=findViewById<EditText>(R.id.company).text.toString()
            val year=findViewById<EditText>(R.id.year).text.toString()
            val designation=findViewById<EditText>(R.id.Designation).text.toString()
            val enroll=findViewById<EditText>(R.id.enroll).text.toString()
            if(packege=="" || name == "" || company=="" || year =="" || designation==""){
                Toast.makeText(this, "Please Fill All Required Fields * ", Toast.LENGTH_SHORT).show()
            }else {
                findViewById<View>(R.id.LodingPage).isVisible = true

                mDbRef.child("Company").child(company.toLowerCase()+year).child("placedStudnts").get().addOnSuccessListener {
                    if (it.value!=null){
                        //Toast.makeText(this, it.value.toString(), Toast.LENGTH_SHORT).show()
                        mDbRef.child("Company").child(company.toLowerCase()+year).child("placedStudnts").setValue(it.value.toString().toInt()+1)
                    }
                }.addOnFailureListener{
                    Toast.makeText(this, "not found", Toast.LENGTH_SHORT).show()
                }

                window.setFlags(
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                )
                if (Imagepath != null) {
                    PlacementData = Placement(name, packege,"" ,enroll,company,year.toInt(),designation)
                    sendImagetoStorage()
                } else {
                    PlacementData = Placement(name,packege,"",enroll,company,year.toInt(),designation)
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
        val imageref = StorageReference.child("Images").child("placements").child("profile").child(PlacementData.name.lowercase())

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
                PlacementData.image = uri.toString()
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
        mDbRef.child("Placements").child(PlacementData.enrollment.toLowerCase()).get().addOnSuccessListener {
            if (it.value!=null){
                sendDataToExcel("update")
            }else{
                sendDataToExcel("create")
            }
        }

        mDbRef.child("Placements").child(PlacementData.enrollment.toLowerCase()).setValue(PlacementData)
        Toast.makeText(this, "Data Added", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, Placments::class.java)
        intent.putExtra("UserName",Username)
        intent.putExtra("ImgUrl",UserImg)
        intent.putExtra("year",year)
        startActivity(intent)
        finish()

    }

    private fun sendDataToExcel(action : String) {
        val data= PlacementData
        var url="https://script.google.com/macros/s/AKfycbz4a1Jqsg2ofW6ATPW_tXAlQAw4VDXMOGDTmkh-1JV3RZT6XxN0uqvFYEBCteX8z8Wz/exec?"
        url=url+"action="+action+"&name="+data.name+"&packege="+data.packege+" Lpa"+"&enrollment="+data.enrollment+"&image="+data.image+"&company="+data.company+"&year="+data.year+"&dsignation="+data.dsignation

        val stringrequest = StringRequest(Request.Method.GET, url , Response.Listener{},Response.ErrorListener {})

        val queue : RequestQueue = Volley.newRequestQueue(this)
        queue.add(stringrequest)
    }

}