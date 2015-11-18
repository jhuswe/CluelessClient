package panels;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ConnectToServerPanel 
	extends JPanel
{
	private final static int IPADDX_CHAR_NUM = 15;
	
	protected String ipaddrx;
	protected JTextField input;
	protected JButton okay;
	protected JLabel label1;
	protected JLabel label2;
	protected boolean badAddress;
	
	public ConnectToServerPanel()
	{
		super ( new GridLayout() );
		badAddress = false;
	}

	public void createComponents()
	{
		label1 = new JLabel( "Please Enter Server's IP Address:" ); 
		label2 = new JLabel( "Invalid Address. Please try again." );
		label2.setForeground( Color.RED );
		label2.setVisible( false );
		
		input = new JTextField();
		input.setColumns( IPADDX_CHAR_NUM );
		okay = new JButton( "Okay" );
		okay.setSize( 6, 1 );

		okay.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ipaddrx = input.getText();
				System.out.println("[" + this.getClass() + "] " + "User input is: " + ipaddrx );
				if( ipaddrx == null || ipaddrx.isEmpty() )
				{
					badAddress = true;
					label2.setVisible( true );
				}
				else
					label2.setVisible( false );
			}			
		});
	}
	
	public void layoutComponents() 
	{
		setPreferredSize( new Dimension( 250, 200 ) );
		JPanel pane = new JPanel( new GridBagLayout() );
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTH;
		
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		pane.add( label1, gbc );
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		pane.add( input, gbc );
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		pane.add( label2, gbc );
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		pane.add( okay, gbc );
		
		add( pane );
	}
}
