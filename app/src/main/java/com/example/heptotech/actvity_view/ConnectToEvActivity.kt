package com.example.heptotech.activity_view

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.TextWatcher
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.ImageSpan
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heptotech.R
import com.example.heptotech.actvity_view.ScanGetText
import com.example.heptotech.adapters.VehicleItemCheckBoxAdapter
import com.example.heptotech.bean_dataclass.VehicleItem

class ConnectToEvActivity : AppCompatActivity(),
    VehicleItemCheckBoxAdapter.OnVehicleCheckChangeListener {

    private lateinit var vehicleRecyclerView: RecyclerView
    private lateinit var btnSubmit: TextView
    private lateinit var devicename: TextView
    private lateinit var typeDeviceName: EditText
    private lateinit var typeSerialNumber: EditText
    private lateinit var serialImg:ImageView
    private lateinit var pincodeImg : ImageView

    private lateinit var typePinCode: EditText
    private lateinit var vehicleItemCheckBoxAdapter: VehicleItemCheckBoxAdapter
    private val selectedVehicles = mutableSetOf<String>()
    private val vehicleList = listOf(
        VehicleItem(R.drawable.g_ev, "2-Wheeler"),
        VehicleItem(R.drawable.h_ev, "4-Wheeler")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connect_to_ev)

        vehicleRecyclerView = findViewById(R.id.vehicletype)
        btnSubmit = findViewById(R.id.btn_submit)
        devicename = findViewById(R.id.devicename)
        typeDeviceName = findViewById(R.id.tyoedevice_name)
        typeSerialNumber = findViewById(R.id.type_serialnumber)
        typePinCode = findViewById(R.id.tepe_pincode)
        serialImg = findViewById(R.id.serialImg)
        pincodeImg = findViewById(R.id.pincodeImg)

        val back = findViewById<ImageView>(R.id.back)

        btnSubmit.setOnClickListener {
            if (isAnyInputFilled()) {
                navigateToConnectToServer()
            }
        }

        back.setOnClickListener {
            setResult(Activity.RESULT_OK, Intent())
            finish()
        }

        pincodeImg.setOnClickListener {
            val intent = Intent(this, ScanGetText::class.java)
            startActivity(intent)
        //    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

        }

        serialImg.setOnClickListener {
            val intent = Intent(this, ScanGetText::class.java)
            startActivity(intent)
         //   overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        vehicleItemCheckBoxAdapter = VehicleItemCheckBoxAdapter(vehicleList, this)
        vehicleRecyclerView.layoutManager = LinearLayoutManager(this)
        vehicleRecyclerView.adapter = vehicleItemCheckBoxAdapter

        enableEdgeToEdge()
        setRedStarTextView(devicename)

        // Setup touch listener to hide keyboard when tapping outside EditTexts
        val rootLayout = findViewById<LinearLayout>(R.id.root_layout)
        rootLayout.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val currentFocus = currentFocus
                if (currentFocus != null) {
                    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(currentFocus.windowToken, 0)
                    currentFocus.clearFocus()
                }
            }
            false
        }

        setupTextWatchers()
    }

    // Existing methods...

    private fun setupTextWatchers() {
        val textChangeListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateSubmitButtonState()
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        typeDeviceName.addTextChangedListener(textChangeListener)
        typeSerialNumber.addTextChangedListener(textChangeListener)
        typePinCode.addTextChangedListener(textChangeListener)
    }

    private fun updateSubmitButtonState() {
        btnSubmit.setBackgroundResource(
            if (isAnyInputFilled()) R.drawable.rectanglef_ev else R.drawable.rectangle_34624554_ev
        )
    }

    private fun isAnyInputFilled(): Boolean {
        return typeDeviceName.text.isNotEmpty()
                //typeSerialNumber.text.isNotEmpty() ||
              //  typePinCode.text.isNotEmpty()
    }

    private fun navigateToConnectToServer() {
        val intent = Intent(this, com.example.heptotech.actvity_view.ConnectToServer::class.java).apply {
            putExtra("selectedVehicles", selectedVehicles.toTypedArray())
        }
        startActivity(intent)
       // overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    fun setRedStarTextView(textView: TextView) {
        val context = textView.context
        val text = "Device name *"
        val spannableString = SpannableString(text)
        val starColor = Color.RED
        val starIndex = text.indexOf("*")
        val starDrawable: Drawable? = ContextCompat.getDrawable(context, R.drawable.red_star_ev)
        starDrawable?.setBounds(0, 0, starDrawable.intrinsicWidth, starDrawable.intrinsicHeight)
        val imageSpan = starDrawable?.let { ImageSpan(it, ImageSpan.ALIGN_BOTTOM) }
        spannableString.setSpan(imageSpan, starIndex, starIndex + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(ForegroundColorSpan(starColor), starIndex, starIndex + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textView.text = spannableString
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setResult(Activity.RESULT_OK, Intent())
        finish()
    }

    override fun onVehicleCheckedChange(isChecked: Boolean, vehicleType: String) {
        if (isChecked) {
            selectedVehicles.add(vehicleType)
        } else {
            selectedVehicles.remove(vehicleType)
        }
        updateSubmitButtonState()
    }
}