package dc.xdatareports.reportserver.vo;

public class ReportColumnVO {

	private String title;
	private String field;
	
	public ReportColumnVO(String field, String title) {
		super();
		this.field = field;
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getField() {
		return field;
	}
	
	public void setField(String field) {
		this.field = field;
	}
}
