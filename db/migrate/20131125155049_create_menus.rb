class CreateMenus < ActiveRecord::Migration
  def change
    create_table :menus do |t|
      t.string :name
      t.string :comment
      t.integer :recipe_id
      t.integer :list_id

      t.timestamps
    end
  end
end
