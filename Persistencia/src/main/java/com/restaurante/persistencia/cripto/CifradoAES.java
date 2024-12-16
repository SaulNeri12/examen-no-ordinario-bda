
package com.restaurante.persistencia.cripto;

import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Se encarga de cifrar texto en cifrado AES.
 * @author neri
 */
public class CifradoAES {

    /**
     * Clave universal del programa para las constrasenas.
     * (debe de ser de un maximo de 16 caracteres)
     */
    private static final String SECRET_KEY = "R35T4UR4NT351234";

    /**
     * Encripta el texto pasado como parametro.
     * @param texto Texto crudo.
     * @return Texto encriptado.
     * @throws Exception Si ocurre un error en el cifrado.
     */
    public static String encriptar(String texto) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encryptedData = cipher.doFinal(texto.getBytes());
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    /**
     * Desencripta el texto encriptado pasado como parametro.
     * @param textoEncriptado Texto a desencriptar.
     * @return Texto original.
     * @throws Exception Si ocurre un error en el cifrado.
     */
    public static String desencriptar(String textoEncriptado) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] decodedData = Base64.getDecoder().decode(textoEncriptado);
        byte[] decryptedData = cipher.doFinal(decodedData);
        return new String(decryptedData);
    }
}
