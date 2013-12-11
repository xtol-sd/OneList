class OthersController < ApplicationController
  def index
    @others = Other.all
    @list = List.last
  end

  def new
    @other = Other.new
  end

  def create
    @other = Other.create(other_params)
    @list = List.last
    if @other.save
      flash[:notice] = "Item Created!"
      redirect_to add_others_path(@list)
    else
      render 'new'
    end
  end

  def edit
  end

  def update
    @list = List.find(params[:id])
    if params[:add_others_button]
      @list.update(list_params)
      flash[:notice] = "Step 2 complete: Other items chosen!"
      redirect_to edit_list_path(@list)
    else
      @list.update(list_params)
      flash[:notice] = "List complete!" 
      redirect_to list_path(@list)
    end
  end

  def destroy
  end

  private
    def other_params
      params.require(:other).permit!
      # params.require(:other).permit(:name, :comment, :selected)
    end
    
end
