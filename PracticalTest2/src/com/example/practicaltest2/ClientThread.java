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
	
	private String   op1;
	private String   op2;
	private int      port;
	private String oper;
	private TextView myTextView;
	
	private Socket   socket;
	
	public ClientThread(
			String op1,
			String op2,
			String oper,
			TextView dataTextView) {
		this.op1                 = op1;
		this.op2                    = op2;
		this.oper                    = oper;
		this.myTextView = dataTextView;
	}
	
	@Override
	public void run() {
		try {
			socket = new Socket("127.0.0.1", 2525);
			if (socket == null) {
				lp.println("[CLIENT THREAD] Could not create socket!");
			}
			
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter    printWriter    = new PrintWriter(socket.getOutputStream(), true);
			if (bufferedReader != null && printWriter != null) {
				String cmd = "" + oper + "," + op1 + "," + op2;
				printWriter.println(cmd);
				printWriter.flush();
				String dataInformation;
				while ((dataInformation = bufferedReader.readLine()) != null) {
					final String finalizedDataInformation = dataInformation;
					lp.println("[CLIENT THREAD] " + dataInformation);
					myTextView.post(new Runnable() {
						@Override
						public void run() {
							myTextView.setText(finalizedDataInformation);
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
