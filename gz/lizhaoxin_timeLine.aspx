<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="IndexPage2.aspx.cs" Inherits="SDWebApplication.management.ExpandII.YouHuaJianXiu.IndexPage2" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title></title>
    <script src="../../../plugins/hcharts/jquery-1.8.3.min.js"></script>
    <script src="../../../plugins/echart/js/echarts3/echarts.js"></script>
    <style>
        .unitLabel {
            font-size: 26px;
            margin-left: 36%;
            color: #1E97D0;
            font-family: 'Microsoft YaHei';
        }

        .fillBack {
            background: white;
        }

        .ui-widget-header {
            background: #CCF0FF;
            color: black;
        }

        .f-grid-row-alt {
            background-color: #EBF4F7 !important;
        }

        .f-grid-row {
            background-color: white;
        }

        .f-grid-bodyct {
            background: white;
        }

        .divde_line {
            border-left-style: dashed;
            border-left-width: thin;
        }
        /*toolBar字体靠右*/
        .f-field-fieldlabel {
            text-align: right;
        }

        .detail_div {
            color: #A41017;
            font-size: 20px;
            font-family: fantasy;
            margin-left: 15%;
        }
    </style>
</head>
<body style=" background: #EBF4F7;">
    <form id="form1" runat="server">
        <f:PageManager runat="server" AutoSizePanelID="basePanel"></f:PageManager>
        <f:Panel runat="server" ID="basePanel" ShowHeader="false" ShowBorder="false" Layout="HBox">
            <Toolbars>
                <f:Toolbar ID="tlBar" runat="server">
                    <Items>
                        <f:ToolbarFill runat="server"></f:ToolbarFill>
                         <f:DropDownList runat="server" id="unitDDL" Label="机组" BoxFlex="1" DataTextField="text" DataValueField="value" AutoPostBack="true"
                                        AutoSelectFirstItem="true" ></f:DropDownList>
                        <f:DropDownList runat="server" ID="jxlxDropDownList" Label="检修类型" Required="true" LabelWidth="80"  AutoPostBack="true"></f:DropDownList>
                        <f:DatePicker runat="server" ID="dpStart" Label="开始日期" EnableEdit="true" LabelAlign="right" LabelWidth="80"></f:DatePicker>
                        <f:DatePicker runat="server" ID="dpEnd" Label="结束日期" EnableEdit="true" LabelAlign="right" LabelWidth="80"></f:DatePicker>
                        <f:Button runat="server" ID="btnSaveClose" Icon="SystemSaveClose" Text="重新生成" OnClick="btnSaveClose_Click"></f:Button>
                        <f:Label runat="server" Width="50"></f:Label>
                    </Items>
                </f:Toolbar>
            </Toolbars>
            <Items>
                <%--左边图形--%>
                <f:Panel runat="server" ShowHeader="false" ShowBorder="false" Layout="VBox" BoxFlex="3">
                    <Items>
                        <%-- 柱状图 --%>
                        <f:Panel runat="server" ID="barChartPanel" ShowHeader="false" ShowBorder="false" BoxFlex="7"></f:Panel>
                        <f:Panel runat="server" ShowHeader="false" ShowBorder="false" Layout="Fit" BoxFlex="3">
                            <Items>
                                <f:Grid runat="server" ID="infoGrid" ShowHeader="true" ShowBorder="false" BoxFlex="1" ShowGridHeader="false" 
                                    EnableColumnLines="true" EnableRowLines="true" Title="检修信息" TitleAlign="Center">
                                    <Columns>
                                        <f:BoundField DataField="name" BoxFlex="2   " TextAlign="center"></f:BoundField>
                                        <f:BoundField DataField="value" BoxFlex="1" TextAlign="center"></f:BoundField>
                                    </Columns>
                                </f:Grid>
                            </Items>
                        </f:Panel>
                    </Items>
                </f:Panel>
                <%--时间轴--%>
                <f:Panel runat="server" ID="timeLinePanel" ShowHeader="false" ShowBorder="false" Layout="VBox" BoxFlex="7" CssClass="divde_line"></f:Panel>
            </Items>
        </f:Panel>
    </form>
    <script>
        var timeLinePanelID = '<%=timeLinePanel.ClientID%>';
        var barChartPanelID = '<%=barChartPanel.ClientID%>'
        var timeLineChart = null
        var barChart = null

        function MakeBarChart(data) {
            if (barChart == null) {
                barChart = echarts.init(document.getElementById(barChartPanelID));
            }
            var xAxisDataArray = []
            var seriesDataArray = []

            for (var i = 0; i < data.dataArray.length; i++) {
                var obj = data.dataArray[i]
                xAxisDataArray.push(obj.name)

                var seriesDataObj = {
                    value: obj.value,
                    itemStyle: {
                        normal: {
                            color:obj.color
                        }
                    }
                }
                seriesDataArray.push(seriesDataObj)
            }

            var option = {
                title: {
                    show: true,
                    text: data.title,
                    left: '45%',
                    top:'10'
                },
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                        type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                    }
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: [
                    {
                        type: 'category',
                        data: xAxisDataArray,
                        axisTick: {
                            alignWithLabel: true
                        }
                    }
                ],
                yAxis: [
                    {
                        type: 'value'
                    }
                ],
                series: [
                    {
                        name: '次数',
                        type: 'bar',
                        barWidth: '60%',
                        data: seriesDataArray
                    }
                ]
            }

            barChart.clear()
            barChart.setOption(option)
        }

        function MakeTimeLine(data) {
            if (timeLineChart == null) {
                timeLineChart = echarts.init(document.getElementById(timeLinePanelID));
            }

            if (data.success) {
                var dataArray = [];
                var linksArray = [];
                var recordCount = Number(data.count);
                var chartWidth = $("#" + timeLinePanelID).width();
                var YBase = $("#" + timeLinePanelID).height() / 2;
                var YLong = YBase / 2;
                var YShort = YLong / 1.8;
                var xStap = (chartWidth / data.count) * 0.9 // 相邻节点的在x轴上的间距
                
                for (var i = 0; i < recordCount; i++) {
                    var srcObj = data['record' + i];
                    var dataItem = new Object();
                    var dataTimeItem = new Object();
                    var timeLinkObj = new Object();

                    //某次检修的节点
                    dataItem = {
                        name: 'record' + i,
                        x: xStap * i,
                        y: YBase,
                        briefName: srcObj.briefName,
                        content: srcObj.content,
                        itemStyle: {
                            normal: {
                                color: srcObj.color
                            },
                            emphasis: {
                                color: srcObj.color
                            }
                        },
                        label: {
                            normal: {
                                formatter: function (item) {
                                    var resultStr = item.data.briefName
                                    return resultStr
                                },
                                color: 'black',
                                fontSize:18,
                            }
                        },
                    }

                    var timeItemLoc = GetTimeItemLocationByIndex(i)
                    //某次检修的时间节点
                    dataTimeItem = {
                        name: 'recordTime' + i,
                        x: xStap * i,
                        y: timeItemLoc.y,
                        startTime: srcObj.startTime,
                        endTime: srcObj.endTime,
                        symbolSize: 1,
                        label: {
                            normal: {
                                formatter: function (item) {
                                    var resultStr = item.data.startTime + '\n'
                                    resultStr += item.data.endTime
                                    return resultStr
                                },
                                color: '#6B63D6',
                                fontSize: 20,
                                padding: timeItemLoc.padding
                            }
                        }
                    }

                    dataArray.push(dataItem)
                    dataArray.push(dataTimeItem)

                    //检修节点链接到检修时间节点的连接线对象
                    timeLinkObj = {
                        source: dataItem.name,
                        target: dataTimeItem.name,
                        content: srcObj.content,
                        lineStyle: {
                            normal: {
                                width: 2,
                                color: '#000000'
                            },
                            emphasis: {
                                width: 2,
                                color: '#000000'
                            }
                        },
                        label: {
                            normal: {
                                show:false
                            }
                        },
                    }
                    linksArray.push(timeLinkObj)

                    if (i != 0) {
                        //如果当前节点不是第一个节点，则将此节点与上一节点连接起来
                        var itemLinkObj = {
                            source: 'record' + (i - 1),
                            target: 'record' + i,
                            timeSpan:srcObj.timeSpan,
                            lineStyle: {
                                normal: {
                                    width: 5,
                                    color: '#3BD1FF',
                                }
                            },
                            label: {
                                normal: {
                                    show:true,
                                    formatter: function (item) {
                                        var resultStr = item.data.timeSpan
                                        return resultStr
                                    },
                                    color: '#0D55E2',
                                    padding: [0, 0, 15, 0],
                                    fontSize:14,
                                }
                            }
                        }
                        linksArray.push(itemLinkObj)
                    }
                }

                if (recordCount > 1) {
                    //如果记录数大于0，则在最后补上一个箭头
                    var lastDataItem = {
                        name: 'record' + recordCount,
                        x: xStap * recordCount,
                        y: YBase,
                        symbolSize: 0,
                    }
                    dataArray.push(lastDataItem)

                    var lastLinkItem = {
                        source: 'record' + (recordCount - 1),
                        target: 'record' + recordCount,
                        lineStyle: {
                            normal: {
                                width: 5,
                                color: '#3BD1FF',
                            }
                        },
                        symbol: ["circle", "arrow"],
                        symbolSize: [1, 20]
                    }
                    linksArray.push(lastLinkItem)
                }

                option = {
                    tooltip: {
                        formatter: function (item) {
                            var resultStr = item.data.content
                            return resultStr
                        }
                    },
                    series: [
                        {
                            height: 400,
                            type: 'graph',
                            layout: 'none',
                            focusNodeAdjacency: false,
                            roam: true,
                            symbolSize: 30,
                            label: {
                                normal: {
                                    show: true
                                }
                            },
                            data: dataArray,
                            links: linksArray
                        }
                    ]
                };
                console.log(dataArray);
                console.log(linksArray);

                timeLineChart.clear();
                timeLineChart.setOption(option);
            }

            function GetTimeItemLocationByIndex(index) {
                switch (index % 4) {
                    case 0: return { y: YBase + YLong, padding: [40, 0, 0, 0] };
                    case 1: return { y: YBase - YLong, padding: [0, 0, 40, 0] };
                    case 2: return { y: YBase + YShort, padding: [40, 0, 0, 0] };
                    case 3: return { y: YBase - YShort, padding: [0, 0, 40, 0] };
                    default: return { y: YBase + YLong, padding: [40, 0, 0, 0] };
                }
            }
        }

    </script>
</body>
</html>
