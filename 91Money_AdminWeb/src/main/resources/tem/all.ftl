<!DOCTYPE html>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>FreeMarker动态页面静态化技术</title>
</head>
<body>

<h1>下课</h1>
<h1>字符串：${name}</h1>
<h1>整型：版本：${version}</h1>
<hr/>
对象：
<div>
    <p>用户名：${user.username}</p>
    <p>密码：${user.password}</p>
    <p>邮箱：${user.email}</p>
    <p>手机号：${user.mobile}</p>
</div>
list集合：慢慢秋天来了
<ul>
    <#list weathers as w>
        <li>序号：${w_index},内容：${w}</li>
    </#list>
</ul>
分支：奇数：
<ul>
    <#list weathers as w>
        <#if w_index%2==1>
            <li>序号：${w_index},内容：${w}</li>
        </#if>

    </#list>
</ul>



map集合：
<ol>
    <#list zm?keys as k>
        <li>key:${k}---->value:${zm[k]}</li>
    </#list>
</ol>
<h1>时间：${mtime?string("yyyy-MM-dd")}</h1>
<h1>时间：${mtime?string("yyyy-MM-dd HH:mm:ss")}</h1>
<#--<h1>时间：${mtime?date('yyyy年MM月dd日')}</h1>-->

</body>
</html>