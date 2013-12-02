class RecipesController < ApplicationController
  def index
    @recipes = Recipe.all
  end

  def new
    @recipe = Recipe.new
    @units = %w[bottle bunch can cup dash jigger large liter medium
           ounce package piece pinch pint pound quart slice small
           tablespoon teaspoon unit whole]
    #@recipe.recipe_items.build.build_item 
  end

  def create
    @recipe = Recipe.create(recipe_params)
    if @recipe.save
      flash[:notice] = "Recipe Created!"
      redirect_to new_menu_path
    else
      render 'new'
    end
  end

  def edit
  end

  def show
    @recipe = Recipe.find(params[:id])
  end  

  def update
  end

  def destroy
  end

  private
    def recipe_params
      params.require(:recipe).permit!
      # may be incomplete: params.require(:recipe).permit(:name, :comment, :recipe_items_attributes => [:item_amount, :comment, :unit, :item_attributes => [:name, :comment]])
    end
    
end


