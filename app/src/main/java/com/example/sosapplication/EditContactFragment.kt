package com.example.sosapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.sosapplication.database.EmergencyContactTable
import com.example.sosapplication.databinding.FragmentEditContactBinding
import java.util.jar.Attributes.Name

class EditContactFragment : Fragment() {
    var _binding: FragmentEditContactBinding?=null
    val binding get()=_binding!!
    private val viewModel: ContactViewModel by activityViewModels {
        ContactsViewModelFactory(
            (activity?.application as ApplicationClass).database.contactDao()
        )
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentEditContactBinding.inflate(inflater,container,false)
        var oldName:String?=null
        var oldNumber:String?=null
        var oldId:Int? =null
            oldName=arguments?.getString("Name")
            oldNumber=arguments?.getString("Number")
            oldId=arguments?.getInt("ID",0)

        Log.e("tagggg",oldId.toString())

        Log.e("bundle",oldName.toString())
        binding.name.setText(oldName.toString())
        binding.number.setText(oldNumber.toString())

        binding.editContactButton.setOnClickListener{
            if(isValidEnteries()){
                val contactObj=EmergencyContactTable(oldId,binding.name.text.toString(),binding.number.text.toString())
                viewModel.editContact(contactObj)
                (activity as HomeActivity).replaceFragment(ContactListFragment(),null,false)
            }

        }
        binding.deleteContactButton.setOnClickListener{
            val contactObj=EmergencyContactTable(oldId,binding.name.text.toString(),binding.number.text.toString())
            viewModel.deleteContact(contactObj)
            (activity as HomeActivity).replaceFragment(ContactListFragment(),null,false)
        }
        return binding.root
    }
    private fun isValidEnteries():Boolean {
        if(binding.name.text.isEmpty()){
            binding.name.error="This field cannot be empty"
            return false
        }
        if(binding.number.text.isEmpty()){
            binding.number.error="This field cannot be empty"
            return false
        }
        if(binding.name.text.toString().length>10){
            binding.name.error="Name size cannot be more than 10 digits"
            return false
        }
        if(binding.number.text.toString().length!=10){
            binding.number.error="Please enter a valid phone number"
            return false
        }
        return true
    }
}