package app;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import org.semanticweb.HermiT.cli.CommandLine;

public class InputFrame extends JPanel {
	
	private JLabel asientoLabel;
	private JLabel fechaLabel;
	private JLabel debeLabel;
	private JLabel importe1Label;
	private JLabel haberLabel;
	private JLabel importe2Label;
	
	public JButton confirmBtn;
	private JButton addRowBtn;
	private JButton removeRowBtn;
	
	private JButton addDebeBtn;
	private JButton addHaberBtn;
	private JButton removeDebeBtn;
	private JButton removeHaberBtn;
		
	private GridBagConstraints gc;
	
	private List<InputRow> inputRows;

	public InputFrame() {
		
		asientoLabel = new JLabel("Asiento");
		fechaLabel = new JLabel("Fecha");
		debeLabel = new JLabel("Debe");
		importe1Label = new JLabel("Importe");
		haberLabel = new JLabel("Haber");
		importe2Label = new JLabel("Importe");
		
		confirmBtn = new JButton("Confirmar");
		addRowBtn = new JButton("Agregar Fila");
		removeRowBtn = new JButton("Remover Fila");
		
		addDebeBtn = new JButton("+");
		addHaberBtn = new JButton("+");
		removeDebeBtn = new JButton("-");
		removeHaberBtn = new JButton("-");
		
		inputRows = new ArrayList<InputRow>();
		
		addRowBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addInputRow();
			}
		});
		
		removeRowBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeInputRow();
			}
		});
		
		addDebeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addDebe();
			}
		});
		
		addHaberBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addHaber();
			}
		});		
		
		setBorder(BorderFactory.createEtchedBorder());
		
		Border innerBorder = BorderFactory.createTitledBorder("Ingreso de Asientos");
		Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
				
		addComponents();
	}
	
	private void addComponents() {
		
		setLayout(new GridBagLayout());
		
		gc = new GridBagConstraints();
		
		////////// LABELS //////////
		
		gc.gridy = 0;
		gc.gridx = 0;
		gc.weightx = 1;
		
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(asientoLabel, gc);
		
		
		gc.gridx++;
		add(fechaLabel, gc);
		
		gc.gridx++;
		add(debeLabel, gc);
		
		gc.gridx++;
		add(addDebeBtn, gc);
				
		gc.gridx++;
		add(importe1Label, gc);
				
		gc.gridx++;
		add(haberLabel, gc);
		
		gc.gridx++;
		add(addHaberBtn, gc);
				
		gc.gridx++;
		add(importe2Label, gc);
		
		/////////// ROWS ///////////////
		
		gc.gridy++;
		
		gc.gridx = 0;
		
		addInputRow();
		
		//////////  BUTTON  ////////////
		
		addButtons();
		
	}
	
	private void addButtons() {
		gc.anchor = GridBagConstraints.LAST_LINE_START;
		gc.gridy++;
		
		gc.gridx = 0;
		
		add(addRowBtn, gc);
		
		gc.gridx++;
		add(removeRowBtn, gc);
		
		gc.gridx++;
		add(confirmBtn, gc);
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
	}
	
	private void removeButtons() {
		remove(addRowBtn);
		remove(removeRowBtn);
		remove(confirmBtn);
	}
	
	public List<InputRow> getInputRows() {
		return this.inputRows;
	}
	
	public void addInputRow() {
		gc.weighty = 1;
		
		removeButtons();
		
		int lastGridY = gc.gridy;
		
		InputRow inputRow = new InputRow();
		inputRows.add(inputRow);
		
		int gridy = gc.gridy;
		
		gc.gridx = 0;
		add(inputRow.getAsientoField(), gc);

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
		
		addButtons();
		
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
		removeButtons();
		removeLastRow();
		addLastRow();
		addButtons();
		revalidate();
	}
	
	public void addHaber() {
		inputRows.get(inputRows.size()-1).addHaber();
		removeButtons();
		removeLastRow();
		addLastRow();
		addButtons();
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
		
		removeButtons();
		
		InputRow inputRow = inputRows.get(inputRows.size()-1);
		int lastGridY = gc.gridy;
		int gridy = gc.gridy;
		
		gc.gridx = 0;
		
		add(inputRow.getAsientoField(), gc);
				
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
		
		addButtons();
		
		this.revalidate();
	}
	
	public void updateDef(List<String> asientos) {
		for (InputRow inputRow : this.inputRows) {
			inputRow.updateDef(asientos);
		}
		this.revalidate();
	}
	
}
