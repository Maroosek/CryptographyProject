package pl.marosek.cryptoproject.CryptoUtils

import android.util.Base64
import java.util.Random
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class AES {
    fun generatePassword(): String {
        val chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val passwordLength = Random().nextInt(20) + 8
        val password = StringBuilder()
        val random = Random()

        for (i in 0 until passwordLength) {
            val randomIndex = random.nextInt(chars.length)
            password.append(chars[randomIndex])
        }

        return password.toString()
    }

    fun generateKey(keySize: Int): String {
        var size = keySize
        if (size == 128) {
            size = 16
        } else if (size == 192) {
            size = 24
        } else if (size == 256) {
            size = 32
        }
        val chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val password = StringBuilder()
        val random = Random()

        for (i in 0 until size) {
            val randomIndex = random.nextInt(chars.length)
            password.append(chars[randomIndex])
        }

        return password.toString()
    }

    fun encryptAES(password: String, key: String, iv: String): String {
        //Toast.makeText(this, "encrypting", Toast.LENGTH_SHORT).show()
        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
        val secretKey = SecretKeySpec(key.toByteArray(), "AES")
        val ivParam = IvParameterSpec(iv.toByteArray())
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParam)
        val encrypted = cipher.doFinal(password.toByteArray())
        return Base64.encodeToString(encrypted, Base64.DEFAULT)
    }

    fun decryptAES(password: String, key: String, iv: String): String {
        //Toast.makeText(this, "decrypting", Toast.LENGTH_SHORT).show()
        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
        val secretKey = SecretKeySpec(key.toByteArray(), "AES")
        val ivParam = IvParameterSpec(iv.toByteArray())
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParam)
        val encrypted = Base64.decode(password, Base64.DEFAULT)
        val decrypted = cipher.doFinal(encrypted)
        return String(decrypted)
    }
}
