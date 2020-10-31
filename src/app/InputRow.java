package app;


import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class InputRow {
	
	private JTextField asientoField;
	private JTextField fechaField;
	private List<JComboBox> debeFields;
	private JTextField importe1Field;
	private List<JComboBox> haberFields;
	private JTextField importe2Field;
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
	
	public JTextField getFechaField() {
		return fechaField;
	}

	public void setFechaField(JTextField fechaField) {
		this.fechaField = fechaField;
	}

	public JTextField getImporte1Field() {
		return importe1Field;
	}

	public void setImporte1Field(JTextField importe1Field) {
		this.importe1Field = importe1Field;
	}

	public JTextField getImporte2Field() {
		return importe2Field;
	}

	public void setImporte2Field(JTextField importe2Field) {
		this.importe2Field = importe2Field;
	}

	public List<String> getBoxValues() {
		return boxValues;
	}

	public void setBoxValues(List<String> boxValues) {
		this.boxValues = boxValues;
	}

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
	
	public InputRow() {
	
		asientoField = new JTextField(20);
		fechaField = new JTextField(10);
		debeFields = new ArrayList<JComboBox>();
		importe1Field = new JTextField(10);
		haberFields = new ArrayList<JComboBox>();
		importe2Field = new JTextField(10);
		
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
