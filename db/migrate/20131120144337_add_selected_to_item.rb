class AddSelectedToItem < ActiveRecord::Migration
  def change
    add_column :items, :selected, :boolean
  end
end
