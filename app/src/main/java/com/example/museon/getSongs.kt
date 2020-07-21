package com.example.museon

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.example.museon.models.SongsModel


class getSongs{


    fun getSongs(context: Context): List<SongsModel>? {
        val tempAudioList: MutableList<SongsModel> = ArrayList()
        val uri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Audio.AudioColumns.DATA,
            MediaStore.Audio.AudioColumns.TITLE,
            MediaStore.Audio.AudioColumns.ALBUM,
            MediaStore.Audio.ArtistColumns.ARTIST,
            MediaStore.Audio.AudioColumns.DURATION

        )
        val cursor = context.getContentResolver().query(
            uri,
            projection,
            null,
            null,
            null
        )
        if (cursor != null && cursor.moveToFirst()) {
            val audioModel = SongsModel()
            val title = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
            val artist = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
            val date = cursor.getColumnIndex(MediaStore.Audio.Media.DATE_ADDED)
            val album = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)
            val duration = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)
            val size = cursor.getColumnIndex(MediaStore.Audio.Media.SIZE)
            val path = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
            do {
                val dur = cursor.getLong(duration)
                val currenttitle = cursor.getString(title)
                val currentartist = cursor.getString(artist)
                val currentdate = cursor.getString(date)
                val currentalbum = cursor.getString(album)
                val currentsize = cursor.getString(size)
                val currentpath = cursor.getString(path)
                audioModel.setaName(currenttitle)
                audioModel.setaArtist(currentartist)
                audioModel.setaAlbum(currentalbum)
                audioModel.setalength(dur)
            } while (cursor.moveToNext())
            cursor.close()
        }
    return tempAudioList;
    }

}
