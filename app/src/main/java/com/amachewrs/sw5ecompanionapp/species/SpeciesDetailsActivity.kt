package com.amachewrs.sw5ecompanionapp.species

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.amachewrs.sw5ecompanionapp.R
import com.amachewrs.sw5ecompanionapp.databinding.SpeciesDetailsBinding
import com.amachewrs.sw5ecompanionapp.databinding.SpeciesInfoTemplateBinding
import com.amachewrs.sw5ecompanionapp.species.speciesadapter.Specie
import com.amachewrs.sw5ecompanionapp.species.speciesadapter.getImageID
import com.amachewrs.sw5ecompanionapp.species.speciesadapter.ensureNotEmpty
import kotlin.math.absoluteValue

class SpeciesDetailsActivity : AppCompatActivity() , GestureDetector.OnGestureListener {
    private lateinit var binding: SpeciesDetailsBinding
    private lateinit var infoBinding: SpeciesInfoTemplateBinding
    private lateinit var gestureDetector: GestureDetector
    private lateinit var specie: Specie
    private lateinit var specieInfoView: View
    private lateinit var specieTraitsView: View
    private var atInfo = true
    private val swipeThreshold = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gestureDetector = GestureDetector(this,this)
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
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) intent.getParcelableExtra("Specie", Specie::class.java).ensureNotEmpty()
        else intent.getParcelableExtra<Specie>("Specie").ensureNotEmpty()
    }

    private fun setInfoView(){
        //Image
        infoBinding.specieImage.setImageResource(specie.getImageID(resources,packageName))
        //Table title
        infoBinding.speciesTableTitle.text = specie.printName
        //Table and Texts
        var tableTextArray = arrayOf(infoBinding.speciesSkinColour,infoBinding.speciesHairColour,infoBinding.speciesEyeColour,infoBinding.speciesDistinctions,infoBinding.speciesHeight,infoBinding.speciesHeightPlus,infoBinding.speciesWeight,infoBinding.speciesWeightPlus,infoBinding.speciesHomeworld,infoBinding.speciesLanguage,infoBinding.speciesBioAndApp,infoBinding.speciesSocAndCul,infoBinding.speciesNamesInfo,infoBinding.speciesMaleNames,infoBinding.speciesFemaleNames,infoBinding.speciesSurnames)

        when (specie.specieInfoType){
            1->{infoBinding.speciesNameTable.removeViews(1,2)
                infoBinding.speciesMaleNamesHeader.text = getString(R.string.species_names)}

            2->{infoBinding.speciesNameTable.removeViews(1,2)
                infoBinding.speciesMaleNamesHeader.text = getString(R.string.species_droid_designations)
                infoBinding.speciesSocAndCulHeader.text = getString(R.string.species_droid_utility)
                infoBinding.speciesBioAndAppHeader.text = getString(R.string.species_droid_appearance)
                infoBinding.speciesHomeworldHeader.text = getString(R.string.species_droid_manufacturer)
                infoBinding.speciesSkinColourHeader.text = getString(R.string.species_droid_colour_scheme)
                infoBinding.speciesTable.removeViews(5,2)
                tableTextArray = tableTextArray.subtract(setOf(infoBinding.speciesHairColour,infoBinding.speciesEyeColour)).toTypedArray()}

            3->{infoBinding.speciesNameTable.removeViews(2,1)}

            4->{infoBinding.speciesNameTable.removeViews(2,1)
                infoBinding.speciesHomeworldHeader.text = infoBinding.speciesLanguageHeader.text
                (infoBinding.speciesLanguageRow.parent as ViewGroup).removeView(infoBinding.speciesLanguageRow)}
        }
        for (i in specie.infoTextHeap.indices){
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
        return gestureDetector.onTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event)
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