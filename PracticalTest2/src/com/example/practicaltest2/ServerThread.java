package com.example.practicaltest2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import android.util.Log;
import android.util.LogPrinter;

public class ServerThread extends Thread {
	
	private static final String tag = "MY_TEST2";
	private LogPrinter lp = new LogPrinter(Log.DEBUG, tag);
	
	private int port = 0;
	private ServerSocket serverSocket = null;
	
	private HashMap<String, DataInformation> data = null;
	
	public ServerThread(int port) {
		this.port = port;
		try {
			this.serverSocket = new ServerSocket(port);
		} catch (IOException ioException) {
			lp.println("An exception has occurred: " + ioException.getMessage());
		}
		this.data = new HashMap<String, DataInformation>();
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public int getPort() {
		return port;
	}
	
	public void setServerSocker(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}
	
	public ServerSocket getServerSocket() {
		return serverSocket;
	}
	
	public synchronized void setData(String city, DataInformation weatherForecastInformation) {
		this.data.put(city, weatherForecastInformation);
	}
	
	public synchronized HashMap<String, DataInformation> getData() {
		return data;
	}
	
	@Override
	public void run() {
		try {		
			while (!Thread.currentThread().isInterrupted()) {
				lp.println("[SERVER] Waiting for a connection...");
				Socket socket = serverSocket.accept();
				lp.println("[SERVER] A connection request was received from " + socket.getInetAddress() + ":" + socket.getLocalPort());
				CommunicationThread communicationThread = new CommunicationThread(this, socket);
				communicationThread.start();
			}			
		} catch (IOException ioException) {
			lp.println("An exception has occurred: " + ioException.getMessage());
		}
	}
	
	public void stopThread() {
		if (serverSocket != null) {
			interrupt();
			try {
				serverSocket.close();
			} catch (IOException ioException) {
				lp.println("An exception has occurred: " + ioException.getMessage());
				
			}
		}
	}

}
