class AddCategoryToRecipes < ActiveRecord::Migration
  def change
    add_column :recipes, :category, :string
    add_column :recipes, :yields, :string
  end
end
