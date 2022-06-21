import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ExportToXml {
	

	public static void exportDB (Connection connection) {
		
		String xmlP = new String () ;
		String xmlL = new String () ;
		String xmlW = new String () ;
		String xmlLE = new String () ;
		String xmlWG = new String () ;
		String xmlS = new String () ;
		
		File fileP = new File("Poem-Backup.xml");
		File fileL = new File("Line-Backup.xml");
		File fileW = new File("Word-Backup.xml");
		File fileLE = new File("LE-Backup.xml");
		File fileWG = new File("WordGroup-Backup.xml");
		File fileS = new File("Statistics-Backup.xml");
		
	    try {
			fileP.createNewFile() ;
			fileL.createNewFile() ;
			fileW.createNewFile() ;
			fileLE.createNewFile() ;
			fileWG.createNewFile() ;
			fileS.createNewFile() ;
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	    
	
		xmlP = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n"
				+ "<POEMS>\r\n" ;
		
		String sql = "SELECT ID,STAT_ID,LINK,NAME,PUBLISHER_NAME,COMPOSER FROM POEM" ;
		try {
			PreparedStatement stmt = connection.prepareStatement(sql) ;
			ResultSet rs = stmt.executeQuery() ;
			while (rs.next()) {
				xmlP += "\t<POEM>\n" + "\t<ID>" + rs.getInt("ID") + "</ID>\n" ;
				xmlP += "\t<STAT_ID>" + rs.getInt("STAT_ID") + "</STAT_ID>\n" ;
				xmlP += "\t<LINK>" + rs.getString("LINK") + "</LINK>\n" ;
				xmlP += "\t<NAME>" + rs.getString("NAME") + "</NAME>\n" ;
				xmlP += "\t<PUBLISHER_NAME>" + rs.getString("PUBLISHER_NAME") + "</PUBLISHER_NAME>\n" ;
				xmlP += "\t<COMPOSER>" + rs.getString("COMPOSER") + "</COMPOSER>\n" + "\t</POEM>\n" ;
			}
			
			xmlP += "</POEMS>" ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		//////////////////////////////////////
		
		xmlL = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n"
				+ "<LINES>\r\n" ;
		
		sql = "SELECT ID,VALUE,POEM_ID,LINE_INDEX FROM LINE" ;
		try {
			PreparedStatement stmt = connection.prepareStatement(sql) ;
			ResultSet rs = stmt.executeQuery() ;
			while (rs.next()) {
				xmlL += "\t<LINE>\n" + "\t<ID>" + rs.getInt("ID") + "</ID>\n" ;
				xmlL += "\t<VALUE>" + rs.getString("VALUE") + "</VALUE>\n" ;
				xmlL += "\t<POEM_ID>" + rs.getInt("POEM_ID") + "</POEM_ID>\n" ;
				xmlL += "\t<LINE_INDEX>" + rs.getInt("LINE_INDEX") + "</LINE_INDEX>\n" + "\t</LINE>\n" ;
			}
			
			xmlL += "</LINES>" ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//////////////////////////////////////
		
		
		xmlW = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n"
				+ "  <WORDS>\n" ;
		
		sql = "SELECT ID,LINE_ID,VALUE,LINGUISTIC_EXPRESSION,LENGTH,WORD_INDEX,GROUP_ID FROM WORD" ;
		try {
			PreparedStatement stmt = connection.prepareStatement(sql) ;
			ResultSet rs = stmt.executeQuery() ;
			while (rs.next()) {
				xmlW += "\t<WORD>\n" + "\t<ID>" + rs.getInt("ID") + "</ID>\n" ;
				xmlW += "\t<LINE_ID>" + rs.getInt("LINE_ID") + "</LINE_ID>\n" ;
				xmlW += "\t<VALUE>" + rs.getString("VALUE") + "</VALUE>\n" ;
				xmlW += "\t<LINGUISTIC_EXPRESSION>" + rs.getString("LINGUISTIC_EXPRESSION") + "</LINGUISTIC_EXPRESSION>\n"  ;
				xmlW += "\t<LENGTH>" + rs.getInt("LENGTH") + "</LENGTH>\n" ;
				xmlW += "\t<WORD_INDEX>" + rs.getInt("WORD_INDEX") + "</WORD_INDEX>\n" ;
				xmlW += "\t<GROUP_ID>" + rs.getInt("GROUP_ID") + "</GROUP_ID>\n" + "\t</WORD>\n" ;
			}
			
			xmlW += "</WORDS>" ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//////////////////////////////////////
		
		
		xmlLE = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n"
				+ "<LINGUISTIC_EXPRESSIONS>\r\n" ;
		
		sql = "SELECT NAME FROM LINGUISTIC_EXPRESSION" ;
		try {
			PreparedStatement stmt = connection.prepareStatement(sql) ;
			ResultSet rs = stmt.executeQuery() ;
			while (rs.next()) {
				xmlLE += "\t<LINGUISTIC_EXPRESSION>\n" + "\t<NAME>" + rs.getString("NAME") + "</NAME>\n" + "\t</LINGUISTIC_EXPRESSION>\n" ;
			}
			
			xmlLE += "</LINGUISTIC_EXPRESSIONS>" ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//////////////////////////////////////
		
		
		xmlWG = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n"
				+ "<WORDES_GROUPS>\r\n" ;
		
		sql = "SELECT ID,NAME FROM WORDS_GROUP" ;
		try {
			PreparedStatement stmt = connection.prepareStatement(sql) ;
			ResultSet rs = stmt.executeQuery() ;
			while (rs.next()) {
				xmlWG += "\t<WORDES_GROUP>\n" + "\t<ID>" + rs.getInt("ID") + "</ID>\n" ;
				xmlWG += "\t<NAME>" + rs.getString("NAME") + "</NAME>\n" + "\t</WORDES_GROUP>\n" ;
			}
			
			xmlWG += "</WORDES_GROUPS>" ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//////////////////////////////////////
		
		
		xmlS = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n"
				+ "<ALL_STATISTICS>\r\n" ;
		
		sql = "SELECT STAT_ID,AVG_CHARS_IN_A_WORD,AVG_CHARS_IN_A_SENTENCE FROM STATISTICS" ;
		try {
			PreparedStatement stmt = connection.prepareStatement(sql) ;
			ResultSet rs = stmt.executeQuery() ;
			while (rs.next()) {
				xmlS += "\t<STATISTICS>\n" + "\t<STAT_ID>" + rs.getInt("STAT_ID") + "</STAT_ID>\n" ;
				xmlS += "\t<AVG_CHARS_IN_A_WORD>" + rs.getInt("AVG_CHARS_IN_A_WORD") + "</AVG_CHARS_IN_A_WORD>\n" ;
				xmlS += "\t<AVG_CHARS_IN_A_SENTENCE>" + rs.getInt("AVG_CHARS_IN_A_SENTENCE") + "</AVG_CHARS_IN_A_SENTENCE>\n" + "\t</STATISTICS>\n" ;
			}
			
			xmlS += "</ALL_STATISTICS>" ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			FileWriter fileR = new FileWriter("Poem-Backup.xml");
			fileR.write(xmlP);
			fileR.close();
			fileR = new FileWriter("Line-Backup.xml");
			fileR.write(xmlL);
			fileR.close();
			fileR = new FileWriter("Word-Backup.xml");
			fileR.write(xmlW);
			fileR.close();
			fileR = new FileWriter("LE-Backup.xml");
			fileR.write(xmlLE);
			fileR.close();
			fileR = new FileWriter("WordGroup-Backup.xml");
			fileR.write(xmlWG);
			fileR.close();
			fileR = new FileWriter("Statistics-Backup.xml");
			fileR.write(xmlS);
			fileR.close();
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
