package zhyu.common.objectUtils;

import java.net.InetAddress;

public class HostUtil {
	static InetAddress netAddress = null;

	public static void main(String[] args) {
		System.out.println("host ip:" + HostUtil.getHostIp());
		System.out.println("host name:" + HostUtil.getHostName());
	}

	public static InetAddress getInetAddress() throws Exception {
		return InetAddress.getLocalHost();
	}

	public static String getHostIp() {
		String ip;
		try {
			if (null == netAddress) {
				netAddress = HostUtil.getInetAddress();
			}
			ip = netAddress.getHostAddress();
		} catch (Exception e) {
			e.printStackTrace();
			ip = "unknow ip";
		}
		return ip;
	}

	public static String getHostName() {
		String name;
		try {
			if (null == netAddress) {
				netAddress = HostUtil.getInetAddress();
			}
			name = netAddress.getHostName();
		} catch (Exception e) {
			e.printStackTrace();
			name = "unknow host";
		}
		return name;
	}
}
