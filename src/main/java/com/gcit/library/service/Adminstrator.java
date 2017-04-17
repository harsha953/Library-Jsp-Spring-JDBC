package com.gcit.library.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import com.gcit.library.dao.AuthorDAO;
import com.gcit.library.dao.BookDAO;
import com.gcit.library.dao.BookLoansDAO;
import com.gcit.library.dao.BorrowerDAO;
import com.gcit.library.dao.LibraryBranchDAO;
import com.gcit.library.dao.PublisherDAO;
import com.gcit.library.entity.Author;
import com.gcit.library.entity.Book;
import com.gcit.library.entity.BookLoans;
import com.gcit.library.entity.Borrower;
import com.gcit.library.entity.LibraryBranch;
import com.gcit.library.entity.Publisher;
import com.gcit.library.exceptions.LibraryException;

public class Adminstrator {

	@Autowired
	AuthorDAO aDAO;
	@Autowired
	BookDAO bDAO;
	@Autowired
	PublisherDAO pDAO;
	@Autowired
	LibraryBranchDAO lbDAO;
	@Autowired
	BorrowerDAO brDAO;
	@Autowired
	BookLoansDAO blDAO;

	@Transactional
	public void addAuthor(Author a) throws Exception {
		checkExceptionsInAuthor(a);
		aDAO.create(a);
	}

	private void checkExceptionsInAuthor(Author a) throws LibraryException {
		if (a==null ||a.getAuthorName()==null || a.getAuthorName().trim().length()==0 ) {
			throw new LibraryException("Author Name cannot be null or blank");
		}else if (a.getAuthorName().trim().length()>45) {
			throw new LibraryException("Author name cannot be more than 45 charcters");
		}
	}

	public int getAuthorsCount() throws Exception{
		return aDAO.getCount();	
	}

	public List<Author> getAllAuthors(int pageNo, String searchString) throws Exception {
		if (searchString!=null && !"".equals(searchString)) {
			searchString="%"+searchString+"%";
		}else{
			searchString="%%";
		}
		return aDAO.readAuthorsByName(pageNo, searchString);
	}

	public int getAuthorsCountByName(String searchString) throws Exception {
		return aDAO.getCountByName(searchString);	
	}

	@Transactional
	public void deleteAuthor(Author a) throws Exception{
		aDAO.delete(a);
	}

	public Author getAuthor(int pk) throws Exception{
		return aDAO.read(new Integer[]{pk});
	}

	@Transactional
	public void updateAuthor(Author a) throws Exception{
		checkExceptionsInAuthor(a);
		aDAO.update(a);
	}

	@Transactional
	public void addBook(Book b) throws Exception{
		checkExceptionsInBook(b);
		int key=bDAO.saveAndGetId(b);
		b.setBookId(key);
		if (b.getAuthorsList()!=null) {
			bDAO.createInBookAuthorsTbl(b);
		}
	}

	@Transactional
	public void addPublisher(Publisher p) throws Exception{
		checkExceptionsInPublisher(p);
		pDAO.create(p);
	}

	@Transactional
	public void addBranch(LibraryBranch lb) throws Exception{
		checkExceptionsInBranch(lb);
		lbDAO.create(lb);
	}

	@Transactional
	public void addBorrower(Borrower br) throws Exception{
		checkExceptionsInBorrower(br);
		brDAO.create(br);
	}
	private void checkExceptionsInBorrower(Borrower b) throws LibraryException {
		if (b==null ||b.getName()==null ||	b.getName().trim().length()==0 ) {
			throw new LibraryException("Borrower name cannot be null or blank");
		}else if (b.getName().trim().length()>45) {
			throw new LibraryException("Branch name and branch address cannot be more than 45 charcters");
		}
	}

	@Transactional
	public void updateBook(Book b) throws Exception{
		checkExceptionsInBook(b);
		bDAO.update(b);
		bDAO.deleteBookAuthors(b);
		bDAO.createInBookAuthorsTbl(b);
	}

	private void checkExceptionsInBook(Book b) throws LibraryException {
		if (b==null ||b.getTitle()==null || b.getTitle().trim().length()==0 ) {
			throw new LibraryException("Book title cannot be null or blank");
		}else if (b.getTitle().trim().length()>45) {
			throw new LibraryException("Book title cannot be more than 45 charcters");
		}
	}

	@Transactional
	public void updatePublisher(Publisher p) throws Exception{
		checkExceptionsInPublisher(p);
		Publisher temp=getPublisher(p.getPublisherId());
		if ("".equals(p.getPublisherAddress().trim())) {
			p.setPublisherAddress(temp.getPublisherAddress());
		}
		if ("".equals(p.getPublisherPhone().trim())) {
			p.setPublisherPhone(temp.getPublisherPhone());
		}
		pDAO.update(p);
	}

	private void checkExceptionsInPublisher(Publisher p)
			throws LibraryException {
		if (p==null ||p.getPublisherName()==null ||	p.getPublisherName().trim().length()==0 ) {
			throw new LibraryException("publisher name cannot be null or blank");
		}else if (p.getPublisherName().trim().length()>45) {
			throw new LibraryException("publisher name cannot be more than 45 charcters");
		}
	}

	@Transactional
	public void updateBranch(LibraryBranch lb) throws Exception{
		checkExceptionsInBranch(lb);
		lbDAO.update(lb);
	}

	private void checkExceptionsInBranch(LibraryBranch lb)
			throws LibraryException {
		if (lb==null ||lb.getBranchName()==null ||	lb.getBranchName().trim().length()==0 ) {
			throw new LibraryException("branch name cannot be null or blank");
		}else if(lb.getBranchAddress()==null ||	lb.getBranchAddress().trim().length()==0){
			throw new LibraryException("branch address cannot be null or blank");
		}else if (lb.getBranchName().trim().length()>45 || lb.getBranchAddress().trim().length()>45) {
			throw new LibraryException("Branch name and branch address cannot be more than 45 charcters");
		}
	}

	@Transactional
	public void updateBorrower(Borrower b) throws Exception{
		checkExceptionsInBorrower(b);
		Borrower temp=getBorrower(b.getCardNo());
		if ("".equals(b.getAddress())) {
			b.setAddress(temp.getAddress());
		}
		if ("".equals(b.getPhone())) {
			b.setPhone(temp.getPhone());
		}
		brDAO.update(b);
	}

	@Transactional
	public void deleteBook(Book b) throws Exception{
		bDAO.delete(b);
	}

	@Transactional
	public void deletePublisher(Publisher p) throws Exception{
		pDAO.delete(p);
	}

	@Transactional
	public void deleteBranch(LibraryBranch lb) throws Exception{
		lbDAO.delete(lb);
	}

	@Transactional
	public void deleteBorrower(Borrower b) throws Exception{
		brDAO.delete(b);
	}

	public List<Book> getAllBook(int pageNo, String searchString) throws Exception {
		if (searchString!=null && !"".equals(searchString)) {
			searchString="%"+searchString+"%";
		}else{
			searchString="%%";
		}
		List<Book> list= bDAO.readAll(pageNo, searchString);
		for (Book book : list) {
			if (book.getPublisher()==null) {
				book.setPublisher(null);
			}else{
				book.setPublisher(getPublisher(book.getPublisher().getPublisherId()));
			}
			book.setAuthorsList(getAuthorListForBookId(book.getBookId()));
		}
		return list;
	}

	public List<Author> getAuthorListForBookId(int bookId) throws Exception{
		return aDAO.getAuthorsListForBook(bookId);
	}

	public int getBooksCount() throws Exception{
		return bDAO.getCount();	
	}

	public int getBooksCountByName(String searchString) throws Exception {
		return bDAO.getCountByName(searchString);	
	}

	public LibraryBranch getBranch(int pk) throws Exception{
		return lbDAO.read(new Integer[]{pk});
	}

	public Book getBook(int pk) throws Exception{
		Book book= bDAO.read(new Integer[]{pk});
		if (book.getPublisher()!=null) {
			book.setPublisher(getPublisher(book.getPublisher().getPublisherId()));
		}else{
			book.setPublisher(null);
		}
		book.setAuthorsList(getAuthorListForBookId(book.getBookId()));
		return book;
	}

	public Borrower getBorrower(int pk) throws Exception{
		return brDAO.read(new Integer[]{pk});
	}

	public List<Publisher> getAllPublisher() throws Exception{
		return pDAO.readAll();
	}

	public Publisher getPublisher(int pk) throws Exception{
		return pDAO.read(new Integer[]{pk});
	}

	public List<Book> getAllBooks() throws Exception {
		return bDAO.readAll();
	}

	public List<LibraryBranch> getAllBranches() throws Exception{
		return lbDAO.readAll();
	}

	public List<Author> getAllAuthors() throws Exception {
		return aDAO.readAll();
	}

	public List<Borrower> getAllBorrowers() throws Exception{
		return brDAO.readAll();
	}

	public String decodeString(String s){
		return HtmlUtils.htmlUnescape(s);
	}

	public List<BookLoans> getAllBookLoans(int cardNo) throws Exception{
		if(cardNo!=0){
			List<BookLoans> list = blDAO.getRowsByCardNo(cardNo);
			for (BookLoans bookLoans : list) {
				bookLoans.setLibraryBranch(getBranch(bookLoans.getLibraryBranch().getBranchId()));
				bookLoans.setBook(getBook(bookLoans.getBook().getBookId()));
				bookLoans.setBorrower(getBorrower(bookLoans.getBorrower().getCardNo()));
			}
			return list;
		}
		return null;
	}

	public void updateDueDate(BookLoans bl) throws Exception {
		blDAO.updateByDueDate(bl);
	}

}
