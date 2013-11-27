class Menu < ActiveRecord::Base 
has_many :join_menu_recipes
has_many :recipes, :through => :join_menu_recipes
accepts_nested_attributes_for :recipes

belongs_to :list

  def selected_recipe_ids
	self.selected_recipes.map {|recipe| recipe.id}
  end

  def selected_recipes= (ids)
	self.join_menu_recipes = make_selected_recipe_array(ids)
  end

  def make_selected_recipe_array(ids)
	ids.map {|recipe_id| JoinMenuRecipe.create(:recipe_id => recipe_id)}  
  end


end


 




