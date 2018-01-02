<%@page import="java.util.Calendar"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<title>母线风险年度分析</title>
<%@ include file="../../include/common.jsp"%>
<link href="${ctx}/static/css/main.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../../static/echarts.min.js"></script>
<%
	Calendar now = Calendar.getInstance();//得到当前系统时间 
	int thisYear = now.get(Calendar.YEAR);
%>
<script type="text/javascript">

	

	$.namespace("mxfxndfx.main");
	
	// 页面加载完成后执行
	$(document).ready(function() {
		mxfxndfx.main.yearBoxClick();
		mxfxndfx.main.chartsSearch();
		
	});
	
	//查询
	mxfxndfx.main.chartsSearch = function(){
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
					mxfxndfx.main.dataManager(json);
				}
			}
		});
		
	}
	
	//展示数据处理
	mxfxndfx.main.dataManager = function(data){
		//console.log("查询结果:",data);
		
		//按电压等级统计暂降事件影响母线数量
		var legendData = new Array();
		var seriesData = new Array();
		var charts1Data = data["charts1"];
		for(var index in charts1Data["sysCode"]){
			var json = charts1Data["sysCode"][index];
			legendData.push(json["NAME"]);
			for(var i in charts1Data["dataList"]){
				if(json["CODE"] == charts1Data["dataList"][i]["TJWD"]){
					var object = new Object();
					object["value"] = charts1Data["dataList"][i]["BUSSL"];
					object["name"] = json["NAME"];
					seriesData.push(object);
				}
			}
			if(seriesData[index]==undefined){
				seriesData.push({value:0, name:json["NAME"]});
			}
		}
		mxfxndfx.main.Charts1(legendData,seriesData);
		//按幅值范围统计影响母线数量
		var legendData2 = new Array();
		var seriesData2 = new Array();
		var charts2Data = data["charts2"];
		for(var index in charts2Data["sysCode"]){
			var json = charts2Data["sysCode"][index];
			json["NAME"] = json["NAME"].replace("&lt;","<");
			json["NAME"] = json["NAME"].replace("&gt;",">");
			legendData2.push(json["NAME"]);
			for(var i in charts2Data["dataList"]){
				if(json["CODE"] == charts2Data["dataList"][i]["TJWD"]){
					seriesData2.push(charts2Data["dataList"][i]["BUSSL"]);
				}
			}
			if(seriesData2[index]==undefined){
				seriesData2.push(0);
			}
		}
		mxfxndfx.main.Charts2(legendData2,seriesData2);
		//按月份统计暂降事件影响母线数量
		var seriesData3 = new Array();
		var charts3Data = data["charts3"];
		for(var month=1;month<=12;month++){
			for(var i in charts3Data){
				if(month == charts3Data[i]["TJWD"]){
					seriesData3.push(charts3Data[i]["BUSSL"]==null?0:charts3Data[i]["BUSSL"]);
				}
			}
			if(seriesData3[month-1]==undefined){
				seriesData3.push(0);
			}
		}
		mxfxndfx.main.Charts3(seriesData3);
		//重复影响母线条数
		var legendData4 = new Array();
		var seriesData4 = new Array();
		var charts4Data = data["charts4"];
		for(var index in charts4Data["sysCode"]){
			var json = charts4Data["sysCode"][index];
			legendData4.push(json["NAME"]);
			for(var i in charts4Data["dataList"]){
				if(json["CODE"] == charts4Data["dataList"][i]["TJWD"]){
					var object = new Object();
					object["value"] = charts4Data["dataList"][i]["BUSSL"];
					object["name"] = json["NAME"];
					seriesData4.push(object);
				}
			}
			if(seriesData4[index]==undefined){
				seriesData4.push({value:0, name:json["NAME"]});
			}
		}
		mxfxndfx.main.Charts4(legendData4,seriesData4);
	}
	
	//年度选择监听
	mxfxndfx.main.yearBoxClick = function(){
		$(".upYearTip").click(function(){
			var theYear = parseInt($(".yearTip").html());
			$(".yearTip").html(theYear+1);
			mxfxndfx.main.chartsSearch();
		});
		$(".downYearTip").click(function(){
			var theYear = parseInt($(".yearTip").html());
			$(".yearTip").html(theYear-1);
			mxfxndfx.main.chartsSearch();
		});
	}
	
	//按电压等级统计暂降事件影响母线数量
	
	mxfxndfx.main.Charts1 = function(legendData,seriesData){
		var winWidth = window.innerWidth;    				//获取窗口的宽
		
		var dydjBus = echarts.init(document.getElementById('dydjBus'));
		var firstRadius = ['0%', '9%'];
		var secondRadius = ['10%','19%'];
		var thirdRadius = ['20%', '58%'];
		var forthRadius = ['64%', '65%'];
		
		var centerPosition = ['50%', '42%'];
		
		var optionDydjBus = {
				backgroundColor: 'white',
				title : {
			        text: '',
			        subtext: '',
			        x:'center'
			    },
			    tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {c} 条 ({d}%)"
			    },
			    legend: {
			        x : 'center',
			        y : 'bottom',
			        //itemGap : 50,      //图例间距 
			        textStyle:{
	                	color:'#000',
	              		fontSize:18,
	              		fontWeight:'500'
	          		},
	          		padding : 20,      //图例内边距 ,
	          		width : '75%',
	          		//itemHeight : 18,
			        data:legendData
			    },
			    toolbox: {
			        show : false,
			    },
			    calculable : true,
			    color:['#FD6035','#82C730','#FACE2F','#36B6DD','#0084C5'],
			    series : [{
			            name:'半径模式',
			            type:'pie',
			            radius : firstRadius,
			            center : centerPosition,
			            roseType : 'radius',
			            hoverAnimation:false,   // 关闭悬浮动画
			            cursor:'auto',    // 鼠标悬浮样式默认
			            tooltip : {show: false},  // 关闭鼠标悬浮tip标签
			            itemStyle:{
			                normal: {
			                    color: '#012928',
			                }
			            },
			            label: {
			                normal: {
			                    show: false
			                },
			            },
			            data:[1]
			        },{
			            name:'半径模式',
			            type:'pie',
			            radius : secondRadius,
			            center : centerPosition,
			            roseType : 'radius',
			            hoverAnimation:false,   // 关闭悬浮动画
			            cursor:'auto',    // 鼠标悬浮样式默认
			            tooltip : {show: false},  // 关闭鼠标悬浮tip标签
			            itemStyle:{
			                normal: {
			                    color: '#012928',
			                }
			            },
			            label: {
			                normal: {
			                    show: false
			                },
			            },
			            data:[1]
			        },{
			            name:'半径模式',
			            type:'pie',
			            radius : forthRadius,
			            center : centerPosition,
			            roseType : 'radius',
			            label: {
			                normal: {
			                    show: false
			                },
			            },
			            hoverAnimation:false,   // 关闭悬浮动画
			            cursor:'auto',    // 鼠标悬浮样式默认
			            tooltip : {show: false},  // 关闭鼠标悬浮tip标签
			            itemStyle:{
			                normal: {
			                    color: '#9A9A9A',
			                }
			            },
			            data:[1]
			        },{
			            name:'影响母线数量',
			            type:'pie',
			            radius : thirdRadius,
			            center : centerPosition,
			            roseType : 'radius',
			            x: '50%',               // for funnel
			            max: 40,                // for funnel
			            sort : 'ascending',     // for funnel
			            data:seriesData,
			            itemStyle: {
					           normal:{
					             label:{
					             show:true,
					             formatter: '{c}条',
					            textStyle:{
					                	color:'#000',
					              		fontSize:20,
					              		fontWeight:'500'
					                }
					               
					             },
					             labelLine:{					//标签文字
					            	show:true,
					               length:25,
					               length2 : 70,
					               lineStyle:{
					               	width:2
					               }
					             }
					             },
					             emphasis: {
					                   shadowBlur: 10,
					                   shadowOffsetX: 0,
					                   shadowColor: 'rgba(0, 0, 0, 0.5)'
					               }
					           }
			        }
			    ]
			};
		dydjBus.setOption(optionDydjBus);
		dydjBus.on('click', function (params) {
			mxfxndfx.main.dialogFuc(params.data.name,"yxmx","dydj");
		});
	}
	
	//按幅值范围统计影响母线数量
	mxfxndfx.main.Charts2 = function(legendData,seriesData){
		var fzfwBus = echarts.init(document.getElementById('fzfwBus'));
		var datasTwo = seriesData;
		var optionFzfwBus = {
				backgroundColor: 'white',
	            color: ['#36B6DD'],
	            tooltip: {},
	            xAxis: {
	            	data : legendData,
	            	axisLabel : {
	                    fontSize : 13
	                }
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
	                data: datasTwo,
	                barWidth : '55%'
	            }]
			};
		fzfwBus.setOption(optionFzfwBus);
		fzfwBus.on('click', function (params) {
			mxfxndfx.main.dialogFuc(params.name,"yxmx","PU");
		});
	}
	
	//按月份统计暂降事件影响母线数量
	mxfxndfx.main.Charts3 = function(seriesData){
		var yfBus = echarts.init(document.getElementById('yfBus'));
		var datasThree = seriesData;
		var optionYfBus = {
				backgroundColor: 'white',
			    tooltip: {
			        trigger: 'axis'
			    },
			    xAxis: {
			    	axisLabel : {
	                    fontSize : '16'				//x轴字体的大小
	                },
			        data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月','12月']
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
			        data: datasThree
			    }],
			    color: ['#fd5f35'],
		};
		yfBus.setOption(optionYfBus);
		yfBus.on('click', function (params) {
			mxfxndfx.main.dialogFuc(params.name,"yxmx","month");
		});
	}
	
	//重复影响母线条数
	mxfxndfx.main.Charts4 = function(legendData,seriesData){
		var winWidth = window.innerWidth;    				//获取窗口的宽
		
		var cfyxBus = echarts.init(document.getElementById('cfyxBus'));
		var firstRadius = ['0%', '9%'];
		var secondRadius = ['10%','19%'];
		var thirdRadius = ['20%', '58%'];
		var forthRadius = ['64%', '65%'];
		
		var centerPosition = ['50%', '42%'];
		var optionCfyxBus = {
				backgroundColor: 'white',
				title : {
			        text: '',
			        subtext: '',
			        x:'center'
			    },
			    tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {c} 条 ({d}%)"
			    },
			    legend: {
			        x : 'center',
			        y : 'bottom',
			        itemGap : winWidth * 0.03,
			        textStyle:{
	                	color:'#000',
	              		fontSize:18,
	              		fontWeight:'500'
	          		},
	          		padding : 20,      //图例内边距 
			        data:legendData
			    },
			    toolbox: {
			        show : false,
			    },
			    calculable : true,
			    color:['#FD6035','#82C730','#FACE2F','#36B6DD','#0084C5'],
			    series : [{
			            name:'半径模式',
			            type:'pie',
			            radius : firstRadius,
			            center : centerPosition,
			            roseType : 'radius',
			            hoverAnimation:false,   // 关闭悬浮动画
			            cursor:'auto',    // 鼠标悬浮样式默认
			            tooltip : {show: false},  // 关闭鼠标悬浮tip标签
			            itemStyle:{
			                normal: {
			                    color: '#012928',
			                }
			            },
			            label: {
			                normal: {
			                    show: false
			                },
			            },
			            data:[1]
			        },{
			            name:'半径模式',
			            type:'pie',
			            radius : secondRadius,
			            center : centerPosition,
			            roseType : 'radius',
			            hoverAnimation:false,   // 关闭悬浮动画
			            cursor:'auto',    // 鼠标悬浮样式默认
			            tooltip : {show: false},  // 关闭鼠标悬浮tip标签
			            itemStyle:{
			                normal: {
			                    color: '#012928',
			                }
			            },
			            label: {
			                normal: {
			                    show: false
			                },
			            },
			            data:[1]
			        },{
			            name:'半径模式',
			            type:'pie',
			            radius : forthRadius,
			            center : centerPosition,
			            roseType : 'radius',
			            label: {
			                normal: {
			                    show: false
			                },
			            },
			            hoverAnimation:false,   // 关闭悬浮动画
			            cursor:'auto',    // 鼠标悬浮样式默认
			            tooltip : {show: false},  // 关闭鼠标悬浮tip标签
			            itemStyle:{
			                normal: {
			                    color: '#9A9A9A',
			                }
			            },
			            data:[1]
			        },{
			            name:'影响母线条数',
			            type:'pie',
			            radius : thirdRadius,
			            center : centerPosition,
			            roseType : 'radius',
			            x: '50%',               // for funnel
			            max: 40,                // for funnel
			            sort : 'ascending',     // for funnel
			            data:seriesData,
				        itemStyle: {
				             normal:{
				               label:{
				               show:true,
				               formatter: '{c}条',
				              textStyle:{
				                  	color:'#000',
				                		fontSize:20,
				                		fontWeight:'500'
				                  }
				                 
				               },
				               labelLine:{
				              	show:true,
				                 length:10,
				                 length2 : 80,
				                 lineStyle:{
				                 	width:2
				                 }
				               }
				               },
				               emphasis: {
				                     shadowBlur: 10,
				                     shadowOffsetX: 0,
				                     shadowColor: 'rgba(0, 0, 0, 0.5)'
				                 }
				             }
			        }
			    ]
		        
			};
		cfyxBus.setOption(optionCfyxBus);
		cfyxBus.on('click', function (params) {
			mxfxndfx.main.dialogFuc(params.data.name,"yxmx","cfyx");
		});
	}
	
	//明细页面点击显示
	mxfxndfx.main.dialogFuc = function(clickLegend,zsdx,tjwd){
		var theYear = parseInt($(".yearTip").html());
		console.log('--------------' + clickLegend, '++++++++++++++++++' + theYear, '_+_+_+' + zsdx, tjwd);
		if(tjwd=="PU"){
			switch(clickLegend){
			case "<20%":clickLegend = 1;break;
			case "20%-30%":clickLegend = 2;break;
			case "30%-40%":clickLegend = 3;break;
			case "40%-50%":clickLegend = 4;break;
			case "50%-60%":clickLegend = 5;break;
			case "60%-70%":clickLegend = 6;break;
			case "70%-80%":clickLegend = 7;break;
			case "80%-90%":clickLegend = 8;break;
			case ">90%":clickLegend = 9;break;
			}
		}
		$("#detailWin").dialog({
			width : 1200,
			height : 640,
			href : "view?legendName=" + clickLegend+ "&zsdx=" + zsdx + "&tjwd=" + tjwd + "&year=" +theYear,
			title : clickLegend+"影响母线详情"
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
							<span class="titlespan">按电压等级统计暂降事件影响母线数量</span>
						</div>
						<div region="center" border="true" class="content" id="dydjBus"></div>
					</div>
				</div>
				<div region="center" border="false"  style="padding: 20px 20px 10px 10px;background: #e6f4f7;">
					<div class="easyui-layout" fit="true" border="true">
						<div region="north" border="true" class="title">
							<span class="titlespan">按幅值范围统计影响母线数量</span>
						</div>
						<div region="center" border="true" class="content" id="fzfwBus"></div>
					</div>
				</div>
			</div>
		</div>
		
		<div region="center" border="false" >
			<div class="easyui-layout" fit="true" border="false">
				<div region="west" border="fasle" style="width: 50%; padding: 10px 10px 20px 20px;background: #e6f4f7;">
					<div class="easyui-layout" fit="true" border="true">
						<div region="north" border="true" class="title">
							<span class="titlespan">按月份统计暂降事件影响母线数量</span>
						</div>
						<div region="center" border="true"  class="content" id="yfBus"></div>
					</div>
				</div>
				<div region="center" border="false"  style="padding: 10px 20px 20px 10px;background: #e6f4f7;">
					<div class="easyui-layout" fit="true" border="true">
						<div region="north" border="true" class="title">
							<span class="titlespan">重复影响母线条数</span>
						</div>
						<div region="center" border="true"  class="content" id="cfyxBus"></div>
					</div>
				</div>
			</div>
		</div>
		
	</div>
	<div id="detailWin" data-options="modal:true,closed:true"></div>