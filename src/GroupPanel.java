import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JPanel;


public class GroupPanel extends JPanel implements ActionListener {

	private Connection connection ;
	private JButton newGroup ;
	private JButton addWords ;
	private JButton back ;
	
	
	public GroupPanel (Connection connection) {
		
		this.connection = connection ;
		
		newGroup = new JButton ("New group") ;
		addWords = new JButton ("Add words to group") ;
		
		this.setLayout(new GridLayout(3,0));
		back = new JButton ("BACK") ;
		back.setForeground(Color.gray) ;
		back.addActionListener(this);
		
		newGroup.addActionListener(this);
		addWords.addActionListener(this);
		
		add (newGroup) ;
		add (addWords) ;
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
		
		if (e.getSource() == newGroup) {
			NewGroup ng = new NewGroup (connection) ;
			removeAll ();
			revalidate() ;
			repaint() ;
			this.setLayout(new GridLayout(1,0));
			add(ng) ;
			revalidate() ;
			repaint() ;
		}
		
		if (e.getSource() == addWords) {
			AddWordsToGroup awtg = new AddWordsToGroup (connection) ;
			removeAll ();
			revalidate() ;
			repaint() ;
			this.setLayout(new GridLayout(1,0));
			add(awtg) ;
			revalidate() ;
			repaint() ;
		}
		
	}
	
	

}
