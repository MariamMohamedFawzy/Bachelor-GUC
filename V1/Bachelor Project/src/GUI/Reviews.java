package GUI;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import json.Review;
import liuyang.nlp.lda.com.FileUtil;
import liuyang.nlp.lda.com.Stopwords;
import DB.ManageReviews;
import Engine.Engine;
import Engine.WordTopic;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author apple
 */
public class Reviews extends javax.swing.JFrame {

	private boolean allReviews;

	private String category;
	private double lat;
	private double lng;
	boolean considerLocation;

	private WordTopic word = null;

	ArrayList<Review> reviewsArr = null;

	private DefaultTableModel tableModel;

	HashMap<String, Integer> reviewIdToZIndex = null;

	public boolean isAllReviews() {
		return allReviews;
	}

	public void setAllReviews(boolean allReviews) {
		this.allReviews = allReviews;
	}

	public WordTopic getWord() {
		return word;
	}

	public void setWord(WordTopic word) {
		this.word = word;
	}

	Object[][] reviews = { { null, null, null } };

	/**
	 * Creates new form Reviews2
	 */
	public Reviews(String category, double lat, double lng) {
		allReviews = false;
		this.category = category;
		this.lat = lat;
		this.lng = lng;
		initComponents();
		this.setSize(GUIFunctions.getWindowWidth(),
				GUIFunctions.getWindowHeight());
		// updateRowHeights();
		// resizeColumns();
		GUIFunctions.addResizeEvent(this);
	}

	public Reviews(WordTopic word) {
		allReviews = false;
		this.word = word;
		SwingUtilities.invokeLater(doWorkRunnable);
		initComponents();
		this.setSize(GUIFunctions.getWindowWidth(),
				GUIFunctions.getWindowHeight());
		// updateRowHeights();
		// resizeColumns();
		GUIFunctions.addResizeEvent(this);
	}

	public Reviews(boolean allReview, String category, double lat, double lng,
			boolean considerLocation) {
		this.allReviews = allReview;
		this.category = category;
		this.lat = lat;
		this.lng = lng;
		this.considerLocation = considerLocation;
		if (allReview) {
			SwingUtilities.invokeLater(doWorkRunnable);
		}
		initComponents();
		this.setSize(GUIFunctions.getWindowWidth(),
				GUIFunctions.getWindowHeight());
		// updateRowHeights();
		// resizeColumns();
		GUIFunctions.addResizeEvent(this);
	}

	Runnable doWorkRunnable = new Runnable() {
		@Override
		public void run() {
			initReviews();
			// tableModel = new javax.swing.table.DefaultTableModel(reviews,
			// new String[] { "#", "Review", "Useful votes", "Funny votes",
			// "Cool votes", "Stars" });
			MyTableModel myTableModel = new MyTableModel();
			myTableModel.data = reviews;
			jTable1.setModel(myTableModel);
			jTable1.setDefaultRenderer(ReviewTextView.class,
					new ReviewTextRenderer(true));
			jTable1.setDefaultRenderer(StarRater.class, new StarRenderer(true));
			jTable1.setFont(new java.awt.Font("Meiryo", 0, 13));
			tableModel.fireTableDataChanged();
			updateRowHeights();
			resizeColumns();
			System.out.println("reviews length = " + reviews.length);
		}
	};

	private void initReviews() {
		if (allReviews) {
			reviewsArr = ManageReviews.getReviewsByCategoryAndLocation(
					category, lat, lng, considerLocation);
		} else {
			Object[] obj = Engine.getReviewsOfWord(this.word);
			reviewsArr = (ArrayList<Review>) obj[0];
			reviewIdToZIndex = (HashMap<String, Integer>) obj[1];
		}
		reviews = new Object[reviewsArr.size()][3];
		for (int i = 0; i < reviewsArr.size(); i++) {
			Review review = reviewsArr.get(i);

			if (allReviews) {
				reviews[i] = new Object[] { i + 1, 
						 "<html><br><p style=\"padding:10; font-size:13\">" +
						review.getText()
						+ "<br><br></p><hr style=\"color:#493e73;\"><p>"
						+ "<font color='green'>Useful votes : </font>    <font color='#ffd700'>" + review.getVotes().getUseful() + "</font>&nbsp;&nbsp;&nbsp;&nbsp;"
						+ "<font color='blue'>Funny votes : </font>    <font color='#ffd700'>" + review.getVotes().getFunny() + "</font>&nbsp;&nbsp;&nbsp;&nbsp;" 
						+ "<font color='red'>Cool votes : </font>    <font color='#ffd700'>" + review.getVotes().getCool() + "</font>" 
						+"</p><br><br><br><br></html>"
						,
						new StarRater(5, (float)review.getStars(), 0) };
			} else {
				
				int[] z = GUIFunctions.getModel().z[reviewIdToZIndex.get(review
						.getReviewId())];
				int topicNum = word.getTopicNum();

				String textForView = "<html><br><p style=\"padding:10; font-size:13\">";

				ArrayList<String> docLines = new ArrayList<String>();
				ArrayList<String> words = new ArrayList<String>();

				String lines[] = review.getSentimentText().split("\\r?\\n");
				docLines = new ArrayList<String>(Arrays.asList(lines));

				int k = 0;
				int numOfLines = 1;
				for (String line : docLines) {
					FileUtil.tokenizeWithoutLowerCase(line, words);

					// Remove stop words and noise words
						
					for (int j = 0; j < words.size(); j++) {
						
						if (Stopwords.isStopword(words.get(j))
								|| isNoiseWord(words.get(j))) {
							textForView = textForView + words.get(j) + " ";
							if (textForView.length() > 100 * numOfLines) {
								numOfLines ++;
								textForView += "<br>";
							}
							words.remove(j);
							j--;
						} else if (words.get(j).equals("GOODREVIEW")
								|| words.get(j).equals("BADREVIEW")) {
							k++;
						} else {
							if (z[k] == topicNum) {
//								textForView = textForView + "<b>" + "<u>"
//										+ words.get(j) + "</u>" + "</b>" + " ";
								textForView = textForView + "<b><font color='blue'>" + words.get(j) + "</font></b>" + " ";
							} else {
								textForView = textForView + words.get(j) + " ";
							}
							if (textForView.length() > 100 * numOfLines) {
								numOfLines ++;
								textForView += "<br>";
							}
							k++;
						}
					}

					words.clear();
					textForView = textForView + "<br>";
					numOfLines++;

				}
				
				textForView = textForView + "<br><br></p><hr style=\"color:#493e73;\"><p>"
						+ "<font color='green'>Useful votes : </font>    <font color='#ffd700'>" + review.getVotes().getUseful() + "</font>&nbsp;&nbsp;&nbsp;&nbsp;"
						+ "<font color='blue'>Funny votes : </font>    <font color='#ffd700'>" + review.getVotes().getFunny() + "</font>&nbsp;&nbsp;&nbsp;&nbsp;" 
						+ "<font color='red'>Cool votes : </font>    <font color='#ffd700'>" + review.getVotes().getCool() + "</font>" 
						+"</p><br><br><br><br></html>";

				reviews[i] = new Object[] { i + 1,
						new ReviewTextView(textForView),
						new StarRater(5, (float)review.getStars(), 0) };
			}
		}
	}

	public boolean isNoiseWord(String string) {
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

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		jLabelWord = new javax.swing.JLabel();
		jScrollPane1 = new javax.swing.JScrollPane();
		jTable1 = new javax.swing.JTable();
		jMenuBar1 = new javax.swing.JMenuBar();
		jMenu1 = new javax.swing.JMenu();
		jMenuItemRestart = new javax.swing.JMenuItem();
		jMenuItemTopics = new javax.swing.JMenuItem();
		jMenuItemBusinesses = new javax.swing.JMenuItem();
		jMenuItemBackToTopics = new javax.swing.JMenuItem();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		jLabelWord.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
		if (word != null) {
			jLabelWord.setText(word.word);
		} else {
			jLabelWord.setText("Word");
		}

		jTable1.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
		tableModel = new javax.swing.table.DefaultTableModel(reviews,
				new String[] { "#", "Review", "Stars" });
		jTable1.setRowHeight(30);
		jTable1.setModel(tableModel);
		// jTable1.getColumnModel().getColumn(1).setCellRenderer(new
		// ReviewRowRenderer());

		jScrollPane1.setViewportView(jTable1);

		jMenu1.setText("File");

		jMenuItemTopics.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
		jMenuItemTopics.setText("Show topics");
		jMenuItemTopics.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenuItemTopicsActionPerformed(evt);
			}
		});
		jMenuItemBackToTopics.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
		jMenuItemBackToTopics.setText("Back to topics");
		jMenuItemBackToTopics.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenuItemBackToTopicsActionPerformed(evt);
			}
		});
		if (allReviews) {
			jMenu1.add(jMenuItemTopics);
		} else {
			jMenu1.add(jMenuItemBackToTopics);
		}

		jMenuItemRestart.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
		jMenuItemRestart.setText("Restart");
		jMenuItemRestart.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenuItemRestartActionPerformed(evt);
			}
		});
		jMenu1.add(jMenuItemRestart);

		jMenuItemBusinesses.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
		jMenuItemBusinesses.setText("Rank buinesses");
		jMenuItemBusinesses
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						jMenuItemBusinessesActionPerformed(evt);
					}
				});

		if (!allReviews) {
			jMenu1.add(jMenuItemBusinesses);
		}

		jMenuBar1.add(jMenu1);

		setJMenuBar(jMenuBar1);

		// if (allReviews) {
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(jScrollPane1,
								javax.swing.GroupLayout.DEFAULT_SIZE, 586,
								Short.MAX_VALUE).addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				javax.swing.GroupLayout.Alignment.TRAILING,
				layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(jScrollPane1,
								javax.swing.GroupLayout.DEFAULT_SIZE, 297,
								Short.MAX_VALUE).addContainerGap()));

		// } else {
		// javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
		// getContentPane());
		// getContentPane().setLayout(layout);
		// layout.setHorizontalGroup(layout
		// .createParallelGroup(
		// javax.swing.GroupLayout.Alignment.LEADING)
		// .addGroup(
		// layout.createSequentialGroup()
		// .addContainerGap()
		// .addGroup(
		// layout.createParallelGroup(
		// javax.swing.GroupLayout.Alignment.LEADING)
		// .addComponent(
		// jScrollPane1,
		// javax.swing.GroupLayout.Alignment.TRAILING,
		// javax.swing.GroupLayout.DEFAULT_SIZE,
		// 586,
		// Short.MAX_VALUE)
		// .addGroup(
		// javax.swing.GroupLayout.Alignment.TRAILING,
		// layout.createSequentialGroup()
		// .addGap(0,
		// 0,
		// Short.MAX_VALUE)
		// .addComponent(
		// jLabelWord)
		// .addGap(0,
		// 0,
		// Short.MAX_VALUE)))
		// .addContainerGap()));
		// layout.setVerticalGroup(layout
		// .createParallelGroup(
		// javax.swing.GroupLayout.Alignment.LEADING)
		// .addGroup(
		// layout.createSequentialGroup()
		// .addContainerGap()
		// .addComponent(jLabelWord)
		// .addPreferredGap(
		// javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		// .addComponent(
		// jScrollPane1,
		// javax.swing.GroupLayout.DEFAULT_SIZE,
		// 294, Short.MAX_VALUE)
		// .addContainerGap()));
		//
		// }

		pack();
		setLocationRelativeTo(null);
	}// </editor-fold>

	private void updateRowHeights() {
		for (int row = 0; row < jTable1.getRowCount(); row++) {
			int rowHeight = jTable1.getRowHeight();

			Component comp = jTable1.prepareRenderer(
					jTable1.getCellRenderer(row, 1), row, 1);
			rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);

			jTable1.setRowHeight(row, rowHeight);
		}
	}

	private void resizeColumns() {
		float[] columnWidthPercentage = { 10.0f, 80.0f,
				10.0f };
		int tW = jTable1.getWidth();
		TableColumn column;
		TableColumnModel jTableColumnModel = jTable1.getColumnModel();
		int cantCols = jTableColumnModel.getColumnCount();
		for (int i = 0; i < cantCols; i++) {
			column = jTableColumnModel.getColumn(i);
			int pWidth = Math.round(columnWidthPercentage[i] * tW);
			column.setPreferredWidth(pWidth);
		}
	}

	private void jMenuItemRestartActionPerformed(java.awt.event.ActionEvent evt) {
		GUIFunctions.restart(this);
	}

	private void jMenuItemTopicsActionPerformed(java.awt.event.ActionEvent evt) {
		GUIFunctions.showWords(this, reviewsArr);
	}

	private void jMenuItemBusinessesActionPerformed(
			java.awt.event.ActionEvent evt) {
		GUIFunctions.rankBusinesses(word, reviewsArr, this);
	}
	private void jMenuItemBackToTopicsActionPerformed(
			java.awt.event.ActionEvent evt) {
		GUIFunctions.backToTopics(this);
	}
	

	// Variables declaration - do not modify
	private javax.swing.JLabel jLabelWord;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JTable jTable1;
	private javax.swing.JMenu jMenu1;
	private javax.swing.JMenuBar jMenuBar1;
	private javax.swing.JMenuItem jMenuItemRestart;
	private javax.swing.JMenuItem jMenuItemTopics;
	private javax.swing.JMenuItem jMenuItemBusinesses;
	private javax.swing.JMenuItem jMenuItemBackToTopics;

	// End of variables declaration

	class MyTableModel extends AbstractTableModel {

		boolean DEBUG;

		private String[] columnNames = { "#", "Review", "Stars" };

		private Object[][] data;

		public int getColumnCount() {
			return columnNames.length;
		}

		public int getRowCount() {
			return data.length;
		}

		public String getColumnName(int col) {
			return columnNames[col];
		}

		public Object getValueAt(int row, int col) {
			return data[row][col];
		}

		/*
		 * JTable uses this method to determine the default renderer/ editor for
		 * each cell. If we didn't implement this method, then the last column
		 * would contain text ("true"/"false"), rather than a check box.
		 */
		public Class getColumnClass(int c) {
			return getValueAt(0, c).getClass();
		}

		public boolean isCellEditable(int row, int col) {
			// Note that the data/cell address is constant,
			// no matter where the cell appears onscreen.
			// if (col < 1) {
			// return false;
			// } else {
			// return true;
			// }
			return false;
		}

		public void setValueAt(Object value, int row, int col) {
			if (DEBUG) {
				System.out.println("Setting value at " + row + "," + col
						+ " to " + value + " (an instance of "
						+ value.getClass() + ")");
			}

			data[row][col] = value;
			fireTableCellUpdated(row, col);

			if (DEBUG) {
				System.out.println("New value of data:");
				printDebugData();
			}
		}

		private void printDebugData() {
			int numRows = getRowCount();
			int numCols = getColumnCount();

			for (int i = 0; i < numRows; i++) {
				System.out.print("    row " + i + ":");
				for (int j = 0; j < numCols; j++) {
					System.out.print("  " + data[i][j]);
				}
				System.out.println();
			}
			System.out.println("--------------------------");
		}
	}

}
