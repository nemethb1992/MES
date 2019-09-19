package phoenix.mes.content.utility;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Base64;

public class Pdf {
	protected byte[] file;
	
	public Pdf(String URI) {
		
		try {

		String pdfFilename = convertURI(URI);
		File f = new File(pdfFilename);
		
		BufferedInputStream is = new BufferedInputStream(new FileInputStream(f));
		ByteArrayOutputStream bos = new ByteArrayOutputStream((int)f.length());

		int ch;
		long actual=0;
		while((ch=is.read())!=-1){
		bos.write(ch);
		actual++;
		}
		bos.flush();
		bos.close();
		this.file=bos.toByteArray();
		}
		catch (Exception e) {
		e.printStackTrace();
		}
	}
	
	public String getData() {
		
		return Base64.getEncoder().encodeToString(this.file);
	}
	
	public String convertURI(String URI) {
		return URI.split("file:/")[1];
	}
}
