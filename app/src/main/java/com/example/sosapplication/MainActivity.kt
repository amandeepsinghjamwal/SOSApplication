package com.example.sosapplication

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Toast
import androidx.appcompat.widget.TooltipCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.ViewModelProvider
import com.example.sosapplication.database.ContactssDao
import com.example.sosapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val contactViewModelFactory=ContactsViewModelFactory((application as ApplicationClass).database.contactDao())
        val viewModel:ContactViewModel= ViewModelProvider(this@MainActivity,contactViewModelFactory)[ContactViewModel::class.java]
        ViewCompat.setTooltipText(binding.floatingBtn, "Add or edit sos contacts")
        TooltipCompat.setTooltipText(binding.floatingBtn, "Add or edit sos contacts")
        binding.floatingBtn.setOnClickListener{
            startActivity(Intent(this,HomeActivity::class.java))
        }

        val contacts= emptyList<String>().toMutableList()
        viewModel.contactList.observe(this){ numberList ->
            numberList.forEach{
                contacts+= it.number
            }
        }
        binding.sosButton.setOnClickListener{

                contacts.forEach{
                    val smsManager:SmsManager = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        this.getSystemService(SmsManager::class.java)
                    } else {
                        SmsManager.getDefault()
                    }
                    smsManager.sendTextMessage(it,null,"hi",null,null)
                    Toast.makeText(this,"Sent successfully", Toast.LENGTH_SHORT).show()
            }
        }
    }
}