package com.example.lab2

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
import java.util.*

class InpFragment : Fragment() {
    private val dataModel: DataModel by activityViewModels()
    lateinit var binding: FragmentInpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentInpBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val calc = view.findViewById<Button>(R.id.calc)
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
                    dataModel.message.value=res.toString()
                    dataModel.operCounter.value=counter.toString()}

                else{
                    Toast.makeText(getActivity(), R.string.nan, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance()=InpFragment()

    }
}