package helpers;

import java.util.HashMap;

import nlp.NLP;

public class HelperLDA {
	public static HashMap<String, Integer> lookup = new HashMap<String, Integer>();

	public static void init() {
		NLP.init();
		lookup = new HashMap<String, Integer>();
	}

	public static String getStringWithSentiment(String text) {
		String[] splitted = text.split(" ");
		for (int i = 0; i < splitted.length; i++) {
			int sentiment;
			if (lookup.containsKey(splitted[i])) {
				sentiment = lookup.get(splitted[i]);
			} else {
				sentiment = NLP.findSentiment(splitted[i]);
				lookup.put(splitted[i], sentiment);
			}
			if (sentiment < 2) {
				splitted[i] = splitted[i] + " " + "BADREVIEW";
			} else if (sentiment > 2) {
				splitted[i] = splitted[i] + " " + "GOODREVIEW";
			}
		}
		return String.join(" ", splitted);
	}

}
