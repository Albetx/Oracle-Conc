import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;


public class SearchByGroup extends JPanel implements ActionListener {

	private Connection connection ;
	private JButton back ;
	private JComboBox groups ;
	private JButton search ;
	private JTextArea result ;
	
	
	public SearchByGroup (Connection connection) {
		
		this.connection = connection ;
		
		groups = new JComboBox () ;
		ArrayList<String> groupNames = new ArrayList<String> () ;
		
		String sql = "SELECT DISTINCT NAME FROM WORDS_GROUP" ;
		
		try {
			PreparedStatement stmt = connection.prepareStatement(sql) ;
			ResultSet rs = stmt.executeQuery() ;
			while (rs.next()) 
				groupNames.add(rs.getString("NAME")) ;

			for (int i = 0; i < groupNames.size(); i++) {
				groups.addItem(groupNames.get(i));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		search = new JButton ("SEARCH") ;
		search.addActionListener(this);
		
		this.setLayout(new GridLayout(4,0));
		back = new JButton ("BACK") ;
		back.setForeground(Color.gray) ;
		back.addActionListener(this);
		
		result = new JTextArea () ;
		JScrollPane scrollPane = new JScrollPane(result); 
		scrollPane.setBounds(10,60,780,500);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		result.setEditable(false);
		
		JPanel p1 = new JPanel () ;
		p1.add (groups) ;
		p1.add(search) ;
		
		add (p1) ;
		add (scrollPane) ;
		add (result) ;
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
		
		
		else if (e.getSource() == search) {
			
			result.setText("");
			
			String sql = "SELECT WORD.VALUE AS WV,WORDS_GROUP.ID  FROM WORD,WORDS_GROUP WHERE WORD.GROUP_ID = WORDS_GROUP.ID AND WORDS_GROUP.NAME = ? ORDER BY WORDS_GROUP.ID" ;
			
			try {
				PreparedStatement stmt = connection.prepareStatement(sql) ;
				stmt.setString(1, groups.getSelectedItem().toString());
				ResultSet rs = stmt.executeQuery();
				for (int i=0; rs.next() ; i++) { 
					result.append(i+1 + ". " + rs.getString ("WV") + "\n");
				}
			} catch (SQLException exp) {
				// TODO Auto-generated catch block
				exp.printStackTrace();
			} 
		}
		
	}

}
