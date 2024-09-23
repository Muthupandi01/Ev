package com.example.heptotech.actvity_view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.heptotech.R
import com.example.heptotech.activity_view.ConnectToEvActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.journeyapps.barcodescanner.BarcodeView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ScanGetText : AppCompatActivity() {
    private lateinit var btn_submit: TextView
    private lateinit var closesub: LinearLayout
    private lateinit var closeparent: ImageView
    private lateinit var tv_start_charge: ConstraintLayout
    private lateinit var parentMain: ConstraintLayout
    private lateinit var continueparent: RelativeLayout
    private lateinit var leftButton: LinearLayout
    private lateinit var rightButton: LinearLayout
    private lateinit var leftButtonText: TextView
    private lateinit var rightButtonText: TextView
    private lateinit var rightIcon: ImageView
    private lateinit var leftIcon: ImageView
    private lateinit var activateTextView: TextView
    private lateinit var centralImage: ImageView
    private lateinit var qr_code_frame: RelativeLayout
    private lateinit var flash: ImageView
    private var qrvalue: String? = null
    private lateinit var dummycharge: LinearLayout
    private lateinit var dummyparent: LinearLayout
    private var checkStr = ""
    private lateinit var scannerView: BarcodeView

    // Flag to check if BottomSheetDialog is already shown
    private var isBottomSheetShown = false
    private var isFlashOn = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_get_text)
        // Initialize views
        btn_submit = findViewById(R.id.btn_submit)
        scannerView = findViewById(R.id.scanner_view)
        continueparent = findViewById(R.id.continueparent)
        tv_start_charge = findViewById(R.id.tv_start_charge)
        parentMain = findViewById(R.id.parentMain)
        closeparent = findViewById(R.id.close_button)
        closesub = findViewById(R.id.closesub)
        leftButton = findViewById(R.id.leftButton)
        rightButton = findViewById(R.id.rightButton)
        centralImage = findViewById(R.id.centralImage)
        leftButtonText = findViewById(R.id.leftButtonText)
        rightButtonText = findViewById(R.id.rightButtonText)
        rightIcon = findViewById(R.id.rightIcon)
        leftIcon = findViewById(R.id.leftIcon)
        dummycharge = findViewById(R.id.dummycharge)
        dummyparent = findViewById(R.id.dummyparent)
        qr_code_frame = findViewById(R.id.qr_code_frame)
        flash = findViewById(R.id.flash)

        val scanAnimationView: ImageView = findViewById(R.id.scan_animation_view)
        val animationDrawable: AnimationDrawable = scanAnimationView.drawable as AnimationDrawable
        animationDrawable.start()

        GlobalScope.launch {
            delay(1500)
            val resultIntent = Intent()
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }


        // Set up the QR code scanner
        scannerView.decodeContinuous { result ->
           // handleScanResult(result.text)

            GlobalScope.launch {
                delay(1500)
                val resultIntent = Intent()
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }

        // Handle flashlight toggle
        flash.setOnClickListener {
            toggleFlash()
        }

        // Start scanning when frame is clicked
        qr_code_frame.setOnClickListener {
            checkStr = "scanclick"
            scannerView.resume()
        }

        dummycharge.setOnClickListener {
            checkStr = "dummyclick"
            dummyparent.isVisible = false
            showImagePickerBottomSheet()
        }

        closeparent.setOnClickListener {
            startActivity(Intent(this@ScanGetText, ConnectToEvActivity::class.java))
        }



        btn_submit.setOnClickListener {
            val scanResult = "Sample Scan Result" // Replace with actual scan result
            val resultIntent = Intent()
            resultIntent.putExtra("EXTRA_SCAN_RESULT", scanResult)
            setResult(Activity.RESULT_OK, resultIntent)
            finish() // Close ScanGetText and return to ViewVehicles

        }


        leftButton.setOnClickListener {
            centralImage.setImageResource(R.drawable.connectortypeleft_01_ev)
            updateButtonStyles(leftButton, rightButton, leftButtonText, rightButtonText, leftIcon, rightIcon)
        }

        rightButton.setOnClickListener {
            centralImage.setImageResource(R.drawable.connectortyperight_01_ev)
            updateButtonStyles(rightButton, leftButton, rightButtonText, leftButtonText, rightIcon, leftIcon)
        }

        closesub.setOnClickListener {
            parentMain.isVisible = true
            continueparent.isVisible = false
            dummyparent.isVisible = true
        }
    }
    private fun handleScanResult(scanResult: String) {
        // Only show BottomSheetDialog if it's not already shown
//        if (!isBottomSheetShown) {
//            isBottomSheetShown = true
//            qrvalue = scanResult
//
//            showImagePickerBottomSheet()
//        }

        parentMain.isVisible=false
        continueparent.isVisible=true

    }

    @SuppressLint("MissingInflatedId")
    private fun showImagePickerBottomSheet() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_scanpage, null)
        bottomSheetDialog.setContentView(view)

        val chargeIdEditText = view.findViewById<EditText>(R.id.et_chargeid)
        activateTextView = view.findViewById(R.id.btn_submit)
        val img_tick = view.findViewById<ImageView>(R.id.img_tick)

        val qrvalueSafe = qrvalue ?: ""

        if (checkStr == "dummyclick") {
            chargeIdEditText.setText("")
            img_tick.isVisible = false
            activateTextView.isEnabled = false
            activateTextView.setBackgroundResource(R.drawable.rectangle_1634_ev)
            activateTextView.setTextColor(Color.BLACK)
        } else {
            if (qrvalueSafe.isNotEmpty()) {
                //chargeIdEditText.setText(qrvalueSafe)
                img_tick.isVisible = true
                activateTextView.isEnabled = true
                activateTextView.setBackgroundResource(R.drawable.rectangle_green_ev)
                activateTextView.setTextColor(Color.WHITE)
            } else {
                img_tick.isVisible = false
                activateTextView.isEnabled = false
                activateTextView.setBackgroundResource(R.drawable.rectangle_1634_ev)
                activateTextView.setTextColor(Color.BLACK)
            }
        }

        chargeIdEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val chargeId = s.toString().trim()
                if (chargeId.isEmpty()) {
                    img_tick.isVisible = false
                    activateTextView.isEnabled = false
                    activateTextView.setBackgroundResource(R.drawable.rectangle_1634_ev)
                    activateTextView.setTextColor(Color.BLACK)
                } else {
                    img_tick.isVisible = true
                    activateTextView.isEnabled = true
                    activateTextView.setBackgroundResource(R.drawable.rectangle_green_ev)
                    activateTextView.setTextColor(Color.WHITE)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        activateTextView.setOnClickListener {
            bottomSheetDialog.dismiss()
            parentMain.isVisible = false
            continueparent.isVisible = true
        }

        bottomSheetDialog.setOnDismissListener {
            isBottomSheetShown = false
            activateTextView.clearFocus()
            dummyparent.isVisible = true
            img_tick.isVisible = false
            activateTextView.isEnabled = false
            activateTextView.setBackgroundResource(R.drawable.rectangle_1634_ev)
            activateTextView.setTextColor(Color.BLACK)
        }

        bottomSheetDialog.show()
    }

    private fun updateButtonStyles(
        activeButton: LinearLayout,
        inactiveButton: LinearLayout,
        activeTextView: TextView,
        inactiveTextView: TextView,
        activeIcon: ImageView,
        inactiveIcon: ImageView
    ) {
        activeButton.setBackgroundResource(R.drawable.rectangle_34624418_ev)
        inactiveButton.setBackgroundResource(R.drawable.rectangle_34624417_ev)
        activeTextView.setTextColor(ContextCompat.getColor(this, R.color.greentxt))
        inactiveTextView.setTextColor(ContextCompat.getColor(this, R.color.commontxtcolor))
        activeIcon.isVisible = true
        inactiveIcon.isVisible = false
    }

    private fun toggleFlash() {
        if (isFlashOn) {
            // Turn off the flashlight
            scannerView.setTorch(false)
            flash.setImageResource(R.drawable.group_1666_ev)
            isFlashOn = false
        } else {
            // Turn on the flashlight
            scannerView.setTorch(true)
            flash.setImageResource(R.drawable.flash_on_ev)
            isFlashOn = true
        }

    }


    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this@ScanGetText, ConnectToEvActivity::class.java))
    }

    override fun onResume() {
        super.onResume()
        // Resume QR code scanning
        scannerView.resume()
    }

    override fun onPause() {
        super.onPause()
        // Pause QR code scanning
        scannerView.pause()
    }
}