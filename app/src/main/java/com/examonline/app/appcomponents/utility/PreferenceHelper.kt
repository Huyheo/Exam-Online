package com.examonline.app.appcomponents.utility

import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.examonline.app.R
import com.examonline.app.appcomponents.di.MyApp

public class PreferenceHelper {
    private val masterKeyAlias: String = createGetMasterKey()
    private val sharedPreference: SharedPreferences = EncryptedSharedPreferences.create(
        MyApp.getInstance().resources.getString(R.string.app_name),
        masterKeyAlias,
        MyApp.getInstance().applicationContext,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
    public fun setToken(paramValue: String?): Unit {
        with(sharedPreference.edit()) {
            this.putString("token", paramValue!!)
            apply()
        }
    }
    public fun getToken(): String? {
        with(sharedPreference) {
            return getString("token", null)
        }
    }
    public fun setUserName(paramValue: String?): Unit {
        with(sharedPreference.edit()) {
            this.putString("username", paramValue!!)
            apply()
        }
    }
    public fun getUserName(): String? {
        with(sharedPreference) {
            return getString("username", null)
        }
    }
    public fun setAvatar(paramValue: String?): Unit {
        with(sharedPreference.edit()) {
            this.putString("avatar", paramValue!!)
            apply()
        }
    }
    public fun getAvatar(): String? {
        with(sharedPreference) {
            return getString("avatar", null)
        }
    }
    public fun setEmail(paramValue: String?): Unit {
        with(sharedPreference.edit()) {
            this.putString("email", paramValue!!)
            apply()
        }
    }
    public fun getEmail(): String? {
        with(sharedPreference) {
            return getString("email", null)
        }
    }
    public fun setUserID(paramValue: String?): Unit {
        with(sharedPreference.edit()) {
            this.putString("userid", paramValue!!)
            apply()
        }
    }
    public fun getUserID(): String? {
        with(sharedPreference) {
            return getString("userid", null)
        }
    }
    public fun setIsStudent(paramValue: Boolean?): Unit {
        with(sharedPreference.edit()) {
            this.putBoolean("isstudent", paramValue!!)
            apply()
        }
    }
    public fun getIsStudent(): Boolean? {
        with(sharedPreference) {
            return getBoolean("isstudent", false)
        }
    }
    public fun setIsLogin(paramValue: Boolean?): Unit {
        with(sharedPreference.edit()) {
            this.putBoolean("islogin", paramValue!!)
            apply()
        }
    }
    public fun getIsLogin(): Boolean? {
        with(sharedPreference) {
            return getBoolean("islogin", false)
        }
    }
    public fun setTimeExpire(paramValue: Long): Unit {
        with(sharedPreference.edit()) {
            this.putLong("timeexpire", paramValue)
            apply()
        }
    }
    public fun getTimeExpire(): Long {
        with(sharedPreference) {
            return getLong("timeexpire", 0)
        }
    }
    public fun setLogout(): Unit {
        with(sharedPreference.edit()) {
            this.putBoolean("islogin", false)
            this.putString("userid", "")
            this.putString("email", "")
            this.putString("avatar", "")
            this.putString("username", "")
            this.putString("token", "")
            this.putLong("timeexpire", 0)
            this.putBoolean("isstudent", false)
            apply()
        }
    }
    private fun createGetMasterKey(): String = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
}