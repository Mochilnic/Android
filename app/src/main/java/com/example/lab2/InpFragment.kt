package com.example.lab2

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.lab2.databinding.FragmentInpBinding
import com.google.android.material.textfield.TextInputEditText
import java.lang.ClassCastException
import java.util.*

class InpFragment : Fragment() {
    private val dataModel: DataModel by activityViewModels()
    lateinit var binding: FragmentInpBinding
    private lateinit var onTextSentListener: OnTextSent
    private lateinit var onShowStorage: ShowStorage

    interface OnTextSent {
        fun sendData(
            editTextInput: String
        )
    }
    interface ShowStorage {
        fun show()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            onTextSentListener = context as OnTextSent
            onShowStorage = context as ShowStorage
        } catch (e: ClassCastException) {
            throw ClassCastException(
                activity.toString()
                        + " must implement onTextSent and ShowStorage"
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentInpBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val calc = view.findViewById<Button>(R.id.calc)
        val openStorage = view.findViewById<Button>(R.id.openStorage)
        val first = view.findViewById<TextInputEditText>(R.id.first)
        val second = view.findViewById<TextInputEditText>(R.id.second)
        val plus = view.findViewById<RadioButton>(R.id.radioButton1)
        val minus = view.findViewById<RadioButton>(R.id.radioButton2)
        val multiply = view.findViewById<RadioButton>(R.id.radioButton3)
        val divide = view.findViewById<RadioButton>(R.id.radioButton4)
        var counter:Int=0

        fun String.isInt(): Boolean {
            val v = toIntOrNull()
            return when(v) {
                null -> false
                else -> true
            }
        }

        calc.setOnClickListener(View.OnClickListener {
            if (first.getText().toString().trim { it <= ' ' } == "" || second.getText().toString()
                    .trim { it <= ' ' } == "") {
                Toast.makeText(getActivity(), R.string.errtext, Toast.LENGTH_LONG).show()
            } else {
                if (first.getText().toString().trim { it <= ' ' }.isInt() && second.getText().toString()
                        .trim { it <= ' ' }.isInt()) {
                    val a = Objects.requireNonNull(first.getText()).toString().trim { it <= ' ' }
                        .toInt()
                    val b = Objects.requireNonNull(second.getText()).toString().trim { it <= ' ' }
                        .toInt()
                    var res = 0
                    counter+=1
                    if (plus.isChecked()) {
                        res = a + b
                    }
                    if (minus.isChecked()) {
                        res = a - b
                    }
                    if (multiply.isChecked()) {
                        res = a * b
                    }
                    if (divide.isChecked()) {
                        if (second.getText().toString().trim { it <= ' ' } == "0") {
                            Toast.makeText(getActivity(), R.string.zerotext, Toast.LENGTH_LONG).show()
                            return@OnClickListener
                        }
                        res = a / b
                    }
                    val text=res.toString()
                    Toast.makeText(getActivity(), "Data saved", Toast.LENGTH_SHORT).show()
                    dataModel.message.value=text
                    dataModel.operCounter.value=counter.toString()
                    passData(
                        text
                    )
                }

                else{
                    Toast.makeText(getActivity(), R.string.nan, Toast.LENGTH_LONG).show()
                }
            }
        })

        openStorage.setOnClickListener {
            showStorage()
        }
    }
    private fun showStorage() {
        onShowStorage.show()
    }

    private fun passData(
        editTextInput: String
    ) {
        onTextSentListener.sendData(
            editTextInput
        )
    }
    companion object {
        @JvmStatic
        fun newInstance()=InpFragment()

    }
}