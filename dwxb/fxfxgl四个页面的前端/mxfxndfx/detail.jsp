<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../../include/header.jsp"%>
<head>
<script type="text/javascript">
	$.namespace("mxfxndfx.detail");
	mxfxndfx.detail.legendName = '${legendName}';
	mxfxndfx.detail.zsdx = '${zsdx}';
	mxfxndfx.detail.tjwd = '${tjwd}';
	mxfxndfx.detail.year = '${year}';
	mxfxndfx.detail.qyIdList = '${qyIdList}';
	mxfxndfx.detail.qyNameList = '${qyNameList}';
	
	// 初始化Datagrid
	mxfxndfx.detail.initBusGrid = function() {
		$("#detailGrid").datagrid({
			url : "getDetail",
			queryParams : {
				legendName : mxfxndfx.detail.legendName,
				zsdx : mxfxndfx.detail.zsdx,
				tjwd : mxfxndfx.detail.tjwd,
				year : mxfxndfx.detail.year
			},
			method : "get",
			pageList:[15,30,45,60],
			pageSize : 15,
			toolbar : "#",
			fit : true,
			pagination : true,
			idField : "ID", //支持分页选择记录
			pagePosition : "bottom",
			rownumbers : true,
			border : false,
			singleSelect : false,
			striped : true,
			fitColumns : true,
			selectOnCheck : true,
			checkOnSelect : true,
			scrollbarSize :0, //隐藏滚动条
			columns : [ [ {
				field : "EVENT_ID",
				title : "事件ID",
				align : "center",
				halign : "center",
				width : 40
			}, {
				field : "BUSID",
				title : "母线ID",
				align : "center",
				halign : "center",
				width : 30
			}, {
				field : "HAPPEN_DATE",
				title : "发生时间",
				align : "center",
				halign : "center",
				width : 30
			}, {
				field : "ORGNO",
				title : "所属单位",
				align : "center",
				halign : "center",
				width : 30,
				formatter : function(value) {
					if (value) {
	 					var result = mxfxndfx.detail.formatOrg(value);
	 					return result;
	 				};
	 			}
			},{
				field : "VLTYNAME",
				title : "电压等级",
				align : "center",
				halign : "center",
				width : 15
			}, {
				field : "FZFWNAME",
				title : "幅值范围",
				align : "center",
				halign : "center",
				width : 20
			}] ],
			onDblClickRow : function(index,row) {
			}
		});
	};

	//所属单位显示
	mxfxndfx.detail.formatOrg = function(value){
		
		var qyIdList = eval(mxfxndfx.detail.qyIdList);
		var qyNameList = eval(mxfxndfx.detail.qyNameList);
		//console.log("jj",qyIdList,qyNameList);
		for(var index in qyIdList){
			if(value==qyIdList[index]){
				return qyNameList[index];
			}
		}
		return value;
	}
	
	// 页面加载完成后执行
	$(document).ready(function() {
		mxfxndfx.detail.initBusGrid();
	});
</script>
</head>

<div class="easyui-layout" fit="true" border="true">
	<div region="center" border="false" style="padding: 10px; padding-bottom: 0px;">
		<div class="easyui-layout" fit="true" border="true">
<!-- 			<div region="north" border="false" style="padding-bottom: 10px;"> -->
<!-- 				<div class="main-toolbar"> -->
<!-- 					<div class="searchBar"> -->
<!-- 						<table style="border-spacing: 0;"> -->
							
<!-- 						</table> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 			</div> -->
			<div region="center" border="false" >
				<table id="detailGrid"></table>
			</div>
		</div>
	</div>
</div>