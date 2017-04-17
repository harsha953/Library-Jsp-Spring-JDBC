package com.gcit.library;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import com.gcit.library.entity.Book;
import com.gcit.library.entity.BookCopies;
import com.gcit.library.entity.LibraryBranch;
import com.gcit.library.service.Adminstrator;
import com.gcit.library.service.Librarian;

@Controller
public class LibrarianController {

	@Autowired         
	Adminstrator admin;

	@Autowired
	Librarian lib;

	@RequestMapping(value = {"/addBookCopies"}, method = RequestMethod.POST)
	public String addBookCopies(@RequestParam int branchId, @RequestParam int bookId, @RequestParam int noOfCopies, Model model) {
		try{	
			BookCopies bc=new BookCopies();
				Book b=new Book();
				LibraryBranch lb=new LibraryBranch();
				lb.setBranchId(branchId);
				b.setBookId(bookId);
				bc.setBook(b);
				bc.setLibraryBranch(lb);
				int old=lib.getNoOfCopiesForBranch(bc);
				bc.setNoOfCopies(noOfCopies+old);
				lib.addBookCopiesToBranch(bc);
				model.addAttribute("result", "Add copies to branch is successful!!");
			}catch(Exception e){
				model.addAttribute("result", e.getMessage());
			}
		return "index";
	}
	
	@RequestMapping(value = {"/showAddBookCopies"}, method = RequestMethod.GET)
	public String showAddBookCopies(Locale locale, Model model) {
		try{
			List<LibraryBranch> branchList=admin.getAllBranches();
			model.addAttribute("branchList", branchList);
			List<Book> bookList=admin.getAllBooks();
			model.addAttribute("bookList", bookList);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "addBookCopies";
	}

	@RequestMapping(value = {"/twoDropdown"}, method = RequestMethod.GET)
	@ResponseBody
	public StringBuilder twoDropdown(@RequestParam String val1, @RequestParam String val2, Model model) {
		StringBuilder sb=new StringBuilder();
		try{
			val1=encodeString(val1);
			val2=encodeString(val2);
			BookCopies bc=new BookCopies();
			Book b=new Book();
			LibraryBranch lb=new LibraryBranch();
			lb.setBranchId(Integer.parseInt(val1));
			b.setBookId(Integer.parseInt(val2));
			bc.setBook(b);
			bc.setLibraryBranch(lb);
			int noOfCopies;
			noOfCopies = lib.getNoOfCopiesForBranch(bc);
			sb.append("Existing Number of Copies :"+noOfCopies);
		}catch (Exception e) {
			e.printStackTrace();
			//sb.append("operation failed");
		}
		return sb;
	}

	private String encodeString(String s){
		return HtmlUtils.htmlEscape(s);
	}


}
