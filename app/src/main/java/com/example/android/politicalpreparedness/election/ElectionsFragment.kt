package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.databinding.FragmentElectionsBinding
import com.example.android.politicalpreparedness.network.CivicsApi

class ElectionsFragment : Fragment() {

    private val viewModel by viewModels<ElectionsViewModel> {
        ElectionsViewModelFactory(ElectionDatabase.getInstance(requireContext()).electionDao, CivicsApi.retrofitService)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        //TODO: Add ViewModel values and create ViewModel
        //TODO: Add binding values
        val binding = FragmentElectionsBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.electionsViewModel = viewModel


        //TODO: Link elections to voter info

        //TODO: Initiate recycler adapters

        //TODO: Populate recycler adapters
        return binding.root

    }

    //TODO: Refresh adapters when fragment loads

}