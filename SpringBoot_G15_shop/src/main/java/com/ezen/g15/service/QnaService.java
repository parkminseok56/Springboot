package com.ezen.g15.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.g15.dao.IQnaDao;
import com.ezen.g15.dto.QnaVO;

@Service
public class QnaService {
	
	@Autowired
	 IQnaDao qdao;

	public List<QnaVO> listQna() {
		return qdao.listQna();
	}

	public Object getQna(int qseq) {		
		return qdao.getQna( qseq);
	}

}
