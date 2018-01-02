<%@page import="java.util.Calendar"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<title>区域分线分析</title>
<%@ include file="../../include/common.jsp"%>
<link href="${ctx}/static/css/main.css" rel="stylesheet" type="text/css" />
<%-- <script type="text/javascript" src="${ctx}/static/jquery-1.8.3.min.js"></script> --%>
<script type="text/javascript" src="../../static/echarts.min.js"></script>
<%
	Calendar now = Calendar.getInstance();//得到当前系统时间 
	int thisYear = now.get(Calendar.YEAR);
%>
<script type="text/javascript">
 	$.namespace("qyfxfx.main");

 	//需要展示的区域名称列表(按顺序展示)
 	qyfxfx.main.qyNameList = [];
 	//区域名称列表对应的区域ID列表
 	qyfxfx.main.qyIDList = [];
 	
	// 页面加载完成后执行
	$(document).ready(function() {
		qyfxfx.main.yearBoxClick();
		qyfxfx.main.chartsSearch();
	});
	
	//查询
	qyfxfx.main.chartsSearch = function(){
		var theYear = parseInt($(".yearTip").html());
		$.ajax({
			url : 'search',
			type:'POST',
			data:{
				'year':theYear
			},
			success:function(result){
				var json = JSON.parse(result);
				if(json["isSuccess"]==false){
					$.messager.alert("提示","查询失败！");
				}else{
					qyfxfx.main.dataManager(json);
				}
			}
		});
		
	}
	
	//展示数据处理
	qyfxfx.main.dataManager = function(data){  
		//console.log("查询结果:",data);
		//获取常量定义的区域id及名称
		qyfxfx.main.qyNameList = data["qyList"]["qyName"];
		qyfxfx.main.qyIDList = data["qyList"]["qyId"];
		
		//按供电区域统计暂降事件影响母线数量
		var charts1Data = data["charts1"];
		var charts1ValueList = new Array();
		for(var index in qyfxfx.main.qyIDList){
			var quID = qyfxfx.main.qyIDList[index];
			for(var i in charts1Data){
				if(quID == charts1Data[i]["TJWD"]){
					charts1ValueList.push(charts1Data[i]["BUSSL"]==null?0:charts1Data[i]["BUSSL"]);
				}
			}
			if(charts1ValueList[index]==undefined){
				charts1ValueList.push(0);
			}
		}
		qyfxfx.main.Charts1(qyfxfx.main.qyNameList,charts1ValueList);
		//按供电区域统计暂降事件影响用户数量
		var charts2Data = data["charts2"];
		var charts2ValueList = new Array();
		for(var index in qyfxfx.main.qyIDList){
			var quID = qyfxfx.main.qyIDList[index];
			for(var i in charts2Data){
				if(quID == charts2Data[i]["TJWD"]){
					charts2ValueList.push(charts2Data[i]["CONSSL"]==null?0:charts2Data[i]["CONSSL"]);
				}
			}
			if(charts2ValueList[index]==undefined){
				charts2ValueList.push(0);
			}
		}
		qyfxfx.main.Charts2(qyfxfx.main.qyNameList,charts2ValueList);
		//按月份统计暂降事件影响用户数量
		var charts3Data = data["charts3"];
		var charts3ValueList = new Array();
		for(var month=1;month<=12;month++){
			for(var i in charts3Data){
				if(month == charts3Data[i]["TJWD"]){
					charts3ValueList.push(charts3Data[i]["CONSSL"]==null?0:charts3Data[i]["CONSSL"]);
				}
			}
			if(charts3ValueList[month-1]==undefined){
				charts3ValueList.push(0);
			}
		}
		qyfxfx.main.Charts3(charts3ValueList);
		//按供电区域统计重复影响母线条数
		var charts4Data = data["charts4"];
		var charts4ValueList = new Array();
		for(var index in qyfxfx.main.qyIDList){
			var quID = qyfxfx.main.qyIDList[index];
			for(var i in charts4Data){
				if(quID == charts4Data[i]["TJWD"]){
					charts4ValueList.push(charts4Data[i]["BUSSL"]==null?0:charts4Data[i]["BUSSL"]);
				}
			}
			if(charts4ValueList[index]==undefined){
				charts4ValueList.push(0);
			}
		}
		qyfxfx.main.Charts4(qyfxfx.main.qyNameList,charts4ValueList);
	}
	
	//年度选择监听
	qyfxfx.main.yearBoxClick = function(){
		$(".upYearTip").click(function(){
			var theYear = parseInt($(".yearTip").html());
			$(".yearTip").html(theYear+1);
			qyfxfx.main.chartsSearch();
		});
		$(".downYearTip").click(function(){
			var theYear = parseInt($(".yearTip").html());
			$(".yearTip").html(theYear-1);
			qyfxfx.main.chartsSearch();
		});
	}
	
	//按供电区域统计暂降事件影响母线数量
	qyfxfx.main.Charts1 = function(qyNameList,chartsValueList){
		var myChart = echarts.init(document.getElementById('eneratrix'));
		var datas = chartsValueList;
		var option = {
			backgroundColor: 'white',
            color: ['#3398DB'],
            tooltip: {},
            xAxis: {
            	data : qyNameList,
            	axisLabel : {
                    fontSize : '16'				//x轴字体的大小
                },
            },
            yAxis: {
            	name: '单位:条',
            	nameGap: '20',
            	nameTextStyle: {
		        	fontSize: '18'				//y轴名称字体大小
		        },
		        axisLabel : {
                    fontSize : '16'				//y轴字体的大小
                },
            },
            grid:{
            	left:50,
            	right:20
            },
            series: [{
                type: 'bar',
                data: datas,
                barWidth : '55%'
            }]
		};
		myChart.setOption(option);
		myChart.on('click', function (params) {
			qyfxfx.main.dialogFuc(params.name,"yxmx","org");
		});
	}
	
	//按供电区域统计暂降事件影响用户数量
	qyfxfx.main.Charts2 = function(qyNameList,chartsValueList){
		var myUsers = echarts.init(document.getElementById('NumberUsers'));
		var datasTwo = chartsValueList;
		var optionUsers = {
				backgroundColor: 'white',
	            color: ['#3eba30'],
	            tooltip: {},
	            xAxis: {
	            	data : qyNameList,
	            	axisLabel : {
	                    fontSize : '16'				//x轴字体的大小
	                },
	            },
	            yAxis: {
	            	name: '单位:户',
	            	nameGap: '20',
			        nameTextStyle: {
			        	fontSize: '18'				//y轴名称字体大小
			        },
			        axisLabel : {
	                    fontSize : '16'				//y轴字体的大小
	                },
	            },
	            grid:{
	            	left:50,
	            	right:20
	            },
	            series: [{
	                type: 'bar',
	                data: datasTwo,
	                barWidth : '55%'
	            }]
			};
		myUsers.setOption(optionUsers);
		myUsers.on('click', function (params) {
			qyfxfx.main.dialogFuc(params.name,"yxyh","org");
		});
	}
	
	//按月份统计暂降事件影响用户数量
	qyfxfx.main.Charts3 = function(chartsValueList){
		var myUsersTwo = echarts.init(document.getElementById('NumberUsersTwo'));
		var datasThree = chartsValueList;
		var optionUsersTwo = {
				backgroundColor: 'white',
			    tooltip: {
			        trigger: 'axis'
			    },
			    xAxis: {
			        data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月','12月'],
			        axisLabel : {
	                    fontSize : '16'				//x轴字体的大小
	                },
			    },
			    yAxis: {
			        type: 'value',
			        name: '单位:户',
			        nameGap: '20',
			        nameTextStyle: {
			        	fontSize: '18'				//y轴名称字体大小
			        },
			        axisLabel : {
	                    fontSize : '16'				//y轴字体的大小
	                },
			        axisLine: {
			            show: false
			        }
			    },
			    grid:{
	            	left:50,
	            	right:20
	            },
	            series: [{
			        type: 'line',
			        data: datasThree
			    }],
			    color: ['#fd5f35'],
		};
		myUsersTwo.setOption(optionUsersTwo);
		myUsersTwo.on('click', function (params) {
			qyfxfx.main.dialogFuc(params.name,"yxyh","month");
		});
	}
	
	//按供电区域统计重复影响母线条数
	qyfxfx.main.Charts4 = function(qyNameList,chartsValueList){
		var myChartTwo = echarts.init(document.getElementById('eneratrixTwo'));
		var datasFour = chartsValueList;
		var optionTwo = {
				backgroundColor: 'white',
			    tooltip: {
			        trigger: 'axis'
			    },
			    xAxis: {
			        data: qyNameList,
			        axisLabel : {
	                    fontSize : '16'				//x轴字体的大小
	                },
			    },
			    yAxis: {
			        type: 'value',
			        name: '单位:条',
			        nameGap: '20',
			        nameTextStyle: {
			        	fontSize: '18'				//y轴名称字体大小
			        },
			        axisLabel : {
	                    fontSize : '16'				//y轴字体的大小
	                },
			        axisLine: {
			            show: false
			        }
			    },
			    grid:{
	            	left:50,
	            	right:20
	            },
	            series: [{
			        type: 'line',
			        data: datasFour
			    }],
			    color: ['#0c6462'],
		};
		myChartTwo.setOption(optionTwo);
		myChartTwo.on('click', function (params) {
			qyfxfx.main.dialogFuc(params.name,"cfyxBus","org");
		});
	}
	
	//明细页面点击显示
	qyfxfx.main.dialogFuc = function(clickLegend,zsdx,tjwd){
		var theYear = parseInt($(".yearTip").html());
		console.log(clickLegend,zsdx,tjwd,theYear);
		var dialogTitle = "";
		if(zsdx=="yxmx"){
			dialogTitle = "影响母线详情";
		}else if(zsdx=="yxyh"){
			dialogTitle = "影响用户详情";
		}else if(zsdx=="cfyxBus"){
			dialogTitle = "重复影响母线详情";
		}
		$("#detailWin").dialog({
			width : 1200,
			height : 640,
			href : "view?legendName=" + clickLegend+ "&zsdx=" + zsdx + "&tjwd=" + tjwd + "&year=" +theYear,
			title : clickLegend+""+dialogTitle
		}).dialog("open");
	}
	
</script>
</head>

<body>
  <div class="timecheck" >
		<div class="yearBox">
			<div class="yearContainer">
				<div class="yearTip"><%=thisYear%></div>
				<div class="upYearTip" ></div>
				<div class="downYearTip" ></div>
			</div>
		</div>
	</div>
	<div class="easyui-layout" fit="true" border="false">
		<div region="north" border="false" style="height: 50%;">
			<div class="easyui-layout" fit="true" border="false">
				<div region="west"  border="fasle" style="width: 50%; padding: 20px 10px 10px 20px;background: #e6f4f7;">
					<div class="easyui-layout" fit="true" border="true">
						<div region="north" border="true" class="title">
							<span class="titlespan">按供电区域统计暂降事件影响母线数量</span>
						</div>
						<div region="center" border="true" class="content" id="eneratrix"></div>
					</div>
				</div>
				<div region="center" border="false"  style="padding: 20px 20px 10px 10px;background: #e6f4f7;">
					<div class="easyui-layout" fit="true" border="true">
						<div region="north" border="true" class="title">
							<span class="titlespan">按供电区域统计暂降事件影响用户数量</span>
						</div>
						<div region="center" border="true" class="content" id="NumberUsers"></div>
					</div>
				</div>
			</div>
		</div>
		<div region="center" border="false" >
			<div class="easyui-layout" fit="true" border="false">
				<div region="west" border="fasle" style="width: 50%; padding: 10px 10px 20px 20px;background: #e6f4f7;">
					<div class="easyui-layout" fit="true" border="true">
						<div region="north" border="true" class="title">
							<span class="titlespan">按月份统计暂降事件影响用户数量</span>
						</div>
						<div region="center" border="true"  class="content" id="NumberUsersTwo"></div>
					</div>
				</div>
				<div region="center" border="false"  style="padding: 10px 20px 20px 10px;background: #e6f4f7;">
					<div class="easyui-layout" fit="true" border="true">
						<div region="north" border="true" class="title">
							<span class="titlespan">按供电区域统计重复影响母线条数</span>
						</div>
						<div region="center" border="true"  class="content" id="eneratrixTwo"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="detailWin" data-options="modal:true,closed:true"></div>