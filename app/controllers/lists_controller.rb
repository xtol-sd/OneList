class ListsController < ApplicationController
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

  def show
    @list = List.find(params[:id])
  end
  
  def edit
  end

  def update
  end

  def destroy
  end

  private
    def list_params
      #params.require(:list).permit!
      #Update to correct permission once controller complete:
      params.require(:list).permit(:name, :comment)
    end
     
end
