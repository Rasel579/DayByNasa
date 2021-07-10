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
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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
        @RequiresApi(Build.VERSION_CODES.O)
        private val DATE = LocalDateTime.now().minusDays(2).format(DateTimeFormatter.ofPattern("yyyy/MM/dd")).toString()
        @RequiresApi(Build.VERSION_CODES.O)
        fun setData(position: Int) {
            when(position){
                0 -> {
                    itemView.nasa_image.load(marsData?.photos?.get(0)?.srcImg?.let { parseURL(it) })
                    itemView.first_swipe_indicator.setImageResource(R.drawable.drawable_swipe_indicator_active)
                }
                1 -> {
                    itemView.nasa_image.load(getImageUrl(earthData?.get(0)?.image, DATE))
                    itemView.second_swipe_indicator.setImageResource(R.drawable.drawable_swipe_indicator_active)
                }
                2 -> {
                    itemView.nasa_image.load(MOON_PIC_URL)
                    itemView.third_swipe_indicator.setImageResource(R.drawable.drawable_swipe_indicator_active)
                }
            }
        }
    }
}


