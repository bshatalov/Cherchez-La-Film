package com.jefflogic.cherchezlafilm

// FilmItem is used in:
// - MainActivity
// - FavouriteActivity
data class FilmItem(
     val image  : Int
    ,val text   : Int
    ,val textStr: String
    ,val note   : Int
    ,val parent : Int?      = null // reference to parent item position (for favourites)
    ,var like   : Boolean   = false
    ,var comment: String    = ""
)