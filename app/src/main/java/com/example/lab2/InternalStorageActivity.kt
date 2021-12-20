package com.example.lab2


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.lang.Exception

class InternalStorageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_internal_storage)

        val data: String = readInternalStorage("Saved")

        val storedText = findViewById<TextView>(R.id.storageText)

        if (data == "") {
            storedText.text = "Storage is empty"
        }

        storedText.text = data
    }

    private fun readInternalStorage(fileName: String): String{
        try {
            val fileInputStream: FileInputStream? = openFileInput(fileName)
            val inputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            val stringBuilder: StringBuilder = StringBuilder()
            var text: String?
            while (run {
                    text = bufferedReader.readLine()
                    text
                } != null) {
                stringBuilder.append(text)
            }
            return stringBuilder.toString()
        }
        catch (e: Exception){
            return "Storage is empty"
        }
    }
}