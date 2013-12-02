class AddUnitToRecipeItems < ActiveRecord::Migration
  def change
    add_column :recipe_items, :unit, :string
  end
end
