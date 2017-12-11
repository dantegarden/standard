<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/commons/taglibs.jsp"%>
<%@include file="/WEB-INF/commons/pre-include.jsp"%>

<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">

<title>DVT AUTOCODE WEB-UI</title>

<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<script src="${ctx}/static/my/js/main.js"></script>

</head>

<body data-color="grey" class="flat">
	<div id="wrapper">
		<div id="header">
			<h1>
				<a href="#">Unicorn Admin</a>
			</h1>
			<a id="menu-trigger" href="#"><i class="fa fa-align-justify"></i></a>
		</div>

		<div id="sidebar">
			<div id="search">
				<input type="text" placeholder="Search here..."/><button type="submit" class="tip-right" title="Search"><i class="fa fa-search"></i></button>
			</div>
			<ul>
				<li class="active"><a href="${ctx}/test"><i class="fa fa-home"></i> <span>代码生成器</span></a></li>
			</ul>
		</div>

		<div id="content">
			<div id="content-header">
				<h1>代码生成器</h1>

			</div>
			<div id="breadcrumb">
				<a href="#" title="Go to Home" class="tip-bottom"><i
					class="fa fa-home"></i> 首页</a> <a href="#" class="current">代码生成器</a>
			</div>
	
			<div class="container-fluid">
				<div class="row">
					<div class="col-xs-12">
						<div class="widget-box">
							<div class="widget-title">
								<span class="icon">
									<i class="fa fa-align-justify"></i>									
								</span>
								<h5>录入</h5>
							</div>
							<div class="widget-content nopadding">
								<form id="inputForm"  method="post" class="form-horizontal">
									<div class="form-group">
										<label class="col-sm-3 col-md-3 col-lg-2 control-label">Odoo项目URL</label>
										<div class="col-sm-9 col-md-9 col-lg-10">
											<input name="odooUrl" type="text" class="form-control input-sm" />
										</div>
									</div>
									
									<div class="form-group">
										<label for="" class="col-sm-3 col-md-3 col-lg-2 control-label">Odoo账号及密码</label>
										<div class="col-sm-9 col-md-9 col-lg-10">
											<div class="row">
												<div class="col-md-6">
													<div class="input-icon icon-sm">
														<i class="fa fa-tint"></i>
														<input name="uname" type='text' class="form-control input-sm" value="admin" />
													</div>
												</div>
												<div class="col-md-6">
													<div class="input-icon on-right icon-sm">
														<input name="pwd" type='text' class="form-control input-sm" value="123456"/>
														<i class="fa fa-laptop"></i>
													</div>
												</div>
											</div>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 col-md-3 col-lg-2 control-label">数据库名</label>
										<div class="col-sm-9 col-md-9 col-lg-10">
											<input name="db" type="text" class="form-control input-sm" />
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 col-md-3 col-lg-2 control-label">action Id</label>
										<div class="col-sm-9 col-md-9 col-lg-10">
											<input name="action_id" type="text" class="form-control input-sm" />
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 col-md-3 col-lg-2 control-label">view id</label>
										<div class="col-sm-9 col-md-9 col-lg-10">
											<div class="row">
												<div class="col-md-4">
													<input type="text" name="search_id" class="form-control input-sm" placeholder="输入筛选器视图id" />
													<span class="help-block text-center">search view_id</span>
												</div>
												<div class="col-md-4">
													<input type="text" name="tree_id" class="form-control input-sm" placeholder="输入列表视图id"/>
													<span class="help-block text-center">tree view_id</span>
												</div>
												<div class="col-md-4">
													<input type="text" name="form_id" class="form-control input-sm" placeholder="输入表单视图id"/>
													<span class="help-block text-center">form view_id</span>
												</div>
											</div>
										</div>
									</div>
									
									<div class="form-actions">
										<button id="btn-commit" type="button" class="btn btn-primary btn-sm">生成</button> 或 <a id="btn-reset" class="text-danger" href="javascript:void(0);">重置</a>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		
		
		
		
		<div class="row">
			<div id="footer" class="col-xs-12">
					2012 - 2013 &copy; Unicorn Admin. Brought to you by <a href="https://wrapbootstrap.com/user/diablo9983">diablo9983</a>
			</div>
		</div>
	</div>
</body>
</html>
