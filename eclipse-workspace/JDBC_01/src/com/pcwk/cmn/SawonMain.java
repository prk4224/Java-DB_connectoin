package com.pcwk.cmn;

import java.util.List;

public class SawonMain {

	private SawonDao dao;
	private SawonVO  sawon01;
	private SearchVO search01;
	
	public SawonMain() {
		dao = new SawonDao();
		//public SawonVO(int empno, String ename, String hiredate, int deptno) {
		sawon01 = new SawonVO(9001,"카카오","",20);
		//public SearchVO(String searchDiv, String searchWord, int pageSize, int pageNum) {
		
		search01 =new SearchVO("","",10,1);
	}
	
	public void doSave() {
		int flag = dao.doSave(sawon01);
		if(1==flag) {
			System.out.println("-------------------");
			System.out.println("-등록 성공-");
			System.out.println("-------------------");
		}else {
			System.out.println("-------------------");
			System.out.println("-등록 실패-");
			System.out.println("-------------------");		
		}
	}
	
	
	public void doDelete() {
		int flag = dao.doDelete(sawon01);
		if( 1== flag) {
			System.out.println("==================");
			System.out.println("=삭제 성공=");
			System.out.println("==================");
		}else {
			System.out.println("==================");
			System.out.println("=삭제 실패=");
			System.out.println("==================");			
		}
	}
	
	
	public void doSelectOne() {
		SawonVO outVO = dao.doSelectOne(sawon01);
		if(null != outVO) {
			System.out.println("-------------------");
			System.out.println("--조회 성공-");
			System.out.println("-------------------");
		}else {
			System.out.println("-------------------");
			System.out.println("--조회 실패-");
			System.out.println("-------------------");			
		}
	}
	
	public void doRetrieve() {
		List<SawonVO> list = dao.doRetrieve(search01);
		if(list.size()>0) {
			for(SawonVO vo :list) {
				System.out.println("vo:"+vo);
			}
		}else {
			System.out.println("-------------------");
			System.out.println("--목록 조회 실패-");
			System.out.println("-------------------");				
		}
	}
	
	
	public static void main(String[] args) {
		
		SawonMain main=new SawonMain();
		//삭제
		//main.doDelete();
		
		//등록
		//main.doSave();
		
		//단건 조회
		//main.doSelectOne();
		
		//목록조회
		main.doRetrieve();

	}

}












