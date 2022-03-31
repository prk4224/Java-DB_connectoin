package com.pcwk.cmn;

public class SawonMain {
	
	private SawonDao dao;
	private SawonVO sawon01;
	
	public SawonMain() {
		dao = new SawonDao();
		//public SawonVO(int empno, String ename, String hiredate, int deptno) {
		sawon01 = new SawonVO(8888,"카카오","",20);
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

	public static void main(String[] args) {

		SawonMain main = new SawonMain();
		main.doSave();
	}

}
