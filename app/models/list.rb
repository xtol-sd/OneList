class List < ActiveRecord::Base
  has_many :list_items
  has_many :items, :through => :list_items
  accepts_nested_attributes_for :items
  #accepts_nested_attributes_for :list_items #needed?

  has_one :menu 

  def selected_item_ids
	self.selected_items.map {|item| item.id}
  end

  def selected_items= (ids)
	self.list_items = make_selected_item_array(ids)
  end

  def make_selected_item_array(ids)
	ids.map {|item_id| ListItem.create(:item_id => item_id)}  
  end
   
end

	

# {"utf8"=>"✓", 
# "authenticity_token"=>"EGzTNfU6eDBdm2R6ID1HmAQOCTwC1e8r+5rT0crykpY=", 
# "menu"=>{"list_id"=>"11", 
         # "selected_recipes"=>["", "1", "2"]}, 
         # "button"=>"", "action"=>"create", "controller"=>"menus"}

	

