package com.example.heptotech.adapters

import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.heptotech.R
import com.example.heptotech.bean_dataclass.Brand
import kotlinx.coroutines.*

class BrandAdapterDevice(
    private var brandList: List<Brand>,
    private val button: Button,
    private val clickListener: (Brand?) -> Unit // Allow null to signify no selection
) : RecyclerView.Adapter<BrandAdapterDevice.BrandViewHolder>() {

    private var selectedPosition: Int = RecyclerView.NO_POSITION // Track the selected position
    private var ongoingJob: Job? = null // Track the ongoing coroutine job

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandViewHolder {
        // Inflate the layout for the individual item
        val view = LayoutInflater.from(parent.context).inflate(R.layout.devicecard, parent, false)
        return BrandViewHolder(view)
    }

    override fun onBindViewHolder(holder: BrandViewHolder, position: Int) {
        // Bind the brand data to the view holder
        val brand = brandList[position]
        holder.bind(brand, position)
    }

    override fun getItemCount(): Int = brandList.size

    inner class BrandViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val brandImageView: ImageView = itemView.findViewById(R.id.brand_id_img)
        private val brandTextView: TextView = itemView.findViewById(R.id.brand_id_txt)
        private val tickImageView: ImageView = itemView.findViewById(R.id.imgtick)
        private val txt_pair: TextView = itemView.findViewById(R.id.txt_pair)
        private val subtextview: TextView = itemView.findViewById(R.id.subtextview)

        fun bind(brand: Brand, position: Int) {
            // Set image and text for the brand item
            brandImageView.setImageResource(brand.imageResId)
            brandTextView.text = brand.name

            // Set default state for all items
            tickImageView.visibility = View.GONE
            txt_pair.visibility = View.VISIBLE
            subtextview.visibility = View.GONE

            if (position == selectedPosition) {
                if (selectedPosition == 0) {
                    subtextview.text = "Pairing"
                    tickImageView.setImageResource(R.drawable.times_circle_ev)

                    // Cancel any ongoing job before starting a new one
                    ongoingJob?.cancel()

                    // Launch the coroutine for pairing simulation
                    ongoingJob = GlobalScope.launch(Dispatchers.Main) {
                        delay(1000) // Simulate pairing delay

                        // Step 1: Show "Paired" in blue
                        val pairedText = SpannableString("Paired")
                        pairedText.setSpan(
                            ForegroundColorSpan(ContextCompat.getColor(itemView.context,
                                R.color.blue_common
                            )),
                            0, pairedText.length,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                        subtextview.text = pairedText

                        delay(500) // Additional delay for next update

                        // Step 2: Show "Paired (Syncing 95%)" with colors
                        val fullText = "Paired (Syncing 95%)"
                        val spannableText = SpannableString(fullText)

                        // Set "Paired" to blue
                        spannableText.setSpan(
                            ForegroundColorSpan(ContextCompat.getColor(itemView.context,
                                R.color.blue_common
                            )),
                            0, "Paired".length,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                        )

                        // Set "(Syncing 95%)" to green
                        spannableText.setSpan(
                            ForegroundColorSpan(ContextCompat.getColor(itemView.context,
                                R.color.greentxt
                            )),
                            "Paired".length, fullText.length,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                        )

                        subtextview.text = spannableText

                        // Change the image to a success icon after pairing
                        tickImageView.setImageResource(R.drawable.check_circle_ev) // Replace with your success image resource

                        // Enable the button after pairing is complete
                        button.isEnabled = true
                        button.backgroundTintList = ContextCompat.getColorStateList(itemView.context,
                            R.color.greentxt
                        )
                    }
                } else {
                    subtextview.text = "Unable to sync, device is not vehicle"
                    subtextview.setTextColor(Color.parseColor("#D7FF0000"))
                    tickImageView.setImageResource(R.drawable.close_ev)

                    // Ensure the button is disabled for other selections
                    button.isEnabled = false
                    button.backgroundTintList = ContextCompat.getColorStateList(itemView.context,
                        R.color.hintColor
                    )
                }
                tickImageView.visibility = View.VISIBLE
                txt_pair.visibility = View.GONE
                subtextview.visibility = View.VISIBLE
            }

            itemView.setOnClickListener {
                // Cancel the ongoing job if another item is clicked
                ongoingJob?.cancel()

                selectedPosition = if (selectedPosition == position) {
                    RecyclerView.NO_POSITION // Deselect if the same item is clicked
                } else {
                    position // Select the new item
                }
                notifyDataSetChanged() // Refresh the list to reflect the changes

                // Disable the button immediately when selecting an item (it will be enabled later if it's the 0th position and the pairing is successful)
                button.isEnabled = false
                button.backgroundTintList = ContextCompat.getColorStateList(itemView.context,
                    R.color.hintColor
                )

                // Handle the click event
                val selectedBrand = if (selectedPosition != RecyclerView.NO_POSITION) brand else null
                clickListener(selectedBrand)
            }
        }
    }
}
