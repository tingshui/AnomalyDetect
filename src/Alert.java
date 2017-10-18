/* This program alert the anomaly
 * */

import java.sql.Timestamp;

public class Alert {
	private static MutableInt mutableInt = new MutableInt();
	
	private String name;
	private boolean started;
	private int temp;
	private int error;
	private Timestamp timestamp;
	
	public Alert(String name, int temp, int error)
	{
		this.name = name;
		this.temp = temp;
		this.error = error;
		started = false;
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getTemp(){
		return temp;
	}
	
	public int getError(){
		return error;
	}
	
	public Timestamp getTime(){
		return timestamp;
	}
	
	public void setTime(Timestamp tm){
		this.timestamp = tm;
	}
	

    public void execute()
    {
		if(started) {
			throw new RuntimeException("Task started twice " + name);
		}
		else {
			started = true;
		}
		
		try {
	        Thread.sleep(250);
	        // Alert
	        System.out.println("Alert! " + "Task: " + name + ", Timestamp:" + timestamp);
	        
        }
        catch (InterruptedException e) {
	        e.printStackTrace();
        }
    }
}

class MutableInt {
	private int count = 0;
	
	synchronized int increment() 
	{
		return ++count;
	}
}
