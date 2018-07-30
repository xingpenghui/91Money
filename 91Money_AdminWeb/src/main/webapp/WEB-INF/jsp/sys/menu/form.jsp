<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<title>91Money系统</title>
<link rel="shortcut icon" href="images/logo.ico">
<link href="${pageContext.request.contextPath}/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/font-awesome/css/font-awesome.min.css" rel="stylesheet">
<!-- bootstrap table -->
<link href="${pageContext.request.contextPath}/libs/bootstrap-table/bootstrap-table.min.css" rel="stylesheet" >
<link href="${pageContext.request.contextPath}/css/animate.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
<!-- ztree -->   
<link rel="stylesheet" href="${pageContext.request.contextPath}/libs/zTree_v3/css/metroStyle/metroStyle.css" />
<!-- 自定义css -->
<link href="${pageContext.request.contextPath}/css/my.css" rel="stylesheet">
</head>
<body class="my-content">

	<div id="dtApp" v-cloak>
	
		<div class="panel panel-default">
			<div class="panel-heading">{{title}}</div>
			<div class="panel-body">
				<form id="myform"  method="post" class="form-horizontal myform" novalidate>
					<div class="form-group">
						<label class="col-sm-4 control-label">类型</label>
						<label class="radio-inline">
						  	<input type="radio" name="type" v-model="menu.type" value="0"> 目录
						</label>
						<label class="radio-inline">
						  	<input type="radio" name="type" v-model="menu.type" value="1"> 菜单
						</label>
						<label class="radio-inline">
						  	<input type="radio" name="type" v-model="menu.type" value="2"> 按钮
						</label>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">菜单名称</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" name="name" v-model="menu.name">
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">上级菜单</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" name="parentMenu" placeholder="一级菜单"
							v-model="menu.parentMenu.name" @click="menuTree" readonly="readonly">
						</div>
					</div>
					<div v-show="menu.type == 1" class="form-group">
						<label class="col-sm-4 control-label">菜单URL</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" name="url" v-model="menu.url">
						</div>
					</div>
					<div v-show="menu.type == 1 || menu.type == 2" class="form-group">
						<label class="col-sm-4 control-label">授权标识</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" name="perms" v-model="menu.perms">
						</div>
					</div>
					<div v-show="menu.type == 0 || menu.type == 1" class="form-group">
						<label class="col-sm-4 control-label">排序号</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" name="orderNum" v-model="menu.orderNum">
						</div>
					</div>
					<div v-show="menu.type == 0 || menu.type == 1" class="form-group">
						<label class="col-sm-4 control-label">图标</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" name="icon" v-model="menu.icon">
						</div>
					</div>
					<div class="hr-line-dashed"></div>
					<div class="form-group pull-right">
						<div class="col-sm-12">
							<button class="btn btn-primary" type="button" @click="saveOrUpdate">保存内容</button>
							<a href="index" class="btn btn-white">取消</a>
						</div>
					</div>
				</form>
			
			</div>
		</div>
		
	</div>
	
	
	<div id="menuLayer" style="display: none; padding: 10px;">
		<ul id="menuTree" class="ztree"></ul>
	</div>
	
	<!-- 全局js -->
    <script src="${pageContext.request.contextPath}/libs/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/libs/bootstrap/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/libs/vue.min.js"></script>

	<!--浮层-->
	<script src="${pageContext.request.contextPath}/libs/layer/layer.min.js"></script>
    <!-- ztree -->
    <script src="${pageContext.request.contextPath}/libs/zTree_v3/js/jquery.ztree.all.min.js"></script>
    
    <!--  -->
    <script src="${pageContext.request.contextPath}/libs/FastJson-1.0.min.js"></script>
    
	<!-- 全局通用js配置 -->
	<script src="${pageContext.request.contextPath}/script/common.js"></script>
	
	<!-- 模块 -->
	<script src="${pageContext.request.contextPath}/script/modules/sys/menu/form.js"></script>
</body>
</html>