package phoenix.mes.content.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class RenderView {
	
	public static String render(String url ,HttpServletRequest request ,HttpServletResponse response) throws ServletException, IOException
	{
        HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper(response) {
            private final StringWriter sw = new StringWriter();

            @Override
            public PrintWriter getWriter() throws IOException {
                return new PrintWriter(sw);
            }

            @Override
            public String toString() {
                return sw.toString();
            }
        };
		request.getRequestDispatcher(url).include(request, responseWrapper);
		
        return responseWrapper.toString();
	}
}
