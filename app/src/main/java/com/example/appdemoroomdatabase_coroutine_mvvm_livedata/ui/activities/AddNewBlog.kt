package com.example.appdemoroomdatabase_coroutine_mvvm_livedata.ui.activities

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.appdemoroomdatabase_coroutine_mvvm_livedata.R
import com.example.appdemoroomdatabase_coroutine_mvvm_livedata.databinding.ActivityAddNewBlogBinding
import com.example.appdemoroomdatabase_coroutine_mvvm_livedata.databinding.DialogCustomImageSelectionBinding
import com.example.appdemoroomdatabase_coroutine_mvvm_livedata.entity.Blog
import com.example.appdemoroomdatabase_coroutine_mvvm_livedata.viewmodel.BlogViewModel
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class AddNewBlog : AppCompatActivity(), View.OnClickListener {

    private lateinit var blogViewModel: BlogViewModel
    private lateinit var mBinding: ActivityAddNewBlogBinding
    private var mImagePath: String = ""

    companion object {
        private const val CAMERA = 1
        private const val GALLERY = 2
        private const val IMAGE_PATH_DIRECTORY = "BlogDetails"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_add_new_blog)

        mBinding = ActivityAddNewBlogBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.ivAddBlogImage.setOnClickListener(this)
        mBinding.btnPublishBlog.setOnClickListener(this)


    }

    private fun checkValidations(): Boolean {
        return TextUtils.isEmpty(mBinding.etTitle.text.toString().trim { it <= ' ' }) ||
                TextUtils.isEmpty(mBinding.etAuthor.text.toString().trim { it <= ' ' }) ||
                TextUtils.isEmpty(mBinding.etDescription.text.toString().trim { it <= ' ' })
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.btn_publish_blog -> {
                    if (checkValidations()) {
                        Toast.makeText(
                            this,
                            "All fields should be required !!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        val blog = Blog(
                            ,
                            mBinding.etTitle.text.toString(),
                            mBinding.etAuthor.text.toString(),
                            mBinding.etDescription.text.toString()
                        )
                    }
                }

                R.id.iv_add_blog_image -> {
                    customImageSelectionDialog()
                }
            }
        }
    }

    private fun customImageSelectionDialog() {
        val dialog = Dialog(this)
        val binding: DialogCustomImageSelectionBinding =
            DialogCustomImageSelectionBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)

        binding.tvCamera.setOnClickListener {
            Dexter.withContext(this).withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    report?.let {
                        if (report.areAllPermissionsGranted()) {
                            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                            @Suppress("DEPRECATION")
                            startActivityForResult(intent, 1)
                        }

                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    request: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    showRationalDialogForPermission()
                }

            })
        }

        binding.tvGallery.setOnClickListener {
            Dexter.withContext(this)
                .withPermission(
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                        val galleryIntent =
                            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        @Suppress("DEPRECATION")
                        startActivityForResult(galleryIntent, GALLERY)
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                        Toast.makeText(
                            this@AddNewBlog,
                            "You have to allow camera permission before continue",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        request: PermissionRequest?,
                        token: PermissionToken?
                    ) {
                        showRationalDialogForPermission()
                    }

                })
                .onSameThread()
                .check()
        }
        dialog.show()

    }

    private fun showRationalDialogForPermission() {
        AlertDialog.Builder(this)
            .setMessage("It seems that you disabled the Settings You need to apply before continue")
            .setPositiveButton("GO TO SETTINGS") { _, _ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            .setNegativeButton("CANCEL") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA) {
                data!!.extras?.let {
                    val thumbnai: Bitmap = data.extras?.get("data") as Bitmap

                    try {
                        Glide.with(this)
                            .load(thumbnai)
                            .centerCrop()
                            .listener(object : RequestListener<Drawable> {
                                override fun onLoadFailed(
                                    e: GlideException?,
                                    model: Any?,
                                    target: Target<Drawable>?,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    return false
                                }

                                override fun onResourceReady(
                                    resource: Drawable?,
                                    model: Any?,
                                    target: Target<Drawable>?,
                                    dataSource: DataSource?,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    resource?.let {
                                        Palette.from(resource.toBitmap()).generate() { palette ->
                                            val intColor = palette?.vibrantSwatch?.rgb ?: 0
                                            mBinding.addPublishBlog.setBackgroundColor(intColor)
                                        }
                                    }
                                    return false
                                }
                            })
                            .into(mBinding.ivBlogImage)
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                    mImagePath = saveImageToExternalStorage(thumbnai)

                    mBinding.ivAddBlogImage.setImageDrawable(
                        ContextCompat.getDrawable(
                            this,
                            R.drawable.ic_vector_edit
                        )
                    )
                }
            }
        }
    }

    private fun saveImageToExternalStorage(bitmap: Bitmap): String {
        val wrapper = ContextWrapper(applicationContext)
        var file = wrapper.getDir(IMAGE_PATH_DIRECTORY, Context.MODE_PRIVATE)
        file = File(file, "${UUID.randomUUID()}.jpg")

        try {
            val stream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return file.absolutePath
    }
}