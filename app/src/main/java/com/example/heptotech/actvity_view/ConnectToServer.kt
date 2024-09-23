package com.example.heptotech.actvity_view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.heptotech.R
import com.example.heptotech.activity_view.ConnectToEvActivity

class ConnectToServer : AppCompatActivity() {
    lateinit var back: ImageView
    lateinit var btn_submit: TextView

    lateinit var serverImg: ImageView
    lateinit var idImg: ImageView
    lateinit var passwordImg: ImageView

    lateinit var copied1: TextView
    lateinit var copied2: TextView
    lateinit var copied3: TextView

    lateinit var header2: TextView
    lateinit var header3: TextView

    private val handler = Handler()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connect_to_server)

        back = findViewById(R.id.back)
        btn_submit = findViewById(R.id.btn_submit)
        serverImg = findViewById(R.id.Img)
        idImg = findViewById(R.id.idImg)
        passwordImg = findViewById(R.id.passwordImg)
        copied1 = findViewById(R.id.copied1)
        copied2 = findViewById(R.id.copied2)
        copied3 = findViewById(R.id.copied3)
        header2 = findViewById(R.id.header2)
        header3 = findViewById(R.id.header3)


        val selectedVehicles = intent.getStringArrayExtra("selectedVehicles")
        selectedVehicles?.let {
            when (it.size) {
                1 -> {
                    if (it.contains("2-Wheeler")) {
                        header2.text="Product Key"
                        header3.text="Device Secret"
                    }
                }
                2 -> {
                    if (it.contains("2-Wheeler") && it.contains("4-Wheeler")) {
                        header2.text="ID"
                        header3.text="Password"
                    }
                }
            }}


        serverImg.setOnClickListener {
            showTick(serverImg, "WSS://OCPP.evzone.com")
            copied1.isVisible=true
        }
        idImg.setOnClickListener {
            showTick(idImg, "EV67-CH45-ST-89")
            copied2.isVisible=true
        }
        passwordImg.setOnClickListener {
            showTick(passwordImg, "454545556")
            copied3.isVisible=true
        }

        btn_submit.setOnClickListener {
            val intent = Intent(this@ConnectToServer, MapMakrAftersave::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down)



        }

        back.setOnClickListener {
            val resultIntent = Intent()
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }

    private fun showTick(imageView: ImageView, text: String) {
        imageView.setImageResource(R.drawable.tick_ev) // Your tick image
        copyToClipboard(text)

        // Reset image after 3 seconds
        handler.postDelayed({
            imageView.setImageResource(R.drawable.copyfilesimple_ev) // Your default image
            copied1.isVisible=false
            copied2.isVisible=false
            copied3.isVisible=false

        }, 2000)
    }

    private fun copyToClipboard(text: String) {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Copied Text", text)
        clipboard.setPrimaryClip(clip)
        // Toast.makeText(this, "Text copied to clipboard", Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val resultIntent = Intent()
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }


}
