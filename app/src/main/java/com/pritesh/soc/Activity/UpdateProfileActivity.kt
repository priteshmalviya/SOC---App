package com.pritesh.soc.Activity

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.core.view.isVisible
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.pritesh.soc.R
import com.pritesh.soc.modles.StudentForm
import java.io.ByteArrayOutputStream
import java.io.IOException

class UpdateProfileActivity : AppCompatActivity() {

    private var PICK_IMAGE = 123
    private lateinit var Image : ImageView
    private lateinit var Username : String
    private var ImgUrl : String = ""
    private lateinit var StorageReference : StorageReference
    private var Imagepath : Uri? = null
    private lateinit var mDbRef : DatabaseReference
    private lateinit var StudentData : StudentForm

    // diploma

    lateinit var diplomaInstitute : EditText
    lateinit var diplomarName : EditText
    lateinit var diplomarPercentage : EditText
    lateinit var diplomarAddmission : EditText
    lateinit var diplomarPassout : EditText


    lateinit var diplomaInstitutetxt : TextView
    lateinit var diplomarNametxt : TextView
    lateinit var diplomarPercentagetxt : TextView
    lateinit var diplomarAddmissiontxt : TextView
    lateinit var diplomarPassouttxt : TextView


    // semester For Course
    lateinit var sem1cgpa : EditText
    lateinit var sem1Atkt : EditText
    lateinit var sem2cgpa : EditText
    lateinit var sem2Atkt : EditText
    lateinit var sem3cgpa : EditText
    lateinit var sem3Atkt : EditText
    lateinit var sem4cgpa : EditText
    lateinit var sem4Atkt : EditText
    lateinit var sem5cgpa : EditText
    lateinit var sem5Atkt : EditText
    lateinit var sem6cgpa : EditText
    lateinit var sem6Atkt : EditText
    lateinit var sem7cgpa : EditText
    lateinit var sem7Atkt : EditText
    lateinit var sem8cgpa : EditText
    lateinit var sem8Atkt : EditText


    lateinit var sem3cgpatxt : TextView
    lateinit var sem3Atkttxt : TextView
    lateinit var sem4cgpatxt : TextView
    lateinit var sem4Atkttxt : TextView
    lateinit var sem5cgpatxt : TextView
    lateinit var sem5Atkttxt : TextView
    lateinit var sem6cgpatxt : TextView
    lateinit var sem6Atkttxt : TextView
    lateinit var sem7cgpatxt : TextView
    lateinit var sem7Atkttxt : TextView
    lateinit var sem8cgpatxt : TextView
    lateinit var sem8Atkttxt : TextView

    // Other Attributes
    lateinit var name : EditText
    lateinit var enroll : EditText
    lateinit var yoa : EditText
    lateinit var yop : EditText
    lateinit var dob : EditText
    lateinit var sccShool : EditText
    lateinit var sscBoard : EditText
    lateinit var sscPercentage : EditText
    lateinit var sscYop : EditText
    lateinit var hsccShool : EditText
    lateinit var hsscBoard : EditText
    lateinit var hsscPercentage : EditText
    lateinit var hsscYop : EditText
    lateinit var agregatecgpaUg : EditText
    lateinit var agregatePercentageUg : EditText
    lateinit var agregatecgpaPg : EditText
    lateinit var agregatePercentagePg : EditText
    lateinit var UgCourse : EditText
    lateinit var UgSpecialization : EditText
    lateinit var UgYop : EditText
    lateinit var activBacklog : EditText
    lateinit var activBacklogtxt : TextView
    lateinit var Mobile : EditText
    lateinit var altMobile : EditText
    lateinit var Email : EditText
    lateinit var GapYear : EditText
    lateinit var GapYeartxt : TextView
    lateinit var LocalAdd : EditText
    lateinit var LocalPin : EditText
    lateinit var perAdd : EditText
    lateinit var perHome : EditText
    lateinit var perState : EditText
    lateinit var perpin : EditText
    lateinit var fathername:EditText
    lateinit var fatherContect:EditText
    lateinit var fatherOccupation:EditText
    lateinit var mothername:EditText
    lateinit var motherContect:EditText
    lateinit var Internship:EditText
    lateinit var ckeck : CheckBox

    var item = ArrayList<String>()
    var componentsId = ArrayList<Int>()
    lateinit var Currentsemester : AutoCompleteTextView
    lateinit var adapteritem : ArrayAdapter<String>
    var currentsemposition = 0

    var dataexist = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_profile)
        // importante refrence
        Username=intent.getStringExtra("UserName").toString()
        ImgUrl=intent.getStringExtra("ImgUrl").toString()
        StorageReference= FirebaseStorage.getInstance().reference
        mDbRef = FirebaseDatabase.getInstance().reference

        item.add("I")
        item.add("II")
        item.add("III")
        item.add("IV")
        item.add("V")
        item.add("VI")
        item.add("VII")
        item.add("VIII")
        item.add("PassOut")


        componentsId.add(R.id.semester1Sgpatxt)
        componentsId.add(R.id.semester1Sgpa)
        componentsId.add(R.id.semester1ATKTtxt)
        componentsId.add(R.id.semester1ATKT)
        componentsId.add(R.id.semester2Sgpatxt)
        componentsId.add(R.id.semester2Sgpa)
        componentsId.add(R.id.semester2ATKTtxt)
        componentsId.add(R.id.semester2ATKT)
        componentsId.add(R.id.semester3Sgpatxt)
        componentsId.add(R.id.semester3Sgpa)
        componentsId.add(R.id.semester3ATKTtxt)
        componentsId.add(R.id.semester3ATKT)
        componentsId.add(R.id.semester4Sgpatxt)
        componentsId.add(R.id.semester4Sgpa)
        componentsId.add(R.id.semester4ATKTtxt)
        componentsId.add(R.id.semester4ATKT)
        componentsId.add(R.id.semester5Sgpatxt)
        componentsId.add(R.id.semester5Sgpa)
        componentsId.add(R.id.semester5ATKTtxt)
        componentsId.add(R.id.semester5ATKT)
        componentsId.add(R.id.semester6Sgpatxt)
        componentsId.add(R.id.semester6Sgpa)
        componentsId.add(R.id.semester6ATKTtxt)
        componentsId.add(R.id.semester6ATKT)
        componentsId.add(R.id.semester7Sgpatxt)
        componentsId.add(R.id.semester7Sgpa)
        componentsId.add(R.id.semester7ATKTtxt)
        componentsId.add(R.id.semester7ATKT)
        componentsId.add(R.id.semester8Sgpatxt)
        componentsId.add(R.id.semester8Sgpa)
        componentsId.add(R.id.semester8ATKTtxt)
        componentsId.add(R.id.semester8ATKT)


        Currentsemester = findViewById(R.id.CurrentSemester)

        adapteritem = ArrayAdapter<String>(this,R.layout.list_item,item)

        Currentsemester.setAdapter(adapteritem)

        Currentsemester.setOnItemClickListener({ parent, view, position, id ->

            //Toast.makeText(this, position.toString(), Toast.LENGTH_SHORT).show()
            currentsemposition = position

            var j=0

            for(i in 0..position-1){
                findViewById<TextView>(componentsId[j]).isVisible=true
                findViewById<EditText>(componentsId[1+j]).isVisible=true
                findViewById<TextView>(componentsId[2+j]).isVisible=true
                findViewById<EditText>(componentsId[3+j]).isVisible=true
                j+=4
            }

            for (i in position..7){
                findViewById<TextView>(componentsId[j]).isVisible=false
                findViewById<EditText>(componentsId[1+j]).isVisible=false
                findViewById<TextView>(componentsId[2+j]).isVisible=false
                findViewById<EditText>(componentsId[3+j]).isVisible=false
                j+=4
            }
        })




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


        // diploma

        diplomaInstitute=findViewById(R.id.DiplomaInstitute)
        diplomarName=findViewById(R.id.NameInDiploma)
        diplomarPercentage=findViewById(R.id.PercentageInDiploma)
        diplomarAddmission=findViewById(R.id.AddmisionYearInDiploma)
        diplomarPassout=findViewById(R.id.PassoutInDiploma)


        diplomaInstitutetxt=findViewById(R.id.DiplomaInstitutetxt)
        diplomarNametxt=findViewById(R.id.NameInDiplomatxt)
        diplomarPercentagetxt=findViewById(R.id.PercentageInDiplomatxt)
        diplomarAddmissiontxt=findViewById(R.id.AddmisionYearInDiplomatxt)
        diplomarPassouttxt=findViewById(R.id.PassoutInDiplomatxt)


        // semester for course

        sem1cgpa=findViewById(R.id.semester1Sgpa)
        sem1Atkt=findViewById(R.id.semester1ATKT)
        sem2cgpa=findViewById(R.id.semester2Sgpa)
        sem2Atkt=findViewById(R.id.semester2ATKT)
        sem3cgpa=findViewById(R.id.semester3Sgpa)
        sem3Atkt=findViewById(R.id.semester3ATKT)
        sem4cgpa=findViewById(R.id.semester4Sgpa)
        sem4Atkt=findViewById(R.id.semester4ATKT)
        sem5cgpa=findViewById(R.id.semester5Sgpa)
        sem5Atkt=findViewById(R.id.semester5ATKT)
        sem6cgpa=findViewById(R.id.semester6Sgpa)
        sem6Atkt=findViewById(R.id.semester6ATKT)
        sem7cgpa=findViewById(R.id.semester7Sgpa)
        sem7Atkt=findViewById(R.id.semester7ATKT)
        sem8cgpa=findViewById(R.id.semester8Sgpa)
        sem8Atkt=findViewById(R.id.semester8ATKT)


        sem3cgpatxt=findViewById(R.id.semester3Sgpatxt)
        sem3Atkttxt=findViewById(R.id.semester3ATKTtxt)
        sem4cgpatxt=findViewById(R.id.semester4Sgpatxt)
        sem4Atkttxt=findViewById(R.id.semester4ATKTtxt)
        sem5cgpatxt=findViewById(R.id.semester5Sgpatxt)
        sem5Atkttxt=findViewById(R.id.semester5ATKTtxt)
        sem6cgpatxt=findViewById(R.id.semester6Sgpatxt)
        sem6Atkttxt=findViewById(R.id.semester6ATKTtxt)
        sem7cgpatxt=findViewById(R.id.semester7Sgpatxt)
        sem7Atkttxt=findViewById(R.id.semester7ATKTtxt)
        sem8cgpatxt=findViewById(R.id.semester8Sgpatxt)
        sem8Atkttxt=findViewById(R.id.semester8ATKTtxt)


        // Other
        name = findViewById(R.id.Name)
        enroll = findViewById(R.id.EnrollMent)
        yop = findViewById(R.id.YearOfPassing)
        yoa = findViewById(R.id.YearOfAddmision)
        dob = findViewById(R.id.DateOfBirth)
        sccShool = findViewById(R.id.sscSchoolName)
        sscBoard = findViewById(R.id.sscBoardName)
        sscPercentage = findViewById(R.id.sscPercentage)
        sscYop = findViewById(R.id.sscYearOfPassing)
        hsccShool = findViewById(R.id.hsscSchoolName)
        hsscBoard = findViewById(R.id.hsscBoardName)
        hsscPercentage = findViewById(R.id.hsscPercentage)
        hsscYop = findViewById(R.id.hsscYearOfPassing)
        agregatecgpaUg = findViewById(R.id.CgpaInUG)
        agregatePercentageUg = findViewById(R.id.PercentageInUG)
        agregatecgpaPg = findViewById(R.id.CgpaInPG)
        agregatePercentagePg = findViewById(R.id.PercentageInPG)
        UgCourse = findViewById(R.id.UGCourse)
        UgSpecialization = findViewById(R.id.UGCourseSpecialization)
        UgYop =findViewById(R.id.UGPassingYear)
        activBacklog =findViewById(R.id.NumberOfActiveBacklog)
        activBacklogtxt =findViewById(R.id.NumberOfActiveBacklogtxt)
        Mobile =findViewById(R.id.MobileNumber)
        altMobile =findViewById(R.id.AltenateMobileNumber)
        Email =findViewById(R.id.EmailID)
        GapYear =findViewById(R.id.NumbersGap)
        GapYeartxt =findViewById(R.id.NumbersGaptxt)
        LocalAdd =findViewById(R.id.LocalAddress)
        LocalPin =findViewById(R.id.LocalAddressPincode)
        perAdd =findViewById(R.id.permanentAddress)
        perHome =findViewById(R.id.permanentAddressHomeTown)
        perState =findViewById(R.id.permanentAddressState)
        perpin =findViewById(R.id.permanentAddressPincode)
        fathername=findViewById(R.id.FatherName)
        fatherContect=findViewById(R.id.FatherContect)
        fatherOccupation=findViewById(R.id.FatherOccupation)
        mothername=findViewById(R.id.MotherName)
        motherContect=findViewById(R.id.MotherContect)
        Internship=findViewById(R.id.InternShip)
        ckeck = findViewById(R.id.checkBox)


        ckeck.setOnClickListener{
            findViewById<Button>(R.id.SubmitBtn).isEnabled=ckeck.isChecked
        }


        //  RadioButtons for true
        val Mca=findViewById<RadioButton>(R.id.RadioGroupForCourseMca)
        val diplomay=findViewById<RadioButton>(R.id.RadioGroupForDiplomaYes)
        val Gapy=findViewById<RadioButton>(R.id.RadioGroupForGapYes)
        val Backlogy=findViewById<RadioButton>(R.id.RadioGroupForBacklogYes)

        //   Radio Groups
        val Gap=findViewById<RadioGroup>(R.id.RadioGroupForGap)
        val Course=findViewById<RadioGroup>(R.id.RadioGroupForCourse)
        val Diploma=findViewById<RadioGroup>(R.id.RadioGroupForDiploma)
        val Backlog=findViewById<RadioGroup>(R.id.RadioGroupForBacklog)
        val category=findViewById<RadioGroup>(R.id.RadioGroupForCategory)
        val placement = findViewById<RadioGroup>(R.id.RadioGroupForCampus)
        val gender = findViewById<RadioGroup>(R.id.RadioGroupForGender)
        val Handicap = findViewById<RadioGroup>(R.id.RadioGroupForHandicapped)
        val backloghistory = findViewById<RadioGroup>(R.id.RadioGroupForBacklogHistory)

        var Gapf = false
        var Coursef = false
        var Dipplomaf = false
        var Backlogf = false
        var categoryf = false
        var placementf = false
        var genderf = false
        var Handicapf = false
        var backloghf = false


        //ImageView
        Image=findViewById(R.id.Image)

        Image.setOnClickListener {
            UploadImage()
        }

        mDbRef.child("StudentData").child(Username.substring(0,Username.indexOf('.')).lowercase()).get().addOnSuccessListener {
            if (it.value!=null){
                StudentData=it.getValue(StudentForm::class.java)!!
                dataexist = true
                UpdateForm()
            }
        }.addOnFailureListener{
            //Toast.makeText(this, "Failed To Add Product...", Toast.LENGTH_SHORT).show()
        }

        placement.setOnCheckedChangeListener { group, checkedId ->
            placementf=true
        }

        gender.setOnCheckedChangeListener { group, checkedId ->
            genderf=true
        }

        Handicap.setOnCheckedChangeListener { group, checkedId ->
            Handicapf=true
        }

        backloghistory.setOnCheckedChangeListener { group, checkedId ->
            backloghf=true
        }

        category.setOnCheckedChangeListener { group, checkedId ->
            categoryf=true
        }

        Gap.setOnCheckedChangeListener { group, checkedId ->
            Gapf=true
            findViewById<EditText>(R.id.NumbersGap).isVisible=Gapy.isChecked
            findViewById<TextView>(R.id.NumbersGaptxt).isVisible=Gapy.isChecked
        }

        Backlog.setOnCheckedChangeListener { group, checkedId ->
            Backlogf=true
            findViewById<EditText>(R.id.NumberOfActiveBacklog).isVisible=Backlogy.isChecked
            findViewById<TextView>(R.id.NumberOfActiveBacklogtxt).isVisible=Backlogy.isChecked
        }

        Course.setOnCheckedChangeListener { group, checkedId ->
            Coursef=true
            UpdateCourseUi(Mca.isChecked)

            if(Mca.isChecked){
                item.clear()
                item.add("I")
                item.add("II")
                item.add("III")
                item.add("IV")
                item.add("PassOut")
            }else{
                item.clear()
                item.add("I")
                item.add("II")
                item.add("III")
                item.add("IV")
                item.add("V")
                item.add("VI")
                item.add("VII")
                item.add("VIII")
                item.add("PassOut")
            }
        }

        Diploma.setOnCheckedChangeListener { group, checkedId ->
            Dipplomaf=true
            UpdateDiplomaUi(diplomay.isChecked)
        }

        findViewById<Button>(R.id.SubmitBtn).setOnClickListener {
            if (categoryf && Coursef && Gapf && placementf && genderf && Backlogf && Dipplomaf && backloghf && Handicapf) {
                if (Imagepath != null) {
                    GetData()
                } else {
                    Toast.makeText(this, "Plese Select Image. * ", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Please Fill All Required Fields * ", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun UpdateForm() {

        when(StudentData.category){
            "General"-> findViewById<RadioButton>(R.id.RadioGroupForCategoryGeneral).isChecked=true
            "EWS"-> findViewById<RadioButton>(R.id.RadioGroupForCategoryEWS).isChecked=true
            "OBC"-> findViewById<RadioButton>(R.id.RadioGroupForCategoryOBC).isChecked=true
            "ST"-> findViewById<RadioButton>(R.id.RadioGroupForCategoryST).isChecked=true
            "SC"-> findViewById<RadioButton>(R.id.RadioGroupForCategorySC).isChecked=true
            "Other"-> findViewById<RadioButton>(R.id.RadioGroupForCategoryOther).isChecked=true
        }


        if (StudentData.currentsem!=""){
            currentsemposition = StudentData.currentsem.toInt()
            Currentsemester.setText(item[currentsemposition])
            when (currentsemposition) {
                0 -> {
                    sem1cgpa.isVisible = false
                    sem1Atkt.isVisible = false
                    findViewById<TextView>(R.id.semester1ATKTtxt).isVisible = false
                    findViewById<TextView>(R.id.semester1Sgpatxt).isVisible = false
                    sem2cgpa.isVisible = false
                    sem2Atkt.isVisible = false
                    findViewById<TextView>(R.id.semester2ATKTtxt).isVisible = false
                    findViewById<TextView>(R.id.semester2Sgpatxt).isVisible = false
                    sem3cgpa.isVisible = false
                    sem3Atkt.isVisible = false
                    findViewById<TextView>(R.id.semester3ATKTtxt).isVisible = false
                    findViewById<TextView>(R.id.semester3Sgpatxt).isVisible = false
                    sem4cgpa.isVisible = false
                    sem4Atkt.isVisible = false
                    findViewById<TextView>(R.id.semester4ATKTtxt).isVisible = false
                    findViewById<TextView>(R.id.semester4Sgpatxt).isVisible = false
                    sem5cgpa.isVisible = false
                    sem5Atkt.isVisible = false
                    findViewById<TextView>(R.id.semester5ATKTtxt).isVisible = false
                    findViewById<TextView>(R.id.semester5Sgpatxt).isVisible = false
                    sem6cgpa.isVisible = false
                    sem6Atkt.isVisible = false
                    findViewById<TextView>(R.id.semester6ATKTtxt).isVisible = false
                    findViewById<TextView>(R.id.semester6Sgpatxt).isVisible = false
                    sem7cgpa.isVisible = false
                    sem7Atkt.isVisible = false
                    findViewById<TextView>(R.id.semester7ATKTtxt).isVisible = false
                    findViewById<TextView>(R.id.semester7Sgpatxt).isVisible = false
                    sem8cgpa.isVisible = false
                    sem8Atkt.isVisible = false
                    findViewById<TextView>(R.id.semester8ATKTtxt).isVisible = false
                    findViewById<TextView>(R.id.semester8Sgpatxt).isVisible = false
                }
                1 -> {
                    sem1cgpa.isVisible = true
                    sem1Atkt.isVisible = true
                    findViewById<TextView>(R.id.semester1ATKTtxt).isVisible = true
                    findViewById<TextView>(R.id.semester1Sgpatxt).isVisible = true
                    sem2cgpa.isVisible = false
                    sem2Atkt.isVisible = false
                    findViewById<TextView>(R.id.semester2ATKTtxt).isVisible = false
                    findViewById<TextView>(R.id.semester2Sgpatxt).isVisible = false
                    sem3cgpa.isVisible = false
                    sem3Atkt.isVisible = false
                    findViewById<TextView>(R.id.semester3ATKTtxt).isVisible = false
                    findViewById<TextView>(R.id.semester3Sgpatxt).isVisible = false
                    sem4cgpa.isVisible = false
                    sem4Atkt.isVisible = false
                    findViewById<TextView>(R.id.semester4ATKTtxt).isVisible = false
                    findViewById<TextView>(R.id.semester4Sgpatxt).isVisible = false
                    sem5cgpa.isVisible = false
                    sem5Atkt.isVisible = false
                    findViewById<TextView>(R.id.semester5ATKTtxt).isVisible = false
                    findViewById<TextView>(R.id.semester5Sgpatxt).isVisible = false
                    sem6cgpa.isVisible = false
                    sem6Atkt.isVisible = false
                    findViewById<TextView>(R.id.semester6ATKTtxt).isVisible = false
                    findViewById<TextView>(R.id.semester6Sgpatxt).isVisible = false
                    sem7cgpa.isVisible = false
                    sem7Atkt.isVisible = false
                    findViewById<TextView>(R.id.semester7ATKTtxt).isVisible = false
                    findViewById<TextView>(R.id.semester7Sgpatxt).isVisible = false
                    sem8cgpa.isVisible = false
                    sem8Atkt.isVisible = false
                    findViewById<TextView>(R.id.semester8ATKTtxt).isVisible = false
                    findViewById<TextView>(R.id.semester8Sgpatxt).isVisible = false
                }
                2 -> {
                    sem1cgpa.isVisible = true
                    sem1Atkt.isVisible = true
                    findViewById<TextView>(R.id.semester1ATKTtxt).isVisible = true
                    findViewById<TextView>(R.id.semester1Sgpatxt).isVisible = true
                    sem2cgpa.isVisible = true
                    sem2Atkt.isVisible = true
                    findViewById<TextView>(R.id.semester2ATKTtxt).isVisible = true
                    findViewById<TextView>(R.id.semester2Sgpatxt).isVisible = true
                    sem3cgpa.isVisible = false
                    sem3Atkt.isVisible = false
                    findViewById<TextView>(R.id.semester3ATKTtxt).isVisible = false
                    findViewById<TextView>(R.id.semester3Sgpatxt).isVisible = false
                    sem4cgpa.isVisible = false
                    sem4Atkt.isVisible = false
                    findViewById<TextView>(R.id.semester4ATKTtxt).isVisible = false
                    findViewById<TextView>(R.id.semester4Sgpatxt).isVisible = false
                    sem5cgpa.isVisible = false
                    sem5Atkt.isVisible = false
                    findViewById<TextView>(R.id.semester5ATKTtxt).isVisible = false
                    findViewById<TextView>(R.id.semester5Sgpatxt).isVisible = false
                    sem6cgpa.isVisible = false
                    sem6Atkt.isVisible = false
                    findViewById<TextView>(R.id.semester6ATKTtxt).isVisible = false
                    findViewById<TextView>(R.id.semester6Sgpatxt).isVisible = false
                    sem7cgpa.isVisible = false
                    sem7Atkt.isVisible = false
                    findViewById<TextView>(R.id.semester7ATKTtxt).isVisible = false
                    findViewById<TextView>(R.id.semester7Sgpatxt).isVisible = false
                    sem8cgpa.isVisible = false
                    sem8Atkt.isVisible = false
                    findViewById<TextView>(R.id.semester8ATKTtxt).isVisible = false
                    findViewById<TextView>(R.id.semester8Sgpatxt).isVisible = false
                }
                3 -> {
                    sem1cgpa.isVisible = true
                    sem1Atkt.isVisible = true
                    findViewById<TextView>(R.id.semester1ATKTtxt).isVisible = true
                    findViewById<TextView>(R.id.semester1Sgpatxt).isVisible = true
                    sem2cgpa.isVisible = true
                    sem2Atkt.isVisible = true
                    findViewById<TextView>(R.id.semester2ATKTtxt).isVisible = true
                    findViewById<TextView>(R.id.semester2Sgpatxt).isVisible = true
                    sem3cgpa.isVisible = true
                    sem3Atkt.isVisible = true
                    findViewById<TextView>(R.id.semester3ATKTtxt).isVisible = true
                    findViewById<TextView>(R.id.semester3Sgpatxt).isVisible = true
                    sem4cgpa.isVisible = false
                    sem4Atkt.isVisible = false
                    findViewById<TextView>(R.id.semester4ATKTtxt).isVisible = false
                    findViewById<TextView>(R.id.semester4Sgpatxt).isVisible = false
                    sem5cgpa.isVisible = false
                    sem5Atkt.isVisible = false
                    findViewById<TextView>(R.id.semester5ATKTtxt).isVisible = false
                    findViewById<TextView>(R.id.semester5Sgpatxt).isVisible = false
                    sem6cgpa.isVisible = false
                    sem6Atkt.isVisible = false
                    findViewById<TextView>(R.id.semester6ATKTtxt).isVisible = false
                    findViewById<TextView>(R.id.semester6Sgpatxt).isVisible = false
                    sem7cgpa.isVisible = false
                    sem7Atkt.isVisible = false
                    findViewById<TextView>(R.id.semester7ATKTtxt).isVisible = false
                    findViewById<TextView>(R.id.semester7Sgpatxt).isVisible = false
                    sem8cgpa.isVisible = false
                    sem8Atkt.isVisible = false
                    findViewById<TextView>(R.id.semester8ATKTtxt).isVisible = false
                    findViewById<TextView>(R.id.semester8Sgpatxt).isVisible = false
                }
                4 -> {
                    sem1cgpa.isVisible = true
                    sem1Atkt.isVisible = true
                    findViewById<TextView>(R.id.semester1ATKTtxt).isVisible = true
                    findViewById<TextView>(R.id.semester1Sgpatxt).isVisible = true
                    sem2cgpa.isVisible = true
                    sem2Atkt.isVisible = true
                    findViewById<TextView>(R.id.semester2ATKTtxt).isVisible = true
                    findViewById<TextView>(R.id.semester2Sgpatxt).isVisible = true
                    sem3cgpa.isVisible = true
                    sem3Atkt.isVisible = true
                    findViewById<TextView>(R.id.semester3ATKTtxt).isVisible = true
                    findViewById<TextView>(R.id.semester3Sgpatxt).isVisible = true
                    sem4cgpa.isVisible = true
                    sem4Atkt.isVisible = true
                    findViewById<TextView>(R.id.semester4ATKTtxt).isVisible = true
                    findViewById<TextView>(R.id.semester4Sgpatxt).isVisible = true
                    sem5cgpa.isVisible = false
                    sem5Atkt.isVisible = false
                    findViewById<TextView>(R.id.semester5ATKTtxt).isVisible = false
                    findViewById<TextView>(R.id.semester5Sgpatxt).isVisible = false
                    sem6cgpa.isVisible = false
                    sem6Atkt.isVisible = false
                    findViewById<TextView>(R.id.semester6ATKTtxt).isVisible = false
                    findViewById<TextView>(R.id.semester6Sgpatxt).isVisible = false
                    sem7cgpa.isVisible = false
                    sem7Atkt.isVisible = false
                    findViewById<TextView>(R.id.semester7ATKTtxt).isVisible = false
                    findViewById<TextView>(R.id.semester7Sgpatxt).isVisible = false
                    sem8cgpa.isVisible = false
                    sem8Atkt.isVisible = false
                    findViewById<TextView>(R.id.semester8ATKTtxt).isVisible = false
                    findViewById<TextView>(R.id.semester8Sgpatxt).isVisible = false
                }
                5 -> {
                    sem1cgpa.isVisible = true
                    sem1Atkt.isVisible = true
                    findViewById<TextView>(R.id.semester1ATKTtxt).isVisible = true
                    findViewById<TextView>(R.id.semester1Sgpatxt).isVisible = true
                    sem2cgpa.isVisible = true
                    sem2Atkt.isVisible = true
                    findViewById<TextView>(R.id.semester2ATKTtxt).isVisible = true
                    findViewById<TextView>(R.id.semester2Sgpatxt).isVisible = true
                    sem3cgpa.isVisible = true
                    sem3Atkt.isVisible = true
                    findViewById<TextView>(R.id.semester3ATKTtxt).isVisible = true
                    findViewById<TextView>(R.id.semester3Sgpatxt).isVisible = true
                    sem4cgpa.isVisible = true
                    sem4Atkt.isVisible = true
                    findViewById<TextView>(R.id.semester4ATKTtxt).isVisible = true
                    findViewById<TextView>(R.id.semester4Sgpatxt).isVisible = true
                    sem5cgpa.isVisible = true
                    sem5Atkt.isVisible = true
                    findViewById<TextView>(R.id.semester5ATKTtxt).isVisible = true
                    findViewById<TextView>(R.id.semester5Sgpatxt).isVisible = true
                    sem6cgpa.isVisible = false
                    sem6Atkt.isVisible = false
                    findViewById<TextView>(R.id.semester6ATKTtxt).isVisible = false
                    findViewById<TextView>(R.id.semester6Sgpatxt).isVisible = false
                    sem7cgpa.isVisible = false
                    sem7Atkt.isVisible = false
                    findViewById<TextView>(R.id.semester7ATKTtxt).isVisible = false
                    findViewById<TextView>(R.id.semester7Sgpatxt).isVisible = false
                    sem8cgpa.isVisible = false
                    sem8Atkt.isVisible = false
                    findViewById<TextView>(R.id.semester8ATKTtxt).isVisible = false
                    findViewById<TextView>(R.id.semester8Sgpatxt).isVisible = false
                }
                6 -> {
                    sem1cgpa.isVisible = true
                    sem1Atkt.isVisible = true
                    findViewById<TextView>(R.id.semester1ATKTtxt).isVisible = true
                    findViewById<TextView>(R.id.semester1Sgpatxt).isVisible = true
                    sem2cgpa.isVisible = true
                    sem2Atkt.isVisible = true
                    findViewById<TextView>(R.id.semester2ATKTtxt).isVisible = true
                    findViewById<TextView>(R.id.semester2Sgpatxt).isVisible = true
                    sem3cgpa.isVisible = true
                    sem3Atkt.isVisible = true
                    findViewById<TextView>(R.id.semester3ATKTtxt).isVisible = true
                    findViewById<TextView>(R.id.semester3Sgpatxt).isVisible = true
                    sem4cgpa.isVisible = true
                    sem4Atkt.isVisible = true
                    findViewById<TextView>(R.id.semester4ATKTtxt).isVisible = true
                    findViewById<TextView>(R.id.semester4Sgpatxt).isVisible = true
                    sem5cgpa.isVisible = true
                    sem5Atkt.isVisible = true
                    findViewById<TextView>(R.id.semester5ATKTtxt).isVisible = true
                    findViewById<TextView>(R.id.semester5Sgpatxt).isVisible = true
                    sem6cgpa.isVisible = true
                    sem6Atkt.isVisible = true
                    findViewById<TextView>(R.id.semester6ATKTtxt).isVisible = true
                    findViewById<TextView>(R.id.semester6Sgpatxt).isVisible = true
                    sem7cgpa.isVisible = false
                    sem7Atkt.isVisible = false
                    findViewById<TextView>(R.id.semester7ATKTtxt).isVisible = false
                    findViewById<TextView>(R.id.semester7Sgpatxt).isVisible = false
                    sem8cgpa.isVisible = false
                    sem8Atkt.isVisible = false
                    findViewById<TextView>(R.id.semester8ATKTtxt).isVisible = false
                    findViewById<TextView>(R.id.semester8Sgpatxt).isVisible = false
                }
                7 -> {
                    sem1cgpa.isVisible = true
                    sem1Atkt.isVisible = true
                    findViewById<TextView>(R.id.semester1ATKTtxt).isVisible = true
                    findViewById<TextView>(R.id.semester1Sgpatxt).isVisible = true
                    sem2cgpa.isVisible = true
                    sem2Atkt.isVisible = true
                    findViewById<TextView>(R.id.semester2ATKTtxt).isVisible = true
                    findViewById<TextView>(R.id.semester2Sgpatxt).isVisible = true
                    sem3cgpa.isVisible = true
                    sem3Atkt.isVisible = true
                    findViewById<TextView>(R.id.semester3ATKTtxt).isVisible = true
                    findViewById<TextView>(R.id.semester3Sgpatxt).isVisible = true
                    sem4cgpa.isVisible = true
                    sem4Atkt.isVisible = true
                    findViewById<TextView>(R.id.semester4ATKTtxt).isVisible = true
                    findViewById<TextView>(R.id.semester4Sgpatxt).isVisible = true
                    sem5cgpa.isVisible = true
                    sem5Atkt.isVisible = true
                    findViewById<TextView>(R.id.semester5ATKTtxt).isVisible = true
                    findViewById<TextView>(R.id.semester5Sgpatxt).isVisible = true
                    sem6cgpa.isVisible = true
                    sem6Atkt.isVisible = true
                    findViewById<TextView>(R.id.semester6ATKTtxt).isVisible = true
                    findViewById<TextView>(R.id.semester6Sgpatxt).isVisible = true
                    sem7cgpa.isVisible = true
                    sem7Atkt.isVisible = true
                    findViewById<TextView>(R.id.semester7ATKTtxt).isVisible = true
                    findViewById<TextView>(R.id.semester7Sgpatxt).isVisible = true
                    sem8cgpa.isVisible = false
                    sem8Atkt.isVisible = false
                    findViewById<TextView>(R.id.semester8ATKTtxt).isVisible = false
                    findViewById<TextView>(R.id.semester8Sgpatxt).isVisible = false
                }
                8 -> {
                    sem1cgpa.isVisible = true
                    sem1Atkt.isVisible = true
                    findViewById<TextView>(R.id.semester1ATKTtxt).isVisible = true
                    findViewById<TextView>(R.id.semester1Sgpatxt).isVisible = true
                    sem2cgpa.isVisible = true
                    sem2Atkt.isVisible = true
                    findViewById<TextView>(R.id.semester2ATKTtxt).isVisible = true
                    findViewById<TextView>(R.id.semester2Sgpatxt).isVisible = true
                    sem3cgpa.isVisible = true
                    sem3Atkt.isVisible = true
                    findViewById<TextView>(R.id.semester3ATKTtxt).isVisible = true
                    findViewById<TextView>(R.id.semester3Sgpatxt).isVisible = true
                    sem4cgpa.isVisible = true
                    sem4Atkt.isVisible = true
                    findViewById<TextView>(R.id.semester4ATKTtxt).isVisible = true
                    findViewById<TextView>(R.id.semester4Sgpatxt).isVisible = true
                    sem5cgpa.isVisible = true
                    sem5Atkt.isVisible = true
                    findViewById<TextView>(R.id.semester5ATKTtxt).isVisible = true
                    findViewById<TextView>(R.id.semester5Sgpatxt).isVisible = true
                    sem6cgpa.isVisible = true
                    sem6Atkt.isVisible = true
                    findViewById<TextView>(R.id.semester6ATKTtxt).isVisible = true
                    findViewById<TextView>(R.id.semester6Sgpatxt).isVisible = true
                    sem7cgpa.isVisible = true
                    sem7Atkt.isVisible = true
                    findViewById<TextView>(R.id.semester7ATKTtxt).isVisible = true
                    findViewById<TextView>(R.id.semester7Sgpatxt).isVisible = true
                    sem8cgpa.isVisible = true
                    sem8Atkt.isVisible = true
                    findViewById<TextView>(R.id.semester8ATKTtxt).isVisible = true
                    findViewById<TextView>(R.id.semester8Sgpatxt).isVisible = true
                }
            }
        }

        if (StudentData.placment){
            findViewById<RadioButton>(R.id.radioButton1).isChecked=true
        }else{
            findViewById<RadioButton>(R.id.radioButton2).isChecked=true
        }

        if (StudentData.course=="MCA"){
            findViewById<RadioButton>(R.id.RadioGroupForCourseMca).isChecked=true
        }else{
            findViewById<RadioButton>(R.id.RadioGroupForCourseImca).isChecked=true
        }

        if (StudentData.gender=="Male"){
            findViewById<RadioButton>(R.id.RadioGroupForGenderMale).isChecked=true
        }else{
            findViewById<RadioButton>(R.id.RadioGroupForGenderFemale).isChecked=true
        }

        if (StudentData.speciallyAbled){
            findViewById<RadioButton>(R.id.RadioGroupForHandicappedYes).isChecked=true
        }else{
            findViewById<RadioButton>(R.id.RadioGroupForHandicappedNo).isChecked=true
        }

        if (StudentData.activeBacklog){
            findViewById<RadioButton>(R.id.RadioGroupForBacklogYes).isChecked=true
        }else{
            findViewById<RadioButton>(R.id.RadioGroupForBacklogNo).isChecked=true
        }

        if (StudentData.backlogHistory){
            findViewById<RadioButton>(R.id.RadioGroupForBacklogHistoryYes).isChecked=true
        }else{
            findViewById<RadioButton>(R.id.RadioGroupForBacklogHistoryNo).isChecked=true
        }


        if (StudentData.diploma){
            findViewById<RadioButton>(R.id.RadioGroupForDiplomaYes).isChecked=true
        }else{
            findViewById<RadioButton>(R.id.RadioGroupForDiplomaNo).isChecked=true
        }

        if (StudentData.gap){
            findViewById<RadioButton>(R.id.RadioGroupForGapYes).isChecked=true
        }else{
            findViewById<RadioButton>(R.id.RadioGroupForGapNo).isChecked=true
        }

        name.setText(StudentData.name)
        enroll.setText(StudentData.enrollNo)
        yoa.setText(StudentData.yoa)
        yop.setText(StudentData.yop)
        dob.setText(StudentData.dob)
        sccShool.setText(StudentData.schoolNameSSc)
        sscBoard.setText(StudentData.schoolBoarSSc)
        sscPercentage.setText(StudentData.schoolpercentageSSc)
        sscYop.setText(StudentData.schoolYopSSc)
        hsccShool.setText(StudentData.schoolNameHSSc)
        hsscBoard.setText(StudentData.schoolBoarHSSc)
        hsscPercentage.setText(StudentData.schoolpercentageHSSc)
        hsscYop.setText(StudentData.schoolYopHSSc)
        agregatecgpaUg.setText(StudentData.aggregateUgCgpa)
        agregatePercentageUg.setText(StudentData.aggregateUgPercentage)
        agregatecgpaPg.setText(StudentData.aggregatePgCgpa)
        agregatePercentagePg.setText(StudentData.aggregatePgPercentage)
        UgCourse.setText(StudentData.ugCourse)
        UgSpecialization.setText(StudentData.ugSpecialization)
        UgYop.setText(StudentData.ugyop)
        Mobile.setText(StudentData.mobile)
        altMobile.setText(StudentData.alternatemobile)
        Email.setText(StudentData.email)
        sem1cgpa.setText(StudentData.semester1sgpa)
        sem1Atkt.setText(StudentData.semester1Atkt)
        sem2cgpa.setText(StudentData.semester2sgpa)
        sem2Atkt.setText(StudentData.semester2Atkt)
        sem3cgpa.setText(StudentData.semester3sgpa)
        sem3Atkt.setText(StudentData.semester3Atkt)
        sem4cgpa.setText(StudentData.semester4sgpa)
        sem4Atkt.setText(StudentData.semester4Atkt)
        sem5cgpa.setText(StudentData.semester5sgpa)
        sem5Atkt.setText(StudentData.semester5Atkt)
        sem6cgpa.setText(StudentData.semester6sgpa)
        sem6Atkt.setText(StudentData.semester6Atkt)
        sem7cgpa.setText(StudentData.semester7sgpa)
        sem7Atkt.setText(StudentData.semester7Atkt)
        sem8cgpa.setText(StudentData.semester8sgpa)
        sem8Atkt.setText(StudentData.semester8Atkt)
        LocalAdd.setText(StudentData.localAdd)
        LocalPin.setText(StudentData.pincodeLocal)
        perAdd.setText(StudentData.permanentAdd)
        perHome.setText(StudentData.permanentHome)
        perState.setText(StudentData.permanentState)
        perpin.setText(StudentData.permanentPincode)
        fathername.setText(StudentData.fatherName)
        fatherOccupation.setText(StudentData.fatherOccupation)
        fatherContect.setText(StudentData.fatherContect)
        mothername.setText(StudentData.motherName)
        motherContect.setText(StudentData.motherContect)
        Internship.setText(StudentData.internship)
        diplomaInstitute.setText(StudentData.diplomaInstitute)
        diplomarName.setText(StudentData.diplomaCourse)
        diplomarPercentage.setText(StudentData.diplomaPercentage)
        diplomarAddmission.setText(StudentData.diplomayoa)
        diplomarPassout.setText(StudentData.diplomayop)
        activBacklog.setText(StudentData.numberOfBacklog)
        GapYear.setText(StudentData.gapyear)
        Glide.with(this).load(ImgUrl).circleCrop().into(Image)
        Imagepath= Uri.parse("hello")
    }

    private fun GetData() {
        var course=""
        if (findViewById<RadioButton>(R.id.RadioGroupForCourseMca).isChecked){
            course="MCA"
        }else{
            course="Integrated MCA"
        }
        var gender=""
        if(findViewById<RadioButton>(R.id.RadioGroupForGenderMale).isChecked){
            gender="Male"
        }else{
            gender="Female"
        }
        val i : Int=findViewById<RadioGroup>(R.id.RadioGroupForCategory).checkedRadioButtonId
        StudentData = StudentForm(findViewById<RadioButton>(R.id.radioButton1).isChecked,name.text.toString(),enroll.text.toString(),course,yoa.text.toString(),yop.text.toString(),
            dob.text.toString(),gender,findViewById<RadioButton>(i).text.toString(),findViewById<RadioButton>(R.id.RadioGroupForHandicappedYes).isChecked,sccShool.text.toString(),sscBoard.text.toString(),
            sscPercentage.text.toString(),sscYop.text.toString(),hsccShool.text.toString(),hsscBoard.text.toString(),hsscPercentage.text.toString(),
            hsscYop.text.toString(),agregatecgpaUg.text.toString(),agregatePercentageUg.text.toString(),agregatecgpaPg.text.toString(),
            agregatePercentagePg.text.toString(),UgCourse.text.toString(),UgSpecialization.text.toString(),UgYop.text.toString(),findViewById<RadioButton>(R.id.RadioGroupForBacklogYes).isChecked,
            activBacklog.text.toString(),findViewById<RadioButton>(R.id.RadioGroupForBacklogHistoryYes).isChecked,findViewById<RadioButton>(R.id.RadioGroupForDiplomaYes).isChecked,
            diplomarName.text.toString(),diplomarAddmission.text.toString(),diplomarPassout.text.toString(),diplomarPercentage.text.toString(),diplomaInstitute.text.toString(),
            Mobile.text.toString(),altMobile.text.toString(), Email.text.toString(),findViewById<RadioButton>(R.id.RadioGroupForGapYes).isChecked,GapYear.text.toString(),
            sem1cgpa.text.toString(),sem1Atkt.text.toString(),sem2cgpa.text.toString(),sem2Atkt.text.toString(),sem3cgpa.text.toString(),sem3Atkt.text.toString(),sem4cgpa.text.toString(),
            sem4Atkt.text.toString(),sem5cgpa.text.toString(),sem5Atkt.text.toString(),sem6cgpa.text.toString(),sem6Atkt.text.toString(),sem7cgpa.text.toString(),sem7Atkt.text.toString(),
            sem8cgpa.text.toString(),sem8Atkt.text.toString(),LocalAdd.text.toString(),LocalPin.text.toString(),perAdd.text.toString(),perHome.text.toString(),
            perState.text.toString(),perpin.text.toString(),fathername.text.toString(),fatherContect.text.toString(),fatherOccupation.text.toString(),mothername.text.toString(),
            motherContect.text.toString(),Internship.text.toString(),ImgUrl,Username,currentsemposition.toString())

        checkData()
    }

    private fun checkData() {
        if(StudentData.name=="" || StudentData.enrollNo=="" || StudentData.yoa=="" || StudentData.yop=="" || StudentData.dob=="" || StudentData.schoolNameSSc=="" || StudentData.schoolBoarSSc=="" || StudentData.schoolpercentageSSc=="" || StudentData.schoolYopSSc==""
            || StudentData.schoolNameHSSc=="" || StudentData.schoolBoarHSSc=="" || StudentData.schoolpercentageHSSc=="" || StudentData.schoolYopHSSc=="" || StudentData.mobile=="" || StudentData.alternatemobile=="" || StudentData.email==""
            || StudentData.localAdd=="" || StudentData.pincodeLocal=="" || StudentData.permanentAdd==""  || StudentData.permanentHome==""  || StudentData.permanentState==""  || StudentData.permanentPincode==""  || StudentData.fatherName==""  || StudentData.fatherContect==""
            || StudentData.fatherOccupation==""   || StudentData.motherName==""  || StudentData.motherContect==""){
            Toast.makeText(this, "Please Fill All Required Fields * ", Toast.LENGTH_SHORT).show()
            return
        }

        //StudentData.aggregatePgCgpa=="" || StudentData.aggregatePgCgpa=="" ||

        if(StudentData.course=="MCA"){
            if(StudentData.aggregateUgCgpa=="" || StudentData.aggregateUgPercentage=="" ||  StudentData.ugCourse=="" || StudentData.ugyop=="" || StudentData.ugSpecialization==""){
                Toast.makeText(this, "Please Fill All Ug Details ", Toast.LENGTH_SHORT).show()
            }
        }

        if(StudentData.activeBacklog){
            if (StudentData.numberOfBacklog==""){
                Toast.makeText(this, "Please Fill All Required Fields * ", Toast.LENGTH_SHORT).show()
                return
            }
        }

        if(StudentData.diploma){
            if(StudentData.diplomayoa=="" || StudentData.diplomayop=="" || StudentData.diplomaCourse=="" || StudentData.diplomaInstitute=="" || StudentData.diplomaPercentage==""){
                Toast.makeText(this, "Please Fill All Required Fields * ", Toast.LENGTH_SHORT).show()
                return
            }
        }
        if (StudentData.gap){
            if (StudentData.gapyear==""){
                Toast.makeText(this, "Please Fill All Required Fields * ", Toast.LENGTH_SHORT).show()
                return
            }
        }
        if (StudentData.course=="Imca"){
            if(StudentData.semester2sgpa=="" || StudentData.semester2Atkt=="" || StudentData.semester3sgpa=="" || StudentData.semester3Atkt=="" || StudentData.semester4sgpa=="" || StudentData.semester4Atkt==""
                || StudentData.semester5sgpa=="" || StudentData.semester5Atkt=="" || StudentData.semester6sgpa=="" || StudentData.semester6Atkt=="" || StudentData.semester7sgpa=="" || StudentData.semester7Atkt==""){
                Toast.makeText(this, "Please Fill All Required Fields * ", Toast.LENGTH_SHORT).show()
                return
            }
        }

        if(StudentData.currentsem.toInt()>7){
            StudentData.aggregateUgCgpa = ((StudentData.semester1sgpa.toFloat()+StudentData.semester2sgpa.toFloat()+StudentData.semester3sgpa.toFloat()+StudentData.semester4sgpa.toFloat()+StudentData.semester5sgpa.toFloat()+StudentData.semester6sgpa.toFloat())/6).toString()
            StudentData.aggregateUgPercentage = (StudentData.aggregateUgCgpa.toFloat()*10).toString()
            StudentData.ugCourse = "IMCA"
            StudentData.ugCourse = "Computer Application"
        }

        sendDataToServer()
    }

    private fun sendDataToServer(){
        findViewById<View>(R.id.LodingPage).isVisible = true
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
        //Toast.makeText(this, Imagepath.toString(), Toast.LENGTH_SHORT).show()
        mDbRef.child("StudentData").child(Username.substring(0,Username.indexOf('.')).lowercase()).setValue(StudentData)
        if (Imagepath.toString() != "hello"){
            sendImagetoStorage()
        }else{
            mDbRef.child("Users").child(Username.substring(0,Username.indexOf('.')).lowercase()).child("mobileNumber").setValue(findViewById<TextView>(R.id.MobileNumber).text.toString())

            if(dataexist) {
                sendDataToExcel("update")
            }else{
                sendDataToExcel("create")
            }

            val intent= Intent(this,UserProfileActivity::class.java)
            intent.putExtra("UserName",Username)
            startActivity(intent)
            finish()
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
        val imageref = StorageReference.child("Images").child("Students").child("ProfilePhoto").child(Username.lowercase())

        //Image compresesion
        var bitmap: Bitmap? = null
        try {
            bitmap = MediaStore.Images.Media.getBitmap(contentResolver, Imagepath)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap!!.compress(Bitmap.CompressFormat.JPEG, 10, byteArrayOutputStream)
        val data: ByteArray = byteArrayOutputStream.toByteArray()

        ///putting image to storage
        val uploadTask = imageref.putBytes(data)
        uploadTask.addOnSuccessListener {
            imageref.downloadUrl.addOnSuccessListener { uri ->
                ImgUrl = uri.toString()
                updateImage()
            }.addOnFailureListener {
                Toast.makeText(applicationContext,"Faild To Select Image.", Toast.LENGTH_SHORT).show()
            }
            Toast.makeText(applicationContext, "Data Is Uploaded", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(applicationContext,"Failed To Upload Image",Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateImage() {
        val path=Username.substring(0,Username.indexOf('.')).lowercase()
        mDbRef.child("StudentData").child(path).child("imageUrl").setValue(ImgUrl)
        mDbRef.child("Users").child(path).child("imageUrl").setValue(ImgUrl)
        mDbRef.child("Users").child(path).child("mobileNumber").setValue(findViewById<TextView>(R.id.MobileNumber).text.toString())
        StudentData.imageUrl = ImgUrl
        if(dataexist) {
            sendDataToExcel("update")
        }else{
            sendDataToExcel("create")
        }

        val intent= Intent(this,UserProfileActivity::class.java)
        intent.putExtra("UserName",Username)
        startActivity(intent)
        finish()
    }

    private fun UpdateDiplomaUi(Diploma: Boolean) {
        diplomaInstitute.isVisible=Diploma
        diplomarName.isVisible=Diploma
        diplomarPercentage.isVisible=Diploma
        diplomarAddmission.isVisible=Diploma
        diplomarPassout.isVisible=Diploma


        diplomaInstitutetxt.isVisible=Diploma
        diplomarNametxt.isVisible=Diploma
        diplomarPercentagetxt.isVisible=Diploma
        diplomarAddmissiontxt.isVisible=Diploma
        diplomarPassouttxt.isVisible=Diploma
    }

    private fun UpdateCourseUi(Mca: Boolean) {
        if(!Mca){
            var h=sem2Atkt.hint.toString()
            sem2Atkt.hint=h+" *"
            h=sem2cgpa.hint.toString()
            sem2cgpa.hint=h+" *"
        }else{
            var h=sem2Atkt.hint.toString()
            if(h.contains('*')) {
                sem2Atkt.hint = h.substring(0, h.indexOf('*') - 1)
                h = sem2cgpa.hint.toString()
                sem2cgpa.hint = h.substring(0, h.indexOf('*') - 1)
            }
        }



        agregatecgpaUg.isVisible = Mca
        agregatePercentageUg.isVisible = Mca
        UgCourse.isVisible = Mca
        UgSpecialization.isVisible = Mca
        UgYop.isVisible = Mca


        findViewById<TextView>(R.id.CgpaInUGtxt).isVisible = Mca
        findViewById<TextView>(R.id.PercentageInUGtxt).isVisible = Mca
        findViewById<TextView>(R.id.UGCoursetxt).isVisible = Mca
        findViewById<TextView>(R.id.UGCourseSpecializationtxt).isVisible = Mca
        findViewById<TextView>(R.id.UGPassingYeartxt).isVisible = Mca

        sem3cgpa.isVisible=!Mca
        sem3Atkt.isVisible=!Mca
        sem4cgpa.isVisible=!Mca
        sem4Atkt.isVisible=!Mca
        sem5cgpa.isVisible=!Mca
        sem5Atkt.isVisible=!Mca
        sem6cgpa.isVisible=!Mca
        sem6Atkt.isVisible=!Mca
        sem7cgpa.isVisible=!Mca
        sem7Atkt.isVisible=!Mca
        sem8cgpa.isVisible=!Mca
        sem8Atkt.isVisible=!Mca


        sem3cgpatxt.isVisible=!Mca
        sem3Atkttxt.isVisible=!Mca
        sem4cgpatxt.isVisible=!Mca
        sem4Atkttxt.isVisible=!Mca
        sem5cgpatxt.isVisible=!Mca
        sem5Atkttxt.isVisible=!Mca
        sem6cgpatxt.isVisible=!Mca
        sem6Atkttxt.isVisible=!Mca
        sem7cgpatxt.isVisible=!Mca
        sem7Atkttxt.isVisible=!Mca
        sem8cgpatxt.isVisible=!Mca
        sem8Atkttxt.isVisible=!Mca
    }

    private fun sendDataToExcel(action : String) {
        val data= StudentData
       val queue : RequestQueue = Volley.newRequestQueue(this)
               var url="https://script.google.com/macros/s/AKfycbxBmSvWjtiXzeBxfuVtnmu-SwnxXP85foqjYKO4EAEx_lgT7F_V3-PZQ_jXZ5IR1kGq/exec?"
               url=url+"action="+action+"&placment="+data.placment+"&fullname="+data.name+"&enroll="+data.enrollNo+"&university=RGPV&course="+data.course+"&yoa="+data.yoa+"&yop="+data.yop+"&dob="+data.dob+"&gender="+data.gender+"&category="+data.category+"&abled="+data.speciallyAbled+"&ssc="+data.schoolpercentageSSc+"&hssc="+data.schoolpercentageHSSc+"&diplomapercentage="+data.diplomaPercentage+"&ugcgpa="+data.aggregateUgCgpa+"&ugpercentage="+data.aggregateUgPercentage+"&pgcgpa="+data.aggregatePgCgpa+"&pgpercentage="+data.aggregatePgPercentage+"&ugcourse="+data.ugCourse+"&ugspecialization="+data.ugSpecialization+"&ugyop="+data.ugyop+"&backlog="+data.activeBacklog+"&backlognumber="+data.numberOfBacklog+"&backloghistory="+data.backlogHistory+"&diploma="+data.diploma+"&diplomayop="+data.diplomayop+"&diplomayoa="+data.diplomayoa+"&diplomainstitute="+data.diplomaInstitute+"&mobile="+data.mobile+"&alternetmobile="+data.alternatemobile+"&email="+data.email+"&sscname="+data.schoolNameSSc+"&sscboard="+data.schoolBoarSSc+"&sscyop="+data.schoolYopSSc+"&hsscname="+data.schoolNameHSSc+"&hsscboard="+data.schoolBoarHSSc+"&hsscyop="+data.schoolYopHSSc+"&gap="+data.gap+"&gapnumber="+data.gapyear+"&sem1sgpa="+data.semester1sgpa+"&sem1atkt="+data.semester1Atkt+"&sem2sgpa="+data.semester2sgpa+"&sem2atkt="+data.semester2Atkt+"&sem3sgpa="+data.semester3sgpa+"&sem3atkt="+data.semester3Atkt+"&sem4sgpa="+data.semester4sgpa+"&sem4atkt="+data.semester4Atkt+"&sem5sgpa="+data.semester5sgpa+"&sem5atkt="+data.semester5Atkt+"&sem6sgpa="+data.semester6sgpa+"&sem6atkt="+data.semester6Atkt+"&sem7sgpa="+data.semester7sgpa+"&sem7atkt="+data.semester7Atkt+"&sem8sgpa="+data.semester8sgpa+"&sem8atkt="+data.semester8Atkt+"&localadd="+data.localAdd+"&localpin="+data.pincodeLocal+"&permanentadd="+data.permanentAdd+"&permanenthometown="+data.permanentHome+"&permanentstate="+data.permanentState+"&permanentpin="+data.permanentPincode+"&fathername="+data.fatherName+"&fathercontect="+data.fatherContect+"&fatheroccupation="+data.fatherOccupation+"&mothername="+data.motherName+"&mothercontect="+data.motherContect+"&internship="+data.internship+"&imgurl="+data.imageUrl+"&declaration=Yes"
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