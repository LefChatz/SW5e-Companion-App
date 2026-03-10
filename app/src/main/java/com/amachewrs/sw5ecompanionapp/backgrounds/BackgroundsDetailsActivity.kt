package com.amachewrs.sw5ecompanionapp.backgrounds

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.amachewrs.sw5ecompanionapp.R
import com.amachewrs.sw5ecompanionapp.databinding.BackgroundsDetailsBinding
import java.util.LinkedList

class BackgroundsDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val background = intent.getStringExtra("Background").toString()
        val inflater=layoutInflater

        val binding = BackgroundsDetailsBinding.inflate(inflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.coord){ cl,windowInsets ->
            cl.updatePadding(0,windowInsets.getInsets(WindowInsetsCompat.Type.systemBars()).top)
            WindowInsetsCompat.CONSUMED }

        binding.Title.text=background.replace("_"," ").replace(".","-")
        if(background=="retired_adventurer") binding.Title.text=getString(R.string.backgrounds_un_retired_adventurer)

        @SuppressLint("DiscouragedApi")
        val identifier = resources.getIdentifier(background,"array",packageName)

        if (identifier==0) {
            val tempText = inflater.inflate(R.layout.universal_textview_nofont_gold,binding.ll,false).findViewById<TextView>(R.id.textview)
            tempText.text = resources.getString(R.string.error_please_report_this)
            binding.ll.addView(tempText)
        }
        else{
            val detailsHeap = LinkedList<CharSequence>()
            resources.getTextArray(identifier).toCollection(detailsHeap)

            binding.SourceBook.text=detailsHeap.poll()
            generateDetails(binding.ll, resources, inflater, detailsHeap)
        }

        binding.BackButton.setOnClickListener { returntomain() }

        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                returntomain()
            }
        })


    }

    private fun returntomain() {
        finish()
    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}