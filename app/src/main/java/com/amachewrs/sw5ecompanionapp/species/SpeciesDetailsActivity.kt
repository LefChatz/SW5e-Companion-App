package com.amachewrs.sw5ecompanionapp.species

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.amachewrs.sw5ecompanionapp.R
import com.amachewrs.sw5ecompanionapp.databinding.SpeciesDetailsBinding
import kotlin.math.absoluteValue

class SpeciesDetailsActivity : AppCompatActivity() , GestureDetector.OnGestureListener {
    private var mode=0
    private val swipethreshold = 100
    private lateinit var binding: SpeciesDetailsBinding
    private lateinit var gestdect: GestureDetector
    private lateinit var inflater: LayoutInflater
    private lateinit var specie: String
    private lateinit var tempstring: String
    private lateinit var specietext: CharSequence
    private var infoidentif = 0

    @SuppressLint("DiscouragedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gestdect = GestureDetector(this,this)
        specie = intent.getStringExtra("Specie").toString()
        inflater=layoutInflater

        binding = SpeciesDetailsBinding.inflate(inflater)
        setContentView(binding.root)

        binding.Title.text=specie.replace("_"," ")

        binding.BackButton.setOnClickListener { returntomain() }

        binding.dummybutton.setOnClickListener { changeview() }

        infoidentif=resources.getIdentifier("species_"+specie+"_info","layout",packageName)
        specietext=resources.getText(resources.getIdentifier(specie+"_traitsText","string",packageName))

        if (infoidentif!=0){
            inflater.inflate(infoidentif, binding.ll, true)
        }
        else{
            inflater.inflate(R.layout.universal_textview_nofont_gold,binding.ll,true).findViewById<TextView>(R.id.textview).text=getString(R.string.error_please_report_this)
        }
        tempstring=specie.replace("_"," ") + " traits"

        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                returntomain()
            }
        })
    }
    @SuppressLint("DiscouragedApi")
    private fun changeview(){
        binding.scrolly.scrollTo(0,0)
        binding.scrolly.fling(0)
        binding.ll.removeAllViews()

        if (mode==0) {
            val tempview = inflater.inflate(R.layout.universal_title_goldbar_text_textview,binding.ll,false)
            tempview.findViewById<TextView>(R.id.headertext).text=tempstring
            tempview.findViewById<TextView>(R.id.contenttext).text=specietext

            binding.ll.addView(tempview)
            binding.dummybutton.text = getText(R.string.traits)
            mode=1
        } else {
            inflater.inflate(resources.getIdentifier("species_"+specie+"_info","layout",packageName), binding.ll, true)
            binding.dummybutton.text = getString(R.string.info)
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

    override fun onScroll(p0: MotionEvent?, p1: MotionEvent, p2: Float, p3: Float): Boolean {
        return false
    }

    override fun onLongPress(p0: MotionEvent) {
        return
    }

    override fun onFling(e0: MotionEvent?, e1: MotionEvent, vx: Float, vy: Float): Boolean {
        if (e0 != null) {
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
        }
        return true
    }

    private fun returntomain() {
        finish()
    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}