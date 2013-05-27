package cc.pinecone.renren.devicecontroller.servlet;

import javax.servlet.http.HttpServletRequest;

public class DataTablesParamUtility {
	
	public static JQueryDataTableParamModel getParam(HttpServletRequest request)
	{
		if(request.getParameter("sEcho")!=null && request.getParameter("sEcho")!= "")
		{
			JQueryDataTableParamModel param = new JQueryDataTableParamModel();
			param.sEcho = request.getParameter("sEcho");
//			System.out.println("sEcho:"+param.sEcho);
			param.sSearch = request.getParameter("sSearch");
//			System.out.println("sSearch:"+param.sSearch);
			param.sColumns = request.getParameter("sColumns");
//			System.out.println("sColumns:"+param.sColumns);
			param.iDisplayStart = Integer.parseInt( request.getParameter("iDisplayStart") );
//			System.out.println("iDisplayStart:"+param.iDisplayStart);
			param.iDisplayLength = Integer.parseInt( request.getParameter("iDisplayLength") );
//			System.out.println("iDisplayLength:"+param.iDisplayLength);
			param.iColumns = Integer.parseInt( request.getParameter("iColumns") );
//			System.out.println("iColumns:"+param.iColumns);
			param.iSortingCols = Integer.parseInt( request.getParameter("iSortingCols") );
//			System.out.println("iSortingCols:"+param.iSortingCols);
			param.iSortColumnIndex = Integer.parseInt(request.getParameter("iSortCol_0"));
//			System.out.println("iSortColumnIndex:"+param.iSortColumnIndex);
			param.sSortDirection = request.getParameter("sSortDir_0");
//			System.out.println("sSortDirection:"+param.sSortDirection);
			return param;
		}else
			return null;
	}
}
