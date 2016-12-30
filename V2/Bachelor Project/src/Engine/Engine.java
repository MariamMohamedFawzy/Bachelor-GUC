package Engine;

import helpers.HelperGUI;
import helpers.HelperManageReviews;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import DB.ManageReviews;
import json.Business;
import json.JsonToJava;
import json.Review;
import wordcram.Word;
import liuyang.nlp.lda.main.LdaModel;

public class Engine {


	public static Object[] doTopicModelling(ArrayList<Review> reviews) {

//		HelperGUI.writeReviewsToFiles(reviews);

		return HelperGUI.doTopicModelling(reviews);

	}

	public static WordTopic[] getWordsOfTopics() {

		return HelperGUI.getWordsOfTopics();

	}

	public static Object[] getReviewsOfWord(WordTopic word) {
		
		return HelperGUI.getReviewsOfWord(word);
		
	}
	
	public static Object[] rankBusinesses(WordTopic wordTopic, LdaModel model,
			ArrayList<Review> reviews, HashMap<String, Integer> reviewIdToZIndex) {
		return ManageReviews.rankBusinesses(wordTopic, model, reviews, reviewIdToZIndex);
	}

}
