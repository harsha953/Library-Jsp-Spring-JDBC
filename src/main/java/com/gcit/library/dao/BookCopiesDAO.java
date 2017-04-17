package com.gcit.library.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.library.entity.Book;
import com.gcit.library.entity.BookCopies;
import com.gcit.library.entity.LibraryBranch;

public class BookCopiesDAO extends BaseDAO<BookCopies> implements ResultSetExtractor<List<BookCopies>> {

	@Override
	public void create(BookCopies be) throws SQLException {
		template.update("insert into tbl_book_copies(bookId, branchId, noOfCopies) values(?,?,?)", new Object[] { be.getBook().getBookId(),
				be.getLibraryBranch().getBranchId(), be.getNoOfCopies() });
	}

	@Override
	public void update(BookCopies be) throws SQLException {
		template.update("update tbl_book_copies set noOfCopies=? where bookId=? and branchId=?", new Object[] { be.getNoOfCopies(), be.getBook().getBookId(),
				be.getLibraryBranch().getBranchId() });
	}

	@Override
	public void delete(BookCopies be) throws SQLException {
		template.update("delete from tbl_book_copies where bookId=? and branchId=?", new Object[] { be.getBook().getBookId(),
				be.getLibraryBranch().getBranchId() });

	}

	@Override
	public BookCopies read(Integer[] pk) throws SQLException {
		List<BookCopies> list = template.query("select * from tbl_book_copies where bookId=? and branchId=?", pk, this);
		if (list!=null) {
			return list.get(0);
		}
		return null;
	}

	public List<BookCopies> readAll() throws SQLException {
		return template.query("select * from tbl_book_copies", new Object[] {}, this);
	}

	@Override
	public List<BookCopies> extractData(ResultSet rs) throws SQLException {
		List<BookCopies> list = new ArrayList<BookCopies>();
		while (rs.next()) {
			BookCopies bc = new BookCopies(new Book(rs.getInt("bookId")), new LibraryBranch(rs.getInt("branchId")), rs.getInt("noOfCopies"));
			list.add(bc);
		}
		return list;
	}

	public List<BookCopies> getListOfBooks(int branchId) throws SQLException {
		return template.query("select * from tbl_book_copies where branchId=? and noOfCopies>0", new Object[] { branchId }, this);
	}

}
