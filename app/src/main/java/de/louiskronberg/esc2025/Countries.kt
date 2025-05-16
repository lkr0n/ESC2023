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
                 "Norwegen",
                 R.drawable.norway,
                 "Kyle Alessandro",
                 "Lighter"
             ),
             Country(
                 "Luxemburg",
                 R.drawable.luxembourg,
                 "Laura Thorn",
                 "La poupée monte le son"
             ),
             Country(
                 "Estland",
                 R.drawable.estonia,
                 "Tommy Cash",
                 "Espresso Macchiato"
             ),
             Country(
                 "Israel",
                 R.drawable.israel,
                 "Yuval Raphael",
                 "New Day Will Rise"
             ),
             Country(
                 "Litauen",
                 R.drawable.lithuania,
                 "Katarsis",
                 "Tavo akys"
             ),
             Country(
                 "Spanien",
                 R.drawable.spain,
                 "Melody",
                 "Esa diva"
             ),
             Country(
                 "Ukraine",
                 R.drawable.ukraine,
                 "Ziferblat",
                 "Bird of Pray"
             ),
             Country(
                 "Vereinigtes Königreich",
                 R.drawable.unitedkingdom,
                 "Remember Monday",
                 "What the Hell Just Happened?"
             ),
             Country(
                 "Österreich",
                 R.drawable.austria,
                 "JJ",
                 "Wasted Love"
             ),
             Country(
                 "Island",
                 R.drawable.iceland,
                 "Væb",
                 "Róa"
             ),
             Country(
                 "Lettland",
                 R.drawable.latvia,
                 "Tautumeitas",
                 "Bur man laimi"
             ),
             Country(
                 "Niederlande",
                 R.drawable.netherlands,
                 "Claude",
                 "C'est la vie"
             ),
             Country(
                 "Finnland",
                 R.drawable.finland,
                 "Erika Vikman",
                 "Ich komme"
             ),
             Country(
                 "Italien",
                 R.drawable.italy,
                 "Lucio Corsi",
                 "Volevo essere un duro"
             ),
             Country(
                 "Polen",
                 R.drawable.poland,
                 "Justyna Steczkowska",
                 "Gaja"
             ),
             Country(
                 "Deutschland",
                 R.drawable.germany,
                 "Abor & Tynna",
                 "Baller"
             ),
             Country(
                 "Griechenland",
                 R.drawable.greece,
                 "Klavdia",
                 "Asteromata"
             ),
             Country(
                 "Armenien",
                 R.drawable.armenia,
                 "PARG",
                 "Survivor"
             ),
             Country(
                 "Schweiz",
                 R.drawable.switzerland,
                 "Zoë Më",
                 "Voyage"
             ),
             Country(
                 "Malta",
                 R.drawable.malta,
                 "Miriana Conte",
                 "Serving"
             ),
             Country(
                 "Portugal",
                 R.drawable.portugal,
                 "NAPA",
                 "Deslocado"
             ),
             Country(
                 "Dänemark",
                 R.drawable.denmark,
                 "Sissal",
                 "Hallucination"
             ),
             Country(
                 "Schweden",
                 R.drawable.sweden,
                 "KAJ",
                 "Bara bada bastu"
             ),
             Country(
                 "Frankreich",
                 R.drawable.france,
                 "Louane",
                 "Maman"
             ),
             Country(
                 "San Marino",
                 R.drawable.sanmarino,
                 "Gabry Ponte",
                 "Tutta l'Italia"
             ),
             Country(
                 "Albanien",
                 R.drawable.albania,
                 "Shkodra Elektronike",
                 "Zjerm"
             )
         )
     }
}