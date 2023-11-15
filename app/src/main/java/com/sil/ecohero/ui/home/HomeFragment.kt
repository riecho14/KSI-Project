package com.sil.ecohero.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sil.ecohero.ListTrashAdapter
import com.sil.ecohero.R
import com.sil.ecohero.Trash
import com.sil.ecohero.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var rvTrash: RecyclerView
    private val list = ArrayList<Trash>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        rvTrash = binding.rvTrash
        rvTrash.setHasFixedSize(true)

        if(list.isEmpty()){
            list.addAll(getListTrash())
        }

        showRecyclerList()

        return root
    }

    private fun showRecyclerList() {
        rvTrash.layoutManager = LinearLayoutManager(requireContext())
        val listTrashAdapter = ListTrashAdapter(list)
        rvTrash.adapter = listTrashAdapter

    }

    private fun getListTrash(): ArrayList<Trash> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataPhoto = resources.getStringArray(R.array.data_photo)
        val dataDesc = resources.getStringArray(R.array.data_desc)
        val listTrash = ArrayList<Trash>()
        for(i in dataName.indices){
            val trash = Trash(dataName[i], dataPhoto[i], dataDesc[i])
            listTrash.add(trash)
        }
        return listTrash
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}