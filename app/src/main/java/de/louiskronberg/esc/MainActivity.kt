package de.louiskronberg.esc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import de.louiskronberg.esc.countryDetail.CountryDetailActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // create a view object corresponding to the xml of the same name in 
        // the layout directory. This also sets this view to be the content 
        // of this activity
        setContentView(R.layout.activity_main)

        // find a RecyclerView defined in the xml layout files and populate it
        // with an adapter wrapping the list of countries to be displayed
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view);
        val countryAdapter = CountryAdapter(Countries.countries) { country ->
            val bottom = SongDialogFragment(country)

            bottom.show(supportFragmentManager, SongDialogFragment.TAG)
        }

        // attach ItemMoveCallback to the RecyclerView making the elements of RecyclerView
        // draggable
        val callback = ItemMoveCallback(countryAdapter)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(recyclerView)
        recyclerView.adapter = countryAdapter
    }

}
