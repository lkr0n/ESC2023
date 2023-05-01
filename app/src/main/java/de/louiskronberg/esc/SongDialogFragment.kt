package de.louiskronberg.esc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SongDialogFragment(
    private val country: Countries.Country
) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog!!.window!!.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        dialog!!.window!!.setDimAmount(0.75f)
        return inflater.inflate(R.layout.bottom_sheet_dialog, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textView: TextView = view.findViewById(R.id.bottom_text)
        textView.text = getString(R.string.country_description, country.artist, country.song)
    }
    companion object {
        const val TAG: String = "SongDialogFragment"
    }
}
