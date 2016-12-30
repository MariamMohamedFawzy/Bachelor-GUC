package helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import json.Business;

public class General {

	public static ArrayList<Business> getBusinessSorted(
			List<Business> businesses, boolean yelpRating) {

		ArrayList<Business> sorted = new ArrayList<Business>();

		HashMap<String, Business> hmB = new HashMap<String, Business>();

		for (Business business : businesses) {
			hmB.put(business.getBusinessId(), business);
		}

		HashMap<String, Double> hmD = General.getHashOfBusinessesValues(
				businesses, yelpRating);

		hmD = Sorter.sortBusinessValues(hmD);

		for (String businessId : hmD.keySet()) {
			Business b = hmB.get(businessId);
			sorted.add(b);
		}

		return sorted;
	}

	public static HashMap<String, Double> getHashOfBusinessesValues(
			List<Business> businesses, boolean yelpRating) {

		HashMap<String, Double> hm = new HashMap<String, Double>();

		for (Business business : businesses) {
			double value = 0;
			if (yelpRating) {
				value = business.getStars();
			} else {
				value = business.getNewStar();
			}
			hm.put(business.getBusinessId(), value);
		}

		return hm;
	}

	public static int[] getIntegerArrayOfBusinesses(
			List<Business> businesses) {

		HashMap<String, Double> hmDYelp = General.getHashOfBusinessesValues(
				businesses, true);

		HashMap<String, Double> hmDNotYelp = General.getHashOfBusinessesValues(
				businesses, false);

		hmDYelp = Sorter.sortBusinessValues(hmDYelp);

		hmDNotYelp = Sorter.sortBusinessValues(hmDNotYelp);

		int[] result = new int[businesses.size()];

		HashMap<String, Integer> hmInt = new HashMap<String, Integer>();

		int counter = 0;

		for (String businessId : hmDNotYelp.keySet()) {

			hmInt.put(businessId, counter);

			counter++;

		}
		counter = 0;
		for (String businessId : hmDYelp.keySet()) {

			result[counter] = hmInt.get(businessId);
			
			counter++;

		}

		return result;

	}
	
	
	
	public static int[] getArrayOfLength(int n) {
		
		int[] arr = new int[n];
		
		for (int i = 0 ; i < n; i++) {
			
			arr[i] = i;
			
		}
		
		return arr;
		
	}
	
	
	

}
