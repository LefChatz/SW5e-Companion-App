package com.amachewrs.sw5ecompanionapp.species

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.amachewrs.sw5ecompanionapp.R
import com.amachewrs.sw5ecompanionapp.databinding.SpeciesDetailsBinding
import kotlin.math.absoluteValue

class SpeciesDetailsActivity : AppCompatActivity() , GestureDetector.OnGestureListener {
    private lateinit var binding: SpeciesDetailsBinding
    private lateinit var gestdect: GestureDetector
    private lateinit var specie: String
    private lateinit var specieTraitsName: String
    private lateinit var specieText: CharSequence
    private lateinit var specieInfoView: View
    private lateinit var specieTraitsView: View
    private var atInfo = true
    private val swipeThreshold = 100
    private var infoIdentifier = 0

    @SuppressLint("DiscouragedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gestdect = GestureDetector(this,this)
        specie = intent.getStringExtra("Specie").toString()

        binding = SpeciesDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.coord){ cl,windowInsets ->
            cl.updatePadding(0,windowInsets.getInsets(WindowInsetsCompat.Type.systemBars()).top)
            WindowInsetsCompat.CONSUMED }

        binding.Title.text=specie.replace("_"," ")

        binding.BackButton.setOnClickListener { returntomain() }

        binding.dummybutton.setOnClickListener { changeview() }

        infoIdentifier=resources.getIdentifier("species_"+specie+"_info","layout",packageName)
        specieText=resources.getText(resources.getIdentifier(specie+"_traitsText","string",packageName))

        if (infoIdentifier!=0){
            specieInfoView = layoutInflater.inflate(infoIdentifier, binding.ll, true)
        }
        else{
            layoutInflater.inflate(R.layout.universal_textview_nofont_gold,binding.ll,true).findViewById<TextView>(R.id.textview).text=getString(R.string.error_please_report_this)
        }
        specieTraitsName=specie.replace("_"," ") + " traits"

        specieTraitsView  = layoutInflater.inflate(R.layout.universal_title_goldbar_text_textview,binding.ll,false)
        specieTraitsView.findViewById<TextView>(R.id.headertext).text=specieTraitsName
        specieTraitsView.findViewById<TextView>(R.id.contenttext).text=specieText

        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                returntomain()
            }
        })
    }

    private fun changeview(){
        binding.scrolly.scrollTo(0,0)
        binding.scrolly.fling(0)
        binding.scrolly.removeAllViews()
        if (atInfo) {
            binding.scrolly.addView(specieTraitsView)
            binding.dummybutton.text = getText(R.string.traits)
        }
        else {
            binding.scrolly.addView(binding.ll)
            binding.dummybutton.text = getString(R.string.info)
        }
        atInfo=!atInfo
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
                if (diffX.absoluteValue > swipeThreshold && vx.absoluteValue > swipeThreshold) {
                    //L to R
                    if (diffX > 0 && !atInfo) changeview()
                    //R to L
                    else if(diffX<0 && atInfo) changeview()
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