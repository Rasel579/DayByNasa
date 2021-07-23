package com.app_maker.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.transition.*
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.text.buildSpannedString
import coil.api.clear
import coil.api.load
import com.app_maker.R
import com.app_maker.databinding.MainFragmentBinding
import com.app_maker.extensions.Navigation
import com.app_maker.models.rest.NasaPictureDTO
import com.app_maker.parseVideo
import com.app_maker.view_models.AppState
import com.app_maker.view_models.MainViewModel
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kotlinx.android.synthetic.main.bottomsheet_layout.*
import kotlinx.android.synthetic.main.main_fragment.*
import java.text.SimpleDateFormat
import java.util.*

class MainFragment : Fragment(){
    private lateinit var viewModel: MainViewModel
    private var data : NasaPictureDTO ?= null
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private lateinit var binding : MainFragmentBinding
    private var day = 0
    private var datePrevDay: String = DATE
    private lateinit var youtubePlayer: YouTubePlayer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.liveData.observe(viewLifecycleOwner, { renderData(it as AppState)})
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.loadDataFromApi(DATE)
        searchOnWikiListener()
        setBottomAppBar(view)
        view?.let { setBottomSheetBehaviour(it.findViewById(R.id.bottom_sheet_container)) }
        onDayClickListener(binding.prevDay)
        onDayClickListener(binding.currentDay)
        lifecycle.addObserver(binding.videoOfTheDay)
        video_of_the_day.addYouTubePlayerListener(object : AbstractYouTubePlayerListener(){
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youtubePlayer = youTubePlayer
                youtubePlayer.cueVideo("0", 0f)
            }
        })
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.navigation_drawer_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.home_app_bar ->{
                Navigation.navigate(this, R.id.container, ViewPagerFragment.newInstance())
            }
            android.R.id.home -> activity?.let {
                BottomNavigationDrawerFragment().show(it.supportFragmentManager, TAG)
            }
            R.id.app_bar_fav ->{
                Navigation.navigate(this, R.id.container, NasaNotesFragment.newInstance())
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setBottomSheetBehaviour(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_DRAGGING
        bottomSheet.setOnClickListener{
            isBottomSheetExpanded = !isBottomSheetExpanded
            when(isBottomSheetExpanded){
                true -> bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                false -> bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun onDayClickListener(prevDay: Chip)= with(binding) {
        prevDay.setOnClickListener{
            youtubePlayer.pause()
            if(prevDay == currentDay){
                viewModel.loadDataFromApi(DATE)
                datePrevDay = DATE
                day = 0
            } else{
                day++
                datePrevDay = SimpleDateFormat("yyyy-MM-dd").format( Date().time.minus(day* DAY_IN_MILLIS))
                viewModel.loadDataFromApi(datePrevDay)
            }
        }
    }
    private  fun setBottomAppBar(view: View?)= with(binding){
        val context = activity as MainActivity
        context.setSupportActionBar(binding.bottomAppBar)
        setHasOptionsMenu(true)
        fab.setOnClickListener{
            if (isMain){
                isMain = false
                binding.bottomAppBar.navigationIcon = null
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_search))
                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_app)
            } else {
                isMain = true
                binding.bottomAppBar.navigationIcon =ContextCompat.getDrawable(context, R.drawable.ic_hamburger_menu_bottom_bar)
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_plus_fab))
                binding.bottomAppBar.replaceMenu(R.menu.navigation_drawer_menu)
            }
        }
    }

    private fun renderData(appState: AppState) {
        when(appState){
            is AppState.Success -> {
                TransitionManager.beginDelayedTransition(loading_page, Fade().apply { duration = 2000 })
                binding.loadingPage.visibility = View.GONE
               data = appState.dataFromNasa
                showMedia(data)
                val span = SpannableString(data?.title).apply {
                    setSpan(ForegroundColorSpan(Color.RED), 0,7,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
                bottom_sheet_description_header.setText(span, TextView.BufferType.SPANNABLE)
                bottom_sheet_description.text = data?.explanation
            }
            is AppState.Loading ->{
                TransitionManager.beginDelayedTransition(loading_page, Fade(Fade.MODE_IN))
                binding.loadingPage.visibility = View.VISIBLE
            }
            is AppState.Errors -> appState.error
        }

    }

    private fun showMedia(data: NasaPictureDTO?) {
        when(data?.urlPicture?.substring(0,18)){
           LINK_ON_YOUTUBE -> {
               binding.pictureOfTheDay.isClickable = false
               TransitionManager.beginDelayedTransition(main, Slide(Gravity.END))
               binding.pictureOfTheDay.clear()
               binding.pictureOfTheDay.visibility = View.GONE
               binding.videoOfTheDay.visibility = View.VISIBLE
               youtubePlayer.cueVideo(parseVideo(data.urlPicture), 0f)
               binding.playPauseCircle.setOnClickListener{
                   isPlayingVideo = !isPlayingVideo
                   when(isPlayingVideo){
                       true -> youtubePlayer.pause()
                       false -> youtubePlayer.play()
                   }
               }
           }
           LINK_ON_JPG -> {
               TransitionManager.beginDelayedTransition(main, Slide(Gravity.END))
               binding.pictureOfTheDay.visibility = View.VISIBLE
               binding.videoOfTheDay.visibility = View.GONE
               binding.pictureOfTheDay.load(data.urlPicture) {
                   crossfade(true)
                   crossfade(100)
               }
              binding.pictureOfTheDay.setOnClickListener(ExpandedListener(binding))
           }
        }
    }

    private fun searchOnWikiListener()= with(binding) {
        inputLayout.setEndIconOnClickListener{
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://en.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
            })
        }
    }

    inner class ExpandedListener(private val binding: MainFragmentBinding) : View.OnClickListener {
        override fun onClick(view: View?) {
            isExpanded = !isExpanded
            TransitionManager.beginDelayedTransition(
                binding.main, TransitionSet()
                    .addTransition(ChangeBounds())
                    .addTransition(ChangeImageTransform())
            )
            val params: ViewGroup.LayoutParams? =
                binding.pictureOfTheDay.layoutParams
            params?.height = if (isExpanded) {
                ViewGroup.LayoutParams.MATCH_PARENT
            } else {
                ViewGroup.LayoutParams.WRAP_CONTENT
            }
            binding.pictureOfTheDay.layoutParams = params
            binding.pictureOfTheDay.scaleType = if (isExpanded) {
                ImageView.ScaleType.CENTER_CROP
            } else {
                ImageView.ScaleType.FIT_CENTER
            }
        }

    }

    companion object {
        fun newInstance() = MainFragment()
        const val sharedPreferenceName = "isSecondaryTheme"
        const val isSecondaryTheme = "isSecondaryTheme"
        private var isMain = true
        private const val TAG = "Bottom_Sheet"
        private const val LINK_ON_YOUTUBE = "https://www.youtub"
        private const val LINK_ON_JPG =  "https://apod.nasa."
        private const val DAY_IN_MILLIS = 86400000
        private var isPlayingVideo = true
        private var isExpanded = false
        private var isBottomSheetExpanded = false
        @SuppressLint("SimpleDateFormat")
        private val DATE = SimpleDateFormat("yyyy-MM-dd").format(Date())
    }

}


