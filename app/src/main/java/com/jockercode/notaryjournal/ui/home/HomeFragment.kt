package com.jockercode.notaryjournal.ui.home


import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.activity.result.contract.ActivityResultContracts

import androidx.fragment.app.Fragment
import com.jockercode.notaryjournal.databinding.FragmentHomeBinding
import com.jockercode.notaryjournal.model.DriverLicense
import com.jockercode.notaryjournal.ui.scan.ScanActivity
import com.jockercode.notaryjournal.util.RawTextParser

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
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

        binding.fab.setOnClickListener { view ->
            val intent = Intent(requireContext(), ScanActivity::class.java)
            scanLauncher.launch(intent)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}