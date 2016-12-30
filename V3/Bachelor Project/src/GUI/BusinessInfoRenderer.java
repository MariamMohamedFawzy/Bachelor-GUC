package GUI;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;

import json.Business;

public class BusinessInfoRenderer extends JLabel implements TableCellRenderer {
	Border unselectedBorder = null;
	Border selectedBorder = null;
	boolean isBordered = true;

	public BusinessInfoRenderer(boolean isBordered) {
		this.isBordered = isBordered;
		setOpaque(true); // MUST do this for background to show up.
	}

	public Component getTableCellRendererComponent(JTable table,
			Object business, boolean isSelected, boolean hasFocus, int row,
			int column) {
		Business newBusiness = (Business) business;

		JLabel jlabelName = new JLabel(newBusiness.getName());
		StarRater starRater = new StarRater(5, Float.valueOf(String
				.valueOf(newBusiness.getStars())), 0);
		JLabel jLabelFullAddress = new JLabel(newBusiness.getFullAddress());
		JLabel jLabelCity = new JLabel("<html><b>" + newBusiness.getCity() + "</b></html>");
		
		String info = "<html><p style=\"padding:10; font-size:13\">";
		info += "<br>";
		info += "<b>&nbsp;<font color='#5e3b6c'>" + newBusiness.getName() + "</font></b>";
		info += "<br>";
		info += "<br>";
		info += "&nbsp;<font color='#805194'>" + newBusiness.getCity() + ",</font>&nbsp;" + newBusiness.getFullAddress();
		info += "<br>";
		info += "<br>";
		info  += "</p></html>";
		
		setText(info);
		
		setFont(new java.awt.Font("Meiryo", 0, 13));
		
		setHorizontalAlignment(LEFT);
		

		if (isBordered) {
			if (isSelected) {
				if (selectedBorder == null) {
					selectedBorder = BorderFactory.createMatteBorder(2, 5, 2,
							5, table.getSelectionBackground());
				}
				setBorder(selectedBorder);
			} else {
				if (unselectedBorder == null) {
					unselectedBorder = BorderFactory.createMatteBorder(2, 5, 2,
							5, table.getBackground());
				}
				setBorder(unselectedBorder);
			}
		}

		return this;
	}

}
