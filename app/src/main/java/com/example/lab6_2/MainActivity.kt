package com.example.lab6_2

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lab6_2.databinding.ActivityMainBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import android.graphics.BitmapFactory
import androidx.lifecycle.MutableLiveData
import java.io.BufferedInputStream
import java.net.URL
import java.util.concurrent.Future


class MainActivity : AppCompatActivity() {
    private var future: Future<*>? = null
    private lateinit var binding: ActivityMainBinding
    private val bitmapData = MutableLiveData<Bitmap>()
    private lateinit var myApp: MyApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        myApp = application as MyApplication

        if (myApp.imgBitmap == null) {
            future = loadImageFromNet()
        } else {
            binding.imgView.setImageBitmap(myApp.imgBitmap)
        }

        bitmapData.observe(this) { value ->
            if (value != null) {
                binding.imgView.setImageBitmap(value)
            }
            myApp.imgBitmap = value
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        future?.cancel(true)
    }

    private fun loadImageFromNet(): Future<*> {
        return myApp.executor.submit {
            val url = URL("https://cdnuploads.aa.com.tr/uploads/Contents/2019/10/24/thumbs_b_c_fb8263ce4f9f43ebdc7634b0d1eb0a08.jpg?v=115427")
            val stream = url.openConnection().getInputStream()
            stream.use {
                val bitmap = BitmapFactory.decodeStream(stream)
                bitmapData.postValue(bitmap)
            }
        }
    }
}