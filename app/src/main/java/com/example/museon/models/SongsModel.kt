
package com.internshala.echo

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable

@SuppressLint("ParcelCreator")
class SongsModel(var songID: Long, var songTitle: String, var artist: String,
            var songData: String, var dateAdded: Long) : Parcelable {
    override fun writeToParcel(p0: Parcel?, p1: Int) {
    }

    override fun describeContents(): Int {
        return 0
    }
}