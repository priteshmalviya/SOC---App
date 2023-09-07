package com.pritesh.soc.Activity

import android.annotation.SuppressLint
import android.app.DownloadManager.Request
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import com.pritesh.soc.Adapter.StudentAdapter
import com.pritesh.soc.Adapter.StudentDetailes
import com.pritesh.soc.R
import com.pritesh.soc.modles.StudentForm
import com.pritesh.soc.modles.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StudentsActivity : AppCompatActivity(), StudentDetailes {

    private lateinit var mDbRef : DatabaseReference
    private lateinit var adapter: StudentAdapter
    val StudentList=ArrayList<StudentForm>()
    val ThroughoutList = ArrayList<StudentForm>()
    val MatchedStudentList=ArrayList<StudentForm>()
    private lateinit var Username : String
    private lateinit var ImgUrl : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_students)

        val rcv=findViewById<RecyclerView>(R.id.rcv)
        mDbRef = FirebaseDatabase.getInstance().reference
        Username=intent.getStringExtra("UserName").toString()

        // ActionBar
        val UserProfil=findViewById<ImageView>(R.id.UserProfile)
        val HomeIcon=findViewById<ImageView>(R.id.HomeIcon)
        val switch=findViewById<Switch>(R.id.switch1)

        mDbRef.child("Users").child(Username.substring(0,Username.indexOf('.')).lowercase()).get().addOnSuccessListener {
            val UserData =it.getValue(User::class.java)!!
            ImgUrl=UserData.imageUrl
            if(ImgUrl!="") {
                Glide.with(this).load(ImgUrl).circleCrop().into(UserProfil)
            }
        }.addOnFailureListener{
            Toast.makeText(this, "Failed To Fetch Data...", Toast.LENGTH_SHORT).show()
            finish()
        }

        UserProfil.setOnClickListener {
            val intent= Intent(this,UserProfileActivity::class.java)
            intent.putExtra("UserName",Username)
            startActivity(intent)
        }

        findViewById<Button>(R.id.DownloadExelebtn).setOnClickListener {
            openExcelSheet("https://docs.google.com/spreadsheets/d/13f7KqzepWFNmnb0CPvM1OJbBovBANiAtqbf05TwBO30/edit#gid=0");
        }

        HomeIcon.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        val NoResult=findViewById<ImageView>(R.id.NoResult)

        switch.setOnClickListener {
            findViewById<EditText>(R.id.SearchBox).setText("")
            MatchedStudentList.clear()
            if (switch.isChecked){
                for(i in ThroughoutList){
                    MatchedStudentList.add(i)
                }
                adapter.notifyDataSetChanged()
            }else{
                for(i in StudentList){
                    MatchedStudentList.add(i)
                }
                adapter.notifyDataSetChanged()
            }
            if(MatchedStudentList.size>0){
                NoResult.isVisible=false
                rcv.isVisible=true
            }else{
                NoResult.isVisible=true
                rcv.isVisible=false
            }
            findViewById<TextView>(R.id.TotalStudents).text="Total Students : "+MatchedStudentList.size
        }


        mDbRef.child("StudentData").addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                StudentList.clear()
                MatchedStudentList.clear()
                for (postSnapshot in snapshot.children) {
                    val student = postSnapshot.getValue(StudentForm::class.java)!!
                    StudentList.add(student)
                    MatchedStudentList.add(student)
                    /*if(student.schoolpercentageSSc.toInt()>=60 && student.schoolpercentageHSSc.toInt()>=60 && student.aggregateUgPercentage.toInt()>=60){
                        ThroughoutList.add(student)
                    }*/
                }
                if(MatchedStudentList.size>0){
                    NoResult.isVisible=false
                    rcv.isVisible=true
                }else{
                    NoResult.isVisible=true
                    rcv.isVisible=false
                }
                findViewById<TextView>(R.id.TotalStudents).text="Total Students : "+MatchedStudentList.size
                adapter.notifyDataSetChanged()
            }
            override fun onCancelled(e: DatabaseError){
                Toast.makeText(this@StudentsActivity, e.toString(), Toast.LENGTH_SHORT).show()
            }
        })


        findViewById<EditText>(R.id.SearchBox).addTextChangedListener(object :
            TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                MatchedStudentList.clear()
                if (switch.isChecked){
                    for(p in ThroughoutList){
                        if (p.name.toLowerCase().contains(p0.toString().toLowerCase()) ||
                            p.enrollNo.toLowerCase().contains(p0.toString().toLowerCase())||
                            p.course.toLowerCase().contains(p0.toString().toLowerCase())){
                            MatchedStudentList.add(p)
                        }
                    }
                    //Toast.makeText(this@StudentsActivity, switch.isChecked.toString(), Toast.LENGTH_SHORT).show()
                }else {
                    for (p in StudentList) {
                        if (p.name.toLowerCase().contains(p0.toString().toLowerCase()) ||
                            p.enrollNo.toLowerCase().contains(p0.toString().toLowerCase()) ||
                            p.course.toLowerCase().contains(p0.toString().toLowerCase())
                        ) {
                            MatchedStudentList.add(p)
                        }
                    }
                    //Toast.makeText(this@StudentsActivity, switch.isChecked.toString(), Toast.LENGTH_SHORT).show()
                }
                if(MatchedStudentList.size>0){
                    NoResult.isVisible=false
                    rcv.isVisible=true
                }else{
                    NoResult.isVisible=true
                    rcv.isVisible=false
                }
                findViewById<TextView>(R.id.TotalStudents).text="Total Students : "+MatchedStudentList.size
                adapter.notifyDataSetChanged()
            }
        })


        adapter = StudentAdapter(MatchedStudentList,this)
        rcv.layoutManager = LinearLayoutManager(this)
        rcv.adapter = adapter

    }

    private fun openExcelSheet(url: String) {
        val uri : Uri = Uri.parse(url)
        startActivity(Intent(Intent.ACTION_VIEW,uri))
    }



    override fun openActivity(id: String) {
        val intent = Intent(this,ViewStudentProfileActivity::class.java)
        intent.putExtra("UserName",Username)
        intent.putExtra("ImgUrl",ImgUrl)
        intent.putExtra("StudentName",id)
        startActivity(intent)
    }
}