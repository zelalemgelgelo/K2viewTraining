/////////////////////////////////////////////////////////////////////////
// Project Web Services
/////////////////////////////////////////////////////////////////////////

package com.k2view.cdbms.usercode.lu.k2_ws.Training;

import java.util.*;

import com.k2view.cdbms.shared.*;
import com.k2view.cdbms.shared.user.WebServiceUserCode;
import com.k2view.cdbms.shared.utils.UserCodeDescribe.*;
import com.k2view.fabric.api.endpoint.Endpoint.*;
import com.k2view.graphIt.script.Scripter;
import java.sql.*;
import java.math.*;
import java.io.*;
import com.k2view.cdbms.sync.*;
import com.k2view.cdbms.lut.*;
import com.k2view.cdbms.shared.logging.LogEntry.*;
import com.k2view.cdbms.func.oracle.OracleToDate;
import com.k2view.cdbms.func.oracle.OracleRownum;
import static com.k2view.cdbms.shared.utils.UserCodeDescribe.FunctionType.*;
import static com.k2view.cdbms.shared.user.ProductFunctions.*;
import static com.k2view.cdbms.usercode.common.SharedLogic.*;
import static com.k2view.cdbms.usercode.common.SharedGlobals.*;

@SuppressWarnings({"unused", "DefaultAnnotationParam"})
public class Logic extends WebServiceUserCode {


	@desc("helps to get all information about customer")
	@webService(path = "test/getCustomerDetails", verb = {MethodType.GET, MethodType.POST, MethodType.PUT, MethodType.DELETE}, version = "1", isRaw = false, produce = {Produce.XML, Produce.JSON})
	public static String wsGetCustomer(String i_id, String i_vipStatus) throws Exception {
		String sql = "select cust.FIRST_NAME||' '||cust.LAST_NAME CUSTOMER_NAME, cont.CONTRACT_ID,cont.CONTRACT_DESCRIPTION," +
		        "sub.SUBSCRIBER_ID,sub.MSISDN,sub.IMSI,sub.SIM,sub.SUBSCRIBER_TYPE,sub.VIP_STATUS " +
				"from PUBLIC_CUSTOMER cust, PUBLIC_CONTRACT cont, SUBSCRIBER sub where cont.CONTRACT_ID=sub.SUBSCRIBER_ID and sub.VIP_STATUS=?";
		String resultStr="";
		Db.Rows rows = ludb("CustomerLU", i_id).fetch(sql, i_vipStatus);
		for (Db.Row r: rows){
			resultStr+= r.toString() ;
		}
		
		return resultStr;
	}

	@webService(path = "test/casesDetail", verb = {MethodType.GET, MethodType.POST, MethodType.PUT, MethodType.DELETE}, version = "1", isRaw = false, produce = {Produce.XML, Produce.JSON})
	public static String wsCase(String i_id, List<Map<String,String>> input) throws Exception {
		String message="";
		if (input!=null && input.size()>0){
			for (int i=0;i<input.size();i++){
			 if (input.get(i)!=null){
			 	String activity_id =input.get(i).get("activity_id");
			 	String case_id =input.get(i).get("case_id");
			 	String case_date  =input.get(i).get("case_date");
			 	String case_type  =input.get(i).get("case_type");
			 	String status  =input.get(i).get("status");
				 if(!activity_id.isEmpty() && activity_id!=null){
					fabric().execute("set sync off");
					fabric().execute("get CustomerLU.?",i_id);
					 //fabric().execute("set sync on");
					fabric().execute("Begin");
					 String sql = "insert into PUBLIC_CASES (activity_id,case_id,case_date,case_type,status) values (?,?,?,?,?)";
					// ludb("CustomerLU", i_id).execute(sql, activity_id, case_id, case_date, case_type, status);
					 fabric().execute(sql, activity_id, case_id, case_date, case_type, status);
			fabric().execute("Commit");
		message="is Inserted";
				 }else{
				 	log.info("not inserted");
				 	message="not inserted";
				 }
				}
			}
			log.info("successfully completed");
		}else{
			log.info("the input is not correct");
			message="error";
		}
			return message;
	}


	@webService(path = "graphite/ws", verb = {MethodType.GET, MethodType.POST, MethodType.PUT, MethodType.DELETE}, version = "1", isRaw = false, produce = {Produce.XML, Produce.JSON})
	public static Object exerciseOnGeaphitWs(String customer_id) throws Exception {
		//Object response= graphit("exerciseOnGraphite.graphit",customer_id);
		Map<String,Object> keyValues= new HashMap<>();

		keyValues.put("times3",(Scripter.F) p->(double)p[0]*3);
 	return graphit("exerciseOnGraphite.graphit",keyValues);
	}


	@webService(path = "enrichmentWeb", verb = {MethodType.GET, MethodType.POST, MethodType.PUT, MethodType.DELETE}, version = "1", isRaw = false, produce = {Produce.XML, Produce.JSON})
	public static Object webServiceEnrichment(String i_id) throws Exception {
		String sql = "select result from cases_webservice";
		String resultStr="";
		Db.Rows rows = ludb("CustomerLU", i_id).fetch(sql);
		
		return rows;
	}


	@webService(path = "wsGraphit", verb = {MethodType.GET, MethodType.POST, MethodType.PUT, MethodType.DELETE}, version = "1", isRaw = false, produce = {Produce.XML, Produce.JSON})
	public static Object wsGraphiteExercise(String i_id) throws Exception {
		String val_brz="Bronze";
		String val_gld="Gold";
		String val_plt="Platinum";
		
		String CUST_STATUS = "SELECT count(*) FROM SUBSCRIBER where SUBSCRIBER.VIP_STATUS=?";
		String cnt_brz = ludb("CustomerLU", i_id).fetch(CUST_STATUS,val_brz).firstValue().toString();
		
		String cnt_gld = ludb("CustomerLU", i_id).fetch(CUST_STATUS,val_gld).firstValue().toString();
		
		String cnt_plt = ludb("CustomerLU", i_id).fetch(CUST_STATUS,val_plt).firstValue().toString();
		
		
		if ((Integer.parseInt(cnt_brz)==0)&&((Integer.parseInt(cnt_gld) !=0)||(Integer.parseInt(cnt_plt) !=0))){
			return graphit("Exercise1.graphit",i_id);
			}
		else{
			return graphit("Exercise1_2brz.graphit",i_id);
			}
	}


	@webService(path = "customerws", verb = {MethodType.GET, MethodType.POST, MethodType.PUT, MethodType.DELETE}, version = "1", isRaw = false, produce = {Produce.XML, Produce.JSON})
	public static Object customer_detailws(String c_id) throws Exception {
		//fabric().execute("set sync off");
		fabric().execute("get CustomerLU.?",c_id);
		//fabric().execute("set sync on");
		return graphit("customerInfo.graphit");
		log.info();
	}






	
}
