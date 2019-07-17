package com.android.kuteapp.module.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.kuteapp.R
import kotlinx.android.synthetic.main.fragment_favs.*


class FavsFragment : Fragment(), View.OnClickListener {

    override fun onClick(v: View?) {
        when (v) {
           //Navigate to Last Final Fragment
            goToFinal -> {
                findNavController().navigate(R.id.finalFragment )
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        goToFinal.setOnClickListener(this)
    }
}