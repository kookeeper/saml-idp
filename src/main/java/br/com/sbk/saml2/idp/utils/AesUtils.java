package br.com.sbk.saml2.idp.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Classe responsável por encriptar/decriptar um valor.
 *
 * @author "TC011758 - Mauricio Cruz"
 * @version 1.0.0
 * @since 1.0.0
 *
 */
@Configuration
public class AesUtils {

   private static final String ALGORITHM = "AES";
   private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";

   private static final Logger LOGGER = LoggerFactory.getLogger(AesUtils.class);

   @Value("${aes.key:default}")
   private String aesKey;

   /**
    * Método responsável pre encriptar um valor fornecido.
    * 
    * @author "TC011758 - Mauricio Cruz"
    * @version 1.0.0
    * @since 1.0.0
    * 
    * @param String input String a ser criptografada
    * @param String key Chave de descriptografia
    * 
    * @return valor encriptado.
    * @throws Exception
    */
   public String encrypt(String input) throws Exception {
      byte[] crypted = null;
      try {
         SecretKeySpec skey = new SecretKeySpec(this.aesKey.getBytes(), ALGORITHM);
         Cipher cipher = Cipher.getInstance(TRANSFORMATION);
         cipher.init(Cipher.ENCRYPT_MODE, skey);
         crypted = cipher.doFinal(input.getBytes());
      } catch (Exception e) {
         LOGGER.error(e.getMessage(), e);
         throw new Exception();
      }
      return parseByte2HexStr(crypted);
   }

   /**
    * Método responsável pre desencriptar um valor fornecido.
    * 
    * @author "TC011758 - Mauricio Cruz"
    * @version 1.0.0
    * @since 1.0.0
    * 
    * @param String input String a ser criptografada
    * @param String key Chave de descriptografia
    * 
    * @return valor desencriptado.
    * @throws Exception
    */
   public String decrypt(String input) throws Exception {

      byte[] output = null;

      try {
         SecretKeySpec skey = new SecretKeySpec(this.aesKey.getBytes(), ALGORITHM);
         Cipher cipher = Cipher.getInstance(TRANSFORMATION);
         cipher.init(Cipher.DECRYPT_MODE, skey);
         output = cipher.doFinal(parseHexStr2Byte(input.replace("Bearer ", "")));
      } catch (Exception e) {
         LOGGER.error(e.getMessage(), e);
         throw new Exception();
      }
      return new String(output);
   }

   /**
    * Método responsável por converter hexadecimal em byte.
    * 
    * @author "TC011758 - Mauricio Cruz"
    * @version 1.0.0
    * @since 1.0.0
    * 
    * @param String a ser convertida.
    * 
    * @return String convertida
    */
   private static byte[] parseHexStr2Byte(String hexStr) {

      if (hexStr.length() < 1)
         return null;
      byte[] result = new byte[hexStr.length() / 2];
      for (int i = 0; i < hexStr.length() / 2; i++) {
         int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
         int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
         result[i] = (byte) (high * 16 + low);
      }
      return result;

   }

   /**
    * Método responsável por converter byte em string.
    * 
    * @author "TC011758 - Mauricio Cruz"
    * @version 1.0.0
    * @since 1.0.0
    * 
    * @param String a ser convertida.
    * 
    * @return String convertida
    */
   private static String parseByte2HexStr(byte buf[]) {

      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < buf.length; i++) {
         String hex = Integer.toHexString(buf[i] & 0xFF);
         if (hex.length() == 1) {
            hex = '0' + hex;
         }
         sb.append(hex.toUpperCase());
      }
      return sb.toString();

   }

}
