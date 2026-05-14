package com.amachewrs.sw5ecompanionapp.species

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.amachewrs.sw5ecompanionapp.R
import com.amachewrs.sw5ecompanionapp.databinding.SpeciesDetailsBinding
import com.amachewrs.sw5ecompanionapp.databinding.SpeciesInfoTemplateBinding
import com.amachewrs.sw5ecompanionapp.species.speciesadapter.Specie
import com.amachewrs.sw5ecompanionapp.species.speciesadapter.toSpecie
import kotlin.math.absoluteValue

class SpeciesDetailsActivity : AppCompatActivity() , GestureDetector.OnGestureListener {
    private lateinit var binding: SpeciesDetailsBinding
    private lateinit var infoBinding: SpeciesInfoTemplateBinding
    private lateinit var gestdect: GestureDetector
    private lateinit var specie: Specie
    private lateinit var specieInfoView: View
    private lateinit var specieTraitsView: View
    private var atInfo = true
    private val swipeThreshold = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gestdect = GestureDetector(this,this)
        specie = getSpecie()

        binding = SpeciesDetailsBinding.inflate(layoutInflater)
        infoBinding = SpeciesInfoTemplateBinding.inflate(layoutInflater)

        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.coord){ cl,windowInsets ->
            cl.updatePadding(0,windowInsets.getInsets(WindowInsetsCompat.Type.systemBars()).top)
            WindowInsetsCompat.CONSUMED }

        binding.Title.text=specie.printName

        binding.BackButton.setOnClickListener { returnToMain() }

        binding.dummybutton.setOnClickListener { changeView() }

        specieInfoView = infoBinding.specieInfoTemplateLl

        setInfoView()

        specieTraitsView  = layoutInflater.inflate(R.layout.universal_title_goldbar_text_textview,binding.scrolly,false)
        specieTraitsView.findViewById<TextView>(R.id.headertext).text= getString(R.string.species_printname_traits, specie.printName)
        specieTraitsView.findViewById<TextView>(R.id.contenttext).text=specie.traitsText

        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                returnToMain()
            }
        })
    }

    @Suppress("DEPRECATION")
    private fun getSpecie(): Specie{
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) intent.getParcelableExtra("Specie", Specie::class.java).toSpecie()
        else intent.getParcelableExtra<Specie>("Specie").toSpecie()
    }

    @SuppressLint("DiscouragedApi")
    private fun setInfoView(){
        //Image
        infoBinding.specieImage.setImageResource(resources.getIdentifier(specie.imageID,"drawable",packageName))
        //Table title
        infoBinding.speciesTableTitle.text = specie.printName
        //Table and Texts
        val tableTextArray = arrayOf(infoBinding.speciesText1,infoBinding.speciesText2,infoBinding.speciesText3,infoBinding.speciesText4,infoBinding.speciesText5,infoBinding.speciesText6,infoBinding.speciesText7,infoBinding.speciesText8,infoBinding.speciesText9,infoBinding.speciesText10,infoBinding.speciesText11,infoBinding.speciesText12,infoBinding.speciesText13,infoBinding.speciesText14,infoBinding.speciesText15,infoBinding.speciesText16)
        for (i in 0..<specie.infoTextHeap.size){
            tableTextArray[i].text=specie.infoTextHeap.pop()
        }
        binding.scrolly.addView(specieInfoView)
    }
    private fun changeView(){
        binding.scrolly.scrollTo(0,0)
        binding.scrolly.fling(0)
        binding.scrolly.removeAllViews()
        if (atInfo) {
            binding.scrolly.addView(specieTraitsView)
            binding.dummybutton.text = getText(R.string.traits)
        }
        else {
            binding.scrolly.addView(specieInfoView)
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
                    if ((diffX > 0 && !atInfo) or (diffX<0 && atInfo)) changeView()
                }
            }
        }
        return true
    }

    private fun returnToMain() {
        finish()
    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}