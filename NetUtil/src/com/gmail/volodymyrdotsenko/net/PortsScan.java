package com.gmail.volodymyrdotsenko.net;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PortsScan {

	public static List<Integer> openPortsList = new ArrayList<Integer>();
	
	public static void main(String[] args) {
		try {
			long startTime = System.currentTimeMillis();
			
			final ExecutorService es = Executors.newFixedThreadPool(100);
			final String ip = "10.91.63.88";
			final int timeout = 200;
			final List<Future<Boolean>> futures = new ArrayList<>();
			for (int port = 1; port <= 65535; port++) {
				futures.add(portIsOpen(es, ip, port, timeout));
			}
			es.shutdown();
			int openPorts = 0;
			for (final Future<Boolean> f : futures) {
				if (f.get()) {
					openPorts++;
				}
			}

			System.out.println("There are " + openPorts
					+ " open ports on host " + ip
					+ " (probed with a timeout of " + timeout + "ms):");
			
			long entTime = System.currentTimeMillis();
			
			for(Integer p : openPortsList) {
				System.out.println(p);
			}
			
			System.out.println("Ports scan took place: " + (entTime - startTime) / 1000 + " sec.");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static Future<Boolean> portIsOpen(final ExecutorService es,
			final String ip, final int port, final int timeout) {

		return es.submit(new Callable<Boolean>() {
			@Override
			public Boolean call() {
				try {
					Socket socket = new Socket();
					socket.connect(new InetSocketAddress(ip, port), timeout);
					socket.close();
					openPortsList.add(port);
					return true;
				} catch (Exception ex) {
					return false;
				}
			}
		});
	}
}