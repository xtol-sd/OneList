class ListsController < ApplicationController
  
  def add_items
    @list = List.last
    @list.items.build
    @items = Item.all
  end

  def index
    @lists = List.all
    @list = List.new 
  end 

  # def new
  #   @list = List.new
  # end

  def create
    @list = List.new 
     if @list.save
       redirect_to new_menu_path
     else
       render 'index'
     end   
  end
3
  def show
    @list = List.find(params[:id])
  end
  
  def edit
    @list = List.find(params[:id])
  end

  def update
    @list = List.find(params[:id])
    #raise params.inspect
    if params[:add_items_button]
      @list.update(list_params)
      flash[:notice] = "Step 2 complete: Items chosen!" 
      redirect_to edit_list_path(@list)
    elsif @list.update(list_params)
      flash[:notice] = "List complete!" 
      redirect_to list_path(@list)
    end
  end

  def destroy
    @list.destroy
    respond_to do |format|
      format.html { redirect_to lists_url }
      format.json { head :no_content }
    end
  end

  private
    def list_params
      params.require(:list).permit!
      #Update to correct permission once controller complete:
      #params.require(:list).permit(:name, :comment, :selected_items)
    end
     
end
