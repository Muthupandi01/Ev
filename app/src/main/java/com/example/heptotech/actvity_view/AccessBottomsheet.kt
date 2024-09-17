package com.example.heptotech.actvity_view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heptotech.R
import com.example.heptotech.adapters.UserAdapter
import com.example.heptotech.bean_dataclass.User
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

class AccessBottomsheet : AppCompatActivity() {
    lateinit var back: ImageView
    lateinit var btn_submit: TextView
    lateinit var usercardrec: RecyclerView
    lateinit var addlnr: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_access_bottomsheet)
        back = findViewById(R.id.back)
        btn_submit = findViewById(R.id.btn_submit)
        usercardrec = findViewById(R.id.usercardrec)
        addlnr = findViewById(R.id.addlnr)

        addlnr.setOnClickListener {

        }

        val users = listOf(
            User(R.drawable.dummy1, "Robert Fox", "Brother", "RFID Card"),
            User(R.drawable.dummy2, "Albert Flores", "Brother", "RFID Card"),
            User(R.drawable.dummy3, "Kristin Watson", "Friend", "App"),
            User(R.drawable.dummy4, "Sister", "Friend", "App , RFID Card"),
            User(R.drawable.dummy5, "Sister", "Friend", "App"),
            User(R.drawable.dummy6, "Harold", "Friend", "RFID Card")
        )
        usercardrec.layoutManager = LinearLayoutManager(this)
        usercardrec.adapter = UserAdapter(users) { selectedUser ->
//            val resultIntent = Intent().apply {
//                putExtra("selected_user_name", selectedUser.username)
//                putExtra("selected_user_relation", selectedUser.relationStatus)
//                putExtra("selected_user_card_type", selectedUser.cardType)
//            }
//            setResult(Activity.RESULT_OK, resultIntent)
//            finish()



//            val bottomSheetDialog = BottomSheetDialog(this)
//            val view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_user, null)
//            bottomSheetDialog.setContentView(view)
//            bottomSheetDialog.show()

            val bottomSheetDialog = BottomSheetDialog(this)
            bottomSheetDialog.setContentView(R.layout.bottom_sheet_user)

            // Ensure the bottom sheet expands fully to show the image
            val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)!!)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

            // Show the dialog
           // bottomSheetDialog.show()



        }



        back.setOnClickListener {
            val resultIntent = Intent()
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
        btn_submit.setOnClickListener {
            val resultIntent = Intent()
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val resultIntent = Intent()
        setResult(Activity.RESULT_OK, resultIntent)
        finish()

    }
}