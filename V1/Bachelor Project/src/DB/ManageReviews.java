package DB;

import helpers.HelperManageReviews;

import java.util.ArrayList;

import json.Business;
import json.Review;
import liuyang.nlp.lda.main.LdaModel;
import Engine.WordTopic;

public class ManageReviews {

	public static void saveReviews(ArrayList<Review> reviews) {
		HelperManageReviews.saveReviews(reviews);
	}
	
	public static void saveReviews(ArrayList<Review> reviews, int startCounter) {
		HelperManageReviews.saveReviews(reviews, startCounter);
	}
	
	public static void updateReviewSentimentText(int[] reviewsLength) {
		HelperManageReviews.updateReviewSentimentText(reviewsLength);
	}

	public static void saveBusinesses(ArrayList<Business> businesses) {
		HelperManageReviews.saveBusinesses(businesses);
	}

	public static ArrayList<Business> readBusinesses() {
		return HelperManageReviews.readBusinesses();
	}

	public static ArrayList<String> readCategories() {
		return HelperManageReviews.getDistinctCategories();
	}

	public static ArrayList<Review> getReviewsByCategoryAndLocation(
			String category, double lng, double lat, boolean considerLocation) {
		return HelperManageReviews.getReviewsByCategoryAndLocation(category,
				lng, lat, considerLocation);
	}
	
	public static Object[] rankBusinesses(WordTopic wordTopic, LdaModel model,
			ArrayList<Review> reviews) {
		return HelperManageReviews.rankBusinesses(wordTopic, model, reviews);
	}

}
