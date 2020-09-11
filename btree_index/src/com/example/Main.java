package com.example;

import com.example.table.Data;
import com.example.table.Table;

public class Main {
	private static final int TABLE_SIZE = 500;

	public static void main(String[] args) {

		Table t = create(true);
		Table t_noindex = create(false);

		for (int i = 0; i < 1; i++) {
			t.selectId(33);
		}

		// index table time.
		long start = System.nanoTime();
		Data ret = null;
		for (int i = 0; i < 10000; i++) {
			ret = t.selectId(100);
		}
		long end = System.nanoTime();
		System.out.println(end - start + "[" + ret + "]");

		// no index table time.
		long start_noindex = System.nanoTime();
		Data ret_noindex = null;
		for (int i = 0; i < 10000; i++) {
			ret_noindex = t_noindex.selectId(100);
		}
		long end_noindex = System.nanoTime();
		System.out.println(end_noindex - start_noindex + "[" + ret_noindex + "]");
	}

	private static Table create(boolean indexCreate) {
		Table table = new Table().testCreate(TABLE_SIZE);
		if (indexCreate)
			table.createIndex();
		return table;
	}
}
