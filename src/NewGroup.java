import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class NewGroup extends JPanel implements ActionListener {

	private Connection connection ;
	private JButton back ;
	private JTextField groupName ;
	private JLabel typeName ;
	private JButton submit ;
	
	
	public NewGroup (Connection connection) {
		
		this.connection = connection ;
		
		groupName = new JTextField (20) ;
		typeName = new JLabel ("Group Name:") ;
		submit = new JButton ("SUBMIT") ;
		
		this.setLayout(new GridLayout(2,0));
		back = new JButton ("BACK") ;
		back.setForeground(Color.gray) ;
		back.addActionListener(this);
		
		JPanel top = new JPanel () ;
		
		top.add(typeName) ;
		top.add(groupName) ;
		top.add(submit) ;
		
		submit.addActionListener(this);
		
		add (top) ;
		add (back) ;
	}
	

	
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == back) {
			GroupPanel gp = new GroupPanel (connection) ;
			removeAll ();
			revalidate() ;
			repaint() ;
			this.setLayout(new GridLayout(1,0));
			add(gp) ;
			revalidate() ;
			repaint() ;
		}
		
		
		if (e.getSource() == submit) {
			
			String sql = "SELECT ID FROM WORDS_GROUP WHERE NAME = ?" ;
			try {
				PreparedStatement stmt = connection.prepareStatement(sql) ;
				stmt.setString (1, groupName.getText()) ;
				int sqlCode = stmt.executeUpdate() ;
				
				if (sqlCode<=0) {
					sql = "INSERT INTO WORDS_GROUP (NAME) VALUES (?)" ;
					try {
						stmt = connection.prepareStatement(sql) ;
						stmt.setString (1, groupName.getText()) ;
						sqlCode = stmt.executeUpdate() ;
						
						if (sqlCode > 0) {
							System.out.println("New words group inserted");
							JOptionPane.showMessageDialog (this, "New words group inserted") ;
						}
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
				else {
					JOptionPane.showMessageDialog (this, "There is a group with the same name") ;
					
				}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			
			
			
			
		}
		
	}

}
