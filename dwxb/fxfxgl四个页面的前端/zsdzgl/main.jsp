<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<title>展示定制管理</title>
<%@ include file="../../include/common.jsp"%>
<link href="${ctx}/static/css/main.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../../static/echarts.min.js"></script>
<%
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	java.util.Date currentTime = new java.util.Date();//得到当前系统时间 
	String end = formatter.format(currentTime); //将日期时间格式化
	currentTime.setHours(0);
	currentTime.setMinutes(0);
	currentTime.setSeconds(0);
	String start = formatter.format(currentTime);
%>
<style type="text/css">
/* 复选框样式替换 */
input[type="checkbox"] {
  -webkit-appearance: none;
}
input[type="checkbox"] {
  -webkit-appearance: none;
  background: #e6f4f7 url("../../static/images/fxfxgl/newCheckBox.png");
  height: 22px;
  vertical-align: text-top;
  width: 22px;
}
input[type="checkbox"]:checked {
  background-position: -48px 0;
}
input[type="checkbox"]:focus,
input[type="checkbox"]:hover {
  background-position: -24px 0;
  outline: none;
}

input[type="checkbox"]:checked {
  background-position: -48px 0;
}

input[type="checkbox"][disabled] {
  background-position: -72px 0;
}

input[type="checkbox"][disabled]:checked {
  background-position: -96px 0;
}

/* --------------- */
.createBtn{
	background-color: #E4522B;
	color: #fff;
	border-radius: 4px;
	text-align: center;
    font-family: Microsoft YaHei;
    width: 275px;
    height: 50px;
    line-height:50px;
    font-size: 22px;
    font-weight: bold;
    letter-spacing:20px;
    margin:0 auto;
    margin-top: 10px;
    cursor: pointer;
}
.viewLable{
	width: 130px;
	height: 41px;
	line-height:41px;
	border-radius:5px;
	background-color: #075957;
	color: #E6F5F8;
	font-size: 20px;
	font-weight: bold;
	text-align: center;
	font-family: Microsoft YaHei;
	letter-spacing:2px;
}
.viewSelect{
	color: #04534E;
	font-size: 20px;
	height: 41px;
	line-height:41px;
	margin-left: 16px;
	letter-spacing:2px;
}
.barIcon{
	background-image: url("../../static/images/fxfxgl/barIcon.png");
	background-repeat: no-repeat;
	background-position: bottom left;
}
.pieIcon{
	background-image: url("../../static/images/fxfxgl/pieIcon.png");
	background-repeat: no-repeat;
	background-position: bottom left;
}
.lineIcon{
	background-image: url("../../static/images/fxfxgl/lineIcon.png");
	background-repeat: no-repeat;
	background-position: bottom left;
}
.objectTitleDiv{
	width: 100%;
	height: 45px;
	line-height:45px;
	border-top-left-radius:6px;
	border-top-right-radius:6px;
	border: 1px solid #044E4D;
	border-bottom: 0px;
	color: #04534E;
	font-size: 20px;
	text-indent: 10px;
}
.objectContentDiv{
	width: 100%;
	border-bottom-left-radius:6px;
	border-bottom-right-radius:6px;
	border: 1px dashed #044E4D;
	min-height: 100px;
}
.clean{
	clear: both;
}
#charts1Div,#charts2Div{
	visibility:hidden;
}
</style>
<script type="text/javascript">
	$.namespace("zsdzgl.main");
	
	zsdzgl.main.orgList = ${orgList};
	zsdzgl.main.chartsNum = 0; //统计charts生成个数，用于控制交互
	zsdzgl.main.chartsDiv; //charts生成地址 
	zsdzgl.main.zsdxText = "";
	zsdzgl.main.tjwdText = "";
	zsdzgl.main.unitText = "";
	zsdzgl.main.charts1Parems = new Object(); //第一个图的产生参数
	zsdzgl.main.charts2Parems = new Object(); //第二个图的产生参数
	
	// 页面加载完成后执行
	$(document).ready(function() {
		zsdzgl.main.zsdxCheckBox();
		zsdzgl.main.initOrgCombobox();
		zsdzgl.main.chartsClick();
	});
	
	//生成charts
	zsdzgl.main.chartsSearch = function(){
		//check生成条件是否完整
		var selectZsdx; //展示对象
		var selectZsfs; //展示方式
		var selectOrg; //供电单位
		var startDate; //开始时间
		var endDate; //结束时间
		var selectTjwd; //统计维度
		var selectZsdxList=document.getElementsByName('selectZsdx');
		for(var i=0; i<selectZsdxList.length; i++){
			if(selectZsdxList[i].checked){
				selectZsdx = selectZsdxList[i].value;
				zsdzgl.main.zsdxText = $(selectZsdxList[i]).parent().text().trim();
			}
		}
		var selectZsfsList=document.getElementsByName('selectZsfs');
		for(var i=0; i<selectZsfsList.length; i++){
			if(selectZsfsList[i].checked){
				selectZsfs = selectZsfsList[i].value;
			}
		}
		var selectTjwdList=document.getElementsByName('selectTjwd');
		for(var i=0; i<selectTjwdList.length; i++){
			if(selectTjwdList[i].checked){
				selectTjwd = selectTjwdList[i].value;
				zsdzgl.main.tjwdText = $(selectTjwdList[i]).parent().text().trim();
			}
		}
		selectOrg = $("#orgList").combobox("getValue");
		startDate = $("#startDate").val();
		endDate = $("#endDate").val();
		
		if(selectZsdx==undefined||selectZsdx==null||selectZsdx==""){
			$.messager.alert('错误', '需选择展示对象！', 'error');
			return;
		}
		if(selectZsfs==undefined||selectZsfs==null||selectZsfs==""){
			$.messager.alert('错误', '需选择展示方式！', 'error');
			return;
		}
		if(selectTjwd==undefined||selectTjwd==null||selectTjwd==""){
			$.messager.alert('错误', '需选择统计维度！', 'error');
			return;
		}
		//查询维度为月度时，禁止跨年选择
		if(selectTjwd=="month"&&(startDate.substring(0,4)!=endDate.substring(0,4))){
			$.messager.alert('错误', '统计维度为月时，禁止跨年选择！', 'error');
			return;
		}
		//查询维度为日时，禁止跨月选择
		if(selectTjwd=="day"&&(startDate.substring(0,7)!=endDate.substring(0,7))){
			$.messager.alert('错误', '统计维度为日时，禁止跨月选择！', 'error');
			return;
		}
		//查询维度为供电单位时，禁止选择单位
		if(selectTjwd=="org"&&selectOrg!=""){
			$.messager.alert('错误', '统计维度为供电单位时，仅能选择上海市！', 'error');
			return;
		}
		zsdzgl.main.search(selectZsdx,selectZsfs,selectOrg,startDate,endDate,selectTjwd);
	}
	
	//请求charts数据
	zsdzgl.main.search = function(selectZsdx,selectZsfs,selectOrg,startDate,endDate,selectTjwd){
		$.ajax({
			url : 'search',
			type:'POST',
			data:{
				'viewObject':selectZsdx,
				'viewOrg':selectOrg,
				'viewStartTime':startDate,
				'viewEndTime':endDate,
				'viewTJWD':selectTjwd
			},
			success:function(result){
				var json = JSON.parse(result);
				if(json["isSuccess"]==false){
					$.messager.alert("提示","查询失败！");
				}else{
					var title = zsdzgl.main.titleManager(); //新图的标题
					var chartsParems = new Object(); //查询的参数保存在全局变量，用于点击事件
					chartsParems["zsdx"] = selectZsdx;
					chartsParems["zsfs"] = selectZsfs;
					chartsParems["org"] = selectOrg;
					chartsParems["startDate"] = startDate;
					chartsParems["endDate"] = endDate;
					chartsParems["tjwd"] = selectTjwd;
					chartsParems["title"] = title;
					if(zsdzgl.main.chartsNum==0){
						$("#charts1Div").css("visibility","visible");
						zsdzgl.main.chartsDiv = document.getElementById('charts1');
						$("#charts1Div .titlespan").html(title);
						zsdzgl.main.charts1Parems = chartsParems;
					}else if(zsdzgl.main.chartsNum==1){
						$("#charts2Div").css("visibility","visible");
						zsdzgl.main.chartsDiv = document.getElementById('charts2');
						$("#charts2Div .titlespan").html(title);
						zsdzgl.main.charts2Parems = chartsParems;
					}else{
						zsdzgl.main.chartsDiv = document.getElementById('charts2');
						var oldCharts = echarts.init(zsdzgl.main.chartsDiv);
						var oldChartsOption = oldCharts.getOption();
						var charts1 = echarts.init(document.getElementById('charts1'));
						charts1.clear();
						charts1.setOption(oldChartsOption);
						oldCharts.clear();
						$("#charts1Div .titlespan").html($("#charts2Div .titlespan").html());
						$("#charts2Div .titlespan").html(title);
						zsdzgl.main.charts1Parems = zsdzgl.main.charts2Parems;
						zsdzgl.main.charts2Parems = chartsParems;
					}
					zsdzgl.main.dataManager(json,selectZsdx,selectZsfs,selectOrg,startDate,endDate,selectTjwd);
				}
			}
		});
	}
	
	//生成标题
	zsdzgl.main.titleManager = function(){
		var title = "按"+zsdzgl.main.tjwdText+"统计"+zsdzgl.main.zsdxText;
		//console.log(zsdzgl.main.zsdxText);
		if(zsdzgl.main.zsdxText=="暂降事件"){
			title = title + "发生次数";
			zsdzgl.main.zsdxText = zsdzgl.main.zsdxText + "发生次数";
			zsdzgl.main.unitText = "次";
		}else if(zsdzgl.main.zsdxText=="影响母线"){
			title = title + "数量";
			zsdzgl.main.zsdxText = zsdzgl.main.zsdxText + "数量";
			zsdzgl.main.unitText = "条";
		}else{
			title = title + "数量";
			zsdzgl.main.zsdxText = zsdzgl.main.zsdxText + "数量";
			zsdzgl.main.unitText = "户";
		}
		return title;
	}
	
	//数据处理
	zsdzgl.main.dataManager = function(data,selectZsdx,selectZsfs,selectOrg,startDate,endDate,selectTjwd){
		//console.log("..",data,zsdzgl.main.orgList);
		var seriesData = new Array();
		var legendData = new Array();
		if(selectTjwd=="org"){
			//供电单位
			var qyNameList= zsdzgl.main.orgList["qyName"];
			var qyIDList = zsdzgl.main.orgList["qyId"];
			var chartsData = data["charts"]["dataList"];
			for(var index in qyIDList){
				var quID = qyIDList[index];
				for(var i in chartsData){
					if(quID == chartsData[i]["NAME"]){
						seriesData.push(chartsData[i]["VALUE"]==null?0:chartsData[i]["VALUE"]);
					}
				}
				if(seriesData[index]==undefined){
					seriesData.push(0);
				}
			}
			legendData = qyNameList;
		}else if(selectTjwd=="year"){
			//年度
			var chartsData = data["charts"]["dataList"];
			if(startDate!=""&&endDate!=""){
				//当选择了起始时间和结束时间时，自动补全年份
				var index = 0;
				for(var i=parseInt(startDate.substring(0,4));i<=parseInt(endDate.substring(0,4));i++){
					legendData.push(i);
					for(var j in chartsData){
						if((i+"")==chartsData[j]["NAME"]){
							seriesData.push(chartsData[j]["VALUE"]==null?0:chartsData[j]["VALUE"]);
						}
					}
					if(seriesData[index]==undefined){
						seriesData.push(0);
					}
					index++;
				}
			}else{
				//当未选择起始时间或结束时间时，按查询结果显示值
				for(var j in chartsData){
					legendData.push(chartsData[j]["NAME"]);
					seriesData.push(chartsData[j]["VALUE"]==null?0:chartsData[j]["VALUE"]);
				}
			}
		}else if(selectTjwd=="month"){
			//月
			var chartsData = data["charts"]["dataList"];
			//当选择了起始时间和结束时间时，自动补全月份
			var index = 0;
			for(var i=parseInt(startDate.substring(5,7));i<=parseInt(endDate.substring(5,7));i++){
				legendData.push(i+"月");
				for(var j in chartsData){
					if(i==parseInt(chartsData[j]["NAME"])){
						seriesData.push(chartsData[j]["VALUE"]==null?0:chartsData[j]["VALUE"]);
					}
				}
				if(seriesData[index]==undefined){
					seriesData.push(0);
				}
				index++;
			}
		}else if(selectTjwd=="day"){
			//日
			var chartsData = data["charts"]["dataList"];
			//当选择了起始时间和结束时间时，自动补全天
			var index = 0;
			for(var i=parseInt(startDate.substring(8,10));i<=parseInt(endDate.substring(8,10));i++){
				legendData.push(i);
				for(var j in chartsData){
					if(i==parseInt(chartsData[j]["NAME"])){
						seriesData.push(chartsData[j]["VALUE"]==null?0:chartsData[j]["VALUE"]);
					}
				}
				if(seriesData[index]==undefined){
					seriesData.push(0);
				}
				index++;
			}
		}else{
			//其他
			var chartsData = data["charts"]["dataList"];
			var sysCode = data["charts"]["sysCode"];
			for(var index in sysCode){
				var json = sysCode[index];
				json["NAME"] = json["NAME"].replace("&lt;","<");
				json["NAME"] = json["NAME"].replace("&gt;",">");
				legendData.push(json["NAME"]);
				for(var i in chartsData){
					if(json["CODE"] == chartsData[i]["NAME"]){
						seriesData.push(chartsData[i]["VALUE"]);
					}
				}
				if(seriesData[index]==undefined){
					seriesData.push(0);
				}
			}
		}
		//console.log("--",legendData,seriesData);
		
		//判断展示方式，调用不同charts同时为pie处理数据
		if(selectZsfs=="bar"){
			zsdzgl.main.barCharts(legendData,seriesData);
		}else if(selectZsfs=="line"){
			zsdzgl.main.lineCharts(legendData,seriesData);
		}else if(selectZsfs=="pie"){
			var pieSeriesData = new Array();
			for(var i in legendData){
				var object = new Object();
				object["name"] = legendData[i];
				object["value"] = seriesData[i];
				pieSeriesData.push(object);
			}
			zsdzgl.main.pieCharts(legendData,pieSeriesData);
		}
	}
	
	//柱状图
	zsdzgl.main.barCharts = function(legendData,seriesData){
		var barCharts = echarts.init(zsdzgl.main.chartsDiv);
		var barChartsData = seriesData;
		var optionBar = {
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
	            	name: '单位:'+zsdzgl.main.unitText,
	            	nameGap: '20',
			        nameTextStyle: {
			        	fontSize: '20'
			        },
	            	axisLabel : {
	                    fontSize : '18'
	                }
	            },
	            grid:{
	            	left:50,
	            	right:20
	            },
	            series: [{
	                type: 'bar',
	                data: barChartsData,
	                barWidth : '45%'
	            }]
			};
		barCharts.setOption(optionBar);
		zsdzgl.main.chartsNum++;
	}
	
	//折线图
	zsdzgl.main.lineCharts = function(legendData,seriesData){
		var lineCharts = echarts.init(zsdzgl.main.chartsDiv);
		var lineChartsDatas = seriesData;
		var optionLine = {
				backgroundColor: 'white',
			    tooltip: {
			        trigger: 'axis'
			    },
			    xAxis: {
			        data: legendData
			    },
			    yAxis: {
			        type: 'value',
			        name: '单位:'+zsdzgl.main.unitText,
			        nameGap: '20',
			        nameTextStyle: {
			        	fontSize: '18'
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
			        data: lineChartsDatas
			    }],
			    color: ['#0c6462'],
		};
		lineCharts.setOption(optionLine);
		zsdzgl.main.chartsNum++;
	}
	
	//饼图
	zsdzgl.main.pieCharts = function(legendData,seriesData){
		var winWidth = window.innerWidth;    				//获取窗口的宽
		var pieCharts = echarts.init(zsdzgl.main.chartsDiv);
		var firstRadius = ['0%', '9%'];
		var secondRadius = ['10%','19%'];
		var thirdRadius = ['20%', '58%'];
		var forthRadius = ['64%', '65%'];
		var centerPosition = ['50%', '42%'];
		var optionPie = {
				backgroundColor: 'white',
				title : {
			        text: '',
			        subtext: '',
			        x:'center'
			    },
			    tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {c} "+zsdzgl.main.unitText+" ({d}%)"
			    },
			    legend: {
			        x : 'center',
			        y : 'bottom',
			        itemGap : 10,      //图例间距 
			        textStyle:{
	                	color:'#000',
	              		fontSize:18,
	              		fontWeight:'500'
	          		},
	          		padding : 10,      //图例内边距 
	          		itemHeight : 18,
			        data:legendData,
// 			        height : '8%',		//图例组件的总高
			        width : '75%',
			        top  : '81%',		//距离容器上侧的距离
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
			            name:zsdzgl.main.zsdxText,
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
					             formatter: '{c}'+zsdzgl.main.unitText,
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
		pieCharts.setOption(optionPie);
		zsdzgl.main.chartsNum++;
	}
	
	//单位下拉框初始化
	zsdzgl.main.initOrgCombobox = function(){
		var orgList = orgListManager();
		$("#orgList").combobox({
			valueField : 'id',
			textField : 'name',
			editable : false,
			data : orgList,
			loadFilter : function(data) {
				data.unshift({
					id : '',
					name : '上海市'
				});
				return data;
			},
		});
	}
	
	//处理单位数据
	function orgListManager(){
		var orgList = new Array();
		var orgId = zsdzgl.main.orgList["qyId"];
		var orgName = zsdzgl.main.orgList["qyName"];
		for(var i=0;i<orgId.length;i++){
			var org = new Object();
			org["id"] = orgId[i];
			org["name"] = orgName[i];
			orgList.push(org);
		}
		return orgList;
	}
	
	//选择框监听
	zsdzgl.main.zsdxCheckBox = function(){
		//展示对象选择框控制
		$('#ZsdxDiv :checkbox[type="checkbox"]').each(function(){
            $(this).click(function(){
                if($(this).attr('checked')||$(this).prop('checked')){
                    $('#ZsdxDiv :checkbox[type="checkbox"]').prop('checked',false);
                    $('#ZsdxDiv :checkbox[type="checkbox"]').removeAttr('checked');
                    $(this).prop('checked',true);
                    $(this).attr('checked','checked');
                    //console.log(this.id);
                    $("#yxmxTjwd").css("background-color","rgba(0,0,0,0)");
                	$("#yxyhTjwd").css("background-color","rgba(0,0,0,0)");
                	$("#zjsjTjwd").css("background-color","rgba(0,0,0,0)");
                	$('#TjwdDiv :checkbox[type="checkbox"]').attr("disabled", false);
                    if(this.id=="selectYxmx"){
                    	//影响母线
                    	$("#yxyhTjwd").css("background-color","#c5e4ea");
                    	$("#zjsjTjwd").css("background-color","#c5e4ea");
                    	$('#yxyhTjwd :checkbox[type="checkbox"]').attr("disabled", "disabled");
                    	$('#zjsjTjwd :checkbox[type="checkbox"]').attr("disabled", "disabled");
                    	$('#yxyhTjwd :checkbox[type="checkbox"]').removeAttr('checked');
                    	$('#zjsjTjwd :checkbox[type="checkbox"]').removeAttr('checked');
                    }else if(this.id=="selectYxyh"){
                    	//影响用户
                    	$("#yxmxTjwd").css("background-color","#c5e4ea");
                    	$("#zjsjTjwd").css("background-color","#c5e4ea");
                    	$('#yxmxTjwd :checkbox[type="checkbox"]').attr("disabled", "disabled");
                    	$('#zjsjTjwd :checkbox[type="checkbox"]').attr("disabled", "disabled");
                    	$('#yxmxTjwd :checkbox[type="checkbox"]').removeAttr('checked');
                    	$('#zjsjTjwd :checkbox[type="checkbox"]').removeAttr('checked');
                    }else if(this.id=="selectZjsj"){
                    	//暂降事件
                    	$("#yxmxTjwd").css("background-color","#c5e4ea");
                    	$("#yxyhTjwd").css("background-color","#c5e4ea");
                    	$('#yxmxTjwd :checkbox[type="checkbox"]').attr("disabled", "disabled");
                    	$('#yxyhTjwd :checkbox[type="checkbox"]').attr("disabled", "disabled");
                    	$('#yxmxTjwd :checkbox[type="checkbox"]').removeAttr('checked');
                    	$('#yxyhTjwd :checkbox[type="checkbox"]').removeAttr('checked');
                    }
                }
            });
        });//#c5e4ea
		//展示方式选择框控制
		$('#ZsfsDiv :checkbox[type="checkbox"]').each(function(){
            $(this).click(function(){
            	if($(this).prop('checked')||$(this).attr('checked')){
                    $('#ZsfsDiv :checkbox[type="checkbox"]').prop('checked',false);
                    $('#ZsfsDiv :checkbox[type="checkbox"]').removeAttr('checked');
                    $(this).prop('checked',true);
                    $(this).attr('checked','checked');
                    //console.log(this.id);
                }
            });
        });
		//统计维度选择框控制
		$('#TjwdDiv :checkbox[type="checkbox"]').each(function(){
            $(this).click(function(){
                if($(this).attr('checked')||$(this).prop('checked')){
                	$('#TjwdDiv :checkbox[type="checkbox"]').prop('checked',false);
                	$('#TjwdDiv :checkbox[type="checkbox"]').removeAttr('checked');
                    $(this).prop('checked',true);
                    $(this).attr('checked','checked');
                    //console.log(this.id);
                }
            });
        });
	}
	
	//charts点击事件
	zsdzgl.main.chartsClick = function(){
		var Charts1 = echarts.init(document.getElementById('charts1'));
		var Charts2 = echarts.init(document.getElementById('charts2'));
		Charts1.on('click', function (params) {
			zsdzgl.main.dialogFuc(params.name,zsdzgl.main.charts1Parems);
		});
		Charts2.on('click', function (params) {
			zsdzgl.main.dialogFuc(params.name,zsdzgl.main.charts2Parems);
		});
	}
	//明细页面点击显示
	zsdzgl.main.dialogFuc = function(clickLegend,parems){
		var zsdx = parems["zsdx"];
		var zsfs = parems["zsfs"];
		var org = parems["org"];
		var startDate = parems["startDate"];
		var endDate = parems["endDate"];
		var tjwd = parems["tjwd"];
		var title = parems["title"].split("统计")[1];
		if(tjwd=="fzfw"){
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
			href : "view?legendName=" + clickLegend+ "&zsdx=" + zsdx + "&tjwd=" + tjwd + "&org=" +org + "&startDate=" +startDate + "&endDate=" +endDate,
			title : clickLegend+"-"+title+"详情"
		}).dialog("open");
	}
</script>
</head>

<body>
	<div class="easyui-layout" fit="true" border="false">
		<div region="north" border="false" style="height: 50%;">
			<div class="easyui-layout" fit="true" border="false">
				<div region="west"  border="fasle" style="width: 45%; padding: 20px 10px 10px 50px;background: #e6f4f7;">
					<div id="ZsdxDiv" style="width: 100%;">
						<div class="viewLable" style="float: left;">展示对象:</div>
						<div style="float: left;width: 70%;">
							<div class="viewSelect" style="width: 120px;float: left;">
								<input type="checkbox" name="selectZsdx" id="selectYxmx" value="yxmx" />影响母线
							</div>
							<div class="viewSelect" style="width: 120px;float: left;">
								<input type="checkbox" name="selectZsdx" id="selectYxyh" value="yxyh" />影响用户
							</div>
							<div class="viewSelect" style="width: 120px;float: left;">
								<input type="checkbox" name="selectZsdx" id="selectZjsj" value="zjsj" />暂降事件
							</div>
						</div>
						<div class="clean"></div>
					</div>
					<div id="ZsfsDiv" style="height: 40%;width: 100%;margin-bottom: 25px;">
						<div class="viewLable" style="float: left;margin-top: 80px;">
							展示方式:
						</div>
						<div style="float: left;width: 70%;">
							<div class="viewSelect" style="width: 120px;float: left;height: auto;">
								<div class="barIcon" style="width: 120px;height: 80px;"></div>
								<input type="checkbox" name="selectZsfs" id="selectBar" value="bar" />柱状图
							</div>
							<div class="viewSelect" style="width: 120px;float: left;height: auto;">
								<div class="pieIcon" style="width: 120px;height: 80px;"></div>
								<input type="checkbox" name="selectZsfs" id="selectPie" value="pie" />饼状图
							</div>
							<div class="viewSelect" style="width: 120px;float: left;height: auto;">
								<div class="lineIcon" style="width: 120px;height: 80px;"></div>
								<input type="checkbox" name="selectZsfs" id="selectLine" value="line" />折线图
							</div>
						</div>
						<div class="clean"></div>
					</div>
					<div id="TjtjDiv" style="height: 36%;width: 100%;">
						<div class="viewLable" style="float: left;">统计条件:</div>
						<div style="float: left;width: 500px;">
							<div class="viewSelect" style="float: left;">
								供电单位
								<input id="orgList" class="easyui-combobox" style="width:310px;" />
							</div>
							<div class="viewSelect" style="float: left;letter-spacing:2px;margin-top: 25px;">
								时&nbsp;间&nbsp;段
								<input id="startDate" class="easyui-datebox" style="width:150px;" value="<%=start%>" />
								<span style="font-weight: bold;">-</span>
								<input id="endDate" class="easyui-datebox" style="width:150px;" value="<%=end%>" />
							</div>
						</div>
						<div class="clean"></div>
					</div>
				</div>
				<div region="center" border="false"  style="padding: 20px 20px 10px 10px;background: #e6f4f7;">
					<div class="viewLable" style="float: left;margin-bottom: 25px;">统计维度:</div>
					<div id="TjwdDiv" style="float: left;width: 700px;">
						<div id="ggwdTjwd" style="float: left;width: 300px;margin-left: 15px;">
							<div class="objectTitleDiv">公共维度</div>
							<div class="objectContentDiv">
								<div class="viewSelect" style="float: left;">
									<input type="checkbox" name="selectTjwd" id="selectOrg" value="org" />供电单位
								</div>
								<div class="viewSelect" style="float: left;">
									<input type="checkbox" name="selectTjwd" id="selectYear" value="year" />年度
								</div>
								<div class="viewSelect" style="float: left;">
									<input type="checkbox" name="selectTjwd" id="selectMonth" value="month" />月
								</div>
								<div class="viewSelect" style="float: left;">
									<input type="checkbox" name="selectTjwd" id="selectDay" value="day" />日
								</div>
								<div class="clean"></div>
							</div>
						</div>
						<div id="yxmxTjwd" style="float: left;width: 300px;margin-left: 35px;">
							<div class="objectTitleDiv">影响母线</div>
							<div class="objectContentDiv">
								<div class="viewSelect" style="float: left;">
									<input type="checkbox" name="selectTjwd" id="selectDydj" value="dydj" />电压等级
								</div>
								<div class="viewSelect" style="float: left;">
									<input type="checkbox" name="selectTjwd" id="selectFzfw" value="fzfw" />幅值
								</div>
								<div class="viewSelect" style="float: left;">
									<input type="checkbox" name="selectTjwd" id="selectCfyx" value="cfyx" />重复影响次数
								</div>
								<div class="clean"></div>
							</div>
						</div>
						<div id="yxyhTjwd" style="float: left;width: 300px;margin-left: 15px;margin-top: 25px;">
							<div class="objectTitleDiv">影响用户</div>
							<div class="objectContentDiv">
								<div class="viewSelect" style="float: left;">
									<input type="checkbox" name="selectTjwd" id="selectFhlx" value="fhlx" />负荷类型
								</div>
								<div class="viewSelect" style="float: left;">
									<input type="checkbox" name="selectTjwd" id="selectYdxz" value="ydxz" />用电性质
								</div>
								<div class="clean"></div>
							</div>
						</div>
						<div id="zjsjTjwd" style="float: left;width: 300px;margin-left: 35px;margin-top: 25px;">
							<div class="objectTitleDiv">暂降事件</div>
							<div class="objectContentDiv">
								<div class="viewSelect" style="float: left;">
									<input type="checkbox" name="selectTjwd" id="selectDydj" value="dydj" />电压等级
								</div>
								<div class="viewSelect" style="float: left;">
									<input type="checkbox" name="selectTjwd" id="selectGzlx" value="gzlx" />故障发生原因
								</div>
								<div class="clean"></div>
							</div>
						</div>
					</div>
				</div>
				<div region="south" border="false"  style="height: 70px;background: #e6f4f7;">
					<div class="createBtn" onClick="zsdzgl.main.chartsSearch()">生成</div>
				</div>
			</div>
		</div>
		<div region="center" border="false">
			<div class="easyui-layout" fit="true" border="false">
				<div region="west" border="fasle" style="width: 50%; padding: 10px 10px 20px 20px;background: #e6f4f7;">
					<div class="easyui-layout" fit="true" border="true" id="charts1Div">
						<div region="north" border="true" class="title">
							<span class="titlespan">xxxxxxxxxxx</span>
						</div>
						<div region="center" border="true"  class="content" id="charts1"></div>
					</div>
				</div>
				<div region="center" border="false"  style="padding: 10px 20px 20px 10px;background: #e6f4f7;">
					<div class="easyui-layout" fit="true" border="true" id="charts2Div">
						<div region="north" border="true" class="title">
							<span class="titlespan">xxxxxxxxxxxxxx</span>
						</div>
						<div region="center" border="true"  class="content" id="charts2"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="detailWin" data-options="modal:true,closed:true"></div>