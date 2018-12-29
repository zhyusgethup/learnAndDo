package copyToFile.analyse.template.simple;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.util.concurrent.Service.State;

import copyToFile.analyse.template.Template;

public class LineTemp implements Template{
	public double checkSatisfy(StringBuilder sb) {
		int count = 0;
		int start = 0;
		Pattern pattern = Pattern.compile("\n");
		Matcher matcher = pattern.matcher(sb);
		while(matcher.find()) {
			System.out.println(sb.substring(start,matcher.end() - 1));
			start = matcher.end();
		}
		return 0;
	}
	public StringBuilder appendEnd(StringBuilder sb){
		if(sb.charAt(sb.length()) != '\n'){
			sb.append('\n');
		}
		return sb;
	}
	public static void main(String[] args) {
		StringBuilder sb = new StringBuilder("hello\nsdasd\nsdasd\nweqwe");
		new LineTemp().checkSatisfy(sb);
	}
}
