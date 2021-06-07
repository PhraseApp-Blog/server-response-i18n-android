package com.elixer.paws.ui

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.elixer.paws.R
import com.elixer.paws.util.MyImageLoader
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener() {
            viewModel.loadImage(edittext_breed.text.toString())
            try {
                val imm: InputMethodManager =
                    getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
            } catch (e: Exception) {
            }
        }
        observerLiveData()
    }

    private fun observerLiveData() {

        viewModel.imageUrl.observe(this, { image ->
            if (image.isNullOrBlank()) {
                MyImageLoader.loadImage(this, R.drawable.placeholder, imageView_dog)
            } else {
                MyImageLoader.loadImage(this, image, imageView_dog)
            }
        })

//        viewModel.status.observe(this, { status ->
//            textview_status.text = status
//        })

        viewModel.statusResourceId.observe(this, { statusResourceId ->
            textview_status.text = statusResourceId?.let { getString(it) }
        })
        viewModel.loading.observe(this, { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
        })
    }
}