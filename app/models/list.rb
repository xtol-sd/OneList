class List < ActiveRecord::Base
  has_many :list_items, :dependent => :destroy
  has_many :items, :through => :list_items
  accepts_nested_attributes_for :items
  #accepts_nested_attributes_for :list_items #needed?
  has_many :list_others, :dependent => :destroy
  has_many :others, :through => :list_others
  accepts_nested_attributes_for :others

  has_one :menu 

  def selected_item_ids
	  self.selected_items.map {|item| item.id}
  end

  def selected_items= (ids)
	  ids = ids.reject{ |i| i.empty? }
    former_items = ListItem.find_all_by_list_id(self.id)
    additional_items = make_selected_item_array(ids)

    self.list_items = [former_items + additional_items].flatten
  end

  def make_selected_item_array(ids)
	  ids.map {|item_id| ListItem.create(:item_id => item_id)}  
  end
   


  def selected_other_ids
    self.selected_others.map {|other| other.id}
  end

  def selected_others= (ids)
    ids = ids.reject{ |i| i.empty? }
    # former_items = ListItem.find_all_by_list_id(self.id)
    self.list_others = make_selected_other_array(ids)

    # self.list_others = [former_others + additional_items].flatten
  end

  def make_selected_other_array(ids)
    ids.map {|other_id| ListOther.create(:other_id => other_id)}  
  end
end

	

# {"utf8"=>"✓", 
# "authenticity_token"=>"EGzTNfU6eDBdm2R6ID1HmAQOCTwC1e8r+5rT0crykpY=", 
# "menu"=>{"list_id"=>"11", 
         # "selected_recipes"=>["", "1", "2"]}, 
         # "button"=>"", "action"=>"create", "controller"=>"menus"}

	

