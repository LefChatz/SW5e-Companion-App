package com.example.swtrial2.backgrounds

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.swtrial2.R
import com.example.swtrial2.databinding.BackgroundsDetailsBinding

class BackgroundsDetailsActivity : AppCompatActivity() {
    private lateinit var binding: BackgroundsDetailsBinding
    private lateinit var inflater: LayoutInflater
    private lateinit var background: String
    private lateinit var tempstring: String
    private var backgroundidentif = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        background = intent.getStringExtra("Background").toString()
        inflater=layoutInflater

        binding = BackgroundsDetailsBinding.inflate(inflater)
        setContentView(binding.root)

        binding.Title.text=background.replace("_"," ")

        binding.BackButton.setOnClickListener { returntomain() }

        @SuppressLint("DiscouragedApi")
        backgroundidentif=resources.getIdentifier("backgrounds_$background","layout",packageName)


        if (backgroundidentif!=0){
            inflater.inflate(backgroundidentif, binding.ll, true)
        }
        else{
            inflater.inflate(R.layout.universal_textview_nofont_gold,binding.ll,true).findViewById<TextView>(R.id.textview).text=getString(R.string.error_please_report_this)
        }
        tempstring=background.replace("_"," ") + " traits"

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