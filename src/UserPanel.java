import java.awt.GridLayout;
import java.awt.event.*;
import java.sql.Connection;

import javax.swing.*;
import java.awt.*;


public class UserPanel extends JPanel implements ActionListener{
	
	private JLabel logo ;
	private JButton insert ;
	private JButton funcs ;
	private JButton export ;
	private Connection connection ;
	
	
	public UserPanel (Connection connection) {
		
		this.connection = connection ;
		ImageIcon logoIcon = new ImageIcon ("logo.png") ;
		logo = new JLabel (logoIcon) ;
		insert = new JButton ("Insert file") ;
		funcs = new JButton ("Functions") ;
		export = new JButton ("Export to XML") ;
		
		this.setLayout(new GridLayout(2,1));
		
		JPanel buttons = new JPanel () ;
		
		insert.addActionListener(this);
		funcs.addActionListener(this);
		export.addActionListener(this);
		
		buttons.add(insert) ;
		buttons.add(funcs) ;
		buttons.add(export) ;
		
		add(logo) ;
		add (buttons) ;
		
		logo.revalidate() ;
	}
	
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == insert) {
			FileInsert ins = new FileInsert (connection) ;
			removeAll ();
			revalidate() ;
			repaint() ;
			this.setLayout(new GridLayout(1,0));
			add(ins) ;
			revalidate() ;
			repaint() ;
		}
		
		else if(e.getSource() == funcs) {
			Functions funcs = new Functions (connection) ;
			removeAll ();
			revalidate() ;
			repaint() ;
			this.setLayout(new GridLayout(1,0));
			add(funcs) ;
			revalidate() ;
			repaint() ;
		}
		
		else if(e.getSource() == export) {
			
			ExportToXml.exportDB (connection) ;
			JOptionPane.showMessageDialog (this, "Backup created successfully") ;
		}
		
	}
	

	
	

}
