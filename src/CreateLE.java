import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class CreateLE extends JPanel implements ActionListener {

	private Connection connection ;
	private JButton back ;
	private JTextField nameLE ;
	private JLabel typeLE ;
	private JButton insert ;
	
	
	public CreateLE (Connection connection) {
		
		this.connection = connection ;
		
		nameLE = new JTextField (20) ;
		typeLE = new JLabel ("Type linguistic expression: ") ;
		insert = new JButton ("INSERT") ;
		
		insert.addActionListener (this) ;
		
		this.setLayout(new GridLayout(2,0));
		back = new JButton ("BACK") ;
		back.setForeground(Color.gray) ;
		back.addActionListener(this);
		
		JPanel top = new JPanel () ;
		
		top.add(typeLE) ;
		top.add(nameLE) ;
		top.add(insert) ;
		
		add (top) ;
		add (back) ;
		
	}
	

	
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == back) {
			Functions funcs = new Functions (connection) ;
			removeAll ();
			revalidate() ;
			repaint() ;
			this.setLayout(new GridLayout(1,0));
			add(funcs) ;
			revalidate() ;
			repaint() ;
		}
		
		if (e.getSource() == insert) {


			String sql = "INSERT INTO LINGUISTIC_EXPRESSION (NAME) VALUES (?)" ;
			
			if (nameLE.getText() != null) {
				
				try {
					PreparedStatement stmt = connection.prepareStatement(sql) ;
					stmt.setString(1, nameLE.getText());

					int sqlCode = stmt.executeUpdate() ;
					
					if (sqlCode > 0) {
						System.out.println("New Linguistic Expression inserted");
						JOptionPane.showMessageDialog (this, "New Linguistic Expression inserted") ;
					}
				} catch (SQLException exp) {
					// TODO Auto-generated catch block
					exp.printStackTrace();
				} 
			}
			
			else {
				JOptionPane.showMessageDialog (this, "Please type LE") ;
			}
			
		}
		
	}

}
