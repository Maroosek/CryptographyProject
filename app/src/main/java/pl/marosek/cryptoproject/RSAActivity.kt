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
import pl.marosek.cryptoproject.CryptoUtils.RSA
import kotlin.system.measureTimeMillis

class RSAActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rsa)

        val rsa = RSA()

        val checkBoxRSA = findViewById<CheckBox>(R.id.checkBoxRSA)
        val passwordRSA = findViewById<EditText>(R.id.passwordRSA)
        val generateBtn = findViewById<Button>(R.id.btnGenerateKeysRSA)
        val spinnerRSA = findViewById<Spinner>(R.id.spinnerRSA)
        val listViewRSA = findViewById<ListView>(R.id.listViewRSA)
        val passList = mutableListOf<String>()
        val numberPickerText = findViewById<TextView>(R.id.textViewNumberPicker)
        val numberPickerRSA = findViewById<NumberPicker>(R.id.numberPickerRSA)
        numberPickerRSA.minValue = 1
        numberPickerRSA.maxValue = 500

        spinnerRSA.adapter = ArrayAdapter.createFromResource(
            this,
            R.array.key_sizes_RSA,
            android.R.layout.simple_spinner_item
        )

        checkBoxRSA.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                passwordRSA.visibility = EditText.INVISIBLE
                numberPickerRSA.visibility = NumberPicker.VISIBLE
                numberPickerText.visibility = TextView.VISIBLE
            } else {
                passwordRSA.visibility  = EditText.VISIBLE
                numberPickerRSA.visibility = NumberPicker.INVISIBLE
                numberPickerText.visibility = TextView.INVISIBLE
            }
        }

        generateBtn.setOnClickListener {

            passList.clear()
            //refreshList(listViewRSA, passList)

            val executionTime = measureTimeMillis {

                var password: String
                if (checkBoxRSA.isChecked) {
                    val amountOfPasswords = numberPickerRSA.value

                    for (i in 1..amountOfPasswords) {
                        val singleExecetionTyme = measureTimeMillis {
                            if (spinnerRSA.selectedItem.toString().toInt() <= 1024){ //needed to avoid ListView overflow
                                password = rsa.generatePassword()
                                passList.add("Password: " + password)
                                val keyRSA = rsa.generateKeysRSA(spinnerRSA.selectedItem.toString().toInt())
                                passList.add("Public key: " + rsa.getPublicKey(keyRSA))
                                passList.add("Private key: " + rsa.getPrivateKey(keyRSA))
                                //Toast.makeText(this, password, Toast.LENGTH_SHORT).show()
                                val encrypted = rsa.encryptRSA(password, keyRSA)
                                passList.add("Encrypted password:" + encrypted)
                                passList.add("Decrypted password: " + rsa.decryptRSA(encrypted, keyRSA))
                            }
                            else {
                                password = rsa.generatePassword()
                                passList.add("Password: " + password)
                                val keyRSA = rsa.generateKeysRSA(spinnerRSA.selectedItem.toString().toInt())
                                //passList.add("Public key: " + rsa.getPublicKey(keyRSA))
                                //passList.add("Private key: " + rsa.getPrivateKey(keyRSA))
                                //Toast.makeText(this, password, Toast.LENGTH_SHORT).show()
                                val encrypted = rsa.encryptRSA(password, keyRSA)
                                //passList.add("Encrypted password:" + encrypted)
                                val decrypted = rsa.decryptRSA(encrypted, keyRSA)
                                //passList.add("Decrypted password: " + rsa.decryptRSA(encrypted, keyRSA))
                            }
                        }
                        passList.add("Time taken: " + singleExecetionTyme.toString() + " ms")
                    }



                } else {
                    val singleExecutionTime = measureTimeMillis {
                        password = passwordRSA.text.toString()
                        passList.add("Password: " + password)
                        val keyRSA = rsa.generateKeysRSA(spinnerRSA.selectedItem.toString().toInt())
                        passList.add("Public key: " + rsa.getPublicKey(keyRSA))
                        passList.add("Private key: " + rsa.getPrivateKey(keyRSA))
                        val encrypted = rsa.encryptRSA(password, keyRSA)
                        passList.add("Encrypted password:" + encrypted)
                        passList.add("Decrypted password: " + rsa.decryptRSA(encrypted, keyRSA))
                    }
                    passList.add("Time taken: " + singleExecutionTime.toString() + " ms")
                    //refreshList(listViewRSA, passList)
                }
            }
            refreshList(listViewRSA, passList)
            passList.add("Execution time: " + executionTime.toString() + " ms")
            Toast.makeText(this, "Time: $executionTime ms", Toast.LENGTH_LONG).show()
        }
    }
    private fun refreshList (listView: ListView, list: MutableList<String>) {
        val adapterRSA = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
        listView.adapter = adapterRSA
    }
}