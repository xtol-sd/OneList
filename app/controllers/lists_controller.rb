class ListsController < ApplicationController
  
  # def add_items
  #   @list = List.last
  #   @menu = Menu.find_by_list_id(@list.id)
  #   # @list.items.build
  #   @items = Item.all
  # end

  def add_others
    @list = List.last
    @menu = Menu.find_by_list_id(@list.id)
    # @list.items.build
    @others = Other.all
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

  def show
    @list = List.find(params[:id])
    @menu = Menu.find_by_list_id(@list.id)
  end
  
  def edit
    @list = List.find(params[:id])
    @menu = Menu.find_by_list_id(@list.id)
  end

  def update
    @list = List.find(params[:id])
    if params[:add_others_button]
      @list.update(list_params)
      flash[:notice] = "Step 2 complete: Items chosen!"
      redirect_to edit_list_path(@list)
    else
      @list.update(list_params)
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
      # Update to correct permission once controller complete:
      # params.require(:list).permit(:name, :comment, :list_item_ids, :selected_others)
    end

#from view lists add_items page (tab 2: choose items)
# {"utf8"=>"✓", "_method"=>"patch", 
# "authenticity_token"=>"Cc207N8Aq8DfXUzorjCTii0eGaPIB4uqdByu8A2xH4k=", 
# "list"=>{"selected_others"=>["", "3", "4", "5"]}, 
# "add_items_button"=>"", 
# "action"=>"update", "controller"=>"lists", 
# "id"=>"26"}
     
end

#update action code I wrote to update by hand, in else section
#turns out just using update works!
# ids = params[:list][:list_item_ids].reject{|i| i.empty?}
#       if ids.present?
#         @list.list_items = ListItem.find(ids) 
#       else
#         @list.list_items = []
#       end  
#       flash[:notice] = "List complete!" 
#       redirect_to list_path(@list)