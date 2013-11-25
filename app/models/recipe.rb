class Recipe < ActiveRecord::Base
  has_many :recipe_items, :dependent => :destroy
  has_many :items, :through => :recipe_items
  accepts_nested_attributes_for :recipe_items

  has_many :join_menu_recipes
  has_many :menus, :through => :join_menu_recipes
 
 end
