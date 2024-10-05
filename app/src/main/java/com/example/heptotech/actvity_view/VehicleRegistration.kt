package com.example.heptotech.actvity_view

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.example.heptotech.R
import com.example.heptotech.bean_dataclass.Vehicle
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Picasso
import java.io.File
import java.io.IOException
import java.util.Locale

class VehicleRegistration : AppCompatActivity() {

    private lateinit var imgImage: ImageView
    private lateinit var txtImageUpload: TextView
    private lateinit var lnrMake: LinearLayout
    private lateinit var lnrBrand: LinearLayout
    private lateinit var lnrModel: LinearLayout
    private lateinit var uploadParent: RelativeLayout
    private lateinit var etLicencePlate: EditText
    private lateinit var etYear: EditText
    private lateinit var btnSubmit: TextView

    private lateinit var lnrBluetooth: LinearLayout
    private lateinit var bluetoothImg: ImageView
    private lateinit var bluetoothTxt: TextView

    private lateinit var obdImg: ImageView
    private lateinit var obdTxt: TextView
    private lateinit var lnrObd: LinearLayout

    private lateinit var txtMake: TextView
    private lateinit var txtBrand: TextView
    private lateinit var txtModel: TextView
    private lateinit var imgClose: ImageView
    private lateinit var back: ImageView

    private val IMAGE_PICK_REQUEST_CODE = 1001
    private val REQUEST_CODE_SELECT = 1002
    private val PERMISSION_REQUEST_CODE = 1003
    private val CAMERA_REQUEST_CODE = 1004

    private var selectedImageUri: Uri? = null
    private var isEditing: Boolean = false
    private var vehicleToEdit: Vehicle? = null
    private var vehiclePosition: Int = -1
    private var initialVehicle: Vehicle? = null
    private var imageUrl: String? = null
    private var imageFilePath: String? = null

    private lateinit var checkObd: ImageView
    private lateinit var checkBluetooth: ImageView

    var checkStr="N"

    @SuppressLint("MissingInflatedId", "ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_vehicle_registration)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_REQUEST_CODE)
        }

        imgImage = findViewById(R.id.img_image)
        txtImageUpload = findViewById(R.id.txt_imageupload)
        lnrMake = findViewById(R.id.lnr_make)
        lnrBrand = findViewById(R.id.lnr_brand)
        lnrModel = findViewById(R.id.lnr_model)
        etLicencePlate = findViewById(R.id.et_licenceplate)
        etYear = findViewById(R.id.et_year)
        btnSubmit = findViewById(R.id.btn_submit)
        txtMake = findViewById(R.id.txt_make)
        txtBrand = findViewById(R.id.txt_brand)
        txtModel = findViewById(R.id.txt_model)
        uploadParent = findViewById(R.id.upload_parent)
        imgClose = findViewById(R.id.img_close)

        lnrBluetooth = findViewById(R.id.lnr_bluetooth)
        bluetoothImg = findViewById(R.id.blutooth_img)
        bluetoothTxt = findViewById(R.id.blutooth_txt)

        obdImg = findViewById(R.id.obd_img)
        obdTxt = findViewById(R.id.obd_txt)
        lnrObd = findViewById(R.id.lnr_obd)

        back = findViewById(R.id.back)
        checkObd = findViewById(R.id.checkobd)
        checkBluetooth = findViewById(R.id.checkbluetooth)
        etLicencePlate.setFilters(arrayOf(android.text.InputFilter.AllCaps()))
        // Check if editing
        vehicleToEdit = intent.getSerializableExtra("edit_vehicle") as? Vehicle
        vehiclePosition = intent.getIntExtra("vehicle_position", -1)
        etLicencePlate.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    val upperCaseText = it.toString().toUpperCase(Locale.getDefault())
                    if (it.toString() != upperCaseText) {
                        etLicencePlate.setText(upperCaseText)
                        etLicencePlate.setSelection(upperCaseText.length)
                    }
                }
            }
        })
        if (vehicleToEdit != null) {
            isEditing = true
            initialVehicle = vehicleToEdit // Save initial state
            populateFields(vehicleToEdit!!)
            btnSubmit.text = "Update"
        } else {
            btnSubmit.text = "Submit"
        }

        // Check for permissions
        checkPermissions()

        // Handle image close click
        imgClose.setOnClickListener {
            selectedImageUri = null
            imgImage.setImageResource(0) // Clear the image
            imgClose.isVisible = false // Hide the close button
            txtImageUpload.visibility = TextView.VISIBLE
            imageUrl = ""
        }

        back.setOnClickListener {
            val resultIntent = Intent()
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }




        lnrBluetooth.setOnClickListener {
            // Check if the text color is #0897FF (blue)
            val isBluetoothSelected = (bluetoothTxt.currentTextColor == Color.parseColor("#0897FF"))
            if (checkStr == "B" || isBluetoothSelected) {
                checkBluetooth.isVisible = true
                checkObd.isInvisible = true
            } else {
                // Launch the Bluetooth activity to select a Bluetooth device
                val intent = Intent(this, VehicleBluetoothActivity::class.java).apply {
                    putExtra("SEND", "B")
                }
                startActivityForResult(intent, REQUEST_CODE_SELECT)
            }
        }

        lnrObd.setOnClickListener {
            // Check if the text color is #0897FF (blue) for OBD
            // Replace with the actual color used for OBD if different
            val obdTextColor = Color.parseColor("#0897FF")
            val isObdSelected = (obdTxt.currentTextColor == obdTextColor)
            if (checkStr == "O" || isObdSelected) {
                checkBluetooth.isInvisible = true
                checkObd.isVisible = true
            } else {
                // Launch the Bluetooth activity to select an OBD device
                val intent = Intent(this, VehicleBluetoothActivity::class.java).apply {
                    putExtra("SEND", "W")
                }
                startActivityForResult(intent, REQUEST_CODE_SELECT)
            }
        }


        // Handle submit button click
        btnSubmit.setOnClickListener {
            val make = txtMake.text.toString().trim()
            val brand = txtBrand.text.toString().trim()
            val model = txtModel.text.toString().trim()
            val licensePlate = etLicencePlate.text.toString().trim()
            val year = etYear.text.toString().trim()
            imageUrl = selectedImageUri?.toString() ?: initialVehicle?.imageUrl ?: ""

            // Validation
            if (make.isEmpty()) {
                txtMake.error = "Make is required"
            } else if (brand.isEmpty()) {
                txtBrand.error = "Brand is required"
            } else if (model.isEmpty()) {
                txtModel.error = "Model is required"
            } else if (licensePlate.isEmpty()) {
                etLicencePlate.error = "License Plate is required"
            } else if (year.isEmpty()) {
                etYear.error = "Year is required"
            } else {
                val vehicle = Vehicle(
                    make = make,
                    brand = brand,
                    model = model,
                    licensePlate = licensePlate,
                    year = year,
                    imageUrl = imageUrl!!
                )

                val resultIntent = Intent().apply {
                    if (isEditing) {
                        putExtra("updated_vehicle", vehicle)
                        putExtra("vehicle_position", vehiclePosition)
                    } else {
                        putExtra("new_vehicle", vehicle)
                    }
                }

                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }

        // Handle image upload
        uploadParent.setOnClickListener {
            showImagePickerBottomSheet()
        }

        // Handle Make, Brand, and Model selections
        lnrMake.setOnClickListener { navigateToSelection("Make") }
        lnrBrand.setOnClickListener { navigateToSelection("Brand") }
        lnrModel.setOnClickListener { navigateToSelection("Model") }
    }

    private fun showImagePickerBottomSheet() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_image_picker, null)
        bottomSheetDialog.setContentView(view)

        val chooseCamera = view.findViewById<ConstraintLayout>(R.id.choose_camera)
        val chooseGallery = view.findViewById<ConstraintLayout>(R.id.choose_gallery)

        chooseCamera.setOnClickListener {
            bottomSheetDialog.dismiss()
            openCamera()
        }

        chooseGallery.setOnClickListener {
            bottomSheetDialog.dismiss()
            openGallery()
        }

        bottomSheetDialog.show()
    }

    private fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            val photoFile: File? = try {
                createImageFile()
            } catch (ex: IOException) {
                Log.e("VehicleRegistration", "Error occurred while creating the File: ${ex.message}")
                null
            }
            photoFile?.also {
                val photoURI = FileProvider.getUriForFile(
                    this,
                    "com.example.heptotech.fileprovider",
                    it
                )
                selectedImageUri = photoURI
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE)
            } ?: run {
                Log.e("VehicleRegistration", "Photo file is null")
            }
        } else {
            Log.e("VehicleRegistration", "No activity found to handle camera intent")
        }
    }



    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, IMAGE_PICK_REQUEST_CODE)
    }

    private fun checkPermissions() {
        val cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        val storagePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)

        val permissionsNeeded = mutableListOf<String>()
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.CAMERA)
        }
        if (storagePermission != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

//        if (permissionsNeeded.isNotEmpty()) {
//            ActivityCompat.requestPermissions(this, permissionsNeeded.toTypedArray(), CAMERA_REQUEST_CODE)
//        } else {
//            openCamera()
//        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                //
            } else {
                //  Toast.makeText(this, "Camera permission is required to take a photo", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                IMAGE_PICK_REQUEST_CODE -> {
                    selectedImageUri = data?.data
                    if (selectedImageUri != null) {
                        imgImage.setImageURI(selectedImageUri)
                        imgClose.isVisible = true
                        txtImageUpload.visibility = TextView.GONE
                    }
                }
                CAMERA_REQUEST_CODE -> {
                    selectedImageUri = Uri.fromFile(File(imageFilePath))
                    if (selectedImageUri != null) {
                        imgImage.setImageURI(selectedImageUri)
                        imgClose.isVisible = true
                        txtImageUpload.visibility = TextView.GONE
                    }
                }
                REQUEST_CODE_SELECT -> {
                    if (data != null) {
                        val selectedMake = data.getStringExtra("selected_make")
                        val selectedBrand = data.getStringExtra("selected_brand")
                        val selectedModel = data.getStringExtra("selected_model")

                        txtMake.text = selectedMake ?: txtMake.text
                        txtBrand.text = selectedBrand ?: txtBrand.text
                        txtModel.text = selectedModel ?: txtModel.text
                    }
                }
            }
        }

        val selectedConnection = data?.getStringExtra("SELECTED_CONNECTION")
        when (selectedConnection) {
            "Bluetooth" -> {
                bluetoothImg.setColorFilter(ContextCompat.getColor(this, R.color.blue_common))
                bluetoothTxt.setTextColor(ContextCompat.getColor(this, R.color.blue_common))
                lnrBluetooth.setBackgroundResource(R.drawable.blue_rectangle_ev)
                //checkObd.isInvisible = true
                checkStr="B"
                // checkBluetooth.isVisible = true
            }
            "OBD" -> {
                //   checkObd.isVisible = true
                obdImg.setColorFilter(ContextCompat.getColor(this, R.color.blue_common))
                obdTxt.setTextColor(ContextCompat.getColor(this, R.color.blue_common))
                lnrObd.setBackgroundResource(R.drawable.blue_rectangle_ev)
                checkStr="O"
            }
            else -> {
                checkStr="N"
                // Handle default case
            }
        }
    }

    private fun navigateToSelection(type: String) {
        val intent = Intent(this, MakeBrandModel::class.java).apply {
            putExtra("TYPE", type)
            putExtra("current_make", txtMake.text.toString())
            putExtra("current_brand", txtBrand.text.toString())
            putExtra("current_model", txtModel.text.toString())
        }
        startActivityForResult(intent, REQUEST_CODE_SELECT)
    }


    private fun createImageFile(): File? {
        return try {
            val storageDir = getExternalFilesDir(null)
            File.createTempFile("IMG_", ".jpg", storageDir).apply {
                imageFilePath = absolutePath
            }
        } catch (e: Exception) {
            Log.e("VehicleRegistration", "Error creating image file", e)
            null
        }
    }

    private fun populateFields(vehicle: Vehicle) {
        txtMake.text = vehicle.make
        txtBrand.text = vehicle.brand
        txtModel.text = vehicle.model
        etLicencePlate.setText(vehicle.licensePlate)
        etYear.setText(vehicle.year)
        imageUrl = vehicle.imageUrl

        if (!imageUrl.isNullOrEmpty()) {
            Picasso.get().load(imageUrl).into(imgImage)
            imgClose.isVisible = true
            txtImageUpload.visibility = TextView.GONE
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        val resultIntent = Intent()
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }
}
