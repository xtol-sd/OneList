desc "Import Recipes"
require 'nokogiri'
require 'rubygems'
task :items => :environment do

  doc = Nokogiri::XML(File.open("recipes.xml")) 

  recipes = doc.xpath("//recipe")
@itemnames = doc.xpath("//key")

items_arr = [] 
@itemnames.each do |itemname| 
	items_arr << itemname.text 
end

itemnames_arr = items_arr.uniq.sort
itemnames_hash = Hash[itemnames_arr.map.with_index.to_a]

itemnames_arr.each do |itemname|
	Item.create(:name => itemname)
end	

  recipes.each do |recipe| 
	ingredients = recipe.xpath("*/ingredient")	
	keys = recipe.xpath("*/ingredient/key")
	if ingredients.count >= 1 && ingredients.count == keys.count	
	   	title = recipe.xpath("title").text
	   	category = recipe.xpath("category").text
	   	yields = recipe.xpath("yields").text	   		   	
		instructions = recipe.xpath("instructions").text
		r = Recipe.create(:name => title, 
			:comment => instructions, :category => category, :yields => yields)
	 
		ingredients.each do |ingredient|
		  itemname = ingredient.xpath("key").text
		  item_id = itemnames_hash[itemname] +1
		  amount = ingredient.xpath("amount").text
		  unit = ingredient.xpath("unit").text
		  RecipeItem.create(:recipe_id => r.id, 
		  	:item_id => item_id, :item_amount => amount)
		end
	end

  end
end