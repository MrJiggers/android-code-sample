package com.marmelade.android.spacex.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import com.marmelade.android.spacex.R
import com.marmelade.android.spacex.ui.base.BaseActivity
import com.marmelade.android.spacex.databinding.ActivityMainBinding
import com.marmelade.android.spacex.ui.company.CompanyFragment
import com.marmelade.android.spacex.ui.rockets.RocketsFragment


class MainActivity : BaseActivity<MainActivityViewModel, ActivityMainBinding>() {
    companion object {
        fun getStartIntent(context: Context): Intent = Intent(context, MainActivity::class.java)
    }

    override val vmClassToken: Class<MainActivityViewModel> = MainActivityViewModel::class.java
    private var setOnFilterClickListener: FilterOnClickListener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding?.mainToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        setUpDrawer()

        viewModel.getFilterState().observe(this, {
            val filterMenuItem = binding?.mainToolbar?.menu?.findItem(R.id.mainFilter)
            if (!it.enabledFilter)
                filterMenuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_filter_alt_black_36dp_inactive)
            else filterMenuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_filter_alt_black_36dp_active)
        })
    }

    override fun setViewBinding(inflater: LayoutInflater): ActivityMainBinding =
            ActivityMainBinding.inflate(layoutInflater)

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mainOpenDrawer -> {
                val drawerLayout = binding?.mainDrawer
                if (drawerLayout?.isDrawerOpen(GravityCompat.END) == true)
                    drawerLayout.closeDrawer(GravityCompat.END)
                else drawerLayout?.openDrawer(GravityCompat.END)
                true
            }
            R.id.mainFilter -> {
                setOnFilterClickListener?.filterOnClick(item)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setUpDrawer() {
        val toggle = ActionBarDrawerToggle(
                this,
                binding?.mainDrawer,
                binding?.mainToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        )
        binding?.mainDrawer?.addDrawerListener(toggle)
        val header = binding?.mainNavigation?.inflateHeaderView(R.layout.view_main_drawer_header)
        val headerImage: ImageView? = header?.findViewById(R.id.mainDrawerHeaderImage)
        headerImage?.setImageResource(R.drawable.ic_spacex_logo_black)
        binding?.mainNavigation?.setNavigationItemSelectedListener { menuItem ->
            openFragment(menuItem)
            true
        }
        openFragment()
    }

    private fun openFragment(menuItem: MenuItem? = null) {
        val fragment = when (menuItem?.itemId) {
            R.id.company -> CompanyFragment.newInstance()
            else -> RocketsFragment.newInstance()
        }
        binding?.mainToolbar?.menu?.findItem(R.id.mainFilter)?.isVisible =
                menuItem?.itemId != R.id.company
        binding?.mainContentPlaceholder?.id?.let {
            supportFragmentManager
                    .beginTransaction()
                    .replace(it, fragment)
                    .commit()
        }
        binding?.mainNavigation?.setCheckedItem(menuItem?.itemId ?: R.id.rockets)
        binding?.mainDrawer?.closeDrawers()
    }

    fun getFilterMenuItem(filterOnClickListener: FilterOnClickListener?) {
        this.setOnFilterClickListener = filterOnClickListener
    }
}