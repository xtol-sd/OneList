desc "Import Recipes"
require 'nokogiri'
require 'rubygems'
task :items => :environment do

	doc = Nokogiri::XML(File.open("/home/kevin/recipes.xml"))    

	recipes = doc.xpath("//recipe")

	r = 1
	i = 1

	recipes.each do |recipe| 
		title = recipe.xpath("title").text
		instructions = recipe.xpath("instructions").text
		Recipe.create(:id => r, :name => title, :comment => instructions)

		# itemname = "", amount = "", unit = ""
		ingredients = recipe.xpath("*/ingredient")
		ingredients.each do |ingredient|
			itemname = ingredient.xpath("item").text
			amount = ingredient.xpath("amount").text
			unit = ingredient.xpath("unit").text
			Item.create(:id => i, :name => itemname)
			RecipeItem.create(:recipe_id => r, :item_id => i, :item_amount => amount)

			i += 1
		end

		r += 1
	end
end