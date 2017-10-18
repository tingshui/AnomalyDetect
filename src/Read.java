/* This is the api for read in data
 * */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Read {

	ArrayList<InputData> dataset;
	String filename;
	public Read(String filename){
		this.filename = filename;
	}
	public ArrayList<InputData> readData() throws IOException{
		dataset = new ArrayList<InputData>();
		BufferedReader br = new BufferedReader(new FileReader(filename));
		try {
		    String line = br.readLine();
		    while (line != null) {
		    	String[] d = line.split(",");
		    	InputData ip = new InputData(d[0], Integer.parseInt(d[1]), Integer.parseInt(d[2]));
		    	dataset.add(ip);
		        line = br.readLine();
		    }
		} finally {
		    br.close();
		}
		
		return dataset;
	}
}
