import java.io.IOException;
import java.util.ArrayList;

/* Qianying Lin, Oct.17th
 * This is a program to alert anomaly
 * */
public class AnomDetect {

    public void Detect(final int isTestMode, final String filename)
	{    	
		// Initialize the executor with 10 threads & a queue size of 100. 
		final Detector taskExecutor = new Detector(10);

		// Inject 1000 tasks into the executor in a separate thread. 
		Runnable inserter = new Runnable() {
			public void run()
			{
				if( isTestMode == 0){
					for (int idx = 0; idx < 1000; idx++) {
						// randomly generate input data
						int temp = (int)(Math.random()*80);
						int error =(int)(Math.random()*200);
						//machine name is the id
						String name = "id" + idx;
						Alert myTask = new Alert(name, temp, error);
						System.out.println("Adding input data, machine name: " 
								+ myTask.getName() + ", CPU temp: " + temp + ", Read Error: "+ error);
						taskExecutor.addAnomaly(myTask);
						Thread.yield();
					}
				}
				else{
					Read r = new Read(filename);
					try {
						ArrayList<InputData> dataset = r.readData();
						for (int i = 0; i < dataset.size(); i++){
							InputData ip = dataset.get(i);
							Alert myTask = new Alert(ip.machinename, ip.CUPtemp, ip.Error);
							System.out.println("Adding input data, machine name: " 
									+ myTask.getName() + ", CPU temp: " + myTask.getTemp() + ", Read Error: "+ myTask.getError());
							taskExecutor.addAnomaly(myTask);
							Thread.yield();
						}
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		};
		
		Thread insertThread = new Thread(inserter);
		insertThread.start();
	}

	public static void main(String args[])
	{
		int isTestMode = Integer.parseInt(args[0]);
		String filename = args[1];
		AnomDetect app = new AnomDetect();
		app.Detect(isTestMode, filename);
	}

}
