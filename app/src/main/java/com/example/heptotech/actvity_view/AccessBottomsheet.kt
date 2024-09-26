package com.example.heptotech.activity_view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heptotech.AddNewUserAcces
import com.example.heptotech.R
import com.example.heptotech.adapters.UserAdapter
import com.example.heptotech.adapters.VechicleUserAdapter
import com.example.heptotech.bean_dataclass.User
import com.example.heptotech.bean_dataclass.VechicleUserItem
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textview.MaterialTextView

class AccessBottomsheet : AppCompatActivity() {
    private lateinit var back: ImageView
    private lateinit var btn_submit: TextView
  private  lateinit var linearLayout: LinearLayout
  private lateinit var imageView: ImageView
    private var bottomSheetDialog: BottomSheetDialog? = null

    private lateinit var usercardrec: RecyclerView
    private lateinit var addlnr: LinearLayout

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_access_bottomsheet)


        back = findViewById(R.id.back)
        btn_submit = findViewById(R.id.btn_submit)
        usercardrec = findViewById(R.id.usercardrec)
        addlnr = findViewById(R.id.addlnr)
      //  imageView=findViewById(R.id.imgtick_recycle)

        addlnr.setOnClickListener {
            startActivity(Intent(this, AddNewUserAcces::class.java))
        }

        val users = listOf(
            User(R.drawable.dummy1, "Robert Fox", "Brother", "RFID Card",R.drawable.group_427319118_ev),
            User(R.drawable.dummy2, "Albert Flores", "Brother", "RFID Card",R.drawable.group_427319118_ev),
            User(R.drawable.dummy3, "Kristin Watson", "Friend", "App",R.drawable.group_427319118_ev),
            User(R.drawable.dummy4, "Sister", "Friend", "App, RFID Card",R.drawable.group_427319118_ev),
            User(R.drawable.dummy5, "Sister", "Friend", "App",R.drawable.group_427319118_ev),
            User(R.drawable.dummy6, "Harold", "Friend", "RFID Card",R.drawable.group_427319118_ev)
        )

            usercardrec.layoutManager = LinearLayoutManager(this)
            usercardrec.adapter = UserAdapter(users) { selectedUser ->

                bottomSheetDialog = BottomSheetDialog(this)
                bottomSheetDialog!!.show()
                val sheetView = this.layoutInflater.inflate(R.layout.bottom_sheet_user_temp, null)

                bottomSheetDialog!!.setContentView(sheetView)

                val vehicleRecyclerView = bottomSheetDialog!!.findViewById<RecyclerView>(R.id.recycle_vechicle)
                val linearLayout= bottomSheetDialog!!.findViewById<LinearLayout>(R.id.btn_linear)

                if (vehicleRecyclerView == null || linearLayout == null) {
                    Log.e("AccessBottomsheet", "Vehicle RecyclerView or btn_linear not found.")
                    return@UserAdapter
                }

                val vehicleItems = mutableListOf(
                    VechicleUserItem(R.drawable.mcar_list, "Tesla Model X", "License Plate 123", false),
                    VechicleUserItem(R.drawable.mcar1_list, "Tesla Model Y", "License Plate 456", false),
                    VechicleUserItem(R.drawable.mcar2_list, "Tesla Model S", "License Plate 789", false)
                )

                vehicleRecyclerView.layoutManager = LinearLayoutManager(this)
                vehicleRecyclerView.adapter = VechicleUserAdapter(vehicleItems, object : VechicleUserAdapter.ClickItemListener {
                    override fun onItemClick(position: Int, checked: Boolean) {
                        updateSubmitButtonVisibility(vehicleItems,linearLayout) // Update button visibility here
                    }
                })

                bottomSheetDialog!!.findViewById<ImageView>(R.id.user_img)?.setImageResource(selectedUser.userImageResId)
                bottomSheetDialog!!.findViewById<TextView>(R.id.username_txt)?.text = selectedUser.username
                bottomSheetDialog!!.findViewById<TextView>(R.id.relationstatus)?.text = selectedUser.relationStatus
                bottomSheetDialog!!.findViewById<TextView>(R.id.typecard)?.text = selectedUser.cardType
                bottomSheetDialog!!.findViewById<ImageView>(R.id.imgtick_recycle)?.setImageResource(selectedUser.userImageResId)








                   //showBottomSheet(selectedUser)







                        }

        back.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }

        btn_submit.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }
    }


    private fun showBottomSheet(selectedUser: User) {
        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_user_temp)

        val vehicleRecyclerView = bottomSheetDialog.findViewById<RecyclerView>(R.id.recycle_vechicle)
        val linearLayout=bottomSheetDialog.findViewById<LinearLayout>(R.id.btn_linear)

        if (vehicleRecyclerView == null || linearLayout == null) {
            Log.e("AccessBottomsheet", "Vehicle RecyclerView or btn_linear not found.")
            return
        }

        val vehicleItems = mutableListOf(
            VechicleUserItem(R.drawable.mcar_list, "Tesla Model X", "License Plate 123", false),
            VechicleUserItem(R.drawable.mcar1_list, "Tesla Model Y", "License Plate 456", false),
            VechicleUserItem(R.drawable.mcar2_list, "Tesla Model S", "License Plate 789", false)
        )

        vehicleRecyclerView.layoutManager = LinearLayoutManager(this)
        vehicleRecyclerView.adapter = VechicleUserAdapter(vehicleItems, object : VechicleUserAdapter.ClickItemListener {
            override fun onItemClick(position: Int, checked: Boolean) {
                updateSubmitButtonVisibility(vehicleItems,linearLayout) // Update button visibility here
            }
        })

        bottomSheetDialog.findViewById<ImageView>(R.id.user_img)?.setImageResource(selectedUser.userImageResId)
        bottomSheetDialog.findViewById<TextView>(R.id.username_txt)?.text = selectedUser.username
        bottomSheetDialog.findViewById<TextView>(R.id.relationstatus)?.text = selectedUser.relationStatus
        bottomSheetDialog.findViewById<TextView>(R.id.typecard)?.text = selectedUser.cardType
        bottomSheetDialog.findViewById<ImageView>(R.id.imgtick_recycle)?.setImageResource(selectedUser.userImageResId)



        bottomSheetDialog.show()
    }

    private fun updateSubmitButtonVisibility(vehicleItems: List<VechicleUserItem>,linearLayout: LinearLayout) {
        val anyChecked = vehicleItems.any { it.isChecked }
        linearLayout.visibility = if (anyChecked) {
            Log.d("AccessBottomsheet", "Checkbox checked: Showing submit button.")
            View.VISIBLE
        } else {
            Log.d("AccessBottomsheet", "No checkbox checked: Hiding submit button.")
            View.GONE
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setResult(Activity.RESULT_OK)
        finish()
    }
}
