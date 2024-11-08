package com.example.unitconverterapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder

class MainViewModel: ViewModel() {

    private val celcius = "celcius"
    private  val fahrenheit = "fahrenheit"

    private val _input = MutableLiveData("0") // hanya digunakan di dlm class ini
    val input: LiveData<String> = _input // readonly

    private val _activeInputUnit = MutableLiveData(celcius)
    val activeInputUnit: LiveData<String>   = _activeInputUnit

    private val _output = MutableLiveData("0")
    val output: LiveData<String> = _output

    fun setInput(value: String) {
        val firstValueExceptionValue = listOf(".","-","+")
        if (value in firstValueExceptionValue && _input.value == "0") {
            _input.value = "0"
            return
        }

        if (_input.value == "0") {
            _input.value = value
        } else {
            _input.value += value
        }
    }

    fun clearInput() {

        _input.value = if (_input.value?.length == 1) {
            "0"
        } else {
            _input.value?.dropLast(1)
        }

    }

    fun swapUnit() {
        _activeInputUnit.value = if (_activeInputUnit.value == celcius) fahrenheit else celcius
    }

    fun calculate() {
        val expression = _input.value
        val result = try {
            ExpressionBuilder(expression).build().evaluate()
        } catch (e: Exception) {
            return
        }

        result.let {
            _output.value = when (_activeInputUnit.value) {
                celcius -> celciusToFahrenheit(it).toString()
                fahrenheit -> fahrenheitToCelcius(it).toString()
                else -> "0"
            }
        }
    }

    fun resetInputAndOutput() {
        _input.value = "0"
        _output.value = "0"
    }

    private fun celciusToFahrenheit(celcius: Double): Double {
        return (celcius * 9/5) + 32
    }

    private fun fahrenheitToCelcius(fahrenheit: Double):Double {
        return (fahrenheit - 32 ) * 5/9
    }


}