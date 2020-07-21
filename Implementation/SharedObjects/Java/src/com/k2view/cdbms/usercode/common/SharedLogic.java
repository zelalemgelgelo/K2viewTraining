/////////////////////////////////////////////////////////////////////////
// Project Shared Functions
/////////////////////////////////////////////////////////////////////////

package com.k2view.cdbms.usercode.common;

import java.util.*;
import java.sql.*;
import java.math.*;
import java.io.*;

import com.k2view.cdbms.shared.*;
import com.k2view.cdbms.shared.user.UserCode;
import com.k2view.cdbms.sync.*;
import com.k2view.cdbms.lut.*;
import com.k2view.cdbms.shared.logging.LogEntry.*;
import com.k2view.cdbms.func.oracle.OracleToDate;
import com.k2view.cdbms.func.oracle.OracleRownum;
import com.k2view.cdbms.shared.utils.UserCodeDescribe.*;

import static com.k2view.cdbms.shared.user.ProductFunctions.*;
import static com.k2view.cdbms.shared.user.UserCode.*;
import static com.k2view.cdbms.shared.utils.UserCodeDescribe.FunctionType.*;
import static com.k2view.cdbms.usercode.common.SharedGlobals.*;

@SuppressWarnings({"unused", "DefaultAnnotationParam"})
public class SharedLogic {


	@category("Utilities")
	@type(DecisionFunction)
	@out(name = "decision", type = Boolean.class, desc = "")
	public static Boolean SyncCheck() throws Exception {
		Boolean syncCheck=false;
		
		if(isFirstSync())
		
		syncCheck =true;
		
		return syncCheck;
	}


	@category("Utilities")
	@out(name = "str1", type = String.class, desc = "")
	public static String toUpperCase(String str) throws Exception {
		if(str==null)
			return null;
		
		
		String st= str.toUpperCase();
		char val=st.charAt(0);
				String em="";
		for (int i=1;i<str.length();i++){
			em+=st.charAt(i);
		
		
		}
		String newStr =em.toLowerCase();
		return val +newStr;
			
	}



	
	

}
