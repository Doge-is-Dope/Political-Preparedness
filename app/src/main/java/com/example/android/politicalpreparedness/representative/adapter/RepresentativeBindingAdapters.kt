package com.example.android.politicalpreparedness.representative.adapter

import android.view.View
import android.widget.*
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.representative.model.Representative
import com.example.android.politicalpreparedness.utils.CivicsApiStatus
import timber.log.Timber

@BindingAdapter("representativeList")
fun RecyclerView.setRepresentativeData(data: List<Representative>?) {
    val adapter = adapter as RepresentativeListAdapter
    adapter.submitList(data)
}

@BindingAdapter("profileImage")
fun fetchImage(view: ImageView, src: String?) {
    src?.let {
        val uri = src.toUri().buildUpon().scheme("https").build()
        Glide.with(view.context)
                .load(uri)
                .placeholder(R.drawable.ic_profile)
                .error(R.drawable.ic_profile)
                .circleCrop()
                .into(view)
    }
}

@InverseBindingAdapter(attribute = "stateValue")
fun Spinner.getNewValue(): String {
    val states: Array<String> = resources.getStringArray(R.array.states)
    return states[this.selectedItemPosition]
}

@BindingAdapter("stateValueAttrChanged")
fun setStateListener(spinner: Spinner, stateChange: InverseBindingListener?) {
    if (stateChange == null) {
        spinner.onItemSelectedListener = null
    } else {
        val listener: AdapterView.OnItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                stateChange.onChange()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                stateChange.onChange()
            }
        }
        spinner.onItemSelectedListener = listener
    }
}

@BindingAdapter("stateValue")
fun Spinner.setNewValue(value: String?) {
    val adapter = toTypedAdapter<String>(this.adapter as ArrayAdapter<*>)
    val position = when (adapter.getItem(0)) {
        is String -> adapter.getPosition(value)
        else -> this.selectedItemPosition
    }
    if (position >= 0) {
        setSelection(position)
    }
}

@BindingAdapter("apiStatus")
fun TextView.setStatus(status: CivicsApiStatus?) {
        when (status) {
            CivicsApiStatus.LOADING -> {
                visibility = View.VISIBLE
                text = resources.getString(R.string.text_api_status_loading)
            }
            CivicsApiStatus.ERROR -> {
                visibility = View.VISIBLE
                text = resources.getString(R.string.text_api_status_error)
            }
            CivicsApiStatus.DONE -> {
                visibility = View.GONE
            }
        }
}

inline fun <reified T> toTypedAdapter(adapter: ArrayAdapter<*>): ArrayAdapter<T>{
    return adapter as ArrayAdapter<T>
}
