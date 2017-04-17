package com.gcit.library.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.gcit.library.dao.BookCopiesDAO;
import com.gcit.library.dao.BookLoansDAO;
import com.gcit.library.dao.BorrowerDAO;
import com.gcit.library.entity.Book;
import com.gcit.library.entity.BookCopies;
import com.gcit.library.entity.BookLoans;
import com.gcit.library.entity.Borrower;
import com.gcit.library.entity.LibraryBranch;
import com.gcit.library.exceptions.LibraryException;

public class BorrowerService {

	@Autowired         
	Adminstrator admin;

	@Autowired
	BorrowerDAO brDAO;

	@Autowired
	BookLoansDAO blDAO;

	@Autowired
	BookCopiesDAO bcDAO;

	public boolean checkCardNo(int pk) throws Exception {
		if (pk==0) {
			throw new LibraryException("Card No. cannot be empty");
		}
		Borrower temp=brDAO.read(new Integer[]{pk});
		if (temp==null) {
			return false;
		}else{
			return true;
		}	
	}

	public List<Book> getAllBooksForBranch(int branchId) throws Exception{
		List<Book> list=new ArrayList<Book>();
		List<BookCopies> listBC=bcDAO.getListOfBooks(branchId);
		for (BookCopies bookCopies : listBC) {
			bookCopies.setBook(admin.getBook(bookCopies.getBook().getBookId()));
			bookCopies.setLibraryBranch(admin.getBranch(bookCopies.getLibraryBranch().getBranchId()));
			list.add(bookCopies.getBook());
		}
		return list;
	}

	@Transactional
	public void addBookLoans(BookLoans bl) throws Exception {
		BookLoans temp=blDAO.read(new Integer[]{bl.getBook().getBookId(), bl.getLibraryBranch().getBranchId(), bl.getBorrower().getCardNo()});
		if (temp==null) {
			blDAO.create(bl);
		}else if(temp.getDateIn()==null){
			throw new LibraryException("\" "+bl.getBook().getTitle()+" \"already taken from \""+bl.getLibraryBranch().getBranchName()+"\" by"+bl.getBorrower().getName());
		}else{
			blDAO.delete(bl);
			blDAO.create(bl);
		}
		BookCopies bc=new BookCopies();
		bc.setBook(bl.getBook());
		bc.setLibraryBranch(bl.getLibraryBranch());
		bc=bcDAO.read(new Integer[]{bc.getBook().getBookId(), bc.getLibraryBranch().getBranchId()});
		bc.setNoOfCopies(bc.getNoOfCopies()-1);
		bcDAO.update(bc);
	}

	public List<LibraryBranch> getAllBranchForBorrower(int cardNo) throws Exception {
		List<LibraryBranch> list=new ArrayList<LibraryBranch>();
		List<BookLoans> listBL=blDAO.getListOfBookLoans(cardNo);
		if (listBL==null) {
			return null;
		}else{
			List<Integer> branchIdList=new ArrayList<Integer>();
			for (BookLoans bl : listBL) {
				bl.setBook(admin.getBook(bl.getBook().getBookId()));
				bl.setLibraryBranch(admin.getBranch(bl.getLibraryBranch().getBranchId()));
				bl.setBorrower(admin.getBorrower(bl.getBorrower().getCardNo()));
				if (!branchIdList.contains(bl.getLibraryBranch().getBranchId())) {
					list.add(bl.getLibraryBranch());
				}
				branchIdList.add(bl.getLibraryBranch().getBranchId());
			}
			return list;
		}

	}

	public List<Book> getAllBookForBorrower(int branchId, int cardNo ) throws Exception {
		List<Book> list=new ArrayList<Book>();
		List<BookLoans> listBL=blDAO.getListOfBookLoans(branchId, cardNo);
		for (BookLoans bl : listBL) {
			bl.setBook(admin.getBook(bl.getBook().getBookId()));
			bl.setLibraryBranch(admin.getBranch(bl.getLibraryBranch().getBranchId()));
			bl.setBorrower(admin.getBorrower(bl.getBorrower().getCardNo()));
			list.add(bl.getBook());
		}
		return list;
	}

	@Transactional
	public void updateBookLoans(BookLoans bl) throws Exception{
		blDAO.update(bl);
		BookCopies bc=new BookCopies();
		bc.setBook(bl.getBook());
		bc.setLibraryBranch(bl.getLibraryBranch());
		bc=bcDAO.read(new Integer[]{bc.getBook().getBookId(), bc.getLibraryBranch().getBranchId()});
		bc.setNoOfCopies(bc.getNoOfCopies()+1);
		bcDAO.update(bc);
	}

}
