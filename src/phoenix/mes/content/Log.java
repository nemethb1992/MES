package phoenix.mes.content;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import phoenix.mes.abas.AbasFunctionException;
import phoenix.mes.content.controller.OperatingWorkstation;
import phoenix.mes.content.controller.User;
import phoenix.mes.content.controller.Workstation;


public class Log {
	
	
	public static String getErrorText(int errorCode) {
		switch(errorCode) {
		  case 1:
		  case 2:
		  case 3:
		  case 4:
			  return "Érvénytelen bemenetek!";
		  case 5:
			  return "A művelet az Abas-ban meghiúsult!";
		  case 6:
		  case 7:
			  return "Hibás bemeneti érték! Kérjük, próbálja újra.";
		  case 8:
			  return "Zárolás az adatbázisban! Kérjük, próbálja újra.";
		  case 9:
			  return "Általános hiba! Kérjük, próbálja újra.";
		  case 10:
			  return "Mélyebb szintű hiba lépett fel az Abas-ban a funkció végrehajtása során. Kérjük, próbálja újra.";
		  default:
			  return "Ismeretlen hiba lépett fel a művelet végrehajtása során!";
		}
	}
	
	public enum FaliureType{
		APPLICATION(0,1,"Alkalmazás hiba"),
		LOGIN(0,2,"Bejelentkezési hiba"),
		TASK_SUBMIT(1,3,"Lejelentési hiba"),
		TASK_LIST_LOAD(2,4,"Feladatlista betöltési hiba"),
		TASK_LIST_NAVIGATION(2,5,"Feladatlista navigálási hiba"),
		TASK_DATA_LOAD(1,6,"Feladat betöltési hiba"),
		TASK_INTERRUP(1,7,"Feladat megszakítási hiba"),
		TASK_REFRESH(1,8,"Feladat frissítési hiba"),
		TASK_RESUME(1,9,"Feladat folytatási hiba"),
		TASK_SUSPEND(1,10,"Feladat felfüggesztési hiba"),
		WORKSTATION_LIST_LOAD(2,11,"Állomáslista betöltési hiba");
		
		private final int region;
		private final int type;
		private final String title;
		
		FaliureType(int region, int type, String title){
			this.region = region;
			this.type = type;
			this.title = title;
		}
		 
		public int getRegion() {
			return this.region;
		}
		 
		public int getNo() {
			return this.type;
		}
		 
		public String getTitle() {
			return this.title;
		}

	}
	
	HttpServletRequest request;
	protected Workstation ws;
	protected User user;
	
	public Log(HttpServletRequest request) throws SQLException
	{
		this.request = request;
		user = new User(request);
		ws = new OperatingWorkstation(request);
	}
	
	public void insert(String workSplitNo,String text, String... username) throws SQLException
	{
		String date = new SimpleDateFormat("yyyy.MM.dd hh.mm").format(Calendar.getInstance().getTime());
		PostgreSql pg = new PostgreSql(new AppBuild(request).isTest());
		String command = "INSERT INTO log (user_id,ws_group,ws_no,date,type,text,workslipno) VALUES("+(username.length>0 ? username[0] : user.getUserid())+",'"+ws.getGroup()+"',"+ws.getNumber()+",'"+date+"',0,'"+text+"', '"+workSplitNo+"')";
		pg.sqlUpdate(command);
		pg.dbClose();

	}
	public String get(String workSplitNo) throws SQLException
	{
		PostgreSql pg = new PostgreSql(new AppBuild(request).isTest());
		String result = pg.sqlSingleQuery("SELECT text FROM log WHERE workslipno='"+workSplitNo+"' ORDER BY date DESC","text");
		pg.dbClose();
		return result;
	}
	public boolean logFaliure(FaliureType faliureType, String description, String... workstation) {
		try {
			String date = new SimpleDateFormat("yyyy.MM.dd hh.mm").format(Calendar.getInstance().getTime());
			PostgreSql pg = new PostgreSql(new AppBuild(request).isTest());
			String command = "INSERT INTO application_log (workstation,title,type_id,description,date,region) VALUES('"+(workstation.length>0 ? workstation[0] : "")+"','"+getFaliureTitle(faliureType)+"',"+getFaliureNo(faliureType)+",'"+description+"','"+date+"',"+getFaliureRegion(faliureType)+")";
			pg.sqlUpdate(command);
			pg.dbClose();
		}catch(Exception e) {
			
		}
		return true;
	}
	
	public int getFaliureRegion(FaliureType faliureType)
	{
		return faliureType.getRegion();
	}
	
	public int getFaliureNo(FaliureType faliureType)
	{
		return faliureType.getNo();
	}
	
	public String getFaliureTitle(FaliureType faliureType)
	{
		return faliureType.getTitle();
	}
}
