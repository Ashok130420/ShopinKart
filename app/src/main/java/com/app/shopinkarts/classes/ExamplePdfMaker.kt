package com.app.shopinkarts.classes

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfDocument.PageInfo
import android.os.Environment
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException


class ExamplePdfMaker(var context: Context) {
    fun buildPdf(): String? {
        // create a new document
        val document = PdfDocument()
        val headerTextSize = 20
        val bodyTextSize = 10

        //Calulate total pixels height by finding newlines
        val lastIndex = 0
        val count = 1
        val pageWidth = 200

        //Adding title
        val textViewTitle = TextView(context)
        textViewTitle.layout(
            0, 0, pageWidth, headerTextSize + 4
        ) //text box size heightpx x widthpx (Left, Top,Right,Bottom)
        textViewTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, headerTextSize.toFloat())
        textViewTitle.textAlignment = View.TEXT_ALIGNMENT_CENTER
        textViewTitle.setTextColor(Color.BLACK)
        textViewTitle.typeface = Typeface.MONOSPACE
        textViewTitle.isDrawingCacheEnabled = true
        textViewTitle.text = "Cactus Bob's"
        //Getting size of text view
        textViewTitle.measure(0, 0)
        val titleWidth = textViewTitle.measuredWidth
        val titleHeight = textViewTitle.measuredHeight


        //XXX: Here we create some dummy orders so we can loop through them.
        val ordersList: MutableList<TempOrder> = ArrayList()
        ordersList.add(tempOrderBuilder().setDescription("Mud puppies").setPrice(595))
        ordersList.add(
            tempOrderBuilder().setDescription("Gator bites\n\t-No tartar\n\t-Extra salt")
                .setPrice(650)
        )
        ordersList.add(tempOrderBuilder().setDescription("Pog chips").setPrice(200))
        ordersList.add(tempOrderBuilder().setDescription("Icecream").setPrice(300))
        ordersList.add(tempOrderBuilder().setDescription("Turkey drumstick").setPrice(500))

        // This tableLayout is what will be printed to the pdf
        val tableLayout = TableLayout(context)

        //Title added to layout
        val titleRow = TableRow(context)
        val lpTitle = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT)
        titleRow.layoutParams = lpTitle
        titleRow.addView(textViewTitle)
        tableLayout.addView(titleRow)

        //We will temporarily store these textviews in a list
        for (ord in ordersList) {
            //TableLayout expiriment
            val row = TableRow(context)
            val lp = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT)
            row.layoutParams = lp


            //Adding body
            val bodyItemHeight = 200 //TODO: what is this? Does it matter?
            val textView = TextView(context)
            textView.layout(
                0, 0, pageWidth, count * bodyItemHeight
            ) //text box size heightpx x widthpx (Left, Top,Right,Bottom)
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, bodyTextSize.toFloat())
            textView.setTextColor(Color.BLACK)
            textView.typeface = Typeface.MONOSPACE
            //textView.setShadowLayer(5, 2, 2, Color.CYAN); //text shadow
            textView.text = ord.description + "\t" + ord.price

//            bodyTextViews.add(textView);
            row.addView(textView)
            tableLayout.addView(row)
        }

        //Getting size of text view
        tableLayout.measure(0, 0)
        val bodyWidth = tableLayout.measuredWidth
        val pageHeight = tableLayout.measuredHeight

        //Create a pdf document
        // int pageHeight=titleHeight+bodyHeight;
        Log.d("ez_Receipt", "titleHeight: $titleHeight")
        Log.d("ez_Receipt", "pageHeight: $pageHeight")
        // crate a page description
        val pageInfo = PageInfo.Builder(pageWidth, pageHeight, 1).create()
        // start a page in the pdf
        val page = document.startPage(pageInfo)

        //Drawing to canvas
        val paint = Paint()
        paint.color = Color.BLACK //Setting up our paint
        tableLayout.isDrawingCacheEnabled = true
        tableLayout.measure(
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        tableLayout.layout(0, 0, tableLayout.measuredWidth, tableLayout.measuredHeight)
        tableLayout.buildDrawingCache(true)
        val cs = Bitmap.createBitmap(tableLayout.drawingCache)
        page.canvas.drawBitmap(cs, 0f, titleHeight.toFloat(), paint) //Body

        // finish the page
        document.finishPage(page)
        var filepath: String? = null
        try {

            val root = Environment.getExternalStorageDirectory().toString()
//            val myDir = File("$root/saved_images")

//            val file: File = File(Environment.getExternalStorageDirectory(), "GFG.pdf")
//            val mypath = File(ctx.getExternalFilesDir(null).toString() + "/" + "example.pdf")

            val mypath = File(context.getExternalFilesDir(null).toString() + "/" + "example.pdf")



            Log.d("example.pdf", mypath.toString())

            filepath = mypath.toString()


            document.writeTo(FileOutputStream(mypath))
            Log.d("ez_Receipt", "The external pdf file should be stored at: $filepath")


        } catch (e: FileNotFoundException) {
            Log.d(
                "ez_Receipt",
                "File not found exception. Make sure WRITE_EXTERNAL_STORAGE permissions exist in your AndroidManifest.xml"
            )
        } catch (e: IOException) {
            Log.d("ez_Receipt", "IOException")
        }
        // close the document
        document.close()
        return filepath
    }

    companion object {
        fun tempOrderBuilder(): TempOrder {
            return TempOrder()
        }
    }
}


class TempOrder {
    var price = 0
        private set
    var description: String? = null
        private set

    fun setPrice(price: Int): TempOrder {
        this.price = price
        return this
    }

    fun setDescription(description: String?): TempOrder {
        this.description = description
        return this
    }
}