package com.theberdakh.suvchi.data.remote.utils

fun Int.convertToMessage(): String {
   return when(this){
        200 -> "Soraw jetiskenlikli orınlandı"
        404 -> "Bunday maǵlıwmat tabılmadı"
        422 -> "Sorawda nadurıs maǵlıwmatlar bar"
        201 -> "Maǵlıwmat jetiskenlikli jaratıldı"
        500 -> "Serverdegi qátelik"
       else -> "Qátelik kodı: $this"
    }
}

