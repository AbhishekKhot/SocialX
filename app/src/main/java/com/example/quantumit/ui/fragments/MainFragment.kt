package com.example.quantumit.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.quantumit.R
import com.google.firebase.auth.FirebaseAuth
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = FragmentPagerItemAdapter(childFragmentManager,
        FragmentPagerItems.with(activity)
            .add("LOGIN", LoginFragment::class.java)
            .add("SIGNUP", SignupFragment::class.java)
            .create())

        viewpager.adapter=adapter
       viewpagerTab.setViewPager(viewpager)
    }

    override fun onStart() {
        super.onStart()
        if(firebaseAuth.currentUser?.uid?.isNotEmpty() == true){
            findNavController().navigate(R.id.action_mainFragment_to_newsActivity)
        }
    }
}