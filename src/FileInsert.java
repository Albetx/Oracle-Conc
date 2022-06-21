import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import javax.swing.*;


public class FileInsert extends JPanel implements ActionListener{

	private final int POEM_ID_TO_STAT_ID_MULT = 1000;
	
	private JButton chooseFile ;
	private JButton upload ;
	private JTextField fileDir ;
	private JTextField poemLink ;
	private JTextField poemName ;
	private JTextField poemPublisherName ;
	private JTextField poemComposer ;
	private JFileChooser fc = new JFileChooser();
	private Scanner reader ;
	private Connection connection ;
	private JButton back ;
	
	private float charsInAllSent ;
	private int noOfSent ;
	private int linesInserted ;
	private int lineIndex ;
	private float charsInAllWords;
	private int wordesInserted ;
	private int wordIndex ;
	private Scanner scnLine;
	private int lineID ;
	private int poemID ;
	
	
	public FileInsert (Connection connection) {
		
		this.connection = connection ;
		chooseFile = new JButton ("Choose file") ;
		upload = new JButton ("Upload") ;
		fileDir = new JTextField () ;
		
		poemLink = new JTextField ("Link") ;
		poemName = new JTextField ("Name") ;
		poemPublisherName = new JTextField ("Publisher name") ;
		poemComposer = new JTextField ("Composer") ;
		
		fileDir.setEditable(false);
		
		chooseFile.addActionListener(this);
		upload.addActionListener(this);
		
		this.setLayout(new GridLayout(4,1));
		
		back = new JButton ("BACK") ;
		back.setForeground(Color.gray) ;
		back.addActionListener(this);
		
		add (chooseFile) ;
		add (fileDir) ;
		add (poemLink) ;
		add (poemName) ;
		add (poemPublisherName) ;
		add (poemComposer) ;
		add (upload) ;
		add (back) ;
		
		revalidate() ;

	}
	
	
	public void actionPerformed (ActionEvent e) {
		
		if (e.getSource() == chooseFile) {
			fc.showOpenDialog(this);
			try {
				if (fc.getSelectedFile() != null) {
					reader = new Scanner(fc.getSelectedFile());
					fileDir.setText(fc.getSelectedFile().getName());
				}
				repaint() ;
				revalidate() ;
			} catch (FileNotFoundException e1) {
				System.out.println("File not found");
			}
		}
		
		
		
		else if (e.getSource() == upload) {
			
			
			try {
				String sql = "INSERT INTO POEM (LINK,NAME,PUBLISHER_NAME,COMPOSER) VALUES (?,?,?,?)";
				String[] returnID = {"ID"} ;
				
				PreparedStatement stmt = connection.prepareStatement(sql, returnID) ;
				
				stmt.setString (1, poemLink.getText()) ;
				stmt.setString (2, poemName.getText()) ;
				stmt.setString (3, poemPublisherName.getText()) ;
				stmt.setString (4, poemComposer.getText()) ;
				
				int sqlCode = stmt.executeUpdate() ;
				
				ResultSet rs0 = stmt.getGeneratedKeys() ;
				
				poemID = 0;
				if (rs0.next()) {
					poemID = rs0.getInt(1) ;
				}
				else 
					System.out.println("File inserting error#1");

				rs0.close();
				
				if (sqlCode > 0) {
					System.out.println("New poem inserted");
				}
				

				

				
				
				// Calculating AVG chars in a sentence and creating new lines
				charsInAllSent = 0;
				noOfSent = 0 ;
				linesInserted = 0 ;
				lineIndex = 1 ;
				charsInAllWords = 0;
				wordesInserted = 0 ;
				wordIndex = 1 ;
				lineID = 0;
				while (reader.hasNext()) {
					
					String curSentence = reader.nextLine() ;
					
					insertNewLinesToDB (curSentence, sqlCode) ;
					
					
					charsInAllWords = 0;
					wordesInserted = 0 ;
					wordIndex = 1 ;
					
					// Inserting words
					scnLine = new Scanner (curSentence) ;
					while (scnLine.hasNext()) {
						
						insertWordes (stmt,scnLine,sqlCode) ;
						
					}
					
					stmt.close();
					
					
					if (sqlCode > 0) {
						linesInserted++ ;
					}
					
					lineIndex++ ;
					
					curSentence.replaceAll(" ", "") ; // Removing spaces
					charsInAllSent += curSentence.length() ;
					noOfSent++ ;
					
					if (!reader.hasNext()) {
						scnLine.close();
					}
					
					
				}
				

				
				if (wordIndex-1 == wordesInserted) 
					System.out.println("All words inserted successfully");
				
				reader = new Scanner(fc.getSelectedFile());
				
				
				if (lineIndex-1 == linesInserted) 
					System.out.println("All lines inserted successfully");

				int avgCharsInWord = Math.round(charsInAllWords / (wordIndex-1)) ; // Index starts with 1
				int avgCharsInSent = Math.round(charsInAllSent / noOfSent) ;
				
				// Create statistics for inserted poem
				sql = "INSERT INTO STATISTICS (STAT_ID,AVG_CHARS_IN_A_WORD,AVG_CHARS_IN_A_SENTENCE) VALUES (?,?,?)" ;
				stmt = connection.prepareStatement(sql) ;
				int statID = poemID * POEM_ID_TO_STAT_ID_MULT ;
				stmt.setInt (1, statID) ;
				stmt.setInt (2, avgCharsInWord) ;
				stmt.setInt (3, avgCharsInSent) ;

				sqlCode = stmt.executeUpdate() ;

				if (sqlCode > 0) {
					System.out.println("Statistics created successfully");
					JOptionPane.showMessageDialog (this, "File inserted successfully") ;
				}
				
				sql = "UPDATE POEM SET STAT_ID = ? WHERE ID = ?" ;
				stmt = connection.prepareStatement(sql) ;
				stmt.setInt (1, statID) ;
				stmt.setInt (2, poemID) ;
				stmt.executeUpdate() ;
				
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		
		else if (e.getSource() == back) {
			UserPanel up = new UserPanel (connection) ;
			removeAll ();
			revalidate() ;
			repaint() ;
			this.setLayout(new GridLayout(1,0));
			add(up) ;
			revalidate() ;
			repaint() ;
		}

		
	}
	
	
	public void insertNewLinesToDB (String curSentence, int sqlCode) {
		
		while (curSentence == "\n" || curSentence == "\r" || curSentence == "\t" || curSentence == " "  || curSentence == "") {
			curSentence = reader.nextLine() ;
		}

		String sql = "INSERT INTO LINE (VALUE,POEM_ID,LINE_INDEX) VALUES (?,?,?)" ;
		String[] returnLineID = {"ID"} ;

		try {
		PreparedStatement stmt = connection.prepareStatement(sql , returnLineID );
		
		stmt.setString (1, curSentence) ;
		stmt.setInt (2, poemID) ;
		stmt.setInt (3, lineIndex) ;
		sqlCode = stmt.executeUpdate() ;
		
		ResultSet rs = stmt.getGeneratedKeys() ;
		if (rs.next()) {
			lineID = rs.getInt(1) ;
		}
		else 
			System.out.println("File inserting error#2");
		rs.close();
		stmt.close();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void insertWordes (PreparedStatement stmt,Scanner scnLine, int sqlCode) {
		
		String curWord = scnLine.next() ;

		// Removes all the chars that is not letters
		curWord = curWord.replaceAll("[^A-Za-z]" , "") ;

		
		charsInAllWords += curWord.length() ;
		
		String sql = "INSERT INTO WORD (LINE_ID,VALUE,LENGTH,WORD_INDEX) VALUES (?,?,?,?)" ;
		
		try {
			stmt = connection.prepareStatement(sql);
		
			stmt.setInt (1, lineID) ;
			stmt.setString (2, curWord) ;
			stmt.setInt (3, curWord.length()) ;
			stmt.setInt (4, wordIndex) ;
			
			sqlCode = stmt.executeUpdate() ;
			if (sqlCode > 0) {
				wordesInserted++ ;
			}
			
			wordIndex++ ;		
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	

}