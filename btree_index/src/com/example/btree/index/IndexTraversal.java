package com.example.btree.index;

import java.util.List;

import com.example.table.Data;

public class IndexTraversal {

	public Data getValueNode(IndexNode node, int id) {
		Data ret = null;
		List<Integer> nodeId = node.getId();
		List<IndexNode> nodeNode = node.getNode();
		List<Data> nodeValue = node.getValue();

		// leaf node.
		if (!nodeValue.isEmpty()) {
			for (int i = 0; i < nodeId.size(); i++) {
				if (node.getId().get(i) == id) {
					ret = nodeValue.get(i);
				}
			}
		} else { // branch or root node.
			for (int i = 0; i < nodeId.size(); i++) {
				int tmp = nodeId.get(i);
				if (tmp >= id) {
					IndexNode n = nodeNode.get(i);
					ret = getValueNode(n, id);
					break;
				}
			}
		}
		return ret;
	}

	public IndexNode getUpdateLeafNode(IndexNode node, int id) {
		IndexNode ret = null;
		List<Integer> nodeId = node.getId();
		List<IndexNode> nodeNode = node.getNode();
		List<Data> nodeValue = node.getValue();

		// leaf node.
		if (!nodeValue.isEmpty()) {
			ret = node;
		} else { // branch or root node.
			for (int i = 0; i < nodeId.size(); i++) {
				int tmp = nodeId.get(i);
				// [tmp >= id] is get node from current index.
				// [i == nodeId.size() - 1] is get node from nodeId last index.(case with update id is maxmun id)
				if (tmp >= id || i == nodeId.size() - 1) {
					IndexNode n = nodeNode.get(i);
					ret = getUpdateLeafNode(n, id);
					break;
				}
			}
		}
		return ret;
	}
}
