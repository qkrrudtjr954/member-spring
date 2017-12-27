package dao;

import java.util.List;

import dto.BbsDto;

public interface BbsDaoImpl {
	// 입력되면 true, 안되면 false
	public boolean insert(String title, String content);
	// 삭제되면 true, 안되면 false
	public boolean delete(BbsDto bbsDto);
	// seq 를 리턴한다.
	public int update(int seq, String title, String content);
	// seq 를 참조해 BbsDto를 리턴한다.
	public BbsDto getBbsBySeq(int seq);
	public List<BbsDto> search(String str);
	public List<BbsDto> getBbsList();
	public List<BbsDto> getMyBbsList();
	public BbsDto getLastBbsDtoByTitle(String title, String content);
	public void plusReadCount(BbsDto bbsDto);
}
