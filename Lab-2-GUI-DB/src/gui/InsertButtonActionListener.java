package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import db.Data;
import db.ProgramDB;

class InsertButtonActionListener  implements ActionListener {

    final JTextField codeTextField;
    final JTextField nameTextField;
    final JTextField priceTextField;
    final JTextArea outputTextArea;
	
    public InsertButtonActionListener(JTextField codeTextField, JTextField nameTextField, JTextField priceTextField, JTextArea outputTextArea) {
		this.codeTextField = codeTextField;
		this.nameTextField = nameTextField;
		this.priceTextField = priceTextField;
		this.outputTextArea = outputTextArea;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		final String code = codeTextField.getText().trim();
		final String name = nameTextField.getText().trim();
		final String price = priceTextField.getText().trim();
		
//		final String str = code + "--" + name + "--" + price;
//		outputTextArea.setText(str);
		
		// CHECK & CONVERTDATA
		if (!isDataValid(code, name, price)) {
			final String errmsg = "Invalid Data !";
			this.outputTextArea.setText(errmsg);
		} else {
			final float priceFloat = Float.parseFloat(price);
			
			// Update DB & Present ALL row Data
			try {
				// Open Connection
				ProgramDB.getInstance().openDbConnection();
				// Insert Data to DB
				int rowsAffected = ProgramDB.getInstance().insertData(new Data(code, name, priceFloat));
				outputTextArea.setText("Insert Response / Rows Affected: " + rowsAffected + "\n");
				// Get ALL Data from DB
				final List<Data> dataList = ProgramDB.getInstance().getAllData();
				// Close Connection 
				ProgramDB.getInstance().closeDbConnection();
				
				// Present ALL Data
				for (Data data : dataList) {
					outputTextArea.append(data.asString() + "\n");
				}
				
			} catch (Throwable t) {
				outputTextArea.setText(t.getMessage());
			}

		}
		
	} // END actionPerformed(..)

	private boolean isDataValid(String code, String name, String price) {
		final StringBuilder errorsSb = new StringBuilder();
		if (code.isEmpty()) {
			errorsSb.append("Code is empty !");
		}
		if (name.isEmpty()) {
			if (!errorsSb.isEmpty()) errorsSb.append("\n");
			errorsSb.append("Name is empty !");
		}
		try {
			Float.parseFloat(price);
		} catch (Throwable t) {
			if (!errorsSb.isEmpty()) errorsSb.append("\n");
			errorsSb.append("Price is invalid Float Number ! " + t.getMessage());
		}
		
		if (!errorsSb.isEmpty()) {
			JOptionPane.showMessageDialog(null, errorsSb.toString(), "Invalid Data", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		return true;
	}
	
}
