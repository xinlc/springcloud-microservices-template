package com.example.common.utils;

import cn.hutool.crypto.SecureUtil;
import com.sun.crypto.provider.SunJCE;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.Security;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public final class DesEncryptUtil {

    /** 字符串默认键值 */
    private static String strDefaultKey = "ZK";
    /** 加密工具 */
    private Cipher encryptCipher = null;
    /** 解密工具 */
    private Cipher decryptCipher = null;

    /**
     * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[]
     * hexStr2ByteArr(String strIn) 互为可逆的转换过程
     * @param arrB 需要转换的byte数组
     * @return 转换后的字符串
     */
    public static String byteArr2HexStr(byte[] arrB) {
        int iLen = arrB.length;
        // 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
        StringBuffer sb = new StringBuffer(iLen * 2);
        for (int i = 0; i < iLen; i++) {
            int intTmp = arrB[i];
            // 把负数转换为正数
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }
            // 小于0F的数需要在前面补0
            if (intTmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString();
    }

    /**
     * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB)
     * 互为可逆的转换过程
     * @param strIn 需要转换的字符串
     * @return 转换后的byte数组
     * @throws Exception 本方法不处理任何异常，所有异常全部抛出
     */
    public static byte[] hexStr2ByteArr(String strIn) {
        try {
            byte[] arrB = strIn.getBytes();
            int iLen = arrB.length;

            // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
            byte[] arrOut = new byte[iLen / 2];
            int leg = 2;
            for (int i = 0; i < iLen; i = i + leg) {
                String strTmp = new String(arrB, i, 2);
                arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
            }
            return arrOut;
        } catch (Exception ex) {
            log.debug(ex.toString());
        }
        return new byte[0];
    }

    /**
     * 默认构造方法，使用默认密钥
     * @throws Exception
     */
    public DesEncryptUtil() {
        this(strDefaultKey);
    }

    /**
     * 指定密钥构造方法
     * @param strKey 指定的密钥
     * @throws Exception
     */
    public DesEncryptUtil(String strKey) {
        try {
            Security.addProvider(new SunJCE());
            Key key = getKey(strKey.getBytes());

            encryptCipher = Cipher.getInstance("DES");
            encryptCipher.init(Cipher.ENCRYPT_MODE, key);

            decryptCipher = Cipher.getInstance("DES");
            decryptCipher.init(Cipher.DECRYPT_MODE, key);
        } catch (Exception ex) {
            log.debug(ex.toString());
        }
    }

    /**
     * 加密字节数组
     * @param arrB 需加密的字节数组
     * @return 加密后的字节数组
     * @throws Exception
     */
    public byte[] encrypt(byte[] arrB) {
        try{
            return encryptCipher.doFinal(arrB);
        } catch (Exception ex) {
            log.debug(ex.toString());
        }
        return arrB;
    }

    /**
     * 加密字符串
     * @param strIn 需加密的字符串
     * @return 加密后的字符串
     * @throws Exception
     */
    public String encrypt(String strIn) {
        return byteArr2HexStr(encrypt(strIn.getBytes()));
    }

    /**
     * 解密字节数组
     * @param arrB 需解密的字节数组
     * @return 解密后的字节数组
     * @throws Exception
     */
    public byte[] decrypt(byte[] arrB) {
        try {
            return decryptCipher.doFinal(arrB);
        } catch (Exception ex) {
            log.debug(ex.toString());
        }
        return arrB;
    }

    /**
     * 解密字符串
     * @param strIn 需解密的字符串
     * @return 解密后的字符串
     * @throws Exception
     */
    public String decrypt(String strIn) {
        return new String(decrypt(hexStr2ByteArr(strIn)));
    }

    /**
     * 从指定字符串生成密钥，密钥所需的字节数组长度为8位 不足8位时后面补0，超出8位只取前8位
     * @param arrBTmp 构成该字符串的字节数组
     * @return 生成的密钥
     * @throws Exception
     */
    private Key getKey(byte[] arrBTmp) {
        // 创建一个空的8位字节数组（默认值为0）
        byte[] arrB = new byte[8];

        // 将原始字节数组转换为8位
        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }

        // 生成密钥
        Key key = new SecretKeySpec(arrB, "DES");

        return key;
    }

    /**
     * IM 接口签名
     * @param secretKey 应用秘钥
     * @param nonce 随机数
     * @param timestamp 时间戳（毫秒）
     * @return 签名
     */
    public static String signature(String secretKey, String nonce, String timestamp) {
//        // 字符串参数
//        StringBuilder sb = new StringBuilder(secretKey);
//        sb.append(nonce);
//        sb.append(timestamp);
//        String s = sb.toString();
//        String signature = SecureUtil.sha1(s);

        // Map 参数
        Map<String, Object> map = new HashMap<>();
        map.put("secretKey", secretKey);
        map.put("nonce", nonce);
        map.put("timestamp", timestamp);
        String signature = SecureUtil.signParamsSha1(map);

        return signature;
    }

    public static void main(String[] args) {
		try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String test = "990a08fae28642f99faaa05cd604aa29&029aee7adf06455af44423477ff6d0ba&1074&"+simpleDateFormat.format(new Date());
            DesEncryptUtil des = new DesEncryptUtil(strDefaultKey);
            System.out.println("加密前的字符：" + test);
            System.out.println("加密后的字符：" + des.encrypt(test));
            System.out.println("解密后的字符：" + des.decrypt(des.encrypt(test)));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
    }

}
