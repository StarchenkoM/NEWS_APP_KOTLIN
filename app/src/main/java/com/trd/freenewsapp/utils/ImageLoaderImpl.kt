package com.trd.freenewsapp.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.request.RequestOptions

class ImageLoaderImpl(val context: Context) : ImageLoader {
    override fun loadImage(
        imageUrl: String?,
        targetSource: ImageView,
        @DrawableRes defaultImage: Int
    ) {
        if (!imageUrl.isNullOrEmpty()) {
            Glide.with(context)
                .load(imageUrl)
                .apply(RequestOptions.bitmapTransform(GranularRoundedCorners(18f.toDp(), 18f.toDp(), 0f, 0f)))
                .into(targetSource)
        } else {
            targetSource.setImageResource(defaultImage)
        }
    }

    override fun loadImage(
        bitmap: Bitmap?,
        targetSource: ImageView,
        @DrawableRes defaultImage: Int
    ) {
        if (bitmap != null) {
            Glide.with(context)
                .load(bitmap)
                .apply(RequestOptions.bitmapTransform(GranularRoundedCorners(16f, 16f, 0f, 0f)))
                .into(targetSource)
        } else {
            targetSource.setImageResource(defaultImage)
        }
    }

    private fun Float.toDp(): Float =
        (this * Resources.getSystem().displayMetrics.density).toFloat()
}