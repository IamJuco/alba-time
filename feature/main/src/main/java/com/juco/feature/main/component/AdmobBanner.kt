package com.juco.feature.main.component

import android.content.res.Resources
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.juco.feature.main.BuildConfig

@Composable
fun AdmobBanner(modifier: Modifier = Modifier) {
    val screenWidth = remember { Resources.getSystem().displayMetrics.widthPixels }
    val density = LocalDensity.current.density
    val adWidth = (screenWidth / density).toInt()
    AndroidView(
        modifier = modifier.fillMaxWidth(),
        factory = { context ->
            AdView(context).apply {
                setAdSize(
                    AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(
                        context,
                        adWidth
                    )
                )
                adUnitId = BuildConfig.ADMOB_BANNER_ID
                loadAd(AdRequest.Builder().build())
            }
        }
    )
}