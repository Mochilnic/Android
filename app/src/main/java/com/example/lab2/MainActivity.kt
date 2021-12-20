package com.example.lab2

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.example.lab2.databinding.ActivityMainBinding
import java.io.FileOutputStream
import java.lang.Exception

class MainActivity : AppCompatActivity(), InpFragment.OnTextSent, InpFragment.ShowStorage {
    lateinit var binding: ActivityMainBinding
    private val dataModel: DataModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        openFrag(InpFragment.newInstance(),R.id.inp_frag)
        openFrag(OutpFragment.newInstance(),R.id.outp_frag)
        dataModel.operCounter.observe(this as LifecycleOwner,{
            binding.textView4.text=it
        })
    }
    private fun openFrag(f: Fragment, idHolder: Int){
        supportFragmentManager
            .beginTransaction()
            .replace(idHolder, f)
            .commit()
    }
    override fun sendData(
        editTextInput: String
    ) {
        val bundle = Bundle()

        if (editTextInput.isNotEmpty()) {
            bundle.putString("editTextInput", editTextInput)


            writeFileOnInternalStorage("Saved", editTextInput)

            supportFragmentManager.beginTransaction()
                .replace(R.id.outp_frag, OutpFragment.newInstance()).addToBackStack(null).commit()

        } else {
            Toast.makeText(applicationContext, "Input field empty!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun show() {
        val intent = Intent(this, InternalStorageActivity::class.java)
        startActivity(intent)
    }

    private fun writeFileOnInternalStorage(fileName: String, fileData: String) {
        val fileOutputStream: FileOutputStream
        try {
            fileOutputStream = openFileOutput(fileName, Context.MODE_APPEND)
            fileOutputStream.write(("$fileData, ").toByteArray())
        }catch (e: Exception){
            e.printStackTrace()
        }
    }
}