package com.example.heptotech

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.MenuInflater
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import com.example.heptotech.actvity_view.UserModeCharge

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

        val typeTextView = findViewById<TextView>(R.id.Type_textview)
        val assignTextView = findViewById<TextView>(R.id.Assign_text)
        val appSidTextView = findViewById<TextView>(R.id.APP_text)
        val relationTextView = findViewById<TextView>(R.id.APP_text1)

        // Function to create SpannableString with red asterisk
        fun createSpannableString(baseText: String): SpannableString {
            val spannableString = SpannableString(baseText)
            val asteriskStart = baseText.indexOf('*')
            val asteriskEnd = asteriskStart + 1
            spannableString.setSpan(
                ForegroundColorSpan(Color.RED),
                asteriskStart,
                asteriskEnd,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            return spannableString
        }

        // Set spannable text for all relevant TextViews
        typeTextView.text = createSpannableString(getString(R.string.type))
        assignTextView.text = createSpannableString(getString(R.string.assign_card))
        appSidTextView.text = createSpannableString(getString(R.string.app_sid))
        relationTextView.text = createSpannableString(getString(R.string.relation))

        // Set up PopupMenu for relation selection
        editText.setOnClickListener {
            showPopupMenu(it)
        }

        linearLayout.setOnClickListener {
            val sidText = sidEditText.text.toString()

            if (sidText == "abc11") {
                linearLayout.background = ContextCompat.getDrawable(this, R.drawable.rectangle_34624554)
                textView.setTextColor(ContextCompat.getColor(this, R.color.white))
                sidTextView.visibility = View.GONE
                val intent = Intent(this, UserModeCharge::class.java)
                startActivity(intent)
            } else {
                linearLayout.background = ContextCompat.getDrawable(this, R.drawable.rectangle_34624554)
                textView.setTextColor(ContextCompat.getColor(this, R.color.white))
                sidTextView.visibility = View.VISIBLE // Show "Incorrect SID"
            }
        }

        imageView.setOnClickListener {
            val resultIntent = Intent()
            setResult(Activity.RESULT_OK, resultIntent)
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
            if (isEnable)
                switchimg.setImageResource(R.drawable.yes__1_)
            else
                switchimg.setImageResource(R.drawable.no_1)
            isEnable = !isEnable
        }
    }

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(this, view)
        val menuInflater: MenuInflater = popupMenu.menuInflater
        menuInflater.inflate(R.menu.relation_menu, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.family -> {
                    editText.setText("Family")
                    true
                }
                R.id.friend -> {
                    editText.setText("Friend")
                    true
                }
                R.id.colleague -> {
                    editText.setText("Colleague")
                    true
                }
                R.id.other -> {
                    editText.setText("Other")
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val resultIntent = Intent()
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }
}
