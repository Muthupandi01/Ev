package com.example.heptotech.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.heptotech.R
import com.example.heptotech.bean_dataclass.User

class UserAdapter(
    private val userList: List<User>,
    private val onItemClick: (User) -> Unit // Click listener function
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    // ViewHolder class to hold the view for each list item
    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userImage: ImageView = itemView.findViewById(R.id.user_img)
        private val username: TextView = itemView.findViewById(R.id.username_txt)
        private val relationStatus: TextView = itemView.findViewById(R.id.relationstatus)
        private val typeCard: TextView = itemView.findViewById(R.id.typecard)
        private val carImage: ImageView = itemView.findViewById(R.id.imgtick_recycle)

        // Bind data to the views
        fun bind(user: User, onItemClick: (User) -> Unit) {
            userImage.setImageResource(user.userImageResId)
            username.text = user.username
            relationStatus.text = user.relationStatus
            typeCard.text = user.cardType
            carImage.setImageResource(user.userImage)

            // Set the click listener for the entire itemView
            carImage.setOnClickListener {
                onItemClick(user)
            }
        }
    }

    // Inflate the layout for each item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.usercard_rec, parent, false) // Use your layout file name
        return UserViewHolder(view)
    }

    // Bind data to the ViewHolder
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.bind(user, onItemClick)
    }

    // Return the total number of items
    override fun getItemCount(): Int {
        return userList.size
    }
}
