package pl.marosek.cryptoproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnAES = findViewById<Button>(R.id.btnAES)
        val btnRSA = findViewById<Button>(R.id.btnRSA)

        btnRSA.setOnClickListener {
            val intent = Intent(this, RSAActivity::class.java)
            startActivity(intent)
        }
        btnAES.setOnClickListener {
            val intent = Intent(this, AESActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart(){
        super.onStart()
        Log.d("LIFECYCLE", "onStart") //debugging
    }
    override fun onResume(){
        super.onResume()
        Log.d("LIFECYCLE", "onResume") //debugging
    }
    override fun onPause(){
        super.onPause()
        Log.d("LIFECYCLE", "onPause") //debugging
    }
    override fun onStop(){
        super.onStop()
        Log.d("LIFECYCLE", "onStop") //debugging
    }
    override fun onRestart(){
        super.onRestart()
        Log.d("LIFECYCLE", "onRestart") //debugging
    }
    override fun onDestroy(){
        super.onDestroy()
        Log.d("LIFECYCLE", "onDestroy") //debugging
    }
}