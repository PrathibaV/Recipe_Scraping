package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class DbConnection {
	public Connection DbSetup() {
		Connection conn= connectToDb("postgres","postgres","password");
		createTable(conn, "lfv_recipes_without_eliminateitems");
		createTable(conn, "lchf_recipes_without_eliminateitems");
		createTable(conn, "lfv_recipes_with_addon_items");
		createTable(conn, "lchf_recipes_with_addon_items");
		
		createTable(conn, "lfv_recipes_allergy_hazelnut");
		createTable(conn, "lfv_recipes_allergy_sesame");
		createTable(conn, "lfv_recipes_allergy_walnut");
		createTable(conn, "lfv_recipes_allergy_almond");
		createTable(conn, "lfv_recipes_allergy_cashew");
		createTable(conn, "lfv_recipes_allergy_peanut");
		createTable(conn, "lfv_recipes_allergy_pistachio");
		
		createTable(conn, "lchf_recipes_allergy_hazelnut");
		createTable(conn, "lchfF_recipes_allergy_sesame");
		createTable(conn, "lchf_recipes_allergy_walnut");
		createTable(conn, "lchf_recipes_allergy_almond");
		createTable(conn, "lchf_recipes_allergy_cashew");
		createTable(conn, "lchf_recipes_allergy_peanut");
		createTable(conn, "lchf_recipes_allergy_pistachio");
		
		return conn;
	}
	
	public  Connection connectToDb(String dbname, String user, String pswd) {
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
	
	public  void createTable(Connection conn, String tableName) {
		Statement statement;
		try {
			String query = "create table IF NOT EXISTS "+tableName+"(Recipe_ID varchar(200) PRIMARY KEY, Recipe_Name varchar(2000), Recipe_Category varchar(2000),"
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
	
	
	public static void insertRow(Connection conn, String tableName, Map<String, String> recipe) {
		 PreparedStatement preparedStatement = null;
		    try {
		        //SQL query with parameterized placeholders
		        String query = "INSERT INTO " + tableName + 
		                       "(Recipe_ID,Recipe_Name,Recipe_Category,Food_Category,Ingredients,Preparation_Time," +
		                       "Cooking_Time,Tag,No_Of_Servings,Cuisine_Category,Recipe_Description, Preparation_Method," +
		                       "Nutrient_Values,Recipe_URL) " +
		                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		        
		        //PreparedStatement object with the SQL query
		        preparedStatement = conn.prepareStatement(query);

		        // Set values for each parameter in the prepared statement
		        preparedStatement.setString(1, recipe.get("Recipe ID"));
		        preparedStatement.setString(2, recipe.get("Recipe Name"));
		        preparedStatement.setString(3, recipe.get("Recipe Category"));
		        preparedStatement.setString(4, recipe.get("Food Category"));
		        preparedStatement.setString(5, recipe.get("Ingredients"));
		        preparedStatement.setString(6, recipe.get("Preparation Time"));
		        preparedStatement.setString(7, recipe.get("Cooking Time"));
		        preparedStatement.setString(8, recipe.get("Tag"));
		        preparedStatement.setString(9, recipe.get("No of servings"));
		        preparedStatement.setString(10, recipe.get("Cuisine category"));
		        preparedStatement.setString(11, recipe.get("Recipe Description"));
		        preparedStatement.setString(12, recipe.get("Preparation method"));
		        preparedStatement.setString(13, recipe.get("Nutrient values"));
		        preparedStatement.setString(14, recipe.get("Recipe URL"));
		        
		        // Execute the insert operation
		        preparedStatement.executeUpdate();
		        
		        System.out.println("Row Inserted");
		    } catch (SQLException e) {
		        System.out.println("Error inserting row: " + e.getMessage());
		    } finally {
		        try {
		            if (preparedStatement != null) {
		                preparedStatement.close();
		            }
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }
		}
	}


