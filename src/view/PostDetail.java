package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import delegator.Delegator;
import dto.BbsDto;
import dto.CommentDto;




public class PostDetail extends JFrame implements MouseListener, ActionListener{
	
	JButton allPost;
	JButton myPost;
	
	JButton deleteBtn;
	JButton editBtn;
	JButton commentBtn;
	
	JTable comments;
	
	List<CommentDto> commentList = null;
	
	
	JTextField comment;
	
	JLabel title;
	JLabel wdate;
	JTextPane content;
	
	BbsDto bbsDto;
	
	public PostDetail(BbsDto bbsDto, List<CommentDto> commentList) {
		super("Detail");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.bbsDto= bbsDto;
		this.commentList = commentList;
		
		Container contentPane = getContentPane();
        contentPane.setBackground(Color.yellow);
        contentPane.setLayout(null);
        

        title = new JLabel(bbsDto.getTitle());
		title.setBounds(150, 20, 200, 20);
		title.setFont(new Font(bbsDto.getTitle(), Font.PLAIN, 25));
		contentPane.add(title);
		
		wdate = new JLabel(bbsDto.getWdate());
		wdate.setBounds(220, 70, 200, 20);
		contentPane.add(wdate);
		
		JLabel readcount = new JLabel("조회수 : "+(bbsDto.getReadcount()+1));
		readcount.setBounds(10, 70, 100, 20);
		contentPane.add(readcount);
		
		JLabel contentLabel = new JLabel("Content");
		contentLabel.setBounds(10, 100, 50, 20);
		contentPane.add(contentLabel);

		
		content = new JTextPane();
		content.setText(bbsDto.getContent());
		content.setEditable(false);
		content.setBounds(0, 120, 375, 250);
		
		JScrollPane contentPanel = new JScrollPane(content);
		contentPanel.setBounds(0, 120, 375, 250);
		contentPane.add(contentPanel);

		
		
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createLineBorder(Color.red));
		panel.setPreferredSize(new Dimension(370, commentList.size()*60));
		panel.setLayout(null);
		
		for(int i = 0; i<commentList.size(); i++) {
			JLabel user = new JLabel(commentList.get(i).getUser_id());
			user.setBounds(0, i*60, 75, 20);
			panel.add(user);
			
			JLabel content = new JLabel("<html><p>"+commentList.get(i).getContent()+"</p></html>");
			content.setBounds(5, i*60+20, 270, 40);
			panel.add(content);
			
			JLabel wdate = new JLabel("<html><p>"+commentList.get(i).getWdate().replace(" ", "\n")+"</p></html>");
			wdate.setBounds(280, i*60+20, 95, 40);
			panel.add(wdate);
		}
		
		JScrollPane showCommentPanel = new JScrollPane(panel);
		showCommentPanel.setBounds(0, 370, 375, 150);
		
		contentPane.add(showCommentPanel);
		
		JPanel writeCommentPanel = new JPanel();
		writeCommentPanel.setLayout(null);
		writeCommentPanel.setBounds(0, 520, 375, 50);
		
		comment = new JTextField();
		comment.setLocation(0, 0);
		comment.setSize(300, 50);
		writeCommentPanel.add(comment);
		
		commentBtn = new JButton("댓글");
		commentBtn.setLocation(300, 0);
		commentBtn.setSize(75, 50);
		commentBtn.addActionListener(this);
		writeCommentPanel.add(commentBtn);
		
		contentPane.add(writeCommentPanel);

		
		
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(null);
		btnPanel.setBounds(0, 580, 375, 40);
		btnPanel.setBackground(Color.YELLOW);
		
		Delegator delegator = Delegator.getInstance();
		if (bbsDto.getId().equals(delegator.getCurrent_user().getId())) {
	
			deleteBtn = new JButton("Delete");
			deleteBtn.setBounds(100, 0, 75, 20);
			deleteBtn.addActionListener((ActionEvent e)-> {
				if(JOptionPane.showConfirmDialog(null, "정말 삭제하겠습니까?") == 0) {
					System.out.println("delete");
					boolean result = delegator.bbsController.delete(bbsDto);
					
					if(result) {
						JOptionPane.showMessageDialog(null, "삭제 되었습니다.");
						delegator.bbsController.bbs();
						this.dispose();
					}else {
						JOptionPane.showMessageDialog(null, "삭제할 수 없습니다.");
					}
				}
			});
			btnPanel.add(deleteBtn);
			
			editBtn = new JButton("Edit");
			editBtn.setBounds(175, 0, 75, 20);
			editBtn.addActionListener((ActionEvent e)-> {
				delegator.bbsController.edit(bbsDto);
				this.dispose();
			});
			btnPanel.add(editBtn);
			
		}
		
		allPost = new JButton("All Post");
		allPost.setBounds(75, 20, 100, 20);
		allPost.addActionListener(this);
		btnPanel.add(allPost);
		
		myPost = new JButton("my post");
		myPost.setBounds(175, 20, 100, 20);
		myPost.addActionListener(this);
		btnPanel.add(myPost);
		
		contentPane.add(btnPanel);
		
		
		setBounds(100, 100, 380, 667);
        setResizable(false);
        setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Delegator delegator = Delegator.getInstance();
		Object obj = e.getSource();
		
		if(obj == allPost) {
			delegator.bbsController.bbs();
			this.dispose();
			
		}else if(obj == myPost) {
			delegator.bbsController.myBbs();
			this.dispose();
			
		} 
		else if(obj == commentBtn) {
			String content = comment.getText();
			
			if(content.length() < 5) {
				JOptionPane.showMessageDialog(null, "댓글은 5글자 이상 작성해주세요.");
			}else {
				boolean result = delegator.comController.addComment(content, this.bbsDto.getSeq());
				
				if(result) {
					delegator.bbsController.detail(bbsDto);
					this.dispose();
				}else {
					JOptionPane.showMessageDialog(null, "댓글을 입력할 수 없습니다.");
				}
			}
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


}
