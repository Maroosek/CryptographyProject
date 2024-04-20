package pl.marosek.cryptoproject.CryptoUtils

import android.security.keystore.KeyProperties
import android.util.Base64
import java.nio.charset.StandardCharsets
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.SecureRandom
import java.util.Random
import javax.crypto.Cipher

class RSA {
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

    fun encryptRSA(password: String, keypair: KeyPair): String {
        //Toast.makeText(this, "encrypting", Toast.LENGTH_SHORT).show()
        val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        cipher.init(Cipher.ENCRYPT_MODE, keypair.public)
        val encrypted = cipher.doFinal(password.toByteArray(StandardCharsets.UTF_8))
        return Base64.encodeToString(encrypted, Base64.DEFAULT)
    }

    fun decryptRSA(password: String, keypair: KeyPair): String {
        //Toast.makeText(this, "decrypting", Toast.LENGTH_SHORT).show()
        val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        cipher.init(Cipher.DECRYPT_MODE, keypair.private)
        val decrypted = cipher.doFinal(Base64.decode(password, Base64.DEFAULT))
        return String(decrypted)
    }

    fun generateKeysRSA(size: Int): KeyPair {
        val generator = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA)
        generator.initialize(size, SecureRandom()) //TODO allow set set key size
        val keyRSA = generator.generateKeyPair()

        return keyRSA
    }

    fun getPublicKey(keypair: KeyPair): String {
        val key = Base64.encodeToString(keypair.public.encoded, Base64.DEFAULT)
        return key.toString()
    }

    fun getPrivateKey(keypair: KeyPair): String {
        val key = Base64.encodeToString(keypair.private.encoded, Base64.DEFAULT)
        return key.toString()
    }
}