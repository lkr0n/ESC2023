package de.louiskronberg.esc

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.auth.api.signin.GoogleSignIn
import de.louiskronberg.esc.Countries.Country
import org.json.JSONArray
import org.json.JSONObject
import java.util.Collections


class CountryAdapter(
    private val dataSet: List<Country>,
    private val context: Context,
    private val onClick: (Country) -> Unit
) :
    RecyclerView.Adapter<CountryAdapter.ViewHolder>(),
    ItemMoveCallback.ItemTouchHelperContract {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.country_text)
        val imageView: ImageView = view.findViewById(R.id.country_image)
        lateinit var country: Country
    }

    private var lock = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.country_name, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
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
            for (i in fromPosition until toPosition) {
                Collections.swap(dataSet, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(dataSet, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
        saveRanking()
    }

    override fun onRowSelected(myViewHolder: ViewHolder?) {
        myViewHolder?.itemView?.setBackgroundColor(Color.GRAY)
    }

    override fun onRowClear(myViewHolder: ViewHolder?) {
        myViewHolder?.itemView?.setBackgroundColor(Color.WHITE)
    }

    private fun saveRanking() {
        val volleyQueue = Volley.newRequestQueue(context)
        val url = context.getString(R.string.api_url) + "/ranking"
        val account = GoogleSignIn.getLastSignedInAccount(context)
        val jsonArray = JSONArray(dataSet.map { it.name })
        val json = JSONObject().put("countries", jsonArray)

        val jsonObjectRequest = object : JsonObjectRequest(
            Method.POST,
            url,
            json,
            null,
            { error ->
                Log.i("ERROR", error.toString())
            }
        ) {
            override fun getHeaders(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["Id-Token"] = account!!.idToken!!
                return params
            }
        }

        volleyQueue.add(jsonObjectRequest)
    }

    fun setLock(lock: Boolean) {
        this.lock = lock
    }

    fun getLock(): Boolean {
        return lock
    }
}
