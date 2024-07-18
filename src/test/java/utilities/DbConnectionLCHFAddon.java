package utilities;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLType;
import java.sql.Statement;
import java.sql.Types;
import java.util.Random;
import java.util.Set;

import entity.NutrientValue;
import entity.Recipe;
import entity.RecipeIngredient;
public class DbConnection {
	public static Connection connectToDb(String dbname, String user, String pswd) {
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
	
	public static void insertRecipe(Connection conn, Recipe recipe) {

		String insertQuery = "insert into {{table_name}} (recipe_id , recipe_name , recipe_category , "
				+ "food_category , prep_time , cook_time , no_of_servings , cuisine_category ,"
				+ " recipe_desc, preparation_method, tags, recipe_url) "
				+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? );";
		insertQuery = "Eliminate".equalsIgnoreCase(recipe.getAddonOrEliminate()) ?
				insertQuery.replace("{{table_name}}", "lchf_eliminate_recipe") : 
					insertQuery.replace("{{table_name}}", "lchf_addon_recipe");
		try (PreparedStatement pstmtRecipe = conn.prepareStatement(insertQuery)) {
			pstmtRecipe.setLong(1, recipe.getRecipeId());
			pstmtRecipe.setString(2, recipe.getRecipeName());
			pstmtRecipe.setString(3, recipe.getRecipeCategory());
			pstmtRecipe.setString(4, recipe.getFoodCategory());
			if(recipe.getPrepTime() != null) {
				pstmtRecipe.setString(5, recipe.getPrepTime());
			} else {
				pstmtRecipe.setNull(5, Types.VARCHAR);
			}
			if(recipe.getCookTime() != null) {
				pstmtRecipe.setString(6, recipe.getCookTime());
			} else {
				pstmtRecipe.setNull(6, Types.VARCHAR);
			}
			if(recipe.getNoOfServings() != null) {
				pstmtRecipe.setInt(7, recipe.getNoOfServings());
			} else {
				pstmtRecipe.setNull(7, Types.NUMERIC);
			}
			
			pstmtRecipe.setString(8, recipe.getCuisineCategory());
			if(recipe.getRecipeDesc() != null) {
				String desc = recipe.getRecipeDesc();
				pstmtRecipe.setString(9, desc.length() > 2000 ?
						desc.substring(0, 2000): desc );
			} else {
				pstmtRecipe.setNull(9, Types.VARCHAR);
			}
			if(recipe.getPreparationMethod() != null) {
				String pMtd = recipe.getPreparationMethod();
				pstmtRecipe.setString(10, pMtd.length() > 1000 ? pMtd.substring(0, 1000): pMtd);
						
			}else {
				pstmtRecipe.setNull(10, Types.VARCHAR);
			}
			if(recipe.getRecipeTags() != null) {
				String tag = recipe.getRecipeTags();
				pstmtRecipe.setString(11,tag.length() > 200 ? tag.substring(0, 200): tag);
			}else {
				pstmtRecipe.setNull(11,Types.VARCHAR);
			}
									
			pstmtRecipe.setString(12, recipe.getRecipeUrl());
		
			
			pstmtRecipe.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		insertIngredients(conn, recipe.getRecipeId(), recipe.getRecipeIngredients());
		insertNutrientValues(conn, recipe.getRecipeId(), recipe.getNutrientValues());
	}
	
	public static void insertIngredients(Connection conn, Long recipeId, Set<RecipeIngredient> recipeIngredients) {
		String insertQuery = "insert into recipe_ingredient (recipe_id , igredient_name, quantity ) "
				+ "values(?, ?, ?);";

		try (PreparedStatement pstmtIngredient = conn.prepareStatement(insertQuery)) {
			if(recipeIngredients != null && recipeIngredients.size() > 0) {
				for(RecipeIngredient recipeIngredient : recipeIngredients) {
					pstmtIngredient.setLong(1, recipeId);

					pstmtIngredient.setString(2, recipeIngredient.getIgredientName());
					pstmtIngredient.setString(3, recipeIngredient.getQuantity());

					pstmtIngredient.addBatch();
				}
				pstmtIngredient.executeBatch();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void insertNutrientValues(Connection conn, Long recipeId, Set<NutrientValue> nutrientValues) {
		String insertQuery = "insert into nutrient_value (recipe_id , nutrient_name, nutrient_value ) "
				+ "values(?, ?, ?);";
//		Random rand = new Random(1000);
		try (PreparedStatement pstmtIngredient = conn.prepareStatement(insertQuery)) {
			if(nutrientValues != null && nutrientValues.size() > 0) {
				for(NutrientValue nutrientValue : nutrientValues) {
					pstmtIngredient.setLong(1, recipeId);
					pstmtIngredient.setString(2, nutrientValue.getNutrientName());
					pstmtIngredient.setString(3, nutrientValue.getNutrientValue());
					pstmtIngredient.addBatch();
				}
				pstmtIngredient.executeBatch();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
