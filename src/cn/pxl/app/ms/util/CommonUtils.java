package cn.pxl.app.ms.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javax.servlet.http.HttpSession;

import cn.pxl.app.ms.dto.ResultDto;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CommonUtils {
	
	//十六进制下数字到字符的映射数组  
    private final static String[] hexDigits = {"0", "1", "2", "3", "4",  
        "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};  
      
    /** * 把inputString加密     */  
    public static String generatePassword(String inputString){  
        return encodeByMD5(inputString);  
    }  
      
      /** 
       * 验证输入的密码是否正确 
     * @param password    加密后的密码 
     * @param inputString    输入的字符串 
     * @return    验证结果，TRUE:正确 FALSE:错误 
     */  
    public static boolean validatePassword(String password, String inputString){  
        if(password.equals(encodeByMD5(inputString))){  
            return true;  
        } else{  
            return false;  
        }  
    }  
    /**  对字符串进行MD5加密     */  
    private static String encodeByMD5(String originString){  
        if (originString != null){  
            try{  
                //创建具有指定算法名称的信息摘要  
                MessageDigest md = MessageDigest.getInstance("MD5");  
                //使用指定的字节数组对摘要进行最后更新，然后完成摘要计算  
                byte[] results = md.digest(originString.getBytes());  
                //将得到的字节数组变成字符串返回  
                String resultString = byteArrayToHexString(results);  
                return resultString.toUpperCase();  
            } catch(Exception ex){  
                ex.printStackTrace();  
            }  
        }  
        return null;  
    }  
      
    /**  
     * 转换字节数组为十六进制字符串 
     * @param     字节数组 
     * @return    十六进制字符串 
     */  
    private static String byteArrayToHexString(byte[] b){  
        StringBuffer resultSb = new StringBuffer();  
        for (int i = 0; i < b.length; i++){  
            resultSb.append(byteToHexString(b[i]));  
        }  
        return resultSb.toString();  
    }  
      
    /** 将一个字节转化成十六进制形式的字符串     */  
    private static String byteToHexString(byte b){  
        int n = b;  
        if (n < 0)  
            n = 256 + n;  
        int d1 = n / 16;  
        int d2 = n % 16;  
        return hexDigits[d1] + hexDigits[d2];  
    }  
	
	
	
	/** DateFormat */
    private static SimpleDateFormat sdf = new SimpleDateFormat();
    private static ObjectMapper mapper = new ObjectMapper();
	
	private static ResourceBundle rb;
	public static String getConfig(String id) {
		if (rb == null) {
			rb = ResourceBundle.getBundle("config");
		}
		return rb.getString(id);
	}
	
	/**
    *
    * @param password
    * @return
    * @throws NoSuchAlgorithmException
    */
   public static final String encodePassword(String password) {
       byte[] binary = password.getBytes();
       MessageDigest md = null;
       try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
       byte[] digest = md.digest(binary);
       StringBuilder hexStrBuilder = new StringBuilder();
       for (int i = 0; i < digest.length; i++) {
           if ((digest[i] & 0xff) < 0x10) {
               hexStrBuilder.append(String.valueOf(0));
           }
           hexStrBuilder.append(Integer.toHexString(0xff & digest[i]));
       }
       return hexStrBuilder.toString();
   }
   
   public static final String convertResult(ResultDto rd, HttpSession session) {
	   	
	    String result = "";	   	
	   	try {
			result = mapper.writeValueAsString(rd);
		} catch (Exception e) {
			result = "{'status':0, 'content':'exception'}";
		}
	   	return result;
   }
   
   /**
   *
   * @param date
   * @param format
   * @return
   */
  public static String formatDate(Date date, String format) {
      sdf.applyPattern(format);
      sdf.setLenient(false);
      return sdf.format(date);
  }

  /**
   *
   * @param date
   * @param format
   * @return
   */
  public static Date formatDate(String date, String format) {
      sdf.applyPattern(format);
      sdf.setLenient(false);
      try {
          return sdf.parse(date);
      } catch (ParseException e) {
          return null;
      }
  }
}
