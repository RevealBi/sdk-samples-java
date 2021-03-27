<%@page contentType="text/html" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>MyBI</title>
    <link rel="stylesheet" href="css/site.css"/>
    <link rel="preconnect" href="https://code.jquery.com" />

    <link rel="stylesheet" href="https://code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css" />
    <script type="text/javascript" src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.2.1.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>

    <script src="https://ajax.googleapis.com/ajax/libs/webfont/1/webfont.js"></script>
    <script src="Reveal/infragistics.reveal.js?v=${revealSdkVersion}" charset="utf-8"></script>
</head>
<body>
<script type="text/javascript">
	$.getJSON("${pageContext.request.contextPath}/api/dashboards/", function(data) {
		var items = [];
		$.each( data, function(index, item) {	
			items.push( "<li><a href=\"dashboard.jsp?id=" + item.id + "\">" + item.id + "</a></li>" );
		});
		 
		$( "<ul/>", {
			"class": "my-new-list",
			"html": items.join( "" )
		}).appendTo( "body" );
	});
</script>
<p><a href="dashboard.jsp">New dashboard</a>
</body>
</html>