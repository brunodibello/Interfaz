package app;


import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class DefRow {
	
	private JTextField asientoField;
	private List<JComboBox> debeFields;
	private List<JComboBox> haberFields;
	private List<String> boxValues = new ArrayList<String>() {
		{
			add("11211-RenterDebt");
			add("11311-RenterFee");
			add("11111-Cash");
			add("11112-Bank");
			add("12222-DamageExpenses");
			add("21111-Landlords");
		}
	};
	
	public JTextField getAsientoField() {
		return asientoField;
	}

	public void setAsientoField(JTextField asientoField) {
		this.asientoField = asientoField;
	}

	public List<JComboBox> getDebeFields() {
		return debeFields;
	}

	public void setDebeFields(List<JComboBox> debeFields) {
		this.debeFields = debeFields;
	}

	public List<JComboBox> getHaberFields() {
		return haberFields;
	}

	public void setHaberFields(List<JComboBox> haberFields) {
		this.haberFields = haberFields;
	}
	
	public DefRow() {
	
		asientoField = new JTextField(20);
		debeFields = new ArrayList<JComboBox>();
		haberFields = new ArrayList<JComboBox>();
		
		JComboBox debeBox = new JComboBox();
		DefaultComboBoxModel boxModel = new DefaultComboBoxModel();
		for (String value : boxValues) {
			boxModel.addElement(value);
		}
		debeBox.setModel(boxModel);
		debeBox.setSelectedIndex(0);
		debeFields.add(debeBox);
		
		JComboBox haberBox = new JComboBox();
		boxModel = new DefaultComboBoxModel();
		for (String value : boxValues) {
			boxModel.addElement(value);
		}
		haberBox.setModel(boxModel);
		haberBox.setSelectedIndex(0);
		haberFields.add(haberBox);
		
	}
	
	public void addDebe() {
		JComboBox debeBox = new JComboBox();
		DefaultComboBoxModel boxModel = new DefaultComboBoxModel();
		for (String value : boxValues) {
			boxModel.addElement(value);
		}
		debeBox.setModel(boxModel);
		debeBox.setSelectedIndex(0);
		debeFields.add(debeBox);
	}
	
	public void addHaber() {
		JComboBox haberBox = new JComboBox();
		DefaultComboBoxModel boxModel = new DefaultComboBoxModel();
		for (String value : boxValues) {
			boxModel.addElement(value);
		}
		haberBox.setModel(boxModel);
		haberBox.setSelectedIndex(0);
		haberFields.add(haberBox);
	}
	

}
