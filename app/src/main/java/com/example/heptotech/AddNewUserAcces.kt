package com.example.heptotech

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import com.example.heptotech.activity_view.IphoneMap

class AddNewUserAcces : AppCompatActivity() {

    private lateinit var rootLayout: View
    private lateinit var imageView: ImageView
    private lateinit var textView: TextView
    private lateinit var sidTextView: TextView
    private lateinit var switchimg: ImageView
    private lateinit var editText: EditText
    private lateinit var sidEditText: EditText
    private var isEnable = false

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_new_user_acces)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val linearLayout = findViewById<LinearLayout>(R.id.linearconform)
        rootLayout = findViewById(R.id.main)
        imageView = findViewById(R.id.back1)
        textView = findViewById(R.id.confirm)
        switchimg = findViewById(R.id.switch_btn)
        editText = findViewById(R.id.Relation_Edittext)
        sidTextView = findViewById(R.id.incorrect_sid)
        sidEditText = findViewById(R.id.SID_Edittext)

        // Set up text views with red asterisks
        setupSpannableText()

        // Set up the PopupWindow for relation selection
        editText.setOnClickListener {
            showPopupWindow(it)
        }

        linearLayout.setOnClickListener {
            handleLinearLayoutClick(linearLayout)
        }

        imageView.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }

        rootLayout.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                linearLayout.background = ContextCompat.getDrawable(this, R.drawable.conform_rectangle)
                textView.setTextColor(ContextCompat.getColor(this, R.color.thumb_color))
            }
            false
        }

        switchimg.setOnClickListener {
            toggleSwitchImage()
        }
    }

    private fun setupSpannableText() {
        // Create and set Spannable text for TextViews (if needed)
    }

    private fun showPopupWindow(anchor: View) {
        val popupView = layoutInflater.inflate(R.layout.popup_menu, null)
        val popupWindow = PopupWindow(popupView, anchor.width, LinearLayout.LayoutParams.WRAP_CONTENT)

        // Set onClick listeners for the items
        popupView.findViewById<TextView>(R.id.item_family).setOnClickListener {
            editText.setText("Family")
            popupWindow.dismiss()
        }
        popupView.findViewById<TextView>(R.id.item_friend).setOnClickListener {
            editText.setText("Friend")
            popupWindow.dismiss()
        }
        popupView.findViewById<TextView>(R.id.item_colleague).setOnClickListener {
            editText.setText("Colleague")
            popupWindow.dismiss()
        }
        popupView.findViewById<TextView>(R.id.item_other).setOnClickListener {
            editText.setText("Other")
            popupWindow.dismiss()
        }

        // Show the popup window below the anchor view
        popupWindow.showAsDropDown(anchor)
    }

    private fun handleLinearLayoutClick(linearLayout: LinearLayout) {
        val sidText = sidEditText.text.toString()
        if (sidText == "abc11") {
            linearLayout.background = ContextCompat.getDrawable(this, R.drawable.rectangle_34624554)
            textView.setTextColor(ContextCompat.getColor(this, R.color.white))
            sidTextView.visibility = View.GONE
            startActivity(Intent(this, IphoneMap::class.java))
        } else {
            linearLayout.background = ContextCompat.getDrawable(this, R.drawable.rectangle_34624554)
            textView.setTextColor(ContextCompat.getColor(this, R.color.white))
            sidTextView.visibility = View.VISIBLE
        }
    }

    private fun toggleSwitchImage() {
        if (isEnable) {
            switchimg.setImageResource(R.drawable.yes__1_)
        } else {
            switchimg.setImageResource(R.drawable.no_1)
        }
        isEnable = !isEnable
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setResult(Activity.RESULT_OK)
        finish()
    }
}
