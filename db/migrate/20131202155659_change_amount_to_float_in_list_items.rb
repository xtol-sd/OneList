class ChangeAmountToFloatInListItems < ActiveRecord::Migration
  
  def up
  	remove_column :list_items, :item_amount, :integer
  	add_column :list_items, :item_amount, :float
  end

  def down
  	remove_column :list_items, :item_amount, :float
  	add_column :list_items, :item_amount, :integer
  end

end


