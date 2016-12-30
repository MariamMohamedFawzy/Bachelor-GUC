package GUI;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

import json.Business;
import json.Review;
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
public class Businesses extends javax.swing.JFrame {

	public WordTopic word;
	public ArrayList<Review> reviewsArr;

	private Object[][] tableData;

	private DefaultTableModel tableModel;

	private TableRowSorter<MyTableModel> sorter;

	Object[][] reviews = { { null, null, null, null, null, null, null, null,
			null, null } };

	HashMap<String, Integer> reviewIdToZIndex = null;

	/**
	 * Creates new form Businesses
	 */

	public Businesses(WordTopic word, ArrayList<Review> reviewsArr,
			HashMap<String, Integer> reviewIdToZIndex) {
		this.word = word;
		this.reviewsArr = reviewsArr;
		this.reviewIdToZIndex = reviewIdToZIndex;
		SwingUtilities.invokeLater(doWorkRunnable);
		initComponents();
		this.setSize(GUIFunctions.getWindowWidth(),
				GUIFunctions.getWindowHeight());
		GUIFunctions.addResizeEvent(this);
	}

	Object[] obj = null;

	public Businesses(Object[] obj) {
		this.obj = obj;
		initComponents();
		SwingUtilities.invokeLater(doWorkRunnable);
		this.setSize(GUIFunctions.getWindowWidth(),
				GUIFunctions.getWindowHeight());
		GUIFunctions.addResizeEvent(this);
	}

	Runnable doWorkRunnable = new Runnable() {
		@Override
		public void run() {
			initReviews();
			MyTableModel myTableModel = new MyTableModel();
			myTableModel.data = tableData;
			jTable1.setModel(myTableModel);
			sorter = new TableRowSorter<MyTableModel>(myTableModel);
			jTable1.setRowSorter(sorter);
			jTable1.setDefaultRenderer(StarRater.class, new StarRenderer(true));
			jTable1.setDefaultRenderer(Business.class,
					new BusinessInfoRenderer(true));
			tableModel.fireTableDataChanged();
			updateRowHeights();
			resizeColumns();
		}
	};

	private void initReviews() {
		if (this.obj == null) {
			obj = GUIFunctions.doRankingOfBusinesses(word, reviewsArr,
					reviewIdToZIndex);
		}
		List<Business> businessesList = (List<Business>) obj[0];
		Map<String, Business> businessesMap = new HashMap<String, Business>();
		Map<String, Double> businessesValues = (Map<String, Double>) obj[1];
		calcStattistics(businessesValues);
		for (Business business : businessesList) {
			businessesMap.put(business.getBusinessId(), business);
		}
		double minValue = Integer.MAX_VALUE;
		double maxValue = Integer.MIN_VALUE;
		for (Double value : businessesValues.values()) {
			if (value < minValue) {
				minValue = value;
			}
			if (value > maxValue) {
				maxValue = value;
			}
		}

		double rmse = 0;

		if (businessesList != null && businessesValues != null) {
			tableData = new Object[businessesList.size()][];
			int i = businessesValues.size() - 1;
			for (String businessId : businessesValues.keySet()) {
				Business business = businessesMap.get(businessId);
				double rating = (5 * (maxValue - minValue) + 4
						* businessesValues.get(business.getBusinessId()) - 4 * maxValue)
						/ (maxValue - minValue);
				rating = Math.round(rating * 100.0) / 100.0;
				rmse += Math.pow((rating - business.getStars()), 2);
				double value = Math.round(businessesValues.get(business
						.getBusinessId()) * 100.0) / 100.0;
				business.setNewStar(rating);
				business.getTopicsValues().put(GUIFunctions.currentTopic,
						businessesValues.get(business.getBusinessId()));
				tableData[i] = new Object[] {
						i + 1,
						business,
						businessesValues.get(business.getBusinessId()),
						new StarRater(5, Float.valueOf(String.valueOf(business
								.getStars())), 0),
						new StarRater(5, (float) rating, 0)};

				i--;
			}
			rmse = Math.sqrt(rmse / businessesList.size());

			writeStatistics(businessesList, rmse);
		} else {
			tableData = new Object[][] { { null, null, null, null } };
		}
	}

	private void writeStatistics(List<Business> businessesList, double rmse) {
		FileOutputStream fout;
		ObjectOutputStream oos;

		new File("eval/" + GUIFunctions.currentCategory + "/"
				+ GUIFunctions.currentTopic).mkdirs();

		try {
			fout = new FileOutputStream("eval/" + GUIFunctions.currentCategory
					+ "/" + GUIFunctions.currentTopic + "/businesses" + ".ser");
			oos = new ObjectOutputStream(fout);
			oos.writeObject(businessesList);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			PrintWriter writer = new PrintWriter("eval/"
					+ GUIFunctions.currentCategory + "/"
					+ GUIFunctions.currentTopic + "/rmse" + ".txt", "UTF-8");
			writer.println(rmse);
			writer.close();
		} catch (IOException e) {
			// do something
		}
	}

	public void calcStattistics(Map<String, Double> businessesValues) {
		double max = Double.MIN_VALUE;
		double min = Double.MAX_VALUE;
		double variance = 0;
		double sum = 0;
		for (String key : businessesValues.keySet()) {
			Double value = businessesValues.get(key);
			sum += value;
			if (value < min) {
				min = value;
			}
			if (value > max) {
				max = value;
			}
		}
		double mean = sum / businessesValues.size();
		for (String key : businessesValues.keySet()) {
			Double value = businessesValues.get(key);
			variance += Math.pow(value - mean, 2);
		}
		variance = variance / businessesValues.size();

		System.out.println("max = " + max);
		System.out.println("min = " + min);
		System.out.println("mean = " + mean);
		System.out.println("variance = " + variance);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		setTitle("Businesses");

		jLabelWord = new javax.swing.JLabel();
		jScrollPane1 = new javax.swing.JScrollPane();
		jTable1 = new javax.swing.JTable();
		jMenuBar1 = new javax.swing.JMenuBar();
		jMenu1 = new javax.swing.JMenu();
		jMenuItemRestart = new javax.swing.JMenuItem();
		jMenuItemBackToTopics = new javax.swing.JMenuItem();

		JLabel l1 = new JLabel("Filter Business:", SwingConstants.TRAILING);

		filterText = new JTextField();
		// Whenever filterText changes, invoke newFilter.
		filterText.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				newFilter();
			}

			public void insertUpdate(DocumentEvent e) {
				newFilter();
			}

			public void removeUpdate(DocumentEvent e) {
				newFilter();
			}
		});
		l1.setLabelFor(filterText);

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		jLabelWord.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
		if (word != null) {
			jLabelWord.setText(word.word);
		} else {
			jLabelWord.setText("Word");
		}

		jTable1.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
		tableModel = new javax.swing.table.DefaultTableModel(reviews,
				new String[] { "#", "Business", "Value", "Yelp rating",
						"Rating" });
		jTable1.setRowHeight(30);
		jTable1.setModel(tableModel);
		jTable1.setDefaultRenderer(StarRater.class, new StarRenderer(true));
		// jTable1.getColumnModel().getColumn(1).setCellRenderer(new
		// ReviewRowRenderer());

		jScrollPane1.setViewportView(jTable1);

		jMenu1.setText("File");

		jMenuItemRestart.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
		jMenuItemRestart.setText("Restart");
		jMenuItemRestart.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenuItemRestartActionPerformed(evt);
			}
		});
		jMenuItemBackToTopics.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
		jMenuItemBackToTopics.setText("Back to topics");
		jMenuItemBackToTopics
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						jMenuItemBackToTopicsActionPerformed(evt);
					}
				});

		jMenu1.add(jMenuItemRestart);
		jMenu1.add(jMenuItemBackToTopics);

		jMenuBar1.add(jMenu1);

		setJMenuBar(jMenuBar1);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(
														jScrollPane1,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														586, Short.MAX_VALUE)
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		l1)
																.addGap(18, 18,
																		18)
																.addComponent(
																		filterText)))
								.addContainerGap()));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(jScrollPane1,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										271, Short.MAX_VALUE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(l1)
												.addComponent(
														filterText,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addContainerGap()));

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

	/**
	 * Update the row filter regular expression from the expression in the text
	 * box.
	 */
	private void newFilter() {
		RowFilter<Object, Object> startsWithAFilter = new RowFilter<Object, Object>() {
			public boolean include(
					Entry<? extends Object, ? extends Object> entry) {
				Business b = (Business) entry.getValue(1);
				if (b.getName().toLowerCase()
						.startsWith(filterText.getText().toLowerCase())) {
					return true;
				}
				return false;
			}
		};
		sorter.setRowFilter(startsWithAFilter);
	}

	private void resizeColumns() {
		float[] columnWidthPercentage = { 10.0f, 40.0f, 10.0f, 20.0f, 20.0f};
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
	private javax.swing.JMenuItem jMenuItemBackToTopics;
	private JTextField filterText;

	// End of variables declaration

	class MyTableModel extends AbstractTableModel {

		boolean DEBUG;

		private String[] columnNames = { "#", "Business", "Value",
				"Yelp rating", "Rating" };

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
