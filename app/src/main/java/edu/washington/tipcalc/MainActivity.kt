package edu.washington.tipcalc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import java.text.NumberFormat
import java.util.*
import android.text.Editable

import android.text.TextWatcher
import java.lang.ref.WeakReference
import java.math.BigDecimal


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textBox = findViewById<EditText>(R.id.editTextNumberDecimal)
        val btn = findViewById<Button>(R.id.button)
        textBox.addTextChangedListener(
            object:TextWatcher{
                private val editTextRef: WeakReference<EditText> = WeakReference<EditText>(textBox)
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(editable: Editable) {
                    textBox.removeTextChangedListener(this)
                    val cleanString = editable.toString().replace("[$,.]".toRegex(), "")
                    val parsed: BigDecimal = BigDecimal(cleanString).setScale(2, BigDecimal.ROUND_FLOOR)
                        .divide(BigDecimal(100), BigDecimal.ROUND_FLOOR)
                    val formatted: String = NumberFormat.getCurrencyInstance().format(parsed)
                    textBox.setText(formatted)
                    textBox.setSelection(formatted.length)
                    textBox.addTextChangedListener(this)
                    btn.isEnabled = true;
                }
            }
        );
        btn.setOnClickListener {
            val n: NumberFormat = NumberFormat.getCurrencyInstance(Locale.US)
            val toast = Toast.makeText(applicationContext, n.format(textBox.text.toString().replace("\$", "").replace(",", "").toDouble()*.15), Toast.LENGTH_LONG)
            toast.show()
        }
    }

}