package com.yusufcancakmak.drugabuseapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.yusufcancakmak.drugabuseapp.adapter.EmpAdapter
import com.yusufcancakmak.drugabuseapp.databinding.ActivityFetchingBinding
import com.yusufcancakmak.drugabuseapp.models.EmployeModel

class FetchingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFetchingBinding
    private lateinit var empAdapter: EmpAdapter
    private lateinit var dref:DatabaseReference

    private  var position: Int?=null
    private lateinit var empList:ArrayList<EmployeModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFetchingBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.rvDrugs.apply {
            layoutManager=LinearLayoutManager(this@FetchingActivity)
            setHasFixedSize(true)
            empList= arrayListOf<EmployeModel>()
            getEmployeesData()

        }
        
    }
    private fun getEmployeesData(){
        binding.rvDrugs.visibility=View.GONE
        binding.LoadingData.visibility=View.VISIBLE

        dref=FirebaseDatabase.getInstance().getReference("Employees")

        //data is showw
        dref.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                empList.clear()
                //snapshot is empty or full
                if (snapshot.exists()){
                    for (empSnap in snapshot.children){
                        val employeData = empSnap.getValue(EmployeModel::class.java)
                        empList.add(employeData!!)


                    }
                    val mAdapter=EmpAdapter(empList)
                    binding.rvDrugs.adapter=mAdapter

                    mAdapter.onClickListener={ employeModel -> showIntent(empList.indexOf(employeModel)) }
                    binding.rvDrugs.visibility=View.VISIBLE
                    binding.LoadingData.visibility=View.GONE

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
    private fun showIntent(position: Int){
        val intent=Intent(this,EmployeeDetailsActivity::class.java)
        intent.putExtra("empId",empList[position].empId)
        intent.putExtra("empName",empList[position].empName)
        intent.putExtra("empAge",empList[position].empAge)
        intent.putExtra("empSalary",empList[position].empSalary)
        startActivity(intent)
    }
}