package com.example.museon.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate

import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ColorStateListInflaterCompat.inflate
import androidx.core.content.res.ComplexColorCompat.inflate
import androidx.core.graphics.drawable.DrawableCompat.inflate

import androidx.recyclerview.widget.RecyclerView
import com.example.museon.R
import com.internshala.echo.SongsModel

class songsAdapter(val list: ArrayList<SongsModel>, ctxt : Context) : RecyclerView.Adapter<songsAdapter.SongsViewHolder>(){
    var song: ArrayList<SongsModel>?=null
    var mCtxt: Context? = null

    init{
        this.song = list
        this.mCtxt = ctxt
    }

    override fun onBindViewHolder(holder: SongsViewHolder, position: Int) {
        val songObj = song?.get(position)
        holder.songName?.text = songObj?.songTitle
        holder.songArtist?.text = songObj?.artist
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongsViewHolder {
        val root = LayoutInflater.from(parent.context)
            .inflate(R.layout.song_layout, parent, false)

        return SongsViewHolder(root)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class SongsViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var songName = view.findViewById<TextView>(R.id.songname)
        var songArtist = view.findViewById<TextView>(R.id.songartist)

    }

}