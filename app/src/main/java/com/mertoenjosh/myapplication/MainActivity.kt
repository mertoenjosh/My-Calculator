package com.mertoenjosh.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    lateinit var tvDisplay: TextView

    var lastNumeric: Boolean = false
    var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvDisplay = findViewById(R.id.tvDisplay)
    }

    fun onDigit(view: View) {
        if (tvDisplay.text.trim() == "0") tvDisplay.text = ""
        tvDisplay.append((view as Button).text)
        lastNumeric = true
    }

    fun onClear(view: View) {
        tvDisplay.text = ""
        lastNumeric = false
        lastDot = false
    }

    fun onDelete(view: View) {
        tvDisplay.text = tvDisplay.text.toString().dropLast(1)
    }


    fun onDecimalPoint(view: View) {
        if (lastNumeric && !lastDot ) {
            if (addDecimal()) {
                tvDisplay.append(".")
                lastNumeric = false
                lastDot = true
            }

        }
    }

    private fun addDecimal(): Boolean = !(tvDisplay.text.contains(".") && !isOperatorAdded(tvDisplay.text.toString()))


    fun onEqual(view: android.view.View) {
        if (lastNumeric) {
            var inputValue = tvDisplay.text.toString()
            var prefix = ""

            try {
                if (inputValue.startsWith("-")) {
                    prefix = "-"
                    inputValue = inputValue.substring(1)
                }

                if (inputValue.contains("-")) {
                    // destructured array to the variable names
                    var (one, two) = inputValue.split("-")
                    // Approach one
                    /*
                    tvDisplay.text = ((prefix+one).toDouble() - two.toDouble()).toString()
                    */
                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }
                    tvDisplay.text = removeZeroAfterResult((one.toDouble() - two.toDouble()).toString())
                }

                if (inputValue.contains("+")) {
                    var (one, two) = inputValue.split("+")

                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }
                    tvDisplay.text = removeZeroAfterResult((one.toDouble() + two.toDouble()).toString())
                }

                if (inputValue.contains("*")) {
                    var (one, two) = inputValue.split("*")

                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }
                    tvDisplay.text = removeZeroAfterResult((one.toDouble() * two.toDouble()).toString())
                }

                if (inputValue.contains("/")) {
                    var (one, two) = inputValue.split("/")

                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }

                    tvDisplay.text = removeZeroAfterResult((one.toDouble() / two.toDouble()).toString())
                }


            } catch ( e: ArithmeticException ) {
                e.printStackTrace()
            }

        }
    }

    private fun removeZeroAfterResult(result: String): String {
        var value = result

        if (result.contains(".0")) {  // 99.0 -> 99
            value = result.substring(0, result.length - 2)
        }

        return value
    }

    fun onOperator(view: View) {
        if (lastNumeric && !isOperatorAdded(tvDisplay.text.toString())) {
            tvDisplay.append((view as Button).text)
            lastNumeric = false
            lastDot = false
        }
    }


    private fun isOperatorAdded(value: String): Boolean {
        return if (value.startsWith("-")) {
            false
        }
        else {
            value.contains("/") || value.contains("*") ||
                    value.contains("+") || value.contains("-")
        }
    }

}