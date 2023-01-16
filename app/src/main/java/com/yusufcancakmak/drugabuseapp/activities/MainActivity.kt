package com.yusufcancakmak.drugabuseapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yusufcancakmak.drugabuseapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.insertData.setOnClickListener {
            val intent=Intent(this, InsertionActivity::class.java)
            startActivity(intent)
        }
        binding.FetchData.setOnClickListener {
            val intent=Intent(this, FetchingActivity::class.java)
            startActivity(intent)


        }
    }


}