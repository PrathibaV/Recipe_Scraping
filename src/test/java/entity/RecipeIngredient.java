package entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "recipe_ingredient")
public class RecipeIngredient {

	
	@Column(name = "igredient_name")
	private String igredientName;
	
	@Column(name = "quantity")
	private String quantity;
	
	
	@ManyToOne
	@JoinColumn(name = "recipe_id")
	private Recipe recipe;



	public String getIgredientName() {
		return igredientName;
	}

	public void setIgredientName(String igredientName) {
		this.igredientName = igredientName;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}


	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	@Override
	public int hashCode() {
		return Objects.hash(igredientName, recipe);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RecipeIngredient other = (RecipeIngredient) obj;
		return Objects.equals(igredientName, other.igredientName) && Objects.equals(recipe, other.recipe);
	}

		
	
}
