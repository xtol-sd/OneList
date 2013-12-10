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
  end

  def destroy
  end

  private
    def other_params
      params.require(:other).permit!
      # params.require(:other).permit(:name, :comment, :selected)
    end
    
end
