class MenusController < ApplicationController
  before_action :set_menu, only: [:show, :edit, :update, :destroy]

  # GET /menus
  # GET /menus.json
  def index
    @menus = Menu.all
  end

  # GET /menus/1
  # GET /menus/1.json
  def show
  end

  # GET /menus/new
  def new
    @menu = Menu.new
    @menu.recipes.build
    @recipes = Recipe.all
    @list = List.last
  end

  def edit
    @menu = Menu.find(params[:id])
    @recipes = Recipe.all
  end

  def create
    @menu = Menu.new(menu_params)
    @list = List.last
    #raise params.inspect
    if @menu.save
      flash[:notice] = "Step 1 complete: Recipes chosen!" 
      @menu.add_items_to_list
      redirect_to add_items_path(@list)
    else
      render 'new'
    end
  end

  # PATCH/PUT /menus/1
  # PATCH/PUT /menus/1.json
  def update
    respond_to do |format|
      if @menu.update(menu_params)
        format.html { redirect_to @menu, notice: 'Menu was successfully updated.' }
        format.json { head :no_content }
      else
        format.html { render action: 'edit' }
        format.json { render json: @menu.errors, status: :unprocessable_entity }
      end
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
