package com.kuteapp.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.media.ExifInterface
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import com.android.kuteapp.R
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Anirudh_Sharma on 12-Jun-18.
 */
class ImageUtils(private val context: Context) {

    fun cameraGalleryIntent(activity: Activity, cameraRequestCode: Int, galleryRequestCode: Int): Uri? {
        val root = getFile()
        root.mkdirs()
        val filename = getUniqueImageFilename()
        val sdImageMainDirectory = File(root, filename)
        val outputFileUri = FileProvider.getUriForFile(activity, "com.sampleapp.provider",
                sdImageMainDirectory)
        val dialog = AlertDialog.Builder(activity)
        val items = arrayOf<CharSequence>(activity.getString(R.string.camera), activity.getString(R.string.gallery))
        dialog.setItems(items) { _, n ->
            when (n) {
                0 -> {
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri)
                    if (intent.resolveActivity(activity.packageManager) != null)
                        activity.startActivityForResult(intent, cameraRequestCode)
                }
                1 -> {
                    val pickIntent = Intent(Intent.ACTION_PICK)
                    pickIntent.type = "image/*"
                    activity.startActivityForResult(pickIntent, galleryRequestCode)
                }
            }
        }
        dialog.setTitle(activity.getString(R.string.selection))
        if (outputFileUri != null) {
            dialog.show()
        }
        return outputFileUri
    }

    /**
     * Compresses the image
     *
     * @param filePath : Location of image file
     * @return
     */
    fun compressImage(filePath: String): String {
        var scaledBitmap: Bitmap? = null

        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        var bmp: Bitmap? = BitmapFactory.decodeFile(filePath, options)

        var actualHeight = options.outHeight
        var actualWidth = options.outWidth
        val maxHeight = 816.0f
        val maxWidth = 612.0f
        var imgRatio = (actualWidth / actualHeight).toFloat()
        val maxRatio = maxWidth / maxHeight

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight
                actualWidth = (imgRatio * actualWidth).toInt()
                actualHeight = maxHeight.toInt()
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth
                actualHeight = (imgRatio * actualHeight).toInt()
                actualWidth = maxWidth.toInt()
            } else {
                actualHeight = maxHeight.toInt()
                actualWidth = maxWidth.toInt()
            }
        }

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight)
        options.inJustDecodeBounds = false
        options.inDither = false
        options.inPurgeable = true
        options.inInputShareable = true
        options.inTempStorage = ByteArray(16 * 1024)

        try {
            bmp = BitmapFactory.decodeFile(filePath, options)
        } catch (exception: OutOfMemoryError) {
            exception.printStackTrace()

        }

        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888)
        } catch (exception: OutOfMemoryError) {
            exception.printStackTrace()
        }

        val ratioX = actualWidth / options.outWidth.toFloat()
        val ratioY = actualHeight / options.outHeight.toFloat()
        val middleX = actualWidth / 2.0f
        val middleY = actualHeight / 2.0f

        val scaleMatrix = Matrix()
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY)

        val canvas = Canvas(scaledBitmap!!)
        canvas.matrix = scaleMatrix
        canvas.drawBitmap(bmp!!, middleX - bmp.width / 2, middleY - bmp.height / 2, Paint(
                Paint.FILTER_BITMAP_FLAG))

        val exif: ExifInterface
        try {
            exif = ExifInterface(filePath)

            val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0)
            Log.e("EXIF", "Exif: $orientation")
            val matrix = Matrix()
            if (orientation == 6) {
                matrix.postRotate(90f)
                Log.e("EXIF", "Exif: $orientation")
            } else if (orientation == 3) {
                matrix.postRotate(180f)
                Log.e("EXIF", "Exif: $orientation")
            } else if (orientation == 8) {
                matrix.postRotate(270f)
                Log.e("EXIF", "Exif: $orientation")
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.width, scaledBitmap.height,
                    matrix, true)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        var out: FileOutputStream? = null
        val filename = getFilename()
        try {
            out = FileOutputStream(filename)
            scaledBitmap!!.compress(Bitmap.CompressFormat.JPEG, 95, out)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } finally {
            if (bmp != null) {
                bmp.recycle()
                bmp = null
            }
            if (scaledBitmap != null) {
                scaledBitmap.recycle()
            }
        }
        return filename

    }

    //get unique file name
    private fun getFilename(): String {
        val root = getFile()
        root.mkdirs()
        val filename = getUniqueImageFilename()
        val file = File(root, filename)
        return file.absolutePath
    }

    //get unique file
    fun getUniqueFile(): File {
        val root = getFile()
        root.mkdirs()
        val filename = getUniqueImageFilename()
        return File(root, filename)
    }

    //create a file
    private fun getFile(): File {
        return File(Environment.getExternalStorageDirectory().toString() + File.separator +
                context.resources.getString(R.string.app_name) + File.separator)
    }

    private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round(height.toFloat() / reqHeight.toFloat())
            } else {
                inSampleSize = Math.round(width.toFloat() / reqWidth.toFloat())
            }
        }
        return inSampleSize
    }

    private fun getUniqueImageFilename(): String {
        return "img_" + System.currentTimeMillis() + ".png"
    }

    //get absolute file path
    fun getRealPathFromURI(contentUri: Uri): String {
        // can post image
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = (context as Activity).managedQuery(contentUri, proj, // WHERE clause selection arguments (none)
                null, null, null)// Which
        // columns
        // to
        // return
        // WHERE clause; which rows to return (all rows)
        // Order-by clause (ascending by name)
        val column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        return cursor.getString(column_index)
    }


    /**
     * convert image to base 64 string
     *
     * @param filePath path of image
     * @return base 64 string
     */
    fun getBase64Image(filePath: String): String {
        var filePath = filePath
        filePath = getCompressedImage(filePath)
        val bm = BitmapFactory.decodeFile(filePath)
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos) //bm is the bitmap object
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    /**
     * convert bitmap to base 64 image
     */
    fun convertBitmapToBase64(bmp: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos) //bm is the bitmap object
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    /**
     * get compressed image
     */
    private fun getCompressedImage(filePath: String): String {
        val newFilePath: String
        val file_size = Integer.parseInt((File(filePath).length() shr 10).toString())
        if (file_size >= 120) {
            newFilePath = compressImage(filePath)
        } else {
            newFilePath = filePath
        }
        return newFilePath
    }


    fun storeImage(image: Bitmap): String {
        val pictureFile = getOutputMediaFile()
        if (pictureFile == null) {
            Log.d("TAG",
                    "Error creating media file, check storage permissions: ")// e.getMessage());
            return ""
        }
        try {
            val fos = FileOutputStream(pictureFile)
            image.compress(Bitmap.CompressFormat.PNG, 90, fos)
            fos.close()
        } catch (e: FileNotFoundException) {
            Log.d("TAG", "File not found: " + e.message)
        } catch (e: IOException) {
            Log.d("TAG", "Error accessing file: " + e.message)
        }

        return pictureFile.absolutePath
    }

    /**
     * Create a File for saving an image or video
     */
    private fun getOutputMediaFile(): File? {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        val mediaStorageDir = File(Environment.getExternalStorageDirectory().toString()
                + "/Android/data/"
                + context.packageName
                + "/Files")

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null
            }
        }
        // Create a media file name
        val timeStamp = SimpleDateFormat("ddMMyyyy_HHmm").format(Date())
        val mediaFile: File
        val mImageName = "MI_$timeStamp.jpg"
        mediaFile = File(mediaStorageDir.path + File.separator + mImageName)
        return mediaFile
    }
}