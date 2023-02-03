package com.example.calculatrice_v2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Button
import com.fathzer.soft.javaluator.DoubleEvaluator
import java.text.DecimalFormat


class CalcPortraitActivity : AppCompatActivity(){
    private var input: String = ""
    private var result: TextView? = null
    private var lastOperator: String = ""




    //Les boutons
    private lateinit var bouton0: Button
    private lateinit var bouton1: Button
    private lateinit var bouton2: Button
    lateinit var bouton3: Button
    lateinit var bouton4: Button
    lateinit var bouton5: Button
    lateinit var bouton6: Button
    lateinit var bouton7: Button
    lateinit var bouton8: Button
    lateinit var bouton9: Button
    lateinit var boutonReset: Button
    lateinit var boutonBackspace: Button
    lateinit var boutonDot: Button
    lateinit var boutonEgal: Button
    lateinit var boutonPlus: Button
    lateinit var boutonMoins: Button
    lateinit var boutonMult: Button
    lateinit var boutonDivision: Button
    lateinit var boutonModulo: Button
    lateinit var boutonPlusOuMoins: Button




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calc_portrait)


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
        result = findViewById(R.id.resultTV)


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
                input = input.substring(0, input.length - 1)
                result!!.text = input
            }
        }

        boutonReset.setOnClickListener {
            input = ""
            result!!.text = ""
        }

        boutonPlusOuMoins.setOnClickListener {

            changeSign(result!!.text.toString())
        }

        // Opérations
        boutonEgal.setOnClickListener {
            evaluate()

        }

    }



    private fun addInput(input1: String) {
        input += input1
        result!!.text = input
    }

    private fun setOperator(op: String) {

        if (invalidFormat(result!!.text.toString(),op)){
            println("Format invalide")
        }
        else if(countOperator(result!!.text.toString()) == 1 && !input.contains("-")){   //Si l'expression contient un format valide

            evaluate()
            input += op
            result!!.text = input
        }
        else{
            addInput(op)
        }

    }



    fun invalidFormat(c: String, ch: String): Boolean{
        if(result!!.text.isEmpty() && (ch == "/" || ch == "*" || ch == "+" || ch == "%")) return true //format invalide
        else return false
    }

    fun evaluate(){
        val evaluator = DoubleEvaluator()
        val expression = result!!.text.replace(Regex("×"), "*")
        val myresult = evaluator.evaluate(expression)

        input = DecimalFormat("0.######").format(myresult).toString()
        result!!.text = input

    }

    fun countOperator(input: String): Int{
        val operatorRegex = "[+\\-*/]".toRegex()
        return operatorRegex.findAll(input).count()
    }

    fun invalidSetOperator(ch: Char): Boolean{
        if (result!!.text[result!!.text.length -1] != ch){
            print("impossible que 2 opérateurs se suivent")
            return true
        }
        else return false
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

        result!!.text = input
    }





}



