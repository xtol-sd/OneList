class AddRecipeIdToListItem < ActiveRecord::Migration
  def change
    add_column :list_items, :recipe_id, :integer
  end
end
