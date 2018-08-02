<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>91Money平台</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link href="../../css/common.css" rel="stylesheet" />
<link href="../../css/index.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../../script/jquery.min.js"></script>
<script type="text/javascript" src="../../script/common.js"></script>

</head>
<body>
<script src="../../script/head.js"></script>
<div class="new-announcement">
 <h1>${news.title}</h1>
<!--中间内容-->
<div class="main clearfix mrt30" data-target="sideMenu">
  <div class="wrap">
    <div class="page-left fn-left">
      <div class="mod-borrow">
        <div class="hd">
          <h2 class="pngbg"><i class="icon icon-ttyx"></i> <h1>${news.title?default("默认标题")}</h1></h2>
        </div>
        <div class="bd">
          <div class="des"><span class="fn-left">${news.lastTime?default("2018-08-01")}</span><a href="${news.sourceurl?default("")}" class="fn-right">${news.sourcename?default("")}&gt;&gt;</a></div>
          <div class="borrow-list">
            ${news.contentHtml?default("<h1>服务器宕机，请联系管理员！！！！</h1>")}
          </div>
          <div>
            <h2>关联新闻</h2>
              ${news.refHtml?default("")}
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
</div>
<!--网站底部-->
<script src="../../script/foot.js"></script>
</body>
</html>
