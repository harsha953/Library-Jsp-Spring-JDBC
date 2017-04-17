package com.gcit.library;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import com.gcit.library.entity.Author;
import com.gcit.library.entity.Book;
import com.gcit.library.entity.BookLoans;
import com.gcit.library.entity.Borrower;
import com.gcit.library.entity.LibraryBranch;
import com.gcit.library.entity.Publisher;
import com.gcit.library.service.Adminstrator;
import com.gcit.library.service.BorrowerService;

@Controller
public class HomeController {

	@Autowired
	Adminstrator admin;

	@Autowired
	BorrowerService borService;

	@RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
	public String index(Locale locale, Model model) {
		return "index";
	}

	@RequestMapping(value = {"/showUpdateBranch"}, method = RequestMethod.GET)
	public String showUpdateBranch(Locale locale, Model model) {
		try {
			updateBranchHelp(model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "updateBranch";
	}

	private void updateBranchHelp(Model model) throws Exception {
		List<LibraryBranch> branchList;
		branchList = admin.getAllBranches();
		model.addAttribute("branchList", branchList);
	}

	@RequestMapping(value = {"/updateBranch"}, method = RequestMethod.POST)
	public String updateBranch(@RequestParam int branchId, @RequestParam String branchName, @RequestParam String branchAddress, Model model) {
		try {
			branchName=encodeString(branchName);
			branchAddress=encodeString(branchAddress);
			LibraryBranch lb=new LibraryBranch(branchName, branchAddress);
			lb.setBranchId(branchId);
			admin.updateBranch(lb);
			model.addAttribute("result", "Updating Branch of \""+encodeString(branchName)+"\" is successful!!");

		} catch (Exception e) {
			//e.printStackTrace();
			model.addAttribute("result", e.getMessage());
		}
		return "index";
	}

	@RequestMapping(value = {"/showUpdatePublisher"}, method = RequestMethod.GET)
	public String showUpdatePublisher(Locale locale, Model model) {
		try {
			updatePublisherHelp(model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "updatePublisher";
	}

	private void updatePublisherHelp(Model model) throws Exception {
		List<Publisher> publisherList=admin.getAllPublisher();
		model.addAttribute("publisherList", publisherList);
	}

	@RequestMapping(value = {"/updatePublisher"}, method = RequestMethod.POST)
	public String updatePublisher(@RequestParam int publisherId, @RequestParam String publisherName, @RequestParam String publisherAddress, @RequestParam String publisherPhone, Model model) {
		try {
			publisherName=encodeString(publisherName);
			publisherAddress=encodeString(publisherAddress);
			publisherPhone=encodeString(publisherPhone);
			Publisher p=new Publisher(publisherName, publisherAddress, publisherPhone);
			p.setPublisherId(publisherId);
			admin.updatePublisher(p);
			model.addAttribute("result", "Updating Publisher of \""+encodeString(publisherName)+"\" is successful!!");

		} catch (Exception e) {
			//e.printStackTrace();
			model.addAttribute("result", e.getMessage());
		}
		return "index";
	}

	@RequestMapping(value = {"/showUpdateBorrower"}, method = RequestMethod.GET)
	public String showUpdateBorrower(Locale locale, Model model) {
		try {
			updateBorrowerHelp(model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "updateBorrower";
	}

	private void updateBorrowerHelp(Model model) throws Exception {
		List<Borrower> borrowerList=admin.getAllBorrowers();
		model.addAttribute("borrowerList", borrowerList);
	}

	@RequestMapping(value = {"/updateBorrower"}, method = RequestMethod.POST)
	public String updateBorrower(@RequestParam int cardNo, @RequestParam String name, @RequestParam String address, @RequestParam String phone, Model model) {
		try {
			name=encodeString(name);
			address=encodeString(address);
			phone=encodeString(phone);
			Borrower b=new Borrower(name, address, phone);
			b.setCardNo(cardNo);
			admin.updateBorrower(b);
			model.addAttribute("result", "Updating Borrower of \""+encodeString(name)+"\" is successful!!");
		} catch (Exception e) {
			//e.printStackTrace();
			model.addAttribute("result", e.getMessage());
		}
		return "index";
	}

	@RequestMapping(value = {"/showAddBranch"}, method = RequestMethod.GET)
	public String showAddBranch(Locale locale, Model model) {
		return "addBranch";
	}

	@RequestMapping(value = {"/addBranch"}, method = RequestMethod.POST)
	public String addBranch(@RequestParam String branchName, @RequestParam String branchAddress, Model model) {
		try {
			branchName=encodeString(branchName);
			branchAddress=encodeString(branchAddress);
			LibraryBranch branch=new LibraryBranch(branchName, branchAddress);
			admin.addBranch(branch);
			model.addAttribute("result", "Adding Branch \""+encodeString(branchName)+"\" successful!!");
		} catch (Exception e) {
			model.addAttribute("result", e.getMessage());
		}
		return "index";
	}

	@RequestMapping(value = {"/showAddPublisher"}, method = RequestMethod.GET)
	public String showAddPublisher(Locale locale, Model model) {
		return "addPublisher";
	}

	@RequestMapping(value = {"/addPublisher"}, method = RequestMethod.POST)
	public String addPublisher(@RequestParam String publisherName, @RequestParam String publisherAddress,  @RequestParam String publisherPhone, Model model) {
		try {
			publisherName=encodeString(publisherName);
			publisherAddress=encodeString(publisherAddress);
			publisherPhone=encodeString(publisherPhone);
			Publisher p=new Publisher(publisherName, publisherAddress, publisherPhone);
			admin.addPublisher(p);
			model.addAttribute("result", "Adding Publisher \""+encodeString(publisherName)+"\" successful!!");
		} catch (Exception e) {
			model.addAttribute("result", e.getMessage());
		}
		return "index";
	}

	@RequestMapping(value = {"/showAddBorrower"}, method = RequestMethod.GET)
	public String showAddBorrower(Locale locale, Model model) {
		return "addBorrower";
	}

	@RequestMapping(value = {"/addBorrower"}, method = RequestMethod.POST)
	public String addBorrower(@RequestParam String borrowerName, @RequestParam String borrowerAddress,  @RequestParam String borrowerPhone, Model model) {
		try {
			borrowerName=encodeString(borrowerName);
			borrowerAddress=encodeString(borrowerAddress);
			borrowerPhone=encodeString(borrowerPhone);
			Borrower b=new Borrower(borrowerName, borrowerAddress, borrowerPhone);
			admin.addBorrower(b);
			model.addAttribute("result", "Adding Borrower \""+encodeString(borrowerName)+"\" successful!!");
		} catch (Exception e) {
			model.addAttribute("result", e.getMessage());
		}
		return "index";
	}

	@RequestMapping(value = {"/showDeletePublisher"}, method = RequestMethod.GET)
	public String showDeletePublisher(Locale locale, Model model) {
		try {
			updatePublisherHelp(model);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "deletePublisher";
	}

	@RequestMapping(value = {"/deletePublisher"}, method = RequestMethod.POST)
	public String deletePublisher(@RequestParam int publisherId, Model model) {
		Publisher p=new Publisher();
		p.setPublisherId(publisherId);
		try {
			admin.deletePublisher(p);
			model.addAttribute("result", "Delete Book is successful!!");
		} catch (Exception e) {
			model.addAttribute("result", e.getMessage());
		}
		return "index";
	}

	@RequestMapping(value = {"/showDeleteBranch"}, method = RequestMethod.GET)
	public String showDeleteBranch(Locale locale, Model model) {
		try {
			updateBranchHelp(model);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "deleteBranch";
	}

	@RequestMapping(value = {"/deleteBranch"}, method = RequestMethod.POST)
	public String deleteBranch(@RequestParam int branchId, Model model) {
		try{
			LibraryBranch lb=new LibraryBranch();
			lb.setBranchId(branchId);
			admin.deleteBranch(lb);
			model.addAttribute("result", "Delete Branch is successful!!");
		} catch (Exception e) {
			model.addAttribute("result", e.getMessage());
		}
		return "index";
	}

	@RequestMapping(value = {"/showDeleteBorrower"}, method = RequestMethod.GET)
	public String showDeleteBorrower(Locale locale, Model model) {
		try {
			updateBorrowerHelp(model);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "deleteBorrower";
	}

	@RequestMapping(value = {"/deleteBorrower"}, method = RequestMethod.POST)
	public String deleteBorrower(@RequestParam int cardNo, Model model) {
		try{
			Borrower b=new Borrower();
			b.setCardNo(cardNo);
			admin.deleteBorrower(b);
			model.addAttribute("result", "Delete Borrower is successful!!");
		} catch (Exception e) {
			model.addAttribute("result", e.getMessage());
		}
		return "index";
	}

	@RequestMapping(value = {"/showAddAuthor"}, method = RequestMethod.GET)
	public String showAddAuthor(Locale locale, Model model) {
		return "addAuthor";
	}

	@RequestMapping(value = {"/searchBookLoans"}, method = RequestMethod.GET)
	@ResponseBody
	public StringBuilder searchBookLoans(@RequestParam int searchString) {
		List<BookLoans> list=new ArrayList<BookLoans>();
		StringBuilder sb=new StringBuilder();
		try{
			list=admin.getAllBookLoans(searchString);
			if(list!=null){
				sb.append("<thead><tr><th>cardNo.</th><th>Branch Name</th><th>Book Name</th><th>Date Out</th><th>Due Date</th></tr></thead>");

				for (BookLoans b : list) {
					sb.append("<tbody><tr><th scope='row'>"+b.getBorrower().getCardNo()+"</th>");
					sb.append("<td>"+b.getLibraryBranch().getBranchName()+"</td>");
					sb.append("<td>"+b.getBook().getTitle()+"</td>");

					sb.append("<td>"+b.getDateOut()+"</td>");
					sb.append("<td>"+b.getDueDate()+"</td>");
					String date=new SimpleDateFormat("yyyy-MM-dd").format(new Date());

					sb.append("<td><button type='button' class='btn btn-sm btn-success' href='./dueDateModal?cardNo="+b.getBorrower().getCardNo()+"&branchId="+b.getLibraryBranch().getBranchId()+"&bookId="+b.getBook().getBookId()+"&dueDate="+b.getDueDate()+"&dateOut="+date+" 'data-target='#editModal' data-toggle='modal'>");
					sb.append("<span class='glyphicon glyphicon-edit' aria-hidden='true'></span> Override Due Date</button></td>");
				}
			}else{
				sb.append("<div class='alert alert-info' role='alert'><strong>Info: </strong>No Books Taken</div>");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return sb;
	}

	@RequestMapping(value = {"/checkValidity"}, method = RequestMethod.GET)
	@ResponseBody
	public String checkValidity(@RequestParam int val1, Model model) {
		String s=new String();
		try {
			boolean check=borService.checkCardNo(val1);
			if (check) {
				s="valid";
			}else{
				s="Invalid";
			}
		}catch (Exception e) {
			s="Invalid";
		}
		return s;
	}

	@RequestMapping(value = {"/showAddBook"}, method = RequestMethod.GET)
	public String showAddBook(Locale locale, Model model) {
		try {
			List<Author> authorList=admin.getAllAuthors();
			List<Publisher> publisherList=admin.getAllPublisher();
			model.addAttribute("authorList", authorList);
			model.addAttribute("publisherList", publisherList);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("result", e.getMessage());
		}
		return "addBook";
	}

	@RequestMapping(value = {"/showDueDate"}, method = RequestMethod.GET)
	public String showDueDate(Locale locale, Model model) {
		return "dueDate";
	}

	@RequestMapping(value = {"/addBook"}, method = RequestMethod.POST)
	public String addBook(@RequestParam String title, @RequestParam int[] authorId, @RequestParam int pubId, Model model) {
		title=encodeString(title);
		Book b=new Book();
		try {
			b.setTitle(title);
			if (pubId==0) {
				b.setPublisher(null);
			}else{
				b.setPublisher(admin.getPublisher(pubId));
			}
			List<Author> list=new ArrayList<Author>();
			if (authorId==null) {
				list=null;
			}else{
				for (Integer i : authorId) {
					if (i==0) {
						list=null;
						break;
					}
					list.add(admin.getAuthor(i));
				}
			}
			b.setAuthorsList(list);
			admin.addBook(b);
			model.addAttribute("result", "Adding Book \""+title+"\" successful!!");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("result", e.getMessage());
		}
		return "index";
	}

	@RequestMapping(value = {"/addAuthor"}, method = RequestMethod.POST)
	public String addAuthor(@RequestParam("authorName") String authorname, Model model) {
		Author a=new Author();
		a.setAuthorName(authorname);
		try {
			admin.addAuthor(a);
			model.addAttribute("result", "Adding Author \""+authorname+"\" successful!!");

		} catch (Exception e) {
			model.addAttribute("result", e.getMessage());
		}
		return "index";
	}

	@RequestMapping(value = {"/showDisplayAuthor"}, method = RequestMethod.GET)
	public String displayAuthors(Locale locale, Model model) {
		return "displayAuthors";
	}

	@RequestMapping(value = {"/showDisplaybooks"}, method = RequestMethod.GET)
	public String displayBooks(Locale locale, Model model) {
		return "displayBooks";
	}

	@RequestMapping(value = {"/searchAuthors"}, method = RequestMethod.GET)
	@ResponseBody
	public StringBuilder searchAuthors(@RequestParam String searchString, @RequestParam int pageNo, Model model) {
		searchString=encodeString(searchString);
		List<Author> list=new ArrayList<Author>();
		StringBuilder sb=new StringBuilder();
		try {
			list=admin.getAllAuthors(pageNo, searchString);

			if(!list.isEmpty()){
				sb.append("<thead><tr><th>#</th><th>Author Name</th><th>Edit</th><th>Delete</th></tr></thead>");

				for (Author author : list) {
					sb.append("<tbody><tr><th scope='row'>"+((list.indexOf(author))+ 1)+"</th>");
					sb.append("<td>"+author.getAuthorName().trim()+"</td>");
					sb.append("<td><button type='button' class='btn btn-sm btn-success' href='./updateAuthor?authorId="+author.getAuthorId()+"' data-target='#editModal' data-toggle='modal'>");
					sb.append("<span class='glyphicon glyphicon-edit' aria-hidden='true'></span> Edit</button></td>");
					sb.append("<td><button type='button' class='btn btn-sm btn-danger' href='./deleteAuthor?authorId="+author.getAuthorId()+"' data-target='#deleteModal' data-toggle='modal'>");
					sb.append("<span class='glyphicon glyphicon-trash' aria-hidden='true'></span>Delete</button></td></tr></tbody>");
				}
			}else{

				sb.append("0");
			}
		}catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("flag", "true");
		}
		return sb;
	}

	@RequestMapping(value = {"/searchBooks"}, method = RequestMethod.GET)
	@ResponseBody
	public StringBuilder searchBooks(@RequestParam String searchString, @RequestParam int pageNo, Model model) {
		searchString=encodeString(searchString);
		List<Book> list=new ArrayList<Book>();
		StringBuilder sb=new StringBuilder();
		try {
			list=admin.getAllBook(pageNo, searchString);
			if(list!=null){
				sb.append("<thead><tr><th>#</th><th>Title</th><th>Publisher Name</th><th>Authors</th><th>Edit</th><th>Delete</th></tr></thead>");

				for (Book b : list) {
					sb.append("<tbody><tr><th scope='row'>"+((list.indexOf(b))+ 1)+"</th>");
					sb.append("<td>"+b.getTitle()+"</td>");
					if (b.getPublisher()!=null) {
						sb.append("<td>"+b.getPublisher().getPublisherName()+"</td>");
					}else{
						sb.append("<td></td>");
					}
					if (b.getAuthorsList()!=null) {
						sb.append("<td>");
						String prefix="";
						for (Author a : b.getAuthorsList()) {
							sb.append(prefix);
							prefix=",";
							sb.append(" "+a.getAuthorName());
						}
						sb.append("</td>");

					}else{
						sb.append("<td></td>");
					}
					sb.append("<td><button type='button' class='btn btn-sm btn-success' href='./updateBook?bookId="+b.getBookId()+"' data-target='#editModal' data-toggle='modal'>");
					sb.append("<span class='glyphicon glyphicon-edit' aria-hidden='true'></span> Edit</button></td>");
					sb.append("<td><button type='button' class='btn btn-sm btn-danger' href='./deleteBook?bookId="+b.getBookId()+"' data-target='#deleteModal' data-toggle='modal'>");
					sb.append("<span class='glyphicon glyphicon-trash' aria-hidden='true'></span>Delete</button></td></tr></tbody>");}
			}else{
				sb.append("0");
			}
		}catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("flag", "true");
		}
		return sb;

	}

	@RequestMapping(value = {"/pageAuthors"}, method = RequestMethod.GET)
	@ResponseBody
	public StringBuilder pageAuthors(@RequestParam String searchString, Model model) {
		searchString=encodeString(searchString);
		StringBuilder str = new StringBuilder();
		try{
			int totalCount=admin.getAuthorsCountByName(searchString);
			int pageCount = getPageCount(totalCount);

			if(totalCount!=0){
				str.append("<ul class='pagination'>");
				str.append("<li><a href='#' aria-label='Previous'> <span aria-hidden='true'>&laquo;</span></a></li>");
				for (int i = 1; i <= pageCount; i++) {
					str.append("<li class='page-item'><a href='javascript:search("+i+")'>"+i+"</a></li>");
				}
				str.append("<li><a href='#' aria-label='Next'> <span aria-hidden='true'>&raquo;</span></a></li>");
				str.append("</ul>");
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	@RequestMapping(value = {"/pageBooks"}, method = RequestMethod.GET)
	@ResponseBody
	public StringBuilder pageBooks(@RequestParam String searchString, Model model) {
		searchString=encodeString(searchString);
		StringBuilder str = new StringBuilder();
		try{
			int totalCount=admin.getBooksCountByName(searchString);
			int pageCount = getPageCount(totalCount);

			if(totalCount!=0){
				str.append("<ul class='pagination'>");
				str.append("<li><a href='#' aria-label='Previous'> <span aria-hidden='true'>&laquo;</span></a></li>");
				for (int i = 1; i <= pageCount; i++) {
					str.append("<li class='page-item'><a href='javascript:search("+i+")'>"+i+"</a></li>");
				}
				str.append("<li><a href='#' aria-label='Next'> <span aria-hidden='true'>&raquo;</span></a></li>");
				str.append("</ul>");

			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	@RequestMapping(value = {"/deleteAuthor"}, method = RequestMethod.GET)
	public String showDeleteAuthors(@RequestParam int authorId, Model model) {
		return "deleteAuthor";
	}

	@RequestMapping(value = {"/deleteBook"}, method = RequestMethod.GET)
	public String showDeleteBooks(@RequestParam int bookId, Model model) {
		return "deleteBook";
	}

	@RequestMapping(value = {"/updateAuthor"}, method = RequestMethod.GET)
	public String showUpdateAuthors(@RequestParam int authorId, Model model) {
		return "updateAuthor";
	}

	@RequestMapping(value = {"/updateBook"}, method = RequestMethod.GET)
	public String showUpdateBooks(@RequestParam int bookId, Model model) {
		return "updateBook";
	}

	@RequestMapping(value = {"/deleteAuthorRow"}, method = RequestMethod.POST)
	private String deleteAuthor(@RequestParam int authorId, Model model)  {
		try{
			Author a=new Author();
			a.setAuthorId(authorId);
			admin.deleteAuthor(a);
			model.addAttribute("flag", "false");
			model.addAttribute("result", "deleted Author Successfully!!!");

		}catch(Exception e){
			model.addAttribute("result", e.getMessage());
			model.addAttribute("flag", "true");
		}
		return "displayAuthors";
	}

	@RequestMapping(value = {"/deleteBookRow"}, method = RequestMethod.POST)
	private String deleteBook(@RequestParam int bookId, Model model)  {
		try{
			Book b=new Book();
			b.setBookId(bookId);
			admin.deleteBook(b);
			model.addAttribute("flag", "false");
			model.addAttribute("result", "deleted Book Successfully!!!");

		}catch(Exception e){
			model.addAttribute("result", e.getMessage());
			model.addAttribute("flag", "true");
		}
		return "displayBooks";
	}

	@RequestMapping(value = {"/updateAuthorRow"}, method = RequestMethod.POST)
	private String updateAuthor(@RequestParam int authorId, @RequestParam String authorName, Model model)  {
		try{
			authorName=encodeString(authorName);
			Author a=new Author();
			a.setAuthorId(authorId);
			a.setAuthorName(authorName);
			admin.updateAuthor(a);
			model.addAttribute("flag", "false");
			model.addAttribute("result", "Updated Author name to "+authorName+" Successfully!!!");

		}catch(Exception e){
			model.addAttribute("result", e.getMessage());
			model.addAttribute("flag", "true");
		}
		return "displayAuthors";
	}

	@RequestMapping(value = {"/dueDateModal"}, method = RequestMethod.GET)
	private String showDueDateModal(@RequestParam int cardNo, @RequestParam int branchId, @RequestParam int bookId, @RequestParam java.sql.Date dueDate, @RequestParam java.sql.Date dateOut)  {
		return "dueDateModal";
	}

	@RequestMapping(value = {"/overrideDueDate"}, method = RequestMethod.POST)
	private String overrideDueDate(@RequestParam int cardNo, @RequestParam int branchId, @RequestParam int bookId, @RequestParam java.sql.Date dueDate, Model model)  {
		try{
			Borrower br=new Borrower();
			br.setCardNo(cardNo);
			LibraryBranch lb=new LibraryBranch();
			lb.setBranchId(branchId);
			Book b=new Book();
			b.setBookId(bookId);
			BookLoans bl=new BookLoans(b, lb, br);
			bl.setDueDate(dueDate);
			admin.updateDueDate(bl);
			model.addAttribute("cardNo", cardNo);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "dueDate";
	}

	@RequestMapping(value = {"/updateBookRow"}, method = RequestMethod.POST)
	private String updateBook(@RequestParam String title, @RequestParam int[] authors, @RequestParam int pubId, @RequestParam int bookId, Model model)  {
		title=encodeString(title);
		try{
			Book old=admin.getBook(bookId);
			Book b=new Book();
			b.setBookId(bookId);
			if (title==null || title.trim().length()==0) {
				b.setTitle(old.getTitle());
			}else{
				b.setTitle(title);
			}
			if (authors[0]==0) {
				b.setAuthorsList(null);
			}else{
				List<Author> aList=new ArrayList<Author>();
				for (int i : authors) {
					Author a=admin.getAuthor(i);
					aList.add(a);
				}
				b.setAuthorsList(aList);
			}
			if (pubId==0) {
				b.setPublisher(null);
			}else{
			b.setPublisher(admin.getPublisher(pubId));
			}
			admin.updateBook(b);
			model.addAttribute("flag", "false");
			model.addAttribute("result", "Updated Book Successfully!!!");
		}catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("flag", "true");
			model.addAttribute("result", e.getMessage());
		}
		return "displayBooks";
	}

	private int getPageCount(int totalCount) {
		int pageSize=10;
		int pageCount=0;
		if(totalCount%pageSize>0)
			pageCount=(totalCount/pageSize)+1;
		else
			pageCount=totalCount/pageSize;
		return pageCount;
	}
	private String encodeString(String s){
		return HtmlUtils.htmlEscape(s);
	}


}
