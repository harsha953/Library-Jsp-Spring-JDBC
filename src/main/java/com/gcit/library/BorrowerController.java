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

import com.gcit.library.dao.BorrowerDAO;
import com.gcit.library.entity.Book;
import com.gcit.library.entity.BookLoans;
import com.gcit.library.entity.Borrower;
import com.gcit.library.entity.LibraryBranch;
import com.gcit.library.exceptions.LibraryException;
import com.gcit.library.service.Adminstrator;
import com.gcit.library.service.BorrowerService;

@Controller
public class BorrowerController {

	@Autowired
	BorrowerService borService;

	@Autowired
	Adminstrator admin;

	@Autowired
	BorrowerDAO brDAO;

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

	@RequestMapping(value = {"/showCheckOutBook"}, method = RequestMethod.GET)
	public String showCheckOutBook(Locale locale, Model model) {
		try {
			List<LibraryBranch> branchList = admin.getAllBranches();
			model.addAttribute("branchList", branchList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "checkOutBook";
	}

	@RequestMapping(value = {"/showReturnBook"}, method = RequestMethod.GET)
	public String showReturnBook(Locale locale, Model model) {
		return "returnBook";
	}

	@RequestMapping(value = {"/booksDropdown"}, method = RequestMethod.GET)
	@ResponseBody
	public StringBuilder booksDropdown(@RequestParam String val1, Model model) {
		StringBuilder sb=new StringBuilder();
		try {
			String branchId=encodeString(val1);
			List<Book> list=borService.getAllBooksForBranch(Integer.parseInt(branchId));
			sb.append("<option disabled selected value=\"0\"> -- select an option -- </option>");
			if (list!=null) {
				for (Book b : list) {
					sb.append("<option value="+b.getBookId()+">"+b.getTitle()+"</option>");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb;
	}
	
	@RequestMapping(value = {"/branchDropdown"}, method = RequestMethod.GET)
	@ResponseBody
	public StringBuilder branchDropdown(@RequestParam String val1, Model model) {
		StringBuilder sb=new StringBuilder();
		String cardNo=encodeString(val1);
		try {
			List<LibraryBranch> list=borService.getAllBranchForBorrower(Integer.parseInt(cardNo));
			if (list!=null) {
				sb.append("<option disabled selected value=\"0\"> -- select an option -- </option>");
				for (LibraryBranch branch : list) {
					sb.append("<option value="+branch.getBranchId()+">"
							+branch.getBranchName()+","+branch.getBranchAddress()+"</option>");
				}
			}else{
				sb.append("<option disabled selected value=\"0\"> No Books To Return</option>");
			}
		} catch (Exception e) {
			e.printStackTrace();
			//sb.append("operation failed "+e.getMessage());
		}
		return sb;
	}
	
	@RequestMapping(value = {"/bookDropInReturn"}, method = RequestMethod.GET)
	@ResponseBody
	public StringBuilder bookDropInReturn(@RequestParam String val1, @RequestParam String val2, Model model) {
		StringBuilder sb=new StringBuilder();
		val1=encodeString(val1);
		val2=encodeString(val2);
		try {
			List<Book> list=borService.getAllBookForBorrower(Integer.parseInt(val2), Integer.parseInt(val1));
			sb.append("<option disabled selected value=\"0\"> -- select an option -- </option>");
			for (Book book : list) {
				sb.append("<option value="+book.getBookId()+">"
						+book.getTitle()+"</option>");
			}
		} catch (Exception e) {
			e.printStackTrace();
			//sb.append("operation failed "+e.getMessage());
		}
		return sb;
	}


	@RequestMapping(value = {"/checkOutBook"}, method = RequestMethod.POST)
	public String checkOutBook(@RequestParam int cardNo, @RequestParam int branchId, @RequestParam int bookId,  Model model) {	
		try{
			LibraryBranch lb=admin.getBranch(branchId);
			Borrower bor=admin.getBorrower(cardNo);
			Book b=admin.getBook(bookId);
			BookLoans bl=new BookLoans(b, lb, bor);
			borService.addBookLoans(bl);
			model.addAttribute("result", "CheckOut Book is successful!!");
		}catch (Exception e) {
			//e.printStackTrace();
			model.addAttribute("result", e.getMessage());
		}
		return "index";
	}
	
	@RequestMapping(value = {"/returnBook"}, method = RequestMethod.POST)
	public String returnBook(@RequestParam int cardNo, @RequestParam int branchId, @RequestParam int bookId,  Model model) {	
		try{
			Borrower bor=new Borrower();
			bor.setCardNo(cardNo);
			LibraryBranch lb=new LibraryBranch();
			lb.setBranchId(branchId);
			Book b=new Book();
			b.setBookId(bookId);
			BookLoans bl=new BookLoans(b, lb, bor);
			borService.updateBookLoans(bl);
			model.addAttribute("result", "Return Book is successful!!");
		}catch (Exception e) {
			//e.printStackTrace();
			model.addAttribute("result", e.getMessage());
		}
		return "index";
	}

	private String encodeString(String s){
		return HtmlUtils.htmlEscape(s);
	}







}
