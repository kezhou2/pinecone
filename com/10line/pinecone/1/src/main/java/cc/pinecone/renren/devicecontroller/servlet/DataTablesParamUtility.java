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
			try{
				param.iDisplayStart = Integer.parseInt( request.getParameter("iDisplayStart") );
			}catch(NumberFormatException ex){
				ex.printStackTrace();
			}
//			System.out.println("iDisplayStart:"+param.iDisplayStart);
			try{
				param.iDisplayLength = Integer.parseInt( request.getParameter("iDisplayLength") );
			}catch(NumberFormatException ex){
				ex.printStackTrace();
			}
//			System.out.println("iDisplayLength:"+param.iDisplayLength);
			try{
				param.iColumns = Integer.parseInt( request.getParameter("iColumns") );
			}catch(NumberFormatException ex){
				ex.printStackTrace();
			}
//			System.out.println("iColumns:"+param.iColumns);
			try{
				param.iSortingCols = Integer.parseInt( request.getParameter("iSortingCols") );
			}catch(NumberFormatException ex){
				ex.printStackTrace();
			}
//			System.out.println("iSortingCols:"+param.iSortingCols);
			try{
				param.iSortColumnIndex = Integer.parseInt(request.getParameter("iSortCol_0"));
			}catch(NumberFormatException ex){
				ex.printStackTrace();
			}
//			System.out.println("iSortColumnIndex:"+param.iSortColumnIndex);
			try{
				param.sSortDirection = request.getParameter("sSortDir_0");
			}catch(NumberFormatException ex){
				ex.printStackTrace();
			}
//			System.out.println("sSortDirection:"+param.sSortDirection);
			return param;
		}else
			return null;
	}
}
