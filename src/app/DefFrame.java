package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class DefFrame extends JPanel {
	
	private JLabel asientoLabel;
	private JLabel debeLabel;
	private JLabel haberLabel;
	
	private JButton removeRowBtn;
	private JButton addRowBtn;
	public JButton updateDefBtn;
	
	private JButton addDebeBtn;
	private JButton addHaberBtn;
			
	private GridBagConstraints gc;
	
	private List<DefRow> defRows;

	public DefFrame() {
		
		asientoLabel = new JLabel("Asiento");
		asientoLabel.setFont(new Font("Verdana", Font.PLAIN, 18));
		debeLabel = new JLabel("Debe");
		debeLabel.setFont(new Font("Verdana", Font.PLAIN, 18));
		haberLabel = new JLabel("Haber");
		haberLabel.setFont(new Font("Verdana", Font.PLAIN, 18));
		
		removeRowBtn = new JButton("Remover Fila");
		addRowBtn = new JButton("Agregar Fila");
		updateDefBtn = new JButton("Guardar Definiciones");
		addDebeBtn = new JButton("+");
		addHaberBtn = new JButton("+");
		
		addRowBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addDefRow();
			}
		});
		
		removeRowBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeDefRow();
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
		
		Border innerBorder = BorderFactory.createTitledBorder("Definicion de Asientos");
		Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
		
		defRows = new ArrayList<DefRow>();
				
		addComponents();
	}
	
	private void addComponents() {
		
		setLayout(new GridBagLayout());
		
		gc = new GridBagConstraints();
		
		gc.gridy = 0;
		gc.gridx = 0;
		gc.weightx = 1;
		
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		
	
		////////// LABELS //////////
				
		add(asientoLabel, gc);
				
		gc.gridx++;
		add(debeLabel, gc);
		gc.gridx++;
		add(addDebeBtn, gc);
		
		gc.gridx++;
		add(haberLabel, gc);
		gc.gridx++;
		add(addHaberBtn, gc);
		
		gc.gridy++;
		gc.gridwidth = 5;
		gc.gridx=0;
		gc.insets = new Insets(10,0,10,0);
		gc.fill = GridBagConstraints.HORIZONTAL;
		JSeparator sep = new JSeparator(JSeparator.HORIZONTAL);
		sep.setBackground(Color.BLACK);
		sep.setPreferredSize(new Dimension(5,1));
		add(sep,  gc);
		gc.fill = GridBagConstraints.NONE;
		gc.gridwidth = 1;
		gc.insets = new Insets(0,0,0,0);
		
		/////////// ROWS ///////////////
		
		gc.gridy++;
		
		gc.gridx = 0;
		
		addDefRow();
		
		//////////BUTTONS  ////////////
		addButtons();
		
	}
	
	private void removeButtons() {
		remove(addRowBtn);
		remove(removeRowBtn);
		remove(updateDefBtn);
	}

	private void addButtons() {
		
		gc.anchor = GridBagConstraints.LAST_LINE_START;
		gc.gridx = 0;

		add(addRowBtn, gc);
		
		gc.gridx++;
		add(removeRowBtn, gc);
		
		gc.gridx++;
		add(updateDefBtn, gc);
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
	}
	
	public List<DefRow> getDefRows() {
		return this.defRows;
	}
	
	public List<String> getAsientos() {
		List<String> asientos = new ArrayList<String>();
		for (DefRow defRow : this.defRows) {
			asientos.add(defRow.getAsientoField().getText());
		}
		return asientos;
	}
	
	public void addDefRow() {
		gc.weighty = 1;
		
		removeButtons();
		
		int lastGridY = gc.gridy;
		
		DefRow defRow = new DefRow();
		defRows.add(defRow);
		
		int gridy = gc.gridy;
		
		gc.gridx = 0;
		gc.gridwidth = 1;
		
		add(defRow.getAsientoField(), gc);
				
		gc.gridx++;
		gc.gridwidth =2;
		for (JComboBox combBox : defRow.getDebeFields()) {
			add(combBox, gc);
			gc.gridy++;
		}
		if (gc.gridy > lastGridY) {
			lastGridY = gc.gridy;
		}
		gc.gridy = gridy;
		
		gc.gridx++;
		gc.gridx++;
		for (JComboBox combBox : defRow.getHaberFields()) {
			add(combBox, gc);
			gc.gridy++;
		}
		if (gc.gridy > lastGridY) {
			lastGridY = gc.gridy;
		}
		
		gc.gridy = lastGridY;
		
		addButtons();
		
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
		removeButtons();
		removeLastRow();
		addLastRow();
		addButtons();
		revalidate();
	}
	
	public void addHaber() {
		defRows.get(defRows.size()-1).addHaber();
		removeButtons();
		removeLastRow();
		addLastRow();
		addButtons();
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
		
		add(defRow.getAsientoField(), gc);
				
		gc.gridx++;
		gc.gridwidth = 2;
		for (JComboBox combBox : defRow.getDebeFields()) {
			add(combBox, gc);
			gc.gridy++;
		}
		if (gc.gridy > lastGridY) {
			lastGridY = gc.gridy;
		}
		gc.gridy = gridy;
		
		gc.gridx++;
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
