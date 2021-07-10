package com.app_maker.extensions

import android.content.Context
import androidx.fragment.app.Fragment
import com.app_maker.ui.ViewPagerFragment
import kotlinx.android.extensions.LayoutContainer

class Navigation() {
  companion object{
      fun navigate(currentFragment: Fragment, container : Int, fragment: Fragment){
          val manager =  currentFragment.activity?.supportFragmentManager
         manager?.beginTransaction()?.replace(container, fragment)
             ?.addToBackStack("tag")?.commit()
      }
  }
}