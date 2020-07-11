package com.jefflogic.cherchezlafilm

// FilmItem is used in:
// - MainActivity
// - FavouriteActivity
data class FilmItem(
     val imageRes  : Int
    ,val textRes   : Int
    ,val noteRes   : Int
    ,val textStr: String
    ,val parent : Int?      = null // reference to parent item position (for favourites)
    ,var like   : Boolean   = false
    ,var comment: String    = ""
)