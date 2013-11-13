class CreateRecipeIngredients < ActiveRecord::Migration
  def change
    create_table :recipe_ingredients do |t|
      t.int :recipe_id
      t.int :ingredient_id
      t.int :amount

      t.timestamps
    end
  end
end
