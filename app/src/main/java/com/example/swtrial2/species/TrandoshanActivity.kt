package com.example.swtrial2.species

import android.content.res.Configuration
import android.os.Bundle
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
import kotlin.math.absoluteValue

class TrandoshanActivity : AppCompatActivity() , GestureDetector.OnGestureListener {
    private val swipethreshold = 10
    private lateinit var gestdect: GestureDetector
    private lateinit var dummybutton: Button
    private lateinit var inflater: LayoutInflater
    private lateinit var coordl: CoordinatorLayout
    private lateinit var ll: LinearLayoutCompat
    private lateinit var scrolly: ScrollView
    private var mode=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.species_trandoshan)

        inflater = layoutInflater
        gestdect = GestureDetector(this,this)

        scrolly = findViewById(R.id.trandoshanscrollview)
        coordl = findViewById(R.id.coordlayout)
        ll = findViewById(R.id.trandoshanll)
        dummybutton = findViewById(R.id.dummybutton)

        dummybutton.setOnClickListener {
            changeview()
        }


    }
    private fun changeview(){
        scrolly.scrollTo(0,0)
        scrolly.fling(0)
        ll.removeAllViews()
        if (dummybutton.text.toString() == "Info") {
            val tempview = inflater.inflate(R.layout.class_textview,ll,false)
            tempview.findViewById<TextView>(R.id.headertext).text=getText(R.string.trandoshan_traitsHeader)
            tempview.findViewById<TextView>(R.id.contenttext).text=getText(R.string.trandoshan_traitsText)
            ll.addView(tempview)
            dummybutton.text = getText(R.string.traits)
            mode=1
        } else {
            inflater.inflate(R.layout.species_trandoshan_info, ll, true)
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