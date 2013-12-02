class AddUnitToListItems < ActiveRecord::Migration
  def change
    add_column :list_items, :unit, :string
  end
end
