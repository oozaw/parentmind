package com.capstone.parentmind.view.video

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.parentmind.R
import com.capstone.parentmind.view.adapter.VideoAdapter

class VideoActivity : AppCompatActivity() {
//ini untuk nyoba hasil recycleview saja

    private var layoutManager: RecyclerView.LayoutManager?=null
    private var adapter: VideoAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_education)

        val rvVidio = findViewById<RecyclerView>(R.id.rv_list_vidio)
        layoutManager = LinearLayoutManager(this)
        rvVidio.layoutManager = layoutManager

        adapter = VideoAdapter()
        rvVidio.adapter = adapter

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