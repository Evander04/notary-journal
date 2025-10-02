package com.jockercode.notaryjournal.ui.records

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jockercode.notaryjournal.databinding.ItemNotaryRecordBinding
import com.jockercode.notaryjournal.model.Notary

class RecordAdapter (private val records: List<Notary>): RecyclerView.Adapter<RecordAdapter.RecordViewHolder>(){

    inner class RecordViewHolder(val binding: ItemNotaryRecordBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        val binding = ItemNotaryRecordBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return RecordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        val record = records[position]
        with(holder.binding) {
            txtFullName.text = record.fullName
            txtAddress.text = "Address: ${record.address}"
            txtDob.text = "DOB: ${record.dob}"
            txtIdType.text = "ID Type: ${record.documentType}"
            txtNotaryType.text = "Notary: ${record.notaryType}"
            txtDateCreated.text = "Created: ${record.dateCreated}"
        }
    }

    override fun getItemCount() = records.size
}