package com.example.practicaltest2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import android.util.Log;
import android.util.LogPrinter;
import android.widget.TextView;

public class ClientThread extends Thread {
	
	private static final String tag = "MY_TEST2";
	private LogPrinter lp = new LogPrinter(Log.DEBUG, tag);
	
	private String   address;
	private int      port;
	private String   city;
	private String   informationType;
	private TextView myTextView;
	
	private Socket   socket;
	
	public ClientThread(
			String address,
			int port,
			String city,
			String informationType,
			TextView weatherForecastTextView) {
		this.address                 = address;
		this.port                    = port;
		this.city                    = city;
		this.informationType         = informationType;
		this.myTextView = weatherForecastTextView;
	}
	
	@Override
	public void run() {
		try {
			socket = new Socket(address, port);
			if (socket == null) {
				lp.println("[CLIENT THREAD] Could not create socket!");
			}
			
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter    printWriter    = new PrintWriter(socket.getOutputStream(), true);
			if (bufferedReader != null && printWriter != null) {
				printWriter.println(city);
				printWriter.flush();
				printWriter.println(informationType);
				printWriter.flush();
				String dataInformation;
				while ((dataInformation = bufferedReader.readLine()) != null) {
					final String finalizedDataInformation = dataInformation;
					lp.println("[CLIENT THREAD] " + dataInformation);
					myTextView.post(new Runnable() {
						@Override
						public void run() {
							myTextView.append(finalizedDataInformation + "\n");
						}
					});
				}
			} else {
				lp.println("[CLIENT THREAD] BufferedReader / PrintWriter are null!");
			}
			socket.close();
		} catch (IOException ioException) {
			lp.println("[CLIENT THREAD] An exception has occurred: " + ioException.getMessage());

		}
	}

}
