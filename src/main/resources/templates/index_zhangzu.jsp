<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html >
	<head>
		<meta charset="utf-8" />
		<title>宋账本</title>
		<!--index_zhangzu.jsp -->
		<meta http-equiv="pragma" content="no-cache"/>   
		<meta http-equiv="Cache-Control" content="no-cache, must-revalidate"/>   
		<meta http-equiv="expires" content="0"/>
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"> 
		<link href="${pageContext.request.contextPath}/static/bootstrap3.0.0/css/bootstrap.min.css" rel="stylesheet" media="screen">
		<link href="${pageContext.request.contextPath}/static/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
		<%@include file="./css_zhangzu.jsp" %>
		
	</head>
	
	<body>
		<%@include file="./logo.jsp" %>
		
		<div class="container">
			
			<div class="page-content">
				
				<div class="col-sm-12 ">
					<h1>
						<small>
							<i class="icon-list-alt"></i>
							流水账一览
							
						</small>
					</h1>
				</div>
				<br/>
		        <div class="col-sm-12 ">
		          <button type="button" class="btn btn-lg btn-block btn-primary" onclick="location.href = '<%=request.getContextPath() %>/zhangzu/to_zhangzu_insert.do';">添加新单据</button>
		        </div>
				<br/>
		        <div class="col-sm-12 ">
		          <button type="button" class="btn btn-lg btn-block btn-info" onclick="location.href = '<%=request.getContextPath() %>/zhangzu/to_zhangzu_analysis_2021.do?zhangzu_ac=${INDEX_AC}';">统计分析(2021年)</button>
		        </div>
				<br/>
		        <div class="col-sm-12 ">
		          <button type="button" class="btn btn-lg btn-block btn-info" onclick="location.href = '<%=request.getContextPath() %>/zhangzu/to_zhangzu_analysis_2020.do?zhangzu_ac=${INDEX_AC}';">统计分析(2020年)</button>
		        </div>
				<br/>
		        <div class="col-sm-12 ">
		          <button type="button" class="btn btn-lg btn-block btn-info" onclick="location.href = '<%=request.getContextPath() %>/zhangzu/to_zhangzu_analysis_2019.do?zhangzu_ac=${INDEX_AC}';">统计分析(2019年)</button>
		        </div>
				<br/>
		        <div class="col-sm-12 ">
		          <button type="button" class="btn btn-lg btn-block btn-info" onclick="location.href = '<%=request.getContextPath() %>/zhangzu/to_zhangzu_analysis.do?zhangzu_ac=${INDEX_AC}';">统计分析(2018年)</button>
		        </div>
				<br/>
		        <div class="col-sm-12 ">
		          <button type="button" class="btn btn-lg btn-block btn-warning" onclick="location.href = '<%=request.getContextPath() %>/zhangzu/to_index_zhangzu.do?FLG=2020';">2020年</button>
		        </div>
				<br/>
		        <div class="col-sm-12 ">
		          <button type="button" class="btn btn-lg btn-block btn-warning" onclick="location.href = '<%=request.getContextPath() %>/zhangzu/to_index_zhangzu.do?FLG=2019';">2019年</button>
		        </div>
				<br/>
		        <div class="col-sm-12 ">
		          <button type="button" class="btn btn-lg btn-block btn-warning" onclick="location.href = '<%=request.getContextPath() %>/zhangzu/to_index_zhangzu.do?FLG=2018';">2018年</button>
		        </div>
				<br/>
							<!--PAGE CONTENT BEGINS-->
								<table id="sample-table-1" class="table table-striped table-bordered table-hover">
								<thead>
									<tr >
										<!--
										<th width="5%">ID</th>
										-->
										<th width="10%">日期</th>
										<th width="15%">名称</th>
										<th width="10%">金额</th>
										<th width="10%">分类</th>
										<th width="10%">收支</th>
										<th width="20%">备注</th>
										<th width="10%">售价</th>
										<th width="10%">操作</th>
									</tr>
								</thead>
								<tbody>
								<c:forEach items="${zhangzus}"  var="zhangzu"  >
								
									<tr >

									
										<!--
										<td><a target='_blank' href='${pageContext.request.contextPath}/blog/articles/${blog.id}.html'>${chr.id}</a></td>
										-->
										<!--
										<td>${zhangzu.id}</td>
										-->
										<td>${zhangzu.z_date}</td>
										<td>${zhangzu.z_name}</td>
										<td>${zhangzu.z_amount}</td>
										<td>${zhangzu.z_type}</td>
										<td>${zhangzu.z_io_div}</td>
										<td>${zhangzu.z_remark}</td>
										<td>${zhangzu.z_m_amount}</td>
										<td>
										<button class="btn btn-info btn-small" onclick="javascript:window.location.href='<%=request.getContextPath() %>/zhangzu/to_zhangzu_update.do?id=${zhangzu.id}'">
											编辑
										</button>

										</td>
										
									</tr>
								</c:forEach>
								</tbody>
							</table>
		
								


		    
		        <div class="col-sm-12 ">
		          <button type="button" class="btn btn-lg btn-block btn-primary" onclick="location.href = '<%=request.getContextPath() %>/zhangzu/to_zhangzu_insert.do';">添加新单据</button>
		        </div>

							<!--PAGE CONTENT ENDS-->

	</div><!--/.main-container-->

	

	
<script type="text/javascript">
		
function get_express_by_name(){
	var c_name = document.all.c_name.value;
	
	//alert(getEx(file_name).toString().toLowerCase());
	if (c_name == ""){
		$("#div_alert_c_name_blank").attr("class","alert alert-danger");
	}else {
		document.all.myform.action="${pageContext.request.contextPath}/haoyun/get_express_by_name.do?c_name="+c_name;
		document.all.myform.submit(); 
	}

}
function get_express_by_number(){
	var c_number = document.all.c_number.value;
	
	if (c_number == ""){
		$("#div_alert_c_number_blank").attr("class","alert alert-danger");
	}else {
		document.all.myform.action="${pageContext.request.contextPath}/haoyun/get_express_by_number.do?c_number="+c_number;
		document.all.myform.submit(); 
	}

}

			
</script>

</div>
</body>
</html>