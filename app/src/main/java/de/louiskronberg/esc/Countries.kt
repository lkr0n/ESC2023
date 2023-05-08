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
        var locked: Boolean = false,
        var backgroundColor: Int = Color.WHITE,
        var score: Int = 0
    )

     companion object {

         val countries = arrayOf(
             Country(
                 "Norwegen",
                 R.drawable.norway,
                 "Alessandra",
                 "Queen of Kings"
             ),
             Country(
                 "Malta",
                 R.drawable.malta,
                 "The Busker",
                 "Dance (Our Own Party)"
             ),
             Country(
                 "Serbien",
                 R.drawable.serbia,
                 "Luke Black",
                 "Samo mi se spava (Само ми се спава)"
             ),
             Country(
                 "Lettland",
                 R.drawable.latvia,
                 "Sudden Lights",
                 "Aijā"
             ),
             Country(
                 "Portugal",
                 R.drawable.portugal,
                 "Mimicat",
                 "Ai coração"
             ),
             Country(
                 "Irland",
                 R.drawable.ireland,
                 "Wild Youth",
                 "We Are One"
             ),
             Country(
                 "Kroatien",
                 R.drawable.croatia,
                 "Let 3",
                 "Mama ŠČ!"
             ),
             Country(
                 "Schweiz",
                 R.drawable.switzerland,
                 "Remo Forrer",
                 "Watergun"
             ),
             Country(
                 "Israel",
                 R.drawable.israel,
                 "Noa Kirel",
                 "Unicorn"
             ),
             Country(
                 "Moldavien",
                 R.drawable.moldova,
                 "Pasha Parfeni",
                 "Soarele și luna"
             ),
             Country(
                 "Schweden",
                 R.drawable.sweden,
                 "Loreen",
                 "Tattoo"
             ),
             Country(
                 "Aserbaidschan",
                 R.drawable.azerbaijan,
                 "TuralTuranX",
                 "Tell Me More"
             ),
             Country(
                 "Tschechien",
                 R.drawable.czechrepublic,
                 "Vesna",
                 "My Sister's Crown"
             ),
             Country(
                 "Niederlande",
                 R.drawable.netherlands,
                 "Mia Nicolai and Dion Cooper",
                 "Burning Daylight"
             ),
             Country(
                 "Finland",
                 R.drawable.finland,
                 "Käärijä",
                 "Cha Cha Cha"
             ),
             Country(
                 "Dänemark",
                 R.drawable.denmark,
                 "Reiley",
                 "Breaking My Heart"
             ),
             Country(
                 "Armenien",
                 R.drawable.armenia,
                 "Brunette",
                 "Future Lover"
             ),
             Country(
                 "Rumänien",
                 R.drawable.romania,
                 "Theodor Andrei",
                 "D.G.T. (Off and On)"
             ),
             Country(
                 "Estland",
                 R.drawable.estonia,
                 "Alika",
                 "Bridges"
             ),
             Country(
                 "Belgien",
                 R.drawable.belgium,
                 "Gustaph",
                 "Because of You"
             ),
             Country(
                 "Zypern",
                 R.drawable.cyprus,
                 "Andrew Lambrou",
                 "Break a Broken Heart"
             ),
             Country(
                 "Island",
                 R.drawable.iceland,
                 "Diljá",
                 "Power"
             ),
             Country(
                 "Griechenland",
                 R.drawable.greece,
                 "Victor Vernicos",
                 "What They Say"
             ),
             Country(
                 "Polen",
                 R.drawable.poland,
                 "Blanka",
                 "Solo"
             ),
             Country(
                 "Slovenien",
                 R.drawable.slovenia,
                 "Joker Out",
                 "Carpe Diem"
             ),
             Country(
                 "Georgien",
                 R.drawable.georgia,
                 "Iru",
                 "Echo"
             ),
             Country(
                 "San Marino",
                 R.drawable.sanmarino,
                 "Piqued Jacks",
                 "Like an Animal"
             ),
             Country(
                 "Österreich",
                 R.drawable.austria,
                 "Teya and Salena",
                 "Who the Hell Is Edgar?"
             ),
             Country(
                 "Albanien",
                 R.drawable.albania,
                 "Albina and Familja Kelmendi",
                 "Duje"
             ),
             Country(
                 "Litauen",
                 R.drawable.lithuania,
                 "Monika Linkytė",
                 "Stay"
             ),
             Country(
                 "Australien",
                 R.drawable.australia,
                 "Voyager",
                 "Promise"
             ),
             Country(
                 "Frankreich",
                 R.drawable.france,
                 "La Zarra",
                 "Évidemment"
             ),
             Country(
                 "Deutschland",
                 R.drawable.germany,
                 "Lord of the Lost",
                 "Blood & Glitter"
             ),
             Country(
                 "Italien",
                 R.drawable.italy,
                 "Marco Mengoni",
                 "Due vite"
             ),
             Country(
                 "Spanien",
                 R.drawable.spain,
                 "Blanca Paloma",
                 "Eaea"
             ),
             Country(
                 "Ukraine",
                 R.drawable.ukraine,
                 "Tvorchi",
                 "Heart of Steel"
             ),
             Country(
                 "England",
                 R.drawable.unitedkingdom,
                 "Mae Muller",
                 "I Wrote a Song"
             ),
         )
     }
}