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
					   	<div class="col-sm-2 control-label">用户序号</div>
					   	<div class="col-sm-10">
					      <input type="text" class="form-control" v-model="ud.uid" placeholder="用户序号"/>
					    </div>
					</div>
					<div class="form-group">
					   	<div class="col-sm-2 control-label">真实名称</div>
					   	<div class="col-sm-10">
					      <input type="text" class="form-control" v-model="ud.realname" placeholder="真实名称"/>
					    </div>
					</div>
					<div class="form-group">
					   	<div class="col-sm-2 control-label">身份证号</div>
					   	<div class="col-sm-10">
					      <input type="text" class="form-control" v-model="ud.idnumber" placeholder="身份证号"/>
					    </div>
					</div>
					<div class="form-group">
					   	<div class="col-sm-2 control-label">性别</div>
					   	<div class="col-sm-10">
					      <input type="text" class="form-control" v-model="ud.sex" placeholder="性别"/>
					    </div>
					</div>
					<div class="form-group">
					   	<div class="col-sm-2 control-label">出生日期</div>
					   	<div class="col-sm-10">
					      <input type="text" class="form-control" v-model="ud.birthdate" placeholder="出生日期"/>
					    </div>
					</div>
					<div class="form-group">
					   	<div class="col-sm-2 control-label">审核状态</div>
					   	<div class="col-sm-10">
					      <input type="text" class="form-control" v-model="ud.flag" placeholder="审核状态"/>
					    </div>
					</div>
					<div class="hr-line-dashed"></div>
					<div class="form-group pull-right">
						<div class="col-sm-12">
							<button type="button" class="btn btn-primary" @click="update" >确定</button>
							<a href="index" class="btn btn-white">返回</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!-- 全局js -->
	<script src="${pageContext.request.contextPath}/libs/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/libs/bootstrap/js/bootstrap.min.js"></script>
	<!-- vue -->
	<script src="${pageContext.request.contextPath}/libs/vue.min.js"></script>
	<!--浮层-->
	<script src="${pageContext.request.contextPath}/libs/layer/layer.min.js"></script>
	<!-- fastjson 解析-->
	<script src="${pageContext.request.contextPath}/libs/FastJson-1.0.min.js"></script>
	<!-- 全局js设置 -->
	<script src="${pageContext.request.contextPath}/script/common.js"></script>
	<!-- 模块 -->
	<script src="${pageContext.request.contextPath}/script/modules/sys/config/form.js"></script>
</body>
</html>