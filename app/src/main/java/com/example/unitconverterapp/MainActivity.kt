package com.example.unitconverterapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }
    // define view reference
    private val btnCelcius by lazy { findViewById<Button>(R.id.btn_celcius) }
    private val btnFarenheit by lazy { findViewById<Button>(R.id.btn_farenheit) }
    private val btnSwap by lazy { findViewById<Button>(R.id.btn_swap) }
    private val tvInput by lazy { findViewById<TextView>(R.id.tv_input) }
    private val tvOutput by lazy { findViewById<TextView>(R.id.tv_output) }

    private val btnOne by lazy { findViewById<Button>(R.id.btn_1) }
    private val btnTwo by lazy { findViewById<Button>(R.id.btn_2) }
    private val btnThree by lazy { findViewById<Button>(R.id.btn_3) }
    private val btnFour by lazy { findViewById<Button>(R.id.btn_4) }
    private val btnFive by lazy { findViewById<Button>(R.id.btn_5) }
    private val btnSix by lazy { findViewById<Button>(R.id.btn_6) }
    private val btnSeven by lazy { findViewById<Button>(R.id.btn_7) }
    private val btnEight by lazy { findViewById<Button>(R.id.btn_8) }
    private val btnNine by lazy { findViewById<Button>(R.id.btn_9) }
    private val btnZero by lazy { findViewById<Button>(R.id.btn_0) }

    private val btnPlus by lazy { findViewById<Button>(R.id.btn_plus) }
    private val btnSubstract by lazy { findViewById<Button>(R.id.btn_substract) }
    private val btnClear by lazy { findViewById<Button>(R.id.btn_c) }
    private val btnEqual by lazy { findViewById<Button>(R.id.btn_equal) }
    private val btnDecimal by lazy { findViewById<Button>(R.id.btn_Dot) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        observeInputValue()
        observeOutputValue()

        btnSwap.setOnClickListener {
            animateInputIndicator()
            viewModel.swapUnit()
            viewModel.resetInputAndOutput()

        }

        btnOne.setButtonClickListener("1")
        btnTwo.setButtonClickListener("2")
        btnThree.setButtonClickListener("3")
        btnFour.setButtonClickListener("4")
        btnFive.setButtonClickListener("5")
        btnSix.setButtonClickListener("6")
        btnEight.setButtonClickListener("8")
        btnNine.setButtonClickListener("9")
        btnZero.setButtonClickListener("0")

        btnPlus.setButtonClickListener("+")
        btnSubstract.setButtonClickListener("-")
        btnDecimal.setButtonClickListener(".")
        btnClear.setOnClickListener {
            viewModel.clearInput()
        }

        btnEqual.setOnClickListener {
            viewModel.calculate()
        }

    }

    private  fun observeInputValue() {
        viewModel.input.observe(this) {input -> tvInput.text = input}
    }

    private  fun observeOutputValue() {
        viewModel.output.observe(this) {output -> tvOutput.text = output}
    }

    private fun animateInputIndicator() {
        // get initial position
        val initialY1 = btnCelcius.y
        val initalY2 = btnFarenheit.y

        // animate button celcius
        btnCelcius.animate()
            .alpha(0f)
            .y(initalY2)
            .setDuration(300)
            .withEndAction {
                btnCelcius.y = initalY2
                btnCelcius.alpha = 1f
            }

        // animate button farenheit
        btnFarenheit.animate()
            .alpha(0f)
            .y(initalY2)
            .setDuration(300)
            .withEndAction {
                btnFarenheit.y = initialY1
                btnFarenheit.alpha = 1f
            }
    }

    // function extension
    private fun Button.setButtonClickListener(value: String) {
        setOnClickListener {
            viewModel.setInput(value)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.input.removeObservers(this)
        viewModel.output.removeObservers(this)
    }
}