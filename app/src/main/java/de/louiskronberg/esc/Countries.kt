package de.louiskronberg.esc

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
                 "Schweden",
                 R.drawable.sweden,
                 "Marcus & Martinus",
                 "Unforgettable"
             ),
             Country(
                 "Ukraine",
                 R.drawable.ukraine,
                 "Alyona Alyona und Jerry Heil",
                 "Teresa & Maria"
             ),
             Country(
                 "Deutschland",
                 R.drawable.germany,
                 "",
                 "Always on the Run"
             ),
             Country(
                 "Luxemburg",
                 R.drawable.luxembourg,
                 "Tali",
                 "Fighter"
             ),
             Country(
                 "Niederlande",
                 R.drawable.netherlands,
                 "Joost Klein",
                 "Europapa"
             ),
             Country(
                 "Israel",
                 R.drawable.israel,
                 "Eden Golan",
                 "Hurricane"
             ),
             Country(
                 "Litauen",
                 R.drawable.lithuania,
                 "Silvester Belt",
                 "Luktelk"
             ),
             Country(
                 "Spanien",
                 R.drawable.spain,
                 "Nebulossa",
                 "Zorra"
             ),
             Country(
                 "Estland",
                 R.drawable.estonia,
                 "5miinust und Puuluup",
                 "(Nendest) narkootikumidest ei tea me (küll) midagi"
             ),
             Country(
                 "Irland",
                 R.drawable.ireland,
                 "Bambie Thug",
                 "Doomsday Blue"
             ),
             Country(
                 "Lettland",
                 R.drawable.latvia,
                 "Dons",
                 "Hollow"
             ),
             Country(
                 "Griechenland",
                 R.drawable.greece,
                 "Marina Satti",
                 "Zari"
             ),
             Country(
                 "Großbritannien",
                 R.drawable.unitedkingdom,
                 "Olly Alexander",
                 "Dizzy"
             ),
             Country(
                 "Norwegen",
                 R.drawable.norway,
                 "Gåte",
                 "Ulveham"
             ),
             Country(
                 "Italien",
                 R.drawable.italy,
                 "Angelina Mango",
                 "La noia"
             ),
             Country(
                 "Serbien",
                 R.drawable.serbia,
                 "Teya Dora",
                 "Ramonda"
             ),
             Country(
                 "Finland",
                 R.drawable.finland,
                 "Windows95man",
                 "No Rules!"
             ),
             Country(
                 "Portugal",
                 R.drawable.portugal,
                 "Iolanda",
                 "Grito"
             ),
             Country(
                 "Armenien",
                 R.drawable.armenia,
                 "Ladaniva",
                 "Jako"
             ),
             Country(
                 "Zypern",
                 R.drawable.cyprus,
                 "Silia Kapsis",
                 "Liar"
             ),
             Country(
                 "Schweiz",
                 R.drawable.switzerland,
                 "Nemo",
                 "The Code"
             ),
             Country(
                 "Slovenien",
                 R.drawable.slovenia,
                 "Raiven",
                 "Veronika"
             ),
             Country(
                 "Kroatien",
                 R.drawable.croatia,
                 "Baby Lasagna",
                 "Rim Tim Tagi Dim"
             ),
             Country(
                 "Georgien",
                 R.drawable.georgia,
                 "Nutsa Buzaladze",
                 "Firefighter"
             ),
             Country(
                 "Frankreich",
                 R.drawable.france,
                 "Slimane",
                 "Mon amour"
             ),
             Country(
                 "Österreich",
                 R.drawable.austria,
                 "Kaleen",
                 "We Will Rave"
             ),
         )
     }
}