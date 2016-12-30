package Main;

import helpers.General;
import helpers.JoinTopicsHelper;

import java.util.ArrayList;
import java.util.List;

import json.Business;
import DCG.NDCG;
import KendallTau.KendallTau;
import files.Reader;

public class Main {

	static String[] categories = { "Sports Clubs"
		, "Accessories",
			"Donuts"
		};

	public static void calculateIDCG(String category, String topic,
			String version1, String version2) {

		List<Business> businessesV1 = Reader.readBusinesses(version1, category,
				topic);

		List<Business> businessesV2 = Reader.readBusinesses(version2, category,
				topic);

		// ArrayList<Business> businessesV1_1 = General.getBusinessSorted(
		// businessesV1, false);
		//
		// ArrayList<Business> businessesV2_1 = General.getBusinessSorted(
		// businessesV2, false);

		double ndcgV1 = NDCG.calculateNDCG((ArrayList<Business>) businessesV1);

		double ndcgV2 = NDCG.calculateNDCG((ArrayList<Business>) businessesV2);

		System.out.println("NDCG of v" + version1 + " = " + ndcgV1);

		System.out.println("NDCG of v" + version2 + " = " + ndcgV2);

	}

	public static void calculateIDCG(String category, String version1,
			String version2) {

		ArrayList<Business> businessesV1_1 = JoinTopicsHelper.joinBusinesses(
				category, version1);

		ArrayList<Business> businessesV2_1 = JoinTopicsHelper.joinBusinesses(
				category, version2);

		double ndcgV1 = NDCG.calculateNDCG(businessesV1_1);

		double ndcgV2 = NDCG.calculateNDCG(businessesV2_1);

		System.out.println("NDCG of v" + version1 + " = " + ndcgV1);

		System.out.println("NDCG of v" + version2 + " = " + ndcgV2);

	}

	public static void getRMSE(String category, String topic, String version1,
			String version2) {

		double rmseV1 = Reader.readRMSE(version1, category, topic);

		double rmseV2 = Reader.readRMSE(version2, category, topic);

		System.out.println("RMSE of v" + version1 + " = " + rmseV1);

		System.out.println("RMSE of v" + version2 + " = " + rmseV2);

	}

	public static void calculateKendalTau(String category, String topic,
			String version1, String version2) {

		List<Business> businessesV1 = Reader.readBusinesses(version1, category,
				topic);

		List<Business> businessesV2 = Reader.readBusinesses(version2, category,
				topic);

		int v1Arr[] = General.getIntegerArrayOfBusinesses(businessesV1);

		int v2Arr[] = General.getIntegerArrayOfBusinesses(businessesV2);

		int[] yelpRating = General.getArrayOfLength(businessesV1.size());

		long v1 = KendallTau.distance(yelpRating, v1Arr);

		yelpRating = General.getArrayOfLength(businessesV2.size());

		long v2 = KendallTau.distance(yelpRating, v2Arr);

		System.out.println("Number of inversions in v" + version1 + " = " + v1);

		System.out.println("Number of inversions in v" + version2 + " = " + v2);

	}

	public static void calculateKendalTau(String category, String version1,
			String version2) {

		ArrayList<Business> businessesV1 = JoinTopicsHelper.joinBusinesses(
				category, version1);

		ArrayList<Business> businessesV2 = JoinTopicsHelper.joinBusinesses(
				category, version2);

		int v1Arr[] = General.getIntegerArrayOfBusinesses(businessesV1);

		int v2Arr[] = General.getIntegerArrayOfBusinesses(businessesV2);

		int[] yelpRating = General.getArrayOfLength(businessesV1.size());

		long v1 = KendallTau.distance(yelpRating, v1Arr);

		yelpRating = General.getArrayOfLength(businessesV2.size());

		long v2 = KendallTau.distance(yelpRating, v2Arr);

		System.out.println("Number of inversions in v" + version1 + " = " + v1);

		System.out.println("Number of inversions in v" + version2 + " = " + v2);

	}

	public static void main(String[] args) {

		String version1 = "3";
		String version2 = "4";

		for (int i = 0; i < categories.length; i++) {

			String categoryName = categories[i];

			for (int j = 0; j < 10; j++) {

				System.out.println("Category : " + categoryName + " , topic : "
						+ j);

				System.out.println("RMSE");

				getRMSE(categoryName, String.valueOf(j), version1, version2);

				System.out.println("NDCG");

				calculateIDCG(categoryName, String.valueOf(j), version1,
						version2);

				System.out.println("Kendall Tau");

				calculateKendalTau(categoryName, String.valueOf(j), version1,
						version2);

				System.out.println();

				System.out.println();

			}

			// joined

			System.out.println("After Joining");

			System.out.println();

			System.out.println("NDCG");

			calculateIDCG(categoryName, version1, version2);

			System.out.println();

			System.out.println("Kendall Tau");

			calculateKendalTau(categoryName, version1, version2);

		}

	}

}
