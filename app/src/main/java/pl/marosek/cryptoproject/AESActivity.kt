package pl.marosek.cryptoproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ListView
import android.widget.NumberPicker
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import pl.marosek.cryptoproject.CryptoUtils.AES
import kotlin.system.measureTimeMillis

class AESActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aes)

        val aes = AES()

        val checkBoxAES = findViewById<CheckBox>(R.id.checkBoxAES)
        val passwordAES = findViewById<EditText>(R.id.passwordAES)
        val generateBtn = findViewById<Button>(R.id.btnGenerateKeysAES)
        val spinnerAES = findViewById<Spinner>(R.id.spinnerAES)
        val listViewRSA = findViewById<ListView>(R.id.listViewAES)
        val passList = mutableListOf<String>()
        val numberPickerText = findViewById<TextView>(R.id.textViewNumberPickerAES)
        val numberPickerAES = findViewById<NumberPicker>(R.id.numberPickerAES)
        numberPickerAES.minValue = 1
        numberPickerAES.maxValue = 500

        spinnerAES.adapter = ArrayAdapter.createFromResource(
            this,
            R.array.key_sizes_AES,
            android.R.layout.simple_spinner_item
        )

        checkBoxAES.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                passwordAES.visibility = EditText.INVISIBLE
                numberPickerAES.visibility = NumberPicker.VISIBLE
                numberPickerText.visibility = TextView.VISIBLE
            } else {
                passwordAES.visibility = EditText.VISIBLE
                numberPickerAES.visibility = NumberPicker.INVISIBLE
                numberPickerText.visibility = TextView.INVISIBLE
            }
        }
        generateBtn.setOnClickListener {
            passList.clear()
            refreshList(listViewRSA, passList)

            val executionTime = measureTimeMillis {

                var password = ""
                if (checkBoxAES.isChecked) {
                    val amountOfPasswords = numberPickerAES.value

                    for (i in 1..amountOfPasswords) {
                        val singleExecutionTime = measureTimeMillis {
                            password = aes.generatePassword()
                            passList.add("Password: " + password)
                            val secretKey = aes.generateKey(spinnerAES.selectedItem.toString().toInt())
                            passList.add("Key: " + secretKey)
                            val iv = aes.generateKey(16)
                            passList.add("IV: " + iv)
                            val encrypted = aes.encryptAES(password, secretKey, iv)
                            passList.add("Encrypted password:" + encrypted)
                            //var decrypted = decryptAES(encrypted, secretKey, iv)
                            passList.add("Decrypted password: " + aes.decryptAES(encrypted, secretKey, iv))
                        }
                        passList.add("Time taken: " + singleExecutionTime.toString() + " ms")
                        //generateKeysRSA()
                    }

                    refreshList(listViewRSA, passList)

                } else {
                    val singleExecutionTime = measureTimeMillis {
                        password = passwordAES.text.toString()
                        passList.add("Password: " + password)
                        val secretKey = aes.generateKey(spinnerAES.selectedItem.toString().toInt())
                        passList.add("Key: " + secretKey)
                        val iv = aes.generateKey(16)
                        passList.add("IV: " + iv)
                        val encrypted = aes.encryptAES(password, secretKey, iv)
                        passList.add("Encrypted password:" + encrypted)
                        //var decrypted = decryptAES(encrypted, secretKey, iv)
                        passList.add("Decrypted password: " + aes.decryptAES(encrypted, secretKey, iv))
                    }
                    passList.add("Time taken: " + singleExecutionTime.toString() + " ms")
                    refreshList(listViewRSA, passList)
                }
            }
            passList.add("Execution time: " + executionTime.toString() + " ms")

            Toast.makeText(this, "Time: $executionTime ms", Toast.LENGTH_LONG).show()
        }
    }
    private fun refreshList (listView: ListView, list: MutableList<String>) {
        val adapterRSA = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
        listView.adapter = adapterRSA
    }
}