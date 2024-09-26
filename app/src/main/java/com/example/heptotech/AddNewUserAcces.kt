package com.example.heptotech

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import com.example.heptotech.activity_view.AccessBottomsheet
import com.example.heptotech.activity_view.IphoneMap

class AddNewUserAcces : AppCompatActivity() {

    private lateinit var rootLayout: View
    private lateinit var imageView: ImageView
    private lateinit var confirmLayout: LinearLayout
    private lateinit var switchimg: ImageView
    private lateinit var relationEditText: EditText
    private lateinit var sidEditText: EditText
    private lateinit var incorrectSidTextView: TextView
    private lateinit var textView1:TextView


    private val checkboxes = mutableListOf<CheckBox>()
    private var isSwitchEnabled = false

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_new_user_acces)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        rootLayout = findViewById(R.id.main)
        imageView = findViewById(R.id.back1)
        confirmLayout = findViewById(R.id.linearconform)
        switchimg = findViewById(R.id.switch_btn)
        relationEditText = findViewById(R.id.Relation_Edittext)
        sidEditText = findViewById(R.id.SID_Edittext)
        incorrectSidTextView = findViewById(R.id.incorrect_sid)
        textView1 = findViewById(R.id.confirm)

        // Initialize CheckBoxes
        checkboxes.add(findViewById(R.id.checbox_Allow))
        checkboxes.add(findViewById(R.id.checbox1))
        checkboxes.add(findViewById(R.id.checbox3))

        // Set up listeners
        imageView.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }

        confirmLayout.setOnClickListener {
            handleConfirmClick()
        }

        relationEditText.setOnClickListener {
            showPopupWindow(it)
        }

        switchimg.setOnClickListener {
            toggleSwitchImage()
        }

    }

    private fun showPopupWindow(anchor: View) {
        val popupView = layoutInflater.inflate(R.layout.popup_menu, null)
        val popupWindow = PopupWindow(popupView, anchor.width, LinearLayout.LayoutParams.WRAP_CONTENT)

        popupView.findViewById<TextView>(R.id.item_family).setOnClickListener {
            relationEditText.setText("Family")
            popupWindow.dismiss()
        }
        popupView.findViewById<TextView>(R.id.item_friend).setOnClickListener {
            relationEditText.setText("Friend")
            popupWindow.dismiss()
        }
        popupView.findViewById<TextView>(R.id.item_colleague).setOnClickListener {
            relationEditText.setText("Colleague")
            popupWindow.dismiss()
        }
        popupView.findViewById<TextView>(R.id.item_other).setOnClickListener {
            relationEditText.setText("Other")
            popupWindow.dismiss()
        }

        popupWindow.showAsDropDown(anchor)
    }

    private fun handleConfirmClick() {
        val sidText = sidEditText.text.toString()
        val isAppChecked = findViewById<CheckBox>(R.id.checbox_App).isChecked
        val isRfidChecked = findViewById<CheckBox>(R.id.checbox_Apps).isChecked
        val isAnyCheckboxChecked = checkboxes.any { it.isChecked }

        // Check if SID is correct, at least one checkbox is checked, and the switch is enabled
        if (sidText == "abc11" && isAnyCheckboxChecked && isSwitchEnabled && relationEditText.text.isNotEmpty() && (isAppChecked || isRfidChecked)) {
            confirmLayout.setBackgroundResource(R.drawable.rectangle_34624554)
            textView1.setTextColor(ContextCompat.getColor(this, R.color.white))
            incorrectSidTextView.visibility = View.GONE

            startActivity(Intent(this, AccessBottomsheet::class.java))
        } else {
            // Reset background if conditions are not met
           // confirmLayout.setBackgroundColor(Color.WHITE) // Or any default color you want
            confirmLayout.background = ContextCompat.getDrawable(this, R.drawable.rectangle_554__ev)
            textView1.setTextColor(ContextCompat.getColor(this, R.color.thumb_color))
            incorrectSidTextView.visibility = View.VISIBLE
        }
    }

    private fun toggleSwitchImage() {
        isSwitchEnabled = !isSwitchEnabled
        switchimg.setImageResource(if (isSwitchEnabled) R.drawable.yes__1_ else R.drawable.no_1)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setResult(Activity.RESULT_OK)
        finish()
    }
}
