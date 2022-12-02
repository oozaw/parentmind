package com.capstone.parentmind.view.article
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.parentmind.R
import com.capstone.parentmind.view.adapter.ArtikelAdapter

class ArtikelActivity : AppCompatActivity() {

    private var layoutManager: RecyclerView.LayoutManager?=null
    private var adapter: ArtikelAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artikel_education)

        val rvArtikel = findViewById<RecyclerView>(R.id.rv_list_ahli_parenting)
        layoutManager = LinearLayoutManager(this)
        rvArtikel.layoutManager = layoutManager

        adapter = ArtikelAdapter()
        rvArtikel.adapter = adapter

    }

    //option main menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_navbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_search_bar->{
            }
            R.id.menu_notification->{
            }
        }
        return super.onOptionsItemSelected(item)
    }
}