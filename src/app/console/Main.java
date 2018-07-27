package app.console;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 * Note: Get connection to database in JDBC:
 * -jdbc:<driver protocol>:<connection details>
 * i.e.:
 * -MS SQL Server: jdbc:odbc:demoDB
 * -Oracle		 : jdbc:oracle:oguz@myserver:1521:demoDB
 * -MySql		 : jdbc:mysql://localhost:3306/demoDB
 * @author t420
 *
 */
public class Main {

	public static void main(String[] args) throws Exception {
		
		//Accessing driver from JAR file
		Class.forName("com.mysql.jdbc.Driver");
		
		//Creating a variable for the connection called "con"
		Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","");
		//jdbc:mysql:/localhost:3306 -> database path to recognise by Connector
		//1. root is the database user
		//2. root is the password
		
		//Here we generate our query
		PreparedStatement statement=con.prepareStatement("select * from names");
		
		//Creating a variable to execute query
		ResultSet result=statement.executeQuery();
		while(result.next()){
			System.out.println(result.getString(1)+" "+result.getString(2));
			//getString returns tha data
			//1 is the first field of table
			//2 is the second field of the table
			
		}
	}	
}
