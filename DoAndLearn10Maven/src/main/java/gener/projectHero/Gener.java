package gener.projectHero;

import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.security.auth.x500.X500Principal;

import gener.projectHero.Vo.Param;

public class Gener {
	// public static final int Guild_Create = ModuleID.Guild + 1; // ��������

	/**
	 * case "join": sendMessage(connection,120002,getDataMap(new
	 * Object[]{"id",1231231}//guildId )); break;
	 * 
	 */
	// public static String c_name = "int\\s(\\w+)\\s";
	public static String c_name = "int\\s(\\w+)\\s=\\s\\w+\\.\\w+\\s+\\+\\s(\\d+);\\s+//\\s+(\\S+)";
	// public static String c_method = "@Client[(]Command//.(\\w+)[)]";
	public static String c_method = "Command(.*?);";
	public static String has_Pa = "@CParam";
	public static String get_name = "\\.(\\w+)[)]";
	public static String c_map = "\\.(\\w+)[)].*?@CParam[(]\"(\\w+)\"";
	public static String get_param = "@CParam[(]\"(\\w+)\"[)]\\s*(\\w+)";
	public static String caseA = "case \"##1\"://##3\nsendMessage(connection,##2,getDataMap(new Object[]{}));\n break;\n";
	public static String caseB = "case ##1:\nSystem.out.println(\"##2\");\nbreak;";

	public static void main(String[] args) {
		List<Vo> vos = parseCommand();
		Map<String, List<Param>> params = parseMethod();
		for (Vo vo : vos) {
			String name = vo.getName();
			if (params.containsKey(name)) {
				List<Param> pa = params.get(name);
				if (pa != null) {
					vo.setParams(pa);
				}
			}
		}
		vos.forEach(x -> System.out.println("name = " + x.getMessage()   + " id: " + x.getId() + "  param:" + x.getParams()));
		StringBuilder cA = getCaseA(vos);
		System.out.println(cA);
		StringBuilder cB = getCaseB(vos);
		System.out.println(cB);
	}
	private static StringBuilder getCaseB(List<Vo> vos) {
		StringBuilder sb = new StringBuilder();
		for (Vo vo : vos) {
			String pp = caseB;
			pp = pp.replace("##1", vo.getId() + "");
			pp = pp.replace("##2", vo.getMessage() + "�ɹ�");
			sb.append(pp);
		}
		return sb;
	}

	// ����ĸתСд
	public static String toLowerCaseFirstOne(String s) {
		if (Character.isLowerCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
	}

	private static StringBuilder getCaseA(List<Vo> vos) {
		StringBuilder sb = new StringBuilder();
		for (Vo vo : vos) {
			String sub = new String(caseA);
			String name = toLowerCaseFirstOne(vo.getName());
			Integer id = vo.getId();
			List<Param> params = vo.getParams();
			sub = sub.replace("##1", name);
			sub = sub.replace("##2", id + "");
			sub = sub.replace("##3", vo.getMessage());
			if (params !=null && params.size() > 0) {
				StringBuilder pp = new StringBuilder();
				for (Param param : params) {
					pp.append("\"" + param.getName() + "\", ");
					switch (param.getType()) {
					case "String":
						pp.append("\"\",");
						break;
					case "int":
						pp.append(1231231 + ",");
						break;
					case "boolean":
						pp.append("true,");
						break;
					}
				}
				pp.replace(pp.length() - 1, pp.length(), "");
				sub = sub.replace("{}", "{" + pp + "}");
//				System.out.println(sb);
			} else {
			}
			sb.append(sub);
		}
		return sb;
	}

	private static Map<String, List<Param>> getMapFromList(List<String> strs) {
		Pattern pattern1 = Pattern.compile(has_Pa);
		Pattern pattern2 = Pattern.compile(get_name);
		Map<String, List<Param>> maps = new HashMap<>();
		for (String str : strs) {
			Matcher m_has = pattern1.matcher(str);
			Matcher m_name = pattern2.matcher(str);
			String name = null;
			if (m_name.find()) {
				name = m_name.group(1);
			}
			if (m_has.find()) {
				// System.out.println("1");
				Pattern p3 = Pattern.compile(get_param);
				Matcher m = p3.matcher(str);
				List<Param> params = new ArrayList<>();
				while (m.find()) {
					String p_name = m.group(1);
					String type = m.group(2);
					Vo.Param param = new Vo.Param(p_name, type);
					params.add(param);
				}
				maps.put(name, params);
			} else {
				maps.put(name, null);
			}
		}
		return maps;
	}

	private static Map<String, List<Param>> parseSB(StringBuilder sb) {

		Pattern pattern = Pattern.compile(c_method);
		Matcher matcher = pattern.matcher(sb);
		List<String> strs = new ArrayList<>();
		while (matcher.find()) {
			String word = matcher.group(1);
			strs.add(word);
		}
		Map<String, List<Param>> map = getMapFromList(strs);
		return map;
	}

	private static Map<String, List<Param>> parseMethod() {
		String name = "E:\\git repository\\DoAndLearn1.0\\bin\\gener\\projectHero\\method.txt";
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(name));
			String command = null;
			while ((command = br.readLine()) != null) {
				sb.append(command);
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return parseSB(sb);
	}

	private static List<Vo> parseCommandToVo(List<String> lists) {
		List<Vo> vos = new ArrayList<>();
		Pattern pattern = Pattern.compile(c_name);
		for (String c : lists) {
			Matcher matcher = pattern.matcher(c);
			while (matcher.find()) {
				String name = matcher.group(1);
				String id = matcher.group(2);
				String meString = matcher.group(3);
				Vo vo = new Vo();
				vo.setId(Integer.parseInt(id));
				vo.setMessage(meString);
				vo.setName(name);
				vos.add(vo);
			}
		}
		return vos;
	}

	private static List<Vo> parseCommand() {
		String name = "E:\\git repository\\DoAndLearn1.0\\bin\\gener\\projectHero\\command.txt";
//		System.out.println(name);
		List<String> commandList = new ArrayList<>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(name));
			String command = null;
			while ((command = br.readLine()) != null) {
				commandList.add(command);
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		List<Vo> vos = parseCommandToVo(commandList);
		// System.out.println(vos);
		return vos;
	}
}
