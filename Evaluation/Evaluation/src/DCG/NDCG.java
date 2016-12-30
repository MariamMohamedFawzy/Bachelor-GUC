package DCG;

// https://github.com/learning-layers/SocRec/blob/master/code/solr-resource-recommender-framework/src/main/java/at/knowcenter/recommender/solrpowered/evaluation/metrics/NDCG.java
// changed

import helpers.General;

import java.util.ArrayList;

import json.Business;

/**
 * Calculates the nDCG measure on the recommended resources based on expecting resources
 * 
 * @author Emanuel Lacic
 */
public class NDCG {

	
	
	public static double calculateNDCG(ArrayList<Business> data) {
		double dcg = 0;
		
		ArrayList<Business> businesses = General.getBusinessSorted(data, true);
		
		ArrayList<Business> businessesPredicted = General.getBusinessSorted(data, false);
		
		double idcg = calculateIDCG(businesses);

		if (idcg == 0) {
			return 0;
		}

		for (int i = 0; i < businessesPredicted.size(); i++) {
			Business b = businessesPredicted.get(i);

			// compute NDCG part
			int rank = i + 1;

			dcg += (Math.pow(2, b.getStars()) - 1.0) * (Math.log(2) / Math.log(rank + 1));
		}

		return dcg / idcg;
	}
	
	public static double calculateIDCG(ArrayList<Business> businesses) {
		double idcg = 0;
		
		for (int i = 0; i < businesses.size(); i++){
			Business b = businesses.get(i);
			idcg += (Math.pow(2, b.getStars()) - 1.0) * ( Math.log(2) / Math.log(i + 2) );
		}

		return idcg;
	}

	

}