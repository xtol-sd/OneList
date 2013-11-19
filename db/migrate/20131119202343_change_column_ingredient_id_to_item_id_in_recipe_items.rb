class ChangeColumnIngredientIdToItemIdInRecipeItems < ActiveRecord::Migration

  def self.up
    rename_column :recipe_items, :ingredient_id, :item_id
  end

  def self.down
    rename_column :recipe_items, :item_id, :ingredient_id
  end

end
