package br.com.redesenhe.protect.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object ChCrypto{
    @RequiresApi(Build.VERSION_CODES.O)
    @JvmStatic fun aesEncrypt(v:String) = AES256.encrypt(v, secretKey)
    @RequiresApi(Build.VERSION_CODES.O)
    @JvmStatic fun aesDecrypt(v:String) = AES256.decrypt(v, secretKey)
}

private const val secretKey = "l4;WWHw:.}byWlJo<4Ph|1tgs;I2TXe6"

private object AES256{
    @RequiresApi(Build.VERSION_CODES.O)
    private val encorder = Base64.getEncoder()
    @RequiresApi(Build.VERSION_CODES.O)
    private val decorder = Base64.getDecoder()
    private fun cipher(opmode:Int, secretKey:String):Cipher{
        if(secretKey.length != 32) throw RuntimeException("SecretKey length is not 32 chars")
        val c = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val sk = SecretKeySpec(secretKey.toByteArray(Charsets.UTF_8), "AES")
        val iv = IvParameterSpec(secretKey.substring(0, 16).toByteArray(Charsets.UTF_8))
        c.init(opmode, sk, iv)
        return c
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun encrypt(str:String, secretKey:String):String{
        val encrypted = cipher(Cipher.ENCRYPT_MODE, secretKey).doFinal(str.toByteArray(Charsets.UTF_8))
        return String(encorder.encode(encrypted))
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun decrypt(str:String, secretKey:String):String{
        val byteStr = decorder.decode(str.toByteArray(Charsets.UTF_8))
        return String(cipher(Cipher.DECRYPT_MODE, secretKey).doFinal(byteStr))
    }
}