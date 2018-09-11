package com.baidu.ueditor.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.giant.zzidc.base.utils.FileUploadUtil;

/**
 * [百度编辑器上传cupian ]
 * @author LiYatao
 * @date 2018年9月6日 上午11:47:51
 * @company Gainet
 * @version 1.0
 * @copyright copyright (c) 2018
 */
@Controller
@RequestMapping(value = "/ueditor")
public class UeditorController {
	
	/**
	 * [上传百度编辑器中的文件到云存储] <br>
	 * @author LiYatao <br>
	 * @date 2018年9月6日 上午11:51:27 <br>
	 * @param request
	 * @return <br>
	 */
	@RequestMapping(value = "/upload")
	@ResponseBody
    public Object config(HttpServletRequest request) {
        return FileUploadUtil.uploadImage(request);
    }

}
