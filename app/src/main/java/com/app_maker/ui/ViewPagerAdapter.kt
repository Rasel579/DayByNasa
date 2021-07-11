package com.app_maker.ui

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.app_maker.MOON_PIC_URL
import com.app_maker.R
import com.app_maker.getImageUrl
import com.app_maker.models.rest.MarsMutableApiDTO
import com.app_maker.models.rest.NasaEpicDTO
import com.app_maker.parseURL
import kotlinx.android.synthetic.main.item_layout.view.*
import java.text.SimpleDateFormat
import java.util.*

class ViewPagerAdapter(
    private val earthData: MutableList<NasaEpicDTO>?,
    private val marsData: MarsMutableApiDTO?
) : RecyclerView.Adapter<ViewPagerAdapter.PagerVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerVH {
        return PagerVH(LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: PagerVH, position: Int) {
        holder.setData(position)
    }

    override fun getItemCount(): Int = 3
    inner class PagerVH(itemView : View) : RecyclerView.ViewHolder(itemView) {
        private val DAY_IN_MILLIS = 86400000
        private val DATE = SimpleDateFormat("yyyy/MM/dd").format( Date().time.minus(1* DAY_IN_MILLIS))
        fun setData(position: Int) {
            when(position){
                0 -> {
                    itemView.nasa_image.load(marsData?.photos?.get(0)?.srcImg?.let { parseURL(it) })
                    itemView.first_swipe_indicator.setImageResource(R.drawable.drawable_swipe_indicator_active)
                    itemView.item_main_text.text = "${marsData?.photos?.get(0)?.camera?.get("full_name")} ${marsData?.photos?.get(0)?.earthDate}"
                }
                1 -> {
                    itemView.nasa_image.load(getImageUrl(earthData?.get(0)?.image, DATE))
                    itemView.second_swipe_indicator.setImageResource(R.drawable.drawable_swipe_indicator_active)
                    itemView.item_main_text.text = "${earthData?.get(0)?.caption} ${earthData?.get(0)?.date}"
                    Log.e("Eart",getImageUrl(earthData?.get(0)?.image, DATE))
                }
                2 -> {
                    itemView.nasa_image.load(MOON_PIC_URL)
                    itemView.third_swipe_indicator.setImageResource(R.drawable.drawable_swipe_indicator_active)
                    itemView.item_main_text.text = "lorem ipsum"
                }
            }
        }
    }
}


