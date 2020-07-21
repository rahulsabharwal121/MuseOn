package com.example.museon

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.museon.fragments.*
import com.example.museon.models.SongsModel
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*

class HomeActivity : AppCompatActivity() {
    var list: ArrayList<String>? = null
    var listView: ListView? = null
    var adapter: ArrayAdapter<String>? = null

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    val homefragment = HomeFragment()
                    openFragment(homefragment)
                    true
                }
                R.id.album -> {
                    val albumfragment = AlbumsFragment()
                    openFragment(albumfragment)
                    true
                }

                R.id.songs -> {
                    val songsfragment = SongsFragment()
                    openFragment(songsfragment)
                    true
                }

                R.id.playlists -> {
                    val playlistfragment = PlaylistsFragment()
                    openFragment(playlistfragment)
                    true
                }
                R.id.artists -> {
                    val artistfragment = ArtistFragment()
                    openFragment(artistfragment)
                    true
                }
            }
            true
        }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.framelayout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        addupdatePermissions();

        val bottomnavbar: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomnavbar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        val fragment = HomeFragment()
        openFragment(fragment)
    }


    @RequiresApi(Build.VERSION_CODES.Q)
    fun addupdatePermissions() {
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
    getSongs(this);
    adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list!!)
    listView!!.adapter = adapter
    listView!!.onItemClickListener =
        OnItemClickListener { adapterView, view, i, l -> }
}

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
        val cursor = contentResolver.query(
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
                val currentalbum = cursor.getString(album)
                audioModel.setaName(currenttitle)
                audioModel.setaArtist(currentartist)
                audioModel.setaAlbum(currentalbum)
                audioModel.setalength(dur)

                list!!.add(
                    """
                            $currenttitle
                            $currentartist
                            """.trimIndent()
                )

            } while (cursor.moveToNext())
            cursor.close()
        }
        return tempAudioList;
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