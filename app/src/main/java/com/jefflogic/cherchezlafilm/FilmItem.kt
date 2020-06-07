package com.jefflogic.cherchezlafilm

data class FilmItem(
     val image  : Int
    ,val text   : Int
    ,val textStr: String
    ,val note   : Int
    ,val parent : Int?      = null // reference to parent item position (for favourites)
    ,var like   : Boolean   = false
    ,var comment: String    = ""
) {
    //var like   : Boolean? = null
    //var comment: String?  = null
//    var view: View? = null
//    var color : Int? = null
//    var selected: Boolean? = null
}

