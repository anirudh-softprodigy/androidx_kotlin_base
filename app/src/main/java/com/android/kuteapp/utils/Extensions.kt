package com.kuteapp.utils

import android.content.Context
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.android.kuteapp.R
import com.squareup.moshi.Moshi
import com.squareup.picasso.Picasso
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException

/**
 * Created by Anirudh_Sharma on 01-May-18.
 */
object Extensions {

    fun ViewGroup.inflate(layoutRes: Int): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, false)
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(this.windowToken, 0)
    }

    //load image form url
    fun ImageView.loadImage(url: String, @DrawableRes placeholder: Int) {
        if (placeholder != 0) {
            Picasso.get().load(url).placeholder(placeholder).error(placeholder).into(this)
        } else {
            Picasso.get().load(url).into(this)
        }
    }


    fun View.showView() {
        this.visibility = View.VISIBLE
    }

    fun View.hideView() {
        this.visibility = View.GONE
    }

    //return error message from webservice error code
    fun Throwable.getErrorMessage(context: Context): String {
        return if (this is HttpException || this is UnknownHostException
                || this is ConnectException) {
            context.resources.getString(R.string.warning_network_error)
        } else {
            context.resources.getString(R.string.error_occurred)
        }
    }

    //check if email is valid
    fun String.isValidEmail(): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }

    //convert to response using Moshi
    inline fun <reified T> Moshi.fromJson(json: String): T? = this.adapter(T::class.java).fromJson(json)

}


