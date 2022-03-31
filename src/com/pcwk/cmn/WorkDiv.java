package com.pcwk.cmn;


import java.util.List;
/**
 * <pre>
 * 모든 DAO 표준 WorkDiv를 implements 받을것.
 * @author ITSC
 *</pre>
 */
public interface WorkDiv<T> {
	
	/**
	 * <pre>
	 * 목록조회
	 * @param dto
	 * @return
	 * </pre>
	 */
	public List<T> doRetrieve(DTO dto);
	
	/**
	 * <pre>
	 * 도서 등록
	 * @param dto
	 * @return 1(성공)/0(실패)
	 * </pre>
	 */
	public int doSave(T dto);
	
	/**
	 * <pre>
	 * 도서 삭제
	 * @param dto
	 * @return 1(성공)/0(실패)
	 * </pre>
	 */
	public int doDelete(T dto);
	
	/**
	 * <pre>
	 * 단건 조회
	 * @param dto
	 * @return DTO
	 * </pre>
	 */
	public T doSelectOne(T dto);

	/**
	 * <pre>
	 * 수정 : delete,insert
	 * @param dto
	 * @return 1(성공)/0(실패)
	 */
	public int doUpdate(T dto);
	
	
	default String excelDown(String path) {
		return "download_path";
	}

}
