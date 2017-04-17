package com.gcit.library.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.library.entity.Book;
import com.gcit.library.entity.BookLoans;
import com.gcit.library.entity.Borrower;
import com.gcit.library.entity.LibraryBranch;

public class BookLoansDAO extends BaseDAO<BookLoans> implements ResultSetExtractor<List<BookLoans>> {

	@Override
	public void create(BookLoans be) throws SQLException {
		template.update("insert into tbl_book_loans(bookId, branchId, cardNo, dateOut, dueDate) values(?,?,?, curdate(), date_add(curdate(), interval 7 day))", 
				new Object[]{be.getBook().getBookId(), be.getLibraryBranch().getBranchId(), be.getBorrower().getCardNo()});
	}

	@Override
	public void update(BookLoans be) throws SQLException {
		template.update("update tbl_book_loans set dateIn=curdate() where bookId=? and branchId=? and cardNo=?", 
				new Object[]{be.getBook().getBookId(), be.getLibraryBranch().getBranchId(), be.getBorrower().getCardNo()});
		
	}
	public void updateByDueDate(BookLoans be) throws SQLException {
		//System.out.println("DAODate "+be.getDueDate()+be.getBook().getBookId()+be.getLibraryBranch().getBranchId()+be.getBorrower().getCardNo());
		template.update("update tbl_book_loans set dueDate=? where bookId=? and branchId=? and cardNo=?", 
				new Object[]{be.getDueDate(), be.getBook().getBookId(), be.getLibraryBranch().getBranchId(), be.getBorrower().getCardNo()});
		
	}
	@Override
	public void delete(BookLoans be) throws SQLException {
		template.update("delete from tbl_book_loans where bookId=? and branchId=? and cardNo=?", 
				new Object[]{be.getBook().getBookId(), be.getLibraryBranch().getBranchId(), be.getBorrower().getCardNo()});	
	}

	@Override
	public BookLoans read(Integer[] pk) throws SQLException {
		List<BookLoans> list= template.query("select * from tbl_book_loans where bookId=? and branchId=? and cardNo=?", pk, this);
		if (list.size()!=0) {
			return list.get(0);
		}
		return null;
	}

	public List<BookLoans> readAll() throws SQLException {
		return template.query("select * from tbl_book_loans", new Object[]{}, this);
	}

	@Override
	public List<BookLoans> extractData(ResultSet rs) throws SQLException {
		List<BookLoans> list=new ArrayList<BookLoans>();
		while (rs.next()) {
			BookLoans bl=new BookLoans(new Book(rs.getInt("bookId")), new LibraryBranch(rs.getInt("branchId")), new Borrower(rs.getInt("cardNo")));
			bl.setDateOut(rs.getDate("dateOut"));
			bl.setDueDate(rs.getDate("dueDate"));
			bl.setDateIn(rs.getDate("dateIn"));
			list.add(bl);
		}
		return list;
	}

	public List<BookLoans> getListOfBookLoans(int cardNo) throws SQLException{
		return template.query("select * from tbl_book_loans where cardNo=? and dateIn is null", new Object[]{cardNo}, this);
		
	}

	public List<BookLoans> getListOfBookLoans(int branchId, int cardNo) throws SQLException {
		return template.query("select * from tbl_book_loans where branchId=? and cardNo=? and dateIn is null", new Object[]{branchId, cardNo}, this);
		
	}

	public List<BookLoans> getRowsByCardNo(int cardNo) throws SQLException{
		return template.query("select * from tbl_book_loans where cardNo=? and dateIn is null", new Object[] {cardNo}, this);
	}

}
