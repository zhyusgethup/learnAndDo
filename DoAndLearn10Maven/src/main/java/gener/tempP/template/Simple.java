package gener.tempP.template;

import java.util.Scanner;

public class Simple {
/***
 * vid,ff,owener,ct,owner_id,log,lv,exp,guildId,memSize,nm,desc,
 * @param args
 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String string = null;
		scanner.useDelimiter("\\.");
//		while((string = scanner.nextLine()) != null) {
		while (null != (string = scanner.next())) {
//			System.out.println("goodsCounts.put(" + string + ",10);");
			System.out.print(120000 + Integer.parseInt(string) + ",");
		}//
		scanner.close();
	}

}
