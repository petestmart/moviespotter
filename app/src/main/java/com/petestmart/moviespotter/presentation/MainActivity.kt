package com.petestmart.moviespotter.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.petestmart.moviespotter.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
