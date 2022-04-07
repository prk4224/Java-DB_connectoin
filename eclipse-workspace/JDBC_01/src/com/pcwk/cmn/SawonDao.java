package com.pcwk.cmn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SawonDao implements WorkDiv<SawonVO> {

	public SawonDao() {

	}

	public Connection connect() {

		Connection connection = null;// DB연결 정보

		// jdbc:oracle:thin:@IP:PORT:전역DB명칭
		String dbURL = "jdbc:oracle:thin:@localhost:1521:xe";// URL
		String dbUSER = "scott";// ID
		String dbPASS = "pcwk";// 비번

		try {
			// jdbc oracle driver load
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// db연결
			connection = DriverManager.getConnection(dbURL, dbUSER, dbPASS);
			System.out.println("connection:" + connection);

		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException:" + e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("SQLException:" + e.getMessage());
			e.printStackTrace();
		}

		return connection;
	}

	// 목록조회
	@Override
	public List<SawonVO> doRetrieve(DTO dto) {
		List<SawonVO> sawonList=new ArrayList<SawonVO>();
		SearchVO  inVO = (SearchVO) dto;
		
		Connection conn = null;// DB연결 정보
		PreparedStatement pstmt = null;// SQL + 데이터
		ResultSet rs    = null;//DB에서 전달된 정보 추출
		StringBuilder  sb=new StringBuilder(300);
		// 1. DB연결
		conn = connect();
		
		// 2. SQL작성
		sb.append(" SELECT tt1.rnum as num,		                                      \n");
		sb.append("        tt1.empno,		                                          \n");
		sb.append("        tt1.ename,		                                          \n");
		sb.append("        TO_CHAR(tt1.hiredate,'YYYY/MM/DD') AS hiredate,		      \n");
		sb.append("        tt1.deptno		                                          \n");
		sb.append(" FROM (		                                                      \n");
		sb.append("         SELECT ROWNUM AS rnum,t1.*		                          \n");
		sb.append("         FROM (		                                              \n");
		sb.append("                 SELECT *		                                  \n");
		sb.append("                 FROM sawon		                                  \n");
		sb.append("                 --조건		                                      \n");
		sb.append("                 ORDER BY hiredate DESC		                      \n");
		sb.append("         )t1		                                                  \n");
		sb.append("         --WHERE ROWNUM <=(&PAGE_SIZE*(&PAGE_NUM-1)+&PAGE_SIZE)	  \n");
		sb.append("         WHERE ROWNUM <=10			                              \n");
		sb.append(" )tt1		                                                      \n");
		sb.append(" --WHERE rnum >=(&PAGE_SIZE*(&PAGE_NUM-1)+1)		                  \n");
		sb.append(" WHERE rnum >=1                                                    \n");		
		
		System.out.println("query:\n"+sb.toString());
		System.out.println("param:"+inVO.toString());
		try {
			
			pstmt = conn.prepareStatement(sb.toString());
			//TO DO: param set
			
			// 4. SQL실행: ResultSet
			rs = pstmt.executeQuery();
			
			// 5. return 받은 ResultSet에서 DB에서 전달된 데이터 추출!
			while(rs.next()) {
				SawonVO outVO=new SawonVO();
				outVO.setNo(rs.getInt("num"));
				outVO.setEmpno(rs.getInt("empno"));
				outVO.setEname(rs.getString("ename"));
				outVO.setHiredate(rs.getString("hiredate"));
				outVO.setDeptno(rs.getInt("deptno"));
				
				sawonList.add(outVO);
			}
			
		}catch(SQLException e) {
			System.out.println("SQLException:"+e.getMessage());
			e.printStackTrace();
		}finally {
			//rs 자원반납
			if(null !=rs) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			
			//pstmt 자원반납
			if(null !=pstmt) {
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
		
		
		return sawonList;
	}

	// insert
	@Override
	public int doSave(SawonVO dto) {
		int flag = 0;
		Connection conn = null;// DB연결 정보
		PreparedStatement pstmt = null;// sql+data
		StringBuilder sb = new StringBuilder(200);

		// 1. DB연결
		conn = connect();

		// 2. SQL작성
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
		System.out.println("param: " + dto.toString());

		// 3. param전달
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, dto.getEmpno()); // ?첫번째, int(사번)값을 넣어 주겠다.
			pstmt.setString(2, dto.getEname());// ?두번째, String(이름)값을 넣어 주겠다.
			pstmt.setInt(3, dto.getDeptno()); // ?세번째, int(부서번호)값을 넣어 주겠다.

			// 4. SQL실행
			flag = pstmt.executeUpdate();
			// 5. SQL실행결과
			System.out.println("flag: " + flag);
		} catch (SQLException e) {
			System.out.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			// 6. 자원반납
		} finally {
			// pstmt 자원반납
			if (null != pstmt) {
				try {
					pstmt.close();
				} catch (SQLException e) {

				}
			}

			// conn 자원반납
			if (null != conn) {
				try {
					conn.close();
				} catch (SQLException e) {

				}
			}

		}

		return flag;
	}

	// delete
	@Override
	public int doDelete(SawonVO dto)  {
		int flag = 0;

		Connection conn = null;// DB연결 정보
		PreparedStatement pstmt = null;// SQL + 데이터
		StringBuilder sb = new StringBuilder(100);

		// 1. DB연결
		conn = connect();
		
		// 2. SQL작성
		sb.append(" DELETE FROM sawon   \n");
		sb.append(" WHERE   empno = ?   \n");

		System.out.println("query:\n" + sb.toString());
		System.out.println("param: " + dto.toString());

		try {
			//트랜잭션 처리
			//conn.setAutoCommit(true);//트랜잭션 자동 commit
			
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, dto.getEmpno());
			// 4. SQL실행
			flag = pstmt.executeUpdate();
			// 5. SQL실행결과
			System.out.println("flag: " + flag);
			
			//트랜잭션 처리
//			if(1==flag) {
//				conn.commit();
//			}else {
//				conn.rollback();
//			}
			
			
		} catch (SQLException e) {
			//conn.rollback();
			System.out.println("SQLException:" + e.getMessage());
			e.printStackTrace();
		} finally {
			// pstmt 자원반납

			if (null != pstmt) {
				try {
					pstmt.close();
				} catch (SQLException e) {

				}
			}

			// conn 자원반납
			if (null != conn) {
				try {
					conn.close();
				} catch (SQLException e) {

				}
			}

		}

		return flag;
	}

	// 단건조회
	@Override
	public SawonVO doSelectOne(SawonVO dto) {
		SawonVO outVO = null;//return
		Connection conn = null;// DB연결 정보
		PreparedStatement pstmt = null;// SQL + 데이터
		ResultSet         rs    = null;//DB에서 전달된 정보 추출
		StringBuilder     sb    =new StringBuilder(50);
		
		// 1. DB연결
		conn = connect();
		
		// 2. SQL작성
		
		sb.append(" SELECT empno,                                             \n");
	    sb.append("        ename,                                             \n");
	    sb.append("        TO_CHAR(hiredate,'YYYY/MM/DD HH24:MI:SS') hiredate,\n");
	    sb.append("        deptno                                             \n");
		sb.append(" FROM sawon                                                \n");
		sb.append(" WHERE empno = ?		                                      \n");
		
		System.out.println("query: \n "+sb.toString());
		System.out.println("param: "+dto.toString());
		
		try {
		
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, dto.getEmpno());
			
			// 4. SQL실행: ResultSet
			rs = pstmt.executeQuery();
			
			// 5. return 받은 ResultSet에서 DB에서 전달된 데이터 추출!
			if(rs.next()) {
				outVO = new SawonVO();
				outVO.setEmpno(rs.getInt("empno"));
				outVO.setEname(rs.getString("ename"));
				outVO.setHiredate(rs.getString("hiredate"));
				outVO.setDeptno(rs.getInt("deptno"));
			}
			
			System.out.println("outVO:"+outVO);
			
		}catch(SQLException e) {
			System.out.println("SQLException:"+e.getMessage());
		}finally {
			//rs 자원반납
			if(null !=rs) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			
			//pstmt 자원반납
			if(null !=pstmt) {
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
		
		return outVO;
	}

	
	
	
	
	
	// update
	@Override
	public int doUpdate(SawonVO dto) {
		// TODO Auto-generated method stub
		return 0;
	}

}



















