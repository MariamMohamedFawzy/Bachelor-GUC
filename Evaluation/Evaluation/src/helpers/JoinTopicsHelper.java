package helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import json.Business;
import files.Reader;

public class JoinTopicsHelper {

	public static ArrayList<Business> joinBusinesses(String category, String version) {
		
		ArrayList<Business> arr = new ArrayList<Business>();

		HashMap<String, Double> hmValues = new HashMap<String, Double>();

		HashMap<String, Business> hmBusinesses = new HashMap<String, Business>();

		for (int i = 0; i < 10; i++) {

			List<Business> businessList = Reader.readBusinesses(version,
					category, String.valueOf(i));
			
			joinTwolists(businessList, hmValues, hmBusinesses);

		}
		
		double minValue = Double.MAX_VALUE;
		
		double maxValue = Double.MIN_VALUE;
		
		for (String str : hmValues.keySet()) {
			
			double value = hmValues.get(str);
			
			if (value > maxValue) {
				maxValue = value;
			}
			
			if (value < minValue) {
				minValue = value;
			}
			
		}
		
//		hmValues = Sorter.sortBusinessValues(hmValues);
		
		for (String str : hmBusinesses.keySet()) {
			
			Business b = hmBusinesses.get(str);
			
			double rating = (5 * (maxValue - minValue) + 4
					* hmValues.get(b.getBusinessId()) - 4 * maxValue)
					/ (maxValue - minValue);
			
			rating = Math.round(rating * 100.0) / 100.0;
			
			b.setNewStar(rating);
		}
		
		
		for (String str : hmBusinesses.keySet()) {
			
			arr.add(hmBusinesses.get(str));
			
		}
		
		return arr;

	}

	private static void joinTwolists(List<Business> businessList,
			HashMap<String, Double> hmValues,
			HashMap<String, Business> hmBusinesses) {

		for (Business business : businessList) {

			double value = 0;

			hmBusinesses.putIfAbsent(business.getBusinessId(), business);
			
			hmValues.putIfAbsent(business.getBusinessId(), 0.0);

			value = hmValues.getOrDefault(business.getBusinessId(), 0.0);

			for (Integer k : business.getTopicsValues().keySet()) {

				value += business.getTopicsValues().get(k);

			}

			hmValues.replace(business.getBusinessId(), value);

		}

	}

}
