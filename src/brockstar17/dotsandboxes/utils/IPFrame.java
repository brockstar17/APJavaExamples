package brockstar17.dotsandboxes.utils;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import brockstar17.dotsandboxes.DBClient;

@SuppressWarnings("serial")
public class IPFrame extends JFrame implements ActionListener, ItemListener, WindowListener
{

	private int port = 22222;
	private String ip;

	private JTextField field;

	public IPFrame()
	{
		super("Enter Server IP");
		ip = "";

		this.setSize(300, 150);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		this.setLocationRelativeTo(null);

		Container c = this.getContentPane();
		c.setLayout(new FlowLayout());

		field = new JTextField();
		field.setColumns(20);
		// field.setToolTipText("Enter the IP address of the Server");
		field.addActionListener(this);

		c.add(field);

		JRadioButton radB = new JRadioButton("localhost", false);
		radB.addItemListener(this);

		c.add(radB);

		this.addWindowListener(this);

		this.setVisible(true);
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// System.out.println(ip);
		new DBClient(ip, port);
	}

	@Override
	public void windowClosing(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void itemStateChanged(ItemEvent arg0) {
		this.ip = "localhost";
		this.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (field.getText() != null && field.getText() != "") {
			this.ip = field.getText();
			this.dispose();
		}
	}

}
