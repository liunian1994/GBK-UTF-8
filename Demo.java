package demo;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class Demo {
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		String str = "2¡¤";//[-62,-73]
		System.out.println(Arrays.toString(str.getBytes()));
		System.out.println(str);
		try {
			System.out.println(str.getBytes("utf-8").length);
			String str1 = new String(getUTF8BytesFromGBKString(str),"UTF-8");
			System.out.println(str1);
			System.out.println(str1.getBytes("utf-8").length);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	
	public static byte[] getUTF8BytesFromGBKString(String gbkStr) {
		int n = gbkStr.length();
		byte[] utfBytes = new byte[3 * n];
		int k = 0;
		for (int i = 0; i < n; i++) {
			int m = gbkStr.charAt(i);
			if(m < 128 && m >=0) {
				utfBytes[k++] = (byte) m;
				continue;
			}
			else if( m < 2048){
				utfBytes[k++] = (byte) ((m>>6) | 0xc0);
				utfBytes[k++] = (byte) (0x80 | ((m & 0x3f)));
				continue;
			}
			utfBytes[k++] = (byte) (0xe0 | (m >> 12));
            utfBytes[k++] = (byte) (0x80 | ((m >> 6) & 0x3f));
            utfBytes[k++] = (byte) (0x80 | (m & 0x3f));
		}
		if(k < utfBytes.length) {
			byte[] tmp = new byte[k];
			System.arraycopy(utfBytes, 0, tmp, 0, k);
			System.out.println(Arrays.toString(tmp));
			return tmp; 
		}
		return utfBytes;
	}
	
	
	
}
