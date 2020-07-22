package com.example.museon.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.museon.Adapters.songsAdapter
import com.example.museon.R
import com.internshala.echo.SongsModel
import android.app.Activity as Activity1

class SongsFragment : Fragment(){

    var getSonglist: ArrayList<SongsModel>?=null
    var ctxt: Activity1? = null
    var recyclerView:RecyclerView? = null
    var songsadapter: songsAdapter?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val  view = inflater!!.inflate(R.layout.fragment_songs, container, false)
        recyclerView = view?.findViewById<RecyclerView>(R.id.recycler_view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getSonglist = getSongs()
        songsadapter = songsAdapter(getSonglist as ArrayList<SongsModel>, ctxt as Context)
        val mLayoutManager = LinearLayoutManager(ctxt)
        recyclerView?.layoutManager = mLayoutManager
        recyclerView?.adapter = songsadapter

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctxt = context as Activity1
    }


    fun getSongs(): ArrayList<SongsModel>? {
        val tempAudioList: ArrayList<SongsModel> = java.util.ArrayList()
        val uri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val contentResolver =ctxt?.contentResolver

        var cursor = contentResolver?.query(
            uri,
            null,
            null,
            null,
            null
        )
        if (cursor != null && cursor.moveToFirst()) {
            val data = cursor.getColumnIndex(MediaStore.Audio.Media.DATA)
            val id = cursor.getColumnIndex(MediaStore.Audio.Media._ID)
            val title = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
            val artist = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
            val date = cursor.getColumnIndex(MediaStore.Audio.Media.DATE_ADDED)
            val album = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)
            val duration = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)
            while (cursor.moveToNext()){
                val dur = cursor.getLong(duration)
                val currenttitle = cursor.getString(title)
                val currentartist = cursor.getString(artist)
                val currentalbum = cursor.getString(album)
                val currentID = cursor.getLong(id)
                val currentdate = cursor.getLong(date)
                val currentdata = cursor.getString(data)

                tempAudioList.add(SongsModel(currentID, currenttitle, currentartist, currentdata, currentdate ))
            }
            cursor.close()
        }
        return tempAudioList;
    }

}
