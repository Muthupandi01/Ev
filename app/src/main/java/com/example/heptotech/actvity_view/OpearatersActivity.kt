package com.example.heptotech.actvity_view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heptotech.OperatorAdapter
import com.example.heptotech.OperatorVechicleAdapter
import com.example.heptotech.R
import com.example.heptotech.bean_dataclass.OperatorData
import com.example.heptotech.bean_dataclass.OperatorVechicleData
import com.example.heptotech.bean_dataclass.User
import com.example.heptotech.bean_dataclass.VechicleUserItem
import com.example.heptotech.bean_dataclass.VehicleItem
import com.google.android.material.bottomsheet.BottomSheetDialog

class OpearatersActivity : AppCompatActivity(),OperatorAdapter.OnItemClickListener
{
    private lateinit var createtextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operators)
        val operatorList = listOf(
            OperatorData(R.drawable.dummy1_ev, "Robert Fox", "Brother", "RFID Card"),
            OperatorData(R.drawable.dummy2_ev, "Albert Flores", "Brother", "RFID Card"),
            OperatorData(R.drawable.dummy3_ev, "Kristin Watson", "Friend", "App"),
            OperatorData(R.drawable.dummy4_ev, "Sister", "Friend", "App, RFID Card"),
            OperatorData(R.drawable.dummy5_ev, "Sister", "Friend", "App"),
            OperatorData(R.drawable.dummy6_ev, "Harold", "Friend", "RFID Card")
        )

        val recyclerView: RecyclerView = findViewById(R.id.usercardrect)
        recyclerView.layoutManager = LinearLayoutManager(this) // or context if in Fragment
        recyclerView.adapter = OperatorAdapter(operatorList, this)
    }

    @SuppressLint("MissingInflatedId")
    override fun onItemClick(operator: OperatorData)
    {
        val bottomSheetDialog = BottomSheetDialog(this,R.style.ShoppingList_BottomSheetDialog)
        val bottomSheetView = layoutInflater.inflate(R.layout.bottomsheet_operators, null)
        val OperatorRecyclerView = bottomSheetView.findViewById<RecyclerView>(R.id.recycle_vechicle1)
        val submit = bottomSheetView.findViewById<LinearLayout>(R.id.btn_linear1)

        val vehicleItems = listOf(
            OperatorVechicleData(R.drawable.mcar_list_ev, "Tesla Model X", "License Plate 123", false),
            OperatorVechicleData(R.drawable.mcar1_list_ev, "Tesla Model Y", "License Plate 456", false),
            OperatorVechicleData(R.drawable.mcar2_list_ev, "Tesla Model S", "License Plate 789", false)
        )
        OperatorRecyclerView.layoutManager = LinearLayoutManager(this)
        OperatorRecyclerView.adapter = OperatorVechicleAdapter(vehicleItems)


        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.show()
        submit.setOnClickListener()
        {
            bottomSheetDialog.dismiss()

        }

    }






}

