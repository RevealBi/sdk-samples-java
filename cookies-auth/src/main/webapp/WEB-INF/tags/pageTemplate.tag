<%@tag description="Page template" pageEncoding="UTF-8"%>
<%@attribute name="title"%>
<%@attribute name="bodyHeader" fragment="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>${title}</title>
    <link rel="stylesheet" href="css/site.css"/>

    <link rel="preconnect" href="https://code.jquery.com" />
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css" />
    <script type="text/javascript" src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.2.1.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>
    <script src="lib/dayjs/dayjs.min.js"></script>
    <script src="https://cdn.quilljs.com/1.3.6/quill.js"></script>
    <link href="https://cdn.quilljs.com/1.3.6/quill.snow.css" rel="stylesheet">

    <script src="https://ajax.googleapis.com/ajax/libs/webfont/1/webfont.js"></script>
    <script src="Reveal/infragistics.reveal.js?v=${revealSdkVersion}" charset="utf-8"></script>
</head>
<body>
    <script type="text/javascript">
    	$.ig.RevealSdkSettings.setBaseUrl('${pageContext.request.contextPath}/reveal-api/');
        $.ig.RevealSdkSettings.requestWithCredentialsFlag = true;
        function customLoadDashboard(dashboardId, dashboardLoadedFunc) {
            $.ig.RevealUtility.loadDashboard(dashboardId, function (dashboard) {
                $('#upmedia-loading').hide('slow', function () { $('#upmedia-loading').remove(); });
                dashboardLoadedFunc(dashboard);
            }, function (error) { console.log(error); });
        }
        
    </script>
    <header>
    </header>
    
    <jsp:invoke fragment="bodyHeader"/>
    
    <section>
        <div id="revealView">
            <div id="upmedia-loading"><span>Opening Dashboard...</span></div>
        </div>
    </section>
        
	<jsp:doBody/>
    
    <script type="text/javascript">
        WebFont.load({
            custom: {
                families: ['Roboto-Regular', 'Roboto-Bold', 'Roboto-Medium'] 
            },
            active: function () {
                runSample();
            },
        });

    </script>
</body>
</html>