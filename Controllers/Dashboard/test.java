package Dashboard;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Servlet implementation class test
 */
public class test extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public test() {
        super();
        // TODO Auto-generated constructor stub
    }
    public void test2()
    {
    	 JSONParser parser = new JSONParser();
    	    try
    	    {
    	        Object object = parser.parse(new FileReader("MES\\Public\\json\\langsrc.json"));
    	        
    	        JSONObject jsonObject = (JSONObject)object;
    	        
    	        String name = (String) jsonObject.get("en");
    	        
    	        JSONArray elements = (JSONArray)jsonObject.get("en");
    	        
    	        System.out.println("Name: " + name);
    	        System.out.println("Countries:");
    	        for(Object items : elements)
    	        {
    	            System.out.println("\t"+items.toString());
    	        }
    	    }
    	    catch(FileNotFoundException fe)
    	    {
    	        fe.printStackTrace();
    	    }
    	    catch(Exception e)
    	    {
    	        e.printStackTrace();
    	    }
    }
   
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		test2();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
