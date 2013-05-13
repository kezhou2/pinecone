package org.apache.jsp.WEB_002dINF.views;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class login_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n");
      out.write("<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\r\n");
      out.write("<link rel=\"stylesheet\"  href=\"css/style.css\">\r\n");
      out.write("<title>login</title>\r\n");
      out.write("<style>\r\n");
      out.write("body {\r\n");
      out.write(" \tmargin-top: 0;\r\n");
      out.write("}\r\n");
      out.write("</style>\r\n");
      out.write("</head>\r\n");
      out.write("\r\n");
      out.write("<body>\r\n");
      out.write("<div class=\"login_mian\">\r\n");
      out.write("<div class=\"login_logo\"> <a href=\"#\"><img src=\"images/logon.jpg\" width=\"45\" /></a> </div>\r\n");
      out.write("<div class=\"login_box\">\r\n");
      out.write("\t<form action=\"devices.html\" method=\"post\">\r\n");
      out.write("        <ul class=\"login_text\">\r\n");
      out.write("            <li> <input type=\"text\" name=\"username\"/> </li>\r\n");
      out.write("            <li> <input type=\"password\" name=\"password\"/> </li>\r\n");
      out.write("            <li> <input style=\" width: 13px; float: left;\" type=\"checkbox\"  /> <p>è®°ä½å¯ç ï¼</p> </li>\r\n");
      out.write("            <!-- <li><a id=\"login\" href=\"devices.html\"></a><a id=\"login_on\" href=\"#\"></a> </li> -->\r\n");
      out.write("            <li><a id=\"login\" href=\"#\"><input type=\"submit\" value=\"\"/></a><a id=\"login_on\" href=\"#\"></a> </li>\r\n");
      out.write("        </ul>\r\n");
      out.write("    </form>\r\n");
      out.write("</div>\r\n");
      out.write("</div>\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
