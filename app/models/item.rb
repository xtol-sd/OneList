class Item < ActiveRecord::Base
  has_many :recipe_items
  has_many :recipes, :through => :recipe_items

  has_many :list_items, :dependent => :destroy
  has_many :lists, :through => :list_items
end
