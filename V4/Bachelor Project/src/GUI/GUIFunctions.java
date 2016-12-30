package GUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import json.JsonToJava;
import json.Review;
import liuyang.nlp.lda.main.Documents;
import liuyang.nlp.lda.main.LdaModel;
import processing.core.PApplet;
import processing.core.PSurface;
import DB.ManageReviews;
import Engine.Engine;
import Engine.WordTopic;
import JXMapClasses.GeocoderExample;
import JXMapClasses.GeocoderSpecficLocation;

public class GUIFunctions {

	public static void start() {
		JsonToJava.start();
	}

	private static Object[] rankedBusinesses;

	public static FirstWindow firstWindowFrame;

	private static ArrayList<Review> wordReviews = null;
	private static ArrayList<Review> allReviews = null;

	private static LdaModel model = null;
	private static Documents docSet;

	private static int windowWidth;
	private static int windowHeight;

	private static int posX;
	private static int posY;
	
	public static String currentCategory = "";

	private static JFrame businessFrame = null;
  
  public static int currentTopic = 0;

	public static void addResizeEvent(Frame frame) {
		frame.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent evt) {
				Component c = (Component) evt.getSource();
				GUIFunctions.setWindowHeight(c.getHeight());
				GUIFunctions.setWindowWidth(c.getWidth());
			}

			public void componentMoved(ComponentEvent evt) {
				Component c = (Component) evt.getSource();
				GUIFunctions.setPosX(c.getX());
				GUIFunctions.setPosY(c.getY());
			}
		});
	}

	public static void showMap() {

		firstWindowFrame.setVisible(false);

		final GeocoderExample mapView = new GeocoderExample();

		GeocoderExample.frame = new JFrame("Geocoder");

		GeocoderExample.frame
				.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		GeocoderExample.frame.add(mapView, BorderLayout.CENTER);
		GeocoderExample.frame.setSize(700, 500);
		GeocoderExample.frame.setLocationRelativeTo(null);
		GeocoderExample.frame.setVisible(true);
	}

	public static void hideMapWithOk() {

		firstWindowFrame.getjTextFieldLat().setText(
				Double.toString(GeocoderExample.getFinalLoc().getLat()));
		firstWindowFrame.getjTextFieldLong().setText(
				Double.toString(GeocoderExample.getFinalLoc().getLng()));

		// GeocoderExample.frame.setVisible(false);
		firstWindowFrame.setVisible(true);
		GeocoderExample.frame.dispose();
	}

	public static void cancelMap() {
		// GeocoderExample.frame.setVisible(false);
		firstWindowFrame.setVisible(true);
		GeocoderExample.frame.dispose();
	}

	public static void backToTopics(JFrame frame) {
		PApplet.main(new String[] { "--bgcolor=#ECE9D8", "GUI.MyTest" });
		frame.dispose();
	}

	public static void hideMapAfterBusinessShow() {
		GeocoderSpecficLocation.frame.dispose();
		Businesses b = new Businesses(getRankedBusinesses());
		b.setVisible(true);
	}

	public static void showSpecificBusiness(JFrame frame, double lat, double lng) {
		businessFrame = frame;
		frame.dispose();
		GeocoderSpecficLocation geo = new GeocoderSpecficLocation(lat, lng);
		GeocoderSpecficLocation.frame = new JFrame("Business at " + lat + ", "
				+ lng);

		GeocoderSpecficLocation.frame
				.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		GeocoderSpecficLocation.frame.add(geo, BorderLayout.CENTER);
		GeocoderSpecficLocation.frame.setSize(getWindowWidth(),
				getWindowHeight());
		GeocoderSpecficLocation.frame.setLocationRelativeTo(null);
		GeocoderSpecficLocation.frame.setVisible(true);
	}

	public static void showWords() {
		firstWindowFrame.disable();
		Runnable doWorkRunnable = new Runnable() {
			@Override
			public void run() {
				double lat;
				double lng;
				String latStr = firstWindowFrame.getjTextFieldLat().getText();
				String lngStr = firstWindowFrame.getjTextFieldLong().getText();
				boolean considerLocation = true;
				try {
					lat = Double.parseDouble(latStr);
				} catch (Exception e) {
					lat = -1;
					considerLocation = false;
				}
				try {
					lng = Double.parseDouble(lngStr);
				} catch (Exception e) {
					lng = -1;
					considerLocation = false;
				}
				if (firstWindowFrame.getjComboBox1().getSelectedItem() != null) {
					// ArrayList<Review> reviews = Engine.filterReviews(
					// firstWindowFrame.getjComboBox1().getSelectedItem()
					// .toString(), lat, lng);
					currentCategory = firstWindowFrame.getjComboBox1()
							.getSelectedItem().toString();
					ArrayList<Review> reviews = ManageReviews
							.getReviewsByCategoryAndLocation(firstWindowFrame
									.getjComboBox1().getSelectedItem()
									.toString(), lng, lat, considerLocation);
					if (reviews.size() > 0) {
						doTopicModelling(reviews);
						PApplet.main(new String[] { "--bgcolor=#ECE9D8",
								"GUI.MyTest" });
						firstWindowFrame.setVisible(false);
					} else {
						firstWindowFrame.enable();
					}
				} else {
					System.out.println("0 reviews");
					firstWindowFrame.enable();
				}
			}
		};
		SwingUtilities.invokeLater(doWorkRunnable);
	}

	public static void showWords(JFrame frame, ArrayList<Review> reviews) {
		if (reviews.size() > 0) {
			frame.disable();
			Runnable doWorkRunnable = new Runnable() {
				@Override
				public void run() {
					doTopicModelling(reviews);
					PApplet.main(new String[] { "--bgcolor=#ECE9D8",
							"GUI.MyTest" });
					frame.setVisible(false);
				}
			};
			SwingUtilities.invokeLater(doWorkRunnable);
		} else {
			System.out.println("0 reviews");
		}
	}

	public static void showReviewsOfWord(PSurface surface, Frame frame,
			WordTopic word) {

		Runnable doWorkRunnable = new Runnable() {
			@Override
			public void run() {
				frame.disable();
				Reviews wordReviews = new Reviews(word);
        currentTopic = word.getTopicNum();
				wordReviews.setVisible(true);
				surface.setVisible(false);
				frame.dispose();
			}
		};
		SwingUtilities.invokeLater(doWorkRunnable);

	}

	public static void showReviewsFromFirstWindow() {
		if (firstWindowFrame.getjComboBox1().getSelectedItem() != null) {
			firstWindowFrame.setVisible(false);
			double lat;
			double lng;
			String latStr = firstWindowFrame.getjTextFieldLat().getText();
			String lngStr = firstWindowFrame.getjTextFieldLong().getText();
			boolean considerLocation = true;
			try {
				lat = Double.parseDouble(latStr);
			} catch (Exception e) {
				lat = -1;
				considerLocation = false;
			}
			try {
				lng = Double.parseDouble(lngStr);
			} catch (Exception e) {
				lng = -1;
				considerLocation = false;
			}
			currentCategory = firstWindowFrame.getjComboBox1()
					.getSelectedItem().toString();
			Reviews allReviews = new Reviews(true, firstWindowFrame
					.getjComboBox1().getSelectedItem().toString(), lat, lng,
					considerLocation);
			allReviews.setVisible(true);
		}
	}

	//
	public static void restart(JFrame frame) {
		firstWindowFrame.enable();
		firstWindowFrame.setSizeAndLocationFromPrev();
		firstWindowFrame.setVisible(true);
		// frame.setVisible(false);
		frame.dispose();
		// firstWindowFrame = new FirstWindow();

	}

	public static void resetFirstWindow() {
		firstWindowFrame.getjComboBox1().setSelectedIndex(-1);
		firstWindowFrame.getjTextFieldLat().setText("");
		firstWindowFrame.getjTextFieldLong().setText("");
	}

	public static void rankBusinesses(WordTopic wordTopic,
			ArrayList<Review> reviews, HashMap<String, Integer> reviewIdToZIndex, JFrame frame) {
		Businesses businessesFrame = new Businesses(wordTopic, reviews, reviewIdToZIndex);
		businessesFrame.setVisible(true);
		frame.dispose();
	}

	public static Object[] doRankingOfBusinesses(WordTopic wordTopic,
			ArrayList<Review> reviews, HashMap<String, Integer> reviewIdToZIndex) {
		return Engine.rankBusinesses(wordTopic, getModel(), reviews, reviewIdToZIndex);
	}

	//
	private static void doTopicModelling(ArrayList<Review> reviews) {
		if (reviews.size() > 0) {
			setAllReviews(reviews);
			Object[] obj = Engine.doTopicModelling(reviews);
			model = (LdaModel) obj[0];
			docSet = (Documents) obj[1];
		}

	}

	//
	public static ArrayList<Review> getWordReviews() {
		return wordReviews;
	}

	public static void setWordReviews(ArrayList<Review> wordReviews) {
		GUIFunctions.wordReviews = wordReviews;
	}

	public static ArrayList<Review> getAllReviews() {
		return allReviews;
	}

	public static void setAllReviews(ArrayList<Review> allReviews) {
		GUIFunctions.allReviews = allReviews;
	}

	public static int getWindowWidth() {
		return windowWidth;
	}

	public static void setWindowWidth(int windowWidth) {
		GUIFunctions.windowWidth = windowWidth;
	}

	public static int getWindowHeight() {
		return windowHeight;
	}

	public static void setWindowHeight(int windowHeight) {
		GUIFunctions.windowHeight = windowHeight;
	}

	public static int getPosX() {
		return posX;
	}

	public static void setPosX(int posX) {
		GUIFunctions.posX = posX;
	}

	public static int getPosY() {
		return posY;
	}

	public static void setPosY(int posY) {
		GUIFunctions.posY = posY;
	}

	public static LdaModel getModel() {
		return model;
	}

	public static void setModel(LdaModel model) {
		GUIFunctions.model = model;
	}

	public static Documents getDocSet() {
		return docSet;
	}

	public static void setDocSet(Documents docSet) {
		GUIFunctions.docSet = docSet;
	}

	public static Object[] getRankedBusinesses() {
		return rankedBusinesses;
	}

	public static void setRankedBusinesses(Object[] rankedBusinesses) {
		GUIFunctions.rankedBusinesses = rankedBusinesses;
	}

}
