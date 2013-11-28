class Item < ActiveRecord::Base
  has_many :recipe_items
  has_many :recipes, :through => :recipe_items

  accepts_nested_attributes_for :recipe_items, allow_destroy: true
  
  has_many :list_items, :dependent => :destroy
  has_many :lists, :through => :list_items
end
