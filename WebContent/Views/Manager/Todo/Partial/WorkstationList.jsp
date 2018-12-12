<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="phoenix.mes.content.AppBuild"%>
<%@page import="phoenix.mes.content.PostgreSql"%>
<%@page import="phoenix.mes.content.controller.User"%>
<%@page import="phoenix.mes.content.utility.OutputFormatter"%>
<%@page import="phoenix.mes.content.utility.OutputFormatter.DictionaryEntry"%>
<%
	OutputFormatter of = (OutputFormatter)session.getAttribute("OutputFormatter");
	PostgreSql pg = new PostgreSql(new AppBuild(request).isTest());
	User user = new User(request);
	String level = (String)request.getAttribute("level");
	String value = (String)request.getAttribute("value");
	String method = "";
	String field = "";
	String command;
	List<Map<String, String>> list = null;
	String groupSqlFieldName = "";
	String numberSqlFieldName = "";
	String nameSqlFieldName = "";
	
	String countCommand = "SELECT count(stations.pc) as count FROM group_relation "+
  	    		"LEFT JOIN stations on group_relation.workstation_group = stations.csoport "+
  	    		"LEFT JOIN users ON group_relation.user_id = users.id " + 
  	    		"WHERE users.username='"+user.getUsername()+"' "+
  	    		"GROUP BY stations.pc";
	if(level == "0" && pg.count(countCommand, "count") == 1) {
		level = "1";
	}
	switch (level) {
	case "1":
		method = "StationItemSelect(this,2)";
		field = "csoport";
		
		command = "SELECT stations.csoport FROM stations LEFT JOIN profitcenter ON stations.pc = profitcenter.id LEFT JOIN group_relation ON group_relation.workstation_group = stations.csoport WHERE "+(null != value ? "long = '"+value+"' AND":"")+" group_relation.user_id = "+user.getUserid()+" GROUP BY stations.csoport";
		list = pg.sqlQuery(command, field);
		pg.dbClose();
		break;
	case "2":
		method = "clickOnStation(this)";
		groupSqlFieldName = "csoport";
		numberSqlFieldName = "sorszam";
		nameSqlFieldName = "nev_" + of.getLocale().getLanguage();
		
		command = "select "+groupSqlFieldName+", "+numberSqlFieldName+", "+nameSqlFieldName+" from stations where csoport='" + request.getParameter("element")
		+ "' ORDER BY sorszam ASC";
		list = pg.sqlQuery(command, groupSqlFieldName, numberSqlFieldName, nameSqlFieldName);
		pg.dbClose();
		break;
	case "0":
	default:
		method = "StationItemSelect(this,1)";
		field = "long";
		command = "SELECT profitcenter.long FROM group_relation " + 
		"LEFT JOIN stations on group_relation.workstation_group = stations.csoport " + 
		"LEFT JOIN users ON group_relation.user_id = users.id " + 
		"LEFT JOIN profitcenter ON profitcenter.id = stations.pc " + 
		"WHERE users.username='"+user.getUsername()+"' GROUP BY profitcenter.id";
		list = pg.sqlQuery(command, field);
		pg.dbClose();
		break;
	}

	for (Map<String, String> row : (List<Map<String,String>>)list) {
%>
<div class='tmts_stationBtnDivCont col-12 px-0' 
	value='<%=("2".equals(level) ? row.get(groupSqlFieldName)+"!"+row.get(numberSqlFieldName)+"!"+row.get(nameSqlFieldName) : row.get(field))%>' OnClick='<%=method%>'>
	<input disabled class='si1' value='<%=("2".equals(level) ? row.get(nameSqlFieldName) : row.get(field))%>'>
</div>
<% } %>