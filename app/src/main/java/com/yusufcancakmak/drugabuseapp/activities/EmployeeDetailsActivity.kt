package com.yusufcancakmak.drugabuseapp.activities

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.yusufcancakmak.drugabuseapp.R
import com.yusufcancakmak.drugabuseapp.databinding.ActivityEmployeeDetailsBinding
import com.yusufcancakmak.drugabuseapp.models.EmployeModel

class EmployeeDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEmployeeDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmployeeDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        setValuesToViews()
        binding.btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("empId").toString(),
                intent.getStringExtra("empName").toString()
            )

        }
        binding.btnDelete.setOnClickListener {
            deleteRecord(intent.getStringExtra("empId").toString())
        }
    }
    private fun deleteRecord(id:String){
        val dbref=FirebaseDatabase.getInstance().getReference("Employees").child(id)
        val mTask = dbref.removeValue()
        mTask.addOnSuccessListener {
            Toast.makeText(this,"Employee data deleted",Toast.LENGTH_SHORT).show()
            val intent=Intent(this,FetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener { errror->
            Toast.makeText(this,"Employee data not deleted",Toast.LENGTH_SHORT).show()
        }

    }


    private fun initView() {}
    private fun setValuesToViews() {
        binding.tvEmpId.text = intent.getStringExtra("empId")
        binding.tvEmpName.text = intent.getStringExtra("empName")
        binding.tvEmpAge.text = intent.getStringExtra("empAge")
        binding.tvEmpSalary.text = intent.getStringExtra("empSalary")
    }

    private fun openUpdateDialog(
        empId: String,
        empName: String
    ) {

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_update)
        dialog.setCancelable(true)

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window!!.attributes)
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT

        val update_edname = dialog.findViewById<EditText>(R.id.update_edname)
        val update_edage = dialog.findViewById<EditText>(R.id.update_edage)
        val update_edsalary = dialog.findViewById<EditText>(R.id.update_edsalary)
        val update_btn = dialog.findViewById<Button>(R.id.btn_update)


        update_edname.setText(intent.getStringExtra("empName").toString())
        update_edage.setText(intent.getStringExtra("empAge").toString())
        update_edsalary.setText(intent.getStringExtra("empSalary").toString())

        update_btn.setOnClickListener {
            updateEmpData(
                empId,
                update_edname.text.toString(),
                update_edage.text.toString(),
                update_edsalary.text.toString()
            )
            dialog.dismiss()
            Toast.makeText(applicationContext, "Employee Data Uplodated", Toast.LENGTH_SHORT).show()



            binding.tvEmpName.text = update_edname.text.toString()
            binding.tvEmpAge.text = update_edage.text.toString()
            binding.tvEmpSalary.text = update_edsalary.text.toString()
        }


        dialog.show()
        dialog.window!!.attributes = layoutParams

    }

    private fun updateEmpData(id: String, name: String, age: String, salary: String) {
        val dbref = FirebaseDatabase.getInstance().getReference("Employees").child(id)
        val empınfo = EmployeModel(id, name, age, salary)
        dbref.setValue(empınfo)


    }


}
