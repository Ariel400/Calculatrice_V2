package com.example.calculatrice_v2

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fathzer.soft.javaluator.DoubleEvaluator
import java.text.DecimalFormat


class CalcPortraitActivity : AppCompatActivity(), View.OnClickListener{
    private var input: String = ""
    private var resultTV: TextView? = null


    //Les boutons
    private lateinit var bouton0: Button
    private lateinit var bouton1: Button
    private lateinit var bouton2: Button
    private lateinit var bouton3: Button
    private lateinit var bouton4: Button
    private lateinit var bouton5: Button
    private lateinit var bouton6: Button
    private lateinit var bouton7: Button
    private lateinit var bouton8: Button
    private lateinit var bouton9: Button
    private lateinit var boutonReset: Button
    private lateinit var boutonBackspace: Button
    private lateinit var boutonDot: Button
    private lateinit var boutonEgal: Button
    private lateinit var boutonPlus: Button
    private lateinit var boutonMoins: Button
    private lateinit var boutonMult: Button
    private lateinit var boutonDivision: Button
    private lateinit var boutonModulo: Button
    private lateinit var boutonPlusOuMoins: Button




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calc_portrait)
    }


    override fun onClick(p0: View?) {
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

        fun addInput(input1: String) {
            input += input1
            resultTV.text = input
        }
        fun invalidFormat(c: String, ch: String): Boolean{
            return resultTV.text.isEmpty() && (ch == "/" || ch == "*" || ch == "+" || ch == "%")
        }

        fun evaluate(){
            val evaluator = DoubleEvaluator()
            val expression = resultTV.text.replace(Regex("×"), "*")
            val myresult = evaluator.evaluate(expression)

            input = DecimalFormat("0.######").format(myresult).toString()
            resultTV.text = input

        }
        fun countOperator(input: String): Int{
            val operatorRegex = "[+\\-*/]".toRegex()
            return operatorRegex.findAll(input).count()
        }
        fun setOperator(op: String) {

            if (invalidFormat(resultTV.text.toString(),op)){
                Toast.makeText(this, "Format invalide", Toast.LENGTH_SHORT).show()
            }
            else if(countOperator(resultTV.text.toString()) == 1 && !input.contains("-")){   //Si l'expression contient un format valide
                evaluate()
                input += op
                resultTV.text = input
            }
            else{
                addInput(op)
            }

        }



        fun changeSign(screen: String) {
            var currentExpression = screen

            val lastIndex = currentExpression.length - 1
            var lastNumberStart = lastIndex
            while (lastNumberStart >= 0 && currentExpression[lastNumberStart].isDigit()) {
                lastNumberStart--
            }
            lastNumberStart++

            var lastNumber = currentExpression.substring(lastNumberStart, lastIndex + 1).toDouble()
            lastNumber = -lastNumber
            currentExpression = currentExpression.substring(0, lastNumberStart) +'(' + lastNumber.toString()+')'

            input = currentExpression

            resultTV.text = input
        }



        //supportActionBar?.hide();

        bouton0=findViewById(R.id.bouton0)
        bouton1=findViewById(R.id.bouton1)
        bouton2=findViewById(R.id.bouton2)
        bouton3=findViewById(R.id.bouton3)
        bouton4=findViewById(R.id.bouton4)
        bouton5=findViewById(R.id.bouton5)
        bouton6=findViewById(R.id.bouton6)
        bouton7=findViewById(R.id.bouton7)
        bouton8=findViewById(R.id.bouton8)
        bouton9=findViewById(R.id.bouton9)
        boutonReset=findViewById(R.id.boutonAC)
        boutonBackspace=findViewById(R.id.boutonSup)
        boutonDot=findViewById(R.id.boutonDot)
        boutonEgal=findViewById(R.id.boutonEgal)
        boutonPlus=findViewById(R.id.boutonPlus)
        boutonMoins=findViewById(R.id.boutonMoins)
        boutonMult=findViewById(R.id.boutonMult)
        boutonDivision=findViewById(R.id.boutonDivision)
        boutonModulo=findViewById(R.id.boutonModulo)
        boutonPlusOuMoins=findViewById(R.id.boutonPlusOuMoins)



        //Ajout de Nombres
        bouton0.setOnClickListener{addInput("0")}
        bouton1.setOnClickListener{addInput("1")}
        bouton2.setOnClickListener{addInput("2")}
        bouton3.setOnClickListener{addInput("3")}
        bouton4.setOnClickListener{addInput("4")}
        bouton5.setOnClickListener{addInput("5")}
        bouton6.setOnClickListener{addInput("6")}
        bouton7.setOnClickListener{addInput("7")}
        bouton8.setOnClickListener{addInput("8")}
        bouton9.setOnClickListener{addInput("9")}
        boutonDot.setOnClickListener{addInput(".")}

        //Ajout d'Opérateurs
        boutonPlus.setOnClickListener { setOperator("+") }
        boutonMoins.setOnClickListener { setOperator("-") }
        boutonMult.setOnClickListener { setOperator("*") }
        boutonDivision.setOnClickListener { setOperator("/") }
        boutonModulo.setOnClickListener { setOperator("%") }

        //fonctions autres



        boutonBackspace.setOnClickListener {
            if (input.isNotEmpty()) {
                if(input.length == 2 && input[0] == '-'){
                    resultTV.text = ""
                    input = ""
                }
                else{
                    input = input.substring(0, input.length - 1)
                    resultTV.text = input
                }

            }

        }

        boutonReset.setOnClickListener {
            input = ""
            resultTV.text = ""
        }

        boutonPlusOuMoins.setOnClickListener {

            changeSign(resultTV.text.toString())
        }

        // Opérations
        boutonEgal.setOnClickListener {
            evaluate()
        }
    }

}



