package com.example.tripacker.tripacker.ws.remote;
public interface AsyncCaller {
	
	public void onBackgroundTaskCompleted(int requestCode, Object result);
	
}
