package com.sil.ecohero.ui.scanResult

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.sil.ecohero.R
import com.sil.ecohero.databinding.ActivityScanResultBinding
import java.io.*

class ScanResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScanResultBinding
    private lateinit var viewModel: ScanResultViewModel
    private val SAVE_PDF_REQUEST_CODE = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Hasil Scan"
            setHomeAsUpIndicator(R.drawable.ic_arrow_back_24)
        }

        val imageUri = intent.getStringExtra("imageUri")
        val bitmap = BitmapFactory.decodeFile(imageUri)

        binding.photoReceived.setImageBitmap(bitmap)

        viewModel = ViewModelProvider(this)[ScanResultViewModel::class.java]

        viewModel.errorMessage.observe(this) { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        }

        viewModel.scanResult.observe(this) { result ->
            processResult(result)
        }

        imageUri?.let {
            viewModel.processImage(it)
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

//    private fun getCancer(): List<Cancer> {
//        return CancerData.cancer
//    }

    @SuppressLint("StringFormatInvalid")
    private fun processResult(result: ScanResultViewModel.ClassificationResult) {

        val className = result.className
        val confidence = result.confidence.toString()

        var resultScan = ""

        if (className == "organic") {
            resultScan = "Sampah Organik"
        } else if (className == "inorganic") {
            resultScan = "Sampah Anorganik"
        } else if (className == "hazardous") {
            resultScan = "Sampah B3"
        }

        val resultText = resultScan
        val acurationText = "Nilai Akurasi: " + confidence

        binding.nameReceived.text = resultText
        binding.confidence.text = acurationText



        val dataDescArray = resources.getStringArray(R.array.data_desc)
        val descriptionText: String = when (resultScan) {
            "Sampah Organik" -> dataDescArray[0]
            "Sampah Anorganik" -> dataDescArray[1]
            "Sampah B3" -> dataDescArray[2]
            else -> dataDescArray.lastOrNull() ?: "-"
        }

        binding.description.text = descriptionText



    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SAVE_PDF_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                val inputStream = FileInputStream(File(getExternalFilesDir(null), "scan_result.pdf"))
                val outputStream = contentResolver.openOutputStream(uri)
                outputStream?.let { inputStream.copyTo(it) }
                inputStream.close()
                outputStream?.close()
                Toast.makeText(this, "PDF file saved", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}