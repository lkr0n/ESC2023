package de.louiskronberg.esc2025

import android.graphics.Color
import androidx.annotation.DrawableRes

class Countries {
    data class Country(
        val name: String,
        @DrawableRes
        val image: Int,
        val artist: String,
        val song: String,
        var locked: Boolean = true,
        var backgroundColor: Int = Color.WHITE,
        var score: Int = 0
    )

     companion object {

         val countries = arrayOf(
             Country(
                 "Deutschland",
                 R.drawable.germany,
                 "Isaak",
                 "Always on the Run"
             ),
             Country(
                 "Spanien",
                 R.drawable.spain,
                 "Nebulossa",
                 "Zorra"
             ),
             Country(
                 "Großbritannien",
                 R.drawable.unitedkingdom,
                 "Olly Alexander",
                 "Dizzy"
             ),
             Country(
                 "Italien",
                 R.drawable.italy,
                 "Angelina Mango",
                 "La noia"
             ),
             Country(
                 "Schweiz",
                 R.drawable.switzerland,
                 "Nemo",
                 "The Code"
             ),
             Country(
                 "Frankreich",
                 R.drawable.france,
                 "Slimane",
                 "Mon amour"
             ),
         )
     }
}