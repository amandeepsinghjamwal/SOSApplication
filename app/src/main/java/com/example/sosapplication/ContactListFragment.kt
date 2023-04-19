package com.example.sosapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sosapplication.databinding.FragmentContactListBinding

class ContactListFragment : Fragment() {
    private var _binding:FragmentContactListBinding?=null
    private val binding get()=_binding!!
    private val viewModel: ContactViewModel by activityViewModels {
        ContactsViewModelFactory(
            (activity?.application as ApplicationClass).database.contactDao()
        )
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        _binding= FragmentContactListBinding.inflate(inflater,container,false)
        val adapter=AdapterFile{
             val bundle= Bundle()
            bundle.putString("Name",it.name)
            bundle.putString("Number",it.number)
            bundle.putInt("ID",it.id!!)
            (activity as HomeActivity).replaceFragment(EditContactFragment(),bundle,true)
        }
        binding.recyclerView.adapter=adapter
        viewModel.contactList.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }

        binding.recyclerView.layoutManager=LinearLayoutManager(requireContext())
        binding.floatingBtn.setOnClickListener{
            (activity as HomeActivity).replaceFragment(AddContactFragment(),null,true)
        }
        return binding.root
    }
}