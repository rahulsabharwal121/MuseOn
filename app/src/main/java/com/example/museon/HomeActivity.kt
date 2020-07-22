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
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*

class HomeActivity : AppCompatActivity() {
    var list: ArrayList<String>? = null
    var listView: ListView? = null
    var adapter: ArrayAdapter<String>? = null
    val MY_REQ =1;
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
        }
    }
    }

