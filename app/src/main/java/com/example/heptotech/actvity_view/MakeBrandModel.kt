package com.example.heptotech.actvity_view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heptotech.itemlistener.ItemClickListenervehicle
import com.example.heptotech.R
import com.example.heptotech.adapters.BrandAdapter
import com.example.heptotech.adapters.MakeAdapter
import com.example.heptotech.adapters.ModelAdapter
import com.example.heptotech.bean_dataclass.Make
import com.example.heptotech.bean_dataclass.Model
import com.example.heptotech.bean_dataclass.Brand

class MakeBrandModel : AppCompatActivity(), ItemClickListenervehicle<Any> {

    private lateinit var makeRecyclerView: RecyclerView
    private lateinit var brandRecyclerView: RecyclerView
    private lateinit var modelRecyclerView: RecyclerView

    private lateinit var makeParent: LinearLayout
    private lateinit var brandParent: LinearLayout
    private lateinit var modelParent: LinearLayout

    private lateinit var makeAdapter: MakeAdapter
    private lateinit var brandAdapter: BrandAdapter
    private lateinit var modelAdapter: ModelAdapter


    private lateinit var back: ImageView

    private val makeList = listOf(
        Make(R.drawable.tesla_ev, "Tesla"),
        Make(R.drawable.chevolet_ev, "Toyota"),
        Make(R.drawable.bmw_ev, "BMW"),
        Make(R.drawable.honda_ev, "Honda"),
        Make(R.drawable.benz_ev, "Mercedus"),
        Make(R.drawable.kia_ev, "KIA"),
        Make(R.drawable.jaguar_ev, "Jaguar"),
        Make(R.drawable.audi_ev, "Audi"),
        Make(R.drawable.hyndai_ev, "Hyundai"),
        Make(R.drawable.chevolet_ev, "Chevrolet"),
        Make(R.drawable.landrver_ev, "Land Rover"),
        Make(R.drawable.lexus_ev, "Lexus")
    )

    private val brandList = listOf(
        Brand(R.drawable.car2_ev, "Tesla Model X"),
        Brand(R.drawable.car2_ev, "Tesla Model S"),
        Brand(R.drawable.car4_ev, "Tesla Model Y"),
        Brand(R.drawable.car1_ev_ev, "Tesla Model Roadster")

    )

    private val modelList = listOf(
        Model("Dual Motor AWD 100 kWh (670 Hp)"),
        Model("Plaid Tri Motor AWD 100 kWh (1020 HP)")

    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_brand_model)

        // Initialize RecyclerViews and Parents
        makeRecyclerView = findViewById(R.id.make_recycler)
        brandRecyclerView = findViewById(R.id.brand_recycler)
        modelRecyclerView = findViewById(R.id.model_recycler)

        makeParent = findViewById(R.id.make_parent)
        brandParent = findViewById(R.id.brand_parent)
        modelParent = findViewById(R.id.model_parent)
        back = findViewById(R.id.back)

        // Initialize Adapters
        makeAdapter = MakeAdapter(makeList, this)
        brandAdapter = BrandAdapter(brandList, this)
        modelAdapter = ModelAdapter(modelList, this)

        back.setOnClickListener {
            val resultIntent = Intent()
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        // Get the type from Intent
        val type = intent.getStringExtra("TYPE")

        // Set up RecyclerViews and visibility based on the type
        when (type) {
            "Make" -> {


                makeRecyclerView.layoutManager = GridLayoutManager(this, 2)
                makeRecyclerView.adapter = makeAdapter
                makeParent.visibility = View.VISIBLE
                brandParent.visibility = View.GONE
                modelParent.visibility = View.GONE

                // Setup search filter for Make
                val searchMake = findViewById<EditText>(R.id.et_search_make)
                searchMake.addTextChangedListener { text ->
                    makeAdapter.filter(text.toString())
                }


            }

            "Brand" -> {
                brandRecyclerView.layoutManager = LinearLayoutManager(this)
                brandRecyclerView.adapter = brandAdapter
                makeParent.visibility = View.GONE
                brandParent.visibility = View.VISIBLE
                modelParent.visibility = View.GONE

                // Setup search filter for Brand
                val searchBrand = findViewById<EditText>(R.id.et_search_brand)
                searchBrand.addTextChangedListener { text ->
                    brandAdapter.filter(text.toString())
                }

                searchBrand.setOnFocusChangeListener { v, hasFocus ->
                    if (hasFocus) {
                        // Keep the EditText visible when focused
                        brandParent.visibility = View.VISIBLE
                    }
                }
            }

            "Model" -> {
                modelRecyclerView.layoutManager = LinearLayoutManager(this)
                modelRecyclerView.adapter = modelAdapter
                makeParent.visibility = View.GONE
                brandParent.visibility = View.GONE
                modelParent.visibility = View.VISIBLE

                // Setup search filter for Model
                val searchModel = findViewById<EditText>(R.id.et_search_model)
                searchModel.addTextChangedListener { text ->
                    modelAdapter.filter(text.toString())
                }
                searchModel.setOnFocusChangeListener { v, hasFocus ->
                    if (hasFocus) {
                        // Keep the EditText visible when focused
                        modelParent.visibility = View.VISIBLE
                    }
                }
            }
        }
     }



     override fun onItemSelected(item: Any) {
        val resultIntent = Intent().apply {
            when (item) {
                is Make -> putExtra("selected_make", item.name)
                is Brand -> putExtra("selected_brand", item.name)
                is Model -> putExtra("selected_model", item.name)
            }
        }

        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }
}
