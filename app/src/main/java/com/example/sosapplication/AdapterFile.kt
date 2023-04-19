package com.example.sosapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sosapplication.database.EmergencyContactTable
import com.example.sosapplication.databinding.LayoutFileBinding

class AdapterFile(private val onItemClicked:(EmergencyContactTable)->Unit):ListAdapter<EmergencyContactTable,AdapterFile.ItemViewHolder>(DiffCallBack) {
    inner class ItemViewHolder(var binding: LayoutFileBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(contact:EmergencyContactTable){
            binding.apply {
                binding.name.text=contact.name
                binding.number.text=contact.number
            }
        }
    }

    companion object {
        private val DiffCallBack= object : DiffUtil.ItemCallback<EmergencyContactTable>(){
            override fun areItemsTheSame(oldEmergencyContactTable: EmergencyContactTable, newEmergencyContactTable: EmergencyContactTable): Boolean {
                return oldEmergencyContactTable===newEmergencyContactTable
            }

            override fun areContentsTheSame(oldEmergencyContactTable: EmergencyContactTable, newEmergencyContactTable: EmergencyContactTable): Boolean {
                return oldEmergencyContactTable.number==newEmergencyContactTable.number
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(LayoutFileBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val current=getItem(position)
        holder.itemView.setOnClickListener{
            onItemClicked(current)
        }
        holder.bind(current)
    }

}


