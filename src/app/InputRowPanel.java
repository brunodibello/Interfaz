package app;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InputRowPanel extends JPanel {
	
	private List<InputRow> inputRows;
	
	private GridBagConstraints gc;
	
	public InputRowPanel() {
		
		inputRows = new ArrayList<InputRow>();
		
		setLayout(new GridBagLayout());
		
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.NONE;	
		gc.gridy = 0;
		gc.weightx = 1;
		gc.weighty = 1;
		gc.gridx = 0;
		gc.gridwidth = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		//gc.insets = new Insets(0, 0, 0, 0);
		
	}
	
	public List<InputRow> getInputRows() {
		return this.inputRows;
	}
	
	public void addInputRow() {
		
		int lastGridY = gc.gridy;
		
		InputRow inputRow = new InputRow();
		inputRows.add(inputRow);
		
		int gridy = gc.gridy;
		
		gc.gridx = 0;
		add(inputRow.getAsientoField(), gc);
		
		
		
		gc.gridx++;
		add(inputRow.getFechaField(), gc);
		gc.insets = new Insets(0, 0, 0, 50);
		gc.gridx++;
		for (JComboBox combBox : inputRow.getDebeFields()) {
			add(combBox, gc);
			gc.gridy++;
		}
		if (gc.gridy > lastGridY) {
			lastGridY = gc.gridy;
		}
		gc.gridy = gridy;
		
		gc.gridx++;
		for (JTextField importeField : inputRow.getImporte1Fields()) {
			add(importeField, gc);
			gc.gridy++;
		}
		if (gc.gridy > lastGridY) {
			lastGridY = gc.gridy;
		}
		gc.gridy = gridy;
		
		gc.gridx++;
		for (JComboBox combBox : inputRow.getHaberFields()) {
			add(combBox, gc);
			gc.gridy++;
		}
		if (gc.gridy > lastGridY) {
			lastGridY = gc.gridy;
		}
		gc.gridy = gridy;
		
		gc.gridx++;
		
		for (JTextField importeField : inputRow.getImporte2Fields()) {
			add(importeField, gc);
			gc.gridy++;
		}
		if (gc.gridy > lastGridY) {
			lastGridY = gc.gridy;
		}
		gc.gridy = gridy;
		
		gc.gridx++;
		add(inputRow.getAsientoDef(), gc);
		
		gc.gridy = lastGridY;
		
		this.revalidate();
		
	}
	
	public void removeInputRow() {
		if (this.inputRows.size() > 0) {
			removeLastRow();
			inputRows.remove(inputRows.size()-1);
			revalidate();
		}
	}
	
	public void addDebe() {
		inputRows.get(inputRows.size()-1).addDebe();
		removeLastRow();
		addLastRow();
		revalidate();
	}
	
	public void addHaber() {
		inputRows.get(inputRows.size()-1).addHaber();
		removeLastRow();
		addLastRow();
		revalidate();
	}
	
	public void removeDebe() {
		inputRows.get(inputRows.size()-1).removeDebe();
		removeLastRow();
		addLastRow();
		revalidate();
	}
	
	public void removeHaber() {
		inputRows.get(inputRows.size()-1).removeHaber();
		removeLastRow();
		addLastRow();
		revalidate();
	}
	
	private void removeLastRow() {
		InputRow inputRow = inputRows.get(inputRows.size()-1);
		remove(inputRow.getAsientoField());
		for (JComboBox cb : inputRow.getDebeFields()) {
			remove(cb);
		}
		for (JComboBox cb : inputRow.getHaberFields()) {
			remove(cb);
		}
		remove(inputRow.getFechaField());
		for (JTextField importeField : inputRow.getImporte1Fields()) {
			remove(importeField);
		}
		for (JTextField importeField : inputRow.getImporte2Fields()) {
			remove(importeField);
		}
		remove(inputRow.getAsientoDef());
	}
	
	private void addLastRow() {
		InputRow inputRow = inputRows.get(inputRows.size()-1);
		int lastGridY = gc.gridy;
		int gridy = gc.gridy;
		
		gc.gridx = 0;
		
		add(inputRow.getAsientoField(), gc);
		
		//gc.insets = new Insets(0, 0, 0, 50);
		
		gc.gridx++;
		add(inputRow.getFechaField(), gc);
		
		gc.gridx++;
		for (JComboBox combBox : inputRow.getDebeFields()) {
			add(combBox, gc);
			gc.gridy++;
		}
		if (gc.gridy > lastGridY) {
			lastGridY = gc.gridy;
		}
		gc.gridy = gridy;
		
		gc.gridx++;
		for (JTextField importeField : inputRow.getImporte1Fields()) {
			add(importeField, gc);
			gc.gridy++;
		}
		if (gc.gridy > lastGridY) {
			lastGridY = gc.gridy;
		}
		gc.gridy = gridy;
		
		gc.gridx++;
		for (JComboBox combBox : inputRow.getHaberFields()) {
			add(combBox, gc);
			gc.gridy++;
		}
		if (gc.gridy > lastGridY) {
			lastGridY = gc.gridy;
		}
		gc.gridy = gridy;
		
		gc.gridx++;
		for (JTextField importeField : inputRow.getImporte2Fields()) {
			add(importeField, gc);
			gc.gridy++;
		}
		if (gc.gridy > lastGridY) {
			lastGridY = gc.gridy;
		}
		gc.gridy = gridy;
		
		gc.gridx++;
		add(inputRow.getAsientoDef(), gc);
		
		gc.gridy = lastGridY;
		
		this.revalidate();
	}
	
	public void updateDef(List<String> asientos) {
		for (InputRow inputRow : this.inputRows) {
			inputRow.updateDef(asientos);
		}
		this.revalidate();
	}

}
