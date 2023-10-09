package com.example.swtrial2.currentlyuseless

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.swtrial2.R

class SearchableActivity : AppCompatActivity() {
    var simplelist: ListView = TODO()
    val spellList =
        arrayOf("India", "China", "australia", "Portugle", "America", "NewZealand")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searchable)

        val arrayAdapter: ArrayAdapter<*>
        val listView = findViewById<ListView>(R.id.simpleListView)
        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,spellList)
        listView.adapter = arrayAdapter

        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                doMySearch(query)
                startActivity(Intent(this@SearchableActivity, Spell1Activity::class.java))
            }
        }

    }
    fun doMySearch(search: String){

    }
}