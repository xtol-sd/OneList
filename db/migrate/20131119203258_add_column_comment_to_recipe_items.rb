class AddColumnCommentToRecipeItems < ActiveRecord::Migration
  def change
    add_column :recipe_items, :comment, :string
  end
end
