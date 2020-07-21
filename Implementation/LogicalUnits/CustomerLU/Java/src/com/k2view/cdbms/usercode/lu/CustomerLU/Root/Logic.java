/////////////////////////////////////////////////////////////////////////
// LU Functions
/////////////////////////////////////////////////////////////////////////

package com.k2view.cdbms.usercode.lu.CustomerLU.Root;

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


	@type(RootFunction)
	@out(name = "subscriber_id", type = Long.class, desc = "")
	@out(name = "invoice_id", type = Long.class, desc = "")
	@out(name = "issued_date", type = String.class, desc = "")
	@out(name = "due_date", type = String.class, desc = "")
	@out(name = "status", type = String.class, desc = "")
	@out(name = "balance", type = Long.class, desc = "")
	@out(name = "invoice_image", type = String.class, desc = "")
	public static void fnPop_invoice(String input) throws Exception {
				String sql = "SELECT subscriber_id, invoice_id, issued_date, due_date, status, balance, invoice_image FROM public.invoice";
				db("CRM_DB").fetch(sql).each(row->{
					yield(row.cells());
				});
			
	}



	
	
	
	
}
