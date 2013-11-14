class RecipesController < ApplicationController
  def index
    @recipes = Recipe.all
  end

  def new
    @recipe = Recipe.new
  end

  def create
    @recipe = Recipe.new(recipe_params)

    if @recipe.save
      flash[:notice] = "Recipe Created!"
      redirect_to recipes_path
    else
      render 'new'
    end
  end

  def edit
  end

  private
  
    def recipe_params
      params.require(:recipe).permit(:name, :comment, :ingredients_attributes => [:name, :comment])
    end
end
