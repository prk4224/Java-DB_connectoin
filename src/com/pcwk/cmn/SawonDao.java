package com.pcwk.cmn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class SawonDao implements WorkDiv<SawonVO> {
	
	private Connection connection; // DB 연결 정보
	
	public SawonDao() {
	}
	
	public Connection connect() {
		
		Connection connection = null;
		
		//jdbc:oracle:thin:@IP:Port:전역 DB 명칭
		String dbURL = "jdbc:oracle:thin:@localhost:1521:xe";//URL
		String dbUSER = "scott";
		String dbPASS = "pcwk";
		
		//jdbc oracle driver load
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//db연결
			connection = DriverManager.getConnection(dbURL,dbUSER,dbPASS);
			System.out.println("connection:" + connection);
			
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: " + e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
			e.printStackTrace();
		}
		return connection;
	}

	//목록 조회
	@Override
	public List<SawonVO> doRetrieve(DTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	//insert
	@Override
	public int doSave(SawonVO dto) {
		int flag = 0;
        Connection conn  = null;//DB연결 정보
        PreparedStatement pstmt = null;//sql+data
        StringBuilder  sb=new StringBuilder(200);
        
        //1. DB연결
        conn = connect();
        
        //2. SQL작성
        sb.append(" INSERT INTO sawon (  \n");
        sb.append("     empno,           \n");
        sb.append("     ename,           \n");
        sb.append("     hiredate,        \n");
        sb.append("     deptno           \n");
        sb.append(" ) VALUES (           \n");
        sb.append("     ?,               \n");
        sb.append("     ?,               \n");
        sb.append("     sysdate,         \n");
        sb.append("     ?                \n");
        sb.append(" )                    \n");
        System.out.println(sb.toString());
        System.out.println("param: "+dto.toString());
        
        //3. param전달
        try {
			pstmt  = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, dto.getEmpno());   //첫번째, int(사번)값을 넣어 주겠다.
			pstmt.setString(2, dto.getEname());//두번째, Strin(이름)값을 넣어 주겠다.
			pstmt.setInt(3, dto.getDeptno());  //세번째,int(부서번호)값을 넣어 주겠다.
			
			//4. SQL실행	
			flag = pstmt.executeUpdate();
			//5. SQL실행결과
			System.out.println("flag: "+flag);
		} catch (SQLException e) {
		    System.out.println("SQLException:"+e.getMessage());
			e.printStackTrace();
		//6. 자원반납
		} finally {
			 //pstmt 자원반납
		     if(null != pstmt)	{
		    	 try {
					pstmt.close();
				} catch (SQLException e) {
					
				}
		     }
		     
		     //conn 자원반납
		     if(null != conn) {
		    	 try {
					conn.close();
				} catch (SQLException e) {
					
				}
		     }   
		}
	return flag;
	
	}

	//delete
	@Override
	public int doDelete(SawonVO dto) {
		// TODO Auto-generated method stub
		return 0;
	}

	//단건 조회
	@Override
	public SawonVO doSelectOne(SawonVO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	//update
	@Override
	public int doUpdate(SawonVO dto) {
		// TODO Auto-generated method stub
		return 0;
	}

}
