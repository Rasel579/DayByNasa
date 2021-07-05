package com.app_maker.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import coil.api.load
import com.app_maker.MainActivity
import com.app_maker.R
import com.app_maker.databinding.MainFragmentBinding
import com.app_maker.models.rest.NasaPictureDTO
import com.app_maker.view_models.AppState
import com.app_maker.view_models.MainViewModel
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.bottomsheet_layout.*
import kotlinx.android.synthetic.main.main_fragment.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var data : NasaPictureDTO
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private lateinit var binding : MainFragmentBinding
    var datePrevDay: String = DATE

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.liveData.observe(viewLifecycleOwner, { renderData(it as AppState)})
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.loadDataFromApi(DATE)
        searchOnWikiListener()
        setBottomAppBar(view)
        view?.let { setBottomSheetBehaviour(it.findViewById(R.id.bottom_sheet_container)) }
        onDayClickListener(binding.prevDay)
        onDayClickListener(binding.currentDay)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.navigation_drawer_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.app_bar_fav -> Toast.makeText(context, getString(R.string.Menu_Toast_Text), Toast.LENGTH_SHORT).show()
            R.id.app_bar_search ->Toast.makeText(context, getString(R.string.Menu_Toast_Text), Toast.LENGTH_SHORT).show()
            android.R.id.home -> activity?.let {
                BottomNavigationDrawerFragment().show(it.supportFragmentManager, TAG)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setBottomSheetBehaviour(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onDayClickListener(prevDay: Chip)= with(binding) {
        prevDay.setOnClickListener{
            if(prevDay == currentDay){
                viewModel.loadDataFromApi(DATE)
                datePrevDay = DATE
            } else{
                datePrevDay = LocalDate.parse(datePrevDay).minusDays(1).toString()
                viewModel.loadDataFromApi(datePrevDay)
            }
        }
    }
    private  fun setBottomAppBar(view: View?)= with(binding){
        val context = activity as MainActivity
        context.setSupportActionBar(bottom_app_bar)
        setHasOptionsMenu(true)
        fab.setOnClickListener{
            if (isMain){
                isMain = false
                bottom_app_bar.navigationIcon = null
                bottom_app_bar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_search))
                bottom_app_bar.replaceMenu(R.menu.menu_bottom_app)
            } else {
                isMain = true
                bottom_app_bar.navigationIcon =ContextCompat.getDrawable(context, R.drawable.ic_hamburger_menu_bottom_bar)
                bottom_app_bar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_plus_fab))
                bottom_app_bar.replaceMenu(R.menu.navigation_drawer_menu)
            }
        }
    }

    private fun renderData(appState: AppState) {
        when(appState){
            is AppState.Success -> {
               data = appState.dataFromNasa
                picture_of_the_day.load(data.urlPicture){
                    crossfade(true)
                }
                bottom_sheet_description_header.text = data.title
                bottom_sheet_description.text = data.explanation
            }
            is AppState.Loading -> appState
            is AppState.Errors -> appState.error
        }

    }

    private fun searchOnWikiListener()= with(binding) {
        inputLayout.setEndIconOnClickListener{
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://en.wikipedia.org/wiki/${input_edit_text.text.toString()}")
            })
        }
    }

    companion object {
        fun newInstance() = MainFragment()
        const val sharedPreferenceName = "isSecondaryTheme"
        const val isSecondaryTheme = "isSecondaryTheme"
        private var isMain = true
        private const val TAG = "Bottom_Sheet"
        @SuppressLint("SimpleDateFormat")
        private val DATE = SimpleDateFormat("yyyy-MM-dd").format(Date())
    }
}