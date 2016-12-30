package helpers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Sorter {

	public static HashMap<String, Double> sortBusinessValues(
			HashMap<String, Double> businessesValues) {
		
		businessesValues = businessesValues.entrySet().stream()
			    .sorted(Collections.reverseOrder(Comparator.comparing(Entry::getValue)))
			    .collect(Collectors.toMap(Entry::getKey, Entry::getValue,
			          (e1, e2) -> e1, LinkedHashMap::new));
		
		return businessesValues;
	}


//	public static void main(String[] args) {
//		HashMap<String, Double> hm = new HashMap<String, Double>();
//
//		hm.put("1", 1.0);
//
//		hm.put("5", 5.0);
//
//		hm.put("3", 3.0);
//
//		hm.put("4", 4.0);
//
//		hm.put("0", 0.0);
//		
//		
//		System.out.println(hm);
//		
//		hm = sortBusinessValues(hm);
//		
//		System.out.println(hm);
//
//		
//	}

}
