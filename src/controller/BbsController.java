package controller;

import java.util.List;

import javax.swing.JOptionPane;

import dto.BbsDto;
import service.BbsService;
import view.Bbs;
import view.Edit;
import view.PostDetail;
import view.Write;

public class BbsController {
	
	BbsService bbsService = new BbsService();
	
	public void bbs() {
		List<BbsDto> list = bbsService.getBbsList();
		new Bbs(list);
	}
	public void search(String query) {
		List<BbsDto> list = bbsService.search(query);
		
		if (list == null) {
			JOptionPane.showMessageDialog(null, "찾으려는 정보가 없습니다.");
			list = bbsService.getBbsList();
			new Bbs(list);
		}else {
			new Bbs(list);
		}
		
	}

	public void myBbs() {
		List<BbsDto> list = bbsService.getMyBbsList();
		new Bbs(list);
	}
	
	public void detail(BbsDto bbs) {
		// increase read count
		bbsService.plusReadCount(bbs);
		new PostDetail(bbs);
	}
	public boolean delete(BbsDto bbsDto) {
		return bbsService.delete(bbsDto);
	}
	
	public void edit(BbsDto bbsDto) {
		new Edit(bbsDto);
	}
	public BbsDto update(int seq, String title, String content) {
		// TODO Auto-generated method stub
		return bbsService.update(seq, title, content);
	}
	public BbsDto insert(String title, String content) {
		// TODO Auto-generated method stub
		return bbsService.insert(title, content);
	}
	public void write() {
		new Write();
	}

}
