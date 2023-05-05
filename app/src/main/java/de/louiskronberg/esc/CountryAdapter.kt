package de.louiskronberg.esc

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.Collections
import de.louiskronberg.esc.Countries.Country


class CountryAdapter(
    private val dataSet: List<Country>,
    private val onClick: (Country) -> Unit
) :
    RecyclerView.Adapter<CountryAdapter.ViewHolder>(),
    ItemMoveCallback.ItemTouchHelperContract {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.country_text)
        val imageView: ImageView  = view.findViewById(R.id.country_image)
        lateinit var country: Country;
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.country_name, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val context = holder.itemView.context
        val country = dataSet[position]

        holder.country = country
        holder.textView.text = holder.country.name
        holder.imageView.setImageResource(country.image)
        holder.itemView.setOnClickListener {
            onClick(country)
        }
    }

    override fun onRowMoved(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i  in fromPosition until toPosition) {
                Collections.swap(dataSet, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(dataSet, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onRowSelected(myViewHolder: ViewHolder?) {
        myViewHolder?.itemView?.setBackgroundColor(Color.GRAY)
    }

    override fun onRowClear(myViewHolder: ViewHolder?) {
        myViewHolder?.itemView?.setBackgroundColor(Color.WHITE)
    }
}
