package cn.com.twsm.newstopic.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class General {
	private static String[] dect = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"
								   ,"0","1","2","3","4","5","6","7","8","9"};

	private static final long ONE_MINUTE = 60000L;  
    private static final long ONE_HOUR = 3600000L;  
    private static final long ONE_DAY = 86400000L;  
    private static final long ONE_WEEK = 604800000L;  
  
    private static final String ONE_SECOND_AGO = "秒前";  
    private static final String ONE_MINUTE_AGO = "分钟前";  
    private static final String ONE_HOUR_AGO = "小时前";  
    private static final String ONE_DAY_AGO = "天前";  
    private static final String ONE_MONTH_AGO = "月前";  
    private static final String ONE_YEAR_AGO = "年前";  
  
   
    /**
     * 将时间转化为 一分钟前 半小时前格式
     * @param dateTime
     * @param formatStr
     * @return
     */
    public static String showTime(String dateTime, String formatStr) {
    	if(General.isEmpty(dateTime)){
    		return null;
    	}
    	if(General.isEmpty(formatStr)){
    		formatStr = "yyyy-MM-dd HH:mm:ss";
    	}
    	if(dateTime == null || formatStr == null)
    		return null;
    	SimpleDateFormat format = new SimpleDateFormat(formatStr); 
    	Date date = null;
    	try{
    		date = format.parse(dateTime);
    	}catch(Exception ex){
    		ex.printStackTrace();
    		return null;
    	}
        long delta = new Date().getTime() - date.getTime();  
        if (delta < 1L * ONE_MINUTE) {  
            long seconds = toSeconds(delta);  
            return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;  
        }  
        if (delta < 45L * ONE_MINUTE) {  
            long minutes = toMinutes(delta);  
            return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;  
        }  
        if (delta < 24L * ONE_HOUR) {  
            long hours = toHours(delta);  
            return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;  
        }  
        if (delta < 48L * ONE_DAY) {  
            long days = toDays(delta);  
            return (days <= 0 ? 1 : days) + ONE_DAY_AGO;  
        }  
        if (delta < 12L * 4L * ONE_WEEK) {  
            long months = toMonths(delta);  
            return (months <= 0 ? 1 : months) + ONE_MONTH_AGO;  
        } else {  
            long years = toYears(delta);  
            return (years <= 0 ? 1 : years) + ONE_YEAR_AGO;  
        }  
    }  
  
    private static long toSeconds(long date) {  
        return date / 1000L;  
    }  
  
    private static long toMinutes(long date) {  
        return toSeconds(date) / 60L;  
    }  
  
    private static long toHours(long date) {  
        return toMinutes(date) / 60L;  
    }  
  
    private static long toDays(long date) {  
        return toHours(date) / 24L;  
    }  
  
    private static long toMonths(long date) {  
        return toDays(date) / 30L;  
    }  
  
    private static long toYears(long date) {  
        return toMonths(date) / 365L;  
    }  
	
	
	/**
	 * 获取指定位数的随机字符串【A-Z 0-9】
	 * @param length
	 * @return
	 */
	public static String getRandomStr(int length){
		StringBuffer sb = new StringBuffer(length);
		Random random = new Random();
		for(int i=0;i<length;i++){
			sb.append(dect[Math.abs(random.nextInt())%dect.length]);
		}
		return sb.toString();
	}
	
	/**
	 * 在一个字符串中读取当前index所在位置的整行内容，并返回
	 * @param str
	 * @param index
	 * @return
	 */
	public static String readIndexLine(String str, int index){
		if(index == -1){
			return null;
		}
		
		if(isEmpty(str)){
			return null;
		}
		if(str.indexOf("\r\n") == -1){
			return str;
		}
		if(str.indexOf("\r\n") == str.lastIndexOf("\r\n")){
			return str;
		}
		
		String prefixStr = str.substring(0, index);
		String suffixStr = str.substring(index);
		
		int start = prefixStr.lastIndexOf("\r\n");
		if(start == -1){
			start = 0;
		}
		
		int end = suffixStr.indexOf("\r\n");
		if(end == -1){
			end = suffixStr.length();
		}

		return prefixStr.substring(start) + suffixStr.substring(0, end);
	}
	/**
	 * 获取一个字符串的首字母小写格式
	 * @param str
	 * @return
	 */
	public static String getFirstCharLower(String str){
		if(isEmpty(str)){
			return null;
		}
		
		String firstChar = String.valueOf(str.charAt(0));
		if(firstChar.toLowerCase().equals(firstChar)){
			return str;
		}
		
		return str.replaceFirst(firstChar, firstChar.toLowerCase());
	}
	
	/**
	 * 从str中获取标签tagName之间的内容
	 * @param str
	 * @param tagName
	 * @return
	 */
	public static String getContentBetweenTag(String str, String tagName){
		if(isEmpty(str) || isEmpty(tagName)){
			return null;
		}
		
		String startTag = "<" + tagName + ">";
		String endTag = "</" + tagName + ">";
		
		int start = str.indexOf(startTag) + startTag.length();
		int end = str.indexOf(endTag);
		
		if(start==-1 ||  end== -1 || start > end){
			return null;
		}
		
		return str.substring(start,end).trim();
	}
	
	/**
	 * word文档的XML格式编码
	 * @param str
	 * @return
	 */
	public static String wordEncode(String str){
		if (General.isNotEmpty(str)) {
			//word中的XML格式换行
			String wordCRLF = 
			"<w:p wsp:rsidR=\"00BC45B0\" wsp:rsidRDefault=\"00BC45B0\" wsp:rsidP=\"00BC45B0\">"
				+ "<w:pPr>"
					+ "<w:autoSpaceDE w:val=\"off\"/>"
					+ "<w:autoSpaceDN w:val=\"off\"/>"
					+ "<w:adjustRightInd w:val=\"off\"/>"
					+ "<w:spacing w:before-lines=\"0\" w:after-lines=\"0\" w:line=\"240\" w:line-rule=\"auto\"/>"
					+ "<w:jc w:val=\"left\"/>"
					+ "<w:rPr>"
						+ "<w:rFonts w:ascii=\"Courier New\" w:h-ansi=\"Courier New\" w:cs=\"Courier New\" w:hint=\"fareast\"/>"
						+ "<wx:font wx:val=\"Courier New\"/>"
						+ "<w:color w:val=\"7F7F9F\"/>"
						+ "<w:kern w:val=\"0\"/>"
						+ "<w:sz w:val=\"20\"/>"
						+ "<w:sz-cs w:val=\"20\"/>"
					+ "</w:rPr>"
				+ "</w:pPr>"
			+ "</w:p>";
			//移除最后一个换行
			if(str.lastIndexOf('\n') == str.length()-1){
				str = str.substring(0, str.length()-1);
			}
			str = replace(str, "<", "&lt;");
			str = replace(str, ">", "&gt;");
			str = replace(str, "\n", wordCRLF);
		}
		return str;
	}
	
	/**
	 * 获取一个字符串的首字母大写格式
	 * @param str
	 * @return
	 */
	public static String getFirstCharUpper(String str){
		if(isEmpty(str)){
			return null;
		}
		
		String firstChar = String.valueOf(str.charAt(0));
		if(firstChar.toUpperCase().equals(firstChar)){
			return str;
		}
		
		return str.replaceFirst(firstChar, firstChar.toUpperCase());
	}
		
	/**
	 * 判断字符串是否为空(包含null与"")
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		if(str == null || "".equals(str))
			return true;
		return false;
	}
	
	/**
	 * 判断字符串是否为非空(包含null与"")
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str){
		if(str == null || "".equals(str))
			return false;
		return true;
	}
	
	/**
	 * 字符串去空格方法
	 * @param str
	 * @return
	 */
	public static String trim(String str){
		if(str == null)
			return "";
		return str.trim();
	}
	
	/**
	 * 替换字符串
	 * @param src 源字符串
	 * @param oldstr 要替换的字符串
	 * @param newstr 新字符串
	 * @return
	 */
	public static String replace(String src, String oldstr, String newstr) {
		StringBuffer dest = new StringBuffer();
		int beginIndex = 0;
		int endIndex = 0;
		while (true) {
			endIndex = src.indexOf(oldstr, beginIndex);
			if (endIndex >= 0) {
				dest.append(src.substring(beginIndex, endIndex));
				dest.append(newstr);
				beginIndex = endIndex + oldstr.length();
			} else {
				dest.append(src.substring(beginIndex));
				break;
			}
		}
		return dest.toString();
	}
	
	/**
	 * 防止SQL注入
	 * @param str
	 * @return
	 */
	public static String sqlStr(String str) {
		if (str != null) {
			str = replace(str, "'", "");
			//str = replace(str, " ", "");
			str = replace(str, "\\", "&#92;");
		}
		return str;
	}
	
	/**
     * 日期类型转换成yyyy-MM-ddTHH:mm:ss.SSSzzzzz+08.00格式字符串
     * @param Date date
     * @return String
     */
    public static String xmlDateTime2xmlDateTimeStr(Date date) {
    	if(date == null)
    		return null;
    	
    	Calendar ca = Calendar.getInstance();
    	ca.setTime(date);
    	XMLGregorianCalendar calendar;
    	try {
            calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar((GregorianCalendar)ca);
        } catch(Exception ex){
        	return null;
        }
        return calendar.toString();
    }
	
	/**
     * 获取当前时间戳
     * @return 字符串形式的时间戳
     */
    public static String getTimestamp(){
        return xmlDateTime2xmlDateTimeStr(new Date());
    }
    
    /**
     * 时间字符串转换为long
     * @param timeStr
     * @param format
     * @return
     */
    public static long timeStr2Long(String timeStr, String format){
    	if(format == null)
    		format = "yyyy-MM-dd HH:mm:ss";
    	
    	SimpleDateFormat formatter = new SimpleDateFormat(format);
    	try{
    		return formatter.parse(timeStr).getTime();
    	} catch(Exception ex){
    		ex.printStackTrace();
    		return -1;
    	}
    }
    /**
     * long时间转换为字符串
     * @param timeLong
     * @param format
     * @return
     */
    public static String timeLong2Str(Long timeLong, String format){
    	if(timeLong == null)
    		return null;
    	
    	if(format == null)
    		format = "yyyy-MM-dd HH:mm:ss";
    	SimpleDateFormat formatter = new SimpleDateFormat(format);
    	Date date = new Date(timeLong);
    	return formatter.format(date);
    }
    
    /**
     * 将空字符串转换为""
     * @param str
     * @return
     */
    public static String convertNullToEmpty(String str){
    	if(General.isEmpty(str))
    		return "";
    	return str;
    }
    
	/**
	 * 判断是否为数字
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		try{
			Integer.parseInt(str);
			return true;
		}catch(Exception ex){
			return false;
		}
	}
	
	/**
	 * URL编码方法
	 * @param url
	 * @param encode 字符集 默认为UTF-8
	 * @return
	 */
	public static String encodeURL(String url, String encode) {
		if(General.isEmpty(encode)) encode = "utf8";
		try {
			return java.net.URLEncoder.encode(url, encode);
		} catch (UnsupportedEncodingException e) {
			return url;
		}
	}
	
	/**
	 * URL解码方法
	 * @param url
	 * @param decode 字符集 默认为UTF-8
	 * @return
	 */
	public static String decodeURL(String url, String decode) {
		String u = url;
		if(General.isEmpty(decode)) decode = "utf8";
		try {
			u = java.net.URLDecoder.decode(url, decode);
		} catch (Exception e) {
			return url;
		}
		return u;
	}
	
	/**
	 * 用指定分隔符分隔字符串
	 * @param str
	 * @param separator
	 * @return
	 */
	public static List<String> segmentationStr(String str,String separator){
		List<String> result = new ArrayList<String>();
		if(General.isEmpty(str))
			return result;
		StringTokenizer token = new StringTokenizer(str, separator);
		while (token.hasMoreElements()) {
			result.add(token.nextToken());
		}
		return result;
	}
	
	/**
	 * 判断字符串是否为合法手机号 11位 13 14 15 18开头
	 * @param str
	 * @return boolean
	 */
	public static boolean isMobile(String str){
		if(General.isEmpty(str))
			return false;
		return str.matches("^(13|14|15|18)\\d{9}$");
	}
	
	/**
	 * 判断是否为正确的邮件格式
	 * @param str
	 * @return boolean
	 */
	public static boolean isEmail(String str){
		if(General.isEmpty(str))
			return false;
		return str.matches("^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$");
	}
	
	/**
	 * HTML字符串编码
	 * @param str
	 * @return
	 */
	public static String HTMLEncode(String str) {
		if (str != null) {
			//需要放在前面替换，以防止替换掉其他已经替换过一次的，如 "\\", "&#92;"  lixinglei modify  at 2012-06-15
			str = replace(str, "&", "&amp;");
			str = replace(str, "'", "&#39;");
			str = replace(str, "\"", "&quot;");
			str = replace(str, "<", "&lt;");
			str = replace(str, ">", "&gt;");
			str = replace(str, "<<", "&raquo;");
			str = replace(str, ">>", "&laquo;");
			str = replace(str, "'", "");	//??
			str = replace(str, "\"", "");	//??
			str = replace(str, "\\r\\n", "\n"); //lixinglei modify  at 2012-06-15
			str = replace(str, "\\n", "<br>"); //lixinglei modify  at 2012-06-15
			str = replace(str, "\r\n", "\n");
			str = replace(str, "\n", "<br>");
			//需要放在\n之后替换，以防止替换掉\n的\ lixinglei modify  at 2012-06-15
			str = replace(str, "\\", "&#92;");
			str = replace(str, "  ", "　");
			str = replace(str, "&amp;amp;", "&amp;");	//??
			str = replace(str, "&amp;quot;", "&quot;");	//??
			str = replace(str, "&amp;lt;", "&lt;");		//??
			str = replace(str, "&amp;gt;", "&gt;");		//??
			str = replace(str, "&amp;nbsp;", "&nbsp;");	//??
		}
		return str;
	}
	
	/**
	 * 替换特殊字符
	 * @param str
	 * @return
	 */
	public static String replaceSpec(String str) {
		if (str != null) {
			str = replace(str, "&", "");
			str = replace(str, "'", "");
			str = replace(str, "\"", "");
			str = replace(str, "<", "");
			str = replace(str, ">", "");
			str = replace(str, "<<", "");
			str = replace(str, ">>", "");
			str = replace(str, "'", "");	
			str = replace(str, "\"", "");	
			str = replace(str, "\\r\\n", ""); 
			str = replace(str, "\\n", "");
			str = replace(str, "\r\n", "");
			str = replace(str, "\n", "");
			str = replace(str, "\\", "");
			str = replace(str, "  ", "");
			str = replace(str, "&amp;amp;", "");	
			str = replace(str, "&amp;quot;", "");	
			str = replace(str, "&amp;lt;", "");		
			str = replace(str, "&amp;gt;", "");		
			str = replace(str, "&amp;nbsp;", "");	
		}
		return str;
	}
		
	
	/**
	 * 去掉回车换行
	 * @param str
	 * @return
	 */
	public static String replaceEnterLine(String str) {
		if (str != null) {
			//需要放在前面替换，以防止替换掉其他已经替换过一次的，如 "\\", "&#92;"  lixinglei modify  at 2012-06-15
			//str = replace(str, "&", "&amp;");
			str = replace(str, "'", "&#39;");
			//str = replace(str, "\"", "&quot;");
			//str = replace(str, "<", "&lt;");
			//str = replace(str, ">", "&gt;");
			//str = replace(str, "<<", "&raquo;");
			//str = replace(str, ">>", "&laquo;");
			str = replace(str, "'", "");	//??
			//str = replace(str, "\"", "");	//??
			str = replace(str, "\\r\\n", "<br>"); //lixinglei modify  at 2012-06-15
			str = replace(str, "\\n", "<br>"); //lixinglei modify  at 2012-06-15
			str = replace(str, "\r\n", "<br>");
			str = replace(str, "\n", "<br>");
			//需要放在\n之后替换，以防止替换掉\n的\ lixinglei modify  at 2012-06-15
			//str = replace(str, "\\", "&#92;");
			//str = replace(str, "  ", "　");
			//str = replace(str, "&amp;amp;", "&amp;");	//??
			//str = replace(str, "&amp;quot;", "&quot;");	//??
			//str = replace(str, "&amp;lt;", "&lt;");		//??
			//str = replace(str, "&amp;gt;", "&gt;");		//??
			//str = replace(str, "&amp;nbsp;", "&nbsp;");	//??
		}
		return str;
	}
	
	/**
	 * HTML字符串解码HTMLDecode
	 * @param str
	 * @return
	 */
	public static String HTMLDecode(String str) {
		if (str != null) {
			//str = replace(str, "&amp;", "&amp;amp;");
			str = replace(str, "&nbsp;", " ");	
			str = replace(str, "&gt;", "&amp;gt;");		
			str = replace(str, "&lt;", "&amp;lt;");		
			str = replace(str, "&quot;", "&amp;quot;");	 
			str = replace(str, "&amp;", "&");
			str = replace(str, " ", "\u0020");
			str = replace(str, "　", "\u0020\u0020");
			str = replace(str, "&#92;", "\\");
			str = replace(str, "<br>", "\n");
			str = replace(str, "\n", "\r\n");
			str = replace(str, "&laquo;", ">>");
			str = replace(str, "&raquo;", "<<");
			str = replace(str, "&gt;", ">");
			str = replace(str, "&lt;", "<");
			str = replace(str, "&quot;", "\"");
			str = replace(str, "&#39;", "'");
			
		}
		return str;
	} 
	
	/**
	 * 获得指定格式的时间
	 * @param date
	 * @param formatStr
	 * @return
	 */
	public static String getDateByFormat(Date date, String formatStr){
        if (date == null){
            return null;
        }
        
        SimpleDateFormat sf = new SimpleDateFormat(formatStr);
        return sf.format(date);
    }
	
	/**
	 * 判断是否为浮点数或者整数
	 * @param str
	 * @return true Or false
	 * add by Shi weiyan
	 */
	public static boolean isNumeric(String str){
          Pattern pattern = Pattern.compile("^(-?\\d+)(\\.\\d+)?$");
          Matcher isNum = pattern.matcher(str);
          if( !isNum.matches() ){
                return false;
          }
          return true;
    }
	
	/**
	 * URL参数编码
	 * @param url
	 * @return
	 */
	public static String encodeURLParam(String url,String charset){
		if(url==null) return null;
		int rex = url.indexOf("=");
		if(rex == -1) return url;
		
		StringBuffer sb = new StringBuffer();
		List<String> list = General.segmentationStr(url, "=");
		for(int i=0;i<list.size();i++){
			String temp = list.get(i);
			if(i==0) sb.append(temp).append("=");
			else{
				if(temp.indexOf("&") ==0){
					sb.append(temp).append("=");
				}else if(temp.indexOf("&") == -1)
					sb.append(General.encodeURL(temp, charset));
				else{
					sb.append(General.encodeURL(temp.substring(0, temp.indexOf("&")), charset)).append(temp.substring(temp.indexOf("&")));
					sb.append("=");
				}
			}
		}
		return sb.toString();
	}
	
	/**
	 * 依据格式获取当前时间
	 * @param format
	 * @return
	 */
	public static String getNow(String format){
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Date date = new Date();
		return formatter.format(date);
	}
	
	public static String getNow(){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		return formatter.format(date);
	}
	public static String getDay(){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return formatter.format(date);
	}
	public static String getDay(int i){
		i=i-1;
		Calendar cal = Calendar.getInstance();
		Date now = new Date();
		cal.setTime(now);
		cal.add(java.util.Calendar.DAY_OF_MONTH,-i);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH)+1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		String mStr = "";
		String dStr = "";
		if(month<10){
			mStr = "0"+month;
		}else{
			mStr  = ""+month;
		}
		if(day<10){
			dStr = "0"+day;
		}else{
			dStr = ""+day;
		}
		return year+"-"+mStr+"-"+dStr+" 00:00:00";
	}
	public static void main(String args[]){
		System.out.println(getDay(30));
	}
	/**      
	 * getNow(判断时间是否为空，空则返回当前时间) 
	 * @author wangli
	 * @param   name          
	 * @return String       
	 * @Exception 异常对象            
	*/
	public static String getStTime(String str){
		if(isNotEmpty(str)) return str;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		return formatter.format(date);
	}
	
	
	/**
	 * 以|线开始，且以|线结束
	 */
	public static String startAndEndWithVerticalBar(String string){
		if(General.isEmpty(string)){
			return null;
		}
		if(General.isEmpty(string.replaceAll("\\|", "").trim())){
			return null;
		}
		if(!string.startsWith("|")){
			string = "|"+string;
		}
		if(!string.endsWith("|")){
			string = string + "|";
		}
		return string;
	}
	/**
	 * 去掉数组中值想等的字符串                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 
	 * @param tagList
	 */
	public static void trimArrayList(List<String> tagList) {
		Set<String> set = new HashSet<String>();
		Iterator<String> it = tagList.iterator();
		String str = null;
		while(it.hasNext()){
			 str = it.next(); 
             if(set.contains(str))   it.remove(); 
             else   set.add(str);
		}
	}
	public static String checkFileType(String FileName){
		if(FileName.endsWith(".mp3")){
			return "audio";
		}
		if(FileName.endsWith(".epub")){
			return "ebook";
		}
		if(FileName.endsWith(".wma")){
			return "vedio";
		}
		if(FileName.endsWith(".doc")){
			return "file";
		}
		return "error";
		
	}
	public static String getCurrentMonth(){
		int month=0;
		Calendar calendar = Calendar.getInstance();
		month=calendar.get(Calendar.MONTH);
		month +=1;
		if(month<10){
			return "0"+month;
		}else{
			return ""+month;
		}
	}
	
	/**
	 * 获取用户IP
	 * @param request
	 * @return
	 */
	public static String getIp(HttpServletRequest request){
		String ip="";
    	ip = request.getHeader("x-forwarded-for");
    	if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
    		ip = request.getHeader("Proxy-Client-IP");
    	}
    	if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
    		ip = request.getHeader("WL-Proxy-Client-IP");
    	}
    	if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
    		ip = request.getRemoteAddr();
    	}
    	List<String> list = General.segmentationStr(ip, ",");
    	
    	if(list.size()>1){
    		return list.get(0);
    	}
    	return ip;
	}
	
	/**
	 * 判断两个字符串相似程度
	 * @return float 0-1之间 越接近1说明两个字符串之间相似程度越高
	 * @createTime 2012-1-12
	 */
	public static float levenshtein(String str1,String str2) {
		//计算两个字符串的长度。
		int len1 = str1.length();
		int len2 = str2.length();
		//建立上面说的数组，比字符长度大一个空间
		int[][] dif = new int[len1 + 1][len2 + 1];
		//赋初值，步骤B。
		for (int a = 0; a <= len1; a++) {
			dif[a][0] = a;
		}
		for (int a = 0; a <= len2; a++) {
			dif[0][a] = a;
		}
		//计算两个字符是否一样，计算左上的值
		int temp;
		for (int i = 1; i <= len1; i++) {
			for (int j = 1; j <= len2; j++) {
				if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
					temp = 0;
				} else {
					temp = 1;
				}
				//取三个值中最小的
				dif[i][j] = min(dif[i - 1][j - 1] + temp, dif[i][j - 1] + 1,
						dif[i - 1][j] + 1);
			}
		}
		//System.out.println("字符串\""+str1+"\"与\""+str2+"\"的比较");
		//取数组右下角的值，同样不同位置代表不同字符串的比较
		//System.out.println("差异步骤："+dif[len1][len2]);
		//计算相似度
		float similarity =1 - (float) dif[len1][len2] / Math.max(str1.length(), str2.length());
		//System.out.println("相似度："+similarity);
		return similarity;
	}
	
	public static String arrays2String(String[] arrays){
		if(arrays == null || arrays.length < 1){
			return null;
		}
		StringBuilder sb = new StringBuilder(arrays.length);
		for (String arr : arrays) {
			sb.append(arr + ",");
		}
		String ret = sb.toString();
		if(isNotEmpty(ret)){
			return ret.substring(0, ret.length() - 1);
		}
		return null;
	}
	
	//得到最小值
	private static int min(int... is) {
		int min = Integer.MAX_VALUE;
		for (int i : is) {
			if (min > i) {
				min = i;
			}
		}
		return min;
	}
	/**      
	 * stToList_Integer(将用英文逗号分割的数字字符串转换为List<Integer>) 
	 * @author wangli
	 * @param   name          
	 * @return String       
	 * @Exception 异常对象            
	*/
	public static List<Integer> stToList_Integer(String idsString) {
		String []st = idsString.split(",");
		List<Integer> list = new ArrayList<Integer>();
		try {
			for(String s:st){
				list.add(Integer.parseInt(s.replaceAll(" ", "")));
			}
		} catch (Exception e) {
			return null;
		}
		return list;
	}
	public static List<Double> stToList_Double(String idsString) {
		String []st = idsString.split(",");
		List<Double> list = new ArrayList<Double>();
		try {
			for(String s:st){
				list.add(Double.parseDouble(s.replaceAll(" ", "")));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}
	public static List<String> stToList_String(String str) {
		String []st = str.split(",");
		List<String> list = new ArrayList<String>();
		try {
			for(String s:st){
				list.add(s.replaceAll(" ", ""));
			}
		} catch (Exception e) {
			return null;
		}
		return list;
	}
	
	public static int getList_Integer_MIN(List<Integer> list) {
		Integer a=0;
		for(int i=0;i<list.size();i++){
			if(i == 0)a=list.get(i);
			else if(a > list.get(i)){
				a = list.get(i);
			}
		}
		return a;
	}
	/**
	 * 转换List<String> 为  List<Integer>
	 * @return List<Integer>
	 * @throws Exception
	 */
	public static List<Integer> convert(List<String> list){
		
		if(list!=null && list.size() > 0){
			List<Integer> integerList = new ArrayList<Integer>();
			for(String str:list){
				integerList.add(Integer.parseInt(str));
			}
			return integerList;
		}
		return null;
	}
	/**
	 * 取得当前时间的前两天
	 * @param date
	 * @return
	 */
	public static String getNextDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -2);
		date = calendar.getTime();
		return new SimpleDateFormat("yyyy-MM-dd ").format(date);
	}
	
	/**
	 * 取得当前时间的前一周时间
	 * @param date
	 * @return
	 */
	public static String getOneWeekDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -6);
		date = calendar.getTime();
		return new SimpleDateFormat("yyyy-MM-dd ").format(date);
	}
	
	

	// 复制文件
	public static void copyFile(File sourceFile, File targetFile) throws IOException {
	    BufferedInputStream inBuff = null;
	    BufferedOutputStream outBuff = null;
	    try {
	        // 新建文件输入流并对它进行缓冲
	        inBuff = new BufferedInputStream(new FileInputStream(sourceFile));
	
	        // 新建文件输出流并对它进行缓冲
	        outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));
	
	        // 缓冲数组
	        byte[] b = new byte[1024 * 5];
	        int len;
	        while ((len = inBuff.read(b)) != -1) {
	            outBuff.write(b, 0, len);
	        }
	        // 刷新此缓冲的输出流
	        outBuff.flush();
	    } finally {
	        // 关闭流
	        if (inBuff != null)
	            inBuff.close();
	        if (outBuff != null)
	            outBuff.close();
	    }
	}
}
