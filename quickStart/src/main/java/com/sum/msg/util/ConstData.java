package com.sum.msg.util;

public class ConstData {

	private static final String ADDRESS = "192.168.213.128";
	private static final String PORT = "9876";
	
	
	public static String getServerAddress() {
		return ADDRESS + ":" + PORT;
	}
}
