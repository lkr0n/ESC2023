package de.louiskronberg.esc

import androidx.annotation.DrawableRes

class Countries {
    data class Country(
        val name: String,
        @DrawableRes
        val image: Int,
        val artist: String,
        val song: String,
    )

     companion object {

         val countries = arrayOf(
             Country(
                 "Norway",
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
                 "Serbia",
                 R.drawable.serbia,
                 "Luke Black",
                 "Samo mi se spava (Само ми се спава)"
             ),
             Country(
                 "Latvia",
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
                 "Ireland",
                 R.drawable.ireland,
                 "Wild Youth",
                 "We Are One"
             ),
             Country(
                 "Croatia",
                 R.drawable.croatia,
                 "Let 3",
                 "Mama ŠČ!"
             ),
             Country(
                 "Switzerland",
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
                 "Moldova",
                 R.drawable.moldova,
                 "Pasha Parfeni",
                 "Soarele și luna"
             ),
             Country(
                 "Sweden",
                 R.drawable.sweden,
                 "Loreen",
                 "Tattoo"
             ),
             Country(
                 "Azerbaijan",
                 R.drawable.azerbaijan,
                 "TuralTuranX",
                 "Tell Me More"
             ),
             Country(
                 "Czech Republic",
                 R.drawable.czechrepublic,
                 "Vesna",
                 "My Sister's Crown"
             ),
             Country(
                 "Netherlands",
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
                 "Denmark",
                 R.drawable.denmark,
                 "Reiley",
                 "Breaking My Heart"
             ),
             Country(
                 "Armenia",
                 R.drawable.armenia,
                 "Brunette",
                 "Future Lover"
             ),
             Country(
                 "Romania",
                 R.drawable.romania,
                 "Theodor Andrei",
                 "D.G.T. (Off and On)"
             ),
             Country(
                 "Estonia",
                 R.drawable.estonia,
                 "Alika",
                 "Bridges"
             ),
             Country(
                 "Belgium",
                 R.drawable.belgium,
                 "Gustaph",
                 "Because of You"
             ),
             Country(
                 "Cyprus",
                 R.drawable.cyprus,
                 "Andrew Lambrou",
                 "Break a Broken Heart"
             ),
             Country(
                 "Iceland",
                 R.drawable.iceland,
                 "Diljá",
                 "Power"
             ),
             Country(
                 "Greece",
                 R.drawable.greece,
                 "Victor Vernicos",
                 "What They Say"
             ),
             Country(
                 "Poland",
                 R.drawable.poland,
                 "Blanka",
                 "Solo"
             ),
             Country(
                 "Slovenia",
                 R.drawable.slovenia,
                 "Joker Out",
                 "Carpe Diem"
             ),
             Country(
                 "Georgia",
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
                 "Austria",
                 R.drawable.austria,
                 "Teya and Salena",
                 "Who the Hell Is Edgar?"
             ),
             Country(
                 "Albania",
                 R.drawable.albania,
                 "Albina and Familja Kelmendi",
                 "Duje"
             ),
             Country(
                 "Lithuania",
                 R.drawable.lithuania,
                 "Monika Linkytė",
                 "Stay"
             ),
             Country(
                 "Australia",
                 R.drawable.australia,
                 "Voyager",
                 "Promise"
             ),
             Country(
                 "France",
                 R.drawable.france,
                 "La Zarra",
                 "Évidemment"
             ),
             Country(
                 "Germany",
                 R.drawable.germany,
                 "Lord of the Lost",
                 "Blood & Glitter"
             ),
             Country(
                 "Italy",
                 R.drawable.italy,
                 "Marco Mengoni",
                 "Due vite"
             ),
             Country(
                 "Spain",
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
                 "United Kingdom",
                 R.drawable.unitedkingdom,
                 "Mae Muller",
                 "I Wrote a Song"
             ),
         )
     }
}