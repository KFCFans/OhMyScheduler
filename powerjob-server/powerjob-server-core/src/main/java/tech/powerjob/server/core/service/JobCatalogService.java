package tech.powerjob.server.core.service;

import tech.powerjob.common.model.TreeNode;
import tech.powerjob.server.persistence.remote.model.JobCatalogDO;

import java.util.Collection;

/**
 * @author : wuweihong
 * @desc : TODO  请填写你的功能描述
 * @date : 2024-12-23
 */


public interface JobCatalogService {

	Long saveCatalog(JobCatalogDO jobCatalogDO);

	void delete(Collection<Long> catalogIds);

	TreeNode<JobCatalogDO> getCatalogTree();

}
