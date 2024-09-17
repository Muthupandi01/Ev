package com.example.heptotech.adapters





import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.heptotech.itemlistener.ItemListenervehicle
import com.example.heptotech.R
import com.example.heptotech.actvity_view.ViewVehicles
import com.example.heptotech.bean_dataclass.Vehicle
import com.squareup.picasso.Picasso

class VehicleAdapter(
    private val vehicleList: MutableList<Vehicle>,
    private val itemListener: ItemListenervehicle
) : RecyclerView.Adapter<VehicleAdapter.VehicleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.vehicle_card, parent, false)
        return VehicleViewHolder(view)
    }

    override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {
        val vehicle = vehicleList[position]
        holder.bind(vehicle, position)
    }

    override fun getItemCount() = vehicleList.size

    fun addVehicle(vehicle: Vehicle) {
        vehicleList.add(vehicle)
        notifyItemInserted(vehicleList.size - 1)
    }

    fun updateVehicleList(newList: List<Vehicle>) {
        vehicleList.clear()
        vehicleList.addAll(newList)
        notifyDataSetChanged()
    }

    fun updateVehicleAtPosition(position: Int, updatedVehicle: Vehicle) {
        if (position >= 0 && position < vehicleList.size) {
            vehicleList[position] = updatedVehicle
            notifyItemChanged(position)
        }
    }

    inner class VehicleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cardimg: CardView = itemView.findViewById(R.id.cardimg)
        private val modelTextView: TextView = itemView.findViewById(R.id.model)
        private val licensePlateTextView: TextView = itemView.findViewById(R.id.licene)
        private val imageView: ImageView = itemView.findViewById(R.id.showimg)
        private val deleteCard: ImageView = itemView.findViewById(R.id.delete_card)
        private val editCard: ImageView = itemView.findViewById(R.id.edit_card)

        fun bind(vehicle: Vehicle, position: Int) {
            modelTextView.text = vehicle.brand
            licensePlateTextView.text = "Licence Plate : ${vehicle.licensePlate}"
            if (!vehicle.imageUrl.isNullOrEmpty()) {
                Picasso.get().load(vehicle.imageUrl).placeholder(R.drawable.picasoloading).into(imageView)
            } else {
                imageView.setImageResource(R.drawable.image_not_available_ev) // Provide a default image
            }

            editCard.setOnClickListener {
                itemListener.onItemClicked(position, vehicle)
            }

            deleteCard.setOnClickListener {
                itemListener.onItemDeleted(position)
            }



            cardimg.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, ViewVehicles::class.java)
                // Add any extras you need to pass with the intent here
                context.startActivity(intent)

            }
        }
    }
}
