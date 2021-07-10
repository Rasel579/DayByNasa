package com.app_maker


const val baseNasaUrl = "https://api.nasa.gov/"
const val EARTH_PIC_URL = "https://images-assets.nasa.gov/image/a-sky-view-of-earth-from-suomi-npp_16611703184_o/a-sky-view-of-earth-from-suomi-npp_16611703184_o~orig.jpg"
const val MOON_PIC_URL = "https://images-assets.nasa.gov/image/PIA10570/PIA10570~orig.jpg"
fun getImageUrl(imageId: String?, date: String) = "https://api.nasa.gov/EPIC/archive/natural/${date}/png/${imageId}.png?api_key=${BuildConfig.NASA_APIKEY}"
const val MARS_IMG_URI = "http://mars.jpl.nasa.gov/msl-raw-images/msss/00001/mcam/0001ML0000001000I1_DXXX.jpg"
fun parseURL(url: String) : String {
    val newUrl = url.subSequence(4, url.length)
    return "https${newUrl}"
}