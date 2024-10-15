package com.example.heptotech.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.heptotech.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddStopFragment  : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("AddStopFragment", "onCreateView called")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.addstop_bottom_sheet, container, false)
    }
    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
}