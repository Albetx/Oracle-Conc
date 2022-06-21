import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class AddWordsToGroup extends JPanel implements ActionListener {

	private Connection connection ;
	private JButton back ;
	private JTextField word ;
	private JComboBox chooseWord ;
	private JComboBox chooseGroup ;
	private JLabel chooseGroupLbl ;
	private JLabel choose ;
	private JLabel type ;
	private JButton submit1 ;
	private JButton submit2 ;
	
	
	
	public AddWordsToGroup (Connection connection) {
		
		this.connection = connection ;
		
		word = new JTextField (20) ;
		chooseWord = new JComboBox () ;
		chooseGroup = new JComboBox () ;
		
		ArrayList<String> words = new ArrayList<String> () ;
		
		String sql = "SELECT DISTINCT VALUE FROM WORD" ;
		
		try {
			PreparedStatement stmt = connection.prepareStatement(sql) ;
			ResultSet rs = stmt.executeQuery() ;
			while (rs.next()) 
				words.add(rs.getString("VALUE")) ;

			for (int i = 0; i < words.size(); i++) {
				chooseWord.addItem(words.get(i));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		ArrayList<String> groups = new ArrayList<String> () ;
		sql = "SELECT DISTINCT NAME FROM WORDS_GROUP" ;
		
		try {
			PreparedStatement stmt = connection.prepareStatement(sql) ;
			ResultSet rs = stmt.executeQuery() ;
			while (rs.next()) 
				groups.add(rs.getString("NAME")) ;

			for (int i = 0; i < groups.size(); i++) {
				chooseGroup.addItem(groups.get(i));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		choose = new JLabel ("Choose word: ") ;
		type = new JLabel ("OR type: ") ;
		chooseGroupLbl = new JLabel ("Choose Group: ") ;
		
		submit1 = new JButton ("SUBMIT") ;
		submit2 = new JButton ("SUBMIT") ;
		
		submit1.addActionListener(this);
		submit2.addActionListener(this);
		
		this.setLayout(new GridLayout(4,0));
		back = new JButton ("BACK") ;
		back.setForeground(Color.gray) ;
		back.addActionListener(this);
		
		JPanel p1 = new JPanel ();
		JPanel p2 = new JPanel ();
		JPanel p3 = new JPanel ();
		
		p1.add(chooseGroupLbl) ;
		p1.add(chooseGroup) ;
		p2.add(choose) ;
		p2.add (chooseWord) ;
		p2.add(submit1) ;
		p3.add(type) ;
		p3.add(word) ;
		p3.add(submit2) ;
		
		add (p1) ;
		add (p2) ;
		add (p3) ;
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
		
		
		if (e.getSource() == submit1) {
			
			int groupID = -1;
			
			String sql = "SELECT ID FROM WORDS_GROUP WHERE NAME = ?" ;
			try {
				PreparedStatement stmt = connection.prepareStatement(sql) ;
				stmt.setString(1, chooseGroup.getSelectedItem().toString());
				ResultSet rs = stmt.executeQuery() ;
				if (rs.next())
					groupID = rs.getInt("ID") ;
				else 
					System.out.println("Error: no group with this name");
					
				
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			sql = "UPDATE WORD SET GROUP_ID = ? WHERE VALUE = ? AND NOT EXISTS (SELECT VALUE FROM WORD WHERE VALUE = ? AND GROUP_ID = ?)" ;
			
			try {
				PreparedStatement stmt = connection.prepareStatement(sql) ;
				stmt.setInt(1,groupID) ;
				stmt.setString(2, chooseWord.getSelectedItem().toString());
				stmt.setString(3, chooseWord.getSelectedItem().toString());
				stmt.setInt(4,groupID) ;
				
				int sqlCode = stmt.executeUpdate() ;
				
				if (sqlCode > 0) 
					JOptionPane.showMessageDialog (this, "The word was inserted to the group") ;
				
				else 
					JOptionPane.showMessageDialog (this, "The word already in this group") ;
				
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		if (e.getSource() == submit2) {
			
			int sqlCode = 0; 
			String sql = "SELECT ID FROM WORD WHERE VALUE = ?" ;
			PreparedStatement stmt;
			try {
			stmt = connection.prepareStatement(sql);

			stmt.setString (1,word.getText()) ;
			sqlCode = stmt.executeUpdate() ;
			
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			// If there is such word in the DB
			if (sqlCode > 0) {
				
				int groupID = -1;
				
				sql = "SELECT ID FROM WORDS_GROUP WHERE NAME = ?" ;
				try {
					stmt = connection.prepareStatement(sql) ;
					stmt.setString(1, chooseGroup.getSelectedItem().toString());
					ResultSet rs = stmt.executeQuery() ;
					if (rs.next())
						groupID = rs.getInt("ID") ;
					else 
						System.out.println("Error: no group with this name");
						
					
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				sql = "UPDATE WORD SET GROUP_ID = ? WHERE VALUE = ? AND NOT EXISTS (SELECT VALUE FROM WORD WHERE VALUE = ? AND GROUP_ID = ?)" ;
				
				try {
					stmt = connection.prepareStatement(sql) ;
					stmt.setInt(1,groupID) ;
					stmt.setString(2, word.getText());
					stmt.setString(3, word.getText());
					stmt.setInt(4,groupID) ;
					
					sqlCode = stmt.executeUpdate() ;
					
					if (sqlCode > 0) 
						JOptionPane.showMessageDialog (this, "The word was inserted to the group") ;
					else 
						JOptionPane.showMessageDialog (this, "The word already in this group") ;
					
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			else
				JOptionPane.showMessageDialog (this, "No such word in the DB") ;
		}
		
	}

}
