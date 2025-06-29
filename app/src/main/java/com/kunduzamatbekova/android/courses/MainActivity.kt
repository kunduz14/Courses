package com.kunduzamatbekova.android.courses

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.kunduzamatbekova.android.courses.databinding.ActivityMainBinding
import com.kunduzamatbekova.android.feature_account.AccountFragment
import com.kunduzamatbekova.android.feature_favourites.FavouritesCoursesFragment
import com.kunduzamatbekova.android.feature_home.HomeFragment
import com.kunduzamatbekova.android.feature_login.LoginFragment

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for ActivityMainBinding most not be null")

    private var currentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefs = getSharedPreferences("auth", MODE_PRIVATE)
        val isLogIn = prefs.getBoolean("is_log_in", false)

        if (savedInstanceState == null) {
            val initialFragment = if (isLogIn) HomeFragment() else LoginFragment()
            currentFragment = initialFragment
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, initialFragment)
                .commit()
        }

        binding.navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_home -> {
                    switchFragment(HomeFragment())
                    true
                }

                R.id.action_favourites -> {
                    switchFragment(FavouritesCoursesFragment())
                    true
                }

                R.id.action_account -> {
                    switchFragment(AccountFragment())
                    true
                }

                else -> false
            }
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (currentFragment is HomeFragment || currentFragment is LoginFragment) {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                } else {
                    switchFragment(HomeFragment())
                    binding.navigation.selectedItemId = R.id.action_home
                }
            }
        })
    }

    private fun switchFragment(newFragment: Fragment) {
        if (currentFragment?.javaClass == newFragment.javaClass) {
            return
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, newFragment)
            .commit()

        currentFragment = newFragment
    }
}