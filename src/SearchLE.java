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
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;


public class SearchLE extends JPanel implements ActionListener {

	private final int MAX_LINES_TO_FINED = 5 ;
	
	private Connection connection ;
	private JButton back ;
	private JTextPane text ;
	private JTextField typeLE ;
	private JComboBox chooseLE ;
	private JLabel choose ;
	private JLabel type ;
	private JLabel found ;
	private JButton submit1 ;
	private JButton submit2 ;
	private JButton submit3 ;
	private JComboBox chooseFromListOfLE ;
	private int [] lineIndex ;
	private int [] poemID ;
	private String selectedLE ;
	
	
	public SearchLE (Connection connection) {
		
		this.connection = connection ;
		
		selectedLE = new String () ;
		
		typeLE = new JTextField (20) ;
		
		lineIndex = new int [MAX_LINES_TO_FINED] ;
		poemID = new int [MAX_LINES_TO_FINED] ;
		
		text = new JTextPane  ();
		text.setContentType("text/html");
		JScrollPane scrollPane = new JScrollPane(text); 
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		text.setEditable(false);
		
		chooseFromListOfLE = new JComboBox () ;
		chooseLE = new JComboBox () ;
		
		ArrayList<String> les = new ArrayList<String> () ;
		
		String sql = "SELECT DISTINCT NAME FROM LINGUISTIC_EXPRESSION" ;
		
		try {
			PreparedStatement stmt = connection.prepareStatement(sql) ;
			ResultSet rs = stmt.executeQuery() ;
			while (rs.next()) 
				les.add(rs.getString("NAME")) ;

			for (int i = 0; i < les.size(); i++) {
				chooseLE.addItem(les.get(i));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		choose = new JLabel ("Choose LE: ") ;
		type = new JLabel ("OR type: ") ;
		found = new JLabel ("Found: ") ;
		
		submit1 = new JButton ("SUBMIT") ;
		submit2 = new JButton ("SUBMIT") ;
		submit3 = new JButton ("SUBMIT") ;
		
		submit1.addActionListener(this);
		submit2.addActionListener(this);
		submit3.addActionListener(this);
		
		this.setLayout(new GridLayout(5,0));
		back = new JButton ("BACK") ;
		back.setForeground(Color.gray) ;
		back.addActionListener(this);
		
		JPanel p1 = new JPanel ();
		JPanel p2 = new JPanel ();
		JPanel p3 = new JPanel ();
		
		p1.add(choose) ;
		p1.add(chooseLE);
		p1.add(submit1) ;
		p2.add(type) ;
		p2.add(typeLE) ;
		p2.add(submit2) ;
		p3.add(found) ;
		p3.add(chooseFromListOfLE) ;
		p3.add(submit3) ;
		
		
		add(p1) ;
		add(p2) ;
		add (p3) ;
		add(scrollPane) ;
		add(back) ;
		
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
		
		
		if (e.getSource() == submit1) {
			
			chooseFromListOfLE.removeAllItems();
			
			// Getting line index
			String sql = "SELECT VALUE,POEM_ID,LINE_INDEX FROM LINE" ;
			lineIndex = new int [MAX_LINES_TO_FINED] ;
			poemID = new int [MAX_LINES_TO_FINED] ;
			String line = new String () ;
			
			selectedLE = chooseLE.getSelectedItem().toString() ;
			
			try {
				PreparedStatement stmt = connection.prepareStatement(sql) ;
				ResultSet rs1 = stmt.executeQuery();
				int i = 0 ;
				while (rs1.next() && i<MAX_LINES_TO_FINED) {
					line = rs1.getString("VALUE") ;
					if (line.contains(chooseLE.getSelectedItem().toString())) {
						lineIndex [i] = rs1.getInt("LINE_INDEX") ;
						poemID [i] = rs1.getInt("POEM_ID") ;
						chooseFromListOfLE.addItem("Option:" + (i+1));
						i++ ;
					}
				}

				
			} catch (SQLException exp) {
				// TODO Auto-generated catch block
				exp.printStackTrace();
			}
			
		}
		
		else if (e.getSource() == submit2) {
			
			chooseFromListOfLE.removeAllItems();
			
			selectedLE = typeLE.getText() ;
			
			// Getting line index
			String sql = "SELECT VALUE,POEM_ID,LINE_INDEX FROM LINE" ;
			lineIndex = new int [MAX_LINES_TO_FINED] ;
			poemID = new int [MAX_LINES_TO_FINED] ;
			String line = new String () ;
			try {
				PreparedStatement stmt = connection.prepareStatement(sql) ;
				ResultSet rs1 = stmt.executeQuery();
				int i = 0 ;
				while (rs1.next() && i<MAX_LINES_TO_FINED) {
					line = rs1.getString("VALUE") ;
					if (line.contains(typeLE.getText())) {
						lineIndex [i] = rs1.getInt("LINE_INDEX") ;
						poemID [i] = rs1.getInt("POEM_ID") ;
						chooseFromListOfLE.addItem("Option:" + (i+1));
						i++ ;
					}
				}
			} catch (SQLException exp) {
				// TODO Auto-generated catch block
				exp.printStackTrace();
			}
			
		}
		
		else if (e.getSource() == submit3) {
			
			String sql = "SELECT VALUE,LINE_INDEX FROM LINE WHERE POEM_ID = ? AND (LINE_INDEX = ? OR LINE_INDEX = ? OR LINE_INDEX = ?) ORDER BY LINE_INDEX" ;
			
			String resultText = new String () ;
			try {
				PreparedStatement stmt = connection.prepareStatement(sql) ;
				stmt.setInt(1, poemID[chooseFromListOfLE.getSelectedIndex()]);
				stmt.setInt(2, lineIndex[chooseFromListOfLE.getSelectedIndex()]);
				stmt.setInt(3, lineIndex[chooseFromListOfLE.getSelectedIndex()]+1);
				stmt.setInt(4, lineIndex[chooseFromListOfLE.getSelectedIndex()]-1);
				
				ResultSet rs1 = stmt.executeQuery();
				
				while (rs1.next()) {
					resultText += "\n" + rs1.getString("VALUE");
				}
				
				resultText = resultText.replace(selectedLE, "<b>" + selectedLE + "</b>") ;
				resultText = resultText.replaceAll("\n", "<br>") ;
				
				text.setText("<html>" + resultText + "</html>");
				
			} catch (SQLException exp) {
				// TODO Auto-generated catch block
				exp.printStackTrace();
			}
		}
		
	}

}
