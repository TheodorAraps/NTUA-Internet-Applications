package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

class ProgramJFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final int WIDTH = 500;
	private static final int HEIGHT = 600;
	
	private static final Font verdana14bold = new Font("Verdana", Font.BOLD, 14);
	private static final Color blueColor = new Color(0, 62, 129);
	
	public ProgramJFrame() {
		
		// Set the Title of the window (Frame)
		super("DB INSERT & VIEW DATA GUI");
		
		// Set Frame in the middle of our screen with the given dimensions 
	    final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	    final int x = (screen.width - WIDTH) / 2;
	    final int y = (screen.height - HEIGHT) / 2;
	    setBounds(x, y, WIDTH, HEIGHT);
		
		/* COMPONENTS & STYLING */
		
	    // Title
		final JLabel title1Label = new JLabel("Provide Product Data"); 
		title1Label.setFont(verdana14bold);
		title1Label.setForeground(blueColor);
		// Code 
	    final JLabel codeLabel = new JLabel("Code:"); 
	    final JTextField codeTextField = new JTextField(10);
	    // Name
	    final JLabel nameLabel = new JLabel("Name:"); 
	    final JTextField nameTextField = new JTextField(30);
	    // Price
	    final JLabel priceLabel = new JLabel("Price:"); 
	    final JTextField priceTextField = new JTextField(10);	    
	    // Button
	    final JButton button = new JButton("Insert Data & View Existing Ones");
	    // Output
	    final JLabel title2Label = new JLabel("All Products"); 
	    title2Label.setFont(verdana14bold);
	    title2Label.setForeground(blueColor);
	    final JTextArea outputTextArea = new JTextArea(10, 30); 
	    outputTextArea.setEditable(false);
	    final JScrollPane scrolTextArea = new JScrollPane(outputTextArea, 
	    	ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    
	    /* ADD LISTENERS TO COMPONENTS */
	    
	    button.addActionListener(
	    	new InsertButtonActionListener(
	    		codeTextField, nameTextField, priceTextField, outputTextArea));
	    
	    /* ADD COMPONENTS to JFrame */
	    
	    final JPanel panel = new JPanel();
	    final GridBagLayout layout = new GridBagLayout();
		panel.setLayout(layout);

		addComponent(panel, title1Label, 	0, 0, 2, 1, 100, 10);
		addComponent(panel, codeLabel, 		1, 0, 1, 1, 40,  5);
		addComponent(panel, codeTextField, 	1, 1, 1, 1, 60,  5);
		addComponent(panel, nameLabel, 		2, 0, 1, 1, 40,  5);
		addComponent(panel, nameTextField, 	2, 1, 1, 1, 60,  5);		
		addComponent(panel, priceLabel, 	3, 0, 1, 1, 40,  5);
		addComponent(panel, priceTextField, 3, 1, 1, 1, 60,  5);			
		addComponent(panel, button,		 	4, 0, 2, 1, 100, 5);
		addComponent(panel, title2Label, 	5, 0, 2, 1, 100, 10);
		addComponent(panel, scrolTextArea,	6, 0, 2, 6, 100, 45);
		
		this.setContentPane(panel);
	}
	
	private void addComponent(final JPanel panel, final Component com, int row, int column, int width, int height, int wx, int wy) {
		final GridBagConstraints constraints = new GridBagConstraints();
		// Insets(int top, int left, int bottom, int right)
		constraints.insets = new Insets( 5, 10, 5, 10 );  
	    constraints.fill=GridBagConstraints.BOTH;
		// Specifies the cell containing the leading edge of the component's display area
		constraints.gridx=column;
		// Specifies the cell at the top of the component's display area
		constraints.gridy=row;
		// Specifies the number of cells in a row for the component's display area.
		constraints.gridwidth=width;
		// Specifies the number of cells in a column for the component's display area.
		constraints.gridheight=height;
		// Specifies how to distribute extra horizontal space.
		constraints.weightx=wx;
		// Specifies how to distribute extra vertical space.
		constraints.weighty=wy;
		// Add Constraints before adding Component to Panel
		// IF Panel Layout is not a GridBagLayout, a ClassCastException would be thrown
		final GridBagLayout panelGBL = (GridBagLayout) panel.getLayout();
		panelGBL.setConstraints(com,constraints);
		panel.add(com);
	}
	
}
