$(function() {

	//===== Datatables =====//

	oTable = $('#data-table').dataTable({
		"bJQueryUI": false,
		"bAutoWidth": true,
		"bPaginate": false,
		"bFilter": false,
		"sScrollY": "450",
		"bSort": false,
		"bScrollCollapse": true,
		"sPaginationType": "full_numbers",
		"fnInitComplete" : function(oSettings, json) {
			// Find the wrapper and hide all thead
			$('#data-table').parents('.dataTables_wrapper').first().find("div[class='dataTables_scrollHead']").remove();
		}
    });

});
