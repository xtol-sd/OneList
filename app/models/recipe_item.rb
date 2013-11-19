class RecipeItem < ActiveRecord::Base
  belongs_to :recipe
  belongs_to :item
  accepts_nested_attributes_for :item
end
