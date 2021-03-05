package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.databinding.FragmentElectionsBinding
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter
import com.example.android.politicalpreparedness.election.adapter.ElectionListener
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Election

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

        binding.recyclerUpcoming.adapter = ElectionListAdapter(ElectionListener {
            viewModel.displayElectionDetails(it)
        })

        viewModel.navigateToElectionDetails.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                navigateToDetailFragment(it)
                viewModel.displayElectionDetailsComplete()
            }
        })


        //TODO: Link elections to voter info

        //TODO: Initiate recycler adapters

        //TODO: Populate recycler adapters
        return binding.root

    }

    //TODO: Refresh adapters when fragment loads

    private fun navigateToDetailFragment(election: Election) {
        this.findNavController().navigate(ElectionsFragmentDirections.actionElectionsFragmentToElectionDetailFragment(election.id, election.division))
    }

}