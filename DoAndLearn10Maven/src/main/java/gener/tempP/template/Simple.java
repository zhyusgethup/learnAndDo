package gener.tempP.template;

import java.util.Arrays;
import java.util.Scanner;

public class Simple {
/***
 * vid,ff,owener,ct,owner_id,log,lv,exp,guildId,memSize,nm,desc,
 * @param args
 */
	public static void main(String[] args) {
//		Scanner scanner = new Scanner(System.in);
		String string = null;
		String string2 = "     hero_id      color_type      move_type      move_num      weapon_type   level      star      stage      hp      attack      speed      defense      magic_defense      technique      luck      bless_type      equiped_weapon      equiped_support      equiped_aoyi      equiped_a      equiped_b      equiped_c      equiped_seal      sp       exp";
//		String string2 = scanner.next();
		boolean black = false;
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < string2.length(); i++) {
			char a = string2.charAt(i);
			if(a == ' ' && !black) {
				black = true;
			}else if(a != ' '){
				black = false;
			}else {
				continue;
			}
			sb.append(a);
		}
		System.out.println(sb);
		String str = sb.toString().trim();
		String[] array = str.split(" ");
		System.out.println(Arrays.toString(array));
		for(String aa : array) {
			aa = toUpperCaseFirstOne(aa);
			System.out.println("HeroVO hvo = iCfg.get" + aa + "();");
		}
//		scanner.close();
	}
	public static String toUpperCaseFirstOne(String s){
		  if(Character.isUpperCase(s.charAt(0)))
		    return s;
		  else
		    return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
		}
}
