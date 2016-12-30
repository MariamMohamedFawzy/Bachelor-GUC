package Engine;

import helpers.HelperGUI;

import java.util.ArrayList;

import json.Review;
import liuyang.nlp.lda.main.LdaModel;
import DB.ManageReviews;

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
			ArrayList<Review> reviews) {
		return ManageReviews.rankBusinesses(wordTopic, model, reviews);
	}

}
