<%@page import="com.gcit.library.LibraryConfig"%>
<%@page import="org.springframework.context.annotation.AnnotationConfigApplicationContext"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.library.entity.Publisher"%>
<%@page import="com.gcit.library.entity.Book"%>
<%@page import="com.gcit.library.entity.Author"%>
<%@page import="com.gcit.library.service.Adminstrator"%>
<%
	ApplicationContext ctx = new AnnotationConfigApplicationContext(LibraryConfig.class);
	Adminstrator admin = ctx.getBean(Adminstrator.class);
	Integer bookId=Integer.parseInt(request.getParameter("bookId"));
	Book b=admin.getBook(bookId);
	Publisher pObj=b.getPublisher();
	
	List<Author> aList=admin.getAllAuthors();
	List<Publisher> pList=admin.getAllPublisher();
%>

<div class="modal-content">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
		<h4 class="modal-title">Edit Book</h4>
	</div>
	<form action="updateBookRow" method="post" id="myform">
	<div class="modal-body">
			<label for="BookTitle">Book Title :</label>
			<input type="text" class="form-control" name="title" placeholder="Edit &quot; <%=b.getTitle() %> &quot;" style="width: 400px;" >
			 
			 <label for="Authors">Authors :</label>
			 <br>
			 <select class="js-example-basic-multiple" multiple="multiple" style="width:400px;" name="authors">
			<option value="0" >-- select/ None --</option>	
				<%int flag=0;
				for(Author author:aList) {%>
					<%if(b.getAuthorsList()!=null){ %>
					<%for(Author a2:b.getAuthorsList()) {%>
						<% if(a2.getAuthorId()==author.getAuthorId() && a2.getAuthorName().equals(author.getAuthorName())){%>
							<option value=<%=author.getAuthorId() %> selected="selected"><%=author.getAuthorName() %></option>
						<%}else{ %>
							<option value=<%=author.getAuthorId() %>><%=author.getAuthorName() %></option>
				<%}}%>
				<%}else{ flag=1;%>
				
					<option value=<%=author.getAuthorId() %>><%=author.getAuthorName() %></option>
				<%}	
				}%>
				<%if(flag==1){%>
					<option value="0" selected="selected">-- select/ None --</option>	
				<%} %>
			</select>
			
			<br>
			
			<label for="publisherName">Publisher Name :</label>
			<select id="pub" name="pubId" class="form-control" style="width: 400px;">
    	 <option value="0">-- select an option --</option>
    	  	
    	<% for(Publisher p:pList){ %> 
    	<%if(pObj!=null && p.getPublisherId()==pObj.getPublisherId() ) {%>
    		<option value="<%=p.getPublisherId()%>" selected="selected"><%=p.getPublisherName()%></option>
    	<%}else{ %>
    		<option value="<%=p.getPublisherId()%>"><%=p.getPublisherName()%></option>
    	
    	<%}} %>
    		 
    	</select><br>
		<input type="hidden" value="<%=b.getBookId()%>" name="bookId">
				
	</div>
	<div class="modal-footer">
		<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
		<input id="save" type="submit" class="btn btn-primary" value="Save changes" />
	</div>
	</form>
</div>
	
<script>
	$(document).ready(function() {

		$('.modal').on('hidden.bs.modal', function(e) {
			$(this).removeData();
		});
		$('.modal').on('shown.bs.modal', function(e) {
			$(".js-example-basic-multiple").select2();
		});
	});
</script>