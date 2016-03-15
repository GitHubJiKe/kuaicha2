package com.ypf.kuaicha.util;

public abstract class ForeTask extends BackForeTask {
	
	public ForeTask(){}

	@Override
	protected abstract void runInBackground();

	@Override
	protected void runInForeground(){
	    
	}

}
