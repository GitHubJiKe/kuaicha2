package com.ypf.kuaicha.util;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class BackForeTask {
	private static ExecutorService _threadPool = Executors.newFixedThreadPool(6);
	private static Handler _handler = new Handler(Looper.getMainLooper());
	
//	protected boolean exeBackTask ;
	
	public BackForeTask(){
//		if(!exeBackTask){
//			_handler.post(new Runnable() {
//				@Override
//				public void run() {
//					runInForeground();
//				}
//			});
//		}
	}
	
	public void execute(){
	    _threadPool.execute(new Runnable() {
            @Override
            public void run() {
                runInBackground();
                _handler.post(new Runnable() {
                    @Override
                    public void run() {
                        runInForeground();
                    }
                });
            }
        });
	}
	
	protected abstract void runInBackground();
	protected abstract void runInForeground();
}

