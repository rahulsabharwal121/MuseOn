package com.example.museon.models

class SongsModel {
        var aPath: String? = null
        var aName: String? = null
        var aAlbum: String? = null
        var aArtist: String? = null
        var length: Long? = 0

        fun getalength(): Long? {
            return length
        }

        fun setalength(aPath: Long?) {
            this.length = length
        }

        fun getaPath(): String? {
            return aPath
        }

        fun setaPath(aPath: String?) {
            this.aPath = aPath
        }

        fun getaName(): String? {
            return aName
        }

        fun setaName(aName: String?) {
            this.aName = aName
        }

        fun getaAlbum(): String? {
            return aAlbum
        }

        fun setaAlbum(aAlbum: String?) {
            this.aAlbum = aAlbum
        }

        fun getaArtist(): String? {
            return aArtist
        }

        fun setaArtist(aArtist: String?) {
            this.aArtist = aArtist
        }
    }