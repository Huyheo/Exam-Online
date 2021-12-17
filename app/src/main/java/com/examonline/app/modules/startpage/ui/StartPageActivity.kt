package com.examonline.app.modules.startpage.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.activity.viewModels
import com.examonline.app.MainActivity
import com.examonline.app.R
import com.examonline.app.appcomponents.base.BaseActivity
import com.examonline.app.appcomponents.utility.PreferenceHelper
import com.examonline.app.databinding.ActivityStartPageBinding
import com.examonline.app.modules.login.ui.LoginActivity
import com.examonline.app.modules.startpage.`data`.viewmodel.StartPageVM
import org.koin.core.KoinComponent
import org.koin.core.inject
import kotlin.String
import kotlin.Unit

@Suppress("DEPRECATION")
public class StartPageActivity :
    BaseActivity<ActivityStartPageBinding>(R.layout.activity_start_page), KoinComponent {
  private val viewModel: StartPageVM by viewModels<StartPageVM>()
  private val prefs: PreferenceHelper by inject()


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }
  public override fun setUpClicks(): Unit {
//    binding.image.setOnClickListener {
//      val destIntent = LoginActivity.getIntent(this, null)
//      startActivity(destIntent)
//      this.finish()
//    }
  }

  public override fun onInitialized(): Unit {
    binding.startPageVM = viewModel
    Handler().postDelayed({
      if (prefs.getIsLogin() == true && prefs.getTimeExpire()>System.currentTimeMillis()){
        val destIntent = Intent(this, MainActivity::class.java)
        destIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(destIntent)
        val welcome = getString(R.string.welcome_back)
        val displayName = prefs.getUserName()
        Toast.makeText(
          applicationContext,
          "$welcome $displayName",
          Toast.LENGTH_LONG
        ).show()
        finish()
      }
      else{
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
      }
    }, 3000)

  }

  public companion object {
    public const val TAG: String = "START_PAGE_ACTIVITY"

    public fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, StartPageActivity::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }
}
