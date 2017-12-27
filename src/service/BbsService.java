package service;

import java.util.List;

import dao.BbsDao;
import dto.BbsDto;


public class BbsService implements BbsServiceImpl {

	BbsDao bbsDao = new BbsDao();
	
	
	// if inserted return BbsDto, else return null
	@Override
	public BbsDto insert(String title, String content) {
		// TODO Auto-generated method stub
		boolean result = bbsDao.insert(title, content);
		BbsDto bbs = null;
		
		// 입력 성공
		if(result) {
			bbs = bbsDao.getLastBbsDtoByTitle(title, content);
		}
		
		return bbs;
	}

	// if deleted parameter bbsDto then return true, else return false
	@Override
	public boolean delete(BbsDto bbsDto) {
		return bbsDao.delete(bbsDto);
	}

	// if updated return updated BbsDto
	@Override
	public BbsDto update(int seq, String title, String content) {
		int result = bbsDao.update(seq, title, content); // update 실패하면 -1 리턴
		BbsDto bbs = null;
		
		if(result > -1) {
			bbs = bbsDao.getBbsBySeq(seq);
		}
		
		return bbs;
	}
	
	@Override
	public List<BbsDto> search(String str) {
		// TODO Auto-generated method stub
		return bbsDao.search(str);
	}

	@Override
	public List<BbsDto> getBbsList() {
		// TODO Auto-generated method stub
		return bbsDao.getBbsList();
	}

	@Override
	public List<BbsDto> getMyBbsList() {
		// TODO Auto-generated method stub
		return bbsDao.getMyBbsList();
	}

	@Override
	public void plusReadCount(BbsDto bbsDto) {
		// TODO Auto-generated method stub
		bbsDao.plusReadCount(bbsDto);
	}

}
