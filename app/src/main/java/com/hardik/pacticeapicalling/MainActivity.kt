package com.hardik.pacticeapicalling

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.hardik.pacticeapicalling.databinding.ActivityMainBinding
import com.hardik.pacticeapicalling.domain.model.UserModel
import com.hardik.pacticeapicalling.domain.use_case.GetUserUseCase
import com.hardik.pacticeapicalling.presentation.MainViewModel
import com.hardik.pacticeapicalling.presentation.ui.dashboard.DashboardFragment
import com.hardik.pacticeapicalling.presentation.ui.home.HomeFragment
import com.hardik.pacticeapicalling.presentation.viewModelFactory
import com.hardik.pacticeapicalling.utillities.FragmentSessionUtils

class MainActivity : AppCompatActivity() {
    private final val TAG = MainActivity::class.java.simpleName

    private lateinit var binding: ActivityMainBinding
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Enable the Up button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        val navController = findNavController(R.id.nav_host_fragment_activity_main)
//        val appBarConfiguration = AppBarConfiguration(setOf(R.id.navigation_home, R.id.navigation_dashboard))
//        setupActionBarWithNavController(navController, appBarConfiguration)

        val fragmentSessionUtils = FragmentSessionUtils.getInstance()

        if (savedInstanceState == null){
            val currentFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main)
            if (currentFragment !is HomeFragment) { fragmentSessionUtils.switchFragment(
                supportFragmentManager,
                HomeFragment(),//HomeFragment.newInstance(param1 = "file1", param2 = "file2"),//HomeFragment(),
                false,
                                                                                               )
            }
        }
        mainViewModel = ViewModelProvider(this, viewModelFactory { MainViewModel(GetUserUseCase(MyApp.appModule.userRepository)) }).get(MainViewModel::class.java)


        mainViewModel.state.observe(this){
            it?.let {
                if (it.isLoading){
                    Log.d(TAG, "onCreate: It Is Loading!!!")
                }
                if (it.error.isNotBlank()){
                    Log.d(TAG, "onCreate: It Has Error!!!\t${it.error}")
                }
                if (it.users.isNotEmpty()){
                    for (user: UserModel in it.users){
                        Log.e(TAG, "onCreate: user: ${user.name}" )
                    }
                }
            }
        }

        Thread {
            try {
                Thread.sleep(5000L) // Sleep for 2 seconds

                fragmentSessionUtils.switchFragment(supportFragmentManager, DashboardFragment(),true)
                Log.v(TAG, "onCreate: ${mainViewModel.state.value}")
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }.start() // Start the thread
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}