<%@page contentType="text/html" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:pageTemplate title="Sales - UpMedia">
   <jsp:attribute name="bodyHeader">
   	<script>
	    $(function () {
	        var startDate = 'January 01, 2019 00:0:00';
	        var endDate = 'December 11, 2020 00:0:00';
	        $("#slider-range").slider({
	            range: true,
	            min: new Date(startDate).getTime() / 1000,
	            max: new Date(endDate).getTime() / 1000,
	            step: 86400,
	            values: [new Date(startDate).getTime() / 1000, new Date(endDate).getTime() / 1000],
	            slide: function (event, ui) {
	                $("#fromDate").text(new Date(ui.values[0] * 1000).toDateString());
	                $("#toDate").text(new Date(ui.values[1] * 1000).toDateString());
	
	            },
	            change: function (event, ui) {
	                revealView.setDateFilter(new $.ig.RVDateDashboardFilter($.ig.RVDateFilterType.CustomRange, new $.ig.RVDateRange(new Date(ui.values[0] * 1000), new Date(ui.values[1] * 1000))));
	            }
	        });
	        $("#fromDate").text(new Date(startDate).toDateString());
	        $("#toDate").text(new Date(endDate).toDateString());
	
	    });
	
	    var activeTeritorries = ["Americas", "APAC", "EMEA", "India", "Japan"];
	    var initialactiveTeritorries = activeTeritorries.slice();
	
	    function updateTerritoriesFilter() {
	        window.revealView.dashboard.filters.getByTitle("Territory").selectedValues = activeTeritorries;
	    }
	
	    function checkAllTerritories() {
	        document.getElementById("americasCheckbox").checked = true;
	        document.getElementById("apacCheckbox").checked = true;
	        document.getElementById("emeaCheckbox").checked = true;
	        document.getElementById("indiaCheckbox").checked = true;
	        document.getElementById("japanCheckbox").checked = true;
	    }
	
	    function flipTerritories(territory) {
	        if (activeTeritorries.indexOf(territory) > -1) {
	            activeTeritorries.splice(activeTeritorries.indexOf(territory), 1);
	        }
	        else {
	            activeTeritorries.push(territory);
	        }
	        if (activeTeritorries.length == 0) {
	            activeTeritorries = initialactiveTeritorries.slice();
	            checkAllTerritories();
	        }
	
	        updateTerritoriesFilter();
	    }
	</script>
	
	<div class="controls">
	    <div class="container">
	        <div id="fromDate" style="width:150px;"></div>
	        <div id="slider-range" style="width:300px"></div>
	        <div id="toDate" style="width:150px;"></div>
	    </div>
	
	    <label class="container">
	        Americas
	        <input type="checkbox" onchange="flipTerritories('Americas')" id="americasCheckbox" checked>
	        <span class="checkmark"></span>
	    </label>
	    <label class="container">
	        APAC
	        <input type="checkbox" onchange="flipTerritories('APAC')" id="apacCheckbox" checked>
	        <span class="checkmark"></span>
	    </label>
	    <label class="container">
	        EMEA
	        <input type="checkbox" onchange="flipTerritories('EMEA')" id="emeaCheckbox" checked>
	        <span class="checkmark"></span>
	    </label>
	    <label class="container">
	        India
	        <input type="checkbox" onchange="flipTerritories('India')" id="indiaCheckbox" checked>
	        <span class="checkmark"></span>
	    </label>
	    <label class="container">
	        Japan
	        <input type="checkbox" onchange="flipTerritories('Japan')" id="japanCheckbox" checked>
	        <span class="checkmark"></span>
	    </label>
	</div>
   </jsp:attribute>   
   
   <jsp:body>
	<script type="text/javascript">
        function runSample() {
            var dashboardId = "Sales";

            customLoadDashboard(dashboardId, function (dashboard) {

                var startDate = 'January 01, 2019 00:0:00';
                var endDate = 'December 11, 2020 00:0:00';
                $('#upmedia-loading').hide('slow', function () { $target.remove(); });

                //revealSettings.dashboard = dashboard;
                //revealSettings.setDateFilter(new $.ig.RVDateDashboardFilter($.ig.RVDateFilterType.CustomRange, new $.ig.RVDateRange(new Date(startDate), new Date(endDate))));
                window.revealView = new $.ig.RevealView("#revealView");
                window.revealView.showFilters = false;
                window.revealView.dashboard = dashboard;
            });
        }
    </script>
   </jsp:body>
</t:pageTemplate>