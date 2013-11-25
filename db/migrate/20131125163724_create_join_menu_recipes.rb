class CreateJoinMenuRecipes < ActiveRecord::Migration
  def change
    create_table :join_menu_recipes do |t|
      t.integer :menu_id
      t.integer :recipe_id

      t.timestamps
    end
  end
end
