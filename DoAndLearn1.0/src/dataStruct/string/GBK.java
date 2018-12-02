package dataStruct.string;import javax.print.StreamPrintService;

public class GBK {

	public static void main(String[] args) {
		String string = "�Ұ���һ��һ��";
		String string2 = "һ�������ַ�����";
		String string3 = "�����HOW ARE";
		String[] strs = new String[] {string,string2,string3};
		for (int i = 0; i < strs.length; i++) {
			checkGBK(strs[i]);
			checkStringLength(strs[i]);
			System.out.println();
		}
	}
	private static void checkStringLength(String str) {
		System.out.println(str.length());
	}
	private static void checkGBK(String str) {
//		[\u0391-\uFFE5]
		int len = 0;
		for(int i = 0; i < str.length(); i++) 
		{
			char a = str.charAt(i);
			if(a >= '\u0391' && a <= '\uFFE5') {
				len += 2;
			}else {
				len++;
			}
		}
		System.out.println(len);
	}

}
