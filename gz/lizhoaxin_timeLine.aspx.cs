using Business.DAO;
using FineUIPro;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Utils;

namespace SDWebApplication.management.ExpandII.YouHuaJianXiu
{
    public partial class IndexPage2 : PageBase
    {
        protected Dictionary<string, object> dic = null;

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)//&& "getTimeLineData".Equals(Request.Params["action"])
            {
                LoadDat();
            }
        }

        private void LoadDat()
        {
            dpStart.Text = DateTime.Now.AddYears(-5).ToString("yyyy-MM-dd");
            BindUnitDDL();
            LoadData();
        }
        private void BindUnitDDL()
        {
            string[] unitArray = Session["p_unit"] as string[];
            DataTable table = new DataTable();
            table.Columns.Add("text");
            table.Columns.Add("value");
            if (unitArray != null && unitArray.Length > 0)
            {
                DataRow row;
                for (int i = 0; i < unitArray.Length; i++)
                {
                    row = table.NewRow();
                    row["text"] = string.Format("#{0}机组", (i + 1).ToString());
                    row["value"] = unitArray[i];
                    table.Rows.Add(row);
                }

                this.unitDDL.DataSource = table;
                this.unitDDL.DataBind();
            }
            string unitId = GetQueryValue("unitId");
            if (!string.IsNullOrWhiteSpace(unitId))
            {
                this.unitDDL.SelectedValue = unitId;
            }
            if ("54" == unitId)
            {
                this.unitDDL.SelectedValue = unitId;
            }

            jxlxDropDownList.Items.Clear();
            jxlxDropDownList.Items.Add("全部", "");
            jxlxDropDownList.Items.Add(DataDic.jxlxAxName, DataDic.jxlxAxVal);
            jxlxDropDownList.Items.Add(DataDic.jxlxBxName, DataDic.jxlxBxVal);
            jxlxDropDownList.Items.Add(DataDic.jxlxCxName, DataDic.jxlxCxVal);
            jxlxDropDownList.Items.Add(DataDic.jxlxDxName, DataDic.jxlxDxVal);
        }
        private void LoadData()
        {

            string unitID = this.unitDDL.SelectedValue;
            string startTime = this.dpStart.Text.Trim();
            string endTime = this.dpEnd.Text.Trim();
            string jxlx = this.jxlxDropDownList.SelectedItem.Value.Trim();
            string plantId = Session["p_plant"].ToString();
            Dictionary<string, object> paramDic = new Dictionary<string, object>();
            paramDic["plantId"] = plantId;
            paramDic["unitId"] = unitID;
            paramDic["kssjBt"] = startTime;
            paramDic["jssjLt"] = endTime;
            paramDic["jxlx"] = jxlx;
            DataTable dt = MaintenanceManagementDao.Instance.GetObjects(paramDic, null, null);
            dic = new Dictionary<string, object>();
            dic["success"] = false;

            if (dt != null && dt.Rows.Count > 0)
            {

                int dx = 0, xx = 0, jjx = 0, number = 0,xiaoxiu = 0,xiaoxiuB = 0;
                dic["success"] = true;
                dic["count"] = dt.Rows.Count;

                foreach (DataRow row in dt.Rows)
                {
                    Dictionary<string, object> detail = new Dictionary<string, object>();

                    detail["overhaulTypeName"] = row["LXNAME"].ToString();
                    detail["startTime"] = row["KSSJ"].ToString().Replace("/", ".");
                    detail["endTime"] = row["JSSJ"].ToString().Replace("/", ".");
                    detail["overhaulTypeCode"] = row["LX"].ToString();
                    detail["content"] = row["NR"].ToString().Replace("\r\n", "").Replace(";", ";<br/>").Replace("；", "；<br/>");

                    if (number != 0)
                    {
                        //计算时间间隔
                        DataRow rowTem = dt.Rows[number - 1];
                        string sjjg_ks = rowTem["JSSJ"].ToString().Replace("/", "-");
                        string sjjg_js = row["KSSJ"].ToString().Replace("/", "-");

                        DateTime dtkssj = Convert.ToDateTime(sjjg_ks);
                        DateTime dtjssj = Convert.ToDateTime(sjjg_js);

                        string sjjg = DateDiff(dtkssj, dtjssj);
                        detail["timeSpan"] = sjjg;
                    }
                    
                    //计算各种检修类型的次数
                    switch (row["LX"].ToString())
                    {
                        case "0"://投产
                            detail["briefName"] = "投";
                            detail["color"] = "#00EE00";
                            break;
                        case "1"://大修
                            dx++;
                            detail["briefName"] = "大";
                            detail["color"] = "#EE0000";
                            break;
                        case "2": //小修(B)
                            xx++;
                            xiaoxiuB++;
                            detail["briefName"] = "小(B)";
                            detail["color"] = "#FFA54F";
                            break;
                        case "3": //小修
                            detail["briefName"] = "小";
                            detail["color"] = "#EEEE00";
                            xx++;
                            xiaoxiu++;
                            break;
                        case "4"://季节性检修
                            detail["briefName"] = "季";
                            detail["color"] = "#FFE7BA";
                            jjx++;
                            break;
                    }

                    dic.Add("record" + number, detail);
                    number++;
                }
                //add by jujun=======================================================start
                DataTable leftDt = new DataTable();

                leftDt.Columns.Add("name");
                leftDt.Columns.Add("value");

                DataRow row1 = leftDt.NewRow();
                row1["name"] = "开始时间:";

                DataRow row2 = leftDt.NewRow();
                row2["name"] = "大修:";

                DataRow row3 = leftDt.NewRow();
                row3["name"] = "小修:";

                DataRow row4 = leftDt.NewRow();
                row4["name"] = "季节性检修:";

                DataRow row5 = leftDt.NewRow();
                row5["name"] = "振动区累计运行时间占比:";


                if (startTime == "")
                {
                    row1["value"] = "投产至今";
                }
                else
                {
                    row1["value"] = startTime;
                }
                row2["value"] = dx + "次";
                row3["value"] = xx + "次";
                row4["value"] = jjx + "次";
                row5["value"] = dx + "%";

                leftDt.Rows.Add(row1);
                leftDt.Rows.Add(row2);
                leftDt.Rows.Add(row3);
                leftDt.Rows.Add(row4);
                leftDt.Rows.Add(row5);

                infoGrid.DataSource = leftDt;
                infoGrid.DataBind();

                Dictionary<string, object> barDic = new Dictionary<string, object>();
                List<Dictionary<string, object>> dataList = new List<Dictionary<string, object>>();
                dataList.Add(new Dictionary<string, object> { { "name", "大修" }, { "value", dx }, { "color", "#EE0000" } });
                dataList.Add(new Dictionary<string, object> { { "name", "小修(B)" }, { "value", xiaoxiuB }, { "color", "#FFA54F" } });
                dataList.Add(new Dictionary<string, object> { { "name", "小修" }, { "value", xiaoxiu }, { "color", "#EEEE00" } });
                dataList.Add(new Dictionary<string, object> { { "name", "季节性检修" }, { "value", jjx }, { "color", "#FFE7BA" } });
                barDic["title"] = unitDDL.SelectedText;
                barDic["dataArray"] = dataList;
                barDic["dataArray"] = dataList;

                var timeLineJson = JsonHelper.ToJson(dic);
                var barJson = JsonHelper.ToJson(barDic);
                PageContext.RegisterStartupScript(string.Format("MakeTimeLine({0});MakeBarChart({1});", timeLineJson, barJson));
                //add by jujun=======================================================end
            }
            else
            {
                Alert.Show("查询无结果");
            }
        }

        //计算时间间隔
        private string DateDiff(DateTime DateTime1, DateTime DateTime2)
        {
            string dateDiff = null;
            TimeSpan ts1 = new TimeSpan(DateTime1.Ticks);
            TimeSpan ts2 = new TimeSpan(DateTime2.Ticks);
            TimeSpan ts = ts1.Subtract(ts2).Duration();
            //dateDiff = ts.Days.ToString() + "天" + ts.Hours.ToString() + "小时" + ts.Minutes.ToString() + "分钟" + ts.Seconds.ToString() + "秒";
            dateDiff = ts.Days.ToString() + "天";
            return dateDiff;
        }
        protected void btnSaveClose_Click(object sender, EventArgs e)
        {
            ////AJAX后，日期控件取不到值。此处做缓存
            //kssjDate = kssjDatePicker.Text.Trim();
            //jssjDate = jssjDatePicker.Text.Trim();
            LoadData();
            //PageContext.RegisterStartupScript("timeLineAjax();");
        }
    }
}