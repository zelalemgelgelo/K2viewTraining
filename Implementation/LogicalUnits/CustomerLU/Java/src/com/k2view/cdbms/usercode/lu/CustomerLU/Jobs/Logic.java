/////////////////////////////////////////////////////////////////////////
// LU Functions
/////////////////////////////////////////////////////////////////////////

package com.k2view.cdbms.usercode.lu.CustomerLU.Jobs;
//package com.k2view.cdbms.usercode.common;
import java.util.*;
import java.sql.*;
import java.math.*;
import java.io.*;

import com.k2view.cdbms.shared.*;
import com.k2view.cdbms.shared.Globals;
import com.k2view.cdbms.shared.user.UserCode;
import com.k2view.cdbms.sync.*;
import com.k2view.cdbms.lut.*;
import com.k2view.cdbms.shared.utils.UserCodeDescribe.*;
import com.k2view.cdbms.shared.logging.LogEntry.*;
import com.k2view.cdbms.func.oracle.OracleToDate;
import com.k2view.cdbms.func.oracle.OracleRownum;
import com.k2view.cdbms.usercode.lu.CustomerLU.*;

import static com.k2view.cdbms.shared.utils.UserCodeDescribe.FunctionType.*;
import static com.k2view.cdbms.shared.user.ProductFunctions.*;
import static com.k2view.cdbms.usercode.common.SharedLogic.*;
import static com.k2view.cdbms.usercode.lu.CustomerLU.Globals.*;

@SuppressWarnings({"unused", "DefaultAnnotationParam"})
public class Logic extends UserCode {


	@type(UserJob)
	public static void job_function() throws Exception {
		String sqlFetch = "SELECT city, intid, lat, long, pop, state FROM k2view_customerlu_trainingproject.new_parser_zel";
		
		try {
			Db.Rows rows = db("dbcassandra").fetch(sqlFetch);
		
		
		} catch (Exception e) {
			log.error("the data base data is not find");
		}
		fabric().beginTransaction();
		Db.Rows rows = db("dbcassandra").fetch("SELECT city, intid, lat, long, pop, state FROM k2view_customerlu_trainingproject.new_parser_zel");
		
		
		String input = "insert into k2view_customerlu_trainingproject.job_table_sync (city, intid, lat, long, pop, state) values (?,?,?,?,?,?) ";
		for (Db.Row r : rows) {
			String cast = "";
			String city = cast + r.get("city");
			String intid = cast + r.get("intid");
			String lat = cast + r.get("lat");
			String lon = cast + r.get("long");
			String pop = cast + r.get("pop");
			String state = cast + r.get("state");
		
			fabric().execute(input, city, intid, lat, lon, pop, state);
			fabric().commit();
		}
	}


	@type(UserJob)
	@out(name = "result", type = Object.class, desc = "")
	public static Object jobs_parsing_function(Object[] input) throws Exception {
		String csvFile = "C:/csvfile/geolocation.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		Map<String, String> val = new HashMap<>();
		
		int count = 0;
		try {
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				String[] country = line.split(cvsSplitBy);
				count++;
				for (int i = 0; i < country.length; i++) {
		
					String st = "" + count;
					val.put(st, country[i]);
				}
		
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		return Logic.stringsplitfunction(val);
	}


	@category("String")
	@out(name = "city", type = String.class, desc = "")
	@out(name = "lat", type = String.class, desc = "")
	@out(name = "long", type = String.class, desc = "")
	@out(name = "state", type = String.class, desc = "")
	@out(name = "pop", type = String.class, desc = "")
	@out(name = "intid", type = String.class, desc = "")
	public static Object stringsplitfunction(Map<String, String> input) throws Exception {


		return new Object[]{
				input.get("0"),
				input.get("1"),
				input.get("2"),
				input.get("3"),
				input.get("4"),
				input.get("5")
		};

	}
}
