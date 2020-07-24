/////////////////////////////////////////////////////////////////////////
// LU Functions
/////////////////////////////////////////////////////////////////////////

package com.k2view.cdbms.usercode.lu.CustomerLU.Utilities;

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


	@type(DecisionFunction)
	@out(name = "decision", type = Boolean.class, desc = "")
	public static Boolean exerciseDecisionFunction() throws Exception {
		int CRMCases_threshold=25000; //latest known number of cases in CRM_DB.CASES.
		Boolean syncInd = false;
		
		String count = db("CRM_DB").fetch("SELECT count(*) FROM CASES").firstValue().toString();
		//puts the number of rows in the CASES DB into a variable count.
		
		reportUserMessage(count);
		int counter=Integer.parseInt(count);
		
		if ( counter >= CRMCases_threshold){
		reportUserMessage("new case in !!");
		syncInd = true;	
		}
		
		return syncInd;
	}


	
}
