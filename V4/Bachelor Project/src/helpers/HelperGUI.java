package helpers;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import json.Review;
import liuyang.nlp.lda.main.Documents;
import liuyang.nlp.lda.main.LdaGibbsSampling;
import liuyang.nlp.lda.main.LdaModel;
import processing.core.PVector;
import DB.ManageReviews;
import Engine.WordTopic;
import GUI.GUIFunctions;

public class HelperGUI {

	public static int numLines = 0;

	public static void purgeDirectory(File dir) {
		for (File file : dir.listFiles()) {
			if (file.isDirectory())
				purgeDirectory(file);
			file.delete();
		}
	}

	public static Object[] doTopicModelling() {
		try {
			return LdaGibbsSampling.prevMain();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Object[] doTopicModelling(ArrayList<Review> reviews) {
//		ArrayList<Review> reviewsWithSentiment = new ArrayList<Review>();
//		HashMap<String, Integer> lookup = new HashMap<String, Integer>();
//		NLP.init();
//		for (Review review : reviews) {
//			Review review2 = new Review();
//			String[] strs = review.getText().split(" ");
//			for (int i = 0; i < strs.length; i++) {
//				int sentiment;
//				if (lookup.containsKey(strs[i])) {
//					sentiment = lookup.get(strs[i]);
//				} else {
//					sentiment = NLP.findSentiment(strs[i]);
//					lookup.put(strs[i], sentiment);
//				}
//				if (sentiment < 2) {
//					strs[i] = strs[i] + " " + "BADREVIEW";
//				} else if (sentiment > 2) {
//					strs[i] = strs[i] + " " + "GOODREVIEW";
//				}
//			}
//			String txt = String.join(" ", strs);
//			review2.setText(txt);
//			review2.setReviewId(review.getReviewId());
//			review2.setBusinessId(review.getBusinessId());
//			reviewsWithSentiment.add(review2);
//		}
//		try {
//			return LdaGibbsSampling.prevMain(reviewsWithSentiment);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		try {
			return LdaGibbsSampling.prevMain(reviews);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static WordTopic[] getWordsOfTopics() {

		ArrayList<WordTopic> words = new ArrayList<WordTopic>();

		LdaModel model = GUIFunctions.getModel();
		int topNum = 20; // Find the top 20 topic words in each topic
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) screenSize.getWidth();
		int height = (int) screenSize.getHeight();
		for (int i = 0; i < model.K; i++) {
			int x = 0;
			int y = 0;
			if (i < model.K / 2) {
				x = (width / 2) + 250 * (i - (model.K / 4));
				y = (height / 2) - 250;
			} else {
				x = (width / 2) + 250 * ((i - (model.K / 2)) - (model.K / 4));
				y = (height / 2) + 100;
			}
			
			int yPos = y;
			int xPos = x;
			List<Integer> tWordsIndexArray = new ArrayList<Integer>();
			for (int j = 0; j < model.V; j++) {
				tWordsIndexArray.add(new Integer(j));
			}
			Collections.sort(tWordsIndexArray, model.new TwordsComparable(
					model.phi[i]));

			for (int t = 0; t < topNum; t++) {
				if (model.docSet2.indexToTermMap.get(tWordsIndexArray.get(t))
						.equals("goodreview")
						|| model.docSet2.indexToTermMap.get(
								tWordsIndexArray.get(t)).equals("badreview")) {
					continue;
				}
				WordTopic wt = new WordTopic(
						model.docSet2.indexToTermMap.get(tWordsIndexArray
								.get(t)),
						(float) model.phi[i][tWordsIndexArray.get(t)], i);
				wt.setProperty("colorType", String.valueOf(i));
				wt.setPlace(new PVector(xPos, yPos));
				words.add(wt);
			}
		}

		WordTopic[] result = new WordTopic[words.size()];

		for (int i = 0; i < words.size(); i++) {
			result[i] = words.get(i);
		}

		test.Writer.writeWordsOfTopic(words);
		
		return result;

	}

	public static Object[] getReviewsOfWord(WordTopic word) {
		Object[] obj = new Object[2];
		ArrayList<Review> result = new ArrayList<Review>();
		HashMap<String, Integer> reviewIdToZIndex = new HashMap<String, Integer>();
		if (word != null) {
			int topicIndex = word.getTopicNum();

			ArrayList<Integer> docsIndex = new ArrayList<Integer>();

			LdaModel model = GUIFunctions.getModel();
			Documents docSet = GUIFunctions.getDocSet();

			for (int i = 0; i < model.z.length; i++) {
//				ArrayList<Integer> arr = new ArrayList(Arrays.asList(model.z[i]));
//
//				if (arr.contains(topicIndex)) {
//					docsIndex.add(i);
//				}

				for (int j = 0; j < model.z[i].length; j++) {
					if (model.z[i][j] == topicIndex) {
//						System.out.println(model.z[i][j] + " " + topicIndex + " "+ i);
						docsIndex.add(i);
						break;
					}
				}
			}

			ArrayList<Review> allReviews = GUIFunctions.getAllReviews();

			ArrayList<String> reviewsIds = new ArrayList<String>();
			
			

			if (docSet.fromReviews) {
				for (int i = 0; i < docsIndex.size(); i++) {
					int docIndex = docsIndex.get(i);
					String docPath = docSet.docs.get(docIndex).docName;
					reviewsIds.add(docPath);
					reviewIdToZIndex.put(docPath, docIndex);
				}
			} else {
				for (int i = 0; i < docsIndex.size(); i++) {
					int docIndex = docsIndex.get(i);
					String docPath = docSet.docs.get(docIndex).docName;
					int lastSlash = docPath.lastIndexOf("/");
					String docId = docPath.substring(lastSlash + 8,
							docPath.length());
					reviewsIds.add(docId);
				}
			}

			System.out.println("all reviews size = " + allReviews.size());
			
			for (Review review : allReviews) {
				if (reviewsIds.contains(review.getReviewId())) {
					result.add(review);
				}
			}
			System.out.println("result size = " + result.size());
			test.Writer.writeReviewsOfTopic(word.getTopicNum(), result);
		} else {
			System.out.println("Word is null");
		}
		
		obj[0] = result;
		obj[1] = reviewIdToZIndex;
		
		return obj;
	}

	public static void rankBusinesses(WordTopic word, ArrayList<Review> reviews, HashMap<String, Integer> reviewIdToZIndex) {
		LdaModel model = GUIFunctions.getModel();

		ManageReviews.rankBusinesses(word, model, reviews, reviewIdToZIndex);

	}

}
