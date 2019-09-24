package phoenix.mes.content.utility;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;

public class Pdf {
	protected byte[] file;
	
	public Pdf(String URI) {
		
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
	        this.file = out.toByteArray();
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public String getData() {
		
		return Base64.getEncoder().encodeToString(this.file);
	}
}
