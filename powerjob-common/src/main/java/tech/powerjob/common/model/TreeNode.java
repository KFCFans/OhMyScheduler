package tech.powerjob.common.model;

import lombok.Data;

import java.util.List;

/**
 * @author : wuweihong
 * @desc : TODO  请填写你的功能描述
 * @date : 2024-12-23
 */

@Data
public class TreeNode<T> {

	private Long id;

	private String label;

	private String name;

	private String value;

	private String icon;

	private Long parentId;

	private String parentName;

	private List<TreeNode<T>> children;

}
