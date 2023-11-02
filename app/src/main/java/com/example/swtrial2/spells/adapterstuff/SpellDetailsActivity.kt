package com.example.swtrial2.spells.adapterstuff

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatButton
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.example.swtrial2.R
import com.example.swtrial2.databinding.ActivitySpelldetailsBinding

class SpellDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySpelldetailsBinding
    private lateinit var spell: Spell
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var backButton: AppCompatButton
    @SuppressLint("DiscouragedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        spell = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("Spell", Spell::class.java).toSpell()
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<Spell>("Spell").toSpell()
        }
        binding= ActivitySpelldetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar = findViewById(R.id.force_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        //title and text assignment
        val spTitle:TextView=findViewById(R.id.SpellDetailsTitle)
        spTitle.text=spell.printedname


        val spText:TextView=findViewById(R.id.SpellDetailsText)
        spText.text=spell.detailsText

        //Background
        val spCoord:CoordinatorLayout=findViewById(R.id.SpellDetailsCoord)
        with(spell.side.toString()){
            when{
                this.contains("Dark",true)->{spCoord.background=AppCompatResources.getDrawable(this@SpellDetailsActivity,R.drawable.darkbg1)}
                this.contains("Light",true)->{spCoord.background=AppCompatResources.getDrawable(this@SpellDetailsActivity,R.drawable.lightbg1)}
                this.contains("Universal",true)->{}
                else->{spCoord.background=AppCompatResources.getDrawable(this@SpellDetailsActivity,R.drawable.error404)}
            }

        }

        backButton=findViewById(R.id.forceBackButton)
        backButton.setOnClickListener {
            returntomain()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_spell_details, menu)
        toolbar.overflowIcon = AppCompatResources.getDrawable(this, R.drawable.dots3gold)
        return super.onCreateOptionsMenu(menu)
    }

    fun returntomain() {
        finish()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}