package tech.powerjob.server.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.powerjob.common.model.TreeNode;
import tech.powerjob.common.response.ResultDTO;
import tech.powerjob.server.auth.Permission;
import tech.powerjob.server.auth.RoleScope;
import tech.powerjob.server.auth.interceptor.ApiPermission;
import tech.powerjob.server.core.service.JobCatalogService;
import tech.powerjob.server.persistence.remote.model.JobCatalogDO;

import javax.annotation.Resource;
import java.util.Collections;

/**
 * @author : wuweihong
 * @desc : TODO  请填写你的功能描述
 * @date : 2024-12-23
 */


@Slf4j
@RestController
@RequestMapping("/job/catalog")
public class JobCatalogController {

	@Resource
	private JobCatalogService jobCatalogService;

	@PostMapping("/save")
	@ApiPermission(name = "JobCatalog-Save", roleScope = RoleScope.APP, requiredPermission = Permission.WRITE)
	public ResultDTO<Void> saveJobInfo(@RequestBody JobCatalogDO request) {
		jobCatalogService.saveCatalog(request);
		return ResultDTO.success(null);
	}

	@GetMapping("/delete")
	@ApiPermission(name = "JobCatalog-Delete", roleScope = RoleScope.APP, requiredPermission = Permission.WRITE)
	public ResultDTO<Void> deleteJob(String jobId) {
		jobCatalogService.delete(Collections.singletonList(Long.valueOf(jobId)));
		return ResultDTO.success(null);
	}

	@GetMapping("/getTree")
	public ResultDTO<TreeNode<JobCatalogDO>> getTree() {
		return ResultDTO.success(jobCatalogService.getCatalogTree());
	}

}
