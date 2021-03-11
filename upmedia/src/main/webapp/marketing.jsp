<%@page contentType="text/html" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:pageTemplate title="Marketing - UpMedia">
   <jsp:body>
   <script type="text/javascript">
        function runSample() {
            var dashboardId = "Marketing";
            customLoadDashboard(dashboardId, function (dashboard) {
                window.revealView = new $.ig.RevealView("#revealView");
                window.revealView.dashboard = dashboard;

                revealView.onVisualizationLinkingDashboard = function (title, url, callback) {
                    //provide the dashboard id of the target of the link
                    callback("Campaigns");
                };
            });
        }
    </script>
   </jsp:body>
</t:pageTemplate>