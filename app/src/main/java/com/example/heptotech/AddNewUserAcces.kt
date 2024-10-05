package com.example.heptotech

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
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
import com.example.heptotech.actvity_view.IphoneRouteplan

class AddNewUserAcces : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var confirmLayout: LinearLayout
    private lateinit var switchimg: ImageView
    private lateinit var relationEditText: EditText
    private lateinit var sidEditText: EditText
    private lateinit var incorrectSidTextView: TextView
    private lateinit var textView1: TextView

    private val checkboxes = mutableListOf<CheckBox>()
    private var isSwitchEnabled = false

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_new_user_acces)
        WindowCompat.setDecorFitsSystemWindows(window, false)

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
            if (checkConditions()) {
                startActivity(Intent(this, IphoneRouteplan::class.java))
            }
        }

        relationEditText.setOnClickListener {
            showPopupWindow(it)
        }

        switchimg.setOnClickListener {
            toggleSwitchImage()
            updateConfirmLayoutBackground() // Update background when switch is toggled
        }

        // Add text change listener for SID EditText
        sidEditText.setOnFocusChangeListener { _, _ ->
            updateConfirmLayoutBackground()
            //checkSidVisibility()
        }

        // Add listener for relation EditText
        relationEditText.setOnFocusChangeListener { _, _ ->
            updateConfirmLayoutBackground()
        }

        // Add listener for checkboxes
        checkboxes.forEach { checkbox ->
            checkbox.setOnCheckedChangeListener { _, _ -> updateConfirmLayoutBackground() }
        }
        incorrectSidTextView.visibility = View.GONE
    }

    private fun showPopupWindow(anchor: View) {
        val popupView = layoutInflater.inflate(R.layout.popup_menu, null)
        val popupWindow = PopupWindow(popupView, anchor.width, LinearLayout.LayoutParams.WRAP_CONTENT)

        popupView.findViewById<TextView>(R.id.item_family).setOnClickListener {
            relationEditText.setText("Family")
            popupWindow.dismiss()
            updateConfirmLayoutBackground() // Update when a relation is selected
        }
        popupView.findViewById<TextView>(R.id.item_friend).setOnClickListener {
            relationEditText.setText("Friend")
            popupWindow.dismiss()
            updateConfirmLayoutBackground()
        }
        popupView.findViewById<TextView>(R.id.item_colleague).setOnClickListener {
            relationEditText.setText("Colleague")
            popupWindow.dismiss()
            updateConfirmLayoutBackground()
        }
        popupView.findViewById<TextView>(R.id.item_other).setOnClickListener {
            relationEditText.setText("Other")
            popupWindow.dismiss()
            updateConfirmLayoutBackground()
        }

        popupWindow.showAsDropDown(anchor)
    }

    private fun updateConfirmLayoutBackground() {
      //  val sidText = sidEditText.text.toString()
        val isAppChecked = findViewById<CheckBox>(R.id.checbox_App).isChecked
        val isRfidChecked = findViewById<CheckBox>(R.id.checbox_Apps).isChecked
        val isAnyCheckboxChecked = checkboxes.any { it.isChecked }

        // Determine if the SID is correct
      //  val isSidCorrect = sidText == "abc11"

        // Check if conditions are met for enabling the button
        if (sidEditText.text.isNotEmpty() && isAnyCheckboxChecked && isSwitchEnabled && relationEditText.text.isNotEmpty() && (isAppChecked || isRfidChecked)) {
            confirmLayout.setBackgroundResource(R.drawable.rectangle_34624554)
            textView1.setTextColor(ContextCompat.getColor(this, R.color.white))
           // incorrectSidTextView.visibility = View.GONE // Hide if SID is correct
        } else {
            confirmLayout.background = ContextCompat.getDrawable(this, R.drawable.rectangle_554__ev)
            textView1.setTextColor(ContextCompat.getColor(this, R.color.thumb_color))
          //  incorrectSidTextView.visibility= View.GONE

            // Show the incorrect SID message only if the SID is incorrect
          //  incorrectSidTextView.visibility = if (!isSidCorrect) View.VISIBLE else View.GONE
        }
    }

   /* private fun checkSidVisibility() {
        // Show the "incorrect SID" message only if the SID is incorrect
        if (sidEditText.text.toString() != "abc11") {
            incorrectSidTextView.visibility = View.VISIBLE
        } else {
            incorrectSidTextView.visibility = View.GONE
        }*/
    //}

    private fun checkConditions(): Boolean {
        // Check conditions for navigation
      //  val sidText = sidEditText.text.toString()
        val isAppChecked = findViewById<CheckBox>(R.id.checbox_App).isChecked
        val isRfidChecked = findViewById<CheckBox>(R.id.checbox_Apps).isChecked
        val isAnyCheckboxChecked = checkboxes.any { it.isChecked }

        return (sidEditText.text.isNotEmpty() && isAnyCheckboxChecked && isSwitchEnabled &&
                relationEditText.text.isNotEmpty() && (isAppChecked || isRfidChecked))
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
