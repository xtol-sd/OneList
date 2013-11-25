class Menu < ActiveRecord::Base 
has_many :join_menu_recipes
has_many :recipes, :through => :join_menu_recipes
accepts_nested_attributes_for :recipes

belongs_to :list

  def selected_recipe_ids
	self.recipes.map {|recipe| recipe.id}
  end

  def selected_recipes= (ids)
	self.join_menu_recipes = make_selected_recipe_array(ids)
  end

  def make_selected_recipe_array(ids)
	ids.map {|recipe_id| JoinMenuRecipe.create(:recipe_id => recipe_id)}  
  end


end


  # def desired_skill_ids
  #   self.desired_skills.map {|ds| ds.skill_id}
  # end
  
  # def desired_skills= (ids_with_levels)
  #   self.employee_desired_skills = make_desired_skill_array(ids_with_levels)
  # end
  
  # def make_desired_skill_array(ids_with_levels)
  #   ids_with_levels.map {|skill_id, interest_number| EmployeeDesiredSkill.create(:skill_id => skill_id, :interest_level => interest_number)}
  # end




