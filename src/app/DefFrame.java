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
import javax.swing.JScrollPane;
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
	
	private DefRowPanel defRowPanel;
		
	private GridBagConstraints gc;

	public DefFrame() {
		
		asientoLabel = new JLabel("Asiento");
		debeLabel = new JLabel("Debe");
		haberLabel = new JLabel("Haber");
		
		removeRowBtn = new JButton("Remover Fila");
		addRowBtn = new JButton("Agregar Fila");
		updateDefBtn = new JButton("Guardar Definiciones");
		addDebeBtn = new JButton("+");
		addHaberBtn = new JButton("+");
		
		addRowBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				defRowPanel.addDefRow();
			}
		});
		
		removeRowBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				defRowPanel.removeDefRow();
			}
		});
		
		addDebeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				defRowPanel.addDebe();
			}
		});
		
		addHaberBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				defRowPanel.addHaber();
			}
		});
		
		defRowPanel = new DefRowPanel();
				
		setBorder(BorderFactory.createEtchedBorder());
		
		Border innerBorder = BorderFactory.createTitledBorder("Definicion de Asientos");
		Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
				
		addComponents();
	}
	
	private void addComponents() {
		
		setLayout(new GridBagLayout());
		
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.NONE;
		
		////////// LABELS //////////
		
		gc.gridy = 0;
		
		gc.weightx = 1;
		gc.weighty = 1;
		
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		add(asientoLabel, gc);
		
		gc.weightx = 0.5;
		gc.gridx++;
		add(debeLabel, gc);
		gc.gridx++;
		add(addDebeBtn, gc);
		
		gc.gridx++;
		add(haberLabel, gc);
		gc.gridx++;
		add(addHaberBtn, gc);
		
		/////////// ROWS ///////////////
		
		gc.gridy++;
		
		gc.gridx = 0;
		gc.weightx = 1;
		gc.weighty = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.gridwidth = 100;
		gc.anchor = GridBagConstraints.CENTER;
		add(defRowPanel, gc);		
		defRowPanel.addDefRow();
		
		gc.gridwidth = 1;
		gc.fill = GridBagConstraints.NONE;
				
		//////////  BUTTON  ////////////
		
		addButtons();
		
	}

	private void addButtons() {
		gc.gridy++;
		
		gc.weightx = 0.5;
		gc.weighty = 1;
		
		gc.gridx = 1;
		gc.insets = new Insets(5, 0, 0, 0);
		gc.anchor = GridBagConstraints.PAGE_END;
		add(addRowBtn, gc);
		
		gc.gridx++;
		add(removeRowBtn, gc);
		
		gc.gridx++;
		add(updateDefBtn, gc);
	}
	
	public List<DefRow> getDefRows() {
		return this.defRowPanel.getDefRows();
	}
	
	public List<String> getAsientos() {
		List<String> asientos = new ArrayList<String>();
		for (DefRow defRow : this.defRowPanel.getDefRows()) {
			asientos.add(defRow.getAsientoField().getText());
		}
		return asientos;
	}
	
}
