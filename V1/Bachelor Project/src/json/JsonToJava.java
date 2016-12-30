package json;

import helpers.HelperManageReviews;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import DB.ManageReviews;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

public class JsonToJava {

	public static void start() {
		if (isTheFirstTime()) {
			saveBusinesses();
			int[] reviewsLength = saveReviews();
			updateReviewSentimentText(reviewsLength);
		}
	}


	public static ArrayList<Business> readBusinesses() {
		return ManageReviews.readBusinesses();
	}

	public static Object[] readCategories() {
		ArrayList<String> categories = HelperManageReviews
				.getDistinctCategories();
		return categories.toArray();
	}

	private static int[] saveReviews() {
		int[] reviewsLength = new int[15];
		reviewsLength[0] = 1;
		int counter = saveReviews( "src/reviews/xaa", 1);
		reviewsLength[1] = counter;
		counter = saveReviews( "src/reviews/xab", counter);
		reviewsLength[2] = counter;
		counter = saveReviews( "src/reviews/xac", counter);
		reviewsLength[3] = counter;
		counter = saveReviews( "src/reviews/xad", counter);
		reviewsLength[4] = counter;
		counter = saveReviews( "src/reviews/xae", counter);
		reviewsLength[5] = counter;
		counter = saveReviews( "src/reviews/xaf", counter);
		reviewsLength[6] = counter;
		counter = saveReviews( "src/reviews/xag", counter);
		reviewsLength[7] = counter;
		counter = saveReviews( "src/reviews/xah", counter);
		reviewsLength[8] = counter;
		counter = saveReviews( "src/reviews/xai", counter);
		reviewsLength[9] = counter;
		counter = saveReviews( "src/reviews/xaj", counter);
		reviewsLength[10] = counter;
		counter = saveReviews( "src/reviews/xak", counter);
		reviewsLength[11] = counter;
		counter = saveReviews( "src/reviews/xal", counter);
		reviewsLength[12] = counter;
		counter = saveReviews( "src/reviews/xam", counter);
		reviewsLength[13] = counter;
		counter = saveReviews( "src/reviews/xan", counter);
		reviewsLength[14] = counter;
		System.out.println("counter = " + counter);
		return reviewsLength;
	}
	
	public static void updateReviewSentimentText(int[] reviewsLength) {
		ManageReviews.updateReviewSentimentText(reviewsLength);
	}
	
	private static int saveReviews(String filePath, int counter) {
		ArrayList<Review> reviews = new ArrayList<Review>();
		try {
			for (String line : Files.readAllLines(Paths.get(filePath))) {
				Gson gson = new GsonBuilder().create();
				Review review = gson.fromJson(line, Review.class);
				reviews.add(review);
			}
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ManageReviews.saveReviews(reviews, counter);
		return counter + reviews.size();
	}

	private static void saveBusinesses() {
		ArrayList<Business> businesses = new ArrayList<Business>();
		String businessFilePath = "src/yelp_academic_dataset_business.json";
		try {
			for (String line : Files.readAllLines(Paths.get(businessFilePath))) {
				Gson gson = new GsonBuilder().create();
				Business business = gson.fromJson(line, Business.class);
				businesses.add(business);
			}
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("businesses = " + businesses.size());

		ManageReviews.saveBusinesses(businesses);
	}

	private static boolean isTheFirstTime() {
		boolean flag = false;
		BufferedReader br = null;
		String filePath = "startProject/start.txt";
		try {
			br = new BufferedReader(new FileReader(filePath));

			String line = br.readLine();

			if (line.equals("true")) {
				flag = true;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if (flag) {
			try {
				PrintWriter writer = new PrintWriter(filePath, "UTF-8");
				writer.println("false");
				writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return flag;
	}

	

}
