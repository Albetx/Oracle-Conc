import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;


public class ShowByInfo extends JPanel implements ActionListener{

	private Connection connection ;
	private JComboBox options ;
	private JTextArea text ;
	private JTextField data ;
	private JButton search ;
	private JButton back ;
	
	
	public ShowByInfo (Connection connection) {
		
		back = new JButton ("BACK") ;
		back.addActionListener(this);
		this.connection = connection ;
		
		String[] opt = { "Link", "Name", "Publisher name", "Composer","Word in text"};
		options = new JComboBox (opt) ;
		
		text = new JTextArea () ;
		JScrollPane scrollPane = new JScrollPane(text); 
		scrollPane.setBounds(10,60,780,500);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		text.setEditable(false);
		
		data = new JTextField (15) ;
		
		search = new JButton ("Search") ;
		search.addActionListener(this);
		
		JPanel top = new JPanel () ;
		
		top.add(options) ;
		top.add(data) ;
		
		this.setLayout(new GridLayout(4,0));
		add (top);
		add (search) ;
		add(scrollPane);
		add (back) ;
		
		back.setForeground(Color.gray) ;
		
		text.setSize(400, 400);
	}

	
	
	
	public void actionPerformed(ActionEvent e) {
		
		text.setText("");

		if (e.getSource() == search) {
			
			if (data.getText() == null) {
				System.out.println("No data entered!");
			}
			
			//Search by link
			else if (options.getSelectedIndex() == 0) {
				
				String sql = "SELECT ID,STAT_ID,LINK,NAME,PUBLISHER_NAME,COMPOSER FROM POEM WHERE LINK = ?" ;
				
				try {
					PreparedStatement stmt = connection.prepareStatement(sql) ;
					stmt.setString (1, data.getText()) ;
					ResultSet rs = stmt.executeQuery();
					
					while (rs.next()) {
						text.setText(text.getText() +"ID:" + rs.getInt("ID") + "     " + "STAT_ID:" + rs.getInt("STAT_ID") + "     " + "LINK:" + rs.getString("LINK") + "     " +
								"NAME:" +	rs.getString("NAME") + "     " + "PUBLISHER_NAME:" + rs.getString("PUBLISHER_NAME") + "     " + "COMPOSER:" + rs.getString("COMPOSER") + "\n");
					}
					
				} catch (SQLException exp) {
					exp.printStackTrace();
				}
			}
			
			//Search by name
			else if (options.getSelectedIndex() == 1) {
				
				String sql = "SELECT ID,STAT_ID,LINK,NAME,PUBLISHER_NAME,COMPOSER FROM POEM WHERE NAME = ?" ;
				
				try {
					PreparedStatement stmt = connection.prepareStatement(sql) ;
					stmt.setString (1, data.getText()) ;
					ResultSet rs = stmt.executeQuery();
					
					while (rs.next()) {
						text.setText(text.getText() +"ID:" + rs.getInt("ID") + "     " + "STAT_ID:" + rs.getInt("STAT_ID") + "     " + "LINK:" + rs.getString("LINK") + "     " +
								"NAME:" +	rs.getString("NAME") + "     " + "PUBLISHER_NAME:" + rs.getString("PUBLISHER_NAME") + "     " + "COMPOSER:" + rs.getString("COMPOSER") + "\n");
					}
					
				} catch (SQLException exp) {
					exp.printStackTrace();
				}
			}
			
			//Search by publisher name
			else if (options.getSelectedIndex() == 2) {
				
				String sql = "SELECT ID,STAT_ID,LINK,NAME,PUBLISHER_NAME,COMPOSER FROM POEM WHERE PUBLISHER_NAME = ?" ;
				
				try {
					PreparedStatement stmt = connection.prepareStatement(sql) ;
					stmt.setString (1, data.getText()) ;
					ResultSet rs = stmt.executeQuery();
					
					while (rs.next()) {
						text.setText(text.getText() +"ID:" + rs.getInt("ID") + "     " + "STAT_ID:" + rs.getInt("STAT_ID") + "     " + "LINK:" + rs.getString("LINK") + "     " +
								"NAME:" +	rs.getString("NAME") + "     " + "PUBLISHER_NAME:" + rs.getString("PUBLISHER_NAME") + "     " + "COMPOSER:" + rs.getString("COMPOSER") + "\n");
					}
					
				} catch (SQLException exp) {
					exp.printStackTrace();
				}
			}
			
			//Search by composer
			else if (options.getSelectedIndex() == 3) {
				
				String sql = "SELECT ID,STAT_ID,LINK,NAME,PUBLISHER_NAME,COMPOSER FROM POEM WHERE COMPOSER = ?" ;
				
				try {
					PreparedStatement stmt = connection.prepareStatement(sql) ;
					stmt.setString (1, data.getText()) ;
					ResultSet rs = stmt.executeQuery();
					
					while (rs.next()) {
						text.setText(text.getText() +"ID:" + rs.getInt("ID") + "     " + "STAT_ID:" + rs.getInt("STAT_ID") + "     " + "LINK:" + rs.getString("LINK") + "     " +
								"NAME:" +	rs.getString("NAME") + "     " + "PUBLISHER_NAME:" + rs.getString("PUBLISHER_NAME") + "     " + "COMPOSER:" + rs.getString("COMPOSER") + "\n");
					}
					
				} catch (SQLException exp) {
					exp.printStackTrace();
				}
			}
			
			//Search by word
			else if (options.getSelectedIndex() == 4) {
				
				String sql = "SELECT DISTINCT POEM.ID,STAT_ID,LINK,NAME,PUBLISHER_NAME,COMPOSER FROM WORD,LINE,POEM WHERE LINE.ID = WORD.LINE_ID AND LINE.POEM_ID = POEM.ID AND WORD.VALUE = ?" ;
				
				try {
					PreparedStatement stmt = connection.prepareStatement(sql) ;
					stmt.setString (1, data.getText()) ;
					ResultSet rs = stmt.executeQuery();
					
					while (rs.next()) {
						text.setText(text.getText() +"ID:" + rs.getInt("ID") + "     " + "STAT_ID:" + rs.getInt("STAT_ID") + "     " + "LINK:" + rs.getString("LINK") + "     " +
								"NAME:" +	rs.getString("NAME") + "     " + "PUBLISHER_NAME:" + rs.getString("PUBLISHER_NAME") + "     " + "COMPOSER:" + rs.getString("COMPOSER") + "\n");
					}
					
				} catch (SQLException exp) {
					exp.printStackTrace();
				}
			}
			
		}
		
		
		
		
		else if (e.getSource() == back) {
			Functions funcs = new Functions (connection) ;
			removeAll ();
			revalidate() ;
			repaint() ;
			this.setLayout(new GridLayout(1,0));
			add(funcs) ;
			revalidate() ;
			repaint() ;
		}
	}

}
