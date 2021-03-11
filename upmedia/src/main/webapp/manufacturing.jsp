<%@page contentType="text/html" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:pageTemplate title="Manufacturing - UpMedia">
   <jsp:body>
   <script type="text/javascript">
        function getCheckboxId(index) {
            return "vis" + index + "Checkbox";
        }

        function runSample() {
            var dashboardId = "Manufacturing";
            customLoadDashboard(dashboardId, function (dashboard) {
                window.revealView = new $.ig.RevealView("#revealView");
                window.revealView.dashboard = dashboard;
                window.revealView.onMaximizedVisualizationChanged = function () {
                    if (window.revealView.getMaximizedVisualization() == null) {
                        for (i = 0; i < 8; i++) {
                            var id = getCheckboxId(i);
                            document.getElementById(id).checked = false;
                        }
                    }
                    else {
                        var maximizedVisualization = window.revealView.getMaximizedVisualization();
                        var index = window.revealView.dashboard.visualizations.indexOf(maximizedVisualization);
                        var id = getCheckboxId(index);
                        document.getElementById(id).checked = true;
                    }
                }
            });
        }

        function maximizeVisualization(index) {
            var viz = window.revealView.dashboard.visualizations[index];
            window.revealView.maximizeVisualization(viz);
        }

        function onCheckChanged(index) {
            var id = getCheckboxId(index);
            if (document.getElementById(id).checked) {
                maximizeVisualization(index);
                for (i = 0; i < 8; i++) {
                    if (i == index) {
                        continue;
                    }
                    else {
                        var otherId = getCheckboxId(i);
                        document.getElementById(otherId).checked = false;
                    }
                }
            }
            else {
                window.revealView.minimizeVisualization();
            }
        }
    </script>   
    <div class="bottomnavbar">
        <label class="container">
            Overall Plant Production
            <input type="checkbox" onchange="onCheckChanged('0')" id="vis0Checkbox"/>
            <span class="checkmark"></span>
        </label>
        <label class="container">
            Units Lost
            <input type="checkbox" onchange="onCheckChanged('1')" id="vis1Checkbox"/>
            <span class="checkmark"></span>
        </label>
        <label class="container">
            Cost of Labor vs Revenue
            <input type="checkbox" onchange="onCheckChanged('2')" id="vis2Checkbox"/>
            <span class="checkmark"></span>
        </label>
        <label class="container">
            Units Lost
            <input type="checkbox" onchange="onCheckChanged('3')" id="vis3Checkbox"/>
            <span class="checkmark"></span>
        </label>
        <label class="container">
            Units Produced By Line
            <input type="checkbox" onchange="onCheckChanged('4')" id="vis4Checkbox"/>
            <span class="checkmark"></span>
        </label>
        <label class="container">
            Available by Function
            <input type="checkbox" onchange="onCheckChanged('5')" id="vis5Checkbox"/>
            <span class="checkmark"></span>
        </label>
        <label class="container">
            Orders In vs Orders Shipped
            <input type="checkbox" onchange="onCheckChanged('6')" id="vis6Checkbox"/>
            <span class="checkmark"></span>
        </label>
        <label class="container">
            Line 1 Efficiency
            <input type="checkbox" onchange="onCheckChanged('7')" id="vis7Checkbox"/>
            <span class="checkmark"></span>
        </label>
        <label class="container">
            Line 2 Efficiency
            <input type="checkbox" onchange="onCheckChanged('8')" id="vis8Checkbox"/>
            <span class="checkmark"></span>
        </label>
    </div>
    </jsp:body>
</t:pageTemplate>
