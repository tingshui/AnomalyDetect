/* This program detect the anomaly, add timestamp, and then add them to the queue
 * */

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Detector
{
	final List<TaskRunner> runnerPool = new ArrayList<TaskRunner>();
	final BlockingFIFO blockingFifoQueue = new BlockingFIFO();
	
	class TaskRunner implements Runnable {
		boolean available = true;
		
		@Override
		public void run() {
			while (true) {
				try {
					// if available, take from the queue
					Alert newTask = Detector.this.blockingFifoQueue.take();
					available = false;
					newTask.execute();
					available = true;
				} catch (Throwable th) {
				}
			}

		}
	}
	
	// build a list of detector
	public Detector(int numThreads) {
		for (int i = 0; i < numThreads; i++) {
			runnerPool.add(new TaskRunner());
			Thread t = new Thread(runnerPool.get(i));
			t.start();
		}
	}
	
	// default number is 10
	public Detector() {
		int numThreads = 10;
		for (int i = 0; i < numThreads; i++) {
			runnerPool.add(new TaskRunner());
		}
	}
	
	public void addAnomaly(Alert task)
	{
		try {
			int temp = task.getTemp();
			int error = task.getError();
			String name = task.getName();
			NeuralNet nn = new NeuralNet(temp, error);
			if (nn.isAnomaly()){
				System.out.println("----Add Anomaly. Machine name: " + name + ", temp : " + temp + ", error: "+ error + " is an anomaly!");
				task.setTime(new Timestamp(System.currentTimeMillis()));
				blockingFifoQueue.put(task);
			};
			
		} catch (Exception e) {
//			e.printStackTrace();
		}
	}

}
