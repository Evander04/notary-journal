package com.jockercode.notaryjournal.ui.home


import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast

import androidx.activity.result.contract.ActivityResultContracts

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.jockercode.notaryjournal.databinding.FragmentHomeBinding
import com.jockercode.notaryjournal.db.DatabaseProvider
import com.jockercode.notaryjournal.db.NotaryDatabase
import com.jockercode.notaryjournal.model.DriverLicense
import com.jockercode.notaryjournal.model.Notary
import com.jockercode.notaryjournal.ui.scan.ScanActivity
import com.jockercode.notaryjournal.util.RawTextParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val scanLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){result ->
        if (result.resultCode == RESULT_OK){
            val scannedText = result.data?.getStringExtra("SCAN_RESULT")?:""
            Log.i("<result>",scannedText)
            val driverLicense: DriverLicense = RawTextParser.parse(scannedText)
            Log.i("<result>",driverLicense.toString())
            // Pre-fill editable fields (user can still fix errors)
            binding.inputFullName.setText(driverLicense.fullName ?: "")
            binding.inputAddress.setText(driverLicense.address ?: "")
            binding.inputDob.setText(driverLicense.dob ?: "")

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Setup spinners
        val idTypes = listOf("Driver License", "Passport", "EAD", "GC")
        binding.spinnerIdType.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, idTypes)

        val notaryTypes = listOf("Oath/Affirmation","Acknowledgment", "Jurat", "Copy Certification")
        binding.spinnerNotaryType.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, notaryTypes)

        //FAB
        binding.fab.setOnClickListener { view ->
            val intent = Intent(requireContext(), ScanActivity::class.java)
            scanLauncher.launch(intent)
        }

        //Handle Submit
        binding.btnSubmit.setOnClickListener {
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val now = formatter.format(Date())

            val notaryObject = Notary(
                fullName = binding.inputFullName.text.toString(),
                address = binding.inputFullName.text.toString(),
                dob = binding.inputFullName.text.toString(),
                documentType = binding.spinnerIdType.selectedItem.toString(),
                notaryType = binding.spinnerNotaryType.selectedItem.toString(),
                dateCreated = now
            )

            lifecycleScope.launch {
                val db = DatabaseProvider.getDatabase(requireContext())
                db.notaryDao().insert(notaryObject)
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Saved to database ✅", Toast.LENGTH_SHORT).show()
                }
                clean()
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun clean(){
        binding.inputFullName.setText("")
        binding.inputAddress.setText("")
        binding.inputDob.setText("")
        binding.spinnerIdType.setSelection(0)
        binding.spinnerNotaryType.setSelection(0)
    }

}