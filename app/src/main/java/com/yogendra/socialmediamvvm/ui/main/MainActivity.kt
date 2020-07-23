package com.yogendra.socialmediamvvm.ui.main

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.standalone.cooeyhealthtinder.connectivity.base.ConnectivityProvider
import com.standalone.cooeyhealthtinder.connectivity.base.ConnectivityProvider.*
import com.yogendra.socialmediamvvm.R
import com.yogendra.socialmediamvvm.databinding.ActivityMainBinding
import com.yogendra.socialmediamvvm.utils.IS_INTERNET_AVAILABLE
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import kotlin.math.max
import kotlin.math.min

class MainActivity : AppCompatActivity(), ConnectivityStateListener, HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var bottomNav: BottomNavigationView

    private val provider: ConnectivityProvider by lazy { ConnectivityProvider.createProvider(this) }

    override fun androidInjector(): AndroidInjector<Any> =
        dispatchingAndroidInjector as AndroidInjector<Any>

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        IS_INTERNET_AVAILABLE = provider.getNetworkState().hasInternet()

        setSupportActionBar(binding.actMainToolbar)
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        bottomNav = binding.navView

        navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_article, R.id.navigation_users
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNav.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, dest, _ ->
            when (dest.id) {
                R.id.navigation_article -> showBottomNav()
                R.id.navigation_users -> showBottomNav()
                R.id.navigation_profile -> hideBottomNav()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    private fun showBottomNav() {
        bottomNav.visibility = View.VISIBLE

    }

    private fun hideBottomNav() {
        bottomNav.visibility = View.GONE

    }

    override fun onStart() {
        super.onStart()
        provider.addListener(this)
    }

    override fun onStop() {
        super.onStop()
        provider.removeListener(this)
    }

    override fun onStateChange(state: NetworkState) {
        IS_INTERNET_AVAILABLE = state.hasInternet()
    }

    private fun NetworkState.hasInternet(): Boolean {
        return (this as? NetworkState.ConnectedState)?.hasInternet == true
    }

    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {

        if (navController.currentDestination == navController.getBackStackEntry(R.id.navigation_article).destination) {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed()
                return
            }

            this.doubleBackToExitPressedOnce = true
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

            Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
        } else {
            onSupportNavigateUp()
        }

    }

}