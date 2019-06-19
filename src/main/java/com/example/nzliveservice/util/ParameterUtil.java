package com.example.nzliveservice.util;

//import com.dd.trade.core.util.CommonUtils;
//import com.dd.trade.pay.config.PayConsts;
//import com.dd.trade.web.util.RandomUtil;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/* *
 *类名：ParameterUtil
 *功能：参数处理类
 */

public class ParameterUtil {

	private static Random rad = new Random();  
	/** 日志 */
	private static final Log log = LogFactory.getLog(ParameterUtil.class);
	
	public static Map<String, String> getParamMap(HttpServletRequest request) {

		// 参数解析
		Map<String, String> orderMap = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		log.debug("requestParams="+requestParams);
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			//log.debug("key="+name);
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = i == values.length - 1 ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			// valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			orderMap.put(name, valueStr);
		}
		
		return orderMap;
	}
	
	public static Map<String, String> getParamMapTransCharSet(HttpServletRequest request) throws UnsupportedEncodingException {

		// 参数解析
		Map<String, String> orderMap = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = i == values.length - 1 ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "UTF-8");
			orderMap.put(name, valueStr);
		}
		
		return orderMap;
	}
	
	public static TreeMap<String, String> getParamTreeMap(HttpServletRequest request) {

		TreeMap<String, String> map = new TreeMap<String, String>();
		Map reqMap = request.getParameterMap();
		for(Object key:reqMap.keySet()){
			String value = ((String[])reqMap.get(key))[0];
			System.out.println(key+";"+value);
			map.put(key.toString(),value);
		}
		return map;
	}
	
	 /**
     * 除去数组中的空值和签名参数
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {

        Map<String, String> result = new HashMap<String, String>();

        if (sArray == null || sArray.size() <= 0) {
            return result;
        }

        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("signMsg")
            		|| key.equalsIgnoreCase("signType")) {
                continue;
            }
            result.put(key, value);
        }

        return result;
    }
    
    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
//     * @param signMap 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> paramMap) {
    	
        List<String> keys = new ArrayList<String>(paramMap.keySet());
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
      
            String key = keys.get(i);
            String value = paramMap.get(key);

            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }
    
    /**
     * 排除值为空以及key=“sign_type” 和 key=“sign”的键值对
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
//     * @param signMap 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkStringExceptEmpty(Map<String, String> paramMap) {
    	
        List<String> keys = new ArrayList<String>(paramMap.keySet());
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
      
            String key = keys.get(i);
            String value = paramMap.get(key);
            if("sign_type".equals(key) || "sign".equals(key) || StringUtils.isEmpty(value)) {
            	continue;
            }

            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }
    
    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
//     * @param signMap 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkStringNoSort(Map<String, String> paramMap) {
    	
        List<String> keys = new ArrayList<String>(paramMap.keySet());

        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = paramMap.get(key);

            if ("signMsg".equals(key)) {
            	continue;
            }
            if (null != value && !"".equals(value)) {
	            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
	                prestr = prestr + key + "=" + value;
	            } else {
	                prestr = prestr + key + "=" + value + "&";
	            }
            }
        }

        return prestr;
    }
    
    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param 字符拼接的参数组
     * @return 拼接后字符串
     * @throws UnsupportedEncodingException 
     */
//    public static String createUrlParam(Map<String, String> paramMap) throws UnsupportedEncodingException {
//
//        List<String> keys = new ArrayList<String>(paramMap.keySet());
//        Collections.sort(keys);
//
//        String prestr = "";
//
//        for (int i = 0; i < keys.size(); i++) {
//            String key = keys.get(i);
//            String value = paramMap.get(key);
//
//            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
//                prestr = prestr + key + "=" + URLEncoder.encode(value, PayConsts.DEFAULT_CHARSET);
//            } else {
//                prestr = prestr + key + "=" + URLEncoder.encode(value, PayConsts.DEFAULT_CHARSET) + "&";
//            }
//        }
//        return prestr;
//    }
    
    /**
     * MAP类型数组转换成NameValuePair类型
     * @param properties  MAP类型数组
     * @return NameValuePair类型数组
     */
    public static NameValuePair[] generatNameValuePair(Map<String, String> properties) {
        NameValuePair[] nameValuePair = new NameValuePair[properties.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            nameValuePair[i++] = new NameValuePair(entry.getKey(), entry.getValue());
        }

        return nameValuePair;
    }
    /**
     * 将对象转化为map
     * @param obj
     * @return
     */
	public static Map ConvertObjToMap(Object obj){
	Map<String,Object> reMap = new HashMap<String,Object>();
	if (obj == null) 
	return null;
	Field[] fields = obj.getClass().getDeclaredFields();
	try {
		for(int i=0;i<fields.length;i++){
			try {
				Field f = obj.getClass().getDeclaredField(fields[i].getName());
				f.setAccessible(true);
				Object o = f.get(obj);
				reMap.put(fields[i].getName(), o);
			} catch (NoSuchFieldException e) {
				 // TODO Auto-generated catch block
				 e.printStackTrace();
			} catch (IllegalArgumentException e) {
				 // TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				 // TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	} catch (SecurityException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
	} 
	return reMap;
}

    
    /**
     * 生成代付交易流水号
     * @param partnerId 付款的商家号  PayWay里的pay_merchant_id
     * @return
     */
    public static String getReqSNno(String partnerId, String strTime) {    	 
		return strTime.substring(2, 8) + partnerId
				+ String.format("%04d", rad.nextInt(10000))
				+ strTime.substring(8, 14);
    }
    
    /**
     * 生成代付交易流水号 (23位)
     * @param partnerId 付款的商家号  PayWay里的pay_merchant_id
     * @return
     */
    public static String getDFSNno(String partnerId, String strTime) {    	 
		return strTime.substring(0, 8) + partnerId.substring(6, 8)
				+ String.format("%04d", rad.nextInt(10000))
				+ strTime.substring(8);
    }
    
	/**
	 * 生成易宝代付流水号 (15位)
	 * @return
	 */
	public static String createYBDFSerialNum(String orderTime) {
		String serialNum = orderTime.substring(2,8)
				+ String.format("%03d", rad.nextInt(1000))
				+ orderTime.substring(8,14);
		return serialNum;
	}
    
    /**
     * 生成易通代付代付批次号
     * @param partnerId
     * @return
     */
    public static String getYtBatchNo(String partnerId, String strTime) {    	 
		return strTime.substring(0, 8) + partnerId
				+ String.format("%04d", rad.nextInt(10000))
				+ strTime.substring(8, 14);
    }
    
	
	/**
	 * 生成汇潮代付流水号 (18位)
	 * @return
	 */
//	public static String createHCDFSerialNum() {
//		String timeStr = CommonUtils.getCurrentTimeMills();
//		String serialNum = String.format("%03d", rad.nextInt(1000))
//				+ timeStr.substring(2,8)
//				+ timeStr.substring(14,17)
//				+ timeStr.substring(8,14);
//
//		return serialNum;
//	}
	
	/**
	 * 生成道合支付流水号 ()
	 * @return
	 */
//	public static String createDHSerialNum(String orderTime) {
//		String timeStr = CommonUtils.getCurrentTimeMills();
//		String serialNum = "DH" + orderTime.substring(0,8)
//				+ String.format("%03d", rad.nextInt(1000))
//				+ orderTime.substring(8,14)
//				+ String.format("%03d", rad.nextInt(1000))
//				+ timeStr.substring(14,17);
//		return serialNum;
//	}
	
	/**
	 * 生成汇潮支付流水号 (18位)
	 * @return
	 */
//	public static String createHCSerialNum(String orderTime) {
//		String timeStr = CommonUtils.getCurrentTimeMills();
//		String serialNum = String.format("%03d", rad.nextInt(1000))
//				+ orderTime.substring(2,8)
//				+ timeStr.substring(14,17)
//				+ orderTime.substring(8,14);
//
//		return serialNum;
//	}
	
	/**
	 * 生成依满流水号 (16位)
	 * @return
	 */
	public static String createYMSerialNum(String orderTime) {
		String serialNum = orderTime.substring(2,8)
				+ String.format("%04d", rad.nextInt(10000))
				+ orderTime.substring(8,14);
		return serialNum;
	}
	
	/**
	 * 生成交易流水号 (21位)
	 * @return
	 */
//	public static String createTradeSerialNum(String orderTime) {
//		String timeStr = CommonUtils.getCurrentTimeMills();
//		String serialNum = String.format("%03d", rad.nextInt(1000))
//				+ orderTime.substring(2,8)
//				+ timeStr.substring(14,17)
//				+ String.format("%03d", rad.nextInt(1000))
//				+ orderTime.substring(8,14);
//
//		return serialNum;
//	}
	
	
	
	/**
	 * 生成代付流水号 (21位)
	 * @return
	 */
//	public static String createDFSerialNum() {
//		String timeStr = CommonUtils.getCurrentTimeMills();
//		String serialNum = String.format("%03d", rad.nextInt(1000))
//				+ timeStr.substring(2,8)
//				+ timeStr.substring(14,17)
//				+ String.format("%03d", rad.nextInt(1000))
//				+ timeStr.substring(8,14);
//		return serialNum;
//	}
	
	/**
	 * 生成米贝代付流水号 (20位)
	 * @return
	 */
//	public static String createMBDFSerialNum() {
//		String timeStr = CommonUtils.getCurrentTimeMills();
//		String serialNum = String.format("%03d", rad.nextInt(1000))
//				+ timeStr.substring(2,8)
//				+ timeStr.substring(14,17)
//				+ String.format("%02d", rad.nextInt(100))
//				+ timeStr.substring(8,14);
//		return serialNum;
//	}
	
//	public static String createShenzhenWxSerialNum() {
//		String timeStr = CommonUtils.getCurrentTimeMills();
//		String serialNum = "CP"+timeStr+RandomUtil.generate2(4);
//		return serialNum;
//	}
	
//	public static String createYumingSerialNum() {
//		String timeStr = CommonUtils.getCurrentTimeMills();
//		String serialNum = "YM"+timeStr+RandomUtil.generate2(4);
//		return serialNum;
//	}
//
//	public static String createBoyiSerialNum() {
//		String timeStr = CommonUtils.getCurrentTimeMills();
//		String serialNum = "BY"+timeStr+RandomUtil.generate2(4);
//		return serialNum;
//	}
	
//	public static String createSandSerialNum() {
//		String timeStr = CommonUtils.getCurrentTimeMills();
//		String serialNum = "SD"+timeStr+RandomUtil.generate2(4);
//		return serialNum;
//	}
//
	
//
//	public static String createESerialNum() {
//		String timeStr = CommonUtils.getCurrentTimeMills();
//		String serialNum = "E"+timeStr+RandomUtil.generate2(4);
//		return serialNum;
//	}
	
	
	/**
	 * 生成汇付宝代付流水号 (18位)
	 * @return
	 */
//	public static String createHFBDFSerialNum() {
//		String timeStr = CommonUtils.getCurrentTimeMills();
//		String serialNum = String.format("%01d", rad.nextInt(10))
//				+ timeStr.substring(2,8)
//				+ timeStr.substring(14,17)
//				+ String.format("%02d", rad.nextInt(100))
//				+ timeStr.substring(8,14);
//		return serialNum;
//	}
	
    /**
     * 验证是否是合法的URL
     * @param str
     * @return
     */
    public static boolean isURL(String str){
    	if(str == null || "".equals(str)){
    		return false;
    	}
        //转换为小写
        str = str.toLowerCase();
        String regex = "^((https|http)?://)"  
                + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" //ftp的user@  
               + "(([0-9]{1,3}\\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184  
                 + "|" // 允许IP和DOMAIN（域名） 
                 + "([0-9a-z_!~*'()-]+\\.)*" // 域名- www.  
                 + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\\." // 二级域名  
                + "[a-z]{2,6})" // first level domain- .com or .museum  
                + "(:[0-9]{1,5})?" // 端口- :80  
                + "((/?)|" // a slash isn't required if there is no file name  
                + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";  
        return match( regex ,str );
    }
    
    /**
    * 验证数字输入
    * 
//    * @param 待验证的字符串
    * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
    */
    public static boolean IsNumber(String str) {
	    String regex = "^[0-9]*$";
	    return match(regex, str);
    }
    
    /**
    * 验证非零的正整数
    * 
//    * @param 待验证的字符串
    * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
    */
    public static boolean IsIntNumber(String str) {
	    String regex = "^\\+?[1-9][0-9]*$";
	    return match(regex, str);
    }
    
    /**
    * 验证是否是合法的时间
    * 
//    * @param 待验证的字符串
    * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
    */
    public static boolean IsDateTime(String str) {
    	try {
			new SimpleDateFormat("yyyyMMddHHmmss").parse(str);
			return true;
		} catch (Exception e) {
			return false;
		}
    }
	
	/**
	* @param regex
	* 正则表达式字符串
	* @param str
	* 要匹配的字符串
	* @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
	*/
	private static boolean match(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
	
	/**
	 * 获取一定长度的随机数
	 * @param length
	 * @return
	 */
	public static String getRandomCode( int length) {
		try {
			int  maxVal = (int)Math.pow(10, length);
			return String.format("%0" + length + "d", rad.nextInt(maxVal));
		} catch (Exception e) {
			return "00000000";
		}
	}
	
	/**
	 * 将普通字符串转换成十六进制字符串
	 */
//	public static String STR2HEX(String str) {
//		try {
//			char[] chars = "0123456789ABCDEF".toCharArray();
//			StringBuilder sb = new StringBuilder("");
//			byte[] bs = str.getBytes(PayConsts.DEFAULT_CHARSET);
//			int bit;
//			for (int i = 0; i < bs.length; i++) {
//				bit = (bs[i] & 0x0f0) >> 4;
//				sb.append(chars[bit]);
//				bit = bs[i] & 0x0f;
//				sb.append(chars[bit]);
//			}
//			return sb.toString().trim();
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "";
//		}
//	}

	/**
	 * 将十六进制字符串转换成普通字符串
	 */
	public static String HEX2STR(String hexStr) {
		String str = "0123456789ABCDEF";
		char[] hexs = hexStr.toCharArray();
		byte[] bytes = new byte[hexStr.length() / 2];
		int n;
		for (int i = 0; i < bytes.length; i++) {
			n = str.indexOf(hexs[2 * i]) * 16;
			n += str.indexOf(hexs[2 * i + 1]);
			bytes[i] = (byte) (n & 0xff);
		}
		return new String(bytes);
	}

	public static void main(String[] args) {
		String url = "http://localhost:13232/estd";
		System.out.println(isURL(url));
	}
}
