package arithmetic.bytes;

public class HexCutHalf {

	public static void main(String[] args) {
//		String userKey = "5634";
//		String string = new String(hex2byte(userKey.getBytes()));
//		System.out.println(string);
		String word = "0001";
		System.out.println(Integer.valueOf(word));
//		System.out.println(string.length());
	}
	public static byte[] hex2byte(byte[] b) {  
        if((b.length%2)!=0)  
            throw new IllegalArgumentException();  
        byte[] b2 = new byte[b.length/2];  
        for (int n = 0; n < b.length; n+=2) {  
            String item = new String(b,n,2);  
            b2[n/2] = (byte)Integer.parseInt(item,16);  
        }  
        return b2;  
    } 
}
