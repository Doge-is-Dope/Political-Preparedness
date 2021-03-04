package com.example.android.politicalpreparedness.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter
import com.example.android.politicalpreparedness.network.jsonadapter.ElectionAdapter
import com.example.android.politicalpreparedness.network.models.Election
import java.text.SimpleDateFormat
import java.util.*


/**
 * When there is no Election data (data is null), hide the [RecyclerView], otherwise show it.
 */
@BindingAdapter("listData")
fun RecyclerView.setElectionData(data: List<Election>?) {
    val adapter = adapter as ElectionListAdapter
    adapter.submitList(data)
}


@BindingAdapter("day")
fun TextView.setElectionDay(date: Date?) {
    val calendar = Calendar.getInstance()
    date?.let {
        calendar.time = it
        val dateFormat = SimpleDateFormat("YYYY-MM-dd", Locale.getDefault())
        text = dateFormat.format(calendar.time)
    }
}