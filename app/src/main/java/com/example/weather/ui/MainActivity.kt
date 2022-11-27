package com.example.weather.ui

import android.*
import android.content.*
import android.content.pm.*
import android.net.*
import android.os.*
import android.provider.*
import android.widget.*
import androidx.activity.result.contract.*
import androidx.annotation.*
import androidx.appcompat.app.*
import androidx.core.app.*
import androidx.core.content.*
import com.example.weather.appbase.utils.*
import com.example.weather.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewBinding: ActivityMainBinding by viewBinding()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

    }



}