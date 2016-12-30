package test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import json.Business;
import json.Review;
import Engine.WordTopic;

public class Writer {

	public static String fileName = "out.txt";

	public static void writeAllReviewsOfCategoryToFile(String category,
			List<Review> reviews) {
//		try (FileWriter fw = new FileWriter("writeAllReviewsOfCategoryToFile.txt", true);
//				BufferedWriter bw = new BufferedWriter(fw);
//				PrintWriter out = new PrintWriter(bw)) {
//			out.println("All reviews of " + category);
//			HashMap<String, ArrayList<Review>> hm = new HashMap<String, ArrayList<Review>>();
//
//			System.out.println("enter : " + reviews.size());
//			for (Review review : reviews) {
//				ArrayList<Review> arr = null;
//				if (hm.containsKey(review.getBusinessId())) {
//					arr = hm.get(review.getBusinessId());
//				} else {
//					arr = new ArrayList<Review>();
//					hm.put(review.getBusinessId(), arr);
//				}
//				arr.add(review);
//			}
//			for (String key : hm.keySet()) {
//				ArrayList<Review> arr = hm.get(key);
//				out.println("Business # " + key);
////				System.out.println(key);
//				for (int i = 0; i < arr.size(); i++) {
//					Review review = arr.get(i);
////					System.out.println(review.getReviewId());
//					out.println(i + " - " + review.getReviewId() + " -" + review.getText());
//				}
//				out.println();
//			}
//
//			out.println();
//			out.println();
//			out.println();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	public static void writeWordsOfTopic(ArrayList<WordTopic> words) {
//		HashMap<Integer, ArrayList<WordTopic>> hm = new HashMap<Integer, ArrayList<WordTopic>>();
//		for (WordTopic wordTopic : words) {
//			ArrayList<WordTopic> arr = null;
//			if (hm.containsKey(wordTopic.getTopicNum())) {
//				arr = hm.get(wordTopic.getTopicNum());
//			} else {
//				arr = new ArrayList<WordTopic>();
//				hm.put(wordTopic.getTopicNum(), arr);
//			}
//			arr.add(wordTopic);
//		}
//		try (FileWriter fw = new FileWriter("writeWordsOfTopic.txt", true);
//				BufferedWriter bw = new BufferedWriter(fw);
//				PrintWriter out = new PrintWriter(bw)) {
//			out.println("Topics");
//			for (Integer i : hm.keySet()) {
//				out.println("Topic # " + i);
//				ArrayList<WordTopic> arr = hm.get(i);
//				for (WordTopic wordTopic : arr) {
//					out.print(wordTopic.word + " : " + wordTopic.weight + " , ");
//				}
//				out.println();
//			}
//			out.println();
//			out.println();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

	}

	public static void writeReviewsOfTopic(int topicNum,
			ArrayList<Review> reviews) {
//		try (FileWriter fw = new FileWriter("writeReviewsOfTopic.txt", true);
//				BufferedWriter bw = new BufferedWriter(fw);
//				PrintWriter out = new PrintWriter(bw)) {
//
//			out.println("Reviews of topic # " + topicNum);
//
//			HashMap<String, ArrayList<Review>> hm = new HashMap<String, ArrayList<Review>>();
//
//			for (Review review : reviews) {
//				ArrayList<Review> arr = null;
//				if (hm.containsKey(review.getBusinessId())) {
//					arr = hm.get(review.getBusinessId());
//				} else {
//					arr = new ArrayList<Review>();
//					hm.put(review.getBusinessId(), arr);
//				}
//				arr.add(review);
//			}
//
//			for (String key : hm.keySet()) {
//				ArrayList<Review> arr = hm.get(key);
//				out.println("Business # " + key);
//				for (int i = 0; i < arr.size(); i++) {
//					Review review = arr.get(i);
//					out.print(i + " - " + review.getText());
//				}
//				out.println();
//			}
//
//			out.println();
//
//			out.println();
//			out.println();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	public static void writeRankedBusinesses(Object[] obj, int topicNum) {
//		List<Business> businessesList = (List<Business>) obj[0];
//		Map<String, Double> businessesValues = (Map<String, Double>) obj[1];
//
//		if (businessesList != null && businessesValues != null) {
//			try (FileWriter fw = new FileWriter("writeRankedBusinesses.txt", true);
//					BufferedWriter bw = new BufferedWriter(fw);
//					PrintWriter out = new PrintWriter(bw)) {
//				out.println("Ranked businesses of topic # " + topicNum);
//				for (int i = 0; i < businessesList.size(); i++) {
//					Business b = businessesList.get(i);
//					out.println(i + " - id : " + b.getBusinessId()
//							+ ", name : " + b.getName() + ", value : "
//							+ businessesValues.get(b.getBusinessId()));
//				}
//
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
	}

	public static void writeStringToFile(String str) {
//		try (FileWriter fw = new FileWriter(fileName, true);
//				BufferedWriter bw = new BufferedWriter(fw);
//				PrintWriter out = new PrintWriter(bw)) {
//			out.println(str);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

}
