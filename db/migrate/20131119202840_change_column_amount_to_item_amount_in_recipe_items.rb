class ChangeColumnAmountToItemAmountInRecipeItems < ActiveRecord::Migration
  def self.up
    rename_column :recipe_items, :amount, :item_amount
  end

  def self.down
    rename_column :recipe_items, :item_amount, :amount
  end
end
