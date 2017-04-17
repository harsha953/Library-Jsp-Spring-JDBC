package com.gcit.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.gcit.library.dao.BookCopiesDAO;
import com.gcit.library.dao.BookLoansDAO;
import com.gcit.library.entity.BookCopies;

public class Librarian {

	@Autowired
	BookLoansDAO blDAO;

	@Autowired
	BookCopiesDAO bcDAO;

	public int getNoOfCopiesForBranch(BookCopies bc) throws Exception{
		int value=0;
		BookCopies temp=bcDAO.read(new Integer[]{bc.getBook().getBookId(), bc.getLibraryBranch().getBranchId()});
		if (temp==null) {
			bc.setNoOfCopies(0);
			value=0;
			bcDAO.create(bc);
		}else{
			value=temp.getNoOfCopies();
		}
		return value;
	}

	@Transactional
	public void addBookCopiesToBranch(BookCopies bc) throws Exception {
		bcDAO.update(bc);
	}
}
