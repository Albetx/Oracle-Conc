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
import javax.swing.ScrollPaneConstants;


public class ShowByIndex extends JPanel implements ActionListener{

	private final int MAX_POEMS = 15 ;
	
	private Connection connection ;
	private JButton back ;
	private JTextArea text ;
	private JComboBox poem ;
	private String[] poemNames ;
	
	
	public ShowByIndex (Connection connection) {
		
		this.connection = connection ;
		
		poemNames = new String [MAX_POEMS] ;
		
		text = new JTextArea () ;
		JScrollPane scrollPane = new JScrollPane(text); 
		scrollPane.setBounds(10,60,780,500);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		text.setEditable(false);
		                                     
		
		String sql = "SELECT NAME FROM POEM" ;
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			for (int i=0 ; i<poemNames.length && rs.next() ; i++) {
				if (rs.getString("NAME") != null)
					poemNames [i] = rs.getString("NAME") ;
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		poem = new JComboBox (poemNames) ;
		poem.addActionListener(this);
		JPanel poemCB = new JPanel () ;
		poemCB.add (poem) ;
		
		this.setLayout(new GridLayout(3,0));
		back = new JButton ("BACK") ;
		back.setForeground(Color.gray) ;
		back.addActionListener(this);
		
		
		add (poemCB) ;
		add (scrollPane) ;
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
		
		
		if (e.getSource() == poem) {
			
			String sql = "SELECT DISTINCT POEM.NAME AS pn,LINE.VALUE as lVal,WORD.VALUE AS wVal,LINE.LINE_INDEX FROM POEM,LINE,WORD WHERE LINE.ID = WORD.LINE_ID AND LINE.POEM_ID = POEM.ID AND POEM.NAME = ? ORDER BY POEM.NAME" ;

			String poemName = new String () ;
			String line = new String ();
			String word = new String ();
			String [] splitLine ;
			ArrayList<Integer> wordIndexes = new ArrayList<Integer> () ;
			int lineIndex ;
	 		
			try {
				PreparedStatement stmt = connection.prepareStatement(sql) ;
				stmt.setString (1, poem.getSelectedItem().toString()) ;
				
				ResultSet rs = stmt.executeQuery();
				
				rs.next() ;
				poemName = rs.getString("pn");
				text.setText(text.getText() + "Poems name: " + poemName + "\n\n");
				do {
					line = rs.getString("lVal");
					word = rs.getString("wVal");
					poemName = rs.getString("pn");
					lineIndex = rs.getInt("LINE_INDEX");
					splitLine = line.split(" ") ;
					wordIndexes.clear();

					
					text.append (word + " -->  Line: " + lineIndex + " Index in line: ");
					
					for (int i=0 ; i<splitLine.length ; i++) {
						
						splitLine[i] = splitLine[i].replaceAll("[^A-Za-z]" , "") ;
						if (splitLine[i].equals(word)) {
							wordIndexes.add(i+1) ;
						}
						
					}
					
					for (int j=0 ; j<wordIndexes.size() ; j++) {
						text.setText(text.getText() + wordIndexes.get(j) + ",") ;
					}
					text.append("\n") ;
					
					wordIndexes.clear();
				}
				while (rs.next()) ;

				
			} catch (SQLException exp) {
				// TODO Auto-generated catch block
				exp.printStackTrace();
			}
			
		}
	}
	
}
