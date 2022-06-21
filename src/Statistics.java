import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;


public class Statistics extends JPanel implements ActionListener {

	private Connection connection ;
	private JButton back ;
	private JTextArea result ;
	
	
	public Statistics (Connection connection) {
		
		this.connection = connection ;
		
		result = new JTextArea () ;
		JScrollPane scrollPane = new JScrollPane(result); 
		scrollPane.setBounds(10,60,780,500);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		result.setEditable(false);
		
		Font font = result.getFont();
		float size = font.getSize() + 1.2f;
		result.setFont( font.deriveFont(size) );
		
		String sql = "SELECT DISTINCT NAME,AVG_CHARS_IN_A_WORD,AVG_CHARS_IN_A_SENTENCE FROM POEM,STATISTICS WHERE POEM.STAT_ID = STATISTICS.STAT_ID" ;
		try {
			PreparedStatement stmt = connection.prepareStatement(sql) ;
			ResultSet rs = stmt.executeQuery() ;
			result.append("\n");
			while (rs.next()) 
				result.append("Poem name:" + rs.getString("NAME") + ", AVG chars in a word: " + rs.getString("AVG_CHARS_IN_A_WORD") + ", AVG chars in a sentence: " + rs.getString("AVG_CHARS_IN_A_SENTENCE") + "\n");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		this.setLayout(new GridLayout(2,0));
		back = new JButton ("BACK") ;
		back.setForeground(Color.gray) ;
		back.addActionListener(this);
		
		add (result);
		add (back);
		
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
		
	}

}
