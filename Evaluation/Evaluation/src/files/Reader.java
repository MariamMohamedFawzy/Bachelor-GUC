package files;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

import json.Business;

public class Reader {
	
	public static List<Business> readBusinesses(String version, String category, String topicNumber) {
	
		String filePath = "v" + version + "/eval/" + category + "/" + topicNumber + "/businesses.ser";
		
		List<Business> businesses = null;
		
		FileInputStream streamIn = null;
		ObjectInputStream objectinputstream = null;
		try {
		    streamIn = new FileInputStream(filePath);
		    objectinputstream = new ObjectInputStream(streamIn);
		    businesses = (List<Business>) objectinputstream.readObject();
		} catch (Exception e) {
		    e.printStackTrace();
		} finally {
		    if(objectinputstream != null){
		        try {
					objectinputstream .close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    } 
		}
		
		return businesses;
		
	}
	
	public static double readRMSE(String version, String category, String topicNumber) {
		
		String filePath = "v" + version + "/eval/" + category + "/" + topicNumber + "/rmse.txt";
		
		double rmse = 0;
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(filePath));
			StringBuilder sb = new StringBuilder();
		    String line = br.readLine();
		    rmse = Double.parseDouble(line);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		    try {
		    	if (br != null) {
		    		br.close();	
		    	}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return rmse;

		
	}

}
