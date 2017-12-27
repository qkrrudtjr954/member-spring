package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import dao.BbsDao;
import delegator.Delegator;
import dto.BbsDto;


public class Bbs extends JFrame implements MouseListener, ActionListener{
    
	JTable table;
	JScrollPane jScrPane;
	
	String columnNames[] = { "No", "title", "readcount", "wdate" };
	
	Object rowData[][];
	
	List<BbsDto> list = null;
	
	JButton post;
	JTextField searchField;
	JButton search;
	JButton allPost;
	JButton myBbs;
	JButton logout;
	
	
	public Bbs(List<BbsDto> list) {
		
		super("Bbs List");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.list = list;
		
		Container contentPane = getContentPane();
        contentPane.setBackground(Color.yellow);
        contentPane.setLayout(null);
        
        JLabel title = new JLabel("Post List");
		title.setBounds(150, 68, 200, 20);
		contentPane.add(title);
		
		rowData = new Object[list.size()][columnNames.length];
		
		for (int i = 0; i < list.size(); i++) {
			rowData[i][0] = i+1;
			rowData[i][1] = list.get(i).getTitle();
			rowData[i][2] = list.get(i).getReadcount();
			rowData[i][3] = list.get(i).getWdate();
		}
		
		
		// 앞은 데이터, 뒤는 제목들
		table = new JTable(rowData, columnNames);
		table.addMouseListener(this);
		jScrPane = new JScrollPane(table);
		
		jScrPane.setBounds(0, 100, 375, 300);
		contentPane.add(jScrPane);
		
		
		post = new JButton("글쓰기");
		post.setBounds(100, 420, 150, 20);
		post.addActionListener(this);
		contentPane.add(post);
		
		
		searchField = new JTextField();
		searchField.setBounds(20, 500, 150, 20);
		contentPane.add(searchField);
		
		search = new JButton("검색");
		search.setBounds(170, 500, 150, 20);
		search.addActionListener(this);
		contentPane.add(search);
		
		
		myBbs = new JButton("내가 쓴 글");
		myBbs.setBounds(100, 440, 150, 20);
		myBbs.addActionListener(this);
		contentPane.add(myBbs);
		

		allPost = new JButton("모든 글"); 
		allPost.setBounds(100, 460, 150, 20);
		allPost.addActionListener(this);
		contentPane.add(allPost);
		
		logout = new JButton("로그아웃");
		logout.setBounds(275, 0, 100, 20);
		logout.addActionListener(this);
		contentPane.add(logout);
		
		setBounds(100, 100, 375, 667);
        setResizable(false);
        setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Delegator delegator = Delegator.getInstance();
		Object obj = e.getSource();
		
		
		if(obj == post) {
			delegator.bbsController.write();
			this.dispose();
		}else if(obj == search) {
			String query = searchField.getText();
			
			if(query.trim().equals("")) {
				JOptionPane.showMessageDialog(null, "찾고 싶은 문자를 입력하세요.");
			}else {
				delegator.bbsController.search(query);
				this.dispose();
			}
		}else if(obj == myBbs) {
			delegator.bbsController.myBbs();
			this.dispose();
		}else if(obj == logout) {
			Delegator.getInstance().setCurrent_user(null);
			
			JOptionPane.showMessageDialog(null, "정상적으로 로그아웃 됐습니다.");
			delegator.mainController.main();
			this.dispose();
		}else if(obj == allPost) {
			delegator.bbsController.bbs();
			this.dispose();
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("pressed");
		
		Delegator delegator = Delegator.getInstance();
		
		int rowNum = table.getSelectedRow();
		BbsDto bbs = this.list.get(rowNum);

		delegator.bbsController.detail(bbs);
		this.dispose();
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
