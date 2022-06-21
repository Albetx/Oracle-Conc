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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class searchByIndex extends JPanel implements ActionListener {
	
	
	private Connection connection ;
	private JButton back ;
	private JTextField poem ;
	private JTextField line ;
	private JTextField index ;
	private JButton search ;
	private JTextField result ;
	
	
	
	public searchByIndex (Connection connection) {
		
		this.connection = connection ;
		
		poem = new JTextField ("Poem name") ;
		line = new JTextField ("Line number") ;
		index = new JTextField ("Index of word") ;
		search = new JButton ("SEARCH") ;
		
		search.addActionListener(this);
		
		result = new JTextField (15) ;
		
		JPanel top = new JPanel () ;
		
		top.add(poem);
		top.add(line);
		top.add(index);
		
		
		this.setLayout(new GridLayout(4,0));
		back = new JButton ("BACK") ;
		back.setForeground(Color.gray) ;
		back.addActionListener(this);
		
		add (top) ;
		add (search) ;
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
		
		
		if (e.getSource() == search) {
		
			String sql = "SELECT LINE.VALUE AS LV FROM POEM,LINE WHERE POEM.ID = LINE.POEM_ID AND POEM.NAME =? AND LINE.LINE_INDEX = ? " ;
			try {
				PreparedStatement stmt = connection.prepareStatement(sql) ;
				stmt.setString(1, poem.getText());
				stmt.setInt(2, Integer.parseInt(line.getText()));
				
				ResultSet rs = stmt.executeQuery();
				
				if (rs.next()) {
					String line = rs.getString("LV") ;
					String [] sptLine = line.split(" ") ;
					
					if (Integer.parseInt(index.getText()) <= sptLine.length)
							result.setText(sptLine [Integer.parseInt(index.getText()) -1].replaceAll("[^A-Za-z]" , ""));
				}
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		
	
}


}
