package com.sil.ecohero.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.sil.ecohero.ui.scanResult.ScanResultActivity
import com.sil.ecohero.R
import com.sil.ecohero.databinding.LoadingResultBinding

class LoadingResult : AppCompatActivity() {

    private lateinit var binding: LoadingResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoadingResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        Glide.with(this)
            .asGif()
            .load(R.drawable.loading)
            .into(binding.wait)

        Handler(Looper.getMainLooper()).postDelayed({
            val imageUri = intent.getStringExtra("imageUri")
            val intent = Intent(this, ScanResultActivity::class.java)
            intent.putExtra("imageUri", imageUri)
            startActivity(intent)
            finish()
        },3000)

    }

}