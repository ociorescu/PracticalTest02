package com.example.practicaltest2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.practicaltest2.Constants;

import android.util.Log;
import android.util.LogPrinter;

public class CommunicationThread extends Thread {
	
	private static final String tag = "MY_TEST2";
	private LogPrinter lp = new LogPrinter(Log.DEBUG, tag);
	
	private ServerThread serverThread;
	private Socket       socket;
	
	public CommunicationThread(ServerThread serverThread, Socket socket) {
		this.serverThread = serverThread;
		this.socket       = socket;
	}
	
	@Override
	public void run() {
		if (socket != null) {
			try {
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter    printWriter    = new PrintWriter(socket.getOutputStream(), true);
				if (bufferedReader != null && printWriter != null) {
					lp.println("[COMMUNICATION THREAD] Waiting for parameters from client !");
					String cmd = bufferedReader.readLine();
					String[] split = cmd.split(",");
					if(split.length >= 3){
						String oper = split[0];
						String op1 = split[1];
						String op2 = split[2];
						HashMap<String, DataInformation> data = serverThread.getData();
						DataInformation dataInformation = null;
						if (!op1.isEmpty() && !op2.isEmpty()) {
							lp.println("[COMMUNICATION THREAD] CMD: " + cmd);
							lp.println("[COMMUNICATION THREAD] "+oper + " " + op1 + " " + op2);
							int result = 0;
							if(oper.equals("mul")){
								try {
									Thread.sleep(1000);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								result = Integer.parseInt(op1) * Integer.parseInt(op2);
							}else{
								result = Integer.parseInt(op1) + Integer.parseInt(op2);
							}
							printWriter.println("" + result);
							printWriter.flush();
							
						} else {
							lp.println("[COMMUNICATION THREAD] Error receiving parameters from client (city / information type)!");
						}
					}
				} else {
					lp.println("[COMMUNICATION THREAD] BufferedReader / PrintWriter are null!");
				}
				socket.close();
			} catch (IOException ioException) {
				lp.println("[COMMUNICATION THREAD] An exception has occurred: " + ioException.getMessage());
			}	
		} else {
			lp.println("[COMMUNICATION THREAD] Socket is null!");
		}
	}

}
