<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Home</title>
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/resources/app/css/styles.css">
</head>
<body>
    <div id="wrapper">
		<form:form
		  action="${pageContext.request.contextPath}/article/upload" method="post"
		  modelAttribute="fileUploadForm" enctype="multipart/form-data"> <!-- (1) (2) -->
		  <table>
		    <tr>
		      <th width="35%">File to upload</th>
		      <td width="65%">
		        <form:input type="file" path="file" /> <!-- (3) -->
		        <form:errors path="file" />
		      </td>
		    </tr>
		    <tr>
		      <th width="35%">Description</th>
		      <td width="65%">
		        <form:input path="description" />
		        <form:errors  path="description" />
		      </td>
		    </tr>
		    <tr>
		      <td>&nbsp;</td>
		      <td><form:button>Upload</form:button></td>
		    </tr>
		  </table>
		</form:form>
    </div>
</body>
</html>
