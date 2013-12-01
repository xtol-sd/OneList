class Recipe < ActiveRecord::Base
  has_many :recipe_items, :dependent => :destroy
  has_many :items, :through => :recipe_items
  #accepts_nested_attributes_for :recipe_items

  has_many :join_menu_recipes
  has_many :menus, :through => :join_menu_recipes
  
  accepts_nested_attributes_for :recipe_items,  :reject_if => :all_blank, 
           :allow_destroy => true
  accepts_nested_attributes_for :items

  validates :name, :presence =>  true

 end
