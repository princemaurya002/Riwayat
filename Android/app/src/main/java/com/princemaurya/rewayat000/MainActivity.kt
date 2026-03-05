package com.princemaurya.rewayat000

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import androidx.fragment.app.Fragment
import com.princemaurya.rewayat000.ui.create.CreateBottomSheet
import com.princemaurya.rewayat000.ui.grow.GrowFragment
import com.princemaurya.rewayat000.ui.home.HomeFragment
import com.princemaurya.rewayat000.ui.operations.OperationsFragment
import com.princemaurya.rewayat000.ui.shop.ShopFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val toolbar: MaterialToolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { drawerLayout.open() }

        val navigationView: NavigationView = findViewById(R.id.navigation_view)
        navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_profile -> {
                    drawerLayout.close()
                    true
                }
                R.id.nav_settings -> {
                    drawerLayout.close()
                    true
                }
                else -> false
            }
        }

        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_nav)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    showFragment(HomeFragment())
                    true
                }
                R.id.nav_shop -> {
                    showFragment(ShopFragment())
                    true
                }
                R.id.nav_create -> {
                    showCreateBottomSheet()
                    true
                }
                R.id.nav_operations -> {
                    showFragment(OperationsFragment())
                    true
                }
                R.id.nav_grow -> {
                    showFragment(GrowFragment())
                    true
                }
                else -> false
            }
        }

        val fab: FloatingActionButton = findViewById(R.id.fab_create)
        fab.setOnClickListener {
            showCreateBottomSheet()
        }

        // Show home fragment by default
        if (savedInstanceState == null) {
            showFragment(HomeFragment())
        }
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_container, fragment)
            .commit()
    }

    fun showAiMarketing() {
        showFragment(AiMarketingFragment())
    }

    fun showVisualStudio() {
        showFragment(VisualStudioFragment())
    }

    fun showMarketplace() {
        showFragment(MarketplaceFragment())
    }

    fun showArPreview() {
        showFragment(ArLiteFragment())
    }

    fun showTranslation() {
        showFragment(TranslationFragment())
    }

    private fun showCreateBottomSheet() {
        val bottomSheet = CreateBottomSheet.newInstance()
        bottomSheet.show(supportFragmentManager, "CreateBottomSheet")
    }
}