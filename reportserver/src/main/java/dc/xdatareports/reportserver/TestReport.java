package dc.xdatareports.reportserver;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dc.xdatareports.reportserver.vo.ReportColumnVO;
import dc.xdatareports.reportserver.vo.ReportVO;

public class TestReport {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		List<Person> people = new ArrayList<Person>();
		people.add(new Person("Fabiana", "Gomes"));
		people.add(new Person("Yuri", "Gomes"));
		ReportGenerator<Person> rg = new ReportGenerator<Person>();
		ReportVO<Person> rp = new ReportVO<Person>();
		rp.getColumns().add(new ReportColumnVO("firstName", "Nome"));
		rp.getColumns().add(new ReportColumnVO("lastName", "Sobrenome"));
		rp.setTitle("Aniversariantes");
		rp.setData(people);
		byte[] repStream = rg.exportDynamicReport(rp);
		try (FileOutputStream fos = new FileOutputStream("c:\\teste\\bbb.pdf")) {
		      fos.write(repStream);
		}
	}

}
