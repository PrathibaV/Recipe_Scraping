package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DbConnection {
	public Connection connectToDb(String dbname, String user, String pswd) {
		Connection conn = null;
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + dbname, user, pswd);
			if (conn != null) {
				System.out.println("connection Established");
			} else {
				System.out.println("Connection failed");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return conn;

	}

	public void createTable(Connection conn, String tableName) {
		Statement statement;
		try {
			String query = "create table "+tableName+
					"(Recipe_ID varchar(200), Recipe_Name varchar(200), Recipe_Category varchar(200),"
					+ " Food_Category varchar(200), Ingredients varchar(200), "
					+ "Preparation_Time varchar(200), Cooking_Time varchar(200), "
					+ "Tag varchar(200), No_Of_Servings varchar(200), Cuisine_Category varchar(200),"
					+ " Recipe_Description varchar(200), Preparation_Method varchar(200), "
					+ "Nutrient_Values varchar(200), Recipe_URL varchar(200));";
			statement = conn.createStatement();
			statement.executeUpdate(query);
			System.out.println("Table Created");
		}catch (Exception e) {
			System.out.println(e);
		}
	}

}
