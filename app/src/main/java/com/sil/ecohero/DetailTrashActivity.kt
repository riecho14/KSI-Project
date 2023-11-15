package com.sil.ecohero

import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.sil.ecohero.databinding.ActivityDetailTrashBinding

class DetailTrashActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailTrashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTrashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Detail"
            setHomeAsUpIndicator(R.drawable.ic_arrow_back_24)
        }

        val trash = if (Build.VERSION.SDK_INT>=33){
            intent.getParcelableExtra(KEY_TRASH, Trash::class.java)
        }else{
            intent.getParcelableExtra(KEY_TRASH)
        }

        val tvPhoto:ImageView = binding.photoReceived
        val tvName: TextView = binding.nameReceived
        val tvDescription: TextView = binding.description

        if(trash != null){
            Glide.with(this)
                .load(trash.photo)
                .into(tvPhoto)
            tvName.text = trash.name
            tvDescription.text = trash.desc
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object{
        const val KEY_TRASH = "key_trash"
    }
}