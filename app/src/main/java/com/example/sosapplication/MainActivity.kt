package com.example.sosapplication

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.TooltipCompat
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sosapplication.database.ContactssDao
import com.example.sosapplication.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        var location:String?=""
        fusedLocationClient.lastLocation
            .addOnSuccessListener {
                if(it!=null){
                    location= "https://www.google.com/maps/search/?api=1&query=${it.latitude},${it.longitude}"
                }
                // Got last known location. In some rare situations this can be null.
            }
        Log.e("catc",location.toString())
        val permission = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.SEND_SMS
        )
        if(ContextCompat.checkSelfPermission(this,permission.toString())== PackageManager.PERMISSION_GRANTED){

        }
        else{
            requestPermissions(permission,12,)
        }

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
                    smsManager.sendTextMessage(it,null,"Hi, I am in danger right now. Please help me at $location",null,null)
                    Toast.makeText(this,"Sent successfully", Toast.LENGTH_SHORT).show()
            }
        }
    }
}