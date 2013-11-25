class RemoveColumnRecipeIdFromMenu < ActiveRecord::Migration
  def change
  	remove_column :menus, :recipe_id, :integer
  end
end
