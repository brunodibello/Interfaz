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
	private List<JTextField> importe1Fields;
	private List<JComboBox> haberFields;
	private List<JTextField> importe2Fields;
	private JComboBox asientoDef;
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

	public List<JTextField> getImporte1Fields() {
		return importe1Fields;
	}

	public void setImporte1Field(List<JTextField> importe1Fields) {
		this.importe1Fields = importe1Fields;
	}

	public List<JTextField> getImporte2Fields() {
		return importe2Fields;
	}

	public void setImporte2Fields(List<JTextField> importe2Fields) {
		this.importe2Fields = importe2Fields;
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
	
	public JComboBox getAsientoDef() {
		return asientoDef;
	}

	public void setAsientoDef(JComboBox asientoDef) {
		this.asientoDef = asientoDef;
	}

	public void setImporte1Fields(List<JTextField> importe1Fields) {
		this.importe1Fields = importe1Fields;
	}

	public InputRow() {
	
		asientoField = new JTextField(20);
		fechaField = new JTextField(10);
		debeFields = new ArrayList<JComboBox>();
		importe1Fields = new ArrayList<JTextField>();
		importe1Fields.add(new JTextField(7));
		haberFields = new ArrayList<JComboBox>();
		importe2Fields = new ArrayList<JTextField>();
		importe2Fields.add(new JTextField(7));
		
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
		
		asientoDef = new JComboBox();
		boxModel = new DefaultComboBoxModel();
		asientoDef.setModel(boxModel);
		
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
		importe1Fields.add(new JTextField(10));
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
		importe2Fields.add(new JTextField(10));
	}
	
	public void removeDebe() {
		if (debeFields.size() > 0 && importe1Fields.size() > 0) {
			debeFields.remove(debeFields.size()-1);
			importe1Fields.remove(importe1Fields.size()-1);
		}
	}
	
	public void removeHaber() {
		if (haberFields.size() > 0 && importe2Fields.size() > 0) {
			haberFields.remove(haberFields.size()-1);
			importe2Fields.remove(importe2Fields.size()-1);
		}
	}
	
	public void updateDef(List<String> asientos) {
		DefaultComboBoxModel boxModel = new DefaultComboBoxModel();
		for (String value : asientos) {
			System.out.println(value);
			boxModel.addElement(value);
		}
		asientoDef.setModel(boxModel);
		asientoDef.setSelectedIndex(0);
	}
	

}
