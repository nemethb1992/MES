package phoenix.mes.content.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import phoenix.mes.content.AppBuild;
import phoenix.mes.content.PostgreSql;
import phoenix.mes.content.utility.OutputFormatter;

public class StationAccess {

	HttpServletRequest request;
	protected Integer userid;
	protected List<Map<String, String>> pc_access;
	protected List<Map<String, String>> section_access;
	protected List<Map<String, String>> group_access;
	protected List<String> suggestedGroups;
	
	StationAccess(HttpServletRequest request, Integer userid) throws SQLException{
		this.request = request;
		this.userid = userid;
		setWorkstationAccess();
		this.suggestedGroups = groupCollector();
	}
	
    public List<Map<String, String>> getSignedPc()
    {
    	return pc_access;
    }  
    
    public List<Map<String, String>> getSignedSection()
    {
    	return section_access;
    }
    
    public List<Map<String, String>> getSignedGroup()
    {
    	return group_access;
    }
    
    public List<String> getSuggestedGroups() {
    	return suggestedGroups;
    }
    
    protected List<Map<String, String>> setWorkstationAccess() throws SQLException{
		PostgreSql pg = new PostgreSql(new AppBuild(request).isTest());
		if(sqlBindEngine("select count(pc_id) as count from profitcenter_relation where user_id = "+userid+"", "count")) {
			pc_access = pg.sqlQuery("select pc_id from profitcenter_relation where user_id = "+userid+"", "pc_id");		
		}		
		if(sqlBindEngine("select count(section_id) as count from section_relation where user_id = "+userid+"", "count")) {
			section_access = pg.sqlQuery("select section_id from section_relation where user_id = "+userid+"", "section_id");		
		}		
		if(sqlBindEngine("select count(workstation_group) as count from group_relation where user_id = "+userid+"", "count")) {
			group_access = pg.sqlQuery("select workstation_group from group_relation where user_id = "+userid+"", "workstation_group");
		}
		pg.dbClose();
		
    	return null;
    }
    
    protected List<String> groupCollector() throws SQLException
    {
		PostgreSql pg = new PostgreSql(new AppBuild(request).isTest());
    	List<String> list = new ArrayList<String>();
		if(pc_access != null)
		for (Map<String, String> row : pc_access) {
				List<Map<String, String>> result = pg.sqlQuery("SELECT csoport FROM stations WHERE pc = "+row.get("pc_id")+"","csoport");

				for (Map<String, String> groupRow : result) {
					if(!OutputFormatter.isExists(list,groupRow.get("csoport"))){
						list.add(groupRow.get("csoport"));
					}
				}
		}
		if(section_access != null)
		for (Map<String, String> row : section_access) {
			List<Map<String, String>> result = pg.sqlQuery("SELECT csoport FROM stations WHERE section = "+row.get("section_id")+"","csoport");

			for (Map<String, String> groupRow : result) {

				if(!OutputFormatter.isExists(list,groupRow.get("csoport"))){
					list.add(groupRow.get("csoport"));
				}
			}
		}
		if(group_access != null)
		for (Map<String, String> row : group_access) {
			List<Map<String, String>> result = pg.sqlQuery("SELECT csoport FROM stations WHERE csoport = '"+row.get("workstation_group")+"'","csoport");

			for (Map<String, String> groupRow : result) {
				if(!OutputFormatter.isExists(list,groupRow.get("csoport"))){
					list.add(groupRow.get("csoport"));
				}
			}
		}
		pg.dbClose();
    	return list;
    }
    

    
    protected boolean sqlBindEngine(String command, String field) throws SQLException
    {
		PostgreSql pg = new PostgreSql(new AppBuild(request).isTest());
		String result = pg.sqlSingleQuery(command,field);
		pg.dbClose();
		if("".equals(result) || "0".equals(result)){
			return false;
		}
		return true;
    }
}
