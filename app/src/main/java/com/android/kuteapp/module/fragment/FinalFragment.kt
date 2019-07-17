package com.android.kuteapp.module.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.kuteapp.R
import kotlinx.android.synthetic.main.finalfragment.*

class FinalFragment : Fragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v) {
            goToHome -> {
                //Navigate to First Fragment and backstack is automatically removed because we use popUpTo in the navigation file
                findNavController().navigate(R.id.moveToFirst)

            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.finalfragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        goToHome.setOnClickListener(this)
    }
}