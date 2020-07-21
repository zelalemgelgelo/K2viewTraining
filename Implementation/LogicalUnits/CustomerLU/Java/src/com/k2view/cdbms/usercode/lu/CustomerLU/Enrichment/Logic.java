/////////////////////////////////////////////////////////////////////////
// LU Functions
/////////////////////////////////////////////////////////////////////////

package com.k2view.cdbms.usercode.lu.CustomerLU.Enrichment;

import java.util.*;
import java.sql.*;
import java.math.*;
import java.io.*;
import com.sun.org.apache.bcel.internal.generic.RETURN;
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


	public static void enrichmentFunction() throws Exception {
		log.info("RUNNING ENRICHMENT FUNCTION");
		String newVal= "select associated_line, contract_description from public_contract";

		String sqlUpdate= "update public_contract set associated_line =? where  associated_line =?";

		Db.Rows rows = fabric().fetch(newVal);

		for(Db.Row row:rows){

			String startingNum="+1";
		    String numformater="";

			String cast="";
			String value= cast + row.get("associated_line");

			String valueDesc=cast + row.get("contract_description");

			String regex1="(.*)+1(.*)";
			//String regex2 ="5G LTE";
			//&& valueDesc.matches(regex2)==true)
			
		  if((value.matches(regex1) == false )){

		    numformater = startingNum + " "+ value;
			
		    fabric().execute(sqlUpdate,numformater,value);
			
		  }       
		}
	}


	public static void secondEnrichmentFunction() throws Exception {

	}


	public static void enrichmentFunctionWebService() throws Exception {
		log.info("RUNNING MY NEW WEB ENRICHMENT FUNCTION");
		
		String results="";
		String value="select cust.FIRST_NAME||' '||cust.LAST_NAME CUSTOMER_NAME, cont.CONTRACT_ID,cont.CONTRACT_DESCRIPTION," +
				       "sub.SUBSCRIBER_ID,sub.MSISDN,sub.IMSI,sub.SIM,sub.SUBSCRIBER_TYPE,sub.VIP_STATUS " +
					"from PUBLIC_CUSTOMER cust, PUBLIC_CONTRACT cont, SUBSCRIBER sub where cont.CONTRACT_ID=sub.SUBSCRIBER_ID and sub.VIP_STATUS='platinum'";
		//		String sqlInput = "insert into CASES_WEBSERVICE (CUSTOMER_NAME,CONTRACT_ID,CONTRACT_DESCRIPTION," +
		//				"SUBSCRIBER_ID,MSISDN,IMSI,SIM,SUBSCRIBER_TYPE,VIP_STATUS) " +
		//				"values (?,?,?,?,?,?,?,?,?)";
		
		String sqlInput = "replace into CASES_WEBSERVICE (ID,RESULT) " +
				"values (?,?)";
		Db.Rows rows = fabric().fetch(value);
		for(Db.Row row: rows){
			results += row.toString();
		
		}
		fabric().execute(sqlInput,getInstanceID(),results);
		//		for(Db.Row r : rows){
		//			String cast="";
		//			String cust_name= cast + r.get("CUSTOMER_NAME").toString();
		//			String contract_id= cast + r.get("CONTRACT_ID").toString();
		//			String contract_desc= cast + r.get("CONTRACT_DESCRIPTION").toString();
		//			String subs_id= cast + r.get("SUBSCRIBER_ID").toString();
		//			String msisdn= cast + r.get("MSISDN").toString();
		//			String imsi= cast + r.get("IMSI").toString();
		//			String sim= cast + r.get("SIM").toString();
		//			String sub_type= cast + r.get("SUBSCRIBER_TYPE").toString();
		//			String vip_st= cast + r.get("VIP_STATUS").toString();
		//
		//			fabric().execute(sqlInput,cust_name,contract_id,contract_desc,subs_id,msisdn,imsi,sim,sub_type,vip_st);
		//		}
	}
	
}
