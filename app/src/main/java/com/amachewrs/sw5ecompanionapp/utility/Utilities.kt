package com.amachewrs.sw5ecompanionapp.utility

import android.content.Context
import android.view.Gravity
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat.getColor
import com.amachewrs.sw5ecompanionapp.R
import com.google.android.material.snackbar.Snackbar

class Utilities {
    companion object{
        fun showSnackBar(text: String, rootView: View, context: Context){
            val snackBar = Snackbar.make(rootView,text, Snackbar.LENGTH_SHORT)
                .setTextColor(getColor(context,R.color.gold))

            snackBar.view.background = AppCompatResources.getDrawable(context,R.drawable.snackbar_background)

            val params = snackBar.view.layoutParams as (CoordinatorLayout.LayoutParams)
            params.width= CoordinatorLayout.LayoutParams.WRAP_CONTENT
            params.setMargins(60,0,70,60)
            params.gravity = Gravity.BOTTOM or Gravity.CENTER

            snackBar.view.layoutParams = params

            snackBar.show()
            //UsE sNaCkBaR iNsTeAd
        }
    }
}