package com.pritesh.soc.Activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.pritesh.soc.R
import com.pritesh.soc.modles.StudentForm

class ViewStudentProfileActivity : AppCompatActivity() {



    private lateinit var mDbRef : DatabaseReference
    private lateinit var Username : String
    private lateinit var ImgUrl : String
    private lateinit var StudentName : String
    private lateinit var StudentData : StudentForm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_student_profile)

        Username=intent.getStringExtra("UserName").toString()
        ImgUrl=intent.getStringExtra("ImgUrl").toString()
        StudentName=intent.getStringExtra("StudentName").toString()
        mDbRef = FirebaseDatabase.getInstance().reference

        // ActionBar
        val UserProfil=findViewById<ImageView>(R.id.UserProfile)
        val HomeIcon=findViewById<ImageView>(R.id.HomeIcon)

        if (ImgUrl !="") {
            Glide.with(this).load(ImgUrl).circleCrop().into(UserProfil)
        }

        UserProfil.setOnClickListener {
            val intent= Intent(this,UserProfileActivity::class.java)
            intent.putExtra("UserName",Username)
            startActivity(intent)
        }

        HomeIcon.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        mDbRef.child("StudentData").child(StudentName.substring(0,StudentName.indexOf('.')).lowercase()).get().addOnSuccessListener {
            if (it.value!=null){
                StudentData=it.getValue(StudentForm::class.java)!!
                findViewById<TextView>(R.id.StudentNameAction).text=StudentData.name
                updateUi()
            }
        }.addOnFailureListener{
            //Toast.makeText(this, "Failed To Add Product...", Toast.LENGTH_SHORT).show()
        }

        findViewById<TextView>(R.id.stMobile).setOnClickListener {
            MakeCall(findViewById<TextView>(R.id.stMobile).text.toString())
        }


        findViewById<TextView>(R.id.stAltMobile).setOnClickListener {
            MakeCall(findViewById<TextView>(R.id.stAltMobile).text.toString())
        }

        findViewById<TextView>(R.id.stfatherMobile).setOnClickListener {
            MakeCall(findViewById<TextView>(R.id.stfatherMobile).text.toString())
        }

        findViewById<TextView>(R.id.stmotherMobile).setOnClickListener {
            MakeCall(findViewById<TextView>(R.id.stmotherMobile).text.toString())
        }

        findViewById<TextView>(R.id.stEmail).setOnClickListener {
            sendMail(findViewById<TextView>(R.id.stEmail).text.toString())
        }

    }

    private fun sendMail(Email: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + Email))
        intent.putExtra(Intent.EXTRA_SUBJECT, "helloo")
        intent.putExtra(Intent.EXTRA_TEXT, "hfofsiufopkjh")
        startActivity(intent)
    }

    private fun MakeCall(number: String) {
        val callIntent = Intent(Intent.ACTION_DIAL)
        callIntent.data = Uri.parse("tel:"+number)
        startActivity(callIntent)
    }


    private fun updateUi() {
        val stImage = findViewById<ImageView>(R.id.StudentImage)
        Glide.with(this).load(StudentData.imageUrl).into(stImage)
        findViewById<TextView>(R.id.stName).text=StudentData.name
        findViewById<TextView>(R.id.stEnroll).text=StudentData.enrollNo
        findViewById<TextView>(R.id.stCourse).text=StudentData.course
        findViewById<TextView>(R.id.stYoa).text=StudentData.yoa
        findViewById<TextView>(R.id.stYop).text=StudentData.yop
        findViewById<TextView>(R.id.stDob).text=StudentData.dob
        findViewById<TextView>(R.id.stGender).text=StudentData.gender
        findViewById<TextView>(R.id.stCategory).text=StudentData.category
        findViewById<TextView>(R.id.stHandicapped).text=StudentData.speciallyAbled.toString()
        findViewById<TextView>(R.id.stFathername).text=StudentData.fatherName
        findViewById<TextView>(R.id.stfatheroccupation).text=StudentData.fatherOccupation
        findViewById<TextView>(R.id.stMothername).text=StudentData.motherName
        findViewById<TextView>(R.id.stSscSchool).text=StudentData.schoolNameSSc
        findViewById<TextView>(R.id.stSscBoard).text=StudentData.schoolBoarSSc
        findViewById<TextView>(R.id.stSscYop).text=StudentData.schoolYopSSc
        findViewById<TextView>(R.id.stSscPercentage).text=StudentData.schoolpercentageSSc+" %"
        findViewById<TextView>(R.id.stHSscSchool).text=StudentData.schoolNameHSSc
        findViewById<TextView>(R.id.stHSscBoard).text=StudentData.schoolBoarHSSc
        findViewById<TextView>(R.id.stHSscYop).text=StudentData.schoolYopHSSc
        findViewById<TextView>(R.id.stHSscPercentage).text=StudentData.schoolpercentageHSSc+" %"
        findViewById<TextView>(R.id.stUgcourse).text=StudentData.ugCourse
        findViewById<TextView>(R.id.stUgSpecialization).text=StudentData.ugSpecialization
        findViewById<TextView>(R.id.stUgyop).text=StudentData.ugyop
        findViewById<TextView>(R.id.stUgcgpa).text=StudentData.aggregateUgCgpa
        findViewById<TextView>(R.id.stUgpercentage).text=StudentData.aggregateUgPercentage + " %"
        findViewById<TextView>(R.id.stPgcgpa).text=StudentData.aggregatePgCgpa
        findViewById<TextView>(R.id.stPgpercentage).text=StudentData.aggregatePgPercentage + " %"
        findViewById<TextView>(R.id.stSem1Sgpa).text=StudentData.semester1sgpa
        findViewById<TextView>(R.id.stSem1ATKT).text=StudentData.semester1Atkt
        findViewById<TextView>(R.id.stSem2Sgpa).text=StudentData.semester2sgpa
        findViewById<TextView>(R.id.stSem2ATKT).text=StudentData.semester2Atkt
        findViewById<TextView>(R.id.stSem3Sgpa).text=StudentData.semester3sgpa
        findViewById<TextView>(R.id.stSem3ATKT).text=StudentData.semester3Atkt
        findViewById<TextView>(R.id.stSem4Sgpa).text=StudentData.semester4sgpa
        findViewById<TextView>(R.id.stSem4ATKT).text=StudentData.semester4Atkt
        findViewById<TextView>(R.id.stSem5Sgpa).text=StudentData.semester5sgpa
        findViewById<TextView>(R.id.stSem5ATKT).text=StudentData.semester5Atkt
        findViewById<TextView>(R.id.stSem6Sgpa).text=StudentData.semester6sgpa
        findViewById<TextView>(R.id.stSem6ATKT).text=StudentData.semester6Atkt
        findViewById<TextView>(R.id.stSem7Sgpa).text=StudentData.semester7sgpa
        findViewById<TextView>(R.id.stSem7ATKT).text=StudentData.semester7Atkt
        findViewById<TextView>(R.id.stSem8Sgpa).text=StudentData.semester8sgpa
        findViewById<TextView>(R.id.stSem8ATKT).text=StudentData.semester8Atkt
        findViewById<TextView>(R.id.stActivebacklog).text=if (StudentData.activeBacklog) StudentData.numberOfBacklog else "No"
        findViewById<TextView>(R.id.stBacklogHistory).text=if (StudentData.backlogHistory) "Yes" else "No"
        findViewById<TextView>(R.id.stGap).text=if (StudentData.gap) StudentData.gapyear else "No"
        findViewById<TextView>(R.id.stDiplomaIntitute).text=StudentData.diplomaInstitute
        findViewById<TextView>(R.id.stDiplomaPercentage).text=StudentData.diplomaPercentage
        findViewById<TextView>(R.id.stDiplomaYoa).text=StudentData.diplomayoa
        findViewById<TextView>(R.id.stDiplomayop).text=StudentData.diplomayop
        findViewById<TextView>(R.id.stDiploma).text=StudentData.diplomaCourse
        findViewById<TextView>(R.id.stMobile).text="+91"+StudentData.mobile
        findViewById<TextView>(R.id.stAltMobile).text="+91"+StudentData.alternatemobile
        findViewById<TextView>(R.id.stEmail).text=StudentData.email
        findViewById<TextView>(R.id.stfatherMobile).text="+91"+StudentData.fatherContect
        findViewById<TextView>(R.id.stmotherMobile).text="+91"+StudentData.motherContect
        findViewById<TextView>(R.id.stLocalAdd).text=StudentData.localAdd
        findViewById<TextView>(R.id.stLocalpin).text=StudentData.pincodeLocal
        findViewById<TextView>(R.id.stperAdd).text=StudentData.permanentAdd
        findViewById<TextView>(R.id.stPerPin).text=StudentData.permanentPincode
        findViewById<TextView>(R.id.stPerTown).text=StudentData.permanentHome
        findViewById<TextView>(R.id.stPerstate).text=StudentData.permanentState

        if(StudentData.course=="MCA"){
            if(StudentData.semester2sgpa!=""){
                findViewById<View>(R.id.layoutSem2a).isVisible=true
                findViewById<View>(R.id.layoutSem2s).isVisible=true
            }
        }else{
            findViewById<View>(R.id.layoutSem2a).isVisible=true
            findViewById<View>(R.id.layoutSem2s).isVisible=true
            findViewById<View>(R.id.layoutSem3a).isVisible=true
            findViewById<View>(R.id.layoutSem3s).isVisible=true
            findViewById<View>(R.id.layoutSem4a).isVisible=true
            findViewById<View>(R.id.layoutSem4s).isVisible=true
            findViewById<View>(R.id.layoutSem5a).isVisible=true
            findViewById<View>(R.id.layoutSem5s).isVisible=true
            findViewById<View>(R.id.layoutSem6a).isVisible=true
            findViewById<View>(R.id.layoutSem6s).isVisible=true
            findViewById<View>(R.id.layoutSem7a).isVisible=true
            findViewById<View>(R.id.layoutSem7s).isVisible=true
            if(StudentData.semester8sgpa!=""){
                findViewById<View>(R.id.layoutSem8a).isVisible=true
                findViewById<View>(R.id.layoutSem8s).isVisible=true
            }
        }

        if(StudentData.diploma){
            findViewById<View>(R.id.stDiplomaLayout).isVisible=true
            findViewById<View>(R.id.stDiplomaIntituteLayout).isVisible=true
            findViewById<View>(R.id.stDiplomaPercentageLayout).isVisible=true
            findViewById<View>(R.id.stDiplomaYoaLayout).isVisible=true
            findViewById<View>(R.id.stDiplomaYopLayout).isVisible=true
        }

    }
}