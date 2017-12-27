package service;

import java.util.List;

import dto.BbsDto;

public interface BbsServiceImpl {
	// 입력되면 BbsDto, 안되면 null
	public BbsDto insert(String title, String content);
	// 삭제되면 true, 안되면 false
	public boolean delete(BbsDto bbsDto);
	// BbsDto 를 리턴한다.
	public BbsDto update(int seq, String title, String content);
	// str을 포함한 BbsDto List를 리턴한다.
	public List<BbsDto> search(String str);
	public List<BbsDto> getBbsList();
	public List<BbsDto> getMyBbsList();
	
	public void plusReadCount(BbsDto bbsDto);
}
