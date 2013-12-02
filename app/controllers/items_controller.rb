class ItemsController < ApplicationController
  def index
    @items = Item.all
    @list = List.last
  end

  def new
    @item = Item.new
  end

  def create
    @item = Item.create(item_params)
    @list = List.last
    if @item.save
      flash[:notice] = "Item Created!"
      redirect_to add_items_path(@list)
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
    def item_params
      params.require(:item).permit!
      # params.require(:item).permit(:name, :comment, :selected)
    end
    

end
