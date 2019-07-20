<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %>
<html>
<head>
	<title>insert URL</title>
</head>
<body>
<h1>
	Shortening Service
</h1>
<form method="post" id="mainForm" name="mainForm" action="/checkUrl.do">
<div> <B>insert URL :</B> <input type="text" id="url" name="url" value="${originalUrl}" style="width:400px;"/> <button onclick="checkUrl();"  >입력</button></p>  
      <B> shortUrl :</B> ${shortUrl}</a>
</div>
</form>
</body>
	<script type="text/javascript">
		function checkUrl(){
			$("mainForm").submit();
		}
		
	</script>
</html>
