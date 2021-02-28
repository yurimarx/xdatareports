package dc.xdatareports.reportserver.vo;

import java.util.ArrayList;
import java.util.List;

public class ReportVO<T> {

	private String title;
	private String query;
	private List<ReportColumnVO> columns = new ArrayList<ReportColumnVO>();
	private List<T> data;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	
	public List<ReportColumnVO> getColumns() {
		return columns;
	}
	
	public void setColumns(List<ReportColumnVO> columns) {
		this.columns = columns;
	}
	
	public List<T> getData() {
		return data;
	}
	
	public void setData(List<T> data) {
		this.data = data;
	}
}
