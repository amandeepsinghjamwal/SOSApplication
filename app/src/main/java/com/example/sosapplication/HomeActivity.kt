package com.example.sosapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.sosapplication.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    lateinit var adapter: AdapterFile
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(ContactListFragment(),null,false)
    }

    fun replaceFragment(fragment: Fragment, bundle: Bundle?,backStack:Boolean) {
        val editContactFragment = fragment.apply {
            arguments = bundle
        }
        val fragmentManager=supportFragmentManager
        var fragmentTransaction = fragmentManager.beginTransaction()
        if(backStack){
            fragmentTransaction.replace(R.id.fragmentHost,editContactFragment).addToBackStack(null)
        }
        else{
            fragmentManager.popBackStack()
            fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentHost,editContactFragment)
        }
        fragmentTransaction.commit()
    }
}