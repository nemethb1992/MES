package phoenix.mes.content.utility;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;

/**
 * Servlet implementation class GetPdf
 */
@WebServlet("/GetPdf")
public class GetPdf extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String URI = request.getParameter("uri");
		
		byte[] pdfByte = Open(URI);
		
		ServletOutputStream out = response.getOutputStream();
		
		response.reset();
		response.setContentType("application/pdf");
		response.addHeader("Content-Disposition","inline;filename=temp.pdf");
		
		out.write(pdfByte, 0, pdfByte.length);
		out.flush();
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//file://abas.pmhu.local/pmk/dodrive/TDO/MA/MA-FE-409_(1.0).pdf
		doGet(request, response);
		
	}
	
	
	public byte[] Open(String URI) {
		
	    try {

	        SmbFile smbFile = new SmbFile(URI,NtlmPasswordAuthentication.ANONYMOUS);

	        SmbFileInputStream in = new SmbFileInputStream( smbFile );

			ByteArrayOutputStream out = new ByteArrayOutputStream((int)smbFile.length());
	        
	        byte[] b = new byte[8192];
	        int n, tot = 0;
	        while(( n = in.read( b )) > 0 ) {
	            out.write( b, 0, n );
	            tot += n;
	        }
	        in.close();
	        out.close();
	        return out.toByteArray();
	        
	    } catch (Exception e) {
	        return null;
	    }
	}
	
}
