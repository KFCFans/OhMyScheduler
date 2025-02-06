package tech.powerjob.server.core.service.impl.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.powerjob.common.model.TreeNode;
import tech.powerjob.common.utils.CollectionUtils;
import tech.powerjob.server.core.service.JobCatalogService;
import tech.powerjob.server.persistence.remote.model.JobCatalogDO;
import tech.powerjob.server.persistence.remote.repository.JobCatalogRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author : wuweihong
 * @desc : TODO  请填写你的功能描述
 * @date : 2024-12-23
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class JobCatalogServiceImpl implements JobCatalogService {

	public static final String NODE_ICON = "el-icon-folder";

	private final JobCatalogRepository jobCatalogRepository;


	@Override
	public Long saveCatalog(JobCatalogDO jobCatalogDO) {
		jobCatalogRepository.save(jobCatalogDO);
		String catalogPath;
		if (Objects.isNull(jobCatalogDO.getParentId())) {
			catalogPath = jobCatalogDO.getId().toString();
		} else {
			JobCatalogDO parentDO = jobCatalogRepository.findById(jobCatalogDO.getParentId()).get();
			catalogPath = parentDO.getCatalogPath() + " " + jobCatalogDO.getId().toString();
		}
		jobCatalogDO.setCatalogPath(catalogPath);
		jobCatalogRepository.save(jobCatalogDO);
		return jobCatalogDO.getId();
	}

	@Override
	public void delete(Collection<Long> catalogIds) {
		jobCatalogRepository.deleteAllByIdInBatch(catalogIds);
	}

	@Override
	public TreeNode<JobCatalogDO> getCatalogTree() {
		List<JobCatalogDO> jobCatalogDOList = jobCatalogRepository.findAll();
		TreeNode<JobCatalogDO> root = new TreeNode<>();
		root.setLabel("根目录");
		root.setName("根目录");
		root.setChildren(new ArrayList<>());
		generateTree(root, jobCatalogDOList);
		return root;
	}

	public void generateTree(TreeNode<JobCatalogDO> root, List<JobCatalogDO> jobCatalogDOList) {
		List<JobCatalogDO> subCatalogs = jobCatalogDOList.stream()
				.filter(item -> (Objects.isNull(item.getParentId()) && Objects.isNull(root.getId())) || (Objects.nonNull(item.getParentId()) && item.getParentId().equals(root.getId()))).collect(Collectors.toList());
		if (CollectionUtils.isEmpty(subCatalogs)) {
			return;
		}
		for (JobCatalogDO jobCatalogDO : subCatalogs) {
			TreeNode<JobCatalogDO> treeNode = new TreeNode<>();
			treeNode.setId(jobCatalogDO.getId());
			treeNode.setLabel(jobCatalogDO.getName());
			treeNode.setName(jobCatalogDO.getName());
			treeNode.setParentId(jobCatalogDO.getParentId());
			treeNode.setParentName(root.getName());
			treeNode.setIcon(NODE_ICON);
			treeNode.setValue(jobCatalogDO.getCatalogPath());
			treeNode.setChildren(new ArrayList<>());
			generateTree(treeNode, jobCatalogDOList);
			root.getChildren().add(treeNode);
		}
	}

}
