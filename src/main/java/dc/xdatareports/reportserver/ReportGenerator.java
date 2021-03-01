package dc.xdatareports.reportserver;


import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import dc.xdatareports.reportserver.util.Templates;
import dc.xdatareports.reportserver.vo.ReportColumnVO;
import dc.xdatareports.reportserver.vo.ReportVO;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;


public class ReportGenerator<T> {

	public byte[] exportDynamicReport(ReportVO<T> reportVO) {
		
		StyleBuilder boldStyle = stl.style().bold();
		StyleBuilder boldCenteredStyle = stl.style(boldStyle).setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);
		
		JasperReportBuilder report = report()
										.highlightDetailEvenRows()
										.title(Templates.createVisumTitleComponent(reportVO.getTitle()))
										.pageFooter(cmp.pageXofY().setStyle(boldCenteredStyle))
										.setDataSource(reportVO.getQuery(), reportVO.getConnection());
										
		for(ReportColumnVO column:reportVO.getColumns()) {
			report.addColumn(col.column(column.getTitle(), column.getField(), type.stringType()));
		}
		
		ByteArrayOutputStream out = null;
		
		try {
			out = new ByteArrayOutputStream();
			report.toPdf(out);
			return out.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
		
	}
	
}
