/* Build a FIFO queue
 * */

public class BlockingFIFO {
	
	// create an array of Task with size of 100.
	final Alert[] FIFOQueue = new Alert[100]; 
	// Two Monitors 
	Object notfull = new Object();
	Object notempty = new Object();
	int count = 0, nextin = 0, nextout = 0;

	
	public void put(Alert item) throws Exception {
		while (true) {
			if (count >= FIFOQueue.length) {
				synchronized (notfull) {
					notfull.wait();
				}
			}
			
			// Synchronized Block
			synchronized (this) { 				
				if (count > FIFOQueue.length) {
					continue;
				}

				FIFOQueue[nextin] = item;
				nextin = (nextin + 1) % FIFOQueue.length;
				count++;
				synchronized (notempty) {
					notempty.notify();
				}
				return;
			}
		}
	}
	
	public Alert take() throws Exception {
		while (true) {
			if (count < 1) {
				synchronized (notempty) {
					notempty.wait();
				}
			}
			// Synchronized Block
			synchronized (this) { 
				if (count < 1) {
					continue;
				}

				Alert result = FIFOQueue[nextout];
				nextout = (nextout + 1) % FIFOQueue.length;
				count--;
				synchronized (notfull) {
					notfull.notify();
				}
				return result;
			}
		}
	}
}
