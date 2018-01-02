<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../../include/header.jsp"%>
<head>
<script type="text/javascript">
	$.namespace("zjsjndfx.detail");
	zjsjndfx.detail.legendName = '${legendName}';
	zjsjndfx.detail.zsdx = '${zsdx}';
	zjsjndfx.detail.tjwd = '${tjwd}';
	zjsjndfx.detail.year = '${year}';
	zjsjndfx.detail.qyIdList = '${qyIdList}';
	zjsjndfx.detail.qyNameList = '${qyNameList}';
	
	// 初始化Datagrid
	zjsjndfx.detail.initEventGrid = function() {
		$("#detailGrid").datagrid({
			url : "getDetail",
			queryParams : {
				legendName : zjsjndfx.detail.legendName,
				zsdx : zjsjndfx.detail.zsdx,
				tjwd : zjsjndfx.detail.tjwd,
				year : zjsjndfx.detail.year
			},
			method : "get",
			pageList:[15,30,45,60],
			pageSize : 15,
			toolbar : "#",
			fit : true,
			pagination : true,
			idField : "EVENT_ID", //支持分页选择记录
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
				field : "EVENT_NAME",
				title : "事件名称",
				align : "center",
				halign : "center",
				width : 40
			}, {
				field : "HAPPEN_DATE",
				title : "发生时间",
				align : "center",
				halign : "center",
				width : 30
			}, {
				field : "BDZSL",
				title : "变电站数量",
				align : "center",
				halign : "center",
				width : 15
			}, {
				field : "JCDSL",
				title : "监测点数量",
				align : "center",
				halign : "center",
				width : 15
			}, {
				field : "GZXX",
				title : "故障信息",
				align : "center",
				halign : "center",
				width : 15
			}, {
				field : "BZ",
				title : "备注",
				align : "center",
				halign : "center",
				width : 15
			}, {
				field : "VLTYNAME",
				title : "电压等级",
				align : "center",
				halign : "center",
				width : 15
			}, {
				field : "GZLXNAME",
				title : "故障类型",
				align : "center",
				halign : "center",
				width : 20
			}, {
				field : "ORG_NAME",
				title : "所属单位",
				align : "center",
				halign : "center",
				width : 30,
				formatter : function(value) {
					if (value) {
	 					var result = zjsjndfx.detail.formatOrg(value);
	 					return result;
	 				};
	 			}
			}] ],
			onDblClickRow : function(index,row) {
			}
		});
	};

	//所属单位显示
	zjsjndfx.detail.formatOrg = function(value){
		
		var qyIdList = eval(zjsjndfx.detail.qyIdList);
		var qyNameList = eval(zjsjndfx.detail.qyNameList);
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
		zjsjndfx.detail.initEventGrid();
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