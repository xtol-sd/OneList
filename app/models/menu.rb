class Menu < ActiveRecord::Base 
has_many :join_menu_recipes
has_many :recipes, :through => :join_menu_recipes
accepts_nested_attributes_for :recipes

belongs_to :list

end



