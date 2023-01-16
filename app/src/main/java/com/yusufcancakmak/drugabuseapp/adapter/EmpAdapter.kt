package com.yusufcancakmak.drugabuseapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.recyclerview.widget.RecyclerView
import com.yusufcancakmak.drugabuseapp.databinding.EmpListItemBinding
import com.yusufcancakmak.drugabuseapp.models.EmployeModel

class EmpAdapter(private val emplist:ArrayList<EmployeModel>):RecyclerView.Adapter<EmpAdapter.MyviewHolder>() {

    var onItemClick:((EmployeModel)->Unit)? =null

    inner class MyviewHolder(val binding: EmpListItemBinding):RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyviewHolder {
        return MyviewHolder(EmpListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyviewHolder, position: Int) {
       val currentlist=emplist[position]

        holder.binding.fetchtext.text=currentlist.empName.toString()
        holder.itemView.setOnClickListener { onItemClick!!.invoke(currentlist) }


    }

    override fun getItemCount()=emplist.size
}