<%@page import="io.revealbi.samples.mybi.jsp.DataSourcesHelper"%>
<%@page contentType="text/html" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title><%=request.getParameter("id") == null ? "New Dashboard" : request.getParameter("id")%></title>
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
        window.IGAppBase = '${pageContext.request.contextPath}/reveal-api/';

        function customLoadDashboard(dashboardId, dashboardLoadedFunc) {
            $.ig.RevealUtility.loadDashboard(dashboardId, function (dashboard) {
                $('#dashboard-loading').hide('slow', function () { $('#dashboard-loading').remove(); });
                dashboardLoadedFunc(dashboard);
            }, function (error) { console.log(error); });
        }
        
    </script>
    <header>
        <nav>
            <div class="nav-left">
                <a href="${pageContext.request.contextPath}" id="logo"><img src="images/home-128.png" /></a>
            </div>
            <div class="nav-right">
                <a href="#" id="settings"><img src="images/gear-icon.svg" alt="Settings Icon" /></a>
                <a href="#" id="settings"><img src="images/person-icon.svg" alt="User Icon" /></a>
            </div>
        </nav>
    </header>
    
    <section>
        <div id="revealView">
            <div id="dashboard-loading"><span>Opening Dashboard...</span></div>
        </div>
    </section>
        
	    <script type="text/javascript">
        function openDashboard() {                         
            var dashboardId = <%=request.getParameter("id") == null ? null : "'" + request.getParameter("id") + "'"%>;
            if (dashboardId != null) {
                customLoadDashboard(dashboardId, function (dashboard) {
                    var revealView = new $.ig.RevealView("#revealView");
                    revealView.dashboard = dashboard;
                    setupView(revealView);
                });            	
            } else {            	
                $('#dashboard-loading').remove();

                var revealView = new $.ig.RevealView("#revealView");
                revealView.startInEditMode = true;
                revealView.dashboard = new $.ig.RVDashboard();
                setupView(revealView);
            }
        }
        
        function setupView(revealView) {
        	window.revealView = revealView;
        	
            revealView.onDataSourcesRequested = function (callback) {
            	var dataSources = [];
            	var dataSourceItems = [];
            	
            	var samplesDatasource = new $.ig.RVLocalFileDataSourceItem();
                samplesDatasource.uri = "local:/Samples.xlsx";
                
                var excelDsi = new $.ig.RVExcelDataSourceItem(samplesDatasource);
                excelDsi.title = "Sample Data";
                dataSourceItems.push(excelDsi);
                
                <%= DataSourcesHelper.getDataSourcesScript() %>
                
                callback(new $.ig.RevealDataSources(dataSources, dataSourceItems, true));
            };
            
            revealView.onSave = function(rv, saveEvent) {
            	if (saveEvent.saveAs || saveEvent.name == "New Dashboard") {
            		var name = prompt("Dashboard name: ", saveEvent.name);
            		if (name != null) {
            			//workaround to change the title in the UI
            			saveEvent.serializeWithNewName(name, function(blob) {}, function(error){});
            			
            			saveEvent.name = name;
            			saveEvent._dashboardId = name;
            		}
            	}
    			saveEvent.saveFinished();
            };
        }
    </script>
    
    <script type="text/javascript">
        WebFont.load({
            custom: {
                families: ['Roboto-Regular', 'Roboto-Bold', 'Roboto-Medium'] 
            },
            active: function () {
                openDashboard();
            },
        });

    </script>
</body>
</html>