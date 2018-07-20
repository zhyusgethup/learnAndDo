package zhyu.common.objectUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("all")
public final class JSONUtils {
	private static final ObjectMapper MAPPER = new ObjectMapper();

	public static String toJSONString(Object value) {
		StringWriter writer = new StringWriter(1000);
		try {
			MAPPER.writeValue(writer, value);
		} catch (IOException e) {
		}
		return writer.toString();
	}

	public static MapEntry parseObject(String value) {
		return parseObject(value, MapEntry.class);
	}

	public static <T> T parseObject(String value, Class<T> clazz) {
		try {
			return MAPPER.readValue(value, clazz);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String[] args) {
//		String sign = "{\"area_id\":\"8110101\",\"comments\":\"   \",\"cust_address\":\"喔喔凤凰\",\"cust_contact_list\":[{\"state\":\"10X\",\"mobile_phone\":\"15928764083\",\"cust_contact_id\":35,\"contact_name\":\"效果好\"}],\"cust_id\":\"f790437d-9186-4246-8d64-427d157ad185\",\"cust_name\":\"喔喔\",\"key\":\"f3:46:af:df:9d:06:f3:88:57:dd:77:4c:8c:a9:a3:b7\",\"os_type\":\"00\",\"service_code\":\"CUST_DETAILE_MOD\",\"terminal_type\":\"00\",\"ticket\":\"8a62ae8e-1d40-439e-89df-5b64222cf4d0\",\"ticket_object\":{\"bss_org_id\":\"eebd3367-d663-4874-823c-f6fda0c8a6e1\",\"bss_org_name\":\"软件三部\",\"company_id\":\"2ae66a71-2f58-11e4-bd6b-00163e000f02\",\"company_name\":\"成都微销科技公司\",\"email_addr\":\"liubin1@tydic.com\",\"head_pic_url\":\"http://115.29.77.35/wesale/attach_01/20140901/383ad146-519a-4929-b2ac-014515815df3.png\",\"jion_date\":1409241600000,\"mob_acc_nbr\":\"18108086237\",\"open_fire_id\":\"3c65ca8b-000a-453c-8e17-7c9505daff62\",\"role_id\":\"f4f51dc8-abf7-4c68-997e-06a81a306749\",\"role_name\":\"普通员工\",\"sex\":\"M\",\"user_id\":\"914eccd1-0523-4213-b56c-ad9f4f03eb45\",\"user_name\":\"刘彬\"}}";
		String sign = "{\"area_id\":\"8110101\",\"comments\":\"是的\",\"cust_address\":\"也\",\"cust_attr_list\":[{\"attr_id\":\"16\",\"attr_name\":\"所属行业\",\"attr_value\":\"建筑\",\"attr_value_type\":\"4\",\"state\":\"10A\"},{\"attr_id\":\"17\",\"attr_name\":\"企业规模\",\"attr_value\":\"1~50人\",\"attr_value_type\":\"4\",\"state\":\"10A\"}],\"cust_contact_list\":[{\"contact_name\":\"范海宁\",\"isIndex\":false,\"mobile_phone\":\"18501630341\",\"state\":\"10A\"},{\"contact_name\":\"范海宁\",\"cust_contact_id\":\"70\",\"isIndex\":false,\"mobile_phone\":\"18501630341\",\"state\":\"10X\"}],\"cust_id\":\"6c734fa7-c502-4fc5-b16b-09d1f376465a\",\"cust_name\":\"天源迪科\",\"device_id\":\"00000000-74df-4b63-ffff-ffffcf14b592\",\"key\":\"f3:46:af:df:9d:06:f3:88:57:dd:77:4c:8c:a9:a3:b7\",\"os_type\":\"01\",\"service_code\":\"CUST_DETAILE_MOD\",\"terminal_type\":\"00\",\"ticket\":\"9cd75ea6-c790-4544-b2f2-c034dee91efd\",\"ticket_object\":{\"bss_org_id\":\"eebd3367-d663-4874-823c-f6fda0c8a6e1\",\"bss_org_name\":\"软件三部\",\"company_id\":\"2ae66a71-2f58-11e4-bd6b-00163e000f02\",\"company_name\":\"成都微销科技公司\",\"email_addr\":\"fanhn@tydic.com\",\"head_pic_url\":\"http://115.29.77.35/wesale/head_pic_url/3.jpg\",\"mob_acc_nbr\":\"13600000000\",\"open_fire_id\":\"5ba13edd-9fab-4e58-b3cd-4d09f3f006e8\",\"role_id\":\"f4f51dc8-abf7-4c68-997e-06a81a306749\",\"role_name\":\"普通员工\",\"sex\":\"M\",\"user_id\":\"22e8ea1f-d2e3-425e-b05c-42883d7f27fe\",\"user_name\":\"范海宁\"}}";
		LinkedMapEntry linkedParam = JSONUtils.parseObject(sign, LinkedMapEntry.class);
		List list = linkedParam.getList("cust_attr_list");
		List list1 = linkedParam.getList("cust_contact_list");
		Object obj = linkedParam.get("ticket_object");
		System.out.println(JSONUtils.toJSONString(linkedParam));
	}
}
