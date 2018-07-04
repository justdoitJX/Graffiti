package com.justdoit.graffiti

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable

data class GraffitiPosition(
    val x: Int,
    val y: Int,
    val picId: String,
    var bitmap: Bitmap


) : Parcelable {
    constructor(source: Parcel) : this(
        source.readInt(),
        source.readInt(),
        source.readString(),
        source.readParcelable<Bitmap>(Bitmap::class.java.classLoader)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(x)
        writeInt(y)
        writeString(picId)
        writeParcelable(bitmap, 0)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<GraffitiPosition> = object : Parcelable.Creator<GraffitiPosition> {
            override fun createFromParcel(source: Parcel): GraffitiPosition = GraffitiPosition(source)
            override fun newArray(size: Int): Array<GraffitiPosition?> = arrayOfNulls(size)
        }
    }
}