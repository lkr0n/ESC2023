package de.louiskronberg.esc

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import de.louiskronberg.esc.Countries.Country
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

    private var lock = true
    private var score: Api.Companion.ScoreResponse? = null

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
        holder.imageView.setImageResource(holder.country.image)
        holder.itemView.setOnClickListener {
            onClick(country)
        }

        holder.itemView.setBackgroundColor(Color.WHITE)

        if (lock) {
            holder.textView.setTextColor(Color.GRAY)
        } else {
            holder.textView.setTextColor(Color.BLACK)
        }

        if (score != null) {
            if (score!!.detailed[country.name] == 3) {
               holder.itemView.setBackgroundColor(Color.GREEN)
            }
            if (score!!.detailed[country.name] == 2) {
                holder.itemView.setBackgroundColor(Color.YELLOW)
            }
            if (score!!.detailed[country.name] == 1) {
                holder.itemView.setBackgroundColor(Color.parseColor("#FFA500"))
            }
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
    }

    override fun onRowSelected(myViewHolder: ViewHolder?) {
        myViewHolder?.itemView?.setBackgroundColor(Color.GRAY)
    }

    override fun onRowClear(myViewHolder: ViewHolder?) {
        myViewHolder?.itemView?.setBackgroundColor(Color.WHITE)
    }

    suspend fun saveRanking() {
        val account = GoogleSignIn.getLastSignedInAccount(context)
        val ranking = Api.Companion.RankingResponse(dataSet.map { it.name })
        Api.saveRanking(context.getString(R.string.api_url), account!!.idToken!!, ranking)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun disable() {
        lock = true
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun enable() {
        lock = false
        notifyDataSetChanged()
    }

    fun getLock() : Boolean {
        return lock
    }

    @SuppressLint("NotifyDataSetChanged")
    fun removeScore() {
        this.score = null
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setScore(score: Api.Companion.ScoreResponse) {
        this.score = score
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun getScore() : Api.Companion.ScoreResponse? {
        return this.score
    }
}
