package com.amachewrs.sw5ecompanionapp.species

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.amachewrs.sw5ecompanionapp.R
import com.amachewrs.sw5ecompanionapp.databinding.SpeciesDetailsBinding
import kotlin.math.absoluteValue

class SpeciesDetailsActivity : AppCompatActivity() , GestureDetector.OnGestureListener {
    private var mode=0
    private val swipeThreshold = 100
    private lateinit var binding: SpeciesDetailsBinding
    private lateinit var gestdect: GestureDetector
    private lateinit var inflater: LayoutInflater
    private lateinit var specie: Specie
    private lateinit var tempstring: String
    private val textViewList = mutableListOf<TextView>()
    private lateinit var infoView: View

    @SuppressLint("DiscouragedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gestdect = GestureDetector(this,this)
        specie = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("Specie", Specie::class.java).toSpecie()
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<Specie>("Specie").toSpecie()
        }
        inflater=layoutInflater

        binding = SpeciesDetailsBinding.inflate(inflater)
        setContentView(binding.root)

        binding.Title.text=specie.printname

        binding.BackButton.setOnClickListener { returntomain() }

        binding.dummybutton.setOnClickListener { changeview() }

        infoView.findViewById<ImageView>(R.id.specie_image).setImageDrawable(AppCompatResources.getDrawable(this,specie.imageID))

        infoView = inflater.inflate(R.layout.species_info_template,binding.ll,false)

        val infoText = specie.infoText.split("|")
        for (i in 1..infoText.size){
            textViewList.add(infoView.findViewById(resources.getIdentifier("speciestext$i","id",packageName)))
            textViewList[i-1].text=infoText[i-1]
        }

        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed(){
                returntomain()
            }
        })
        binding.ll.addView(infoView)
    }
    @SuppressLint("DiscouragedApi")
    private fun changeview(){
        binding.scrolly.scrollTo(0,0)
        binding.scrolly.fling(0)
        binding.ll.removeAllViews()

        if (mode==0) {
            binding.ll.removeView(infoView)
            val tempview = inflater.inflate(R.layout.universal_title_goldbar_text_textview,binding.ll,false)
            tempstring="$specie.printname traits"
            tempview.findViewById<TextView>(R.id.headertext).text=tempstring
            tempview.findViewById<TextView>(R.id.contenttext).text=specie.traitsText

            binding.ll.addView(tempview)
            binding.dummybutton.text = getText(R.string.traits)
            mode=1
        } else {
            binding.ll.addView(infoView)
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
                if (diffX.absoluteValue > swipeThreshold && vx.absoluteValue > swipeThreshold) {
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