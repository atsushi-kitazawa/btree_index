package com.example.btree.index;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.example.table.Data;
import com.example.table.Table;

public class IndexGenerator {

	private static final int INITIAL_SIZE = 5;

	public IndexNode generate(Table table) {
		/** create leaf node */
		int loop = (table.size() + INITIAL_SIZE - 1) / INITIAL_SIZE;
		// datas sort
		List<Data> datas = table.selectAll();
		datas = datas.stream().sorted(Comparator.comparing(Data::getId)).collect(Collectors.toList());
		int tmp = 0;
		List<IndexNode> leafHolder = new ArrayList<>();
		for (int i = 0; i < loop; i++) {
			IndexNode leafNode = new IndexNode();
			for (int j = 0; j < INITIAL_SIZE; j++) {
				// FIXME
				try {
					leafNode.addId(datas.get(tmp).getId());
					leafNode.addValue(datas.get(tmp));
					tmp++;
				} catch (RuntimeException e) {
				}
			}
			leafHolder.add(leafNode);
		}
		// setting next node.
		setNextNode(leafHolder);
		// DEBUG
		// System.out.println("=== leaf ===");
		// System.out.println(leafNodes);

		/** create branch and root node */
		List<IndexNode> branchHolder = createBranchNode(leafHolder);
		while (true) {
			List<IndexNode> ret = createBranchNode(branchHolder);
			if (!ret.isEmpty())
				branchHolder = ret;
			else
				// when ret is empty, branchHolder have rootNode.
				break;
		}
		IndexNode rootNode = branchHolder.get(0);
		// DEBUG
		// System.out.println("=== root ===");
		// System.out.println(rootNode);

		return rootNode;
	}

	public void update(IndexNode root, int id, Data d) {
		// get leaf node update target.
		IndexNode leaf = new IndexTraversal().getUpdateLeafNode(root, id);
		List<Integer> ids = leaf.getId();
		ids.add(id);
		Collections.sort(ids);
		List<Data> datas = leaf.getValue();
		datas.add(d);
		leaf.setValue(datas.stream().sorted(Comparator.comparing(Data::getId)).collect(Collectors.toList()));

		// update parent id list.
		updateParentMaxId(leaf, id);
	}

	private List<IndexNode> createBranchNode(List<IndexNode> nodes) {
		if (nodes.size() == 1) {
			// this is root node.
			return new ArrayList<>();
		}
		int parentCnt = (nodes.size() + INITIAL_SIZE - 1) / INITIAL_SIZE;
		int tmp = 0;
		List<IndexNode> branchNodes = new ArrayList<>();
		for (int i = 0; i < parentCnt; i++) {
			IndexNode parentNode = new IndexNode();
			for (int j = 0; j < INITIAL_SIZE; j++) {
				// FIXME
				try {
					parentNode.addId(nodes.get(tmp).getMaxId());
					parentNode.addNode(nodes.get(tmp));
					nodes.get(tmp).setParentNode(parentNode);
					tmp++;
				} catch (RuntimeException e) {
				}
			}
			branchNodes.add(parentNode);
		}
		// DEBUG
		// System.out.println("=== branch ===");
		// System.out.println(branchNodes);

		// setting next node.
		setNextNode(branchNodes);

		return branchNodes;
	}

	private void setNextNode(List<IndexNode> nodes) {
		for (int i = 0; i < nodes.size(); i++) {
			if (i == nodes.size() - 1) {
				break;
			}
			IndexNode leaf = nodes.get(i);
			IndexNode nextLeaf = nodes.get(i + 1);
			leaf.setNextNode(nextLeaf);
		}
	}

	private void updateParentMaxId(IndexNode child, int id) {
		if (child.getMaxId() == id && child.getParentNode() != null) {
			IndexNode parent = child.getParentNode();
			int save = 0;
			for (int i = 0; i < parent.getNode().size(); i++) {
				IndexNode node = parent.getNode().get(i);
				if (node.equals(child)) {
					save = i;
					break;
				}
			}
			parent.getId().set(save, id);
			updateParentMaxId(parent, id);
		} else {
			// id is not maxId or node do not have parent node(is root node).
		}
	}
}
