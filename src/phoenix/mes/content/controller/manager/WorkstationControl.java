package phoenix.mes.content.controller.manager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import phoenix.mes.abas.AbasConnection;
import phoenix.mes.abas.AbasObjectFactory;
import phoenix.mes.abas.WorkCenter;
import phoenix.mes.content.AppBuild;
import phoenix.mes.content.Log;
import phoenix.mes.content.PostgreSql;
import phoenix.mes.content.Log.FaliureType;
import phoenix.mes.content.controller.User;
import phoenix.mes.content.utility.OutputFormatter;
import phoenix.mes.content.utility.RenderView;

/**
 * Servlet implementation class StationControl
 */
public class WorkstationControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  		getServletContext().getRequestDispatcher("/Views/WelcomePage/WelcomePage.jsp").forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		User user = null;
		try {
		AppBuild ab = new AppBuild(request);
		user = new User(request);
		OutputFormatter of = (OutputFormatter)request.getSession().getAttribute("OutputFormatter");

		
		PostgreSql pg = new PostgreSql(new AppBuild(request).isTest());

		String level = request.getParameter("level");
		level = (level != null ? level : "0");
		String value = request.getParameter("element");
		String method = "";
		String field = "";
		String command;
		String group = "";
		String number = "";
		String name = "";
		
		List<String> pcList = new ArrayList<String>();		
		List<Map<String, String>> dataList = new ArrayList<>();
		List<Map<String, String>> finalDataList =  new ArrayList<>();
		
		for(String item : user.getStationAccess().getSuggestedGroups()) {
			
			String pcName = pg.sqlSingleQuery("SELECT "+(of.getLocale().getLanguage() == "de"? "long_de" : "long")+" FROM profitcenter LEFT JOIN stations ON stations.pc = profitcenter.id WHERE csoport='"+item+"' ORDER BY "+(of.getLocale().getLanguage() == "de"? "long_de" : "long")+" ASC", (of.getLocale().getLanguage() == "de"? "long_de" : "long"));
			
			if(!OutputFormatter.isExists(pcList,pcName))
			{
				pcList.add(pcName);
			}
		}
		pg.dbClose();
		if(level == "0" && pcList.size() <= 1) {
			level = "1";
		}
		java.util.Collections.sort(pcList);
		
		switch (level) {
		case "1":
			method = "StationItemSelect(this,2)";
			field = "csoport";
			for(String item : user.getStationAccess().getSuggestedGroups())
			{
				final Map<String, String> row = new HashMap<>((int)Math.ceil((1) / 0.75));
				String segedValue = pg.sqlSingleQuery("SELECT stations.csoport FROM stations LEFT JOIN profitcenter ON stations.pc = profitcenter.id WHERE "+(null != value ? (of.getLocale().getLanguage() == "de"? "long_de = '" : "long = '") +value+"' AND":"")+" csoport='"+item+"' ORDER BY stations.csoport", field);
				if(!"".equals(segedValue))
				{
					row.put("divValue", segedValue);
					dataList.add(row);
				}
			}
//			command = "SELECT stations.csoport FROM stations LEFT JOIN profitcenter ON stations.pc = profitcenter.id LEFT JOIN group_relation ON group_relation.workstation_group = stations.csoport WHERE "+(null != value ? "long = '"+value+"' AND":"")+" group_relation.user_id = "+user.getUserid()+" GROUP BY stations.csoport";
//			list = pg.sqlQuery(command, field);
			pg.dbClose();

			AbasConnection abasConnection = null;
			abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection(user.getUsername(),user.getPassword(), of.getLocale(), ab.isTest());	
			WorkCenter.Details workcenter = null;
			for (Map<String, String> row : (List<Map<String,String>>)dataList) {
				String idNo = row.get("divValue");
				workcenter = AbasObjectFactory.INSTANCE.createWorkCenter(idNo, abasConnection).getDetails(abasConnection);
				String GroupTitle = "("+row.get("divValue") +") "+ workcenter.getDescription();
				final Map<String, String> valueRow = new HashMap<>((int)Math.ceil((3) / 0.75));
				valueRow.put("divValue", row.get("divValue"));
				valueRow.put("method", method);
				valueRow.put("inputValue", GroupTitle);
				finalDataList.add(valueRow);
			}
			break;
		case "2":
			method = "clickOnStation(this)";
			group = "csoport";
			number = "sorszam";
			name = "nev_" + of.getLocale().getLanguage();
			
			command = "select "+group+", "+number+", "+name+" from stations where csoport='" + request.getParameter("element")
			+ "' ORDER BY sorszam ASC";
			dataList = pg.sqlQuery(command, group, number, name);
			pg.dbClose();

			
			
			for (Map<String, String> row : (List<Map<String,String>>)dataList) {
				final Map<String, String> valueRow = new HashMap<>((int)Math.ceil((3) / 0.75));
				valueRow.put("divValue", row.get(group)+"!"+row.get(number)+"!"+row.get(name));
				valueRow.put("method", method);
				valueRow.put("inputValue", row.get(name));
				finalDataList.add(valueRow);
			}
			break;
		case "0":
		default:
			method = "StationItemSelect(this,1)";
			field = "divValue";
			for(String item : pcList)
			{
				final Map<String, String> row = new HashMap<>((int)Math.ceil((1) / 0.75));
				if(!OutputFormatter.isExistsInMap(dataList, "divValue", item))
				{
					row.put("divValue", item);
					dataList.add(row);
				}
			}
			
			for (Map<String, String> row : (List<Map<String,String>>)dataList) {
				final Map<String, String> valueRow = new HashMap<>((int)Math.ceil((3) / 0.75));
				valueRow.put("divValue", row.get(field));
				valueRow.put("method", method);
				valueRow.put("inputValue", row.get(field));
				finalDataList.add(valueRow);
			}
			break;
		}
		
		request.setAttribute("Data", finalDataList);
		
		} catch (SQLException | LoginException e) {
    		try {
				String stackTrace = Log.getStackTraceString(e);
				new Log(request).logFaliure((user == null? "null" : user.getUsername()),FaliureType.WORKSTATION_LIST_LOAD,stackTrace, e.toString(),null);
			}catch(SQLException exc) {
			}
			return;
		}
		
		response.setContentType("text/plain"); 
		response.setCharacterEncoding("UTF-8"); 
		response.getWriter().write(RenderView.render("/Views/Manager/Todo/Partial/WorkstationList.jsp", request, response));
	}
}
