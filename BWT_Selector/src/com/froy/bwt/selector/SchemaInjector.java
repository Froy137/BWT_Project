package com.froy.bwt.selector;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import com.froy.bwt.bwt.generated.BorderWaitTime;
import com.froy.bwt.bwt.generated.BorderWaitTime.Port;
import com.froy.bwt.utils.MarshallingBWTData;
import com.froy.bwt.utils.Mysql_CRUD;

public class SchemaInjector {

	public void dataIntoSchema(String Data, String title) {

		// instantiate unmarshalling mechanism object
		MarshallingBWTData unmarsh = new MarshallingBWTData();
		BorderWaitTime BWT = new BorderWaitTime();
		try {
			BWT = unmarsh.unmarshall(Data);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String lastUpdatedDate, lastUpdatedTime, NumberofPorts;
		lastUpdatedDate = lastUpdatedTime = NumberofPorts = "";

		if (BWT.getLastUpdatedDate() != null) {
			lastUpdatedDate = BWT.getLastUpdatedDate().toString();
		}
		if (BWT.getLastUpdatedTime() != null)
			lastUpdatedTime = BWT.getLastUpdatedTime();

		NumberofPorts = BWT.getNumberOfPorts() + "";

		// inserting into first table //RawXML
		Mysql_CRUD sql = new Mysql_CRUD();
		sql.insert_into_DB_RawXML(Data, title);
		// inserting into second table

		ArrayList<Port> ports = (ArrayList<Port>) BWT.getPort();
		sql.insert_into_DB_PortRecord(ports, title);
		// Arraylistports.get(0).getClass().getFields();

	}

}
