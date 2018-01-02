<%@page import="java.util.Calendar"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<title>暂降事件年度分析</title>
<%@ include file="../../include/common.jsp"%>
<link href="${ctx}/static/css/main.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../../static/echarts.min.js"></script>
<%
	Calendar now = Calendar.getInstance();//得到当前系统时间 
	int thisYear = now.get(Calendar.YEAR);
%>
<script type="text/javascript">
	$.namespace("zjsjndfx.main");
	
	// 页面加载完成后执行
	$(document).ready(function() {
		zjsjndfx.main.yearBoxClick();
		zjsjndfx.main.chartsSearch();
	});
	
	//查询
	zjsjndfx.main.chartsSearch = function(){
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
					zjsjndfx.main.dataManager(json);
				}
			}
		});
		
	}
	
	//展示数据处理
	zjsjndfx.main.dataManager = function(data){
		//console.log("查询结果:",data);
		
		//按电压等级统计暂降事件发生次数
		var legendData = new Array();
		var seriesData = new Array();
		var charts1Data = data["charts1"];
		for(var index in charts1Data["sysCode"]){
			var json = charts1Data["sysCode"][index];
			legendData.push(json["NAME"]);
			for(var i in charts1Data["dataList"]){
				if(json["CODE"] == charts1Data["dataList"][i]["TJWD"]){
					var object = new Object();
					object["value"] = charts1Data["dataList"][i]["FSCS"];
					object["name"] = json["NAME"];
					seriesData.push(object);
				}
			}
			if(seriesData[index]==undefined){
				seriesData.push({value:0, name:json["NAME"]});
			}
		}
		zjsjndfx.main.Charts1(legendData,seriesData);
		//按发生原因统计暂降事件发生次数
		var legendData2 = new Array();
		var seriesData2 = new Array();
		var charts2Data = data["charts2"];
		for(var index in charts2Data["sysCode"]){
			var json = charts2Data["sysCode"][index];
			legendData2.push(json["NAME"]);
			for(var i in charts2Data["dataList"]){
				if(json["CODE"] == charts2Data["dataList"][i]["TJWD"]){
					var object = new Object();
					object["value"] = charts2Data["dataList"][i]["FSCS"];
					object["name"] = json["NAME"];
					seriesData2.push(object);
				}
			}
			if(seriesData2[index]==undefined){
				seriesData2.push({value:0, name:json["NAME"]});
			}
		}
		zjsjndfx.main.Charts2(legendData2,seriesData2);
		//按供电区域统计暂降事件发生次数
		var qyNameList= data["qyList"]["qyName"];
		var qyIDList = data["qyList"]["qyId"];
		var seriesData3 = new Array();
		var charts3Data = data["charts3"];
		for(var index in qyIDList){
			var quID = qyIDList[index];
			for(var i in charts3Data){
				if(quID == charts3Data[i]["TJWD"]){
					seriesData3.push(charts3Data[i]["FSCS"]==null?0:charts3Data[i]["FSCS"]);
				}
			}
			if(seriesData3[index]==undefined){
				seriesData3.push(0);
			}
		}
		zjsjndfx.main.Charts3(qyNameList,seriesData3);
		//按月份统计暂降事件发生次数
		var charts4Data = data["charts4"];
		var thisYearData = new Array();
		var lastYearData = new Array();
		var beforeLastYearData = new Array();
		for(var month=1;month<=12;month++){
			for(var i in charts4Data["thisYearData"]){
				if(month == charts4Data["thisYearData"][i]["TJWD"]){
					thisYearData.push(charts4Data["thisYearData"][i]["FSCS"]==null?0:charts4Data["thisYearData"][i]["FSCS"]);
				}
			}
			for(var i in charts4Data["lastYearData"]){
				if(month == charts4Data["lastYearData"][i]["TJWD"]){
					lastYearData.push(charts4Data["lastYearData"][i]["FSCS"]==null?0:charts4Data["lastYearData"][i]["FSCS"]);
				}
			}
			for(var i in charts4Data["beforeLastYearData"]){
				if(month == charts4Data["beforeLastYearData"][i]["TJWD"]){
					beforeLastYearData.push(charts4Data["beforeLastYearData"][i]["FSCS"]==null?0:charts4Data["beforeLastYearData"][i]["FSCS"]);
				}
			}
			if(thisYearData[month-1]==undefined){
				thisYearData.push(0);
			}
			if(lastYearData[month-1]==undefined){
				lastYearData.push(0);
			}
			if(beforeLastYearData[month-1]==undefined){
				beforeLastYearData.push(0);
			}
		}
		zjsjndfx.main.Charts4(thisYearData,lastYearData,beforeLastYearData);
	}
	
	//年度选择监听
	zjsjndfx.main.yearBoxClick = function(){
		$(".upYearTip").click(function(){
			var theYear = parseInt($(".yearTip").html());
			$(".yearTip").html(theYear+1);
			zjsjndfx.main.chartsSearch();
		});
		$(".downYearTip").click(function(){
			var theYear = parseInt($(".yearTip").html());
			$(".yearTip").html(theYear-1);
			zjsjndfx.main.chartsSearch();
		});
	}
	
	//按电压等级统计暂降事件发生次数
	zjsjndfx.main.Charts1 = function(legendData,seriesData){
		var dydjChart = echarts.init(document.getElementById('dydj'));
		var winWidth = window.innerWidth;    				//获取窗口的宽
		
		var firstRadius = ['0%', '9%'];
		var secondRadius = ['10%','19%'];
		var thirdRadius = ['20%', '58%'];
		var forthRadius = ['64%', '65%'];
		
		var centerPosition = ['50%', '42%'];
		
		var optionPieI = {
				backgroundColor: 'white',
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
			        data:legendData,
				},
				tooltip: {
					formatter : '{a} <br/> {b} : {c} 次 ({d}%)'
				},
				series :[{
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
					name:'暂降事件发生次数',
		            type:'pie',
		            radius : thirdRadius,
		            center : centerPosition,
		            roseType : 'radius',
		            itemStyle: {
			             normal:{
			               label:{
			               show:true,
			               formatter: '{c}次',
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
			             },
		            color: ['#fd6035','#82c730','#36b6dd','#face2f','#0084c5'],
		            data:seriesData
		        }]
		};
		dydjChart.setOption(optionPieI);
		dydjChart.on('click', function (params) {
		    zjsjndfx.main.dialogFuc(params.data.name,"zjsj","dydj");
		});
	}
	
	//按发生原因统计暂降事件发生次数
	zjsjndfx.main.Charts2 = function(legendData,seriesData){
		var fsyyChart = echarts.init(document.getElementById('fsyy'));
		var winWidth = window.innerWidth;    				//获取窗口的宽
		var firstRadius = ['0%', '9%'];
		var secondRadius = ['10%','19%'];
		var thirdRadius = ['20%', '58%'];
		var forthRadius = ['64%', '65%'];
		
		var centerPosition = ['50%', '42%'];
		
		var optionPieII = {
				backgroundColor: 'white',
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
			        data:legendData,
				},
				tooltip: {
					formatter : '{a} <br/> {b} : {c} 次 ({d}%)'
				},
				color: ['#fd6035','#82c730','#36b6dd','#face2f','#0084c5'],
				series :[{
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
					name:'暂降事件发生次数',
		            type:'pie',
		            radius : thirdRadius,
		            center : centerPosition,
		            roseType : 'radius',
		            itemStyle: {
			             normal:{
			               label:{
			               show:true,
			               formatter: '{c}次',
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
			             },
		            data:seriesData	
		        }]
		};
		fsyyChart.setOption(optionPieII);
		fsyyChart.on('click', function (params) {
		    zjsjndfx.main.dialogFuc(params.data.name,"zjsj","gzlx");
		});
	}
	
	//按供电区域统计暂降事件发生次数
	zjsjndfx.main.Charts3 = function(legendData,seriesData){
		var myChart = echarts.init(document.getElementById('powerSupply'));
		var option = {
				backgroundColor: 'white',
				color: ['#3398DB'],
	            tooltip: {},
	            xAxis: {
	            	data : legendData,
	            	axisLabel : {
	                    fontSize : '16'				//x轴字体的大小
	                },
	            },
	            yAxis: {
	            	name: '单位:次',
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
//	                 name: '销量',
	                type: 'bar',
	                data: seriesData,
	                barWidth : '55%'
	            }]
		};
		myChart.setOption(option);
		myChart.on('click', function (params) {
		    zjsjndfx.main.dialogFuc(params.name,"zjsj","org");
		});
	}

	//按月份统计暂降事件发生次数
	zjsjndfx.main.Charts4 = function(thisYearData,lastYearData,beforeLastYearData){
		var thisYear = parseInt($(".yearTip").html());
		var lastYear = thisYear-1;
		var beforeLastYear = thisYear-2;
		var myChartFour = echarts.init(document.getElementById('month'));
		var optionFour={
				backgroundColor: 'white',
				tooltip: {
			        trigger: 'axis', //按x轴显示
			        show: true,
			        axisPointer: {
			            lineStyle: {
			                color: 'none', //不显示线条
			            },
			        },
			        backgroundColor: 'rgba(255, 255, 255, 0.5)',
			        textStyle: {
			            align: 'left',
			            fontSize: 12,
			            color: '#333333',
			        },
			        extraCssText: 'box-shadow: 0 1px 5px 0 rgba(0, 0, 0, 0.2)' //添加阴影
			    },
			    color: ['#3eba30', '#0c6462', '#fd5f35'],
			    grid: {
			        left: '50',
			        right: '20',
//	 		        top: '10',
			        bottom: '40',
			        // containLabel: true
			    },
			    legend: {
			        show: true,
			        icon: 'circle',
			        right: 20,
			        top: 10,
			        textStyle: {
			            fontSize: 12,
			            color: '#333333'
			        },
			        data: [thisYear+"", lastYear+"", beforeLastYear+""]
			    },
			    xAxis: {
			        show: true,
			        type: 'category',
			        axisTick: {
			            show: false
			        },
			        axisLine: {
			            show: false,
			            lineStyle: {
			                // color: '#eeeeee',
			            }
			        },
			        axisLabel : {
	                    fontSize : '16'				//x轴字体的大小
	                },
			        data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
			    },
			    yAxis: {
			        show: true,
			        type: 'value',
			        name: '单位:次',
			        nameGap: '20',
			        nameTextStyle: {
			        	fontSize: '18'				//y轴名称字体大小
			        },
			        axisLabel : {
	                    fontSize : '16'				//y轴字体的大小
	                },
			        axisTick: {
			            show: false
			        },
			        axisLine: {
			            show: false,
			        },
			        splitLine: {
			            show: true,
			            lineStyle: {
			                // color: '#f2f3f7'
			            }
			        },
			        axisLabel: {
			            textStyle: {
			                fontSize: 16,
			                // color: '#999999'
			            }
			        }
			    },
			    series: [{
			        name: beforeLastYear+"",
			        type: 'line',
			        symbol: 'circle',
			        itemStyle: {
			            emphasis: {
			                symbol: 'circle',
			            }
			        },
			        symbolSize: [8, 8],
			        data: beforeLastYearData
			    }, {
			        name: lastYear+"",
			        type: 'line',
			        symbol: 'circle',
			        symbolSize: [8, 8],
			        data: lastYearData
			    }, {
			        name: thisYear+"",
			        type: 'line',
			        symbol: 'circle',
			        symbolSize: [8, 8],
			        data: thisYearData
			    }]
			};
		myChartFour.setOption(optionFour);
		myChartFour.on('click', function (params) {
			zjsjndfx.main.dialogFuc(params.name,"zjsj","month");
		});
	}
	
	//明细页面点击显示
	zjsjndfx.main.dialogFuc = function(clickLegend,zsdx,tjwd){
		var theYear = parseInt($(".yearTip").html());
		//console.log(clickLegend,zsdx,tjwd,theYear);
		$("#detailWin").dialog({
			width : 1200,
			height : 640,
			href : "view?legendName=" + clickLegend+ "&zsdx=" + zsdx + "&tjwd=" + tjwd + "&year=" +theYear,
			title : clickLegend+"暂降事件详情"
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
							<span class="titlespan">按电压等级统计暂降事件发生次数</span>
						</div>
						<div region="center" border="true"  class="content" id="dydj">
						
						</div>
					</div>
				</div>
				<div region="center" border="false"  style="padding: 20px 20px 10px 10px;background: #e6f4f7;">
					<div class="easyui-layout" fit="true" border="true">
						<div region="north" border="true" class="title">
							<span class="titlespan">按发生原因统计暂降事件发生次数</span>
						</div>
						<div region="center" border="true" class="content" id="fsyy">
						
						</div>
					</div>
				</div>
			</div>
		</div>
		<div region="center" border="false" >
			<div class="easyui-layout" fit="true" border="false">
				<div region="west" border="fasle" style="width: 50%; padding: 10px 10px 20px 20px;background: #e6f4f7;">
					<div class="easyui-layout" fit="true" border="true">
						<div region="north" border="true" class="title">
							<span class="titlespan">按供电区域统计暂降事件发生次数</span>
						</div>
						<div region="center" border="true"  class="content" id="powerSupply"></div>
					</div>
				</div>
				<div region="center" border="false"  style="padding: 10px 20px 20px 10px;background: #e6f4f7;">
					<div class="easyui-layout" fit="true" border="true">
						<div region="north" border="true" class="title">
							<span class="titlespan">按月份统计暂降事件发生次数</span>
						</div>
						<div region="center" border="true"  class="content" id="month"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="detailWin" data-options="modal:true,closed:true"></div>