class MenusController < ApplicationController
  before_action :set_menu, only: [:show, :edit, :update, :destroy]

  def index
    @menus = Menu.all
  end

  def show
    @menu = Menu.find(params[:id])
    @list = List.find(@menu.list_id)
  end

  def new
    @menu = Menu.create
    @menu.recipes.build
    @categories = ["Breakfast", "Lunch", "Dinner", "Dessert"]
    @recipes = Recipe.paginate :page => params[:page], :per_page => 10
    @recipes = @recipes.search(params[:search])
    @list = List.last
  end

  def edit 
    @menu = Menu.find(params[:id])
    @categories = ["Breakfast", "Lunch", "Dinner", "Dessert"]
    @recipes = Recipe.paginate :page => params[:page], :per_page => 10
    @recipes = @recipes.search(params[:search])
    @list = List.find(@menu.list_id)
  end

  def create
    @menu = Menu.new(menu_params)
    @list = List.last
    #raise params.inspect
    if @menu.save
      flash[:notice] = "Step 1 complete: Recipes chosen!" 
      @menu.add_list_items
      redirect_to add_items_path(@list)
    else
      render 'new'
    end
  end

  def update
    @menu = Menu.find(params[:id])
    @list = List.find(@menu.list_id)
    # raise params.inspect
    if params[:final_menu_button]
      @menu.update(menu_params)
      flash[:notice] = 'Recipe selections were successfully updated.'
      @menu.add_list_items 
      redirect_to add_items_path(@list)
    else
     @menu.update(menu_params)
     @menu.add_list_items 
    end 
  end

  def destroy
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_menu
      @menu = Menu.find(params[:id])
    end

    # Never trust parameters from the scary internet, only allow the white list through.
    def menu_params
      params.require(:menu).permit!
    end
end

#params.require(:menu).permit(:name, :comment, :list_id, :selected_recipes)
