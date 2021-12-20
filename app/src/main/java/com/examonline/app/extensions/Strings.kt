package com.examonline.app.extensions

import android.util.Patterns
import org.json.JSONObject
import java.util.regex.Pattern
import kotlin.Exception

//Minimum 6 characters, no white spaces
val PASSWORD_PATTERN by lazy { "^" +
//            "(?=.*[0-9])" +         //at least 1 digit
//            "(?=.*[a-z])" +         //at least 1 lower case letter
//            "(?=.*[A-Z])" +         //at least 1 upper case letter
//            "(?=.*[a-zA-Z])" +      //any letter
//            "(?=.*[@#$%^&+=])" +    //at least 1 special character
        "(?=\\S+$)" +           //no white spaces
        ".{6,}" +               //at least 6 characters
        "$"
}

fun String.isEmail(): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isPassword(): Boolean {
    return Pattern.compile(PASSWORD_PATTERN).matcher(this).matches()
}

fun String.isJSONObject():Boolean{
    return try {
        JSONObject(this)
        true
    }catch (e:Exception){
        false
    }
}