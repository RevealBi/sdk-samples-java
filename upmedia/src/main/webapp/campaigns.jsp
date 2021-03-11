<%@page contentType="text/html" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:pageTemplate title="Campaigns - UpMedia">
   <jsp:body>
   <script type="text/javascript">
        function runSample() {
            var dashboardId = "Campaigns";
            customLoadDashboard(dashboardId, function (dashboard) {
                window.revealView = new $.ig.RevealView("#revealView");
                window.revealView.dashboard = dashboard;
            });
        }
    </script>
   </jsp:body>
</t:pageTemplate>