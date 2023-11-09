package com.example.swtrial2.species

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.example.swtrial2.R
import com.example.swtrial2.databinding.SpeciesDetailsBinding
import kotlin.math.absoluteValue

class SpeciesDetailsActivity : AppCompatActivity() , GestureDetector.OnGestureListener {
    private var mode=0
    private val swipethreshold = 10
    private lateinit var binding: SpeciesDetailsBinding
    private lateinit var gestdect: GestureDetector
    private lateinit var dummybutton: Button
    private lateinit var inflater: LayoutInflater
    private lateinit var coordl: CoordinatorLayout
    private lateinit var ll: LinearLayoutCompat
    private lateinit var scrolly: ScrollView
    private lateinit var specie: String
    private lateinit var tempstring: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gestdect = GestureDetector(this,this)
        specie = intent.getStringExtra("Specie").toString()
        inflater=layoutInflater
        binding = SpeciesDetailsBinding.inflate(inflater)
        setContentView(binding.root)

        ll=binding.ll
        scrolly=binding.scrolly
        dummybutton=binding.dummybutton
        tempstring="$specie traits"
        binding.Title.text=specie

        Log.d("specie" , specie)
        inflater.inflate(resources.getIdentifier("species_"+specie+"_info","layout",packageName), ll, true)
    }
    @SuppressLint("DiscouragedApi")
    private fun changeview(){
        scrolly.scrollTo(0,0)
        scrolly.fling(0)
        ll.removeAllViews()

        if (mode==0) {
            val tempview = inflater.inflate(R.layout.class_textview,ll,false)
            tempview.findViewById<TextView>(R.id.headertext).text=tempstring

            tempview.findViewById<TextView>(R.id.contenttext).text=resources.getText(resources.getIdentifier(specie+"_traitsText","string",packageName))
            ll.addView(tempview)
            dummybutton.text = getText(R.string.traits)
            mode=1
        } else {

            inflater.inflate(resources.getIdentifier("species_"+specie+"_info","layout",packageName), ll, true)
            dummybutton.text = getString(R.string.info)
            mode=0
        }
    }
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        super.dispatchTouchEvent(ev)
        return gestdect.onTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (gestdect.onTouchEvent(event)) {
            true
        } else {
            super.onTouchEvent(event)
        }
    }

    override fun onDown(p0: MotionEvent): Boolean {
        return false
    }

    override fun onShowPress(p0: MotionEvent) {
        return
    }

    override fun onSingleTapUp(p0: MotionEvent): Boolean {
        return false
    }

    override fun onScroll(p0: MotionEvent, p1: MotionEvent, p2: Float, p3: Float): Boolean {
        return false
    }

    override fun onLongPress(p0: MotionEvent) {
        return
    }

    override fun onFling(e0: MotionEvent, e1: MotionEvent, vx: Float, vy: Float): Boolean {
        val diffX = e1.x - e0.x
        if(diffX.absoluteValue>(e1.y-e0.y).absoluteValue) {
            if (diffX.absoluteValue > swipethreshold && vx.absoluteValue > swipethreshold) {
                //L to R
                if (diffX > 0 && mode==1) {
                    changeview()
                }
                //R to L
                else if(diffX<0 && mode==0){
                    changeview()
                }
            }
        }
        return true
    }

    fun returntomain(view: View?) {
        finish()
    }
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        returntomain(null)
        super.onBackPressed()
    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}