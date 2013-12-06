desc "Import Recipes"
require 'nokogiri'
require 'rubygems'
task :items => :environment do

  doc = Nokogiri::XML(File.open("/home/crystal/rails_projects/OneList/recipes.xml"))    

  recipes = doc.xpath("//recipe")

  recipes.each do |recipe| 
   	title = recipe.xpath("title").text
	instructions = recipe.xpath("instructions").text
	r = Recipe.create(:name => title, 
		:comment => instructions)
 
	ingredients = recipe.xpath("*/ingredient")
	ingredients.each do |ingredient|
	  itemname = ingredient.xpath("item").text
	  amount = ingredient.xpath("amount").text
	  unit = ingredient.xpath("unit").text
	  i = Item.create(:name => itemname)
	  RecipeItem.create(:recipe_id => r.id, 
	  	:item_id => i.id, :item_amount => amount)
	end

  end
end