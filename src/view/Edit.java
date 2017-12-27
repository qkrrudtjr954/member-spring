package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import dao.BbsDao;
import delegator.Delegator;
import dto.BbsDto;

public class Edit extends JFrame implements ActionListener {

	JTextField title;
	JTextPane content;
	
	
	JLabel label;
	
	JButton edit;
	JButton allPost;
	
	int seq;

	public Edit(BbsDto bbsDto){
		super("Edit");

		this.seq = bbsDto.getSeq();
		
		Container contentPane = getContentPane();
		contentPane.setBackground(Color.yellow);
		contentPane.setLayout(null);

		JLabel maintitle = new JLabel("Post");
		maintitle.setBounds(150, 68, 200, 20);
		contentPane.add(maintitle);


		JLabel idLabel = new JLabel("Title");
		idLabel.setBounds(20, 130, 50, 20);
		contentPane.add(idLabel);

		title = new JTextField(bbsDto.getTitle());
		title.setBounds(20, 150, 335, 20);
		contentPane.add(title);

		JLabel contentLabel = new JLabel("Content");
		contentLabel.setBounds(20, 180, 100, 20);
		contentPane.add(contentLabel);

//		content = new JTextArea();
//		content.setBounds(20, 200, 335, 200);
//		contentPane.add(content);
		
		
		content = new JTextPane();
		content.setText(bbsDto.getContent());
		content.setEditable(true);
		content.setBounds(20, 200, 335, 200);
		
		
		JScrollPane panel = new JScrollPane(content);
		panel.setBounds(20, 200, 335, 200);
		
		contentPane.add(panel);
		
		// --------------------------------------------------
		label = new JLabel("---");
		label.setBounds(0, 0, 200, 30);
		contentPane.add(label);
		contentPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				label.setText("x: " + x + "  y: " + y);
			}
		});
		// --------------------------------------------------




		edit = new JButton("Edit");
		edit.setBounds(100, 500, 150, 20);
		edit.addActionListener(this);
		contentPane.add(edit);
		
		
		allPost = new JButton("All Post");
		allPost.setBounds(100, 520, 150, 20);
		allPost.addActionListener(this);
		contentPane.add(allPost);



		setBounds(100, 100, 375, 667);
		setResizable(false);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Delegator delegator = Delegator.getInstance();
		Object obj = e.getSource();

		if(obj == edit){
			if (!title.getText().equals("") || title.getText().length() < 2 || !content.getText().equals("") || content.getText().length() < 10) {
				
				BbsDto bbsDto = delegator.bbsController.update(this.seq, title.getText(), content.getText());
				
				if(bbsDto != null) {
					JOptionPane.showMessageDialog(null, "글이 수정되었습니다.");
					delegator.bbsController.detail(bbsDto);
					this.dispose();
				}
			}
		}else {
			delegator.bbsController.bbs();
			this.dispose();
		}
	}
}
