package com.gmail.volodymyrdotsenko.net;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class GetAllIpByHostName {

	public static void main(String[] args) {
		try {
			InetAddress address = InetAddress.getByName("www.yahoo.com");
			System.out.println(address.getHostAddress());
			System.out.println(address.getHostName());
			
			for (InetAddress addr : InetAddress
					.getAllByName("stackoverflow.com"))
				System.out.println(addr.getHostAddress());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}