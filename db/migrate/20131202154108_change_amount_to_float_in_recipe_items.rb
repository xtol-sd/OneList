class ChangeAmountToFloatInRecipeItems < ActiveRecord::Migration
  def up
  	remove_column :recipe_items, :item_amount, :integer
  	add_column :recipe_items, :item_amount, :float
  end

  def down
  	remove_column :recipe_items, :item_amount, :float
  	add_column :recipe_items, :item_amount, :integer
  end
end
