class DropTableIngredients < ActiveRecord::Migration
  def up
    drop_table :ingredients
  end

  def down
    create_table :ingredients do |t|
      t.string :name
      t.string :comment

      t.timestamps
    end
  end
end
