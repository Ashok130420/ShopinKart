package com.example.shopinkarts.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.DownloadManager
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.*
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.example.shopinkarts.R
import com.example.shopinkarts.adapter.InvoiceAdapter
import com.example.shopinkarts.classes.SharedPreference
import com.example.shopinkarts.classes.Utils
import com.example.shopinkarts.databinding.ActivityInvoiceBinding
import com.example.shopinkarts.fragments.OrdersFragment
import com.example.shopinkarts.model.CreateProduct
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.DecimalFormat
import java.util.*
import java.util.Calendar.getInstance


class InvoiceActivity : AppCompatActivity() {

    lateinit var binding: ActivityInvoiceBinding
    lateinit var sharedPreference: SharedPreference
    lateinit var invoiceAdapter: InvoiceAdapter
    var position = 0
    var arrayList: List<CreateProduct> = ArrayList()
    var gstAmount = 0F
    var pdfNumber = 1
    var orderId = ""
    var userType = ""

//    var pageHeight = 1120
//    var pageWidth = 792
//    lateinit var bmp: Bitmap
//    lateinit var scaledbmp: Bitmap
//    var PERMISSION_CODE = 101

    companion object {
        val mInstance: InvoiceActivity = InvoiceActivity()

        fun getInstance(): InvoiceActivity {
            return mInstance
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        Utils.changeStatusTextColor(this)
        Utils.changeStatusColor(this, R.color.white)
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_invoice)

        orderId = intent.extras!!.getString("orderId", "")
        Log.d("orderId", orderId.toString())

        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1
        )
        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1
        )

        sharedPreference = SharedPreference(this)
        userType = sharedPreference.getUserType().toString()

        binding.back.setOnClickListener {
            onBackPressed()
        }


//        pdf

//        bmp = BitmapFactory.decodeResource(resources, R.drawable.logo_blue)
//        scaledbmp = Bitmap.createScaledBitmap(bmp, 140, 140, false)
//
//        // on below line we are checking permission
//        if (checkPermissions()) {
//            // if permission is granted we are displaying a toast message.
//            Toast.makeText(this, "Permissions Granted..", Toast.LENGTH_SHORT).show()
//        } else {
//            // if the permission is not granted
//            // we are calling request permission method.
//            requestPermission()
//        }

        binding.downloadInvoiceTV.setOnClickListener {

//                generatePDF()

            val bitmap = getScreenShotFromView(binding.invoiceCL)

            if (bitmap != null) {
                saveMediaToStorage(bitmap)
            }

        }


        /* binding.downloadInvoiceTV.setOnClickListener(object : View.OnClickListener {
             override fun onClick(v: View?) {
                 val receipt = ExamplePdfMaker(this@InvoiceActivity)
                 val pdfFilePath: String? = receipt.buildPdf()
             }
         })*/
        position = intent.extras!!.getInt("position", 0)

        if (userType == "0") {
            binding.customerNameTV.text =
                "${sharedPreference.getName()}\n${sharedPreference.getFlat()},${sharedPreference.getStreet()}, ${sharedPreference.getPin()},${sharedPreference.getCity()},${sharedPreference.getLandmark()}"

        } else {
            binding.customerNameTV.text =
                "${sharedPreference.getBusinessName()}\n${sharedPreference.getBusinessFlat()},${sharedPreference.getBusinessStreet()}, ${sharedPreference.getBusinessPin()},${sharedPreference.getBusinessCity()},${sharedPreference.getBusinessLandmark()}"

        }


        binding.orderIdTV.text = "Order Id-${orderId}"

        binding.paymentTV.text =
            "COD : Collect amount \nRS " + DecimalFormat(".00").format(OrdersFragment.arrayListMyOrders[position].finalAmount + OrdersFragment.arrayListMyOrders[position].gstAmount)+"/-"

        arrayList = OrdersFragment.arrayListMyOrders[position].products

        binding.tAmountValueTV.text =
            "RS " + DecimalFormat(".00").format(OrdersFragment.arrayListMyOrders[position].finalAmount)

        binding.gstTaxValueTV.text =
            "RS " + DecimalFormat(".00").format(OrdersFragment.arrayListMyOrders[position].gstAmount)

        binding.totalValueTV.text =
            "RS " + DecimalFormat(".00").format(OrdersFragment.arrayListMyOrders[position].finalAmount + OrdersFragment.arrayListMyOrders[position].gstAmount)

        gstAmount = OrdersFragment.arrayListMyOrders[position].gstAmount

        invoiceAdapter = InvoiceAdapter(this, arrayList)
        binding.invoiceDetailsRV.adapter = invoiceAdapter
        binding.invoiceDetailsRV.hasFixedSize()
        binding.invoiceDetailsRV.isNestedScrollingEnabled = false


    }

    /* @RequiresApi(Build.VERSION_CODES.R)
     fun generatePDF() {

         val pdfDocument: PdfDocument = PdfDocument()

         val paint: Paint = Paint()
         val title: Paint = Paint()

         val myPageInfo: PdfDocument.PageInfo? =
             PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()


         val myPage: PdfDocument.Page = pdfDocument.startPage(myPageInfo)


         val canvas: Canvas = myPage.canvas


         canvas.drawBitmap(scaledbmp, 56F, 40F, paint)


         title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL))


         title.textSize = 15F


         title.setColor(ContextCompat.getColor(this, R.color.purple_200))


         canvas.drawText("A portal for IT professionals.", 209F, 100F, title)
         canvas.drawText("Geeks for Geeks", 209F, 80F, title)
         title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL))
         title.setColor(ContextCompat.getColor(this, R.color.purple_200))
         title.textSize = 15F


         title.textAlign = Paint.Align.CENTER
         canvas.drawText("This is sample document which we have created.", 396F, 560F, title)


         pdfDocument.finishPage(myPage)

         val file: File = File(Environment.getExternalStorageDirectory(), "GFG.pdf")

         try {

             pdfDocument.writeTo(FileOutputStream(file))


             Toast.makeText(applicationContext, "PDF file generated..", Toast.LENGTH_SHORT).show()
         } catch (e: Exception) {

             e.printStackTrace()


             Toast.makeText(applicationContext, "Fail to generate PDF file..", Toast.LENGTH_SHORT)
                 .show()
         }

         pdfDocument.close()
     }

     fun checkPermissions(): Boolean {

         val writeStoragePermission = ContextCompat.checkSelfPermission(
             applicationContext, WRITE_EXTERNAL_STORAGE
         )

         val readStoragePermission = ContextCompat.checkSelfPermission(
             applicationContext, READ_EXTERNAL_STORAGE
         )

         return writeStoragePermission == PackageManager.PERMISSION_GRANTED && readStoragePermission == PackageManager.PERMISSION_GRANTED
     }


     fun requestPermission() {

         ActivityCompat.requestPermissions(
             this, arrayOf(READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE), PERMISSION_CODE
         )
     }


     override fun onRequestPermissionsResult(
         requestCode: Int, permissions: Array<out String>, grantResults: IntArray
     ) {
         super.onRequestPermissionsResult(requestCode, permissions, grantResults)


         if (requestCode == PERMISSION_CODE) {

             if (grantResults.isNotEmpty()) {

                 if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                     Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show()

                 } else {

                     Toast.makeText(this, "Permission Denied..", Toast.LENGTH_SHORT).show()
                     finish()
                 }
             }
         }
     }*/

    private fun getScreenShotFromView(v: View): Bitmap? {
        // create a bitmap object

        binding.downloadInvoiceTV.visibility = View.INVISIBLE
        binding.back.visibility = View.INVISIBLE

        var screenshot: Bitmap? = null
        try {

            screenshot =
                Bitmap.createBitmap(v.measuredWidth, v.measuredHeight, Bitmap.Config.ARGB_8888)
            // Now draw this bitmap on a canvas
            Log.d("screenshot", screenshot.toString())

            val canvas = Canvas(screenshot)
            v.draw(canvas)
        } catch (e: Exception) {
            Log.e("GFG", "Failed to capture screenshot because:" + e.message)
        }
        // return the bitmap
        return screenshot
    }

    private fun saveMediaToStorage(bitmap: Bitmap) {
        // Generating a file name
        val filename = "${System.currentTimeMillis()}.jpg"

        // Output stream
        var fos: OutputStream? = null

        // For devices running android >= Q
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // getting the contentResolver
            this.contentResolver?.also { resolver ->

                // Content resolver will process the contentvalues
                val contentValues = ContentValues().apply {

                    // putting file information in content values
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }

                // Inserting the contentValues to
                // contentResolver and getting the Uri
                val imageUri: Uri? =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                // Opening an outputstream with the Uri that we got
                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            // These for devices running on android < Q
            val imagesDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)
        }

        fos?.use {
            // Finally writing the bitmap to the output stream that we opened
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            Toast.makeText(
                this, "Invoice saved to Gallery at " + "${
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                }", Toast.LENGTH_SHORT
            ).show()
            finish()
        }

    }

    fun downloadFile(url: String) {
        Toast.makeText(this, "Your download has begun", Toast.LENGTH_SHORT).show()
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            // this will request for permission when user has not granted permission for the app

            ActivityCompat.requestPermissions(
                this as Activity, arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), 1
            )
        } else {

            //Download Script

            val downloadManager =
                this.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager?
            val uri = Uri.parse(url)
            val request = DownloadManager.Request(uri)
            request.setVisibleInDownloadsUi(true)
            request.setTitle(this.resources.getString(R.string.app_name))
            request.setDescription("Downloading...")
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
//                uri.lastPathSegment
                this.resources.getString(R.string.app_name) + "${pdfNumber}.pdf"
            )
            pdfNumber++
            downloadManager!!.enqueue(request)
        }
    }

}