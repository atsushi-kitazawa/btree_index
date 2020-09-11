package com.example.btree.index;

import java.util.ArrayList;
import java.util.List;

import com.example.table.Data;

public class IndexNode {

	public final int SIZE = 10;
	List<Integer> id = new ArrayList<>();
	List<IndexNode> node = new ArrayList<>(); // use other than leaf node.
	List<Data> value = new ArrayList<>(); // use only leaf node.
	IndexNode nextNode = null;
	IndexNode parentNode = null;

	public List<Integer> getId() {
		return this.id;
	}

	public void addId(Integer id) {
		this.id.add(id);
	}

	public void addIds(List<Integer> ids) {
		this.id.addAll(ids);
	}

	public int getMaxId() {
		int max = 0;
		for (int n : id) {
			if (max < n)
				max = n;
		}
		return max;
	}

	public List<IndexNode> getNode() {
		return this.node;
	}

	public List<Data> getValue() {
		return this.value;
	}

	public void setValue(List<Data> d) {
		this.value = d;
	}

	public void addNode(IndexNode n) {
		this.node.add(n);
	}

	public void addValue(Data d) {
		this.value.add(d);
	}

	public void setNextNode(IndexNode n) {
		this.nextNode = n;
	}

	public IndexNode getParentNode() {
		return this.parentNode;
	}

	public void setParentNode(IndexNode n) {
		this.parentNode = n;
	}

	public Data getValueNode(int id) {
		return new IndexTraversal().getValueNode(this, id);
	}

	@Override
	public String toString() {
		return "Node [id=" + id + "]";
	}

}
