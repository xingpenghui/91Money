<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<title>91Money平台</title>
<link rel="shortcut icon" href="images/logo.ico">

<link href="${pageContext.request.contextPath}/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/font-awesome/css/font-awesome.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/libs/jquery.datetimepicker/jquery.datetimepicker.css" rel="stylesheet">

<link href="${pageContext.request.contextPath}/libs/zTree_v3/css/metroStyle/metroStyle.css" rel="stylesheet" >
    
<link href="${pageContext.request.contextPath}/css/animate.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">

<!-- 自定义css -->
<link href="${pageContext.request.contextPath}/css/my.css"
	rel="stylesheet">

</head>
<body class="my-content" >
	<div id="dtApp" v-cloak>
		<div class="panel panel-default">
			<div class="panel-heading">{{ title }}</div>
			<div class="panel-body">
				<form class="form-horizontal">
					<div class="form-group">
					   	<div class="col-sm-2 control-label">用户名</div>
					   	<div class="col-sm-10">
					   		<input type="text" class="form-control" v-model="user.username" placeholder="登录账号"/>
					    </div>
					</div>
					
					<div class="form-group">
					   	<div class="col-sm-2 control-label">密码</div>
					   	<div class="col-sm-10">
					      <input type="password" class="form-control" v-model="user.password" placeholder="密码"/>
					    </div>
					</div>
					<div class="form-group">
					   	<div class="col-sm-2 control-label">邮箱</div>
					   	<div class="col-sm-10">
					      <input type="text" class="form-control" v-model="user.email" placeholder="邮箱"/>
					    </div>
					</div>
					<div class="form-group">
					   	<div class="col-sm-2 control-label">手机号</div>
					   	<div class="col-sm-10">
					      <input type="text" class="form-control" v-model="user.mobile" placeholder="手机号"/>
					    </div>
					</div>
					<div class="form-group">
					   	<div class="col-sm-2 control-label">角色</div>
					   	<div class="col-sm-10">
						   	<label v-for="role in roleList" class="checkbox-inline">
							  <input type="checkbox" :value="role.roleId" v-model="user.roleIdList">{{role.roleName}}
							</label>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-2 control-label">状态</div> 
						<div class="col-sm-10">
							<label class="radio-inline">
							  <input type="radio" name="status" value="0" v-model="user.status"/> 禁用
							</label>
							<label class="radio-inline">
							  <input type="radio" name="status" value="1" v-model="user.status"/> 正常
							</label>
						</div>
					</div>
					<div class="hr-line-dashed"></div>
					<div class="form-group pull-right">
						<div class="col-sm-12">
							<button type="button" class="btn btn-primary" @click="saveOrUpdate" >确定</button>
							<a href="index" class="btn btn-white">返回</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	
	<!-- 选择菜单 -->
	<div id="menuLayer" style="display: none;padding:10px;">
		<ul id="menuTree" class="ztree"></ul>
	</div>

	<!-- 全局js -->
	<script src="${pageContext.request.contextPath}/libs/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/libs/bootstrap/js/bootstrap.min.js"></script>

	<!-- vue -->
	<script src="${pageContext.request.contextPath}/libs/vue.min.js"></script>
	
	<!--浮层-->
	<script src="${pageContext.request.contextPath}/libs/layer/layer.min.js"></script>

	<!--ztree-->
	<script src="${pageContext.request.contextPath}/libs/zTree_v3/js/jquery.ztree.all.min.js"></script>
	
	<!-- fastjson 解析-->
	<script src="${pageContext.request.contextPath}/libs/FastJson-1.0.min.js"></script>
	
	<!-- 全局js设置 -->
	<script src="${pageContext.request.contextPath}/script/common.js"></script>
	
	<!-- 模块 -->
	<script src="${pageContext.request.contextPath}/script/modules/sys/user/form.js"></script>

</body>
</html>