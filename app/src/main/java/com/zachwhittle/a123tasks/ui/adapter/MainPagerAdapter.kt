package com.zachwhittle.a123tasks.ui.adapter

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.zachwhittle.a123tasks.R
import com.zachwhittle.a123tasks.ui.main.ProjectListFragment
import com.zachwhittle.a123tasks.ui.main.TagListFragment
import com.zachwhittle.a123tasks.ui.main.TaskListFragment
import com.zachwhittle.a123tasks.util.Constants
import java.lang.IllegalArgumentException

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class MainPagerAdapter(private val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
    
    private val tabTitles = arrayOf(
        R.string.title_tab0,
        R.string.title_tab1,
        R.string.title_tab2
    )
    
    override fun getItem(position: Int): Fragment {
        return when(position) {
            Constants.POSITION_TASKS    ->      TaskListFragment.newInstance()
            Constants.POSITION_PROJECTS ->      ProjectListFragment.newInstance()
            Constants.POSITIONS_TAGS    ->      TagListFragment.newInstance()
            else                        ->      throw IllegalArgumentException("no tab at position $position")
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(tabTitles[position])
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return 3
    }
}