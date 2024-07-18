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
			String query = "create table IF NOT EXISTS "+tableName+"(Recipe_ID varchar(200), Recipe_Name varchar(2000), Recipe_Category varchar(2000),"
					+ " Food_Category varchar(2000), Ingredients varchar(2000), "
					+ "Preparation_Time varchar(200), Cooking_Time varchar(200), "
					+ "Tag varchar(2000), No_Of_Servings varchar(2000), Cuisine_Category varchar(2000),"
					+ " Recipe_Description varchar(5000), Preparation_Method varchar(5000), "
					+ "Nutrient_Values varchar(2000), Recipe_URL varchar(2000));";
			statement = conn.createStatement();
			statement.executeUpdate(query);
			System.out.println("Table Created");
		}catch (Exception e) {
			System.out.println(e);
		}
	}
	
	
	public void insertRow(Connection conn, String tableName, Integer Recipe_ID, String Recipe_Name, 
			String Recipe_Category, String Food_Category, String Ingredients, String Preparation_Time, String Cooking_Time, 
			String Tag, String No_Of_Servings, String Cuisine_Category, String Recipe_Description, String Preparation_Method,
			String Nutrient_Values, String Recipe_URL) {
		Statement statement;
		try {
			String query =  String.format("insert into %s(Recipe_ID,Recipe_Name,Recipe_Category,Food_Category,Ingredients,Preparation_Time,"
					+ "Cooking_Time,Tag,No_Of_Servings,Cuisine_Category,Recipe_Description,Preparation_Method,Nutrient_Values,Recipe_URL) values"
					+ "(%s,'%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')",
					tableName,Recipe_ID,Recipe_Name,Recipe_Category,Food_Category,Ingredients,Preparation_Time,Cooking_Time,Tag,No_Of_Servings,
					Cuisine_Category,Recipe_Description,Preparation_Method,Nutrient_Values,Recipe_URL);
			statement = conn.createStatement();
			statement.execute(query);
			System.out.println("Row Inserted");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

    
}
