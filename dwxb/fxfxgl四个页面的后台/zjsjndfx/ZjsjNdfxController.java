package cn.com.liandisys.dwxb.web.fxfxgl.zjsjndfx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.liandisys.dwxb.common.web.BaseController;
import cn.com.liandisys.dwxb.service.fxfxgl.qyfxndfx.QyfxNdfxService;
import cn.com.liandisys.dwxb.service.fxfxgl.zjsjndfx.ZjsjNdfxService;

/**
 *暂降事件年度分析controller
 * 
 * @author fangzheng
 *
 */
@Controller
@RequestMapping(value = { "/fxfxgl/zjsjndfx/" })
public class ZjsjNdfxController extends BaseController {
	
	/**
	 * 服务
	 */
	@Autowired
	private ZjsjNdfxService zjsjNdfxService;
	
	@Autowired
	private QyfxNdfxService qyfxNdfxService;
	
    /**
     * 页面初始化
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "list")
    public String list(Model model) {

        return "fxfxgl/zjsjndfx/main";
    }

    /**
     * charts数据按年查询
     * @param model
     * @param pageRequest
     * @param year
     * @return
     */
    @RequestMapping(value = "search")
    @ResponseBody
    public String search(
			Model model,
			@ModelAttribute("pageRequest") PageRequest pageRequest,
			@RequestParam(value = "year", required = false, defaultValue = "") String year) {
    	Map<String, Object> json = new HashMap<String, Object>();
    	try{
    		//获取Const定义的区域id用于页面展示(按顺序)
    		Map<String, Object> qyList = qyfxNdfxService.findQyIdList();
    		//按电压等级统计暂降事件发生次数-饼图数据获取
    		Map<String, Object> charts1 = zjsjNdfxService.findChartsData_charts1(year);
    		//按发生原因统计暂降事件发生次数-饼图数据获取
    		Map<String, Object> charts2 = zjsjNdfxService.findChartsData_charts2(year);
    		//按供电区域统计暂降事件发生次数-柱状图数据获取
    		List<Map<String, Object>> charts3 = zjsjNdfxService.findChartsData_charts3(year);
    		//按月份统计暂降事件发生次数
    		Map<String, Object> charts4 = zjsjNdfxService.findChartsData_charts4(year);
    		json.put("charts1", charts1);
    		json.put("charts2", charts2);
    		json.put("charts3", charts3);
    		json.put("charts4", charts4);
    		json.put("qyList", qyList);
    		json.put("isSuccess", true);
    	}catch(Exception ex){
    		json.put("isSuccess", false);
    	}
    	return convertToJsonData(json);
	}
    
    /**
     * 详细页面数据获取
     * @param model
     * @param eventId
     * @param fhlx
     * @param ydxz
     * @param vlty
     * @param fzfw
     * @return
     */
	@RequestMapping(value = "view")
	public String view(Model model, 
			@RequestParam(value = "legendName", required = false, defaultValue = "") String legendName,
			@RequestParam(value = "zsdx", required = false, defaultValue = "") String zsdx,
			@RequestParam(value = "tjwd", required = false, defaultValue = "") String tjwd,
			@RequestParam(value = "year", required = false, defaultValue = "") String year){

		model.addAttribute("legendName",legendName);
		model.addAttribute("zsdx",zsdx);
		model.addAttribute("tjwd",tjwd);
		model.addAttribute("year",year);
		Map<String, Object> qyList = qyfxNdfxService.findQyIdList();
		model.addAttribute("qyIdList",convertToJsonData(qyList.get("qyId")));
		model.addAttribute("qyNameList",convertToJsonData(qyList.get("qyName")));
		return "fxfxgl/zjsjndfx/detail";
	}
	
	/**
	 * 获取表格数据
	 * @param model
	 * @param pageRequest
	 * @param legendName
	 * @param zsdx
	 * @param tjwd
	 * @param year
	 * @return
	 */
	@RequestMapping(value = "getDetail")
    @ResponseBody
    public String getBusDetail(Model model,
			@ModelAttribute("pageRequest") PageRequest pageRequest,
			@RequestParam(value = "legendName", required = false, defaultValue = "") String legendName,
			@RequestParam(value = "zsdx", required = false, defaultValue = "") String zsdx,
			@RequestParam(value = "tjwd", required = false, defaultValue = "") String tjwd,
			@RequestParam(value = "year", required = false, defaultValue = "") String year){
    	List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
    	//获取Const定义的区域id用于页面展示(按顺序)
		Map<String, Object> qyList = qyfxNdfxService.findQyIdList();
    	data = zjsjNdfxService.findDetailData(legendName, zsdx, tjwd, year, qyList);
    	
    	return convertToJsonData(data);
    }
    
}
