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
            var stdname="pritesh";
            var stdclass="MCA";
            var stdRoll=20;
            openExcelSheet("https://docs.google.com/spreadsheets/d/13f7KqzepWFNmnb0CPvM1OJbBovBANiAtqbf05TwBO30/edit#gid=0");
            //senddata();
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

    /*private fun senddata() {
        val queue :  RequestQueue = Volley.newRequestQueue(this)
                var url="https://script.google.com/macros/s/AKfycbxBmSvWjtiXzeBxfuVtnmu-SwnxXP85foqjYKO4EAEx_lgT7F_V3-PZQ_jXZ5IR1kGq/exec?"
                url=url+"action=create&placment="+data.placment+"&fullname="+data.name+"&enroll="+data.enrollNo+"&university=RGPV&course="+data.course+"&yoa="+data.yoa+"&yop="+data.yop+"&dob="+data.dob+"&gender="+data.gender+"&category="+data.category+"&abled="+data.speciallyAbled+"&ssc="+data.schoolpercentageSSc+"&hssc="+data.schoolpercentageHSSc+"&diplomapercentage="+data.diplomaPercentage+"&ugcgpa="+data.aggregateUgCgpa+"&ugpercentage="+data.aggregateUgPercentage+"&pgcgpa="+data.aggregatePgCgpa+"&pgpercentage="+data.aggregatePgPercentage+"&ugcourse="+data.ugCourse+"&ugspecialization="+data.ugSpecialization+"&ugyop="+data.ugyop+"&backlog="+data.activeBacklog+"&backlognumber="+data.numberOfBacklog+"&backloghistory="+data.backlogHistory+"&diploma="+data.diploma+"&diplomayop="+data.diplomayop+"&diplomayoa="+data.diplomayoa+"&diplomainstitute="+data.diplomaInstitute+"&mobile="+data.mobile+"&alternetmobile="+data.alternatemobile+"&email="+data.email+"&sscname="+data.schoolNameSSc+"&sscboard="+data.schoolBoarSSc+"&sscyop="+data.schoolYopSSc+"&hsscname="+data.schoolNameHSSc+"&hsscboard="+data.schoolBoarHSSc+"&hsscyop="+data.schoolYopHSSc+"&gap="+data.gap+"&gapnumber="+data.gapyear+"&sem1sgpa="+data.semester1sgpa+"&sem1atkt="+data.semester1Atkt+"&sem2sgpa="+data.semester2sgpa+"&sem2atkt="+data.semester2Atkt+"&sem3sgpa="+data.semester3sgpa+"&sem3atkt="+data.semester3Atkt+"&sem4sgpa="+data.semester4sgpa+"&sem4atkt="+data.semester4Atkt+"&sem5sgpa="+data.semester5sgpa+"&sem5atkt="+data.semester5Atkt+"&sem6sgpa="+data.semester6sgpa+"&sem6atkt="+data.semester6Atkt+"&sem7sgpa="+data.semester7sgpa+"&sem7atkt="+data.semester7Atkt+"&sem8sgpa="+data.semester8sgpa+"&sem8atkt="+data.semester8Atkt+"&localadd="+data.localAdd+"&localpin="+data.pincodeLocal+"&permanentadd="+data.permanentAdd+"&permanenthometown="+data.permanentHome+"&permanentstate="+data.permanentState+"&permanentpin="+data.permanentPincode+"&fathername="+data.fatherName+"&fathercontect="+data.fatherContect+"&fatheroccupation="+data.fatherOccupation+"&mothername="+data.motherName+"&mothercontect="+data.motherContect+"&internship="+data.internship+"&imgurl="+data.imageUrl+"&declaration=Yes";
                val stringrequest = StringRequest(com.android.volley.Request.Method.GET, url ,Response.Listener<String> { response ->
                    // Display the first 500 characters of the response string.
                    Toast.makeText(this, response.substring(0, 500), Toast.LENGTH_SHORT).show()
                },
                    Response.ErrorListener { Toast.makeText(this,"failed to update", Toast.LENGTH_SHORT).show()})

                queue.add(stringrequest)
        }

        //Toast.makeText(this, url, Toast.LENGTH_SHORT).show()

    }*/



    override fun openActivity(id: String) {
        val intent = Intent(this,ViewStudentProfileActivity::class.java)
        intent.putExtra("UserName",Username)
        intent.putExtra("ImgUrl",ImgUrl)
        intent.putExtra("StudentName",id)
        startActivity(intent)
    }
}