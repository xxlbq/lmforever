package com.lm.common.util.encoding;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Random;

import sun.io.ByteToCharConverter;
import sun.io.CharToByteConverter;
import sun.io.MalformedInputException;




public class Main {
	
	private static byte[] byteStr;
	
    /**
	 * 转换编码 将ISO-8859-1 传换为GBK
	 */
	public static String toGBK(String str) {
		try {
			if (str == null) {
				str = "";
			} else {
				byte[] gbt = str.getBytes("ISO-8859-1");
				str = new String(gbt, "GBK");
				for(int i=0;i<gbt.length;i++){
					System.out.println("--------" + gbt[i]);
				}
			}
		} catch (Exception e) {
			System.out.println("DealString::toGBK(String)运行时出错：错误为：" + e);
		}
		return str;
	}
	
    /**
     * 字符串转换为ASCII
     */
    public static String toASCII(String str)
    {
        try 
        {
            if(str==null)
                str = "";
            else 
                str=new String(str.getBytes("GBK"),"ISO-8859-1"); 
        }catch (Exception e) {
            System.out.println("DealString::toGBK(String)运行时出错：错误为："+e);
        }
        return str;
    }
    
    /**
     * 字符串转化为字符串
     * */
    @SuppressWarnings("deprecation")
	public static String stringToAscii(String s) 
    {
        try {
            CharToByteConverter toByte = CharToByteConverter.getConverter("UTF-8");
            byte[] orig = toByte.convertAll(s.toCharArray());
           
            char[] dest = new char[orig.length];
            for (int i=0;i<orig.length;i++)
            {
                dest[i] = (char)(orig[i] & 0xFF);
                System.out.println(Integer.toHexString(dest[i]));
                
            }            
            return new String(dest);
        }catch (Exception e) {
            System.out.println("DealString::ChineseStringToAscii(String)运行时出错："+e);
            return s;
        }
    }
    
    /**
     * 字符串转化为字符串
     * */
    @SuppressWarnings("deprecation")
	public static String byteToAscii(byte[] s,String encodeName) 
    {
        try { 
            char[] dest = new char[s.length];
            for (int i=0;i<s.length;i++)
            {
                dest[i] = (char)(s[i] & 0xFF);
                
            }
//            CharToByteConverter toByte = CharToByteConverter.getConverter(encodeName);
//            toByte.convertAll(dest);
            return new String(dest);
        }catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }
    
    /**
     * ASCII转化为字符串
     * */
    @SuppressWarnings("deprecation")
	public static String asciiToString(String s,String encodeName) 
    {
        char[] orig = s.toCharArray();
        byte[] dest = new byte[orig.length];
        for (int i=0;i<orig.length;i++)
        {
            dest[i] = (byte)(orig[i]&0xFF);
        }
        try {
            ByteToCharConverter toChar = ByteToCharConverter.getConverter(encodeName);
            return new String(toChar.convertAll(dest));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		} catch (MalformedInputException e) {
			e.printStackTrace();
			return "";
		}
    }
   
    
    /**
     * 
     * @param bt
     * @return
     */
    public static byte[] unicodeToUTF8(byte[] bt){
    	if(bt.length!=2){
    		//exception;
    	}
    	int lowH = bt[0]&0x0f;
    	int hightH = (bt[0]<<4)&0x0f;
    	int lowL = bt[1]&0x0f;
    	int lowLL = lowL&0x03;
    	int lowLH = (lowL<<2)&0x03;
    	int hightL = bt[1]&0x0f;
    	byte[] b = new byte[3];
    	b[0] = (byte)(hightH|0xe0);
    	b[1] = (byte)(0x80|lowH>>2|lowLH);
    	b[2] = (byte)(0x80|lowLL>>4|hightL);
    	return b;
    }
    
    /**
     * 
     * @param bt
     * @param encodeName
     * @return
     */
    @SuppressWarnings("deprecation")
	public static char[] byteToChar(byte[] bt,String encodeName){
    	char[] cr = new char[2];
    	try {
			ByteToCharConverter convertor = ByteToCharConverter.getConverter(encodeName);
			cr = convertor.convertAll(bt);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MalformedInputException e) {
			e.printStackTrace();
		}
    	return cr;
    }
    
    /**
     * 你"的gb码是：0xC4E3 ，unicode是0x4F60
     * 1 1 1 0 _ _ _ _ 1 0 _ _ _ _ _ _ 1 0 _ _ _ _ _ _
     * 1110 0100 10 1111 01 10 10 0000
     * 
     * e4 bd a0
     *      0000    1000 00    00 0000
     * 1110 0000 10 1000 00 10 00 0000
     * e0 a0 80
     * 1110 1000 10 1000 11 10 10 1010
     * 1000 1000 1110 1010 88eb
     * e8b3aa
     * @throws MalformedInputException
     */
    @SuppressWarnings("deprecation")
	public static String aa() {
    	Random rand = new Random();
		String encoding = "UTF-8";
		String reStr = "";
//		byte b[] = { (byte) '\u00c4', (byte) '\u00E3' };
		//e8b3aa e5958f e382bf e382a4 e38388 e383ab
		byte b[] = { (byte) '\u00e8', (byte) '\u00b3', (byte) '\u00ab' };
		ByteToCharConverter convertor;
		char[] c = new char[b.length];
		try {
			convertor = ByteToCharConverter.getConverter(encoding);
//			c = convertor.convertAll((byte)aa[rand.nextInt(aa.length)]);
			c = convertor.convertAll(b);
			reStr = new String(convertor.convertAll(b));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MalformedInputException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < c.length; i++) {
			System.out.println(Integer.toHexString(c[i]));
		}
		return reStr;
	}
	/**
	 * 以下是Unicode和UTF-8之间的转换关系表：
	 * U-00000000 - U-0000007F: 0xxxxxxx
	 * U-00000080 - U-000007FF: 110xxxxx 10xxxxxx
	 * 				1100-0000(c0)  1101-1111(df) 1000-0000(80) 1010-1111(bf)
	 * U-00000800 - U-0000FFFF: 1110xxxx 10xxxxxx 10xxxxxx
	 * 				e0-ef 80-bf 80-bf
	 * U-00010000 - U-001FFFFF: 11110xxx 10xxxxxx 10xxxxxx 10xxxxxx
	 * 				f0-f7 80-bf 80-bf 80-bf
	 * U-00200000 - U-03FFFFFF: 111110xx 10xxxxxx 10xxxxxx 10xxxxxx 10xxxxxx
	 * U-04000000 - U-7FFFFFFF: 1111110x 10xxxxxx 10xxxxxx 10xxxxxx 10xxxxxx 10xxxxxx
	 * f9 88 a7 be
	 * 
	 * e4 b8 ad e6 96 87
	 * 1110-0010 1011-1000 1010-1011 1110-0110 1001-0110 1000-0111
	 * 中文的范围：\u4e00 - \u9fa5，日文在\u0800 - \u4e00，韩文为\u9fa5以上。
	 */
    /**
     * utf8 Converter to byte array
     */
    @SuppressWarnings("deprecation")
	public static String randomStringUTF8(String encodeName){
    	Random rand = new Random();
    	
    	int c = rand.nextInt(4);
    	c = 2;
    	byte[] bStr = new byte[c+1];
    	switch(c){
    	case 0:
    		//[\x01-\x7f]
    		bStr[0] = stringToByte("01","7f");
    		break;
    	case 1:
    		//[\xc0-\xdf][\x80-\xbf]
    		//1100-0000  1101-1111 1000-0000 1010-1111 
    		//[\xc0-\xdf] //[\x80-\xbf]		
    		bStr[0] = stringToByte("c0","df");    		
    		bStr[1] = stringToByte("80","bf");
    		break;
    	case 2:
    		//[\xe0-\xef][\x80-\xbf]{2}
    		bStr[0] = stringToByte("e0","ef");
    		bStr[1] = stringToByte("80","bf");
    		bStr[2] = stringToByte("80","bf");
    		break;
    	case 3:
    		//[\xf0-\xf7][\x80-\xbf]{3}
    		bStr[0] = stringToByte("f0","f7");
    		bStr[1] = stringToByte("80","bf");
    		bStr[2] = stringToByte("80","bf");
    		bStr[3] = stringToByte("80","bf");
    		break;
    	}

		try {
			ByteToCharConverter toChar = ByteToCharConverter.getConverter(encodeName);
			char[] bToC = toChar.convertAll(bStr);
			return new String(bToC);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		} catch (MalformedInputException e) {
			e.printStackTrace();
			return "";
		}
        
    }
    
    
    
    /**
     * 
     * @param start
     * @param end
     * @return
     */
    public static byte stringToByte(String start ,String end){
    	Random rand = new Random();
    	int[] rang;
    	if(isBlank(start)&&isBlank(end)){
    		return 0;
    	}
    	if(isBlank(start)){
    		start = "0";
    	}
    	if(isBlank(end)){
    		end = "0";
    	}
    	if(fromHexStringToByte(start)>fromHexStringToByte(end)){
    		String m;
    		m = start;
    		start = end;
    		end = m;
    	}
		rang = getRang(fromHexStringToByte(start),fromHexStringToByte(end));
		
		return (byte)rang[rand.nextInt(rang.length)];
    	
    }
    
    /**
     * 
     * @param str
     * @return
     */
    public static boolean isBlank(String str){
    	if(str==null){
    		return true;
    	}else {
    		if((str.trim()).equals("")){
    			return true;
    		}else{
    			return false;
    		}
    	}
    }
    
    /**
	 * Convert a hex string to a byte array. Permits upper or lower case hex.
	 * 
	 * @param s
	 *            String must have even number of characters. and be formed only
	 *            of digits 0-9 A-F or a-f. No spaces, minus or plus signs.
	 * @return corresponding byte array.
	 */
	public static byte[] fromHexString(String s) {
		int stringLength = s.length();
		if ((stringLength & 0x1) != 0) {
			throw new IllegalArgumentException(
					"fromHexString   requires   an   even   number   of   hex   characters");
		}
		byte[] b = new byte[stringLength / 2];

		for (int i = 0, j = 0; i < stringLength; i += 2, j++) {
			int high = charToNibble(s.charAt(i));
			int low = charToNibble(s.charAt(i + 1));
			b[j] = (byte) ((high << 4) | low);
		}
		return b;
	}
	
	/**
	 * Convert a hex string to a byte . Permits upper or lower case hex.
	 * 
	 * @param s
	 *            String must have even number of characters. and be formed only
	 *            of digits 0-9 A-F or a-f. No spaces, minus or plus signs.
	 * @return corresponding byte.
	 */
	public static byte fromHexStringToByte(String s) {
		int stringLength = s.length();
		if ((stringLength & 0x1) != 0) {
			throw new IllegalArgumentException(
					"fromHexString   requires   an   even   number   of   hex   characters");
		}

		if (s.length() != 2) {
			throw new IllegalArgumentException("Invalid   hex   character:   "
					+ s);
		}
		byte b = 0;
		int high = charToNibble(s.charAt(0));
		int low = charToNibble(s.charAt(1));
		b = (byte) ((high << 4) | low);
		return b;
	}
	
	/**
	 * Convert a hex string to a byte . Permits upper or lower case hex.
	 * 
	 * @param s
	 *            String must have even number of characters. and be formed only
	 *            of digits 0-9 A-F or a-f. No spaces, minus or plus signs.
	 * @return corresponding byte.
	 */
	public static byte[] fromHexStringToByteArray(String s) {
		int stringLength = s.length();
		if ((stringLength & 0x1) != 0) {
			throw new IllegalArgumentException(
					"fromHexString   requires   an   even   number   of   hex   characters");
		}

		if (s.length()%2!=0) {
			throw new IllegalArgumentException("Invalid   hex   character:   "
					+ s);
		}
		byte[] b = new byte[s.length()/2];
		for(int i=0;i<b.length;i++){
			int high = charToNibble(s.charAt(i*2));
			int low = charToNibble(s.charAt(i*2+1));
			b[i] = (byte) ((high << 4) | low);
		}
		
		return b;
	}

	/**
	 * convert a single char to corresponding nibble.
	 * 
	 * @param c
	 *            char to convert. must be 0-9 a-f A-F, no spaces, plus or minus
	 *            signs.
	 * 
	 * @return corresponding integer
	 */
	private static int charToNibble(char c) {
		if ('0' <= c && c <= '9') {
			return c - '0';
		} else if ('a' <= c && c <= 'f') {
			return c - 'a' + 0xa;
		} else if ('A' <= c && c <= 'F') {
			return c - 'A' + 0xa;
		} else {
			throw new IllegalArgumentException("Invalid   hex   character:   "
					+ c);
		}
	}
    
    /**
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
    public static int[] getRang(byte start,byte end){
    	if(start==end){
    		int[] rang = new int[1];
    		rang[0] = start;
    		return rang;
    	}
    	int[] rang = new int[end-start];
    	
    	for(int i=0;i<end-start;i++){
    		rang[i]=start+i;
    	}
    	return rang;
    }

	/**
	 * @param args
	 */
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		String encodeName = "UTF-8";
// [code][\x01-\x7f]|[\xc0-\xdf][\x80-\xbf]|[\xe0-\xef][\x80-\xbf]{2}|[\xf0-\xff][\x80-\xbf]{3}[/code]
		
//[\xb0-\xcf][\xa0-\xd3]|[\xd0-\xf4][\xa0-\xfe]|[\xB0-\xF3]
//[\xA1-\xFE]|[\xF4][\xA1-\xA6]|[\xA4][\xA1-\xF3]|[\xA5][\xA1-\xF6]|[\xA1][\xBC-\xBE]
		// System.out.println(main.aa());
		System.out.println(asciiToString(stringToAscii("質問タイトル"),encodeName));
		//System.out.print(aa());
		String s = "88eb";
		byte[] b = fromHexStringToByteArray(s);
		char[] c = byteToChar(unicodeToUTF8(b), encodeName);
		for (int i = 0; i < c.length; i++) {
			System.out.println(Integer.toHexString(c[i]));
		}
		ByteToCharConverter toChar;
		try {
			toChar = ByteToCharConverter.getConverter(encodeName);
			c = toChar.convertAll(b);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MalformedInputException e) {
			e.printStackTrace();
		}
        System.out.println(new String(c));
		
	}
	
	/**
     * 
     */
    @SuppressWarnings("deprecation")
	public static String randomStringUTF8Test(){
    	//[\xb0-\xcf][\xa0-\xd3]
    	//[code]\xa4[\xa1-\xf3][/code]
    	//[code][\xa1-\xa2][\xa0-\xfe][/code]
    	byte[] bStr = new byte[3];
    	bStr[0] = stringToByte("e4","e4");
    	bStr[1] = stringToByte("b8","b8");
    	bStr[2] = stringToByte("ad","ad");
    	ByteToCharConverter toChar;
		try {
			toChar = ByteToCharConverter.getConverter("UTF-8");
			return new String(toChar.convertAll(bStr));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		} catch (MalformedInputException e) {
			e.printStackTrace();
			return "";
		}
        
    }
    
    /**
     * 
     * @param path
     * @return
     */
    public static String writeFile(String path){
    	
    	File f = new File(path);
		//stream image file
		BufferedOutputStream bout = null;
		//@TODO 调整图片的大小后再保存
		try {
			
			bout = new BufferedOutputStream(new FileOutputStream(f));
			byte[] data = new byte[1024];
			int length = 0;
			data =  byteStr;
			bout.write(data, 0, length);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bout != null) {
				try {
					bout.close();
				} catch (Exception e) {
				}
			}
		}
		return "";
    }
}
