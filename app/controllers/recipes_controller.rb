class RecipesController < ApplicationController
  def index
    @recipes = Recipe.all
  end

  def new
    @recipe = Recipe.new
    @recipe.recipe_ingredients.build.build_ingredient
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

  private
  
    def recipe_params
      params.require(:recipe).permit(:name, :comment, :recipe_ingredients_attributes => [:amount, :ingredient_attributes => [:name, :comment]])
    end
end


