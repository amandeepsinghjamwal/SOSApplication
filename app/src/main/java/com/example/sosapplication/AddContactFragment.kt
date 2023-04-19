package com.example.sosapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.sosapplication.database.EmergencyContactTable
import com.example.sosapplication.databinding.FragmentAddContactBinding


class AddContactFragment : Fragment() {
    private val viewModel: ContactViewModel by activityViewModels {
        ContactsViewModelFactory(
            (activity?.application as ApplicationClass).database.contactDao()
        )
    }
    private var _binding:FragmentAddContactBinding?=null
    val binding get()=_binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentAddContactBinding.inflate(inflater,container,false)
        binding.addContactButton.setOnClickListener{
            if (isValidEnteries()){
                val contact= EmergencyContactTable(null,binding.name.text.toString(),binding.number.text.toString())
                viewModel.addContact(contact)
                (activity as HomeActivity).replaceFragment(ContactListFragment(),null,false)
            }
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