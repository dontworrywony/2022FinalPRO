package com.example.aluapplication

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.firebase.ui.storage.images.FirebaseImageLoader
import com.google.firebase.storage.StorageReference
import java.io.InputStream

//Glide

@GlideModule
class MyAppGlideModule : AppGlideModule() {     //정해진 방식이 아닌 내가 정한 방식대로 이미지를 불러올 때 사용
    override fun registerComponents(context: Context, glide: Glide, registry: Registry) { //firebase 속 storage에 있는 이미지를 불러옴
        // Register FirebaseImageLoader to handle StorageReference
        registry.append(
            StorageReference::class.java, InputStream::class.java,
            FirebaseImageLoader.Factory()
        )
    }
}