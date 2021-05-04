package com.eseo.sampleapi.ui.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.recyclical.datasource.emptyDataSource
import com.afollestad.recyclical.setup
import com.afollestad.recyclical.withItem
import com.bumptech.glide.Glide
import com.eseo.sampleapi.R
import com.eseo.sampleapi.data.models.LocalUser
import com.eseo.sampleapi.data.service.UsersService
import com.eseo.sampleapi.ui.main.fragment.viewHolder.LocalUserViewHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListFragment : Fragment() {

    val dataSource = emptyDataSource()
    var page = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        init(view)
        return view
    }

    private fun init(view: View) {
        view.findViewById<Button>(R.id.refresh).setOnClickListener { getData() }

        view.findViewById<RecyclerView>(R.id.rvUser).setup {
            withDataSource(dataSource)
            withLayoutManager(LinearLayoutManager(this@ListFragment.requireContext()))
            withItem<LocalUser, LocalUserViewHolder>(R.layout.item_list) {
                onBind(::LocalUserViewHolder) { _, item ->
                    title.text = "${item.first_name} ${item.last_name}"
                    Glide.with(image.context)
                        .load(item.avatar)
                        .into(image)
                }
                onClick {
                    findNavController().navigate(ListFragmentDirections.showUserDetails(item.id))
                }
            }
        }

    }

    private fun getData() {
        page += 1
        CoroutineScope(Dispatchers.IO).launch {
            val users = UsersService.instance.getUsers(page)
            activity?.runOnUiThread {
                dataSource.addAll(users)
            }
        }
    }

    companion object {
        fun newInstance() = DetailsFragment()
    }
}