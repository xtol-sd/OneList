class AddSelectedToRecipe < ActiveRecord::Migration
  def change
    add_column :recipes, :selected, :boolean
  end
end
