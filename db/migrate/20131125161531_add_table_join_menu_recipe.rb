class AddTableJoinMenuRecipe < ActiveRecord::Migration
  def change
    create_table :joinmenurecipes do |t|
      t.integer :menu_id
      t.integer :recipe_id
      t.timestamps
    end
  end
end
