package com.migang.admin.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.migang.admin.BaseTest;
import com.migang.admin.common.CommonConstant;
import com.migang.admin.exception.BusinessException;

/**
 * 工具类－加解密测试
 *
 *＠param addTime 2017-08-12
 *@param author   ChengBo
 */
public class CryptoUtilsTest extends BaseTest {

	//引入日志操作类
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 测试　md5加密
	 */
	@Test
	public void strMd5() throws Exception{
		//设置加密字符串
		String str = "migang";
		
		//加密数据
		String md5Str = CryptoUtils.encodeMD5(str);
		logger.info("beforeMd5:"+str+" afterMd5:"+md5Str);
	}
	
	/**
	 * 测试3Des加解密
	 * 
	 * @param addTime 2017年9月18日
	 * @param author     ChengBo
	 * 
	 * @throws Exception 
	 * @throws UnsupportedEncodingException 
	 */
	@Test
	public void test3Des() throws UnsupportedEncodingException, Exception{
		final String key = CommonConstant.AESKey;
        // 加密流程
//     String telePhone = "{\"mobile\":\"15613187762\",\"password\":\"123456\",\"captcha\":\"abc123\"}";
//		String telePhone = "{\"token\":\"7Yg4zglAd9ohH+nCbbHAX6qpUBc++Bp8HYINGKeDh4uBEKZKyO594Q==\"}";
//		String telePhone =  "{\"mobile\":\"15613187762\",\"password\":\"123456\"}";
//     String telePhone = "15613187762";
//		String telePhone = "{\"address\":\"中华南大街132号 盛景大厦3#6102\",\"city\":\"130\",\"degree\":\"1\",\"wechat\":\"wx_AHfH52\",\"realname\":\"王二\",\"cardnum\":\"360402197104020013\",\"province\":\"6\",\"marriage\":1,\"district\":\"545\",\"liveTime\":3,\"child\":2}";
//     String telePhone = "{\"token\":\"NbpgecKwNQ1XaASzvMoSj6m4ucev3oYypWLiptWl2QeBEKZKyO594Q==\", \"jobName\":\"RN开发工程师\", \"income\":2, \"company\":\"中国人民银行\", \"province\":\"北京\", \"city\":\"北京市\", \"district\":\"朝阳区\", \"address\":\"畅青园\", \"phone\":\"010-89878768\"}";
//		String telePhone = "{\"token\":\"9ljUy+h2BkDnOdtarE4cy6elP+nluV1VFt2gjrLhAr2BEKZKyO594Q==\", \"name\":\"马化腾\", \"phone\":\"010-89878987\", \"relation\":1, \"remark\":\"备注3\"}";
//		String telePhone = "{\"token\":\"9ljUy+h2BkDnOdtarE4cy6elP+nluV1VFt2gjrLhAr2BEKZKyO594Q==\", \"serviceCode\":\"abc123\"}";
//		String telePhone = "{\"token\":\"9ljUy+h2BkDnOdtarE4cy6elP+nluV1VFt2gjrLhAr2BEKZKyO594Q==\", \"mobileCode\":\"abc123\", \"picCode\":\"abc123\"}";
//		String telePhone = "{\"token\":\"tJ9Hk663B/3zoE4UsYbfRylo85D1yXbgkVmgtCb2hxCBEKZKyO594Q==\", \"bankNum\":\"6229489878787878787\", \"mobile\":\"15613187762\",\"bankName\":\"中国农业银行\",\"bankProvince\":\"河北省\",\"bankCity\":\"石家庄市\"}";
//		String telePhone = "{\"itemLimit\":\"7天\", \"borrowMoney\":\"1000\"}";
//		String telePhone = "{\"token\":\"7Yg4zglAd9ohH+nCbbHAX6qpUBc++Bp8HYINGKeDh4uBEKZKyO594Q==\", \"limit\": 4}";
//		String telePhone = "{\"content\":\"现金贷，是小额现金贷款业务的简称，具有方便灵活的借款与还款方式，以及实时审批、快速到账的特性。从2015年开始，现金贷作为消费金融一个重要的分支在中国开始强势崛起。目前一二线城市以线上为主，三四线城市以线下为主。\"}";
//		String telePhone = "{\"mobile\":\"15613187762\", \"type\":\"1\"}";
//		String telePhone = "{\"mobile\":\"15613187762\", \"captcha\":\"abc123\"}";
//		String telePhone = "{\"mobile\":\"15613187762\", \"captcha\":\"abc123\", \"password\":\"123123\"}";
//		String telePhone = "{\"orderId\":\"51\"}";
//		String telePhone = "{\"page\":\"1\", \"pageSize\":\"10\"}";
//		String telePhone = "{\"limit\":\"2\"}"; 
		String telePhone = "This.is.a.message.";
		String telePhone_encrypt = CryptoUtils.AESEncode(telePhone, key);
		System.out.println("加密Key："+key);
        System.out.println("加密前的字符串："+telePhone);
        System.out.println("加密后的字符串："+telePhone_encrypt);

        // 解密流程
        String tele_decrypt = CryptoUtils.AESDecode(telePhone_encrypt, key);
        System.out.println("解密后的字符串：" + tele_decrypt);
		
      String tele_decrypt1 = CryptoUtils.AESDecode("o0QPdktEXGFe3D82nsEt8YWXznQ/tBEZQDGzkGCu84MLYOQWEqDDWMU/gaE8YeC5Fy+9c60uqN7p\nYOAMgjmj8rHf/Y2jqEgWdAPe806hxD0lz+dGqfFk63uMYH0XJNMD6cmewKzF4w0mmTXANuH/LyVy\n1TuwMrDf/bISJu1yxlekssRSYiE2ZCUFHj2ypWTTGB7/GKfDCfnbM66Nxt2tptd0xToNRITnXy8W\nSkiD+6v4w3+nCcnP1nHx/sKjtmRbxEQDORozV83PfqcYoMMvcdN327z79T/C+LUppJi7wcKxFP+2\nBAx9U8ApJj0zXPX6", CommonConstant.AESKey);
      System.out.println("解密后的字符串：" + tele_decrypt1);
	}
	
	/**
	 * 测试Base64加解密
	 * 
	 * @param addTime 2017年9月18日
	 * @param author     ChengBo
	 */
	@Test
	public void testBase64(){
		String str = "Hello world.";
		System.out.println("加密前字符串："+str);
		
		String enStr = CryptoUtils.encodeBASE64(str);
		System.out.println("Base64加密后："+enStr);
		
		String deStr = CryptoUtils.decodeBASE64(enStr);
		System.out.println("Base64解密后："+ deStr);
	}
	
	/**
	 * 测试　AES加解密操作
	 * 
	 * @throws BusinessException
	 * 
	 * @param addTime 2017年10月25日
	 * @param author     ChengBo
	 */
	@Test
	public void testAesEncode() throws BusinessException{
		//加密规则
		//String encodeRules = "a6ad82fe158f02efbf11a97cddcc2b50";
		String encodeRules = "a6ad82fe158f02ef";
		logger.debug("加密规则字符串："+encodeRules);
		
		//原始数据
		String str = "15613187762";
		logger.debug("加密前的字符串："+ str);
		
		//加密数据
		String encodeData = CryptoUtils.AESEncode(str, encodeRules);
		logger.debug("加密后的字符串："+encodeData);
		
		//解密数据
		String decodeData = CryptoUtils.AESDecode(encodeData, encodeRules);
		logger.debug("解密后的字符串："+decodeData);
		
	}
	
	@Test
	public void testAbc() throws BusinessException{
		/*
		 * 此处使用AES-128-ECB加密模式，key需要为16位。
		 */
		String cKey = "2e60486b8bbf6288";
		// 需要加密的字串
		String cSrc = "lJ6E/wEU2FWlF69OILN9gyIoP/1uKjOFH22F2uY9w7AAh/t7P7JcMniZyTfUZMmhgIkApIiUgxH+wGLHLFX/li/CthKJticNgpFF+w9ac/M=";
		System.out.println(cSrc);
		// 加密
		String enString = CryptoUtils.AESEncode(cSrc, cKey);
		System.out.println("加密后的字串是：" + enString);
		// 解密
		String DeString = CryptoUtils.AESDecode(cSrc, cKey);
		System.out.println("解密后的字串是：" + DeString);
	}
	
	@Test
	public void testJavaJSAES() throws Exception{
		//加密数据
		String content = "{\"mobile\":\"15613187762\", \"type\":\"1\"}";
		String key = "1234567812345678";
		String ret = CryptoUtils.jSAESEncode(content, key);
		System.out.println("加密结果："+ ret);
		
		//解密数据
		String deRet = CryptoUtils.jSAESDecode(ret, key);
		System.out.println("解密数据: "+deRet);
	}
	
	@Test
	public void testAliAES() throws BusinessException{
		String content = "my message";
		String encodeRules = "secret key 123";
		String encode = CryptoUtils._AESEncode(content, encodeRules);
		String decode = CryptoUtils._AESDecode(encode, encodeRules);
		
		System.out.println("加密后字符串: "+ encode);
		System.out.println("加密前字符串: "+ decode);
	}
	
}
