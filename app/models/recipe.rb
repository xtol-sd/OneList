class Recipe < ActiveRecord::Base
  has_many :recipe_items, :dependent => :destroy
  has_many :items, :through => :recipe_items
  accepts_nested_attributes_for :recipe_items
 
 end
