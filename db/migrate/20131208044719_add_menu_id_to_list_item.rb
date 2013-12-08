class AddMenuIdToListItem < ActiveRecord::Migration
  def change
    add_column :list_items, :menu_id, :integer
  end
end
