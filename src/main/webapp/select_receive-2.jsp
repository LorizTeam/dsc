<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="pcpnru.projectModel.*" %>
<%@ page import="pcpnru.projectData.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
	if(session.getAttribute("username") == null)response.sendRedirect("login.jsp");

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>หน้ารายละเอียดการรับ</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width; initial-scale=1.0">
	
		<link rel="shortcut icon" href="/favicon.ico">
		<link href="css/metro.css" rel="stylesheet">
        <link href="css/metro-icons.css" rel="stylesheet">
		<link href="css/metro-schemes.css" rel="stylesheet">
		<link href="css/docs.css" rel="stylesheet">
		<link href="css/jquery.dataTables.min.css" rel="stylesheet">
	 
		<script src="js/jquery-2.1.3.min.js"></script>
	    <script src="js/metro.js"></script>
	    <script src="js/docs.js"></script>
	    <script src="js/prettify/run_prettify.js"></script>
	    <script src="js/ga.js"></script> 
 		<script src="js/jquery.dataTables.min.js"></script>

  </head>
  
  <body>
  	<%	List SelectReceiveList1 = null;
		if (request.getAttribute("SelectReceiveList") == null) {
  		Receive1DB receL = new Receive1DB();
  			SelectReceiveList1 = receL.GetSelectReceiveList("","", "", "", "", "", "");
		}else{
			SelectReceiveList1 = (List) request.getAttribute("SelectReceiveList");
		}
  	%>
  
    <div><%@include file="topmenu.jsp" %></div>
	<br>
	
	<form id="receive-2" action="selectReceive2.action" method="post">
		<input type="hidden" id="docno" name="docno">
		<input type="hidden" id="vol" name="receiveform.vol">
		<input type="hidden" id="project" name="project">
		<input type="hidden" id="cost" name="cost">
		<input type="hidden" id="datetime" name="datetime">
		<input type="hidden" id="amountfrom" name="receiveform.amountfrom">
		<input type="hidden" id="local" name="receiveform.local">
	</form>
	<form action="selectReceive2.action">
		<s:hidden  name="receiveform.project"/>
		<s:hidden  name="receiveform.project_year"/>
		<s:hidden  name="receiveform.cost"/>
		<s:hidden  name="receiveform.docdate"/>
		<s:hidden  name="receiveform.amountfrom"/>
		<s:hidden  name="receiveform.local"/>
		
		<div class="example" data-text="รายการ">
		
		     	<div class="cell" align="center">
		     		<button class="button success" type="submit" name="approve_tobank" value="approve_tobank">ยืนยันการโอนเงิน</button> <input type="checkbox" id="checkall"> เลือกทั้งหมด
		     	</div>
		    
            <table id="table_receives2" class="cell-border display compact nowrap" cellspacing="0"  width="100%">
                <thead>
                <tr>  
                	<th>เลขที่</th>
                	<th>เลขที่เอกสาร</th>
                	<th>เลมที่</th>
                	<th>โครงการ</th>
                    <th>ค่าใช้จ่าย</th>
                    <th>วันที่</th>
                    <th>ได้รับเงินจาก</th>
                    <th>สถานที่</th>
                    <th>ดูรายละเอียด</th>
                    <th>สถานะโอนเงิน </th>
                </tr>
                </thead> 
                  
                 <tbody>
                 <%		List SelectReceiveList = SelectReceiveList1;
                 		if (SelectReceiveList != null) {
						int x = 0;
						for (Iterator iter = SelectReceiveList.iterator(); iter.hasNext();) {
						x++; 
						ReceiveForm receiveMaster = (ReceiveForm) iter.next();
				%>
				
                <tr>  
                    <td align="center"><%=x%></td> 
                    <td class="tddocno" align="left"><%=receiveMaster.getDocNo()%></td>
                    <td class="tdvol" align="left"><%=receiveMaster.getVol()%></td>
                    <td class="tdproject" align="left"><%=receiveMaster.getProject()%></td>  
                    <td class="tdcost" align="left"><%=receiveMaster.getCost()%></td> 
                    <td class="tddatetime" align="center"><%=receiveMaster.getDocdate()%></td> 
                    <td class="tdamountfrom" align="left"><%=receiveMaster.getAmountfrom()%></td> 
                    <td class="tdlocal" align="left"><%=receiveMaster.getLocal()%></td>
                    <td class="" align="center" width="5%"><button class="seedetail"><span class="mif-search fg-green"></span></button></td>
                    <%
                    if(receiveMaster.getApprove_tobank().equals("1")){
                    %>
                    <td class="" align="left"><span class="mif-checkmark fg-green"></span> โอนแล้ว</td>
                    <%
                    }else{
                    %>
                    <td class="" align="left"><input type="checkbox" name="valueapprove_tobank" value="<%=receiveMaster.getDocNo()%> - <%=receiveMaster.getProject()%> - <%=receiveMaster.getProject_year()%>"> ยังไม่โอน</td>
                    <%
                    }
                    %> 
                      
                </tr>
                 <% 	} %>
                
                <%} else { %>
                <tr> 
                    <td colspan="6">ไม่พบข้อมูล</td> 
                </tr> 
                <%	} %> 
                </tbody>
            </table>
             
             <div class="cell" align="center">
	     		<button class="button success" type="submit" name="approve_tobank" value="approve_tobank">ยืนยันการโอนเงิน</button>
	     	</div>
        </div> <!-- End of example table -->
         
    </form>

<script>

	$('#table_receives2').DataTable( { 
	  	scrollY: '60vh', 
	  	scrollX: true,
	  	scrollCollapse: true,
	    ordering: false,
	    "lengthMenu": [[12, 30, 60, 100, -1], [12, 30, 60, 100, "All"]] 
	} ); 
	
	$("button.seedetail").click(function(event){
		var $index = $("button.seedetail").index(this);
		
        var docno = $(".tddocno").eq($index).text();
        var vol = $(".tdvol").eq($index).text();
        var projectname = $(".tdproject").eq($index).text();
	    var costname = $(".tdcost").eq($index).text();
	    var datetime = $(".tddatetime").eq($index).text();
	    var amountfrom = $(".tdamountfrom").eq($index).text();
	    var local = $(".tdlocal").eq($index).text();

	    $("#project").val(projectname);
	 	$("#docno").val(docno);
	 	$("#vol").val(vol);
	 	$("#cost").val(costname);
	 	$("#datetime").val(datetime);
	 	$("#amountfrom").val(amountfrom);
	 	$("#local").val(local);
	 	
		event.preventDefault();
		
		$("#receive-2").submit();
	});
	$("#checkall").click(function(){
		if($(this).prop("checked")){
			$('[name="valueapprove_tobank"]').prop("checked",true);
		}else{
			$('[name="valueapprove_tobank"]').prop("checked",false);
		}
	});
</script>
  </body>
</html>
