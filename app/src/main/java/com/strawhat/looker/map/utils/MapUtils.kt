package com.strawhat.looker.map.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun getBitmapDescriptorFromVector(
    context: Context,
    id: Int,
    width: Int = 24,
    height: Int = 24
): BitmapDescriptor {
    val vectorDrawable = context.getDrawable(id)
    val h = (width * context.resources.displayMetrics.density).toInt();
    val w = (height * context.resources.displayMetrics.density).toInt();
    vectorDrawable?.setBounds(0, 0, w, h)
    val bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bm)
    vectorDrawable?.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bm)
}

fun CameraPositionState.animationToPosition(
    position: LatLng,
    coroutineScope: CoroutineScope,
) {
    coroutineScope.launch {
        try {
            this@animationToPosition.animate(
                CameraUpdateFactory
                    .newCameraPosition(
                        CameraPosition
                            .Builder()
                            .target(position)
                            .zoom(this@animationToPosition.position.zoom)
                            .build()
                    )
            )
        } catch (e: CancellationException) {
            e.printStackTrace()
        }
    }
}