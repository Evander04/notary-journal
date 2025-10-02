package com.jockercode.notaryjournal.ui.records

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.jockercode.notaryjournal.databinding.FragmentRecordsBinding
import com.jockercode.notaryjournal.db.DatabaseProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecordsFragment : Fragment() {

    private var _binding: FragmentRecordsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRecordsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.recordsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        lifecycleScope.launch {
            val db = DatabaseProvider.getDatabase(requireContext())
            val records = db.notaryDao().getAll()

            withContext(Dispatchers.Main) {
                binding.recordsRecyclerView.adapter = RecordAdapter(records)
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}