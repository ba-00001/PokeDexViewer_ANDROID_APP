package com.example.randomimagesp3p7

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide



class ApiAdapter(private val apiList: MutableList<String>) :
    RecyclerView.Adapter<ApiAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val apiImage: ImageView
        val apiName: TextView
        val apiURL: TextView
        var toast: Toast? = null




        init {
            apiImage = view.findViewById(R.id.api_image)
            apiName = view.findViewById(R.id.textView1)
            apiURL = view.findViewById(R.id.textView2)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_item,
            parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = apiList[position]
        val imageUrl = parseImageUrl(currentItem)
        holder.apiURL.text = "API URL: "+imageUrl
        val name = parseName(currentItem)
        holder.apiName.text = "Pokemon name: "+ name + "\nPokemon #: " + (position+1)


        Glide.with(holder.itemView)
            .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${position+1}.png")
            .placeholder(R.drawable.cat) // Use your own placeholder image here
            .centerCrop()
            .into(holder.apiImage)

        holder.apiImage.setOnClickListener {
            if (holder.toast != null) {
                holder.toast?.cancel()
            }
            holder.toast = Toast.makeText(
                holder.itemView.context,
                "Pokemon at position $position clicked",
                Toast.LENGTH_SHORT
            )
            holder.toast?.show()
        }
    }

    override fun getItemCount(): Int {
        return apiList.size
    }

    private fun parseImageUrl(data: String): String {
        // Assuming the format of data is "Name: [name], URL: [url]"
        val parts = data.split(", ")
        val urlPart = parts.find { it.startsWith("URL:") }
        return urlPart?.substringAfter("URL: ") ?: ""
    }

    private fun parseName(data: String): String {
        // Assuming the format of data is "Name: [name], URL: [url]"
        val parts = data.split(", ")
        val urlPart = parts.find { it.startsWith("Name:") }
        return urlPart?.substringAfter("Name: ") ?: ""
    }
}
