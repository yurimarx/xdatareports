package dc.xdatareports.reportserver;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.intersystems.jdbc.IRISDataSource;

import dc.xdatareports.reportserver.vo.ReportColumnVO;
import dc.xdatareports.reportserver.vo.ReportVO;

public class TestReport {

	public static void main(String[] args) throws FileNotFoundException, IOException, SQLException {
		
		
		try {
			IRISDataSource ds = new IRISDataSource();
			ds.setURL("jdbc:IRIS://127.0.0.1:1972/DEV");
			ds.setUser("_system");
			ds.setPassword("SYS");
			Connection dbconnection = ds.getConnection();

			String query = "select * from FCE.Branch";
			
			ReportGenerator<Person> rg = new ReportGenerator<Person>();
			ReportVO<Person> rp = new ReportVO<Person>();
			rp.getColumns().add(new ReportColumnVO("Address", "Address"));
			rp.getColumns().add(new ReportColumnVO("Phone", "Phone"));
			rp.setTitle("Branch");
			rp.setQuery(query);
			rp.setConnection(dbconnection);
			byte[] repStream = rg.exportDynamicReport(rp);
			try (FileOutputStream fos = new FileOutputStream("c:\\teste\\bbb.pdf")) {
			      fos.write(repStream);
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}	
		
	}
	
}
