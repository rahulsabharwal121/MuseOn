package com.example.museon

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.*

class HomeActivity : AppCompatActivity() {
    var list: ArrayList<String>? = null
    var listView: ListView? = null
    var adapter: ArrayAdapter<String>? = null

    @RequiresApi(api = Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        if (ContextCompat.checkSelfPermission(
                this@HomeActivity,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this@HomeActivity,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {
                ActivityCompat.requestPermissions(
                    this@HomeActivity,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    MY_REQ
                )
            } else {
                ActivityCompat.requestPermissions(
                    this@HomeActivity,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    MY_REQ
                )
            }
        } else {
            getallMusic()
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    fun getallMusic() {
        listView = findViewById<View>(R.id.songView) as ListView
        list = ArrayList()
        music
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list!!)
        listView!!.adapter = adapter
        listView!!.onItemClickListener =
            OnItemClickListener { adapterView, view, i, l -> }
    }

    @get:RequiresApi(api = Build.VERSION_CODES.Q)
    val music: Unit
        get() {
            val contentResolver = contentResolver
            val songuri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            val cursor =
                contentResolver.query(songuri, null, null, null, null)
            if (cursor != null && cursor.moveToFirst()) {
                val title = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
                val artist = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
                val date = cursor.getColumnIndex(MediaStore.Audio.Media.DATE_ADDED)
                val album = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)
                val duration = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)
                val totalseconds = duration / 1000
                val min = totalseconds / 60
                val seconds = totalseconds - min * 60
                val size = cursor.getColumnIndex(MediaStore.Audio.Media.SIZE)
                val path = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
                do {
                    val currenttitle = cursor.getString(title)
                    val currentartist = cursor.getString(artist)
                    val currentdate = cursor.getString(date)
                    val currentalbum = cursor.getString(album)
                    val currentsize = cursor.getString(size)
                    val currentpath = cursor.getString(path)
                    list!!.add(
                        """
                            $currenttitle
                            $currentartist
                            $currentdate
                            $currentalbum
                            $currentpath
                            $currentsize
                            $duration
                            """.trimIndent()
                    )
                } while (cursor.moveToNext())
            }
        }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            MY_REQ -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(
                            this@HomeActivity,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                        getallMusic()
                    } else {
                        Toast.makeText(this, "Permission not Granted", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    return
                }
            }
        }
    }

    companion object {
        private const val MY_REQ = 1
    }
}