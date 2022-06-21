
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat.Style;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.*;
import java.awt.*;

import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;



public class ShowAllWords extends JPanel implements ActionListener {
	
	private Connection connection ;
	private JButton back ;
	private JButton submit ;
	private JComboBox options ;
	private JComboBox wordList ;
	private JTextField data ;
	private JTextPane text ;
	private DefaultStyledDocument document ;
	private JScrollPane scrollPane ;
	
	public ShowAllWords (Connection connection) {
		
		submit = new JButton ("SUBMIT") ;
		submit.addActionListener(this);
		
		String[] opt = { "Poem names", "From all poems"};
		options = new JComboBox (opt) ;
		
		wordList = new JComboBox () ;
		data = new JTextField (15) ;
		
		back = new JButton ("BACK") ;
		
		text = new JTextPane  ();
		text.setContentType("text/html");
		scrollPane = new JScrollPane(text); 
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		text.setEditable(false);
		
		this.connection = connection ;
		
		
		this.setLayout(new GridLayout(5,0));
		
		JPanel top = new JPanel () ;
		top.add(options) ;
		top.add(data) ;
		top.add(submit) ;
		
		JLabel lbl = new JLabel ("Context:") ;
		
		add(top) ;
		add(wordList) ;
		add (lbl) ;
		add (scrollPane) ;
		add(back) ;
		
		back.setForeground(Color.gray) ;
		back.addActionListener(this);
		
		wordList.addActionListener(this);
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
		
		
		if (e.getSource() == submit) {
			
			ArrayList<String> words = new ArrayList<String>();
			wordList.removeAllItems();
			
			String sql = "SELECT ID,LINE_ID,VALUE,LINGUISTIC_EXPRESSION,LENGTH,WORD_INDEX,GROUP_ID FROM WORD" ;
			
			if (options.getSelectedIndex() == 0 && options.getSelectedItem() != null) 
				sql = "SELECT DISTINCT WORD.ID,LINE_ID,WORD.VALUE,LINGUISTIC_EXPRESSION,LENGTH,WORD_INDEX,GROUP_ID FROM WORD,LINE,POEM WHERE LINE.ID = WORD.LINE_ID AND LINE.POEM_ID = POEM.ID AND POEM.NAME = ?" ;
			
			try {
				PreparedStatement stmt = connection.prepareStatement(sql) ;
				if (options.getSelectedIndex() == 0)
					stmt.setString (1, data.getText()) ;
				ResultSet rs = stmt.executeQuery();
				
				while (rs.next()) {
					words.add(rs.getString("VALUE"));
				}
				
			} catch (SQLException exp) {
				// TODO Auto-generated catch block
				exp.printStackTrace();
			}
			

			for (int i=0 ; i<words.size() ; i++) {
				wordList.addItem(words.get(i)) ;
			}

			
			
		}
		
		
		else if (e.getSource() == wordList) {
			
			text.setText("");
			String selectedWord="" ;
			if (wordList.getSelectedItem() != null)
				selectedWord = wordList.getSelectedItem().toString() ;
			
			// Getting line index
			String sql = "SELECT LINE_INDEX,POEM_ID FROM WORD,LINE WHERE WORD.VALUE = ? AND WORD.LINE_ID = LINE.ID" ;
			int lineIndex = 0 ;
			int poemID = 0 ;
			try {
				PreparedStatement stmt = connection.prepareStatement(sql) ;
				
				stmt.setString (1,selectedWord) ;
				ResultSet rs1 = stmt.executeQuery();
				if (rs1.next()) {
					lineIndex = rs1.getInt("LINE_INDEX") ;
					poemID = rs1.getInt("POEM_ID") ;
				}
				else 
					System.out.println("Error while looking for lines containing a word#1");
				
			} catch (SQLException exp) {
				// TODO Auto-generated catch block
				exp.printStackTrace();
			}

			// Getting the line and the line before and after for context
			sql = "SELECT DISTINCT LINE_INDEX,LINE.VALUE FROM LINE,WORD WHERE WORD.VALUE = ? AND LINE.POEM_ID = ? AND (LINE_INDEX = ? OR LINE_INDEX = ? OR LINE_INDEX = ?) ORDER BY LINE_INDEX " ;
			
			String resultText = new String () ;
			
			try {
				PreparedStatement stmt = connection.prepareStatement(sql) ;
				stmt.setString (1, selectedWord) ;
				stmt.setInt (2, poemID) ;
				stmt.setInt (3, lineIndex) ;
				stmt.setInt (4, lineIndex-1) ;
				stmt.setInt (5, lineIndex+1) ;
				ResultSet rs2 = stmt.executeQuery();
				while (rs2.next())
					resultText += "\n" + rs2.getString("VALUE");
				
			} catch (SQLException exp) {
				// TODO Auto-generated catch block
				exp.printStackTrace();
			}
			
			resultText = resultText.replace(selectedWord, "<b>" + selectedWord + "</b>") ;
			resultText = resultText.replaceAll("\n", "<br>") ;
			
			text.setText("<html>" + resultText + "</html>");
			
		}
		
	}

}
