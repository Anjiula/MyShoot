package cn.com.liandisys.dwxb.web.fxfxgl.zsdzgl;

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
import cn.com.liandisys.dwxb.service.fxfxgl.zsdzgl.ZsdzglService;

/**
 * 展示定制管理controller
 * 
 * @author fangzheng
 *
 */
@Controller
@RequestMapping(value = { "/fxfxgl/zsdzgl/" })
public class ZsdzglController extends BaseController {
	
	/**
	 * 服务
	 */
	@Autowired
	private ZsdzglService zsdzglService;
	
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
    	//获取Const定义的区域id用于页面展示(按顺序)
		Map<String, Object> qyList = qyfxNdfxService.findQyIdList();
    	model.addAttribute("orgList", convertToJsonData(qyList));
        return "fxfxgl/zsdzgl/main";
    }
    
    /**
     * charts数据定制查询
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
			@RequestParam(value = "viewObject", required = false, defaultValue = "") String viewObject,
			@RequestParam(value = "viewTJWD", required = false, defaultValue = "") String viewTJWD,
			@RequestParam(value = "viewOrg", required = false, defaultValue = "") String viewOrg,
			@RequestParam(value = "viewStartTime", required = false, defaultValue = "") String viewStartTime,
			@RequestParam(value = "viewEndTime", required = false, defaultValue = "") String viewEndTime) {
    	Map<String, Object> json = new HashMap<String, Object>();
    	try{
    		Map<String, Object> charts = zsdzglService.findChartsDataByTerm(viewObject, viewTJWD, viewOrg, viewStartTime, viewEndTime);
    		json.put("charts", charts);
    		json.put("isSuccess", true);
    	}catch(Exception ex){
    		json.put("isSuccess", false);
    	}
		return convertToJsonData(json);
	}
    
    /**
     * 详细页面数据获取
     * @return
     */
	@RequestMapping(value = "view")
	public String view(Model model, 
			@RequestParam(value = "legendName", required = false, defaultValue = "") String legendName,
			@RequestParam(value = "zsdx", required = false, defaultValue = "") String zsdx,
			@RequestParam(value = "tjwd", required = false, defaultValue = "") String tjwd,
			@RequestParam(value = "org", required = false, defaultValue = "") String org,
			@RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
			@RequestParam(value = "endDate", required = false, defaultValue = "") String endDate){

		model.addAttribute("legendName",legendName);
		model.addAttribute("zsdx",zsdx);
		model.addAttribute("tjwd",tjwd);
		model.addAttribute("org",org);
		model.addAttribute("startDate",startDate);
		model.addAttribute("endDate",endDate);
		Map<String, Object> qyList = qyfxNdfxService.findQyIdList();
		model.addAttribute("qyIdList",convertToJsonData(qyList.get("qyId")));
		model.addAttribute("qyNameList",convertToJsonData(qyList.get("qyName")));
		return "fxfxgl/zsdzgl/detail";
	}
	
	/**
	 * 获取表格数据
	 * @return
	 */
	@RequestMapping(value = "getDetail")
    @ResponseBody
    public String getDetail(Model model,
			@ModelAttribute("pageRequest") PageRequest pageRequest,
			@RequestParam(value = "legendName", required = false, defaultValue = "") String legendName,
			@RequestParam(value = "zsdx", required = false, defaultValue = "") String zsdx,
			@RequestParam(value = "tjwd", required = false, defaultValue = "") String tjwd,
			@RequestParam(value = "org", required = false, defaultValue = "") String org,
			@RequestParam(value = "startDate", required = false, defaultValue = "") String startDate,
			@RequestParam(value = "endDate", required = false, defaultValue = "") String endDate){
    	List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
    	//获取Const定义的区域id用于页面展示(按顺序)
    			Map<String, Object> qyList = qyfxNdfxService.findQyIdList();
    	data = zsdzglService.findDetailData(legendName, zsdx, tjwd, org,startDate,endDate,qyList);
    	
    	return convertToJsonData(data);
    }

}
