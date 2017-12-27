package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import delegator.Delegator;

public class Main extends JFrame{
	JLabel label;

	public Main() {

		super("TaTao Talk");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		Container contentPane = getContentPane();
		contentPane.setBackground(Color.yellow);
		contentPane.setLayout(null);

		JButton button = new JButton("start");
		button.setBounds(110, 400, 150, 50);
		button.setBorder(new LineBorder(Color.black, 2));

		button.addActionListener((ActionEvent e) -> {
			Delegator delegator = Delegator.getInstance();
			if (delegator.getCurrent_user() == null) {
				delegator.memController.Login();
				super.dispose();
			} else {
				// bbs
			}
		});
		contentPane.add(button);

		setBounds(100, 100, 375, 667);
		setResizable(false);
		setVisible(true);
	}
}
