import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;


public class Functions extends JPanel implements ActionListener {
	
	private JButton showAllWords ;
	private JButton showByInfo ;
	private JButton searchByIndex ;
	private JButton showByIndex ;
	private JButton groupPanel ;
	private JButton searchByGroup ;
	private JButton createLE ;
	private JButton searchLE ;
	private JButton statistics ;
	private Connection connection ;
	private JButton back ;
	
	public Functions (Connection connection) {
		
		back = new JButton ("BACK") ;
		
		this.connection = connection ;
		
		showAllWords = new JButton ("Show all words") ;
		showByInfo = new JButton ("Show by info") ;
		searchByIndex = new JButton ("Search by index") ;
		showByIndex = new JButton ("Show by index") ;
		groupPanel = new JButton ("Manage groups") ;
		searchByGroup = new JButton ("Search by group") ;
		createLE = new JButton ("Create linguistic expression") ;
		searchLE = new JButton ("Search linguistic expression") ;
		statistics = new JButton ("Statistics") ;
		
		this.setLayout(new GridLayout(5,2));
		
		add (showAllWords) ;
		add (showByInfo) ;
		add (showByIndex) ;
		add (searchByIndex) ;
		add (groupPanel) ;
		add (searchByGroup) ;
		add (createLE) ;
		add (searchLE) ;
		add (statistics) ;
		add (back) ;
		
		back.setForeground(Color.gray) ;
		statistics.setForeground(Color.blue) ;
		
		showAllWords.addActionListener(this);
		back.addActionListener(this);
		showByInfo.addActionListener(this);
		showByIndex.addActionListener(this);
		searchByIndex.addActionListener(this);
		groupPanel.addActionListener (this) ;
		searchByGroup.addActionListener(this);
		createLE.addActionListener(this);
		searchLE.addActionListener(this);
		statistics.addActionListener(this);
		
	}
	
	
	
	public void actionPerformed(ActionEvent e) {

		
		if (e.getSource() == showAllWords) {
			
			ShowAllWords saw = new ShowAllWords (connection) ;
			removeAll ();
			revalidate() ;
			repaint() ;
			this.setLayout(new GridLayout(1,0));
			add(saw) ;
			revalidate() ;
			repaint() ;
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
		
		else if (e.getSource() == showByInfo) {
			ShowByInfo inf = new ShowByInfo (connection) ;
			removeAll ();
			revalidate() ;
			repaint() ;
			this.setLayout(new GridLayout(1,0));
			add(inf) ;
			revalidate() ;
			repaint() ;
		}
		
		else if (e.getSource() == showByIndex) {
			ShowByIndex sbi = new ShowByIndex (connection) ;
			removeAll ();
			revalidate() ;
			repaint() ;
			this.setLayout(new GridLayout(1,0));
			add(sbi) ;
			revalidate() ;
			repaint() ;
		}
		
		else if (e.getSource() == searchByIndex) {
			searchByIndex sbi = new searchByIndex (connection) ;
			removeAll ();
			revalidate() ;
			repaint() ;
			this.setLayout(new GridLayout(1,0));
			add(sbi) ;
			revalidate() ;
			repaint() ;
		}
		
		else if (e.getSource() == groupPanel) {
			GroupPanel gp = new GroupPanel (connection) ;
			removeAll ();
			revalidate() ;
			repaint() ;
			this.setLayout(new GridLayout(1,0));
			add(gp) ;
			revalidate() ;
			repaint() ;
		}
		
		else if (e.getSource() == searchByGroup) {
			SearchByGroup sbg = new SearchByGroup (connection) ;
			removeAll ();
			revalidate() ;
			repaint() ;
			this.setLayout(new GridLayout(1,0));
			add(sbg) ;
			revalidate() ;
			repaint() ;
		}
		
		else if (e.getSource() == createLE) {
			CreateLE cle = new CreateLE (connection) ;
			removeAll ();
			revalidate() ;
			repaint() ;
			this.setLayout(new GridLayout(1,0));
			add(cle) ;
			revalidate() ;
			repaint() ;
		}
		
		else if (e.getSource() == searchLE) {
			SearchLE sle = new SearchLE (connection) ;
			removeAll ();
			revalidate() ;
			repaint() ;
			this.setLayout(new GridLayout(1,0));
			add(sle) ;
			revalidate() ;
			repaint() ;
		}
		
		else if (e.getSource() == statistics) {
			Statistics stat = new Statistics (connection) ;
			removeAll ();
			revalidate() ;
			repaint() ;
			this.setLayout(new GridLayout(1,0));
			add(stat) ;
			revalidate() ;
			repaint() ;
		}
		
	}
	
	

}
