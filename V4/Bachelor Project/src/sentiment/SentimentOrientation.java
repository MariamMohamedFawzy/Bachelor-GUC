package sentiment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.vividsolutions.jts.geomgraph.Position;

import GUI.GUIFunctions;
import json.Review;
import liuyang.nlp.lda.com.FileUtil;
import liuyang.nlp.lda.com.Stopwords;

public class SentimentOrientation {
	
	public static void init() {
		POSTaggerStemmerWithSentiment.init();
	}

	public static double getSentimentOrientationOfReview(Review review,
			int topicNum, HashMap<String, Integer> reviewIdToZIndex) {
		ArrayList<Double> positives = new ArrayList<Double>();
		ArrayList<Double> negatives = new ArrayList<Double>();

		String text = getReviewWithTopics(review, topicNum, reviewIdToZIndex);
		
		Object[] obj = POSTaggerStemmerWithSentiment.getSentimentOfSentences(text);
		
		double sum = 0;
		
		double[] sentimentPos = (double[]) obj[0];
		double[] sentimentNeg = (double[]) obj[1];
		String[] words = (String[]) obj[2];
		String[] lemmas = (String[]) obj[3];
		
//		POSTaggerStemmerWithSentiment.printResult(obj);
		
		for (int i = 0; i < words.length; i++) {
			if (words[i].equals("§")) {
				i++;
				while (i < words.length) {
					if (words[i].equals("§")) {
						break;
					}
					double pos = sentimentPos[i];
					double neg = sentimentNeg[i];
					
					i++;
					
					if ((1 - (pos + neg)) >= 0.3 || pos == neg) {
						continue;
					}

					sum += ((pos - neg) * Math.max(pos, neg)) / (Math.abs(pos - neg) * (pos + neg));
					
				}	
			}
		}
		
		return sum;

	}
	
	public static void close() {
		POSTaggerStemmerWithSentiment.close();
	}

	private  static String getReviewWithTopics(Review review, int topicNum,
			HashMap<String, Integer> reviewIdToZIndex) {
		int[] z = GUIFunctions.getModel().z[reviewIdToZIndex.get(review
				.getReviewId())];

		ArrayList<String> docLines = new ArrayList<String>();
		ArrayList<String> words = new ArrayList<String>();

		String lines[] = review.getSentimentText().split("\\r?\\n");
		docLines = new ArrayList<String>(Arrays.asList(lines));

		String textForView = "";

		int k = 0;
		for (String line : docLines) {
			FileUtil.tokenizeWithoutLowerCase(line, words);

			// Remove stop words and noise words

			for (int j = 0; j < words.size(); j++) {
				if (Stopwords.isStopword(words.get(j))
						|| isNoiseWord(words.get(j))) {
					textForView = textForView + words.get(j) + " ";
					words.remove(j);
					j--;
				} else {
					if (k < z.length && z[k] == topicNum) {
						textForView = textForView + "§ " + words.get(j)
								+ " § ";
					} else {
						textForView = textForView + words.get(j) + " ";
					}
					k++;
				}
			}
			words.clear();
		}
		

		return textForView;
	}

	private static boolean isNoiseWord(String string) {
		// TODO Auto-generated method stub
		string = string.toLowerCase().trim();
		Pattern MY_PATTERN = Pattern.compile(".*[a-zA-Z]+.*");
		Matcher m = MY_PATTERN.matcher(string);
		// filter @xxx and URL
		if (string.matches(".*www\\..*") || string.matches(".*\\.com.*")
				|| string.matches(".*http:.*"))
			return true;
		if (!m.matches()) {
			return true;
		} else
			return false;
	}

}
