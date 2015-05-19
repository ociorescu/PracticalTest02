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
	/*
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
					lp.println("[COMMUNICATION THREAD] Waiting for parameters from client (city / information type)!");
					String city            = bufferedReader.readLine();
					String informationType = bufferedReader.readLine();
					HashMap<String, DataInformation> data = serverThread.getData();
					DataInformation dataInformation = null;
					if (city != null && !city.isEmpty() && informationType != null && !informationType.isEmpty()) {
						if (data.containsKey(city)) {
							lp.println("[COMMUNICATION THREAD] Getting the information from the cache...");
							dataInformation = data.get(city);
						} else {
							lp.println("[COMMUNICATION THREAD] Getting the information from the webservice...");
							HttpClient httpClient = new DefaultHttpClient();
							HttpPost httpPost = new HttpPost(Constants.WEB_SERVICE_ADDRESS);
							List<NameValuePair> params = new ArrayList<NameValuePair>();        
							params.add(new BasicNameValuePair(Constants.QUERY_ATTRIBUTE, city));
							UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
							httpPost.setEntity(urlEncodedFormEntity);
							ResponseHandler<String> responseHandler = new BasicResponseHandler();
							String pageSourceCode = httpClient.execute(httpPost, responseHandler);
							if (pageSourceCode != null) {
								
								Document document = Jsoup.parse(pageSourceCode);
								Element element = document.child(0);
								Elements scripts = element.getElementsByTag(Constants.SCRIPT_TAG);
								for (Element script: scripts) {
									
									String scriptData = script.data();
									
									if (scriptData.contains(Constants.SEARCH_KEY)) {
										int position = scriptData.indexOf(Constants.SEARCH_KEY) + Constants.SEARCH_KEY.length();
										scriptData = scriptData.substring(position);
										
										JSONObject content = new JSONObject(scriptData);
										
										JSONObject currentObservation = content.getJSONObject(Constants.CURRENT_OBSERVATION);
										String temperature = currentObservation.getString(Constants.TEMPERATURE);
										String windSpeed = currentObservation.getString(Constants.WIND_SPEED);
										String condition = currentObservation.getString(Constants.CONDITION);
										String pressure = currentObservation.getString(Constants.PRESSURE);
										String humidity = currentObservation.getString(Constants.HUMIDITY);
										
										dataInformation = new DataInformation(
												temperature,
												windSpeed,
												condition,
												pressure,
												humidity);

										serverThread.setData(city, dataInformation);
										break;
									}
								}
							} else {
								lp.println("[COMMUNICATION THREAD] Error getting the information from the webservice!");
							}
						}
						
						if (dataInformation != null) {
							String result = null;
							if (Constants.ALL.equals(informationType)) {
								result = dataInformation.toString();
							} else if (Constants.TEMPERATURE.equals(informationType)) {
								result = dataInformation.getTemperature();
							} else if (Constants.WIND_SPEED.equals(informationType)) {
								result = dataInformation.getWindSpeed();
							} else if (Constants.CONDITION.equals(informationType)) {
								result = dataInformation.getCondition();
							} else if (Constants.HUMIDITY.equals(informationType)) {
								result = dataInformation.getHumidity();
							} else if (Constants.PRESSURE.equals(informationType)) {
								result = dataInformation.getPressure();
							} else {
								result = "Wrong information type (all / temperature / wind_speed / condition / humidity / pressure)!";
							}
							printWriter.println(result);
							printWriter.flush();
						} else {
							lp.println("[COMMUNICATION THREAD] Weather Forecast information is null!");
						}
						
					} else {
						lp.println("[COMMUNICATION THREAD] Error receiving parameters from client (city / information type)!");
					}
				} else {
					lp.println("[COMMUNICATION THREAD] BufferedReader / PrintWriter are null!");
				}
				socket.close();
			} catch (IOException ioException) {
				lp.println("[COMMUNICATION THREAD] An exception has occurred: " + ioException.getMessage());

			} catch (JSONException jsonException) {
				lp.println("[COMMUNICATION THREAD] An exception has occurred: " + jsonException.getMessage());				
			}
		} else {
			lp.println("[COMMUNICATION THREAD] Socket is null!");
		}
	}
*/
}
