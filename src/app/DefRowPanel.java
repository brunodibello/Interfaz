package app;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class DefRowPanel extends JPanel {
	
	private List<DefRow> defRows;
	
	private GridBagConstraints gc;
	
	public DefRowPanel() {
		
		defRows = new ArrayList<DefRow>();
		
		setLayout(new GridBagLayout());
		
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.NONE;	
		gc.gridy = 0;
		gc.weightx = 1;
		gc.weighty = 1;
		gc.gridwidth = 1;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_START;
		//gc.insets = new Insets(0, 0, 0, 0);
		
	}
	
	public List<DefRow> getDefRows() {
		return this.defRows;
	}
	
	public void addDefRow() {
		
		int lastGridY = gc.gridy;
		
		DefRow defRow = new DefRow();
		defRows.add(defRow);
		
		int gridy = gc.gridy;
		
		gc.gridx = 0;
		
		gc.insets = new Insets(0, 0, 0, 170);
		add(defRow.getAsientoField(), gc);
		
		gc.insets = new Insets(0, 0, 0, 250);
		
		gc.gridx++;
		for (JComboBox combBox : defRow.getDebeFields()) {
			add(combBox, gc);
			gc.gridy++;
		}
		if (gc.gridy > lastGridY) {
			lastGridY = gc.gridy;
		}
		gc.gridy = gridy;
		
		gc.gridx++;
		for (JComboBox combBox : defRow.getHaberFields()) {
			add(combBox, gc);
			gc.gridy++;
		}
		if (gc.gridy > lastGridY) {
			lastGridY = gc.gridy;
		}
		
		gc.gridy = lastGridY;
		
		this.revalidate();
		
	}
	
	public void removeDefRow() {
		if (this.defRows.size() > 0) {
			removeLastRow();
			defRows.remove(defRows.size()-1);
			revalidate();
		}
	}
	
	public void addDebe() {
		defRows.get(defRows.size()-1).addDebe();
		removeLastRow();
		addLastRow();
		revalidate();
	}
	
	public void addHaber() {
		defRows.get(defRows.size()-1).addHaber();
		removeLastRow();
		addLastRow();
		revalidate();
	}
	
	private void removeLastRow() {
		DefRow defRow = defRows.get(defRows.size()-1);
		remove(defRow.getAsientoField());
		for (JComboBox cb : defRow.getDebeFields()) {
			remove(cb);
		}
		for (JComboBox cb : defRow.getHaberFields()) {
			remove(cb);
		}
	}
	
	private void addLastRow() {
		DefRow defRow = defRows.get(defRows.size()-1);
		int lastGridY = gc.gridy;
		int gridy = gc.gridy;
		
		gc.gridx = 0;
		
		gc.insets = new Insets(0, 0, 0, 170);
		add(defRow.getAsientoField(), gc);
		
		gc.insets = new Insets(0, 0, 0, 250);
		
		gc.gridx++;
		for (JComboBox combBox : defRow.getDebeFields()) {
			add(combBox, gc);
			gc.gridy++;
		}
		if (gc.gridy > lastGridY) {
			lastGridY = gc.gridy;
		}
		gc.gridy = gridy;
		
		gc.gridx++;
		for (JComboBox combBox : defRow.getHaberFields()) {
			add(combBox, gc);
			gc.gridy++;
		}
		if (gc.gridy > lastGridY) {
			lastGridY = gc.gridy;
		}
		
		gc.gridy = lastGridY;
		
		this.revalidate();
	}

}
