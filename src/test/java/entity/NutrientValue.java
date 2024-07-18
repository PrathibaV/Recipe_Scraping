package entity;

import java.util.Objects;

import javax.annotation.processing.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="nutrient_value")
public class NutrientValue {
	
	
	
	@Column(name="nutrient_name")
	private String nutrientName;
	
	@Column(name="nutrient_value")
	private String nutrientValue;
	
	@ManyToOne
	@JoinColumn(name = "recipe_id") 
	private Recipe recipe;




	public String getNutrientName() {
		return nutrientName;
	}

	public void setNutrientName(String nutrientName) {
		this.nutrientName = nutrientName;
	}

	public String getNutrientValue() {
		return nutrientValue;
	}

	public void setNutrientValue(String nutrientValue) {
		this.nutrientValue = nutrientValue;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	@Override
	public int hashCode() {
		return Objects.hash(nutrientName, nutrientValue);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NutrientValue other = (NutrientValue) obj;
		return Objects.equals(nutrientName, other.nutrientName) && Objects.equals(nutrientValue, other.nutrientValue);
	}

	
	
	

}
