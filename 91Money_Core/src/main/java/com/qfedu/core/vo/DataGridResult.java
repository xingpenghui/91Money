package com.qfedu.core.vo;

import java.util.List;

public class DataGridResult {

	private Long total;
	private Object rows;
	public DataGridResult(List<?> rows, Long total) {
		this.total = total;
		this.rows = rows;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public Object getRows() {
		return rows;
	}
	public void setRows(Object rows) {
		this.rows = rows;
	}
}
