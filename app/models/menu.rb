class Menu < ActiveRecord::Base 
has_many :join_menu_recipes, :dependent => :destroy
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

  def update_list_items
    #first clear any list items previously associated with this menu,
    #in case of update
    ListItem.where(:menu_id => self.id).destroy_all
    #then create list items for recipes now associated with this menu
    self.recipes.each do |recipe|
      recipe.recipe_items.map {|recipe_item| ListItem.create(
        :list_id => self.list_id, :item_id => recipe_item.item_id, 
        :item_amount => recipe_item.item_amount, :unit => recipe_item.unit,
        :recipe_id => recipe.id, :menu_id => self.id )} 
    end
  end
    
end




 

	

# {"utf8"=>"✓", "_method"=>"patch", 
# "authenticity_token"=>"EGzTNfU6eDBdm2R6ID1HmAQOCTwC1e8r+5rT0crykpY=", 
# "list"=>{"selected_items"=>["", "1", "2", "3", "4", "5"]}, 
# "add_items_button"=>"", "action"=>"update", "controller"=>"lists", 
# "id"=>"11"}

