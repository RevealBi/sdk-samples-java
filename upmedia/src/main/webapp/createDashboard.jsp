<%@page contentType="text/html" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:pageTemplate title="Create Dashboard - UpMedia">
   <jsp:body>
    <script type="text/javascript">
        function runSample() { 
            $('#upmedia-loading').remove();

            revealView = new $.ig.RevealView("#revealView");
            revealView.startInEditMode = true;
            revealView.dashboard = new $.ig.RVDashboard();
            revealView.onDataSourcesRequested = function (callback) {

            	var samplesDatasource = new $.ig.RVLocalFileDataSourceItem();
                samplesDatasource.uri = "local:/Samples.xlsx";
                var excelDsi = new $.ig.RVExcelDataSourceItem(samplesDatasource);
                excelDsi.title = "Sample Data";

                var northWindCustomersDatasource = new $.ig.RVWebResourceDataSource();
                northWindCustomersDatasource.id = "northwindCustomersDataSource";
                northWindCustomersDatasource.useAnonymousAuthentication = true;
                northWindCustomersDatasource.url = "http://northwind.servicestack.net/Customers.csv";
                northWindCustomersDatasource.title = "Northwind Customers";

                var northWindOrdersDatasource = new $.ig.RVWebResourceDataSource();
                northWindOrdersDatasource.id = "northwindOrdersDataSource";
                northWindOrdersDatasource.useAnonymousAuthentication = true;
                northWindOrdersDatasource.url = "http://northwind.servicestack.net/query/orders.json";
                northWindOrdersDatasource.title = "Northwind Orders";
                
                callback(new $.ig.RevealDataSources([northWindCustomersDatasource, northWindOrdersDatasource], [excelDsi], true));
            };
        }
    </script>
    </jsp:body>
</t:pageTemplate>
