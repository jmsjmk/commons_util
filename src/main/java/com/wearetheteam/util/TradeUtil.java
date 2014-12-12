/**
 * Project Name:common_util
 * File Name:TradeUtil.java
 * Package Name:com.wearetheteam.util
 * Date:2014年12月12日上午11:15:44
 *
 */

package com.wearetheteam.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.sun.org.apache.xpath.internal.operations.And;

/**
 * ClassName:TradeUtil <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2014年12月12日 上午11:15:44 <br/>
 *
 * @author Administrator
 * @version
 * @since JDK 1.6
 * @see
 */
public class TradeUtil {
	private static Logger logger = Logger.getLogger(TradeUtil.class);

	/**
	 * 根据指定的签名密钥和算法签名Map<String,String>
	 *
	 * @see 方法内部首先会过滤Map<String,String>参数中的部分键值对
	 * @see 过滤规则:移除键名为"cert","hmac","signMsg"或者键值为null或者键值长度为零的键值对
	 * @see 过滤结果 
	 *      :过滤完Map<String,String>后会产生一个字符串,其格式为[key11=value11|key22=value22|key33
	 *      =value33]
	 * @see And the calls
	 *      {@link TradeUtil#getHexSign(String,String,String,boolean)}进行签名
	 * @param param
	 *          待签名的Map<String,String>
	 * @param charset
	 *          签名时转码用到的字符集
	 * @param algorithm
	 *          签名时所使用的算法,从业务上看,目前其可传入两个值:MD5,SHA-1
	 * @param signKey
	 *          签名用到的密钥
	 * @return String algorithm digest as a lowerCase hex string
	 */
	public static String getHexSign(Map<String, String> param, String charset, String algorithm, String signKey) {
		StringBuilder sb = new StringBuilder();
		List<String> keys = new ArrayList<String>(param.keySet());
		Collections.sort(keys);
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = param.get(key);
			if (key.equalsIgnoreCase("cert") || key.equalsIgnoreCase("hmac") || key.equalsIgnoreCase("signMsg") || (value == null) || (value.length() == 0)) {
				continue;
			}
			sb.append(key).append("=").append(value).append("|");
		}
		sb.append("key=").append(signKey);
		return getHexSign(sb.toString(), charset, algorithm, true);
	}

	/**
	 * 通过指定算法签名字符串
	 *
	 * @see Calculates the algorithm digest and returns the value as a hex string
	 * @see If system dosen't support this <code>algorithm</code>, return "" not
	 *      null
	 * @see It will Calls {@link TradeUtil#getBytes(String str, String charset)}
	 * @see 若系统不支持<code>charset</code>字符集,则按照系统默认字符集进行转换
	 * @see 若系统不支持<code>algorithm</code>算法,则直接返回""空字符串
	 * @see 另外,commons-codec.jar提供的DigestUtils.md5Hex(String
	 *      data)与本方法getHexSign(data, "UTF-8", "MD5", false)效果相同
	 * @param data
	 *          Data to digest
	 * @param charset
	 *          字符串转码为byte[]时使用的字符集
	 * @param algorithm
	 *          目前其有效值为<code>MD5,SHA,SHA1,SHA-1,SHA-256,SHA-384,SHA-512</code>
	 * @param toLowerCase
	 *          指定是否返回小写形式的十六进制字符串
	 * @return String algorithm digest as a lowerCase hex string
	 */
	public static String getHexSign(String data, String charset, String algorithm, boolean toLowerCase) {
		char[] DIGITS_LOWER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		char[] DIGITS_UPPER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		// Used to build output as Hex
		char[] DIGITS = toLowerCase ? DIGITS_LOWER : DIGITS_UPPER;
		// get byte[] from {@link TradeUtil#getBytes(String, String)}
		byte[] dataBytes = StringUtil.getBytes(data, charset);
		byte[] algorithmData = null;
		try {
			// get an algorithm digest instance
			algorithmData = MessageDigest.getInstance(algorithm).digest(dataBytes);
		} catch (NoSuchAlgorithmException e) {
			logger.error("签名字符串[" + data + "]时发生异常:System doesn't support this algorithm[" + algorithm + "]");
			return "";
		}
		char[] respData = new char[algorithmData.length << 1];
		// two characters form the hex value
		for (int i = 0, j = 0; i < algorithmData.length; i++) {
			respData[j++] = DIGITS[(0xF0 & algorithmData[i]) >>> 4];
			respData[j++] = DIGITS[0x0F & algorithmData[i]];
		}
		return new String(respData);
	}

	/**
	 * 获取系统流水号
	 *
	 * @see 若欲指定返回值的长度or是否全由数字组成等,you can call
	 *      {@link TradeUtil#getSysJournalNo(int, boolean)}
	 * @return 长度为20的全数字
	 */
	public static String getSysJournalNo() {
		return getSysJournalNo(20, true);
	}

	/**
	 * 获取系统流水号
	 *
	 * @param length
	 *          指定流水号长度
	 * @param toNumber
	 *          指定流水号是否全由数字组成
	 */
	public static String getSysJournalNo(int length, boolean isNumber) {
		// replaceAll()之后返回的是一个由十六进制形式组成的且长度为32的字符串
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		if (uuid.length() > length) {
			uuid = uuid.substring(0, length);
		} else if (uuid.length() < length) {
			for (int i = 0; i < (length - uuid.length()); i++) {
				uuid = uuid + Math.round(Math.random() * 9);
			}
		}
		if (isNumber) {
			return uuid.replaceAll("a", "1").replaceAll("b", "2").replaceAll("c", "3").replaceAll("d", "4").replaceAll("e", "5").replaceAll("f", "6");
		} else {
			return uuid;
		}
	}

	/**
	 * 金额分转元
	 *
	 * @see 注意:如果传入的参数中含小数点,则直接原样返回
	 * @see 该方法返回的金额字符串格式为<code>00.00</code>,其整数位有且至少有一个,小数位有且长度固定为2
	 * @param amount
	 *          金额的分进制字符串
	 * @return String 金额的元进制字符串
	 */
	public static String moneyFenToYuan(String amount) {
		if (StringUtils.isEmpty(amount)) {
			return amount;
		}
		if (amount.indexOf(".") > -1) {
			return amount;
		}
		if (amount.length() == 1) {
			return "0.0" + amount;
		} else if (amount.length() == 2) {
			return "0." + amount;
		} else {
			return amount.substring(0, amount.length() - 2) + "." + amount.substring(amount.length() - 2);
		}
	}

	/**
	 * 金额元转分
	 *
	 * @see 注意:该方法可处理贰仟万以内的金额,且若有小数位,则不限小数位的长度
	 * @see 注意:如果你的金额达到了贰仟万以上,则不推荐使用该方法,否则计算出来的结果会令人大吃一惊
	 * @param amount
	 *          金额的元进制字符串
	 * @return String 金额的分进制字符串
	 */
	public static String moneyYuanToFen(String amount) {
		if (StringUtils.isEmpty(amount)) {
			return amount;
		}
		// 传入的金额字符串代表的是一个整数
		if (-1 == amount.indexOf(".")) {
			return (Integer.parseInt(amount) * 100) + "";
		}
		// 传入的金额字符串里面含小数点-->取小数点前面的字符串,并将之转换成单位为分的整数表示
		int money_fen = Integer.parseInt(amount.substring(0, amount.indexOf("."))) * 100;
		// 取到小数点后面的字符串
		String pointBehind = (amount.substring(amount.indexOf(".") + 1));
		// amount=12.3
		if (pointBehind.length() == 1) {
			return money_fen + (Integer.parseInt(pointBehind) * 10) + "";
		}
		// 小数点后面的第一位字符串的整数表示
		int pointString_1 = Integer.parseInt(pointBehind.substring(0, 1));
		// 小数点后面的第二位字符串的整数表示
		int pointString_2 = Integer.parseInt(pointBehind.substring(1, 2));
		// amount==12.03,amount=12.00,amount=12.30
		if (pointString_1 == 0) {
			return money_fen + pointString_2 + "";
		} else {
			return money_fen + (pointString_1 * 10) + pointString_2 + "";
		}
	}

	/**
	 * 金额元转分
	 *
	 * @see 该方法会将金额中小数点后面的数值,四舍五入后只保留两位....如12.345-->12.35
	 * @see 注意:该方法可处理贰仟万以内的金额
	 * @see 注意:如果你的金额达到了贰仟万以上,则非常不建议使用该方法,否则计算出来的结果会令人大吃一惊
	 * @param amount
	 *          金额的元进制字符串
	 * @return String 金额的分进制字符串
	 */
	public static String moneyYuanToFenByRound(String amount) {
		if (StringUtils.isEmpty(amount)) {
			return amount;
		}
		if (-1 == amount.indexOf(".")) {
			return (Integer.parseInt(amount) * 100) + "";
		}
		int money_fen = Integer.parseInt(amount.substring(0, amount.indexOf("."))) * 100;
		String pointBehind = (amount.substring(amount.indexOf(".") + 1));
		if (pointBehind.length() == 1) {
			return money_fen + (Integer.parseInt(pointBehind) * 10) + "";
		}
		int pointString_1 = Integer.parseInt(pointBehind.substring(0, 1));
		int pointString_2 = Integer.parseInt(pointBehind.substring(1, 2));
		// 下面这种方式用于处理pointBehind=245,286,295,298,995,998等需要四舍五入的情况
		if (pointBehind.length() > 2) {
			int pointString_3 = Integer.parseInt(pointBehind.substring(2, 3));
			if (pointString_3 >= 5) {
				if (pointString_2 == 9) {
					if (pointString_1 == 9) {
						money_fen = money_fen + 100;
						pointString_1 = 0;
						pointString_2 = 0;
					} else {
						pointString_1 = pointString_1 + 1;
						pointString_2 = 0;
					}
				} else {
					pointString_2 = pointString_2 + 1;
				}
			}
		}
		if (pointString_1 == 0) {
			return money_fen + pointString_2 + "";
		} else {
			return money_fen + (pointString_1 * 10) + pointString_2 + "";
		}
	}
}
