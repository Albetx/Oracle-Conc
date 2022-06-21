import javax.swing.JFrame;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JTable;

public class Main {

	public static void main(String[] args) {
		
		
		String dbURL = "jdbc:oracle:thin:@localhost:1521:xe" ;
		String username = "system" ;
		String password = "11223344" ;
				
		try {
		Connection connection = DriverManager.getConnection(dbURL,username,password) ;
		System.out.println("Connected");
		
		JFrame app = new JFrame ("Concordance Solutions") ;
		app.setSize(450,450);
		app.setVisible(true);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		app.add((new UserPanel (connection))) ;
		
		app.revalidate() ;
		app.repaint() ;
		
		} catch (SQLException e) {
			System.out.println("Error:");
			e.printStackTrace();
		}





	}

}
