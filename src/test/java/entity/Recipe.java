package entity;

import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "lchf_recipe")
public class Recipe {

	@Id	
	@Column(name="recipe_id")
	private Long recipeId;
	
	@Column(name="recipe_name")
	private String recipeName;
	
	@Column(name="recipe_category")
	private String recipeCategory;
	
	@Column(name="food_category")
	private String foodCategory;
	
	
	@Column(name="prep_time")
	private String prepTime;
	
	@Column(name="cook_time")
	private String cookTime;
	
	
	@Column(name="no_of_servings")	
	private Integer noOfServings;
	
	private String noOfServing;
	
	public String getNoOfServing() {
		return noOfServing;
	}

	public void setNoOfServing(String noOfServing) {
		this.noOfServing = noOfServing;
	}

	@Column(name="cuisine_category")	
	private String cuisineCategory;
	
	@Column(name="recipe_desc")	
	private String recipeDesc;
	
	@Column(name="preparation_method")	
	private String preparationMethod;
	
	
	@Column(name="recipe_url")	
	private String recipeUrl;
	
	@Column(name="addon_or_eliminate")
	private String addonOrEliminate;

	@OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<NutrientValue> nutrientValues;
	
	@OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<RecipeIngredient> recipeIngredients;
		

	private String  recipeTags;

	public Set<NutrientValue> getNutrientValues() {
		return nutrientValues;
	}

	public void setNutrientValues(Set<NutrientValue> nutrientValues) {
		this.nutrientValues = nutrientValues;
	}
	
	
	public Long getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(Long recipeId) {
		this.recipeId = recipeId;
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


	public String getCuisineCategory() {
		return cuisineCategory;
	}

	public void setCuisineCategory(String cuisineCategory) {
		this.cuisineCategory = cuisineCategory;
	}

	public String getRecipeDesc() {
		return recipeDesc;
	}

	public void setRecipeDesc(String recipeDesc) {
		this.recipeDesc = recipeDesc;
	}

	public String getPreparationMethod() {
		return preparationMethod;
	}

	public void setPreparationMethod(String preparationMethod) {
		this.preparationMethod = preparationMethod;
	}


	public String getRecipeUrl() {
		return recipeUrl;
	}

	public void setRecipeUrl(String recipeUrl) {
		this.recipeUrl = recipeUrl;
	}

	public String getPrepTime() {
		return prepTime;
	}

	public void setPrepTime(String prepTime) {
		this.prepTime = prepTime;
	}



	public String getCookTime() {
		return cookTime;
	}

	public void setCookTime(String cookTime) {
		this.cookTime = cookTime;
	}

	public Integer getNoOfServings() {
		return noOfServings;
	}

	public void setNoOfServings(Integer noOfServings) {
		this.noOfServings = noOfServings;
	}

	public Set<RecipeIngredient> getRecipeIngredients() {
		return recipeIngredients;
	}

	public void setRecipeIngredients(Set<RecipeIngredient> recipeIngredients) {
		this.recipeIngredients = recipeIngredients;
	}
	
	public void addRecipeIngredients(RecipeIngredient recipeIngredient) {
		this.recipeIngredients.remove(recipeIngredient);
		recipeIngredient.setRecipe(this);
	}

	public void removeRecipeIngredients(RecipeIngredient recipeIngredient) {
		this.recipeIngredients.remove(recipeIngredient);
		recipeIngredient.setRecipe(null);
	}

	

	public String getRecipeTags() {
		return recipeTags;
	}

	public void setRecipeTags(String recipeTags) {
		this.recipeTags = recipeTags;
	}

	public String getAddonOrEliminate() {
		return addonOrEliminate;
	}

	public void setAddonOrEliminate(String addonOrEliminate) {
		this.addonOrEliminate = addonOrEliminate;
	}

	@Override
	public int hashCode() {
		return Objects.hash(recipeId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Recipe other = (Recipe) obj;
		return Objects.equals(recipeId, other.recipeId);
	}

	
	
	
}
