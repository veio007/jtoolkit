package com.veio.example;


import org.apache.commons.codec.binary.Base64;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

public class CodecTest {
	public static void main(String[] args) {
		String str = "01234567890123456789012345adfefadfefafef[954584ffdfdfdfdfdaefffffffffffffefefefefe151212fefe" +
				"afefeffffffffffffffffffffff515455e15f4ef1e5fe4f1e5fe5f4e1f5e4fef1e5f4ef1e5fe15f1e5fe4f1e5fe4f4efef" +
				"effffffffffffffffffffffffffe9555555555555409439043904930434jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj" +
				"ddddddddddddddddddddddddddddddfferrrrrrrrrrrrrgggrgrgrgrgrgrgrgrgrgrgr";
		long start = System.currentTimeMillis();
		for(int i=0;i<1000000;i++) {
			String base64 = Base64.encodeBase64String(str.getBytes());
			byte[] data = Base64.decodeBase64(base64);
		}
		System.out.println(System.currentTimeMillis() - start);
	}
}
