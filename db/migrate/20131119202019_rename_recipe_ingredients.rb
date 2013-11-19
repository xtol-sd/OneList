class RenameRecipeIngredients < ActiveRecord::Migration
  def self.up
  rename_table :recipe_ingredients, :recipe_items
  end

 def self.down
  rename_table :recipe_items, :recipe_ingredients
 end

end
