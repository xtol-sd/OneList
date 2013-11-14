class Ingredient < ActiveRecord::Base
  has_many :recipe_ingredients
  belongs_to :recipe
end
