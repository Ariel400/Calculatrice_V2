package com.example.calculatrice_v2

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
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
       resultTV.setOnLongClickListener {
           val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
           val clip = ClipData.newPlainText("result", resultTV.text)
           clipboard.setPrimaryClip(clip)
           Toast.makeText(this, "Resultat copié dans le press papier", Toast.LENGTH_SHORT).show()
           true
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
                removeParentheses(resultTV)

                val evaluator = DoubleEvaluator()
                val expression = resultTV.text.replace(Regex("×"), "*")
                val result = evaluator.evaluate(expression)

                resultTV.setText(/*expression +'='+ */DecimalFormat("0.######").format(result).toString())

            }

           "."->{
               resultTV.text=""
           }
            "(-)" -> {changeSign(resultTV)}
            //
                else -> {
                    val toAppendString = tv.text.toString()
                    if(isOperator(toAppendString[0]) && isOperator(oldText[oldText.length - 1])){
                        oldText = oldText.substring(oldText.length - 1)
                    }
                    else{
                        val newText =  toAppendString+oldText
                        resultTV.setText(newText)
                    }
                    val newText = oldText + toAppendString
                    resultTV.setText(newText)
                }
        }
    }
   }
    fun isOperator(c: Char): Boolean {
        when(c){
            'x', '*', '×', '/', '+', '-','%' -> { return true}
            else -> return false
        }
    }



    fun changeSign(screen: TextView) {
        var currentExpression = screen.text.toString()

        val lastIndex = currentExpression.length - 1
        var lastNumberStart = lastIndex
        while (lastNumberStart >= 0 && currentExpression[lastNumberStart].isDigit()) {
            lastNumberStart--
        }
        lastNumberStart++

        var lastNumber = currentExpression.substring(lastNumberStart, lastIndex + 1).toDouble()
        lastNumber = -lastNumber
        currentExpression = currentExpression.substring(0, lastNumberStart) +'(' + lastNumber.toString()+')'

        screen.text = currentExpression
    }

    fun conversion(result: TextView){
        DecimalFormat("0.######").format(result).toString()
    }

    fun removeParentheses(screen: TextView) {
        var currentExpression = screen.text.toString()
        currentExpression = currentExpression.replace("(", "")
        currentExpression = currentExpression.replace(")", "")
        screen.text = currentExpression
    }








