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
import javax.swing.JLabel;
import javax.swing.JPanel;
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
	
	private InputRowPanel inputRowPanel;
	
	private GridBagConstraints gc;

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
		
		addRowBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inputRowPanel.addInputRow();
			}
		});
		
		removeRowBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inputRowPanel.removeInputRow();
			}
		});
		
		addDebeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inputRowPanel.addDebe();
			}
		});
		
		addHaberBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inputRowPanel.addHaber();
			}
		});
		
		removeDebeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inputRowPanel.removeDebe();
			}
		});
		
		removeHaberBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inputRowPanel.removeHaber();
			}
		});
		
		inputRowPanel = new InputRowPanel();
		
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
		gc.gridwidth = 1;
		
		gc.weightx = 1;
		gc.weighty = 1;		
		
		gc.anchor = GridBagConstraints.LINE_START;
		add(asientoLabel, gc);
		
		
		gc.gridx++;
		add(fechaLabel, gc);
		
		gc.weightx = 0.5;
		gc.gridx++;
		add(debeLabel, gc);
		
		gc.gridx++;
		add(addDebeBtn, gc);
		
//		gc.gridx++;
//		add(removeDebeBtn, gc);
		
		gc.gridx++;
		gc.weightx = 1;
		add(importe1Label, gc);
		
		
		gc.weightx = 0.5;
		gc.gridx++;
		add(haberLabel, gc);
		
		gc.gridx++;
		add(addHaberBtn, gc);
		
//		gc.gridx++;
//		add(removeHaberBtn, gc);
		gc.insets = new Insets(0, 0, 0, 50);
		gc.gridx++;
		add(importe2Label, gc);
		//gc.anchor = GridBagConstraints.LINE_END;
		
		/////////// ROWS ///////////////
		
		gc.gridy++;
		
		gc.gridx = 0;
		gc.gridwidth = 8;
		add(inputRowPanel, gc);		
		inputRowPanel.addInputRow();
		
		gc.gridwidth = 1;
		gc.fill = GridBagConstraints.NONE;
				
		//////////  BUTTON  ////////////
		
		addButtons();
		
	}
	
	private void addButtons() {
		gc.gridy++;
		
		gc.weightx = 1;
		gc.weighty = 1;
		
		gc.gridx = 3;
		gc.insets = new Insets(5, 0, 0, 0);
		gc.anchor = GridBagConstraints.PAGE_END;
		add(confirmBtn, gc);
		
		gc.gridx++;
		add(addRowBtn, gc);
		
		gc.gridx++;
		add(removeRowBtn, gc);
	}
	
	public List<InputRow> getInputRows() {
		return this.inputRowPanel.getInputRows();
	}
	
}
