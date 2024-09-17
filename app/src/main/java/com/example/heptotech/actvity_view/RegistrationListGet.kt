package com.example.heptotech.actvity_view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heptotech.itemlistener.ItemListenervehicle
import com.example.heptotech.R
import com.example.heptotech.adapters.VehicleAdapter
import com.example.heptotech.bean_dataclass.Vehicle

class RegistrationListGet : AppCompatActivity(), ItemListenervehicle {
    private lateinit var lnrSearch: LinearLayout
    private lateinit var etSearch: EditText
    private lateinit var fab: LinearLayout
    private lateinit var vehicleList: RecyclerView
    private lateinit var nodata: LinearLayout
   // private val vehicleAdapter = VehicleAdapter(mutableListOf(), this)
    private val originalVehicleList = mutableListOf<Vehicle>()
    private lateinit var vehicleAdapter: VehicleAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_list_get)

        lnrSearch = findViewById(R.id.lnr_search)
        etSearch = findViewById(R.id.et_search)
        fab = findViewById(R.id.fab)
        nodata = findViewById(R.id.nodata)
        vehicleList = findViewById(R.id.vehicle_list)
        vehicleAdapter = VehicleAdapter(mutableListOf(), this)
        vehicleList.layoutManager = LinearLayoutManager(this)
        vehicleList.adapter = vehicleAdapter
        val intent = Intent(this, VehicleRegistration::class.java)
        startActivityForResult(intent, REQUEST_CODE_REGISTER_VEHICLE)
        fab.setOnClickListener {
            val intent = Intent(this, VehicleRegistration::class.java)
            startActivityForResult(intent, REQUEST_CODE_REGISTER_VEHICLE)
        }

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterVehicleList(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun filterVehicleList(query: String) {
        val filteredList = if (query.isEmpty()) {
            originalVehicleList
        } else {
            originalVehicleList.filter {
                it.model.contains(query, ignoreCase = true) || it.licensePlate.contains(query, ignoreCase = true)
            }
        }
        vehicleAdapter.updateVehicleList(filteredList)
        updateViewVisibility(filteredList.isEmpty())
    }

    private fun updateViewVisibility(isEmpty: Boolean) {
        if (isEmpty) {
            nodata.visibility = LinearLayout.VISIBLE
            vehicleList.visibility = RecyclerView.GONE
        } else {
            nodata.visibility = LinearLayout.GONE
            vehicleList.visibility = RecyclerView.VISIBLE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_REGISTER_VEHICLE -> {
                    val newVehicle = data?.getSerializableExtra("new_vehicle") as? Vehicle
                    newVehicle?.let {
                        vehicleAdapter.addVehicle(it)
                        originalVehicleList.add(it)
                        filterVehicleList(etSearch.text.toString())
                    }
                }

                REQUEST_CODE_EDIT_VEHICLE -> {
                    val updatedVehicle = data?.getSerializableExtra("updated_vehicle") as? Vehicle
                    val position = data?.getIntExtra("vehicle_position", -1) ?: -1

                    if (updatedVehicle != null && position != -1) {
                        if (position < originalVehicleList.size) {
                            vehicleAdapter.updateVehicleAtPosition(position, updatedVehicle)
                            originalVehicleList[position] = updatedVehicle
                            filterVehicleList(etSearch.text.toString())
                        }
                    }
                }
            }
        }
    }

    override fun onItemClicked(position: Int, vehicle: Vehicle) {
        val intent = Intent(this, VehicleRegistration::class.java).apply {
            putExtra("edit_vehicle", vehicle)
            putExtra("vehicle_position", position)
        }
        startActivityForResult(intent, REQUEST_CODE_EDIT_VEHICLE)
    }

    override fun onItemDeleted(position: Int) {
        if (position >= 0 && position < originalVehicleList.size) {
            originalVehicleList.removeAt(position)
            vehicleAdapter.notifyItemRemoved(position)
            filterVehicleList(etSearch.text.toString())
        }
    }

    companion object {
        private const val REQUEST_CODE_REGISTER_VEHICLE = 1
        private const val REQUEST_CODE_EDIT_VEHICLE = 2
    }
}
