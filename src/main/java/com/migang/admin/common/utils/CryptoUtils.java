package com.migang.admin.common.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.migang.admin.common.CommonConstant;
import com.migang.admin.exception.BusinessException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 加解密相关的工具类
 *
 *＠param addTime 2017-08-24
 *@param author   ChengBo
 */
public class CryptoUtils {

	//设置默认字符编码
	private static final String DEFAULT_CHARSET = "UTF-8";
	
	//设置日志记录
	private static final Logger logger = LoggerFactory.getLogger(CryptoUtils.class);
	
	/**
     * MD5加密
     * 
     * @param bytes
     * 
     * @return a {@link java.lang.String} object.
     */
    public static String encodeMD5(final byte[] bytes) {
        return DigestUtils.md5Hex(bytes);
    }

    /**
     * MD5加密，默认UTF-8
     * 
     * @param str
     * 
     * @return a {@link java.lang.String} object.
     */
    public static String encodeMD5(final String str) {
        return encodeMD5(str, DEFAULT_CHARSET);
    }

    /**
     * MD5加密
     * 
     * @param str
     * 
     * @param charset

     * @return a {@link java.lang.String} object.
     */
    public static String encodeMD5(final String str, final String charset) {
        if (str == null) {
            return null;
        }
        try {
            byte[] bytes = str.getBytes(charset);
            return encodeMD5(bytes);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * SHA加密
     * 
     * @param bytes
     * 
     * @return a {@link java.lang.String} object.
     */
    public static String encodeSHA(final byte[] bytes) {
        return DigestUtils.sha512Hex(bytes);
    }

    /**
     * SHA加密
     * 
     * @param str
     * @param charset
     * 
     * @return a {@link java.lang.String} object.
     */
    public static String encodeSHA(final String str, final String charset) {
        if (str == null) {
            return null;
        }
        try {
            byte[] bytes = str.getBytes(charset);
            return encodeSHA(bytes);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * SHA加密,默认utf-8
	* 
	* @param str
	*            
	* @return a {@link java.lang.String} object.
	*/
	public static String encodeSHA(final String str) {
		return encodeSHA(str, DEFAULT_CHARSET);
	}

	/**
	* BASE64加密
	* 
	* @param bytes
	*            
	* @return a {@link java.lang.String} object.
	*/
	public static String encodeBASE64(final byte[] bytes) {
		return new String(Base64.encodeBase64String(bytes));
	}

	/**
	* BASE64加密
	* 
	* @param str
	* @param charset
	* 
	* @return a {@link java.lang.String} object.
	*/
	public static String encodeBASE64(final String str, String charset) {
		if (str == null) {
		   return null;
		}
		try {
		   byte[] bytes = str.getBytes(charset);
		   return encodeBASE64(bytes);
		} catch (UnsupportedEncodingException e) {
		   throw new RuntimeException(e);
		}
	}

	/**
	* BASE64加密,默认UTF-8
	* 
	* @param str
	*            
	* @return a {@link java.lang.String} object.
	*/
	public static String encodeBASE64(final String str) {
		return encodeBASE64(str, DEFAULT_CHARSET);
	}

	/**
	* BASE64解密,默认UTF-8
	* 
	* @param str
	*            
	* @return a {@link java.lang.String} object.
	*/
	public static String decodeBASE64(String str) {
		return decodeBASE64(str, DEFAULT_CHARSET);
	}

	/**
	* BASE64解密
	* 
	* @param str 
	* @param charset
	*            
	* @return a {@link java.lang.String} object.
	*/
	public static String decodeBASE64(String str, String charset) {
		try {
		   byte[] bytes = str.getBytes(charset);
		   return new String(Base64.decodeBase64(bytes));
		} catch (UnsupportedEncodingException e) {
		   throw new RuntimeException(e);
		}
	}

	/**
	* CRC32字节校验
	* 
	* @param bytes
	* 
	* @return a {@link java.lang.String} object.
	*/
	public static String crc32(byte[] bytes) {
		CRC32 crc32 = new CRC32();
		crc32.update(bytes);
		return Long.toHexString(crc32.getValue());
	}

	/**
	* CRC32字符串校验
	* 
	* @param str
	* @param charset
	*            
	* @return a {@link java.lang.String} object.
	*/
	public static String crc32(final String str, String charset) {
		try {
		   byte[] bytes = str.getBytes(charset);
		   return crc32(bytes);
		} catch (UnsupportedEncodingException e) {
		   throw new RuntimeException(e);
		}
	}

	/**
	* CRC32字符串校验,默认UTF-8编码读取
	* 
	* @param str
	*           
	* @return a {@link java.lang.String} object.
	*/
	public static String crc32(final String str) {
		return crc32(str, DEFAULT_CHARSET);
	}

	/**
	* CRC32流校验
	* 
	* @param input
	*           
	* @return a {@link java.lang.String} object.
	*/
	public static String crc32(InputStream input) {
		CRC32 crc32 = new CRC32();
		CheckedInputStream checkInputStream = null;
		int test = 0;
		try {
		   checkInputStream = new CheckedInputStream(input, crc32);
		   do {
		       test = checkInputStream.read();
		   } while (test != -1);
		   return Long.toHexString(crc32.getValue());
		} catch (IOException e) {
		   e.printStackTrace();
		   throw new RuntimeException(e);
		}
	}

	/**
	* CRC32文件唯一校验
	* 
	* @param file
	*         
	* @return a {@link java.lang.String} object.
	*/
	public static String crc32(File file) {
		InputStream input = null;
		try {
		   input = new BufferedInputStream(new FileInputStream(file));
		   return crc32(input);
		} catch (FileNotFoundException e) {
		   e.printStackTrace();
		   throw new RuntimeException(e);
		} finally {
		   IOUtils.closeQuietly(input);
		}
	}

	/**
	* CRC32文件唯一校验
	* 
	* @param url
	*         
	* @return a {@link java.lang.String} object.
	*/
	public static String crc32(URL url) {
		InputStream input = null;
		try {
		   input = url.openStream();
		   return crc32(input);
		} catch (IOException e) {
		   e.printStackTrace();
		   throw new RuntimeException(e);
		} finally {
		   IOUtils.closeQuietly(input);
		}
	}
	
	/*************************************************************  3DES 加解密　开始　**********************************************************/
	
	/**
	 * 3DES加密字符串
	 * 
	 * @param src
	 * @param key
	 * @return
	 * @throws Exception
	 * 
	 * @param addTime 2017年9月18日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
    public static String encryptThreeDESECB(final String src, final String key) throws BusinessException{
    	try{
	        final DESedeKeySpec dks = new DESedeKeySpec(key.getBytes(DEFAULT_CHARSET));
	        final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
	        final SecretKey securekey = keyFactory.generateSecret(dks);
	
	        final Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
	        cipher.init(Cipher.ENCRYPT_MODE, securekey);
	        final byte[] b = cipher.doFinal(URLEncoder.encode(src, DEFAULT_CHARSET).getBytes());
	
	        final BASE64Encoder encoder = new BASE64Encoder();
	        return encoder.encode(b).replaceAll("\r", "").replaceAll("\n", "");
    	}catch(Exception ex){
    		//记录日志
			logger.error("CryptoUtils encryptThreeDESECB  encodeString: "+src+" exception: "+ex.getMessage());
			
			//抛出异常
			throw new BusinessException(CommonConstant.COMMON_ENCODE_STRING_ERROR, "数据加密失败");
    	}
    }
	
	
	/**
	 * 3Des解密字符串
	 * 
	 * @param src
	 * @param key
	 * @return
	 * @throws Exception
	 * 
	 * @param addTime 2017年9月18日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
    public static String decryptThreeDESECB(final String src, final String key) throws BusinessException{
    	try{
	        //通过Base64, 将字符串转成byte数组
	        final BASE64Decoder decoder = new BASE64Decoder();
	        final byte[] bytesrc = decoder.decodeBuffer(src);
	        
	        //解密的key
	        final DESedeKeySpec dks = new DESedeKeySpec(key.getBytes(DEFAULT_CHARSET));
	        final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
	        final SecretKey securekey = keyFactory.generateSecret(dks);
	
	        //Chipher对象解密
	        final Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
	        cipher.init(Cipher.DECRYPT_MODE, securekey);
	        final byte[] retByte = cipher.doFinal(bytesrc);
	        return URLDecoder.decode(new String(retByte), DEFAULT_CHARSET);
    	}catch(Exception ex){
    		//记录日志
			logger.error("CryptoUtils decryptThreeDESECB  encodeString: "+src+" exception: "+ex.getMessage());
			
			//抛出异常
			throw new BusinessException(CommonConstant.COMMON_DECODE_STRING_ERROR, "数据解密失败");
    	}
    }
	
	/*************************************************************  3DES 加解密　结束　**********************************************************/

    /*************************************************************  AES 加解密　开始　**********************************************************/
    
    /**
     * AES加密操作
     * 
     * 	1.构造密钥生成器
     * 	2.根据encodeRules规则初始化密钥生成器
     * 	3.产生密钥
     * 	4.创建和初始化密码器
     * 	5.内容加密
     * 	6.返回字符串
     * 
     * @param content
     * @param ecnodeRules
     * @return
     * 
     * @param addTime 2017年10月25日
     * @param author     ChengBo
     * @throws BusinessException 
     */
    public static String _AESEncode(String content, String encodeRules) throws BusinessException {
        try {
            //1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            
            //2.根据ecnodeRules规则初始化密钥生成器
            //生成一个128位的随机源,根据传入的字节数组
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(encodeRules.getBytes());
            keygen.init(128, random);
            
            //3.产生原始对称密钥
            SecretKey original_key = keygen.generateKey();
            
            //4.获得原始对称密钥的字节数组
            byte[] raw = original_key.getEncoded();
            
            //5.根据字节数组生成AES密钥
            SecretKey key = new SecretKeySpec(raw, "AES");
            
            //6.根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance("AES");
            
            //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.ENCRYPT_MODE, key);
            
            //8.获取加密内容的字节数组(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
            byte[] byte_encode = content.getBytes(DEFAULT_CHARSET);
            
            //9.根据密码器的初始化方式--加密：将数据加密
            byte[] byte_AES = cipher.doFinal(byte_encode);
            
            //10.将加密后的数据转换为字符串
            //这里用Base64Encoder中会找不到包
            //解决办法：在项目的Build path中先移除JRE System Library，再添加库JRE System Library，重新编译后就一切正常了。
            String AES_encode = new String(new BASE64Encoder().encode(byte_AES));
            
            //11.将字符串返回
            return AES_encode;
        }catch(Exception ex){
    		//记录日志
			logger.error("CryptoUtils AESEncode  encodeString: "+content+" exception: "+ex.getMessage());
			
			//抛出异常
			throw new BusinessException(CommonConstant.COMMON_ENCODE_STRING_ERROR, "数据加密失败");
    	}
    }
    
    /**
     * AES解密数据
     * 
     * 	1.同加密1-4步
     * 	2.将加密后的字符串反纺成byte[]数组
     * 	3.将加密内容解密
     * 
     * @param content
     * @param encodeRules
     * @return
     * 
     * @param addTime 2017年10月25日
     * @param author     ChengBo
     * @throws BusinessException 
     */
    public static String _AESDecode(String content, String encodeRules) throws BusinessException {
        try {
            //1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            
            //2.根据ecnodeRules规则初始化密钥生成器
            //生成一个128位的随机源,根据传入的字节数组
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(encodeRules.getBytes());
            keygen.init(128, random);
            
            //3.产生原始对称密钥
            SecretKey original_key = keygen.generateKey();
            
            //4.获得原始对称密钥的字节数组
            byte[] raw = original_key.getEncoded();
            
            //5.根据字节数组生成AES密钥
            SecretKey key = new SecretKeySpec(raw, "AES");
            
            //6.根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance("AES");
            
            //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.DECRYPT_MODE, key);
            
            //8.将加密并编码后的内容解码成字节数组
            byte[] byte_content = new BASE64Decoder().decodeBuffer(content);
            
            // 9.解密
            byte[] byte_decode = cipher.doFinal(byte_content);
            String AES_decode = new String(byte_decode, DEFAULT_CHARSET);
            
            //10.返回结果
            return AES_decode;
        } catch(Exception ex){
    		//记录日志
			logger.error("CryptoUtils AESDecode  decodeString: "+content+" exception: "+ex.getMessage());
			
			//抛出异常
			throw new BusinessException(CommonConstant.COMMON_DECODE_STRING_ERROR, "数据解密失败");
    	}
    }
    
    /**
     * AES加密操作
     * 
     * 	备注：这种方式是App那边孙兴磊提供的，可以和C++加解密正常通信
     * 
     * @param content
     * @param encodeRules
     * @return
     * @throws BusinessException
     * 
     * @param addTime 2017年11月7日
     * @param author     ChengBo
     */
    public static String AESEncode(String content, String encodeRules) throws BusinessException {
    	try {
			byte[] bytIn = content.getBytes(DEFAULT_CHARSET);
			SecretKeySpec skeySpec = new SecretKeySpec(encodeRules.getBytes(DEFAULT_CHARSET), "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			byte[] bytOut = cipher.doFinal(bytIn);
			String ecrOut = new BASE64Encoder().encode(bytOut);
			return ecrOut;
    	 }catch(Exception ex){
     		//记录日志
 			logger.error("CryptoUtils AESEncode  encodeString: "+content+" exception: "+ex.getMessage());
 			
 			//抛出异常
 			throw new BusinessException(CommonConstant.COMMON_ENCODE_STRING_ERROR, "数据加密失败");
     	}
    }
    
    /**
     * AES解密数据
     * 
     * 	备注：这种方式是App那边孙兴磊提供的，可以和C++加解密正常通信
     * 
     * @param content
     * @param encodeRules
     * @return
     * @throws BusinessException
     * 
     * @param addTime 2017年11月7日
     * @param author     ChengBo
     */
    public static String AESDecode(String content, String encodeRules) throws BusinessException {
    	try {
	    	byte[] raw = encodeRules.getBytes(DEFAULT_CHARSET);
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			byte[] encrypted1 = new BASE64Decoder().decodeBuffer(content);
			byte[] original = cipher.doFinal(encrypted1);
			String originalString = new String(original, DEFAULT_CHARSET);
			return originalString;
    	} catch(Exception ex){
    		//记录日志
			logger.error("CryptoUtils AESDecode  decodeString: "+content+" exception: "+ex.getMessage());
			
			//抛出异常
			throw new BusinessException(CommonConstant.COMMON_DECODE_STRING_ERROR, "数据解密失败");
    	}
    }
    
    /**
     * AES加密数据
     * 
     * 	备注 : 这个方法可以可JS的CryptoJS.AES加解密正常通信
     *					http://blog.csdn.net/black_dreamer/article/details/51902323
     * 
     * @param content
     * @param key
     * @return
     * @throws Exception
     * 
     * @param addTime 2017年12月21日
     * @param author     ChengBo
     */
    public static String jSAESEncode(String data, String key) throws Exception {
         String iv = "1234567812345678";
         
         Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
         int blockSize = cipher.getBlockSize();
         byte[] dataBytes = data.getBytes();
         
         int plaintextLength = dataBytes.length;
         if (plaintextLength % blockSize != 0) {
             plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
         }
         byte[] plaintext = new byte[plaintextLength];
         System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
         SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
         IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
         
         cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
         byte[] encrypted = cipher.doFinal(plaintext);
         
         return new sun.misc.BASE64Encoder().encode(encrypted);
    }
    
    /**
     * AES解密数据   
     * 
     * 	备注 : 这个方法可以可JS的CryptoJS.AES加解密正常通信 
     * 				http://blog.csdn.net/black_dreamer/article/details/51902323
     * 
     * @param encrypted
     * @param key
     * @return
     * @throws Exception
     * 
     * @param addTime 2017年12月21日
     * @param author     ChengBo
     */
    public static String jSAESDecode(String encrypted, String key) throws Exception {
    	String data = encrypted ;
        String iv = "1234567812345678";
        
        byte[] encrypted1 = new BASE64Decoder().decodeBuffer(data);
		Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
        SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
        IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
        byte[] original = cipher.doFinal(encrypted1);
        String originalString = new String(original);
        
        return originalString;
    }
    
    /*************************************************************  AES 加解密　结束　**********************************************************/
}