package com.example.decor_plant.Activity

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import coil.load
import com.example.decor_plant.R
import com.example.decor_plant.ml.LiteModelAiyVisionClassifierPlantsV13
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.label.Category

class FindPlantNameActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val TAG = "FIND MY PLANT"
        private const val PERMISSION_CODE = 1000
        private const val IMAGE_PICTURE_CODE = 1001
        private const val GALLERY_PICTURE_CODE = 1002
    }

    private lateinit var ivFindPlantName: ImageView
    private lateinit var btnTakePhoto: Button
    private lateinit var btnOpenGallery: Button
    private lateinit var btnBackFindPlantName: ImageButton
    private lateinit var tvFindPlantNameResultLabel: TextView
    private lateinit var tvFindPlantNameResultScore: TextView
    private lateinit var bitmap: Bitmap
    private var photoUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_plant_name)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        ivFindPlantName = findViewById(R.id.ivFindPlantName)
        btnOpenGallery = findViewById(R.id.btnOpenGallery)
        btnTakePhoto = findViewById(R.id.btnTakePhoto)
        btnBackFindPlantName = findViewById(R.id.btnBackFindPlantName)
        tvFindPlantNameResultLabel = findViewById(R.id.tvFindPlantNameResultLabel)
        tvFindPlantNameResultScore = findViewById(R.id.tvFindPlantNameResultScore)

        btnTakePhoto.setOnClickListener(this)
        btnOpenGallery.setOnClickListener(this)
        btnBackFindPlantName.setOnClickListener(this)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == IMAGE_PICTURE_CODE && resultCode == Activity.RESULT_OK) {
            ivFindPlantName.load(photoUri)
            bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, photoUri)
            analyzeImage(bitmap)
        }
        else if (requestCode == GALLERY_PICTURE_CODE && resultCode == Activity.RESULT_OK) {
            var uri: Uri? = data?.data
            ivFindPlantName.load(uri)
            bitmap =MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
            analyzeImage(bitmap)
        }
        else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnTakePhoto -> {
                checkPhotoPermision()
            }
            R.id.btnOpenGallery -> {
                openGallery()
            }
            R.id.btnBackFindPlantName -> {
                finish()
            }
        }
    }

    private fun takePhoto() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the camera")

        photoUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values)!!
        //camera intent
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra (MediaStore.EXTRA_OUTPUT, photoUri)
        startActivityForResult(cameraIntent, IMAGE_PICTURE_CODE)
    }

    private fun openGallery() {
        val intent = Intent()
        intent.type ="image/"
        intent.action = Intent.ACTION_PICK
        startActivityForResult(intent, GALLERY_PICTURE_CODE)
    }

    private fun analyzeImage(bitmap: Bitmap) {
        var resized: Bitmap = Bitmap.createScaledBitmap(bitmap, 244, 244, true)
        val model = LiteModelAiyVisionClassifierPlantsV13.newInstance(this)

        var tbuffer = TensorImage.fromBitmap(resized)

        val outputs = model.process(tbuffer)
        val outputFeature = outputs.probabilityAsCategoryList

        var max = getMaxIndexScore(outputFeature)
        tvFindPlantNameResultScore.text = outputFeature[max].score.toString()
        tvFindPlantNameResultLabel.text = outputFeature[max].label

        Log.d(TAG, "Analyze success | ${outputFeature[max].score.toString()}")

    }

    private fun checkPhotoPermision() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED) {
                // permission ditolak
                val permission = arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                // nampilin popup request permission
                requestPermissions(permission, PERMISSION_CODE)
            }
            else{
                takePhoto()
            }
        }
        else{
            takePhoto()
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // dipanggil ketika user menekan ALLOW atau DENY
        when (requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults [0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup was granted
                    takePhoto()
                }
            }
        }
    }

    private fun getMaxIndexScore(outputFeature: List<Category>): Int {
        var index = 0
        var min = 0.0f

        for (i in 0..1000) {
            if (outputFeature[i].score > min) {
                index = i
                min = outputFeature[i].score
            }
        }
        return index
    }

}
