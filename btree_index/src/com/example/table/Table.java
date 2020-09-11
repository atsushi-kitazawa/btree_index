package com.example.table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.btree.index.IndexGenerator;
import com.example.btree.index.IndexNode;

public class Table {

	private List<Data> datas;
	private IndexNode index;

	public Table create(List<Data> d) {
		this.datas = new ArrayList<>(d);
		return this;
	}

	// single select only
	public Data select(Data d) {
		if (index == null) {
			Data ret = datas.stream().filter(data -> data.equals(d)).findFirst().orElse(null);
			return ret;
		}
		Data val = index.getValueNode(d.getId());
		return val;
	}

	// single select only
	public Data selectId(int id) {
		if (index == null) {
			Data ret = datas.stream().filter(data -> data.getId().equals(id)).findFirst().get();
			return ret;
		}
		Data val = index.getValueNode(id);
		return val;
	}

	public List<Data> selectAll() {
		return datas;
	}

	public void insert(Data d) {
		if (!datas.contains(d)) {
			datas.add(d);
			new IndexGenerator().update(index, d.getId(), d);
		} else {
			System.err.println("insert error reason duplicate.");
		}
	}

	public void update(Data d) {

	}

	public void delete(Data d) {

	}

	public int size() {
		return datas.size();
	}

	public void createIndex() {
		index = new IndexGenerator().generate(this);
	}

	public IndexNode getIndex() {
		return index;
	}

	@Override
	public String toString() {
		return "Table [" + datas + "]";
	}

	public Table testCreate(int size) {
		Table t = new Table();
		List<Data> datas = new ArrayList<>();
		for (int i = 1; i <= size; i++) {
			datas.add(new Person(i, "aaa" + i));
		}
		Collections.shuffle(datas);
		t.create(datas);
		return t;
	}

	public static void main(String[] args) {
		Table t = new Table();
		List<Data> datas = new ArrayList<>();
		for (int i = 1; i <= 10; i++) {
			datas.add(new Person(i, "aaa" + i));
		}
		for (int i = 12; i <= 20; i++) {
			datas.add(new Person(i, "aaa" + i));
		}
		t.create(datas);
		t.createIndex();
		System.out.println(t);
		System.out.println(t.getIndex());

//		Data p = t.select(new Person(1, "aaa1"));
//		System.out.println(p);
//		p = t.select(new Person(1, "aaA"));
//		System.out.println(p);

		t.insert(new Person(21, "aaa21"));
		t.insert(new Person(11, "aaa11"));
		System.out.println(t);
		System.out.println(t.getIndex());
//		t.insert(new Person(11, "aaa11"));
//		System.out.println(t);
	}
}
