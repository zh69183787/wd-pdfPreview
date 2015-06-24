/*
 * Created on 2006-4-6
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.wonders.pdfPreview.util;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author lyy

 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class StringUtil {
	/**
	 * 手机号码有效性检查
	 * 简单规则：1开头 11位数字
	 * @param n
	 * @return
	 */
	public static boolean checkMobile(String n) {
		if (n == null || n.length() != 11) return false;
		if (!n.startsWith("1")) return false;
		
		try {
			Long.parseLong(n);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	/**
	 * �õ�html���ӵ�String,��������ǿգ�����toStringȥ2�߿ո�󳤶�Ϊ0������һ��ո�(&nbsp;)
	 * @param o:Ҫ��ʾ�Ķ���
	 * @return
	 */
	public static String getHtmlFormatString(Object o){
		String showValue = "&nbsp;";
		if(o!=null&&!o.toString().trim().equals("")){
			showValue = o.toString(); 
		}
		return showValue;
	}
	
	/**
	 * �õ�һ�����ķǿ���ʾ
	 * @param o
	 * @return
	 */
	public static String getNotNullValueString(Object o){
		String showValue = "";
		if(o!=null){
			showValue = o.toString();
			if(showValue.trim().equals("null")){
				showValue = "";
			}
		}
		return showValue;
	}
	public static String getNotNullTime(Object o){
		String showValue = "";
		if(o!=null){
			showValue = o.toString();
			if(showValue.trim().equals("----/--/--")){
				showValue = "";
			}
		}
		return showValue;
	}
	public static String getNotNullValueNumber(Object o){
		String showValue = "0";
		try {
			if(o!=null){
				showValue = o.toString().trim();
				if(StringUtil.isNull(showValue)){
					showValue = "0";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			showValue = "0";
		}
		return showValue;
	}
		
	public static String getNotNullValue(Object o,String value){
		String showValue = value;
		if(o!=null){
			showValue = o.toString();
			if(showValue.trim().equalsIgnoreCase("null")||showValue.trim().equals("")){
				showValue = value;
			}
		}
		return showValue;
	}
	
	/**
	 * ��ʽ���ɼ۸�ı�����ʽ
	 * @param value
	 * @param pattern
	 * @return
	 */
	public static String getMoneyFormatString(double value, String pattern) {
		if (value == 0.0) {
		    return "";
		} else {
		    try {
		        DecimalFormat format = new DecimalFormat(pattern);
		        return format.format(value);
		    } catch (Exception e) {
		        e.printStackTrace();
		        throw new RuntimeException(e.getMessage());
		    }
		}
	}
	
    /**
     * <p>���������Լ����˵����ַ�ת���ɶ�Ӧ��utf-8�����ַ��������GBK˫�ֽ��ַ�</p>
     * @param oriStirng ԭʼGBK�ַ�
     * @return ת�����utf-8�ַ�
     */
    public static String gbk2UTF8(String oriStirng) {
        if ( oriStirng == null )
            return null;
        else if(oriStirng.trim().length() == 0)
            return "";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < oriStirng.length(); i++) {
            char thisChar = oriStirng.charAt(i);
            sb.append(gbk2UTF8(thisChar));
        }
        return sb.toString();

    }
    /**
     * <p>���������Լ����˵����ַ��GBKת���ɶ�Ӧ��utf-8���룬�������˫�ֽ��ַ�</p>
     * @param oriChar ԭʼGBK�ַ�
     * @return ת�����utf-8�ַ�
     */
    private static String gbk2UTF8(char oriChar) {
        String desString = "";
        //���˫�ֽ��ַ�
        if (oriChar > 128) {
            desString = "\\u" + Integer.toHexString(oriChar);
        }else{
            desString = String.valueOf(oriChar);
        }
        return desString;
    }
	
	public static String getMoneyFormatString(float value, String pattern) {
		return getMoneyFormatString((double)value,pattern);
	}
	/**
	 * �ж��Ƿ�Ϊ��
	 * @param str
	 * @return
	 */
	public static boolean isNull(String str){
		if(str==null||str.trim().equals("")||str.trim().equalsIgnoreCase("null")){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * ������ݿ�ȡ���Timestamp�������ת����----��--��--�ո�ʽ
	 * @param temp
	 * @return
	 * Frank_cp
	 */
	public static String getDateFormatCN(Object temp){
		String time="----��--��--��";
		if(temp!=null){
			String str=temp.toString();
			String yearStr = str.substring(0,4);
			String monthStr = str.substring(5,7);
			String dayStr = str.substring(8,10);
			time=yearStr+"��"+monthStr+"��"+dayStr+"��";
		}
		return time;
	}
	/**
	 * ������ݿ�ȡ���Timestamp�������ת����----/--/--/��ʽ
	 * @param temp
	 * @return
	 * Frank_cp
	 */
	public static String getDateFormatNUM(Object temp){
		String time="----/--/--";
		if(temp!=null&&!temp.toString().trim().equals("")){
			String str=temp.toString();
			String yearStr = str.substring(0,4);
			String monthStr = str.substring(5,7);
			String dayStr = str.substring(8,10);
			time=yearStr+"/"+monthStr+"/"+dayStr;
		}
		return time;
	}
	/**
	 * ������ݿ�ȡ���Timestamp�������ת����2000-10-10��ʽ
	 * @param temp
	 * @return
	 * Frank_cp
	 */
	public static String getDateFormatEN(Object temp){
		String time="";
		if(temp!=null){
			String str=temp.toString();
			String yearStr = str.substring(0,4);
			String monthStr = str.substring(5,7);
			String dayStr = str.substring(8,10);
			time=yearStr+"-"+monthStr+"-"+dayStr;
		}
		return time;
	}
    public static Timestamp putDateIn(String str){
		StringBuffer str1 = new StringBuffer(str.substring(0,4));
		str1.append("-");
		str1.append(str.substring(5,7));
		str1.append("-");
		str1.append(str.substring(8,10));	
		return Timestamp.valueOf(""+str1+" "+"00"+":"+"00"+":"+"00"+"."+"0");
			 
    }
    
    /**
     * ȥ���ַ�ǰ������ַ�
     * @param str
     * @param c
     * @return
     */
	public static String pField3(String str,char c){
		String r = str;
		if(!StringUtil.isNull(str)){
			if(str.startsWith(String.valueOf(c))){
				int i=0;
				while(i<str.length()&&str.charAt(i)==c){
					i++;
				}
				r = str.substring(i,str.length());
			}
		}
		return r;
	}
	
	/**
	 * ȥ���ַ�ǰβ��������ַ�
	 * @param str
	 * @param c
	 * @return
	 */
	public static String pField3tail(String str,char c){
		String r = str;
		if(!StringUtil.isNull(str)){
			if(str.endsWith(String.valueOf(c))){
				int i=str.length()-1;
				while(i>0&&str.charAt(i)==c){
					i--;
				}
				r = str.substring(0,i+1);
				if(r.equals(String.valueOf(c))){
					r = "";
				}
			}
		}
		return r;
	}
	/**
	 * ������ת����001��ʽ���ַ�
	 * @param temp
	 * @return
	 * Frank_cp
	 */
	public static String convertNum2StringWithZero(int num, int length){
		String strNum=Integer.toString(num);
		StringBuffer strBuffer=new StringBuffer();
		
		int strLen = strNum.length();
		
		for (int iLen = 0; iLen <  (length - strLen); iLen++) {
			strBuffer.append("0");
		}
		strBuffer.append(strNum);
		
		return strBuffer.toString();
	}
	
	/**
	 * ��001��ʽ���ַ�ת��������
	 * @param temp
	 * @return
	 * Frank_cp
	 */
	public static String convertStringWithZero2Num(String strNum, int length){
		
		String strTemp = null;
		int strLen = strNum.length();
		int numLen = 0;
		
		for (int iLen = 0; iLen < strLen; iLen++) {
			if ( numLen == 0 ) {
				strTemp = strNum.substring(iLen,iLen+1);
				if (!strTemp.equals("0")) {
					numLen = iLen;
				}
			}
		}
		
		return strNum.substring(numLen,strLen);
	}
	
	/**
	 * ������ݿ�ȡ���Timestamp�������ת����20001010��ʽ
	 * @param temp
	 * @return
	 * Frank_cp
	 */
	public static String getDateFormatNoHenxian(Object temp){
		String time="";
		if(temp!=null){
			String str=temp.toString();
			String yearStr = str.substring(0,4);
			String monthStr = str.substring(5,7);
			String dayStr = str.substring(8,10);
			time=yearStr+monthStr+dayStr;
		}
		return time;
	}
	public static int getInt(String str){
		int r = 0;
		if(str!=null){
			try {
				if(str.indexOf(".")>=0){
					str = str.substring(0,str.indexOf("."));
				}
				r = new Integer(str).intValue();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return r;
	}
	
	public static float getFloat(String str){
		float r = 0;
		if(str!=null){
			try {
				r = new Float(str).floatValue();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return r;
	}
	
	public static String getChinese(String str) {
		try {
			String str1 = str;
			// byte[] str2=str1.getBytes("ISO-8859-1");
			byte[] str2 = str1.getBytes("ISO8859-1");
			String temp = new String(str2);
			;
			return temp;
		} catch (Exception e) {
			return null;
		}
	}
		
	public static boolean StringEquals(String s1,String s2){
		if(s1 == null && s2 == null){
			return true;
		}else if(s1 == null && s2 != null && !s2.equals("")){
			return false;
		}else if(s1 != null && s2 == null && !s1.equals("")){
			return false;
		}else if(s1 == null && s2.equals("")){
			return true;
		}else if(s1.equals("") && s2 == null){
			return true;
		}else if(s1.equals(s2)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * ���ص�ǰ����(yyyy/mm/dd)
	 * 
	 * @return
	 */
	public static String getCurrDate() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		return sdf.format(date);
	}
	
	/**
	 * �����ڸ�ʽΪ��yyyy/mm/ddת���ɸ�ʽ��yyyy-mm-dd
	 * 
	 * @param olddate
	 * @return
	 */
	public static String DateConversion(String olddate) {
		if (null == olddate || olddate.length() < 10) {
			return null;
		}
		String newstring = olddate.substring(0, 4);
		newstring += "-";
		newstring += olddate.substring(5, 7);
		newstring += "-";
		newstring += olddate.substring(8);
		return newstring;
	}
	/**
	 * �����ڸ�ʽΪ��yyyy/mm/ddת���ɸ�ʽ��yyyy-mm
	 * 
	 * @param olddate
	 * @return
	 */
	public static String DateConversion_yyyymm(String olddate) {
		if (null == olddate || olddate.length() < 10) {
			return null;
		}
		String newstring = olddate.substring(0, 4);
		newstring += "-";
		newstring += olddate.substring(5, 7);
		return newstring;
	}
	/**
	 * �����ڸ�ʽΪ��2000-01-31 00:00:00.0ת���ɸ�ʽ��yyyy/mm/dd
	 * 
	 * @param olddate
	 * @return
	 */
	public static String conversionDate(String d){
		if(d.length() < 10){
			return "";
		}
		StringBuffer nd = new StringBuffer();
		nd.append(d.substring(0, 4));
		nd.append("/");
		nd.append(d.subSequence(5, 7));
		nd.append("/");
		nd.append(d.substring(8,10));
		return nd.toString();
	}
	
	/**
	 * ���ص�ǰ����(yyyy/mm/dd)
	 * 
	 * @return
	 */
	public static String getCurrDate_yyyymmddhhmiss() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return sdf.format(date);
	}
	
	public static void main(String args[]){
		String d = StringUtil.fillWithBlank("你好", 10);
		System.out.println(d);
		System.out.println(d.length());
		System.out.println(d.getBytes().length);
	}
	
	
	public static String getNotNullValueStringWithBlank(Object o){
		String showValue = "&nbsp;";
		if(o!=null){
			showValue = o.toString();
			if(showValue.trim().equals("null")){
				showValue = "&nbsp;";
			}
		}
		return showValue;
	}
	
	public static String dealNull(Object str){
		if(str==null||"null".equals(str))
			return "";
		else
			return str.toString();
	}
	
	//content 原始字符串，length 目标长度
	public static String fillWithBlank(String content,int length){
		
		String result = "";
		
		for(int i=0;i<(length-content.getBytes().length);i++){
			result += " ";
		}
		result = content + result;
		
		return result;
	}
	

	
}

