package com.yusufcancakmak.drugabuseapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.yusufcancakmak.drugabuseapp.models.EmployeModel
import com.yusufcancakmak.drugabuseapp.databinding.ActivityInsertionBinding

class InsertionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInsertionBinding
    private lateinit var dbref:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityInsertionBinding.inflate(layoutInflater)
        setContentView(binding.root)



        dbref=FirebaseDatabase.getInstance().getReference("Employees")

        binding.etSaveButton.setOnClickListener {
            saveEmployeeData()
        }

    }
    private fun saveEmployeeData(){
        //getting values
        val edname=binding.etEmpName.text.toString()
        val edage=binding.etEmpAge.text.toString()
        val edsalary=binding.etEmptySalary.text.toString()

        if (edname.isEmpty()){
            Toast.makeText(this,"Please enter name",Toast.LENGTH_SHORT).show()
        }
        if (edage.isEmpty()){
            Toast.makeText(this,"Please enter age",Toast.LENGTH_SHORT).show()
        }
        if (edsalary.isEmpty()){
            Toast.makeText(this,"Please enter salary",Toast.LENGTH_SHORT).show()
        }

        //calısan kmliği
        val empId =dbref.push().key!!

        val employee= EmployeModel(empId, edname, edage, edsalary)

        dbref.child(empId).setValue(employee)
            .addOnCompleteListener {
                Toast.makeText(this,"Data inserted successfully",Toast.LENGTH_SHORT).show()

                //clear text in the line
                binding.etEmpName.text.clear()
                binding.etEmpAge.text.clear()
                binding.etEmptySalary.text.clear()

            }.addOnFailureListener { err->
                Toast.makeText(this,"Error${err.message.toString()}",Toast.LENGTH_SHORT).show()

            }

    }
}


