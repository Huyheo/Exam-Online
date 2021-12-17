package com.examonline.app

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.examonline.app.appcomponents.utility.PreferenceHelper
import com.examonline.app.databinding.ActivityMainBinding
import com.examonline.app.modules.login.ui.LoginActivity
import com.examonline.app.modules.profilescreen.ui.ProfileScreenFragment
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso
import org.koin.core.KoinComponent
import org.koin.core.inject


class MainActivity :  AppCompatActivity(),KoinComponent {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val prefs: PreferenceHelper by inject()


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (prefs.getIsStudent()==false){
            finish()
            val intent:Intent = LoginActivity.getIntent(this,null)
            startActivity(intent)
            Toast.makeText(this,"You are not student!",Toast.LENGTH_LONG).show()
        }
        else {
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)
            setSupportActionBar(binding.appBarMain.findViewById(R.id.toolbar))

            val drawerLayout: DrawerLayout = binding.drawerLayout
            val navView: NavigationView = binding.navView
            val navController = findNavController(R.id.nav_host_fragment_content_main)
            appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.nav_home,
                    R.id.nav_classes,
                    R.id.nav_take_exam,
                    R.id.nav_history_exam,
                    R.id.nav_profile,
                    R.id.nav_logout
                ), drawerLayout
            )
            navView.setupWithNavController(navController)
            binding.appBarMain.findViewById<ImageView>(R.id.image).setOnClickListener {
                drawerLayout.openDrawer(GravityCompat.START)
            }

            binding.navView.menu.findItem(R.id.nav_logout).setOnMenuItemClickListener {
                prefs.setLogout()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                this.finish()
                true
            }

            if (prefs.getAvatar()?.isNotEmpty() == true) {
                val imageView: ImageView =
                    binding.navView.getHeaderView(0).findViewById(R.id.imageView) as ImageView
                Picasso.get().load(prefs.getAvatar()).into(imageView);
            }
            binding.navView.getHeaderView(0).findViewById<TextView>(R.id.hellouser).text =
                "Hello, " + prefs.getUserName()
            binding.navView.getHeaderView(0).findViewById<TextView>(R.id.useremail).text =
                prefs.getEmail()

            binding.navView.getHeaderView(0).setOnClickListener {
                drawerLayout.closeDrawer(GravityCompat.START)
                binding.navView.setCheckedItem(R.id.nav_profile)
                val manager: FragmentManager = this.supportFragmentManager
                val transaction = manager.beginTransaction()
                transaction.replace(
                    R.id.nav_host_fragment_content_main,
                    ProfileScreenFragment::class.java,
                    null
                )
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }
    }

    override fun onBackPressed() {
        if (this.binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    public companion object {
        public const val TAG: String = "MAIN_ACTIVITY"

        public fun getIntent(context: Context, bundle: Bundle?): Intent {
            val destIntent = Intent(context, MainActivity::class.java)
            destIntent.putExtra("bundle", bundle)
            return destIntent
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
