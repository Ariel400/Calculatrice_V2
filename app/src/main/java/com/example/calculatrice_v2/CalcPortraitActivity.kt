package com.example.calculatrice_v2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.fathzer.soft.javaluator.DoubleEvaluator
import java.text.DecimalFormat

class CalcPortraitActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calc_portrait)
    }

    override fun onClick(v: View?) {
        val tv = v as TextView
        val resultTV = findViewById<TextView>(R.id.resultTV)
        var oldText = resultTV.text.toString()

        fun onSaveInstanceState(outState: Bundle) {
            super.onSaveInstanceState(outState)
            outState.putString("oldtext", oldText)

        }

        fun onRestoreInstanceState(savedInstanceState: Bundle) {
            super.onRestoreInstanceState(savedInstanceState)
            oldText = savedInstanceState.getString("oldText") ?: ""

        }

        when(tv.text.toString()){
            "Del" -> {
                if(oldText.length > 0){
                    val newText = oldText.substring(0, oldText.length - 1)
                    resultTV.setText(newText)
                }
            }
            "AC" -> {resultTV.setText(null)}
            "=" -> {
                val evaluator = DoubleEvaluator()
                val expression = resultTV.text.replace(Regex("×"), "*")
                val result = evaluator.evaluate(expression)
                resultTV.setText(DecimalFormat("0.######").format(result).toString())

            }
            else -> {
                val toAppendString = tv.text.toString()
                if(isOperator(toAppendString[0]) && isOperator(oldText[oldText.length - 1])){
                    oldText = oldText.substring(0, oldText.length - 1)
                }
                val newText = oldText + toAppendString
                resultTV.setText(newText)
            }
        }
    }

    fun isOperator(c: Char): Boolean {
        when(c){
            'x', '*', '×', '/', '+', '-','%' -> { return true}
            else -> return false
        }
    }
}