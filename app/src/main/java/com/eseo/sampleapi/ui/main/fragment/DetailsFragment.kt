package com.eseo.sampleapi.ui.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.recyclical.datasource.dataSourceTypedOf
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

class DetailsFragment : Fragment() {

    val args: DetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    private fun getData() {
        CoroutineScope(Dispatchers.IO).launch {
            val user = UsersService.instance.getUser(args.userId)
            activity?.runOnUiThread {
                view?.apply {
                    val avatar = findViewById<ImageView>(R.id.avatar)
                    val identity = findViewById<TextView>(R.id.identity)

                    identity.text = user.identity()
                    Glide.with(avatar.context)
                        .load(user.avatar)
                        .into(avatar)
                }
            }
        }
    }


    companion object {
        fun newInstance() = DetailsFragment()
    }
}