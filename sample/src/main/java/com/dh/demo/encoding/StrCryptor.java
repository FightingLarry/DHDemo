package com.dh.demo.encoding;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class StrCryptor {
    /*
'ENCRYPT_CONFIG' => array(
		// default encrypt config，没有其他default的config了。
		'v1' => array(
			'ENCRYPT_KEY' => 'cqgf971sp394@!#0',
			'ENCRYPT_IV' => '1234567812345678',
			'ENCRYPT_MODE' => 'AES/CBC/NoPadding',
		),
		'v2' => array(
			'ENCRYPT_KEY' => 'f@0$e^e7&8d*ca-c',
			'ENCRYPT_IV' => '3646288361054287',
			'ENCRYPT_MODE' => 'AES/CBC/NoPadding',
		),
		'v3' => array(
			'ENCRYPT_KEY' => '8-d%70@e4&e7!9)d',
			'ENCRYPT_IV' => '2864129876205482',
			'ENCRYPT_MODE' => 'AES/CBC/NoPadding',
		),
*/
    private static final String TAG = "StrCryptor";

    /*******AES key*********/
    private String CRYPTOR_KEY = "8-d%70@e4&e7!9)d";  // = "cqgf971sp394@!#0";
    private String IV = "2864129876205482";            // =  "1234567812345678";
    private String ALGORITHM = "AES/CBC/NoPadding";     // = "AES/CBC/NoPadding";
    private static String CHAR_SET = "utf-8";

    public StrCryptor(){
    }

    /**
     * 加密数据
     * @param plainText
     * @return
     * 网络传输数据时需设置成Base64.URL_SAFE
     */
    public String encodeStringAndCompress(String plainText){
        if(!TextUtils.isEmpty(plainText)){
            try {
                return Base64.encodeToString(encrypt(compressZip(plainText), CRYPTOR_KEY), Base64.URL_SAFE);
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        }

        return "";
    }

    public String encodeString(String plainText){
        if(!TextUtils.isEmpty(plainText)){
            try {
                return Base64.encodeToString(encrypt(plainText.getBytes(CHAR_SET), CRYPTOR_KEY), Base64.DEFAULT);
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        }

        return "";
    }

    /**
     * 解密数据
     * @param encodedText
     * @return
     */
    public String decodeStringAndDecompress(String encodedText){
        if(!TextUtils.isEmpty(encodedText)){
            try {
                return decompressZip(decrypt(Base64.decode(encodedText, Base64.DEFAULT), CRYPTOR_KEY));
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        }

        return "";
    }

    public String decodeString(String encodedText){
        if(!TextUtils.isEmpty(encodedText)){
            try {
                return new String(decrypt(Base64.decode(encodedText, Base64.DEFAULT), CRYPTOR_KEY));
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        }

        return "";
    }


    private byte[] encrypt(byte[] dataBytes , String key) throws Exception {
        if (dataBytes == null || dataBytes.length == 0){
            return null;
        }

        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            int blockSize = cipher.getBlockSize();

            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }

            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

            byte[] keybytes = key.getBytes(CHAR_SET);
            SecretKeySpec keyspec = new SecretKeySpec(keybytes, "AES");
            IvParameterSpec ivspec = new IvParameterSpec(IV.getBytes(CHAR_SET));

            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);
            return encrypted;

        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }

    private byte[] decrypt(byte[] encrypted , String key) throws Exception {
        if (encrypted == null || encrypted.length == 0){
            return null;
        }

        try
        {
            Cipher cipher = Cipher.getInstance(ALGORITHM);

            byte[] keybytes = key.getBytes(CHAR_SET);
            SecretKeySpec keyspec = new SecretKeySpec(keybytes, "AES");
            IvParameterSpec ivspec = new IvParameterSpec(IV.getBytes(CHAR_SET));

            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);

            byte[] original = cipher.doFinal(encrypted);
            return original;
        }
        catch (Exception e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }

    /**
     * gzip 解压缩
     * @param zipBytes
     * @return
     */
    private static String decompressZip(byte[] zipBytes){
        BufferedReader bufferedReader = null;

        try {
            GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(zipBytes));
            bufferedReader = new BufferedReader(new InputStreamReader(gis, CHAR_SET));
            String line = null;
            StringBuffer sb = new StringBuffer();

            while((line = bufferedReader.readLine()) != null){
                sb.append(line);
            }

            return sb.toString();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        } finally {
            if(bufferedReader != null){
                try {
                    bufferedReader.close();
                } catch (IOException e) {

                }
            }
        }

        return "";
    }

    /**
     * gzip压缩
     * @param plainText
     * @return
     */
    private static byte[] compressZip(String plainText){
        BufferedWriter bufferedWriter = null;

        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(new GZIPOutputStream(bos), CHAR_SET));
            bufferedWriter.write(plainText);
            bufferedWriter.flush();
            bufferedWriter.close();
            return bos.toByteArray();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        } finally {
            if(bufferedWriter != null){
                try {
                    bufferedWriter.close();
                } catch (IOException e) {

                }
            }
        }

        return null;
    }


}
