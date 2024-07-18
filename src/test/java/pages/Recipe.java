package pages;

public class Recipe {
	
	private int recipeID;
	private String recipeName;	
	private String recipeCategory;//(Breakfast/lunch/snack/dinner
	private String foodCategory; //(Veg/non-veg/vegan/Jain)
	private String ingredients;
	private String preparationTime;
	private String cookingTime;
	private String tag;
	private String servings;
	private String cuisineCategory;
	private String recipeDescription;
	private String preparationMethod;
	//private Map<String,String> nutrientValues;
	private String nutrientValues;
	private String RecipeURL;
	public int getRecipeID() {
		return recipeID;
	}
	public void setRecipeID(int recipeID) {
		this.recipeID = recipeID;
	}
	public String getRecipeName() {
		return recipeName;
	}
	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}
	public String getRecipeCategory() {
		return recipeCategory;
	}
	public void setRecipeCategory(String recipeCategory) {
		this.recipeCategory = recipeCategory;
	}
	public String getFoodCategory() {
		return foodCategory;
	}
	public void setFoodCategory(String foodCategory) {
		this.foodCategory = foodCategory;
	}
	
	public String getIngredients() {
		return ingredients;
	}
	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
	}
	public String getPreparationTime() {
		return preparationTime;
	}
	public void setPreparationTime(String preparationTime) {
		this.preparationTime = preparationTime;
	}
	public String getCookingTime() {
		return cookingTime;
	}
	public void setCookingTime(String cookingTime) {
		this.cookingTime = cookingTime;
	}
	
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getServings() {
		return servings;
	}
	public void setServings(String servings) {
		this.servings = servings;
	}
	public String getCuisineCategory() {
		return cuisineCategory;
	}
	public void setCuisineCategory(String cuisineCategory) {
		this.cuisineCategory = cuisineCategory;
	}
	public String getRecipeDescription() {
		return recipeDescription;
	}
	public void setRecipeDescription(String recipeDescription) {
		this.recipeDescription = recipeDescription;
	}
	public String getPreparationMethod() {
		return preparationMethod;
	}
	public void setPreparationMethod(String preparationMethod) {
		this.preparationMethod = preparationMethod;
	}
	
	
	
	public String getNutrientValues() {
		return nutrientValues;
	}
	public void setNutrientValues(String nutrientValues) {
		this.nutrientValues = nutrientValues;
	}
	public String getRecipeURL() {
		return RecipeURL;
	}
	public void setRecipeURL(String recipeURL) {
		RecipeURL = recipeURL;
	}
	@Override
	public String toString() {
		return "Recipe [recipeID=" + recipeID + ", recipeName=" + recipeName + ", recipeCategory=" + recipeCategory
				+ ", foodCategory=" + foodCategory + ", ingredients=" + ingredients + ", preparationTime="
				+ preparationTime + ", cookingTime=" + cookingTime + ", tag=" + tag + ", servings=" + servings
				+ ", cuisineCategory=" + cuisineCategory + ", recipeDescription=" + recipeDescription
				+ ", preparationMethod=" + preparationMethod + ", nutrientValues=" + nutrientValues + ", RecipeURL="
				+ RecipeURL + "]";
	}
		
	}
	
	
	
	
	
	

