<%@page import="com.gcit.library.LibraryConfig"%>
<%@page import="org.springframework.context.annotation.AnnotationConfigApplicationContext"%>
<%@page import="com.gcit.library.service.Adminstrator"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="com.gcit.library.entity.Author"%>
<%
	
	Integer authorId=Integer.parseInt(request.getParameter("authorId"));
	ApplicationContext ctx = new AnnotationConfigApplicationContext(LibraryConfig.class);
	Adminstrator admin = ctx.getBean(Adminstrator.class);	
	Author a=admin.getAuthor(authorId);
	((AnnotationConfigApplicationContext)ctx).close();
	
%>


<div class="modal-content">

	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
		<h4 class="modal-title">Edit Author</h4>
	</div>
	<form action="updateAuthorRow" method="post">
	<div class="modal-body">
		
			<input type="text" class="form-control" name="authorName" placeholder="Edit &quot; <%=a.getAuthorName() %> &quot;" style="width: 400px;" >
			<input type="hidden" value="<%=a.getAuthorId()%>" name="authorId">
		
	</div>
	<div class="modal-footer">
		<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
		<input type="submit" class="btn btn-primary" value="Save changes" />
	</div>
	</form>
</div>

<script>
$(document).ready(function()
		{
		    $('.modal').on('hidden.bs.modal', function(e)
		    { 
		        $(this).removeData();
		    }) ;
		});
</script>