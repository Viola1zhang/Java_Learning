package mq.mvc.common;

import java.net.InetAddress;
import java.rmi.UnknownHostException;

public class UUIDUtil {
	private static String localhost = null;
	
	public static String getUUID() {
		if(localhost ==null) {
			localhost = getLocalHost();
			
		}
		return localhost + System.currentTimeMillis();
	}
	
	private static String getLocalHost() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (java.net.UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
