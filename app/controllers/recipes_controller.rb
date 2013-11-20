class RecipesController < ApplicationController
  def index
    @recipes = Recipe.all
  end

  def new
    @recipe = Recipe.new
    @recipe.recipe_items.build.build_item 
  end

  def create
    @recipe = Recipe.create(recipe_params)
    if @recipe.save
      flash[:notice] = "Recipe Created!"
      redirect_to recipes_path
    else
      render 'new'
    end
  end

  def edit
  end

  def update
  end

  def destroy
  end

  private
    def recipe_params
      params.require(:recipe).permit(:name, :comment, :recipe_items_attributes => [:item_amount, :comment, :item_attributes => [:name, :comment]])
    end
    
end


